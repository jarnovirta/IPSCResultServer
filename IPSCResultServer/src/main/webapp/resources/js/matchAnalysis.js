//JavaScript for loading and drawing Google Charts in matchAnalysisPage.jsp


// Arrays used to determine green/red color codes 
// used in setTableCellColors

var chartsReady = false;
var competitorsByDivision = {};

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

	
	setCompetitorsByDivisionList();
	
	setPageGeneralInfoElements(competitorMatchAnalysisData.competitorResultData.competitor, compareToCompetitorMatchAnalysisData.competitorResultData.competitor);
	
	setCompetitorSelectElements();
	
	drawAccuracyPieChart('competitorAccuracyChart', competitorMatchAnalysisData);
	drawAccuracyPieChart('compareToCompetitorAccuracyChart', compareToCompetitorMatchAnalysisData);
	
	drawPercentByStageChart('percentByStageChart', competitorMatchAnalysisData, compareToCompetitorMatchAnalysisData);
	
	drawTimeByStageChart('timeByStageChart', competitorMatchAnalysisData, compareToCompetitorMatchAnalysisData);
			
	updateErrorCostAnalysisTable('competitorErrorCostAnalysisTable', competitorMatchAnalysisData);
	updateErrorCostAnalysisTable('compareToCompetitorErrorCostAnalysisTable', compareToCompetitorMatchAnalysisData);
	
	updateStageResultsTable('competitorStageResultsTable', competitorMatchAnalysisData);
	updateStageResultsTable('compareToCompetitorStageResultsTable', compareToCompetitorMatchAnalysisData);
			
	updateHitsTable('competitorHitsTable', competitorMatchAnalysisData);
	updateHitsTable('compareToCompetitorHitsTable', compareToCompetitorMatchAnalysisData);
	
	setStageResultsTableCellColors();
		
}

function setCompetitorsByDivisionList() {
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
function setPageGeneralInfoElements(competitor, compareToCompetitor) {
	if (competitor == null || compareToCompetitor == null) return;
	$('#matchName').html(match.match_name);
	$('#pageHeadingCompetitorName, #analysisColumnCompetitorName').each(function() {
		$(this).html(competitor.sh_fn + " " + competitor.sh_ln);
	});
	$('#analysisColumnCompareToCompetitorName').html(compareToCompetitor.sh_fn + " " + compareToCompetitor.sh_ln);

}

function setCompetitorSelectElements() {
	setCompetitorSelectOptions('competitor', competitorMatchAnalysisData.competitorResultData.competitor);
	setCompetitorSelectOptions('compareToCompetitor', compareToCompetitorMatchAnalysisData.competitorResultData.competitor);
	
	
}

function setCompetitorSelectOptions(selectId, selectedCompetitor) {
	var optionsAsString = "";
	$.each(competitorsByDivision, function(division, competitors) {
		var optionsString = "<optgroup label='" + division + "'>";
		
		$.each(competitorsByDivision[division], function(key, competitor) {
			optionsString += "<option value=" + competitor.sh_uid;
			if (competitor.sh_uid == selectedCompetitor.sh_uid) optionsString += " selected";
			optionsString += ">" +  competitor.sh_ln + ", " + competitor.sh_fn + "</option>";
		});
		optionsString += "</optgroup>";
		$('#' + selectId).append($(optionsString));
	});
}
function submitCompetitorsChange() {
	hideContent();
	getChartData('${matchId}', $('#competitor').val(), $('#compareToCompetitor').val()); 
}

function setCompetitorName(data) {
	if (data.competitorResultData == null) return;
	
	data.name = data.competitorResultData.competitor.sh_fn + " " 
	+ data.competitorResultData.competitor.sh_ln
	+ " (" 
	+ data.competitorResultData.competitor.sh_dvp 
	+ "/"
	+ data.competitorResultData.competitor.powerFactor
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
	if (competitorData == null || competitorData.competitorResultData == null) return;
	var dataSet = [
		[competitorData.competitorResultData.aHitsSum, 
			competitorData.competitorResultData.cHitsSum,
			competitorData.competitorResultData.dHitsSum,
			competitorData.competitorResultData.missSum,
			competitorData.competitorResultData.noshootHitsSum,
			competitorData.competitorResultData.proceduralPenaltiesSum]
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
	if (competitorData == null || competitorData.competitorResultData == null || match.match_stages == null) return;
	var dataSet = [];
	$.each(match.match_stages, function(index, stage) {
		var line = competitorData.stageResultDataLines[stage.stage_uuid];
		if (line == null) return;
		var stageResultsPath = "${baseUrl}stageResults?matchId="
			+ match.match_id
			+ "&stageId="
			+ stage.stage_uuid
			+ "&division="
			+ competitorData.competitorResultData.competitor.sh_dvp;
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
	if (competitorData == null || competitorData.competitorResultData == null || match.match_stages == null) return;
	var dataSet = [];
	$.each(match.match_stages, function(index, stage) {
		var line = competitorData.errorCostTableLines[stage.stage_uuid];
		var stageResultsPath = "${baseUrl}stageResults?matchId="
			+ match.match_id
			+ "&stageId="
			+ stage.stage_uuid
			+ "&division="
			+ competitorData.competitorResultData.competitor.sh_dvp;
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
	if (competitorData == null || competitorData.competitorResultData == null) return;
	var data = google.visualization.arrayToDataTable([
		['Hit zone', 'Hits'],
        ['A', competitorData.competitorResultData.aHitsSum],
        ['C', competitorData.competitorResultData.cHitsSum],
        ['D', competitorData.competitorResultData.dHitsSum],
        ['Miss', competitorData.competitorResultData.missSum],
        ['NS', competitorData.competitorResultData.noshootHitsSum]
	]);
	var options = {
		width: 600,
		height: 400,
		};
	var chart = new google.visualization.PieChart(document.getElementById(chartId));
	chart.draw(data, options);
}


function drawPercentByStageChart(chartId, competitor1, competitor2) {
	if (competitor1 == null || competitor2 == null || match == null 
			|| competitorMatchAnalysisData.competitorResultData.stagePercentages == null
			|| compareToCompetitorMatchAnalysisData.competitorResultData.stagePercentages == null) return;
	
	var competitor1Percentages;
	var competitor2Percentages;
	// Show division stage percentages if same division for both competitors
	// and Combined results stage percentages if different divisions.
	if (competitor1.competitorResultData.competitor.sh_dvp == competitor2.competitorResultData.competitor.sh_dvp) {
		competitor1Percentages = competitor1.competitorResultData.stagePercentages;
		competitor2Percentages = competitor2.competitorResultData.stagePercentages;
		$('#stageResultChartDivision').html("");
	}
	else {
		
		competitor1Percentages = competitor1.competitorResultData.combinedDivStagePercentages;
		competitor2Percentages = competitor2.competitorResultData.combinedDivStagePercentages;
		
		$('#percentByStageDivision').html("(Combined results)");
	}
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Stage');
	data.addColumn('number', competitor1.name);
	data.addColumn('number', competitor2.name);
	
	var rows = [];
	$.each(competitorMatchAnalysisData.competitorResultData.stagePercentages, function(stageNumber) {
		rows[stageNumber - 1] = [stageNumber.toString(), competitor1Percentages[stageNumber], 
			competitor2Percentages[stageNumber]];
	});
	
	data.addRows(rows);
	
	var options = {
		width: 600,
		height: 400,
		vAxis: {format: '#.##' }

	};
	var chart = new google.charts.Line(document.getElementById(chartId));
	chart.draw(data, google.charts.Line.convertOptions(options));
}
function drawTimeByStageChart(chartId, competitor1, competitor2) {
	if (competitor1 == null || competitor2 == null || match == null || match.match_stages == null) return;
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Stage');
	data.addColumn('number', competitor1.name);
	data.addColumn('number', competitor2.name);
	
	var rows = [];
	$.each(match.match_stages, function(index, stage) {
		
		rows[index] = [stage.stage_number.toString(),
			competitor1.competitorResultData.scoreCards[stage.id].time,
			competitor2.competitorResultData.scoreCards[stage.id].time ];
	});

	data.addRows(rows);
	
	var options = {
		width: 600,
		height: 400
	};
	var chart = new google.charts.Line(document.getElementById(chartId));
	chart.draw(data, google.charts.Line.convertOptions(options));
	
}
function competitorDivisionChange() {
	alert("Competitor division change");
}
