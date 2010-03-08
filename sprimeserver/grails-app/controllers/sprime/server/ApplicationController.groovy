package sprime.server

import org.codehaus.groovy.grails.plugins.springsecurity.Secured;
import org.springframework.security.DisabledException;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;

/**
 * This is the main controller for the server. Or the entry point. This class
 * is responsible for displaying to home page and setting up the login.
 */
class ApplicationController {
    
    	/**
	 * Dependency injection for the authentication service.
	 */
	def authenticateService

        def index = {

            if (isLoggedIn()) {
                render(view: '/viewData')
            } else {
                render(view: '/index')
            }
        }

        @Secured(['ROLE_USER'])
        def viewData = {

            def type = "Current";

            // Break on logic depending on what view we want to show.
            if (params.id == 'daily') {
                // Daily view logic
                type = "Daily"


            } else if (params.id == 'weekly') {
                // Weekly view logic
                type = "Weekly"

            } else if (params.id == 'monthly') {
                // Monthly view logic
                type = "Monthly"

            } else if (params.id == 'yearly') {
                // Yearly view logic
                type = "Yearly"

            } else {
                // Current view logic.

            }

            render(view: '/viewData', model: [type : type])
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
