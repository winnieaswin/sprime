package sprime.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/**
 *
 * @author jeffreystarker
 */
public class BluetoothClient implements DiscoveryListener {

	// object used for waiting
	private final static Object lock = new Object();

	private Map<String, RemoteDevice> bluetoothDevices = new HashMap<String, RemoteDevice>();

	// device connection address
	private static String connectionURL = null;

	// Connection to the device.
	private StreamConnection connection;

	private String deviceName;

	private PrintWriter outboundStream = null;

	public Collection<String> findDevices() throws IOException {

		bluetoothDevices.clear();

		// display local device address and name
		LocalDevice localDevice = LocalDevice.getLocalDevice();
		System.out.println("Address: " + localDevice.getBluetoothAddress());
		System.out.println("Name: " + localDevice.getFriendlyName());

		// find devices
		DiscoveryAgent agent = localDevice.getDiscoveryAgent();
		System.out.println("Starting device inquiry...");
		agent.startInquiry(DiscoveryAgent.GIAC, this);

		// avoid callback conflicts
		try {
			synchronized (lock) {
				lock.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return bluetoothDevices.keySet();
	}

	public void connectToDevice(final String deviceName) throws IOException {
		RemoteDevice deviceToConnectTo = bluetoothDevices.get(deviceName);

		if (deviceToConnectTo == null) {

			throw new IOException("Unable to connect to device " + deviceName);
		}

		UUID[] uuidSet = new UUID[1];

		uuidSet[0] = new UUID("1101", true); // serial, SPP
		// uuidSet[0] = new UUID("0003", true); // rfcomm
		// uuidSet[0] = new UUID("1106", true); // obex file transfer
		// uuidSet[0] = new UUID("1105", true); // obex obj push

		// Get the discovery agent.
		LocalDevice localDevice = LocalDevice.getLocalDevice();
		DiscoveryAgent agent = localDevice.getDiscoveryAgent();

		System.out.println("\nSearching for services...");
		agent.searchServices(null, uuidSet, deviceToConnectTo, this);

		// avoid callback conflicts
		try {
			synchronized (lock) {
				lock.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// check
		if (connectionURL == null) {
			throw new IOException("Unable to connect to device: " + deviceName);
		}

		// connect to the server
		connection = null;
		try {
			connection = (StreamConnection) Connector.open(connectionURL);
		} catch (Exception e) {
			throw new IOException("Unable to connect to device: " + deviceName);
		}

		// Once we have connected to the device, we can store the devicename.
		this.deviceName = deviceName;

		// Create our outbound stream
		OutputStream outStream;
		try {
			outStream = this.connection.openOutputStream();
			outboundStream = new PrintWriter(new OutputStreamWriter(outStream));
		} catch (IOException e) {
			disconnect();
			throw new IOException("Unable to connect to device: " + deviceName);
		}
	}

	public void sendMessage(final String message) {

		if (outboundStream != null) {
			outboundStream.write(message);
			outboundStream.flush();
		}
	}

	public StreamConnection getConnection() {
		return connection;
	}

	public boolean isConnected() {
		return connection != null;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void disconnect() throws IOException {
		deviceName = null;
		bluetoothDevices.clear();
		outboundStream.close();
		outboundStream = null;

		connection.close();

		connection = null;
	}

	// methods of DiscoveryListener
	public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
		// Add device to the map

		String name = null;

		try {
			name = btDevice.getBluetoothAddress() + " ("
				   + btDevice.getFriendlyName(true) + ")";

		} catch (final IOException ioe) {
			return; // If we have an error then don't add the device to the list.
		}

		if (!bluetoothDevices.containsKey(name)) {
			bluetoothDevices.put(name, btDevice);
		}
	}

	// implement this method since services are not being discovered
	public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
		if (servRecord != null && servRecord.length > 0) {
			connectionURL = servRecord[0].getConnectionURL(0, false);
		}
		synchronized (lock) {
			lock.notify();
		}
	}

	// implement this method since services are not being discovered
	public void serviceSearchCompleted(int transID, int respCode) {
		synchronized (lock) {
			lock.notify();
		}
	}

	public void inquiryCompleted(int discType) {
		synchronized (lock) {
			lock.notify();
		}

	}
}