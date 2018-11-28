package org.jtest.app.controller;

import java.util.HashMap;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
 
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
 
/**
 * 
 * @author Administrator
 *
 */
public abstract class BaseController {
   public HttpServletRequest getRequest() {
      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
              .getRequestAttributes()).getRequest();
      return request;
   }
 
   public HttpServletResponse getResponse() {
      HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
              .getRequestAttributes()).getResponse();
      return response;
   }

 
}
