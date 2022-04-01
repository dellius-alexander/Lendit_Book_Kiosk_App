package com.library.lendit_book_kiosk.WebApp.Fragments;

import com.library.lendit_book_kiosk.Security.UserDetails.UserLoginDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragmentsController {

    @GetMapping(value = {"fragments/login_form"})
    public String login_form(Model model){
        model.addAttribute("userLoginDetails",new UserLoginDetails());
        return "fragments/login_form";
    }

    @GetMapping(value = {"fragments/login_page"})
    public String login_page(Model model){
        model.addAttribute("userLoginDetails",new UserLoginDetails());
        return "fragments/login_page";
    }
}
