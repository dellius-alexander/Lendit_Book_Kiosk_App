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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.Serializable;
import java.net.URI;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller(value = "/")
public class LoginController implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public LoginController() { }

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
            HttpServletRequest req
    ) {
        try{
            if (result.hasErrors()) {
                return "login";
            }
            log.info("\n\nUserLoginDetails: {}\n\nMODEL: {} \n\nRESULTS: {}\n\nUSERS: {}\n\nAUTHENTICATION: {}\n\n",
                    userLoginDetails.toString(),
                    model.toString(),
                    result.toString());
            // get the user
            User user = userService.getByEmail(userLoginDetails.getUsername());
            // create a principal
            UserDetails principal = (UserDetails) new UserLoginDetails(user);
            // now a custom authentication token
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
                            principal.getUsername(),
                            user.getPassword(),
                            principal.getAuthorities()
            );

            Authentication auth = customAuthenticationProvider.authenticate(authReq);
            SecurityContextHolder.getContext().setAuthentication(auth);
    //        SecurityContext sc = SecurityContextHolder.getContext();
    //        sc.setAuthentication(auth);
    //        HttpSession session = req.getSession(true);
    //        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
            log.info("\n\nUserService Retrieved User: [ {} ]\n\n", user);
        } catch (Exception e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            log.error("Failure in autoLogin", e);
        }
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
