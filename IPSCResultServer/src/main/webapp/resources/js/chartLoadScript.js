//JavaScript for loading and drawing Google Charts in matchAnalysisPage.jsp


google.charts.load('current', {'packages':['corechart', 'line']});
google.charts.setOnLoadCallback(function() {
	getChartData();
});

var competitorName;
var competitorResultData;
var scoreCards = [];
var errorCostTableLines;
var stageResultDataLines; 

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
		competitorResultData = data.competitorResultData;
		stageResultDataLines = data.stageResultDataLines;
		
		$.each(competitorResultData.scoreCards, function (stageId) {
			
			scoreCards[competitorResultData.scoreCards[stageId].stage.stage_number - 1] = competitorResultData.scoreCards[stageId];
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
		drawStageResultsTable();
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
function drawStageResultsTable() {
	var dataSet = [];
	$.each(stageResultDataLines, function(index) {
		var line = stageResultDataLines[index];
		var stageResultsPath = "${baseUrl}stageResults?matchId="
			+ competitorResultData.match.match_id
			+ "&stageId="
			+ line.scoreCard.stage.stage_uuid
			+ "&division="
			+ competitorResultData.competitor.sh_dvp;
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
	
	$('#stageResultsTable').DataTable( {
        data: dataSet,
        columns: [
        	{ title: "Stage" },
            { title: "A" },
            { title: "C" },
            { title: "D" },
            { title: "M" },
            { title: "NS" },
            { title: "Proc." },
            { title: "Points" },
            { title: "Time" },
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
