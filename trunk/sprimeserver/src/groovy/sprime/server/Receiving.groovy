package sprime.server

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.bluetooth.*;
import javax.microedition.io.*;

public class Receiving implements Runnable {

    private StreamConnection connection = null;
    private InputStream inStream = null;

    private boolean stop = false;

    public Receiving(StreamConnection c) {
        connection = c;
	try {
            inStream = connection.openInputStream();
	} catch (final Exception e) {
            e.printStackTrace();
	}
    }

    @Override
    public void run() {
        while (!this.stop) {
            try {

		BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
		String lineRead = bReader.readLine();
		System.out.println("Server recv: " + lineRead);
		Thread.sleep(500);
            } catch (final Exception e) {
                e.printStackTrace();
            }
	}
    }

    public void stop() {
        this.stop = true;
    }
} // class