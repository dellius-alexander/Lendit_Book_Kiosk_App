package com.library.lendit_book_kiosk.Fragments;

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
}
