package sprime.server


import sprime.server.BluetoothServer;
import sprime.server.Receiving;

class DeviceService {

    def bgThreadManager


    BluetoothServer server;

    def createAndStartServer() {

        server = new BluetoothServer();
        server.createConnection();

        Receiving receiving = new Receiving(server.getConnection());

        bgThreadManager.queueRunnable(receiving);
    }

    def sendMessage(msg) {

        log.info("Sending Message: " + msg);
        server.sendMessage(msg);
    }
}
