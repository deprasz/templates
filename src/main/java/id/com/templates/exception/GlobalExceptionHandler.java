package id.com.templates.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView noHandler(HttpServletRequest request, Exception e)   {
        ModelAndView mav = new ModelAndView("/error");
        mav.addObject("error", e.getMessage());  
        return mav;
    }
	
	@ExceptionHandler(RuntimeException.class)
    public ModelAndView runtimeHandler(HttpServletRequest request, Exception e)   {
		ModelAndView mav = new ModelAndView("/error");
        mav.addObject("error", e.getMessage());  
        return mav;
    }
	
	@ExceptionHandler(Exception.class)
    public ModelAndView execptionHandler(HttpServletRequest request, Exception e)   {
		ModelAndView mav = new ModelAndView("/error");
        mav.addObject("error", e.getMessage());  
        return mav;
    }
}
