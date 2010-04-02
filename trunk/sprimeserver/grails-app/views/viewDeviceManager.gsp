<html>
  <head>
    <meta name="layout" content="main" />
    <title>DEVICE MANAGER</title>

    <script type="text/javascript" src="${resource(dir:'js', file:'deviceManager.js')}"></script>

    <script type="text/javascript">
      sprime.config.isConnected = ${isConnected};
      sprime.config.deviceName = '${deviceName}';
      sprime.config.searchForBluetoothDeviceUrl = "<g:createLink controller='device' action='searchForDevices' />";
      sprime.config.loadingImage = "${resource(dir: 'images', file: 'ajax-loader.gif')}";
      sprime.config.connectToDeviceUrl = "<g:createLink controller='device' action='connectToDevice' />";
      sprime.config.disconnectUrl = "<g:createLink controller='device' action='disconnect' />";

      $(document).ready(sprime.initDeviceManager);

    </script>

    
  </head>
  <body>

        <g:displaySideBar />

        <div class="span-18 last">

          <h3 class="loud">Device Manager</h3>

          <p>You can manage which device the SPRIME server is connected to. Also, you
          can disconnect and search for new devices using this page.</p>

          <div id="statusMessage" style="display: none" class="error">Messages Go Here</div>

          <div id="bluetoothDeviceTable" class="span-11 append-7 last">

            <table id="deviceTable">
                <tr>
                    <th>Device Name</th>
                    <th><a id="searchLink" style="display: none;">Search...</a></th>
                </tr>
            </table>

          </div>

        </div>
   </body>
</html>