package sprime.server

/**
 * This is the JMS service that used to talk to the device.
 * You can send information to this application as long as it is in a specific
 * format.
 *
 * Also, any service that talks to the device must accept message stating
 * whether or not to turn off or on the device.
 */
class DeviceJMSService {

    // exposes this service to the jms plugin, also sets our destination.
    static exposes = ['jms']
    static destination = 'sprimeInQ'

    def jmsService

    /**
     * Send a command to a specific device. Currently, we are only
     * sending out to the sprimeOutQ, but in theory, we can change this method
     * to talk to a specific device if we wanted to.
     */
    void sendCommandMessage(command) {

        // send message to the queue.
        jmsService.send(queue: 'sprimeOutQ', ['command' : command]);
    }

    def onMessage(msg) {
        log.info msg;
    }
}
