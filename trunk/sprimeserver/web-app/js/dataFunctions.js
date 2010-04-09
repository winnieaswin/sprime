/* 
 * This file has the necessary javascript functions to run the data view of the
 * web application.
 */

// Create the sprime namespace.
var sprime = {};

/*
 * Create the sprime configuration.
 * 
 * Here will be a list of configuration options.
 */
sprime.config = {

    // The URL for retrieving the data for the chart.
    retrieveDataUrl : '',

    // The URL for sending a request to the server to toggle the power on or off.
    togglePowerUrl : '',

    // The id of the message box to display messages as results from action.
    messageBoxId : '',

    // The chart configuration.
    chartConfig : {
        width: 700,
        height: 300,
        legend: 'none',
        enableTooltip: true,
        titleX: 'Day',
        titleY: 'Power Usage (Watts)',
        colors: ['green'],
        max: 200,
        min: 000
    },

    // The current chart type to retrieve.
    currentChartType : '',

    // The current message if we have one.
    currentMessage : '',

    currentMessageStatus : true
};

/**
 * This is the global variable for the chart data.
 */
sprime.chart = null;

/**
 * The timer for running the current usage chart.
 */
sprime.timer = null;


sprime.init = function() {

    // If we have a message display it.
    if (sprime.config.currentMessage != '') {
        sprime.displayMessage({
            message: sprime.config.currentMessage,
            success: sprime.config.currentMessageStatus
        });
    }

    // Iterate through all of the side necessary side links and convert them
    // to AJAX functions instead of regular requests.
    $('#dataViews a').each(function(index) {
       $(this).removeAttr('href');
       $(this).attr('href', '#');
       $(this).click(function() {
           sprime.updateChart($(this).attr('id'));
       });
    });

    $('#controlFunctions a').each(function(index) {
       $(this).removeAttr('href');
       $(this).attr('href', '#');
       $(this).click(function() {
           sprime.togglePower($(this).attr('id'));
       })
    });

    // When message is clicked allow it to be closed.
    var messageBox = $('#statusMessage');
    messageBox.click(function() {
       messageBox.slideUp();
    });

}

/**
 * This function is run during the first time the page is loaded.
 */
sprime.drawChart = function() {

    sprime.chart = new google.visualization.LineChart(document.getElementById("chart"));
    sprime.updateChart(sprime.config.currentChartType);
}

/**
 * This function is used to update the chart with new data from given param.
 * 
 * This param is the type of time view to view the data. Examples of paramIds
 * include:
 *   * monthly
 *   * hourly
 *   * yearly
 *   *...
 *   
 * @param paramId the id of the type of time view to display the graph in.
 */
sprime.updateChart = function(paramId) {
    window.clearTimeout();

    $.getJSON(sprime.config.retrieveDataUrl + '/' + paramId,
        function(data) {

          var chartData = new google.visualization.DataTable(data, 0.6);
          sprime.config.chartConfig.title = paramId + " usage";
          sprime.chart.draw(chartData, sprime.config.chartConfig);

          if (paramId == 'current' && sprime.timer == null && data.rows.length > 0) {
              sprime.timer = window.setInterval(sprime.updateChart, 1000, paramId);
	  } else if (paramId != 'current') {
	      window.clearTimeout(sprime.timer);
              sprime.timer = null;
	  }

	  if (data.rows.length == 0) {
              window.clearTimeout(sprime.timer);
          }
     });
}

/**
 * This function is used to send a request to the server to turn off the power
 * of the device. This method is also responsible for displaying the message.
 *
 * @param on Whether to turn the power on or off. Use 'on' or 'off'
 */
sprime.togglePower = function(on) {

    $.getJSON(sprime.config.togglePowerUrl + '/' + on,
        function(data) {
            
            sprime.displayMessage(data);
    });
}

/**
 * Displays the message with the JSON format: {message : '', success : true}.
 */
sprime.displayMessage = function(json) {
    var messageBox = $('#statusMessage');

    messageBox.toggleClass('success', json.success);
    messageBox.toggleClass('error', !json.success);

    messageBox.text(json.message);

    messageBox.slideDown();
}
