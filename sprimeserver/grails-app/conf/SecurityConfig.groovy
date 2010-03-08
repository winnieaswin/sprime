security {

	// see DefaultSecurityConfig.groovy for all settable/overridable properties

	active = true

	loginUserDomainClass = "Person"
	authorityDomainClass = "Authority"

        loginFormUrl = '/'
        defaultTargetUrl = '/viewData'
        authenticationFailureUrl = '/authfail?login_error=1'

        useRequestMapDomainClass = false

        useControllerAnnotations = true
}
