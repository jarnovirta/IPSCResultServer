//JavaScript for loading and drawing Google Charts in matchAnalysisPage.jsp


// Arrays used to determine green/red color codes 
// used in setTableCellColors

var chartsReady = false;
var competitorsByDivision;

var match;

var competitorMatchAnalysisData;
var compareToCompetitorMatchAnalysisData;

$(document).ready(function() {
	hideContent();
	initializeHitsTable('competitorHitsTable');
	initializeHitsTable('compareToCompetitorHitsTable');
	
	initializeStageResultsTable('competitorStageResultsTable');
	initializeStageResultsTable('compareToCompetitorStageResultsTable');
	
	initializeErrorCostAnalysisTable('competitorErrorCostAnalysisTable');
	initializeErrorCostAnalysisTable('compareToCompetitorErrorCostAnalysisTable');
	
});


getChartData('${matchId}', '${competitorId}', '${compareToCompetitorId}');
google.charts.load('current', {'packages':['corechart', 'line']});
google.charts.setOnLoadCallback(function() {
	chartsReady = true;
});
 
function getChartData(matchId, competitorId, compareToCompetitorId) {
	var path = window.location.pathname.substr(0, window.location.pathname.lastIndexOf('/'));
	path += '/matchAnalysis/data?matchId=' + matchId + '&competitorId=' + competitorId;
	path += '&compareToCompetitorId=' + compareToCompetitorId;
	
	$.getJSON(path, handleAjaxResponse);
	
}

function handleAjaxResponse(data, status) {
	if (status != 'success') {
		alert("Problem loading data: " + status);
	}
	else {
		if (chartsReady == true) {
			setContentData(data);
			showContent();
		}
		else {
			var interval = setInterval(function() {
				if (chartsReady == true) {
					clearInterval(interval);
					setContentData(data);
					showContent();
				}
			}, 50);
		}
	}
}
function setContentData(data) {
	match = data.match;
	
	competitorMatchAnalysisData = data.competitorMatchAnalysisData;
	
	compareToCompetitorMatchAnalysisData = data.compareToCompetitorMatchAnalysisData;
			
	setCompetitorName(competitorMatchAnalysisData);
	setCompetitorName(compareToCompetitorMatchAnalysisData);
	
	setCompetitorHitSums(competitorMatchAnalysisData);
	setCompetitorHitSums(compareToCompetitorMatchAnalysisData);
	
	setCompetitorsByDivisionList();
	
	setPageGeneralInfoElements();
	
	setCompetitorSelectElements();
	
	drawAccuracyPieChart('competitorAccuracyChart', competitorMatchAnalysisData);
	drawAccuracyPieChart('compareToCompetitorAccuracyChart', compareToCompetitorMatchAnalysisData);
	
	drawPercentByStageChart();
	
	drawTimeByStageChart();
			
	updateErrorCostAnalysisTable('competitorErrorCostAnalysisTable', competitorMatchAnalysisData);
	updateErrorCostAnalysisTable('compareToCompetitorErrorCostAnalysisTable', compareToCompetitorMatchAnalysisData);
	
	updateStageResultsTable('competitorStageResultsTable', competitorMatchAnalysisData);
	updateStageResultsTable('compareToCompetitorStageResultsTable', compareToCompetitorMatchAnalysisData);
			
	updateHitsTable('competitorHitsTable', competitorMatchAnalysisData);
	updateHitsTable('compareToCompetitorHitsTable', compareToCompetitorMatchAnalysisData);
	
	setStageResultsTableCellColors();
		
}
function setCompetitorHitSums(competitorData) {
	competitorData.aHitsSum = 0;
	competitorData.cHitsSum = 0;
	competitorData.dHitsSum = 0;
	competitorData.missSum = 0;
	competitorData.noshootHitsSum = 0;
	proceduralPenaltiesSum = 0;
	$.each(competitorData.stageResultDataLines, function(key, line) {
		competitorData.aHitsSum += line.scoreCard.aHits;
		competitorData.cHitsSum += line.scoreCard.cHits;
		competitorData.dHitsSum += line.scoreCard.dHits;
		competitorData.missSum += line.scoreCard.misses;
		competitorData.noshootHitsSum += line.scoreCard.noshootHits;
		competitorData.proceduralPenaltiesSum += line.scoreCard.proceduralPenalties;
	});
	
}
function setCompetitorsByDivisionList() {
	competitorsByDivision = {};
	$.each(match.divisionsWithResults, function(key, division) {
		if (division != 'Combined' && !Object.keys(competitorsByDivision).includes(division)) competitorsByDivision[division] = [];
	});
	$.each(match.match_shooters, function(key, competitor) {
		competitorsByDivision[competitor.sh_dvp].push(competitor);
		if (Object.keys(competitorsByDivision).includes('Combined')) competitorsByDivision['Combined'].push(competitor);
	});
	$.each(Object.keys(competitorsByDivision), function(key, division) {
		competitorsByDivision[division].sort(sortAlphabetically);
		
	});
}
function sortAlphabetically(competitor, compareToCompetitor) {
	if(competitor.sh_ln.toLowerCase() < compareToCompetitor.sh_ln.toLowerCase()) return -1;
    if(competitor.sh_ln.toLowerCase() > compareToCompetitor.sh_ln.toLowerCase()) return 1;
}

function showContent() {
	$("#loader").hide();
	$("#contentDiv").show();
	

}
function hideContent() {
	$("#loader").show();
	$("#contentDiv").hide();
	
}
function setPageGeneralInfoElements() {
	if (competitorMatchAnalysisData == null || compareToCompetitorMatchAnalysisData == null) return;
	$('#matchName').html(match.match_name);
	$('#pageHeadingCompetitorName, #analysisColumnCompetitorName').each(function() {
		$(this).html(competitorMatchAnalysisData.name);
	});
	$('#analysisColumnCompareToCompetitorName').html(compareToCompetitorMatchAnalysisData.name);

}

function setCompetitorSelectElements() {
	setCompetitorSelectOptions('competitor', competitorMatchAnalysisData.competitor);
	setCompetitorSelectOptions('compareToCompetitor', compareToCompetitorMatchAnalysisData.competitor);
	
	
}

function setCompetitorSelectOptions(selectId, selectedCompetitor) {
	var optionsAsString = "";
	var optionsHtml = "";
	$.each(competitorsByDivision, function(division, competitors) {
		optionsHtml += "<optgroup label='" + division + "'>";
		
		$.each(competitorsByDivision[division], function(key, competitor) {
			optionsHtml += "<option value=" + competitor.sh_uid;
			if (competitor.sh_uid == selectedCompetitor.sh_uid) optionsHtml += " selected";
			optionsHtml += ">" +  competitor.sh_ln + ", " + competitor.sh_fn + "</option>";
		});
		optionsHtml += "</optgroup>";
		
	});
	$('#' + selectId).empty().append($(optionsHtml));
}
function submitCompetitorsChange() {
	hideContent();
	getChartData('${matchId}', $('#competitor').val(), $('#compareToCompetitor').val()); 
}

function setCompetitorName(data) {
	if (data == null) return;
	
	data.competitor.name = data.competitor.sh_fn + " " 
	+ data.competitor.sh_ln
	+ " (" 
	+ data.competitor.sh_dvp 
	+ "/"
	+ data.competitor.powerFactor
	+ ")";
	
}


function initializeHitsTable(tableId) {
	$('#' + tableId).DataTable( {
        columns: [
        	{ title: "A" },
            { title: "C" },
            { title: "D" },
            { title: "Miss" },
            { title: "NS" },
            { title: "Proc." }
        ],
        paging: false,
		searching: false,
		sort: false,
		info: false, 
    } );
}
function updateHitsTable(tableId, competitorData) {
	if (competitorData == null) return;
	var dataSet = [
		[competitorData.aHitsSum, 
			competitorData.cHitsSum,
			competitorData.dHitsSum,
			competitorData.missSum,
			competitorData.noshootHitsSum,
			competitorData.proceduralPenaltiesSum]
	];
	var table = $('#' + tableId).DataTable();
	table.clear();
	table.rows.add(dataSet);
	table.draw();
}
function setStageResultsTableCellColors() {
	var table = $('#competitorStageResultsTable').DataTable();
	var compareToTable = $('#compareToCompetitorStageResultsTable').DataTable();
	
	table.cells().eq(0).each(function (index) {
	    var node = table.cell(index).node();
	    var compareToNode = compareToTable.cell(index).node();
	    var hitFactor = table.row(index.row).data()[9];
    	var compareToHitFactor = compareToTable.row(index.row).data()[9];
    	
    	if (hitFactor > compareToHitFactor) {
    		$(node).css('background-color', '#c2f0c2');
    		$(node).css('border', '1px solid #a6a6a6');
    		$(compareToNode).css('background-color', 'LightPink');
    		$(compareToNode).css('border', '1px solid #a6a6a6');
    	}
    	else {
    		$(node).css('background-color', 'LightPink');
    		$(node).css('border', '1px solid #a6a6a6');
    		$(compareToNode).css('background-color', '#c2f0c2');
    		$(compareToNode).css('border', '1px solid #a6a6a6');
    	}
	});
	table.draw();
}
function initializeStageResultsTable(tableId) {
	$('#' + tableId).DataTable( {
        columns: [
        	{ title: "Stage" },
            { title: "A" },
            { title: "C" },
            { title: "D" },
            { title: "M" },
            { title: "NS" },
            { title: "Proc." },
            { title: "Points" },
            { title: "Time", render: $.fn.dataTable.render.number( '.', ',', 2)},
            { title: "HF", render: $.fn.dataTable.render.number( '.', ',', 4) },
            { title: "Stage Points", render: $.fn.dataTable.render.number( '.', ',', 4) },
            { title: "Place" },
            { title: "%", render: $.fn.dataTable.render.number( '.', ',', 2)},
            { title: "Diff.", render: $.fn.dataTable.render.number( '.', ',', 2) },
        ],
		paging: false,
		searching: false,
		sort: false,
		info: false, 
    } );	
}
function updateStageResultsTable(tableId, competitorData) {
	if (competitorData == null || match.match_stages == null) return;
	var dataSet = [];
	$.each(match.match_stages, function(index, stage) {
		var line = competitorData.stageResultDataLines[stage.stage_uuid];
		if (line == null) return;
		var stageResultsPath = "${baseUrl}stageResults?matchId="
			+ match.match_id
			+ "&stageId="
			+ stage.stage_uuid
			+ "&division="
			+ competitorData.competitor.sh_dvp;
		var stageName = stage.stage_number + ": " 
			+ stage.stage_name;
		dataSet[index] = [
			"<a href='" + stageResultsPath + "'>"+ stageName + '</a>',
			line.scoreCard.aHits,
			line.scoreCard.cHits,
			line.scoreCard.dHits,
			line.scoreCard.misses,
			line.scoreCard.noshootHits,
			line.scoreCard.proc,
			line.scoreCard.rawpts,
			line.scoreCard.time,
			line.scoreCard.hitFactor,
			line.stagePoints,
			line.stageRank,
			line.stageScorePercentage,
			null
		]
	});
	var table = $('#' + tableId).DataTable();
	table.clear();
	table.rows.add(dataSet);
	table.draw();
}
function initializeErrorCostAnalysisTable(tableId) {
	$('#' + tableId).DataTable( {
        columns: [
        	{ title: "Stage" },
            { title: "Value" },
            { title: "Time", render: $.fn.dataTable.render.number( '.', ',', 2) },
            { title: "A-time", render: $.fn.dataTable.render.number( '.', ',', 2) },
            { title: "Error cost" },
        ],
        paging: false,
		searching: false,
		sort: false,
		info: false, 
    } );	
}
function updateErrorCostAnalysisTable(tableId, competitorData) {
	if (competitorData == null || match.match_stages == null) return;
	
	var dataSet = [];
	$.each(match.match_stages, function(index, stage) {
		var line = competitorData.errorCostTableLines[stage.stage_uuid];
		var stageResultsPath = "${baseUrl}stageResults?matchId="
			+ match.match_id
			+ "&stageId="
			+ stage.stage_uuid
			+ "&division="
			+ competitorData.competitor.sh_dvp;
		var stageName = stage.stage_number + ": " 
			+ stage.stage_name;
		var aTime;
		var errorCosts;
		if (line.scoreCard.hitFactor > 0) {
			aTime = line.aTime;
			errorCosts = "C=" + line.cCost.toString().replace(".", ",");
			errorCosts +=" / D=" + line.dCost.toString().replace(".", ",");
			errorCosts +=" / NS=" + line.proceduralPenaltyAndNoShootCost.toString().replace(".", ",");
			errorCosts +=" / Miss=" + line.missCost.toString().replace(".", ",");
		}
		else {
			aTime = "-";
			errorCosts = "-";
		}
		
		dataSet[index] = [
			"<a href='" + stageResultsPath + "'>"+ stageName + '</a>',
			stage.maxPoints + " (" + line.stageValuePercentage + "%)",
			line.scoreCard.time,
			aTime,
			errorCosts
		]
	});
	var table = $('#' + tableId).DataTable();
	table.clear();
	table.rows.add(dataSet);
	table.draw();
}

function drawAccuracyPieChart(chartId, competitorData) {
	if (competitorData == null) return;
	var data = google.visualization.arrayToDataTable([
		['Hit zone', 'Hits'],
        ['A', competitorData.aHitsSum],
        ['C', competitorData.cHitsSum],
        ['D', competitorData.dHitsSum],
        ['Miss', competitorData.missSum],
        ['NS', competitorData.noshootHitsSum]
	]);
	var options = {
		width: 600,
		height: 400,
		};
	var chart = new google.visualization.PieChart(document.getElementById(chartId));
	chart.draw(data, options);
}


function drawPercentByStageChart() {
	if (match == null || competitorMatchAnalysisData.divisionStagePercentages == null
			|| compareToCompetitorMatchAnalysisData.divisionStagePercentages == null) return;
	var competitorPercentages;
	var compareToCompetitorPercentages;
	
	// Show division stage percentages if same division for both competitors
	// and Combined results stage percentages if different divisions.
	if (competitorMatchAnalysisData.competitor.sh_dvp == compareToCompetitorMatchAnalysisData.competitor.sh_dvp) {
		competitorPercentages = competitorMatchAnalysisData.divisionStagePercentages;
		compareToCompetitorPercentages = compareToCompetitorMatchAnalysisData.divisionStagePercentages;
		$('#percentByStageDivision').html("");
	}
	else {
		competitorPercentages = competitorMatchAnalysisData.combinedResultsStagePercentages;
		compareToCompetitorPercentages = compareToCompetitorMatchAnalysisData.combinedResultsStagePercentages;
		
		$('#percentByStageDivision').html("(Combined results)");
	}
	
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Stage');
	data.addColumn('number', competitorMatchAnalysisData.competitor.name);
	data.addColumn('number', compareToCompetitorMatchAnalysisData.competitor.name);
	
	var rows = [];
	$.each(competitorMatchAnalysisData.divisionStagePercentages, function(stageNumber) {
		rows[stageNumber - 1] = [stageNumber.toString(), competitorPercentages[stageNumber], 
			compareToCompetitorPercentages[stageNumber]];
	});
	
	data.addRows(rows);
	
	var options = {
		width: 600,
		height: 400,
		vAxis: {format: '#.##' }

	};
	var chart = new google.charts.Line(document.getElementById('percentByStageChart'));
	chart.draw(data, google.charts.Line.convertOptions(options));
}
function drawTimeByStageChart() {
	if (competitorMatchAnalysisData.stageResultDataLines == null 
			|| compareToCompetitorMatchAnalysisData.stageResultDataLines == null
			|| match.match_stages == null) {
		return;
	}
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Stage');
	data.addColumn('number', competitorMatchAnalysisData.competitor.name);
	data.addColumn('number', compareToCompetitorMatchAnalysisData.competitor.name);
	
	var rows = [];
	$.each(match.match_stages, function(index, stage) {
		rows[index] = [stage.stage_number.toString(),
			competitorMatchAnalysisData.stageResultDataLines[stage.stage_uuid].scoreCard.time,
			compareToCompetitorMatchAnalysisData.stageResultDataLines[stage.stage_uuid].scoreCard.time];
	});

	data.addRows(rows);
	
	var options = {
		width: 600,
		height: 400
	};
	var chart = new google.charts.Line(document.getElementById('timeByStageChart'));
	chart.draw(data, google.charts.Line.convertOptions(options));
	
}
