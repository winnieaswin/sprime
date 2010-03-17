package sprime.server

import grails.test.*

class DeviceJMSServiceTestsTests extends GrailsUnitTestCase {
    def deviceJMSService


    void testSendMessageToQueue() {

        deviceJMSService.sendCommandMessage("on");
        deviceJMSService.sendCommandMessage("off");
    }
}
