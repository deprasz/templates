package id.com.templates.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import id.com.templates.security.AuthService;

@Controller
public class IndexController {
	@Autowired AuthService authService;

    @RequestMapping("/login")
    public String login(Model model){
    	model.addAttribute("loginError", false);
        return "/login.zul";
    }

    @RequestMapping("/menu")
    public ModelAndView index(Model model){
    	ModelAndView mav = new ModelAndView("/index.zul");
        return mav;
    }

    @RequestMapping("/logout/successfully")
    public ModelAndView logout(Model model){
    	ModelAndView mav = new ModelAndView("/logout.zul");
        return mav;
    }

    @RequestMapping("/registration")
    public ModelAndView register(Model model){
    	ModelAndView mav = new ModelAndView("/page/account/Register.zul");
        return mav;
    }

    @RequestMapping("/forgot-password")
    public ModelAndView forgot(Model model){
    	ModelAndView mav = new ModelAndView("/page/account/ForgotPassword.zul");
        return mav;
    }

    @RequestMapping("/error")
    public ModelAndView error(){
    	ModelAndView view = new ModelAndView();
    	view.setViewName("/error.zul");
        return view;
    }
    
    @RequestMapping("/timeout")
    public ModelAndView timeout(){
    	ModelAndView view = new ModelAndView();
    	view.setViewName("/timeout.zul");
        return view;
    }
}
