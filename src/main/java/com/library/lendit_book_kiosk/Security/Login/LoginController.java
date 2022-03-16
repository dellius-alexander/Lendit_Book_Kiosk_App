package com.library.lendit_book_kiosk.Security.Login;

import com.library.lendit_book_kiosk.Security.Custom.CustomAuthenticationProvider;
import com.library.lendit_book_kiosk.User.User;
import com.library.lendit_book_kiosk.Security.UserDetails.UserLoginDetails;
import com.library.lendit_book_kiosk.User.UserService;
//import org.apache.commons.lang.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.Serializable;
import java.net.URI;

@Controller(value = "/")
public class LoginController implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final UserService userService;

    @Autowired
    public LoginController(
            CustomAuthenticationProvider customAuthenticationProvider,
            UserService userService) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.userService = userService;
    }

    /**
     * Initial login
     *
     * @return login page
     */
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(
            Model model
    ) {
//        User user = new User();
//        model.addAttribute("user", user);
        model.addAttribute("userLoginDetails", new UserLoginDetails());
//        log.info("\n\nUser: {}\n\n",user.toString());
        log.info("\nUserLoginDetails form: {}\n", model.toString());
        return "login";
    }

    /**
     * User login form
     *
     * @param userLoginDetails
     * @param result           the results
     * @return
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
//    @Secured({"ROLE_*","ROLE_GUEST","ROLE_USER","ROLE_STUDENT","ROLE_FACULTY","ROLE_ADMIN","ROLE_SUPERUSER"})
    public String verify(
            @Valid
            @ModelAttribute("userLoginDetails") UserLoginDetails userLoginDetails,
            BindingResult result,
            Model model,
            HttpServletRequest request
    ) {
        log.info("\n\nREQUEST INFO: \n\tURI = {}, \n\tCONTEXTPATH = {}"+
                "\n\tSERVLETPATH = {}\n\tPATHINFO = {}\n\tQUERYSTR = {}",
                request.getRequestURI(),
                request.getContextPath(),
                request.getServletPath(),
                request.getPathInfo(),
                request.getQueryString());
        log.info("USERLOGINDETAILS Retrieved: {}", userLoginDetails);
        // Custom Authentication Token
        User user = userService.getByEmail(userLoginDetails.getUsername());
        log.info("\n\nUserService Retrieved User: [ {} ]\n\n", user);
        log.info("\n\nUserLoginDetails: {}\n\nMODEL: {} \n\nRESULTS: {}\n\nUSERS: {}\n\nAUTHENTICATION: {}\n\n",
                userLoginDetails,
                model.toString(),
                result.toString());
//        if (result.hasErrors()) {
//            throw new NullArgumentException("Empty payload received. \n" + result);
//        }
        if (result.hasErrors()) {
            return "login";
        }
        // TODO: validate user
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath().path("/verify").toUriString());

        UserLoginDetails uld = new UserLoginDetails(user);
        model.addAttribute("user", uld);

        log.info(
                "\n\nURI: {}\n\nRequestedMethod POST: \nUserLoginDetails form => {}\n\n" +
                        "Results: {}\n\nModel: {}\n\nAUTHENTICATION: {}\n\n",
                uri,
                userLoginDetails,
                result,
                model);
        return "index";
    }

    /**
     * Login-error
     * @param throwable
     * @param model
     * @return error
     */
    @ExceptionHandler(Throwable.class)
//    @RequestMapping(value="/login-error", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(
            final Throwable throwable,
            final Model model) {
        log.error("Exception during execution of LendIT Book Kiosk application", throwable);
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

    private Authentication getPrincipal() {
        String userName = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(auth instanceof AnonymousAuthenticationToken)){
            log.info("\nAnonymousAuthenticationToken Exception: TOKEN => {}\n",auth.toString());
        }
//        if (principal instanceof  UserDetails){
//            userName = ((UserDetails)principal).getUsername();
//        } else {
//          userName = principal.toString();
//        }

        return auth;
    }

}
