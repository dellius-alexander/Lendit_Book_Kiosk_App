package com.library.lendit_book_kiosk.Security.Login;

import com.library.lendit_book_kiosk.Security.custom.CustomAuthenticationProvider;
import com.library.lendit_book_kiosk.User.User;
import com.library.lendit_book_kiosk.User.UserLoginDetails;
import com.library.lendit_book_kiosk.User.UserService;
import org.apache.commons.lang.NullArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.Serializable;
import java.net.URI;

@Controller(value = "/")
public class LoginController implements Serializable{
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final UserService userService;

    public LoginController(
            CustomAuthenticationProvider customAuthenticationProvider,
            UserService userService) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.userService = userService;
    }

    /**
     * Initial login
     * @return login page
     */
    @RequestMapping(path = "/login", method=RequestMethod.GET)
    public String login(
            Model model
    ){
//        User user = new User();
//        model.addAttribute("user", user);
        model.addAttribute("userLoginDetails", new UserLoginDetails());
//        log.info("\n\nUser: {}\n\n",user.toString());
        log.info("\nUserLoginDetails form: {}\n",model.toString());
        return "login";
    }

    /**
     * User login form
     * @param userLoginDetails
     * @param result the results
     * @return
     */
    @RequestMapping(value="/verify", method=RequestMethod.POST)
    public String verify(
            @Valid
            @ModelAttribute("userLoginDetails") UserLoginDetails userLoginDetails,
            BindingResult result,
            Model model,
            Authentication authentication
    ) {
        // Custom Authentication Token
        User user = userService.getByEmail(userLoginDetails.getUsername());
        log.info("\n\nUserService Retrieved User: [ {} ]\n\n", user);
        log.info("\n\nUserLoginDetails: {}\n\nMODEL: {} \n\nRESULTS: {}\n\nUSERS: {}\n\n",
                userLoginDetails.toString(),
                model.toString(),
                result.toString());
        if(result.hasErrors()){
            throw new NullArgumentException("Empty payload received. \n" + result.toString());
        }
        if (result.hasErrors()){
            return "login";
        }
        // TODO: validate user
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath().path("/login").toUriString());
        log.info(
                "\n\nRequestedMethod POST: UserLoginDetails form => {}\n\n" +
                        "Results: {}\n\nModel: {}\n\n",
                userLoginDetails.toString(),
                result.toString(),
                model.toString()
        );
        return "index";

    }

    /**
     * Login-error
     * @param throwable
     * @param model
     * @return error
     */
    @ExceptionHandler(Throwable.class)
    @RequestMapping(value="/login-error", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(
            final Throwable throwable,
            final Model model) {
        log.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error"; }

    /**
     * Redirect logout to login
     * @return login
     */
    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String logout(){
        return "/login";
    }

    /**
     * Redirect logout to login
     * @return index
     */
    @RequestMapping(path = "index", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String index(){
        return "index";
    }
}
