#include <SoftwareSerial.h>

/*
 * LED and Relay control over the BlueSMiRF module
 *
 * ~Chris McClanahan
 */

char sval;            // variable to receive data from the serial port
int ledPin = 13;      // LED connected to pin 13 (on-board LED)
int sensorPin = 0;    // select the input pin for the potentiometer
int sensorValue = 0;  // variable to store the value coming from the sensor
int relayPin = 12;    // relay connected to pin 12

void setup() {
	pinMode(ledPin, OUTPUT);      // pin 13 (on-board LED) as OUTPUT
	pinMode(relayPin, OUTPUT);    // pin 12 as OUTPUT
	Serial.begin(115200);         // start serial communication at 115200bps
}

void loop() {

	if ( Serial.available() ) {  // if data is available to read
		sval = Serial.read();      // read it and store it in 'sval'

		if ( sval == '0' ) {             // if '0' was received led 13 is switched off
			digitalWrite(ledPin, LOW);     // turn Off pin 13
			digitalWrite(relayPin, LOW);   // turn Off pin 12
		}

		if ( sval == '1' ) {             // if '1' was received led 13 on
			digitalWrite(ledPin, HIGH);    // turn ON pin 13
			digitalWrite(relayPin, HIGH);  // turn ON pin 12
		}
	}

	{
		sval = 2;
		sensorValue = analogRead(sensorPin);  // read the value from the sensor:
		Serial.println(sensorValue);
		delay(500);                          // waits for 1/2 a second
	}

}


