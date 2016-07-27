import grails.plugin.springsecurity.SpringSecurityUtils

class BootStrap {

	def grailsApplication

	def init = { servletContext ->
		if (SpringSecurityUtils.securityConfigType != 'Requestmap') {
			return
		}

		String requestMapClassName = SpringSecurityUtils.securityConfig.requestMap.className
		def Requestmap = grailsApplication.getClassForName(requestMapClassName)
		if (Requestmap.count()) {
			return
		}

		for (url in ['/', '/index', '/index.gsp', '/**/js/**', '/**/css/**', '/**/images/**', '/**/favicon.ico',
						 '/login', '/login/**', '/logout', '/logout/**',
						 '/hack', '/hack/**', '/tagLibTest', '/tagLibTest/**',
						 '/testRequestmap', '/testRequestmap/**',
						 '/testUser', '/testUser/**', '/testRole', '/testRole/**']) {
			Requestmap.newInstance(url: url, configAttribute: 'permitAll').save(flush: true, failOnError: true)
		}

		assert 21 == Requestmap.count()
	}
}
