<html>
  <head>
    <title>VIEW DATA (${type})</title>

    <script type="text/javascript" src="${resource(dir:'js', file:'dataFunctions.js')}"></script>

    <!--Load the AJAX API-->
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>

    <script type="text/javascript">

      // Set up the configuration url to retrieve data.
      sprime.config.retrieveDataUrl = "<g:createLink controller='powerData' action='retrieveData'/>";
      sprime.config.togglePowerUrl = "<g:createLink controller='powerData' action='controlPower' />";
      sprime.config.messageBoxId = 'statusMessage';
      sprime.config.currentChartType = '${type}';
      sprime.config.currentMessage = '${flash.message}';
      sprime.config.currentMessageStatus = '${flash.success}';

      // Create our google graph.
      google.load("visualization", "1", {packages:["linechart"]});
      google.setOnLoadCallback(sprime.drawChart);

      $(document).ready(sprime.init);

    </script>


    <meta name="layout" content="main" />
  </head>
  <body>

        <g:displaySideBar />

        <div class="span-18 last">

          <div id="statusMessage" style="display: none">Messages Go Here</div>

          <h3 class="loud"><span style="text-transform:capitalize">${type}</span> Power Usage</h3>

          <p>Here is a break down of your ${type} power usage:</p>

          
          <div id="chart" >Render Chart Here</div>

        </div>

  </body>
</html>