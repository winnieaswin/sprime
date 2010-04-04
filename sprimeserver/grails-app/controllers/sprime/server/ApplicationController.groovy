package sprime.server

import org.codehaus.groovy.grails.plugins.springsecurity.Secured;
import org.springframework.security.DisabledException;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;
import sprime.server.DeviceService;

import grails.converters.JSON

/**
 * This is the main controller for the server. Or the entry point. This class
 * is responsible for displaying to home page and setting up the login.
 */
class ApplicationController {
    
    	/**
	 * Dependency injection for the authentication service.
	 */
	def authenticateService

        /**
         * Dependency injection for the device service.
         */
        def deviceService

        /**
         * The main page of the application. This action will redirect the
         * user to the correct page depending on whether they are logged in.
         *
         * If they are logged in, then it will go to the first page that displays
         * data of the sprime device. Otherwise, it will redirect to the login page.
         */
        def index = {

            if (isLoggedIn()) {
                render(view: '/viewData')
            } else {
                render(view: '/index')
            }
        }

        /**
         * This action determines what type of view to show the user. The user
         * can pick any number of time views such as daily, current, hourly,
         * weekly, etc power usage views.
         */
        @Secured(['ROLE_USER'])
        def viewData = {

            def type = "current";

            // Break on logic depending on what view we want to show.
            if (params.id == 'daily') {
                // Daily view logic
                type = "daily"

            } else if (params.id == 'hourly') {
                type = 'hourly'

            } else if (params.id == 'weekly') {
                // Weekly view logic
                type = "weekly"

            } else if (params.id == 'monthly') {
                // Monthly view logic
                type = "monthly"

            } else if (params.id == 'yearly') {
                // Yearly view logic
                type = "yearly"

            } else {
                // Current view logic.

            }

            render(view: '/viewData', model: [type : type])
        }

        /**
         * This action will turn off or on the power of the device depending
         * on what is sent to this action.
         */
        @Secured(['ROLE_USER'])
        def controlPower = {

            def json = [:];

            if (deviceService.isConnected()) {

                def msg = ''

                if (params.control == 'on') {
                    msg = '1';
                    flash.message = 'Power Turned On'

                } else if (params.control == 'off') {
                    msg = '0';
                    flash.message = 'Power Turned Off'
                }

                flash.success = true

                deviceService.sendMessage(msg);

            } else {
                flash.success = false;
                flash.message = 'You are not connected to a device.';

            }

            redirect(action:'viewData', params: params)
        }

    	/**
	 * login failed
	 */
	def authfail = {

		def username = session[AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY]
		def msg = ''
		def exception = session[AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY]
		if (exception) {
			if (exception instanceof DisabledException) {
				msg = "[$username] is disabled."
			}
			else {
				msg = "[$username] wrong username/password."
			}
		}

		if (isAjax()) {
			render "{error: '${msg}'}"
		}
		else {
			flash.message = msg
			redirect action: 'index', params: params
		}
	}

        def logout = {redirect(uri: '/j_spring_security_logout')}

    	/**
	 * Check if logged in.
	 */
	private boolean isLoggedIn() {
		return authenticateService.isLoggedIn()
	}

	private boolean isAjax() {
		return authenticateService.isAjax(request)
	}
}
