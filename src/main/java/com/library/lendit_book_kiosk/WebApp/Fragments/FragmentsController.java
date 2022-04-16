package com.library.lendit_book_kiosk.WebApp.Fragments;

import com.library.lendit_book_kiosk.Book.Book;
import com.library.lendit_book_kiosk.Security.UserDetails.UserLoginDetails;
import com.library.lendit_book_kiosk.User.User;
import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.Serializable;

@Controller(value = "FragmentsController")
@RequestMapping(path = {"fragments/"}, value = {"fragments/"})
public class FragmentsController {
    private static final Logger log = LoggerFactory.getLogger(FragmentsController.class);
    @GetMapping(value = {"login_form"})
    public String login_form(Model model){
        model.addAttribute("userLoginDetails",new UserLoginDetails());
        return "fragments/login_form";
    }

    @GetMapping(value = {"login_page"})
    public String login_page(Model model){
        model.addAttribute("userLoginDetails",new UserLoginDetails());
        return "fragments/login_page";
    }

    @GetMapping(value = {"book_finder"})
    public String book_finder(Model model){
        model.addAttribute("search_book", new SearchBook());
        return "fragments/book_finder";
    }

    @GetMapping(value = {"student"})
    public String student(
            @ModelAttribute("userLoginDetails") @Valid UserLoginDetails userLoginDetails,
            @ModelAttribute("User") @Valid User user,
            @ModelAttribute("title") @Valid String title,
            HttpServletRequest request,
            HttpServletResponse response,
            BindingResult result,
            Model model
    ) {
        try {
            if (result.hasErrors()) {
                return "error";
            }
            log.info("\nUser attribute contents: {}, \nUser Login Details: {}, \nBook Details: {}",
                    user, userLoginDetails, title);
            model.addAttribute("User", user);
            model.addAttribute("userLoginDetails", userLoginDetails);
            model.addAttribute("title", ""); // now use the authentication token to assign a principal/user to the security context holder
            log.info("\nCurrent Session: {},\nResponse Headers: {}\n",
                    request.getSession(),
                    response.getHeaderNames());
            return "fragments/student";
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            ;
            return null;
        }
    }
}

