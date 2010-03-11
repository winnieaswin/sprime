package org.java.bluetooth.sprime;

import java.io.IOException;

public class SprimeBT {

	/**
	 * Main
	 * 
	 * @param args
	 */

	// main method of the application
	public static void main(String[] args) throws IOException {

		/*
		 * Test bed for BT classes. Set to true/false for testing
		 */

		if (false) {
			// scan and print nearby bluetooth devices
			System.out.println("\n" + "Testing BT Device Discovery" + "\n");
			BluetoothDeviceDiscovery bluetoothDeviceDiscovery = new BluetoothDeviceDiscovery();
			bluetoothDeviceDiscovery.listDevices();
		}

		if (false) {
			// scan and print nearby bluetooth device services
			System.out.println("\n" + "Testing BT Service Discovery" + "\n");
			BluetoothServiceDiscovery bluetoothServiceDiscovery = new BluetoothServiceDiscovery();
			bluetoothServiceDiscovery.listServices();
		}

		if (false) {
			// start a bluetooth server
			System.out.println("\n" + "Testing BT Server" + "\n");
			SimpleSPPServer sppServer = new SimpleSPPServer();
			sppServer.runServer();
		}

		if (true) {
			// start a bluetooth client
			System.out.println("\n" + "Testing BT Client" + "\n");
			SimpleSPPClient sppClient = new SimpleSPPClient();
			sppClient.runClient();
		}

	}// end main

}// end class
