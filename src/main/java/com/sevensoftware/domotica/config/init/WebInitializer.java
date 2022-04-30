/**
 * 
 */
package com.sevensoftware.domotica.config.init;

import javax.servlet.ServletRegistration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.sevensoftware.domotica.config.SpringSecurityConfig;
import com.sevensoftware.domotica.config.WebConfiguration;

/**
 * @author LUIS
 *
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{
	
	@Override
	  protected void customizeRegistration(ServletRegistration.Dynamic registration) {
	    registration.setInitParameter("dispatchOptionsRequest", "true");
	    registration.setAsyncSupported(true);
	  }
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { WebConfiguration.class, SpringSecurityConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}
