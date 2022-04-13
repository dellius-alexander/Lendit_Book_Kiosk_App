package com.library.lendit_book_kiosk.WebApp.Login;

import com.library.lendit_book_kiosk.Book.Book;
import com.library.lendit_book_kiosk.Security.Custom.CustomAuthenticationProvider;
import com.library.lendit_book_kiosk.Security.UserDetails.UserLoginDetails;
//import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.Serializable;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller(value = "LoginController")
public class LoginController implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

//    @Autowired
//    private UserService userService;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    protected static UsernamePasswordAuthenticationToken auth;

    public LoginController() { }

    /**
     * Initial login
     *
     * @return login page
     */
    @RequestMapping(path = "login", method = RequestMethod.GET)
    public String login(
            Model model
    ) {
        model.addAttribute("userLoginDetails", new UserLoginDetails());
        log.info("\nUserLoginDetails form: {}\n", model);
        return "login";
    }
    /**
     * User login form verification
     * @param userLoginDetails
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(
            value = {"index"},
            path = {"index"},
            method = RequestMethod.POST)
    public String verify(
            @Valid
            @ModelAttribute("userLoginDetails") UserLoginDetails userLoginDetails,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model)
    {
        try{
            if (result.hasErrors())
            {
                return "error";
            }

            log.info("\n\nUserLoginDetails: {}" +
                    "\n\nRESULTS: {}\n\n",
                    userLoginDetails.toString(),
                    result.toString());

            // send userLoginDetails to be authenticated by our  customAuthenticationProvider
            // and retrieve an authentication token
            this.auth = customAuthenticationProvider.authenticate(
                    userLoginDetails
            );
            // set the SecurityContextHolder auth token
            SecurityContextHolder.getContext().setAuthentication(this.auth);
            // now retrieve the auth token back from SecurityContextHolder to verify it is in place
            SecurityContext sc = SecurityContextHolder.getContext();
            model.addAttribute("book",new Book());
            if (sc  == null){
                throw new SecurityException(
                        "SecurityContextHolder is null. " +
                                "Use SecurityContextHolder.getContext().setAuthentication(token) " +
                                "to set the security context."
                );
            }
            log.info("Security Context Holder is active: {}", sc);

        } catch (Exception e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            log.error("Failure in autoLogin. Redirection back to /login. \n{}", e.getMessage());
            return "redirect:login";
        }
        return "student";
    }
    /**
     * Login-error
     * @param throwable
     * @param model
     * @return error
     */
    @ExceptionHandler(Throwable.class)
    @RequestMapping(value="error", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(
            final Throwable throwable,
            final Model model) {
        log.error("Exception during execution of LendIT Book Kiosk application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
    /**
     * Redirect logout to login
     * @return login
     */
    @RequestMapping(path = "logout", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String logout(){
        return "redirect:login";
    }
    /**
     * Redirect logout to login
     * @return index
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            log.info("Authentication: {}",getPrincipal(request,response));
        }
        catch (Exception e){
            SecurityContextHolder.getContext().setAuthentication(null);
            log.error("Failure to load: \n{}\n{}", request.getPathInfo(), e.getMessage());
            return "redirect:login";
        }
        return "index";
    }
//
//    @RequestMapping(
//            path = {"student"},
//            value = {"student"},
//            method = {RequestMethod.GET})
//    public String getStudentPage(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ){
//        try {
//            log.info("Authenticated User: {}",getPrincipal(request, response));
//        } catch (Exception e) {
//            SecurityContextHolder.getContext().setAuthentication(null);
//            log.error(e.getMessage());
//        }
//        return "student";
//    }
    
    //////////////////////////////////////////////////////////////////////
    /**
     * Sets the
     * Gets the Authentication token from the SecurityContextHolder.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return the Authentication token of current authenticated user
     */
    protected Authentication getPrincipal(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            // set the SecurityContextHolder auth token
//            SecurityContextHolder.getContext().setAuthentication(this.auth);
            // now retrieve the auth token back from SecurityContextHolder
            SecurityContext sc = SecurityContextHolder.getContext();
            HttpSession session = request.getSession(true);
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
            session.setAttribute("verified",true);
            response.sendRedirect(request.getRequestURI().split(";")[0]);
            log.info("\nSuccessfully Authenticated: {}\n", auth.toString());
//            chain.doFilter(request,response);
        }
        catch (Exception e){
            SecurityContextHolder.getContext().setAuthentication(null);
            log.error("Failure to load /index", e.getMessage());
            throw new NullArgumentException("Authentication token is null or Anonymous.");
        }
        return this.auth;
    }
}
