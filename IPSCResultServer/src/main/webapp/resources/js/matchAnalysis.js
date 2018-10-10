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
		if (division != 'Combined' && (Object.keys(competitorsByDivision).indexOf(division) == -1)) competitorsByDivision[division] = [];
	});
	$.each(match.match_shooters, function(key, competitor) {
		competitorsByDivision[competitor.sh_dvp].push(competitor);
		if (Object.keys(competitorsByDivision).indexOf('Combined') > -1) competitorsByDivision['Combined'].push(competitor);
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
	if (competitorData == null || competitorData.scoreCards == null) return;
	
	var dataSet = [
			[getScoreCardsFieldSum(competitorData.scoreCards, "aHits"),
			getScoreCardsFieldSum(competitorData.scoreCards, "cHits"),
			getScoreCardsFieldSum(competitorData.scoreCards, "dHits"),
			getScoreCardsFieldSum(competitorData.scoreCards, "misses"),
			getScoreCardsFieldSum(competitorData.scoreCards, "noshootHits"),
			getScoreCardsFieldSum(competitorData.scoreCards, "proc")]
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
		var card = getScoreCardByStageId(competitorData.scoreCards, stage.id)
		var data;
		if (card != null) {
			data = [
				getStageNameCellText(stage, competitorData.competitor.sh_dvp),
				card.aHits,
				card.cHits,
				card.dHits,
				card.misses,
				card.noshootHits,
				card.proc,
				card.rawpts,
				card.time,
				card.hitFactor,
				card.stagePoints,
				card.stageRank,
				card.scorePercentage
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
		var line = getErrorCostTableLineByStageId(competitorData.errorCostTableLines, stage.id);
		var card = getScoreCardByStageId(competitorData.scoreCards, stage.id);
		var aTime;
		var errorCosts;
		var stageValue = "-";
		var time = "-";
		if (line != null && card != null && card.hitFactor > 0) {
			aTime = line.aTime;
			errorCosts = "C=" + line.cCost.toString().replace(".", ",");
			errorCosts +=" / D=" + line.dCost.toString().replace(".", ",");
			errorCosts +=" / NS=" + line.proceduralPenaltyAndNoShootCost.toString().replace(".", ",");
			errorCosts +=" / Miss=" + line.missCost.toString().replace(".", ",");
			stageValue = stage.maxPoints + " (" + line.stageValuePercentage + "%)";
			time = card.time;
		}
		else {
			aTime = "-";
			errorCosts = "-";
		}
		
		dataSet[index] = [
			getStageNameCellText(stage, competitorData.competitor.sh_dvp),
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

function getStageNameCellText(stage, division) {
	var result;
	var stageResultsPath = "${baseUrl}stageResults?matchId="
		+ match.match_id
		+ "&stageId="
		+ stage.stage_uuid
		+ "&division="
		+ division;
	var stageName = stage.stage_number + ": " 
		+ stage.stage_name;
	result = "<a href='" + stageResultsPath + "'>"+ stageName + '</a>';
	
	return result;
}
function drawAccuracyPieChart(chartId, competitorData) {
	if (competitorData == null || competitorData.scoreCards == null) return;
	var data = google.visualization.arrayToDataTable([
		['Hit zone', 'Hits'],
		['A', getScoreCardsFieldSum(competitorData.scoreCards, "aHits")],
        ['C', getScoreCardsFieldSum(competitorData.scoreCards, "cHits")],
        ['D', getScoreCardsFieldSum(competitorData.scoreCards, "dHits")],
        ['Miss', getScoreCardsFieldSum(competitorData.scoreCards, "misses")],
        ['NS', getScoreCardsFieldSum(competitorData.scoreCards, "noshootHits")]
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
	if (match == null) return;
	
	var competitorPercentages;
	var compareToCompetitorPercentages;
	
	var combinedResults = false;
	
	// Show division stage percentages if same division for both competitors
	// and Combined results stage percentages if different divisions.
	if (competitorMatchAnalysisData.competitor.sh_dvp == compareToCompetitorMatchAnalysisData.competitor.sh_dvp) {
		$('#percentByStageDivision').html("");
	}
	else {
		combinedResults = true;
		$('#percentByStageDivision').html("(Combined results)");
	}
	
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Stage');
	data.addColumn('number', competitorMatchAnalysisData.competitor.name);
	data.addColumn('number', compareToCompetitorMatchAnalysisData.competitor.name);
	
	var rows = [];
	
	$.each(match.match_stages, function(index, stage) {
		var competitorCard = getScoreCardByStageId(competitorMatchAnalysisData.scoreCards, stage.id);
		var compareToCompetitorCard = getScoreCardByStageId(compareToCompetitorMatchAnalysisData.scoreCards, stage.id);
		
		var percentageField = "scorePercentage";
		if (combinedResults == true) percentageField = "combinedDivisionScorePercentage";
		
		var competitorPercentage = null;
		var compareToCompetitorPercentage = null;
		
		if (competitorCard != null) competitorPercentage = competitorCard[percentageField];
		if (compareToCompetitorCard != null) compareToCompetitorPercentage = compareToCompetitorCard[percentageField];
		
		rows[index] = [stage.stage_number.toString(), competitorPercentage, compareToCompetitorPercentage];
	});

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
	if (competitorMatchAnalysisData.scoreCards == null 
			|| compareToCompetitorMatchAnalysisData.scoreCards == null
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
		
		var competitorCard = getScoreCardByStageId(competitorMatchAnalysisData.scoreCards, stage.id);
		var compareToCompetitorCard = getScoreCardByStageId(compareToCompetitorMatchAnalysisData.scoreCards, stage.id);
		
		if (competitorCard != null) competitorTime = competitorCard.time;
		if (compareToCompetitorCard != null) compareToCompetitorTime = compareToCompetitorCard.time;
		
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
function getScoreCardByStageId(cards, stageId) {
	var resultCard;
	$.each(cards, function(index, card) {
		if (card.stageId == stageId) resultCard = card;
	});
	return resultCard;
}
function getErrorCostTableLineByStageId(lines, stageId) {
	var resultLine;
	$.each(lines, function(index, line) {
		if (line.stageId == stageId) resultLine = line;
	});
	return resultLine;
}
function getScoreCardsFieldSum(cards, field) {
	var sum = 0;
	$.each(cards, function(index, card) {
		sum += card[field];
	});
	return sum;
}

