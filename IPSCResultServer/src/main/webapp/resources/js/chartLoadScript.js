//JavaScript for loading and drawing Google Charts in matchAnalysisPage.jsp


google.charts.load('current', {'packages':['corechart', 'line']});
google.charts.setOnLoadCallback(function() {
	getChartData();
});

var competitorResultData;
var competitorName;
var scoreCards = [];

function getChartData() {
	var path = window.location.pathname.substr(0, window.location.pathname.lastIndexOf('/'));
	path += '/matchAnalysis/data?matchId=${matchId}&competitorId=${competitorId}';
	
	$.getJSON(path, handleAjaxResponse);
	
}
function handleAjaxResponse(data, status) {
	if (status != 'success') {
		alert("Problem loading data: " + status);
	}
	else {
		competitorResultData = data;
		$.each(data.scoreCards, function (stageId) {
			scoreCards[data.scoreCards[stageId].stage.stage_number - 1] = data.scoreCards[stageId];
		});
		competitorName = competitorResultData.competitor.sh_fn + " " 
		+ competitorResultData.competitor.sh_ln
		+ " (" 
		+ competitorResultData.competitor.sh_dvp 
		+ "/"
		+ competitorResultData.competitor.powerFactor
		+ ")";
		drawAccuracyPieChart();
		drawPercentByStageChart();
		drawTimeByStageChart();
	}
}

function drawAccuracyPieChart() {
	var data = google.visualization.arrayToDataTable([
		['Hit zone', 'Hits'],
        ['A', competitorResultData.aHitsSum],
        ['C', competitorResultData.cHitsSum],
        ['D', competitorResultData.dHitsSum],
        ['Miss', competitorResultData.missSum],
        ['NS', competitorResultData.noshootHitsSum]
	]);
	var options = {
		title: 'Accuracy',
		width: 600,
		height: 400,
		};
	var chart = new google.visualization.PieChart(document.getElementById('competitorAccuracyChart'));
	chart.draw(data, options);
}
function drawPercentByStageChart() {
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Stage');
	data.addColumn('number', competitorName);
	
	var rows = [];
	$.each(competitorResultData.stagePercentages, function(stageNumber) {
		rows[stageNumber - 1] = [stageNumber.toString(), competitorResultData.stagePercentages[stageNumber]];
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
	var chart = new google.charts.Line(document.getElementById('percentByStageChart'));
	chart.draw(data, google.charts.Line.convertOptions(options));
}
function drawTimeByStageChart() {
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Stage');
	

	
	data.addColumn('number', competitorName);
	var rows = [];
	
	var index = 0;
	$.each(scoreCards, function(key, scoreCard) {
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
	var chart = new google.charts.Line(document.getElementById('timeByStageChart'));
	chart.draw(data, google.charts.Line.convertOptions(options));
}
