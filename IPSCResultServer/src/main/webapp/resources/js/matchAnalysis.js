//JavaScript for loading and drawing Google Charts in matchAnalysisPage.jsp

$(document).ready(function() {
	hideContent();
	initializeHitsTable('competitorHitsTable');
	initializeHitsTable('compareToCompetitorHitsTable');
	
	initializeStageResultsTable('competitorStageResultsTable');
	initializeStageResultsTable('compareToCompetitorStageResultsTable');
	
	initializeErrorCostAnalysisTable('competitorErrorCostAnalysisTable');
	initializeErrorCostAnalysisTable('compareToCompetitorErrorCostAnalysisTable');
	
});
google.charts.load('current', {'packages':['corechart', 'line']});
google.charts.setOnLoadCallback(function() {
	
	getChartData('${matchId}', '${competitorId}', '${compareToCompetitorId}');
});

var match;
 
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
		
		match = data.match;
		competitorData = setCompetitorData(data.competitorData);
		compareToCompetitorData = setCompetitorData(data.compareToCompetitorData);
		setPageGeneralInfoElements(competitorData.resultData.competitor, compareToCompetitorData.resultData.competitor);
		
		drawAccuracyPieChart('competitorAccuracyChart', competitorData);
		drawAccuracyPieChart('compareToCompetitorAccuracyChart', compareToCompetitorData);
		
		drawPercentByStageChart('percentByStageChart', competitorData, compareToCompetitorData);
		
		drawTimeByStageChart('timeByStageChart', competitorData, compareToCompetitorData);
		
				
		updateErrorCostAnalysisTable('competitorErrorCostAnalysisTable', competitorData);
		updateErrorCostAnalysisTable('compareToCompetitorErrorCostAnalysisTable', compareToCompetitorData);
		
		updateStageResultsTable('competitorStageResultsTable', competitorData);
		updateStageResultsTable('compareToCompetitorStageResultsTable', compareToCompetitorData);
		
		updateHitsTable('competitorHitsTable', competitorData);
		updateHitsTable('compareToCompetitorHitsTable', compareToCompetitorData);
		
		showContent();
	}
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
	if ($('#competitor option').length == 0) {
		setCompetitorSelectElements('competitor', match.match_shooters, competitor);
		setCompetitorSelectElements('compareToCompetitor', match.match_shooters, compareToCompetitor);
	}
}

function setCompetitorSelectElements(selectId, competitorList, selectedCompetitor) {
	if (competitorList == null) return;
	var optionsAsString = "";
	for(var i = 0; i < competitorList.length; i++) {
		var competitor = competitorList[i];
		if (selectedCompetitor.practiScoreId == competitor.practiScoreId) optionsAsString += "selected";
		$('#' + selectId)
        .append($("<option></option>")
                   .attr("value", competitor.sh_uid)
                   .text(competitor.sh_fn + " " + competitor.sh_ln)); 
		$('#' + selectId).val(selectedCompetitor.sh_uid);
	};
}

function submitCompetitorsChange() {
	hideContent();
	getChartData('${matchId}', $('#competitor').val(), $('#compareToCompetitor').val()); 
}

function setCompetitorData(data) {
	if (data.competitorResultData == null) return;
	var comp = {};
	comp.resultData = data.competitorResultData;
	comp.stageResultDataLines = data.stageResultDataLines;
	comp.errorCostTableLines = data.errorCostTableLines;

	comp.name = comp.resultData.competitor.sh_fn + " " 
	+ comp.resultData.competitor.sh_ln
	+ " (" 
	+ comp.resultData.competitor.sh_dvp 
	+ "/"
	+ comp.resultData.competitor.powerFactor
	+ ")";
	
	return comp;
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
	if (competitorData == null || competitorData.resultData == null) return;
	var dataSet = [
		[competitorData.resultData.aHitsSum, 
			competitorData.resultData.cHitsSum,
			competitorData.resultData.dHitsSum,
			competitorData.resultData.missSum,
			competitorData.resultData.noshootHitsSum,
			competitorData.resultData.proceduralPenaltiesSum]
	];
	var table = $('#' + tableId).DataTable();
	table.clear();
	table.rows.add(dataSet);
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
        rowCallback: function(row, data, index){
        	if(data[2]> 7){
                $(row).find('td:eq(2)').css('background-color', 'PaleGreen');
            }
        	else {
        		$(row).find('td:eq(2)').css('background-color', 'LightPink');
        	}
          },
		paging: false,
		searching: false,
		sort: false,
		info: false, 
    } );	
}
function updateStageResultsTable(tableId, competitorData) {
	if (competitorData == null || competitorData.resultData == null || match.match_stages == null) return;
	var dataSet = [];
	$.each(match.match_stages, function(index, stage) {
		var line = competitorData.stageResultDataLines[stage.stage_uuid];
		if (line == null) return;
		var stageResultsPath = "${baseUrl}stageResults?matchId="
			+ match.match_id
			+ "&stageId="
			+ stage.stage_uuid
			+ "&division="
			+ competitorData.resultData.competitor.sh_dvp;
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
	if (competitorData == null || competitorData.resultData == null || match.match_stages == null) return;
	var dataSet = [];
	$.each(match.match_stages, function(index, stage) {
		var line = competitorData.errorCostTableLines[stage.stage_uuid];
		var stageResultsPath = "${baseUrl}stageResults?matchId="
			+ match.match_id
			+ "&stageId="
			+ stage.stage_uuid
			+ "&division="
			+ competitorData.resultData.competitor.sh_dvp;
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
	if (competitorData == null || competitorData.resultData == null) return;
	var data = google.visualization.arrayToDataTable([
		['Hit zone', 'Hits'],
        ['A', competitorData.resultData.aHitsSum],
        ['C', competitorData.resultData.cHitsSum],
        ['D', competitorData.resultData.dHitsSum],
        ['Miss', competitorData.resultData.missSum],
        ['NS', competitorData.resultData.noshootHitsSum]
	]);
	var options = {
		width: 600,
		height: 400,
		};
	var chart = new google.visualization.PieChart(document.getElementById(chartId));
	chart.draw(data, options);
}


function drawPercentByStageChart(chartId, competitor1, competitor2) {
	if (competitor1 == null || competitor2 == null || match == null || competitorData.resultData.stagePercentages == null) return;
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Stage');
	data.addColumn('number', competitor1.name);
	data.addColumn('number', competitor2.name);
	
	var rows = [];
	$.each(competitorData.resultData.stagePercentages, function(stageNumber) {
		rows[stageNumber - 1] = [stageNumber.toString(), competitor1.resultData.stagePercentages[stageNumber], 
			competitor2.resultData.stagePercentages[stageNumber]];
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
			competitor1.resultData.scoreCards[stage.id].time,
			competitor2.resultData.scoreCards[stage.id].time ];
	});

	data.addRows(rows);
	
	var options = {
		width: 600,
		height: 400
	};
	var chart = new google.charts.Line(document.getElementById(chartId));
	chart.draw(data, google.charts.Line.convertOptions(options));
	
}
