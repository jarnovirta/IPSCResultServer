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
	initializeHitsTable('hitsTable');
	initializeStageResultsTable('stageResultsTable');
	initializeErrorCostAnalysisTable('errorCostAnalysisTable');

	// Attach events for tab and pill change to redraw charts and tables
	// to ensure they are drawn correctly.
	$(document).on('shown.bs.tab', 'a[data-toggle="pill"]', function (e) {
		var targetPillHref = $(e.target).attr("href").substring(1);
		redrawChartOrTable(targetPillHref);
	});
	$(document).on('shown.bs.tab', 'a[data-toggle="tab"]', function (e) {
		var targetTabHref = $(e.target).attr("href").substring(1);
		redrawChartOrTable(targetTabHref);
	});
	
});

getChartData('${matchId}', '${competitorId}', '${compareToCompetitorId}');
google.charts.load('current', {'packages':['corechart', 'line']});
google.charts.setOnLoadCallback(function() {
	chartsReady = true;
});

// Must redraw charts and tables when first shown to make sure they are displayed
// correctly

function redrawChartOrTable(targetHref) {
	
	switch(targetHref) {
	case "accuracyTab":
	case "competitorAccuracyPill":
		drawAccuracyPieChart('competitorAccuracyChart-small', competitorMatchAnalysisData);
		break;
	case "compareToCompetitorAccuracyPill":
		drawAccuracyPieChart('compareToCompetitorAccuracyChart-small', compareToCompetitorMatchAnalysisData);
		break;
	case "hitsTab":
	case "competitorHitsPill":
		updateHitsTable('competitorHitsTable', competitorMatchAnalysisData);
		
		break;
	case "compareToCompetitorHitsPill":
		updateHitsTable('compareToCompetitorHitsTable', compareToCompetitorMatchAnalysisData);
		break;
	case "stageResultsTab":
	case "competitorStageResultsPill":
		updateStageResultsTable('competitorStageResultsTable', competitorMatchAnalysisData);
		setStageResultsTableCellColors();
		break;
	case "compareToCompetitorStageResultsPill":
		updateStageResultsTable('compareToCompetitorStageResultsTable', compareToCompetitorMatchAnalysisData);
		setStageResultsTableCellColors();
		break;
	case "errorCostTab":
	case "competitorErrorCostPill":
		updateErrorCostAnalysisTable('competitorErrorCostAnalysisTable', competitorMatchAnalysisData);
		break;
	case "compareToCompetitorErrorCostPill":
		updateErrorCostAnalysisTable('compareToCompetitorErrorCostAnalysisTable', compareToCompetitorMatchAnalysisData);
		break;
	case "stageTimesTab":
		drawTimeByStageChart();
		break;
	case "stagePercentTab":
		drawPercentByStageChart();
		break;
	}
}

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
			chartsReadyEventHandler(data);
		}
		else {
			var interval = setInterval(function() {
				if (chartsReady == true) {
					clearInterval(interval);
					chartsReadyEventHandler(data);
				}
			}, 50);
		}
	}
}

function chartsReadyEventHandler(data) {
	setContentData(data);
	showContent();
	setWindowResizeEventListener();
}
function setWindowResizeEventListener() {
	if (document.addEventListener) {
	    window.addEventListener('resize', drawCharts);
	}
	else if (document.attachEvent) {
	    window.attachEvent('onresize', drawCharts);
	}
	else {
	    window.resize = drawCharts;
	}
}

function setContentData(data) {
	match = data.match;
	competitorMatchAnalysisData = data.competitorMatchAnalysisData;
	compareToCompetitorMatchAnalysisData = data.compareToCompetitorMatchAnalysisData;
	
	competitorMatchAnalysisData.empty = emptyResultData(competitorMatchAnalysisData);
	compareToCompetitorMatchAnalysisData.empty = emptyResultData(compareToCompetitorMatchAnalysisData);
	
	setCompetitorName(competitorMatchAnalysisData);
	setCompetitorName(compareToCompetitorMatchAnalysisData);
	
	setCompetitorHitSums(competitorMatchAnalysisData);
	setCompetitorHitSums(compareToCompetitorMatchAnalysisData);
	
	setCompetitorsByDivisionList();
	
	setPageGeneralInfoElements();
	
	setCompetitorSelectElements();
}
function emptyResultData(resultData) {
	var empty = true;
	$.each(resultData.stageResultDataLines, function(index, line) {
		if (line != null) empty = false;
	});
	return empty;
}

function setCompetitorHitSums(competitorData) {
	competitorData.aHitsSum = 0;
	competitorData.cHitsSum = 0;
	competitorData.dHitsSum = 0;
	competitorData.missSum = 0;
	competitorData.noshootHitsSum = 0;
	competitorData.proceduralPenaltiesSum = 0;
	$.each(competitorData.stageResultDataLines, function(key, line) {
		if (line == null) return true;
		competitorData.aHitsSum += line.scoreCard.aHits;
		competitorData.cHitsSum += line.scoreCard.cHits;
		competitorData.dHitsSum += line.scoreCard.dHits;
		competitorData.missSum += line.scoreCard.misses;
		competitorData.noshootHitsSum += line.scoreCard.noshootHits;
		competitorData.proceduralPenaltiesSum += line.scoreCard.proc;
	});
	
}
function setCompetitorsByDivisionList() {
	competitorsByDivision = {};
	$.each(match.match_cats, function(key, division) {
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
	
	// Must draw tables and charts after showing div to make sure
	// they are drawn correctly
	drawCharts();
	updateStageResultsTable('competitorStageResultsTable', competitorMatchAnalysisData);
	updateStageResultsTable('compareToCompetitorStageResultsTable', compareToCompetitorMatchAnalysisData);
	
	setStageResultsTableCellColors();
	
	updateHitsTable('competitorHitsTable', competitorMatchAnalysisData);
	updateHitsTable('compareToCompetitorHitsTable', compareToCompetitorMatchAnalysisData);

	updateErrorCostAnalysisTable('competitorErrorCostAnalysisTable', competitorMatchAnalysisData);
	updateErrorCostAnalysisTable('compareToCompetitorErrorCostAnalysisTable', compareToCompetitorMatchAnalysisData);

}

function drawCharts() {
	drawAccuracyPieChart('competitorAccuracyChart-large', competitorMatchAnalysisData);
	drawAccuracyPieChart('competitorAccuracyChart-small', competitorMatchAnalysisData);
	
	drawAccuracyPieChart('compareToCompetitorAccuracyChart-large', compareToCompetitorMatchAnalysisData);
	drawAccuracyPieChart('compareToCompetitorAccuracyChart-small', compareToCompetitorMatchAnalysisData);
	drawTimeByStageChart();
	drawPercentByStageChart();
	
}

function hideContent() {
	$("#loader").show();
	$("#contentDiv").hide();
	
}

function setPageGeneralInfoElements() {
	if (competitorMatchAnalysisData == null || compareToCompetitorMatchAnalysisData == null) return;
	$('#matchName').html(match.match_name);
	$('#pageHeadingCompetitorName, #analysisColumnCompetitorName').each(function() {
		$(this).html(competitorMatchAnalysisData.competitor.name);
	});
	$('#analysisColumnCompareToCompetitorName').html(compareToCompetitorMatchAnalysisData.competitor.name);
	$('.competitorPillName').each(function() {
		$(this).html(competitorMatchAnalysisData.competitor.sh_ln);
	});
	$('.compareToCompetitorPillName').each(function() {
		$(this).html(compareToCompetitorMatchAnalysisData.competitor.sh_ln);
	});
}

function setCompetitorSelectElements() {
	setCompetitorSelectOptions('competitor', competitorMatchAnalysisData.competitor);
	setCompetitorSelectOptions('compareToCompetitor', compareToCompetitorMatchAnalysisData.competitor);
}

function adjustColumns(tableId) {
	updateHitsTable('competitorHitsTable', competitorMatchAnalysisData);
	
	
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


function initializeHitsTable(tableClass) {
	$('.' + tableClass).DataTable( {
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
		scrollX: true
    } );
}
function updateHitsTable(tableClass, competitorData) {
	if (competitorData == null) return;
	var dataSet = [
		[competitorData.aHitsSum, 
			competitorData.cHitsSum,
			competitorData.dHitsSum,
			competitorData.missSum,
			competitorData.noshootHitsSum,
			competitorData.proceduralPenaltiesSum]
	];
	
	$.each($('table.' + tableClass), function(index, tableElement) {
		if (tableElement.id != "") {
			var table = $('#' + tableElement.id).DataTable();
			table.clear();
			table.rows.add(dataSet);
			table.draw();
		}
	});
}
function setStageResultsTableCellColors() {
	
	var largeCompetitorTable = $('#competitorStageResultsTable-large').DataTable();
	var smallCompetitorTable = $('#competitorStageResultsTable-small').DataTable();
	var largeCompareToCompetitorTable = $('#compareToCompetitorStageResultsTable-large').DataTable();
	var smallCompareToCompetitorTable = $('#compareToCompetitorStageResultsTable-small').DataTable();

	drawStageResultsTableColors(largeCompetitorTable, largeCompareToCompetitorTable);
	drawStageResultsTableColors(smallCompetitorTable, smallCompareToCompetitorTable);
}

function drawStageResultsTableColors(competitorTable, compareToCompetitorTable) {
	competitorTable.cells().eq(0).each(function (index) {
	    var node = competitorTable.cell(index).node();
	    var compareToNode = compareToCompetitorTable.cell(index).node();
	    var rowData = competitorTable.row(index.row).data();
	    var compareToCompetitorRowData = compareToCompetitorTable.row(index.row).data();
	    if (rowData == null || compareToCompetitorRowData == null) return;
	    var hitFactor = rowData[9];
	    var compareToCompetitorHitFactor = compareToCompetitorRowData[9];
	    
	    if (hitFactor == null || compareToCompetitorHitFactor == null || isNaN(hitFactor) || isNaN(compareToCompetitorHitFactor)) return;
	    
	    if (hitFactor > compareToCompetitorHitFactor) {
    		$(node).css('background-color', '#c2f0c2');
    		$(node).css('border', '1px solid #a6a6a6');
    		$(compareToNode).css('background-color', 'LightPink');
    		$(compareToNode).css('border', '1px solid #a6a6a6');
    	}
	    if (hitFactor < compareToCompetitorHitFactor) { 
    		$(node).css('background-color', 'LightPink');
    		$(node).css('border', '1px solid #a6a6a6');
    		$(compareToNode).css('background-color', '#c2f0c2');
    		$(compareToNode).css('border', '1px solid #a6a6a6');
    	}
	});
}
function initializeStageResultsTable(tableClass) {
	$('.' + tableClass).DataTable( {
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
            { title: "%", render: $.fn.dataTable.render.number( '.', ',', 2)}
        ],
		paging: false,
		searching: false,
		sort: false,
		info: false,
		scrollX: true
    } );	
}
function updateStageResultsTable(tableClass, competitorData) {
	if (competitorData == null || match.match_stages == null) return;
	var dataSet = [];
	$.each(match.match_stages, function(index, stage) {
		var line = competitorData.stageResultDataLines[stage.stage_uuid];
		var stageResultsPath = "${baseUrl}stageResults?matchId="
			+ match.match_id
			+ "&stageId="
			+ stage.stage_uuid
			+ "&division="
			+ competitorData.competitor.sh_dvp;
		var stageName = stage.stage_number + ": " + stage.stage_name;
		var stage = "<a href='" + stageResultsPath + "'>"+ stageName + "</a>";
		var data;
		if (line != null) {
			data = [
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
				line.stageScorePercentage
			];
		}
		else {
			data = [stage, 
				"-",
				"-",
				"-",
				"-",
				"-",
				"-",
				"-",
				"-",
				"-",
				"-",
				"-",
				"-"
				];
		}
		dataSet.push(data);
	});
	$.each($('table.' + tableClass), function(index, tableElement) {
		if (tableElement.id != "") {
			var table = $('#' + tableElement.id).DataTable();
			table.clear();
			table.rows.add(dataSet);
			table.draw();
		}
	});
}
function initializeErrorCostAnalysisTable(tableClass) {
	$('.' + tableClass).DataTable( {
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
		scrollX: true
    } );	
}
function updateErrorCostAnalysisTable(tableClass, competitorData) {
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
		var stageValue = "";
		var time = "-";
		if (line != null && line.scoreCard.hitFactor > 0) {
			aTime = line.aTime;
			errorCosts = "C=" + line.cCost.toString().replace(".", ",");
			errorCosts +=" / D=" + line.dCost.toString().replace(".", ",");
			errorCosts +=" / NS=" + line.proceduralPenaltyAndNoShootCost.toString().replace(".", ",");
			errorCosts +=" / Miss=" + line.missCost.toString().replace(".", ",");
			stageValue = stage.maxPoints + " (" + line.stageValuePercentage + "%)";
			time = line.scoreCard.time
		}
		else {
			aTime = "-";
			errorCosts = "-";
		}
		
		dataSet[index] = [
			"<a href='" + stageResultsPath + "'>"+ stageName + '</a>',
			stageValue,
			time,
			aTime,
			errorCosts
		]
	});
	$.each($('table.' + tableClass), function(index, tableElement) {
		if (tableElement.id != "") {
			var table = $('#' + tableElement.id).DataTable();
			table.clear();
			table.rows.add(dataSet);
			table.draw();
		}
	});
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
		width: 300,
		height: 300,
		chartArea: { width: '85%', height: '80%'}
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
	for (stageNumber = 1; stageNumber <= match.match_stages.length; stageNumber++) {
		rows[stageNumber - 1] = [stageNumber.toString(), competitorPercentages[stageNumber], 
			compareToCompetitorPercentages[stageNumber]];
	};
	data.addRows(rows);
	
	var options = {
		vAxis: {format: '#.##' },
		chartArea: { width: '85%', height: '80%'},
        legend: { position: 'top'}
	};
	var chart = new google.visualization.LineChart(document.getElementById('percentByStageChart-large'));
	chart.draw(data, options);
	chart = new google.visualization.LineChart(document.getElementById('percentByStageChart-small'));
	chart.draw(data, options);
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
		var competitorTime = null;
		var compareToCompetitorTime = null;
		
		if (competitorMatchAnalysisData.empty == false && competitorMatchAnalysisData.stageResultDataLines[stage.stage_uuid] != null) competitorTime = competitorMatchAnalysisData.stageResultDataLines[stage.stage_uuid].scoreCard.time;
		if (compareToCompetitorMatchAnalysisData.empty == false && compareToCompetitorMatchAnalysisData.stageResultDataLines[stage.stage_uuid] != null) compareToCompetitorTime = compareToCompetitorMatchAnalysisData.stageResultDataLines[stage.stage_uuid].scoreCard.time;
		rows[index] = [stage.stage_number.toString(), competitorTime,
			compareToCompetitorTime];
	});

	data.addRows(rows);
	
	var options = {
		chartArea: { width: '85%', height: '80%'},
        legend: { position: 'top'}
		
	};
	var chart = new google.visualization.LineChart(document.getElementById('timeByStageChart-large'));
	chart.draw(data, options);
	chart = new google.visualization.LineChart(document.getElementById('timeByStageChart-small'));
	chart.draw(data, options);
	
}
