package sprime.server

/**
 *
 * @author jeffreystarker
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.OutputStream;

import javax.bluetooth.*;
import javax.microedition.io.*;

/**
 * Class that implements an SPP Server which accepts single line of message from an SPP client and
 * sends a single line of response to the client.
 */
public class BluetoothServer {

    StreamConnection connection;

    PrintWriter pWriter;

    public BluetoothServer() {
        // default constructor.

    }

    public void createConnection() throws IOException {
        LocalDevice localDevice = LocalDevice.getLocalDevice();

        // Create a UUID
	UUID uuid = new UUID("1101", true); // serial, SPP
	// UUID uuid = new UUID("1105", true); // obex obj push
	// UUID uuid = new UUID("0003", true); // rfcomm
	// UUID uuid = new UUID("1106", true); // obex file transfer

	// Create the service url
	String connectionString = "btspp://localhost:" + uuid + ";name=Sample SPP Server";

	// open server url
	StreamConnectionNotifier streamConnNotifier = (StreamConnectionNotifier) Connector.open(connectionString);

	// Wait for client connection
	log.info("\nServer Started. Waiting for clients to connect...");
	connection = streamConnNotifier.acceptAndOpen();

	// connect
	log.info("Connecting to client...");
	RemoteDevice dev = RemoteDevice.getRemoteDevice(connection);
	try {
            log.info("Remote device address: " + dev.getBluetoothAddress());
            log.info("Remote device name: " + dev.getFriendlyName(true));
	} catch (final IOException ioe) {
            ioe.printStackTrace();
	}

        // Create sending stream:

        OutputStream outStream;
	try {
            outStream = connection.openOutputStream();
            pWriter = new PrintWriter(new OutputStreamWriter(outStream));
	} catch (IOException e) {
            e.printStackTrace();
	}
    }

    public StreamConnection getConnection() {
        return connection;
    }

    public void sendMessage(msg) {
        try {
            pWriter.write(msg);
            pWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
	}
    }

} // class