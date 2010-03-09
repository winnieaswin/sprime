security {

	// see DefaultSecurityConfig.groovy for all settable/overridable properties

	active = true
        cacheUsers = false

	loginUserDomainClass = "sprime.server.Person"
	authorityDomainClass = "sprime.server.Authority"

        loginFormUrl = '/'
        defaultTargetUrl = '/viewData'
        authenticationFailureUrl = '/authfail?login_error=1'

        useRequestMapDomainClass = false

        useControllerAnnotations = true
}
