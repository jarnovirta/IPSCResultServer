<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
      google.charts.load('current', {'packages':['corechart', 'line']});
      google.charts.setOnLoadCallback(drawCharts);

	function drawCharts() {
		drawAccuracyPieChart();
		drawPercentByStageChart();
		drawTimeByStageChart();
	}
	function drawAccuracyPieChart() {
		var data = google.visualization.arrayToDataTable([
			['Hit zone', 'Hits'],
			['A',     11],
			['C',      5],
			['D',  2],
			['Miss', 2],
		]);
		var options = {
			title: 'Accuracy'
			};
		var chart = new google.visualization.PieChart(document.getElementById('competitorAccuracyChart'));
		chart.draw(data, options);
	}
	function drawPercentByStageChart() {
		
		var data = new google.visualization.DataTable();
		data.addColumn('string', 'Stage');
		data.addColumn('number', 'Jarno Virta (Standard/Major)');
		data.addColumn('number', 'Rob Leatham (Standard/Major)');
		
		data.addRows([
			['1',  100, 50.8],
			['2',  85.9, 49.5],
			['3',  95.4, 37],
			['4',  79.7, 48.8],
			['5',  100, 60.8],
			['6',  83.9, 55.5],
			['7',  85.4, 47],
			['8',  82.7, 38.8],
			['9',  95, 30.8],
			['10',  81.9, 49.5],
			['11',  93.4, 57],
			['12',  80.7, 38.8]
			
		]);
		var options = {
			chart: {
			title: 'Percent by Stage',
			},
			width: 900,
			height: 500,

		};
		var chart = new google.charts.Line(document.getElementById('percentByStageChart'));
		chart.draw(data, google.charts.Line.convertOptions(options));
	}
	function drawTimeByStageChart() {
		var data = new google.visualization.DataTable();
		data.addColumn('string', 'Stage');
		data.addColumn('number', 'Jarno Virta (Standard/Major)');
		data.addColumn('number', 'Rob Leatham (Standard/Major)');
		
		data.addRows([
			['1',  8.55, 12.7],
			['2',  11.35, 15.8],
			['3',  7.35, 10.7],
			['4',  13.55, 11.2],
			['5',  12.4, 14.6],
			['6',  18.55, 22.7],
			['7',  28.55, 32.7],
			['8',  15.45, 22.7],
			['9',  11.55, 15.7],
			['10',  9.55, 17.7],
			['11',  14.55, 12.7],
			['12',  27.55, 36.7]
			
		]);
		var options = {
			chart: {
			title: 'Time by Stage',
			},
			width: 900,
			height: 500

		};
		var chart = new google.charts.Line(document.getElementById('timeByStageChart'));
		chart.draw(data, google.charts.Line.convertOptions(options));
	}

</script>