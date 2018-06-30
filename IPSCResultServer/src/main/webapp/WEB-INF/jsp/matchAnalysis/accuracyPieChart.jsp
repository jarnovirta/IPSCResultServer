<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {

      var data = google.visualization.arrayToDataTable([
        ['Hit zone', 'Hits'],
        ['A',     11],
        ['C',      5],
        ['D',  2],
        ['Miss', 2],
        ['NS',    1]
      ]);

      var options = {
        title: 'Accuracy'
      };

      var chart = new google.visualization.PieChart(document.getElementById('competitorAccuracyChart'));

      chart.draw(data, options);
    }
</script>

<div id="competitorAccuracyChart" style="width: 700px; height: 389px; display: block; margin: 0 auto"></div>  
	    