package sprime.server

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.bluetooth.*;
import javax.microedition.io.*;

import sprime.server.PowerUsage;

public class Receiving implements Runnable {

    private double k = 0.33;

    private double avgDisplay = 0;
    
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

                int watt = Integer.parseInt(lineRead);
                avgDisplay = (watt * k) + avgDisplay * (1 - k);

                PowerUsage usage = new PowerUsage(wattage: avgDisplay);
                usage.save();

		Thread.sleep(1000);
            } catch (final Exception e) {
                e.printStackTrace();
            }
	}
    }

    public void stop() {
        this.stop = true;
    }
} // class