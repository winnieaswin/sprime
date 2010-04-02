package sprime.server

import org.codehaus.groovy.grails.plugins.springsecurity.Secured
import sprime.server.DeviceService;
import grails.converters.JSON;

@Secured(['ROLE_USER'])
class DeviceController {

    def deviceService;

    def displayDeviceManager = {

        def isConnected = deviceService.isConnected();

        def deviceName = "";

        if (isConnected) {
            deviceName = deviceService.getDeviceName();
        }

        render(view: '/viewDeviceManager', model: [deviceName: deviceName, isConnected : isConnected]);
    }

    def searchForDevices = {
        
        def json = [:];
        Collection<String> devices;

        try {
            devices = deviceService.findDevices();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            json.error = 'Unable to search for devices';
        }
        
        json.devices = devices;

        render json as JSON;
    }

    def connectToDevice = {
        def deviceName = params.deviceName;

        def json = [:];

        try {
            deviceService.connectToDevice(deviceName);
            json.connected = true;
            json.deviceName = deviceName;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            json.error = "Unable to connect to device: ${deviceName}";
        }

        render json as JSON;
    }

    def disconnect = {

        def json = [:];

        try {
            deviceService.disconnect();
            json.success = true;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            json.error = "Unable to disconnect from device.";
        }

        render json as JSON;
    }
}
