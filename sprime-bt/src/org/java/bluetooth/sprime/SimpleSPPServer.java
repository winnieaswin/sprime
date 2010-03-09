package org.java.bluetooth.sprime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.bluetooth.*;
import javax.microedition.io.*;

/**
 * Class that implements an SPP Server which accepts single line of message from an SPP client and
 * sends a single line of response to the client.
 */
public class SimpleSPPServer {

	/**
	 * runs a bluetooth client that accepts a string from a client and sends a response
	 */
	public void runServer() throws IOException {

		// display local device address and name
		LocalDevice localDevice = LocalDevice.getLocalDevice();
		System.out.println("Address: " + localDevice.getBluetoothAddress());
		System.out.println("Name: " + localDevice.getFriendlyName());

		// SimpleSPPServer sampleSPPServer = new SimpleSPPServer();
		// sampleSPPServer.startServer();
		this.startServer();

	}

	// start server
	private void startServer() throws IOException {

		// Create a UUID 
		UUID uuid = new UUID("1101", true); // serial, SPP
		//UUID uuid = new UUID("1105", true); // obex obj push
		//UUID uuid = new UUID("0003", true); // rfcomm 
		//UUID uuid = new UUID("1106", true); // obex file transfer
		

		// Create the service url
		String connectionString = "btspp://localhost:" + uuid + ";name=Sample SPP Server";

		// open server url
		StreamConnectionNotifier streamConnNotifier = (StreamConnectionNotifier) Connector.open(connectionString);

		// Wait for client connection
		System.out.println("\nServer Started. Waiting for clients to connect...");
		StreamConnection connection = streamConnNotifier.acceptAndOpen();

		// connect
		RemoteDevice dev = RemoteDevice.getRemoteDevice(connection);
		try {
			System.out.println("Remote device address: " + dev.getBluetoothAddress());
			System.out.println("Remote device name: " + dev.getFriendlyName(true));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		// read string from spp client
		InputStream inStream = connection.openInputStream();
		BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
		String lineRead = bReader.readLine();
		System.out.println(lineRead);

		// send response to spp client
		OutputStream outStream = connection.openOutputStream();
		PrintWriter pWriter = new PrintWriter(new OutputStreamWriter(outStream));
		pWriter.write("Response String from SPP Server\r\n");
		pWriter.flush();

		// close connection
		pWriter.close();
		streamConnNotifier.close();

	}

}