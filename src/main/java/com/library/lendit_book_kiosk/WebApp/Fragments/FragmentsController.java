package com.library.lendit_book_kiosk.WebApp.Fragments;

import com.library.lendit_book_kiosk.Security.UserDetails.UserLoginDetails;
import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;

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

    @GetMapping(value = {"fragments/book_finder"})
    public String book_finder(Model model){
        model.addAttribute("search_book", new SearchBook());
        return "fragments/book_finder";
    }
}

