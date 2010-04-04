package sprime.server


import sprime.server.BluetoothClient;
import sprime.server.Receiving;

class DeviceService {

    def bgThreadManager;

    BluetoothClient client = new BluetoothClient();

    Receiving receivingThread;


    boolean isConnected() {
        return client.isConnected();
    }

    String getDeviceName() {
        return client.getDeviceName();
    }

    Collection<String> findDevices() throws IOException {
        return client.findDevices();
    }

    void connectToDevice(final String name) throws IOException {

        client.connectToDevice(name);
        // also add receiving thread

        receivingThread = new Receiving(client.getConnection());

        bgThreadManager.queueRunnable(receivingThread);
    }

    void sendMessage(final String msg) {
        client.sendMessage(msg);
    }

    void disconnect() throws IOException {
        receivingThread.stop();
        client.disconnect();
    }
}
