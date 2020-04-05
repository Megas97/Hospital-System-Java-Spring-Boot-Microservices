package com.megas.hospitalapp.app.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomErrorController implements ErrorController {
	@GetMapping(value = "/error")
	public ModelAndView error(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		Cookie doctorCookie = new Cookie("loggedInDoctorId", null);
		response.addCookie(doctorCookie);
		Cookie patientCookie = new Cookie("loggedInPatientId", null);
		response.addCookie(patientCookie);
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		String errorMsg = "";
		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			// Server error responses
			if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				errorMsg = "ERROR 500 INTERNAL SERVER ERROR";
			}else if(statusCode == HttpStatus.NOT_IMPLEMENTED.value()) {
				errorMsg = "ERROR 501 NOT IMPLEMENTED";
			}else if(statusCode == HttpStatus.BAD_GATEWAY.value()) {
				errorMsg = "ERROR 502 BAD GATEWAY";
			}else if(statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
				errorMsg = "ERROR 503 SERVICE UNAVAILABLE";
			}else if(statusCode == HttpStatus.GATEWAY_TIMEOUT.value()) {
				errorMsg = "ERROR 504 GATEWAY TIMEOUT";
			}else if(statusCode == HttpStatus.HTTP_VERSION_NOT_SUPPORTED.value()) {
				errorMsg = "ERROR 505 HTTP VERSION NOT SUPPORTED";
			}else if(statusCode == HttpStatus.VARIANT_ALSO_NEGOTIATES.value()) {
				errorMsg = "ERROR 506 VARIANT ALSO NEGOTIATES";
			}else if(statusCode == HttpStatus.INSUFFICIENT_STORAGE.value()) {
				errorMsg = "ERROR 507 INSUFFICIENT STORAGE";
			}else if(statusCode == HttpStatus.LOOP_DETECTED.value()) {
				errorMsg = "ERROR 508 LOOP DETECTED";
			}else if(statusCode == HttpStatus.NOT_EXTENDED.value()) {
				errorMsg = "ERROR 510 NOT EXTENDED";
			}else if(statusCode == HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.value()) {
				errorMsg = "ERROR 511 NETWORK AUTHENTICATION REQUIRED";
			}
		}
		model.addObject("errorMsg", errorMsg);
		model.setViewName("home/custom_error");
		return model;
	}
	
	@Override
    public String getErrorPath() {
        return "/error";
    }
}