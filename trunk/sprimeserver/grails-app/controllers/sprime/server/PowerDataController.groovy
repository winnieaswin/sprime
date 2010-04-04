package sprime.server

import org.codehaus.groovy.grails.plugins.springsecurity.Secured
import grails.converters.JSON
import sprime.server.DeviceService

import sprime.server.PowerUsage
import groovy.time.*

/**
 * This controller is responsible for retrieving JSON data about the power usage
 * depending on the type.
 */
@Secured(['ROLE_USER'])
class PowerDataController {

    def deviceService

    def index = {
        
    }

    def retrieveData = {

        def json = [:];
        def cols = [];
        def rows = [];

        def powerUsages = [];

        switch (params.id) {
            case 'current':
            case 'hourly':
            case 'daily' :
            case 'weekly' :
            case 'monthly':
            case 'yearly' :
            default:
                use (TimeCategory) {
                    def currentDate = new Date();

                    powerUsages = PowerUsage.findAllByDateCreatedBetween(currentDate,
                        currentDate - 20.seconds);
                }
        }

        def col1 = [:];
        col1.id = 'A';
        col1.label = 'Time';
        col1.type = 'string';

        def col2 = [:];
        col2.id = 'A';
        col2.label = 'Usage';
        col2.type = 'number';

        cols.add(col1);
        cols.add(col2);

        powerUsages.each {
            def row = [:];

            def col = [];
            def val1 = [:];
            val1.v = it.dateCreated;
            col.add(val1);

            def val2 = [:];
            val2.v = it.wattage;
            col.add(val2);

            row.c = col;

            rows.add(row);
        };

        json.cols = cols;
        json.rows = rows;

        render json as JSON;
    }

    def controlPower = {

        def json = [:];
        def msg = ''

        if (deviceService.isConnected()) {
            
            if (params.id == 'on') {
                msg = '1';
                json.message = 'Power Turned On'
                json.success = true;
            } else if (params.id == 'off') {
                msg = '0';
                json.message = 'Power Turned Off'
                json.success = true;
            }

            deviceService.sendMessage(msg);
        } else {
            json.message = "You are not connected to a device";
            json.success = false;
        }

        

        render json as JSON;
    }
}
