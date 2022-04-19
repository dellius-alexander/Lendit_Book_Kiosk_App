package com.library.lendit_book_kiosk.WebApp.Fragments;

import com.library.lendit_book_kiosk.Book.Book;
import com.library.lendit_book_kiosk.Book.BookService;
import com.library.lendit_book_kiosk.Security.Custom.CustomAuthenticationProvider;
import com.library.lendit_book_kiosk.Security.UserDetails.UserLoginDetails;
import com.library.lendit_book_kiosk.User.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller(value = "FragmentsController")
@RequestMapping(path = {"fragments/"}, value = {"fragments/"})
public class FragmentsController {
    private static final Logger log = LoggerFactory.getLogger(FragmentsController.class);

    @Autowired
    private static UserLoginDetails userLoginDetails;

    private static final Map<String, Object> payLoad = new HashMap<>();

    @GetMapping(value = {"login_form"})
    public String login_form(Model model){
        if(((UserLoginDetails)payLoad.get("userLoginDetails")).isEmpty() &&
                ! ((UserLoginDetails) model.getAttribute("userLoginDetails")).isEmpty()){
            payLoad.put("userLoginDetails", ((UserLoginDetails) model.getAttribute("userLoginDetails")));
        }
        else if(userLoginDetails.isEmpty()){
            payLoad.put("userLoginDetails", new UserLoginDetails());
        }
        model.addAllAttributes( payLoad.values() );
        log.info("\nPayload: {}\n",payLoad.values().stream().collect(Collectors.toList()));
        return "fragments/login_form";
    }

    @GetMapping(value = {"login_page"})
    public String login_page(Model model){
        if(((UserLoginDetails)payLoad.get("userLoginDetails")).isEmpty() &&
                ! ((UserLoginDetails) model.getAttribute("userLoginDetails")).isEmpty()){
            payLoad.put("userLoginDetails", ((UserLoginDetails) model.getAttribute("userLoginDetails")));
        }
        else if(userLoginDetails.isEmpty()){
            payLoad.put("userLoginDetails", new UserLoginDetails());
        }
//        model.addAllAttributes( payLoad.values() );
        log.info("\nPayload: {}\n",payLoad.values().stream().collect(Collectors.toList()));
        return "fragments/login_page";
    }

    @GetMapping(value = {"book_finder"})
    public String book_finder(Model model){
        if( ! ((UserLoginDetails) model.getAttribute("userLoginDetails")).isEmpty()){
            payLoad.put("userLoginDetails", ((UserLoginDetails) model.getAttribute("userLoginDetails")));
        }
        else if( ! userLoginDetails.isEmpty()){
            payLoad.put("userLoginDetails", userLoginDetails);
        }
        payLoad.put("search_book", new SearchBook());
        model.addAllAttributes( payLoad.values() );
        log.info("\nPayload: {}\n",payLoad.values().stream().collect(Collectors.toList()));
        return "fragments/book_finder";
    }
}

