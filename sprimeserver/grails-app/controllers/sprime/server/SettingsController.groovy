package sprime.server

import org.codehaus.groovy.grails.plugins.springsecurity.Secured;

@Secured(['ROLE_USER'])
class SettingsController {

    def authenticateService

    def index = { }


    /**
     * This action is responsible for displaying the user information, so
     * that the user can edit it.
     */
    def viewUserSettings = {

        def person = authenticateService.userDomain();

        [person : person]
        
    }

    /**
     * This action is responsible for updating the user information.
     */
    def editUserSettings = {

        // Get a version of the person.
        def person = Person.get(params.id)
	if (!person) {
            flash.message = "Person not found with id $params.id"
            redirect action: 'index', controller: 'application'
            return
	}

        if (params.fullname) {
            person.userRealName = fullname;
        }

        if (params.email) {
            person.email = params.email;
        }

        // If we have any of these, then we need to validate the password change.
        if (params.oldpassword || params.newpassword || params.confirmpassword) {

            def oldpassword = authenticateService.encodePassword(params.oldpassword);

            if (!person.passwd.equals(oldpassword)) {
                flash.errorMsg = 'The old password does not equal to the current password.'
                redirect(action:'viewUserSettings');
                return;
            }

            if (!params.newpassword.equals(params.confirmpassword)) {
                flash.errorMsg = 'The new password and the confirm new password are not the same.'
                redirect(action:'viewUserSettings');
                return;
            }

            person.passwd = authenticateService.encodePassword(params.newpassword);

        }

        if (person.save()) {
            flash.message = 'Saved user information'
            // Update the current user context.
            redirect(action: 'viewUserSettings');

	} else {

            flash.errorMsg = 'Unable to save. Please try again later.'
            // Unable to save the user.
            redirect(action: 'viewUserSettings');
	}
    }
}
