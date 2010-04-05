/*
 * This file has the necessary javascript functions to run the device manager page.
 */

// Create the sprime namespace.
var sprime = {};

/*
 * Create the sprime configuration.
 *
 * Here will be a list of configuration options.
 */
sprime.config = {

}

sprime.initDeviceManager = function() {

    // When message is clicked allow it to be closed.
    var messageBox = $('#statusMessage');
    messageBox.click(function() {
       messageBox.slideUp();
    });

    sprime.startDeviceManager();
}

sprime.startDeviceManager = function() {

    // display the message if we are already connected.
    if (sprime.config.isConnected) {
        $('#searchLink').hide();

        var deviceTable = $('#deviceTable');
        var alreadyConnectedRow = sprime.createAlreadyConnectedRow(sprime.config.deviceName);
        deviceTable.append(alreadyConnectedRow);

    } else {

        sprime.setSearchLinkToSearch();
        $('#searchLink').show();
    }
}

sprime.searchForBluetoothDevices = function() {

    var deviceTable = $('#deviceTable');
    $('#deviceTable .deviceRow').each(function() {
        $(this).remove();
    });
    deviceTable.append(sprime.createLoadingRow('SEARCHING'));

    $("#searchLink").unbind('click');
    $("#searchLink").addClass('disabled');

    $.getJSON(sprime.config.searchForBluetoothDeviceUrl,
        function(data) {

            // Remove loading row
            $('#loadingRow').remove();

            if (data.error) {
                sprime.displayError(data);
                sprime.setSearchLinkToSearch();
            } else {
                var deviceArray = data.devices;

                // Set search link to refresh.
                sprime.setSearchLinkToRefresh();
                var devicesTable = $("#deviceTable");


                $.each(deviceArray, function(index, value) {
                   $(devicesTable).append(sprime.createRowForDevice(value));
                });

                if (deviceArray.length == 0) {
                    $(devicesTable).append(sprime.createEmptyRow());
                }
            }

            $("#searchLink").removeClass('disabled');
            $("#searchLink").click(sprime.searchForBluetoothDevices);
    });
}

sprime.disconnect = function() {
    $('#alreadyConnected').remove();
    $('#deviceTable').append(sprime.createLoadingRow('DISCONNECTING'));

    $.getJSON(sprime.config.disconnectUrl,
        function(data) {

            // Remove loading row
            $('#loadingRow').remove();

            if (data.error) {
                prime.displayError(data);
            } else {
                sprime.config.isConnected = false;
                sprime.config.deviceName = '';
            }

            sprime.startDeviceManager();
    });
}

sprime.connectToDevice = function() {
    var deviceName = $(this).attr('id');

    var deviceTable = $('#deviceTable');
    $('#deviceTable .deviceRow').each(function() {
        $(this).remove();
    });
    deviceTable.append(sprime.createLoadingRow('CONNECTING'));

    $("#searchLink").unbind('click');
    $("#searchLink").addClass('disabled');

    $.getJSON(sprime.config.connectToDeviceUrl + "?deviceName=" + deviceName,
        function(data) {

            // Remove loading row
            $('#loadingRow').remove();

            $("#searchLink").removeClass('disabled');

            if (data.error) {
                sprime.displayError(data);
                sprime.setSearchLinkToSearch();

            } else {
                sprime.config.deviceName = data.deviceName;
                sprime.config.isConnected = true;

                // Restart device manager.
                sprime.startDeviceManager();
            }

    });

}

sprime.setSearchLinkToSearch = function() {
    var searchLink = $('#searchLink');
    searchLink.text('Search');
    searchLink.click(sprime.searchForBluetoothDevices);
}

sprime.setSearchLinkToRefresh = function() {
    var searchLink = $('#searchLink');
    searchLink.text('Refresh');
}

sprime.createRowForDevice = function(deviceName) {

    // Creates a row for connecting to a device.
    var deviceRow = document.createElement('tr');
    $(deviceRow).addClass('deviceRow');
    var deviceNameCell = document.createElement('td');
    var deviceConnect = document.createElement('td');

    var deviceConnectLink = document.createElement('a');
    $(deviceConnectLink).removeAttr('href');
    $(deviceConnectLink).attr('href', '#');
    $(deviceConnectLink).attr('id', deviceName);
    $(deviceConnectLink).click(sprime.connectToDevice);

    var deviceConnectLinkText = document.createTextNode('Connect');
    deviceConnectLink.appendChild(deviceConnectLinkText);

    var deviceNameText = document.createTextNode(deviceName);
    deviceNameCell.appendChild(deviceNameText);

    deviceConnect.appendChild(deviceConnectLink);

    deviceRow.appendChild(deviceNameCell);
    deviceRow.appendChild(deviceConnect);

    return deviceRow;
}

sprime.createEmptyRow = function() {
    
    var deviceRow = document.createElement('tr');
    $(deviceRow).addClass('deviceRow');
    
    var emptyCell = document.createElement('td');
    $(emptyCell).attr('colspan', 2);
    var empty = document.createTextNode('Could not find any devices...');
    emptyCell.appendChild(empty);

    deviceRow.appendChild(emptyCell);
    return deviceRow;
}

sprime.createAlreadyConnectedRow = function(deviceName) {
    var alreadyConnectedRow = document.createElement('tr');
    $(alreadyConnectedRow).attr('id', 'alreadyConnected');

    var alreadyConnectedCell = document.createElement('td');
    var alreadyText = document.createTextNode("You are connected to ");
    var alreadyDeviceText = document.createElement('em');
    var alreadyDeviceName = document.createTextNode(deviceName);
    alreadyDeviceText.appendChild(alreadyDeviceName);


    alreadyConnectedCell.appendChild(alreadyText);
    alreadyConnectedCell.appendChild(alreadyDeviceText);
    alreadyConnectedRow.appendChild(alreadyConnectedCell);

    var disconnectCell = document.createElement('td');
    var disconnectLink = document.createElement('a');
    $(disconnectLink).attr('id', 'disconnectLink');
    $(disconnectLink).click(sprime.disconnect);

    disconnectLink.appendChild(document.createTextNode('Disconnect'));
    disconnectCell.appendChild(disconnectLink);
    alreadyConnectedRow.appendChild(disconnectCell);

    return alreadyConnectedRow;
}


sprime.createLoadingRow = function(text) {
    var loadingRow = document.createElement('tr');
    $(loadingRow).attr('id', 'loadingRow');
    var loadingCell = document.createElement('td');
    $(loadingCell).attr('colspan', 2);
    
    var loadingText = document.createTextNode(text);
    var loadingTextNode = document.createElement('em');
    loadingTextNode.appendChild(loadingText);
    loadingCell.appendChild(loadingTextNode);

    var loadingImage = document.createElement('img');
    $(loadingImage).attr('src', sprime.config.loadingImage);
    $(loadingImage).addClass('loadingImage');
    loadingCell.appendChild(loadingImage);
    loadingRow.appendChild(loadingCell);


    return loadingRow;
}

/**
 * Displays the message with the JSON format: {message : '', success : true}.
 */
sprime.displayError = function(json) {
    var messageBox = $('#statusMessage');

    messageBox.text(json.error);

    messageBox.slideDown();
}
