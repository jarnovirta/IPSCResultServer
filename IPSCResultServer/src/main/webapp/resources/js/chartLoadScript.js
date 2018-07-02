//JavaScript for loading and drawing Google Charts in matchAnalysisPage.jsp

$(document).ready(function() {
	initializeHitsTable('hitsTable');
	initializeStageResultsTable('stageResultsTable');
	initializeErrorCostAnalysisTable('errorCostAnalysisTable');
});
google.charts.load('current', {'packages':['corechart', 'line']});
google.charts.setOnLoadCallback(function() {
	
	getChartData('${matchId}', '${competitorId}', '${compareToCompetitorId}');
});

var hitsTable;

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
		competitorData = setCompetitorData(data[0]);
		compareToCompetitorData = setCompetitorData(data[1]);
		setPageHeadingCompetitorName(competitorData.resultData.competitor);
		if ($('#competitor option').length == 0) {
			setCompetitorSelectElements('competitor', competitorData.resultData.match.match_shooters, competitorData.resultData.competitor);
			setCompetitorSelectElements('compareToCompetitor', compareToCompetitorData.resultData.match.match_shooters, compareToCompetitorData.resultData.competitor);
		}
		
		drawAccuracyPieChart('competitorAccuracyChart', competitorData);
		drawPercentByStageChart('percentByStageChart', competitorData);
		drawTimeByStageChart('timeByStageChart', competitorData);
		
		updateErrorCostAnalysisTable('errorCostAnalysisTable', competitorData);
		updateStageResultsTable('stageResultsTable', competitorData);
		updateHitsTable('hitsTable', competitorData);
	}
}
function setPageHeadingCompetitorName(competitor) {
	$('#pageHeadingCompetitorName').html(competitor.sh_fn + " " + competitor.sh_ln);
}
function submitCompetitorsChange() {
	getChartData('${matchId}', $('#competitor').val(), $('#compareToCompetitor').val()); 
}

function setCompetitorData(data) {
	var comp = {};
	comp.resultData = data.competitorResultData;
	comp.stageResultDataLines = data.stageResultDataLines;
	comp.errorCostTableLines = data.errorCostTableLines;
	comp.scoreCards = [];
	
	$.each(comp.resultData.scoreCards, function (stageId) {
		comp.scoreCards[comp.resultData.scoreCards[stageId].stage.stage_number - 1] = comp.resultData.scoreCards[stageId];
	});
	comp.name = comp.resultData.competitor.sh_fn + " " 
	+ comp.resultData.competitor.sh_ln
	+ " (" 
	+ comp.resultData.competitor.sh_dvp 
	+ "/"
	+ comp.resultData.competitor.powerFactor
	+ ")";
	return comp;
}
function setCompetitorSelectElements(selectId, competitorList, selectedCompetitor) {
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
	var dataSet = [];
	$.each(competitorData.stageResultDataLines, function(index) {
		var line = competitorData.stageResultDataLines[index];
		var stageResultsPath = "${baseUrl}stageResults?matchId="
			+ competitorData.resultData.match.match_id
			+ "&stageId="
			+ line.scoreCard.stage.stage_uuid
			+ "&division="
			+ competitorData.resultData.competitor.sh_dvp;
		var stageName = line.scoreCard.stage.stage_number + ": " 
			+ line.scoreCard.stage.stage_name;
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
	var dataSet = [];
	$.each(competitorData.errorCostTableLines, function(index) {
		var line = competitorData.errorCostTableLines[index];
		var stageResultsPath = "${baseUrl}stageResults?matchId="
			+ competitorData.resultData.match.match_id
			+ "&stageId="
			+ line.scoreCard.stage.stage_uuid
			+ "&division="
			+ line.scoreCard.competitor.sh_dvp;
		var stageName = line.scoreCard.stage.stage_number + ": " 
			+ line.scoreCard.stage.stage_name;
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
			line.scoreCard.stage.maxPoints + " (" + line.stageValuePercentage + "%)",
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
	
	var data = google.visualization.arrayToDataTable([
		['Hit zone', 'Hits'],
        ['A', competitorData.resultData.aHitsSum],
        ['C', competitorData.resultData.cHitsSum],
        ['D', competitorData.resultData.dHitsSum],
        ['Miss', competitorData.resultData.missSum],
        ['NS', competitorData.resultData.noshootHitsSum]
	]);
	var options = {
		title: 'Accuracy',
		width: 600,
		height: 400,
		};
	var chart = new google.visualization.PieChart(document.getElementById(chartId));
	chart.draw(data, options);
}


function drawPercentByStageChart(chartId, competitorData) {
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Stage');
	data.addColumn('number', competitorData.name);
	
	var rows = [];
	$.each(competitorData.resultData.stagePercentages, function(stageNumber) {
		rows[stageNumber - 1] = [stageNumber.toString(), competitorData.resultData.stagePercentages[stageNumber]];
	});
	
	data.addRows(rows);
	
	var options = {
		chart: {
		title: 'Percent by Stage',
		},
		width: 600,
		height: 400,
		vAxis: {format: '#.##' }

	};
	var chart = new google.charts.Line(document.getElementById(chartId));
	chart.draw(data, google.charts.Line.convertOptions(options));
}
function drawTimeByStageChart(chartId, competitorData) {
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Stage');
	data.addColumn('number', competitorData.name);
	var rows = [];
	
	var index = 0;
	$.each(competitorData.scoreCards, function(key, scoreCard) {
		rows[index++] = [scoreCard.stage.stage_number.toString(), scoreCard.time];
	});
	data.addRows(rows);
	
	var options = {
		chart: {
		title: 'Time by Stage',
		},
		width: 600,
		height: 400
	};
	var chart = new google.charts.Line(document.getElementById(chartId));
	chart.draw(data, google.charts.Line.convertOptions(options));
}
