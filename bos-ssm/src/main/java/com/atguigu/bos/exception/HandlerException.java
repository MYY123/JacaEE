package com.atguigu.bos.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {
	
	@ExceptionHandler({AuthorizationException.class})
	public ModelAndView handleArithmeticException(Exception ex){
		System.out.println("----> 出异常了: " + ex);
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception", ex);
		return mv;
	}
}
