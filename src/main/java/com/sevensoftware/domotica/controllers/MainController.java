package com.sevensoftware.domotica.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sevensoftware.domotica.entities.CustomUserDetail;
import com.sevensoftware.domotica.entities.Usuario;

@Controller
public class MainController {
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {
		
		System.out.println("Dentro del controllador del login");

	  ModelAndView model = new ModelAndView();
	  if (error != null) {
		  System.out.println("Dentro del error");
		model.addObject("error", "Usuario y Clave Incorrestas!");
	  }

	  if (logout != null) {
		model.addObject("msg", "You've been logged out successfully.");
	  }
	  model.setViewName("login");

	  return model;

	}
	
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView getDashboard(HttpServletRequest request) {		
		
		CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dashboard");
		request.setAttribute("userDetails", userDetails);
		return mv;
	}
	
	@RequestMapping(value = "/socketTest", method = RequestMethod.GET)
	public ModelAndView websocketTest(HttpServletRequest request) {		
						
		ModelAndView mv = new ModelAndView();
		mv.setViewName("WebsocketTest");
		return mv;
	}
	
	
	
	@RequestMapping(value = "/plotTest", method = RequestMethod.GET)
	public ModelAndView plotTest(HttpServletRequest request) {		
						
		ModelAndView mv = new ModelAndView();
		mv.setViewName("plotTest");
		return mv;
	}
	
	
	@RequestMapping(value = "/plotTest2", method = RequestMethod.GET)
	public ModelAndView plotTest2(HttpServletRequest request) {		
						
		ModelAndView mv = new ModelAndView();
		mv.setViewName("plotTest2");
		return mv;
	}
	
	@RequestMapping(value = "/plotTest3", method = RequestMethod.GET)
	public ModelAndView plotTest3(HttpServletRequest request) {		
						
		ModelAndView mv = new ModelAndView();
		mv.setViewName("plotTest3");
		return mv;
	}
	
	//for 403 access denied page
		@RequestMapping(value = "/403", method = RequestMethod.GET)
		public ModelAndView accesssDenied() {

		  ModelAndView model = new ModelAndView();

		  //check if user is login
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			model.addObject("username", userDetail.getUsername());
		  }

		  model.setViewName("page_403");
		  return model;

		}
		
		@RequestMapping(value = "/500", method = RequestMethod.GET)
		public ModelAndView serverError() {

		  ModelAndView model = new ModelAndView();

		  //check if user is login
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			model.addObject("username", userDetail.getUsername());
		  }

		  model.setViewName("page_500");
		  return model;

		}
	
	
}
