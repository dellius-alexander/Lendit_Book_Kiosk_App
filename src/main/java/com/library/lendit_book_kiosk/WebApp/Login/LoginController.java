package com.library.lendit_book_kiosk.WebApp.Login;

import com.library.lendit_book_kiosk.Book.Book;
import com.library.lendit_book_kiosk.Book.BookService;
import com.library.lendit_book_kiosk.Security.Custom.CustomAuthenticationProvider;
import com.library.lendit_book_kiosk.Security.UserDetails.UserLoginDetails;
//import org.apache.commons.lang.NullArgumentException;
import com.library.lendit_book_kiosk.User.User;
import com.library.lendit_book_kiosk.User.UserService;
import groovy.lang.GString;
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
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.io.Serializable;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller(value = "LoginController")
@RequestMapping( value = {"/"}, path = {"/"})
public class LoginController implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    protected static UsernamePasswordAuthenticationToken auth;

    public LoginController() { }

    /**
     * Initial login
     *
     * @return login page
     */
    @GetMapping(
            value = {"login"})
    public String login(
            Model model
    ) {
        try{// clear the security context holder
//            SecurityContextHolder.getContext().setAuthentication(null);
            model.addAttribute("userLoginDetails",
                    new UserLoginDetails());
            log.info(model.toString());
        } catch (Exception e){
//            SecurityContextHolder.getContext().setAuthentication(null);
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return "login";
    }
    /**
     * User login form verification
     * @param userLoginDetails
     * @param result
     * @param request
     * @return
     */
    @PostMapping(
            value = {"index"})
    public String verify(
            @ModelAttribute("userLoginDetails") @Valid UserLoginDetails userLoginDetails,
            HttpServletRequest request,
            HttpServletResponse response,
            BindingResult result,
            Model model)
    {
        try{
            if (result.hasErrors())
            {
                return "error";
            }
            log.info("Searching for User: {}",userLoginDetails.toString());
            // first search to see if user exists then we authenticate incoming client user
            User user = userService.getUser(
                    userLoginDetails.getUsername()
            );
            userLoginDetails = new UserLoginDetails(user);

            // send userLoginDetails to be authenticated by our  customAuthenticationProvider
            // and retrieve an authentication token
            this.auth = customAuthenticationProvider.authenticate(
                    userLoginDetails
            );
            model.addAttribute("User",user);
            model.addAttribute("userLoginDetails", userLoginDetails);
            model.addAttribute("title", "");

            // now use the authentication token to assign a principal/user to the security context holder
            log.info("\nUser Found: {},\nAuthentication Token Assigned to Session: {}\n",
                    user, getPrincipal(request,response));

        } catch (Exception e) {
//            SecurityContextHolder.getContext().setAuthentication(null);
            log.error("Failure in autoLogin. Redirection back to /login. \n{}", e.getMessage());
            e.printStackTrace();
            return "login";
        }
        return "index";
    }


//    /**
//     * Path to Student portal
//     * @return /student
//     */
//    @GetMapping(
//            value = {"student"})
//    public String student(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            Model model
//    ) {
//        try {
//            // now use the authentication token to assign a principal/user to the security context holder
////            log.info("Authentication Token Assigned to Session: [{}]", getPrincipal(request,response));
//            model.addAttribute("User",userService.getUser(this.auth.getName()));
//            model.addAttribute("title", "");
//        }
//        catch (Exception e){
////            SecurityContextHolder.getContext().setAuthentication(null);
//            log.error("Failure to load: \n{}\n{}", request.getPathInfo(), e.getMessage());
//            return "login";
//        }
//        return "fragments/student";
//    }
    /**
     * Login-error
     * @param throwable
     * @param model
     * @return error
     */
    @ExceptionHandler(Throwable.class)
    @GetMapping(
            value = {"error"})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(
            final Throwable throwable,
            final Model model) {
        log.error("Exception during execution of LendIT Book Kiosk application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        throwable.printStackTrace();
        return "error";
    }
    /**
     * Redirect logout to login
     * @return login
     */
    @GetMapping(
            value = {"logout"})
    @ResponseStatus(HttpStatus.OK)
    public String logout(){
        return "redirect:login";
    }
    /**
     * Redirect logout to login
     * @return index
     */
    @GetMapping(
            value = {"index"})
    public String index(
            @ModelAttribute("userLoginDetails") UserLoginDetails userLoginDetails,
            @ModelAttribute("User") User user,
            @ModelAttribute("title") String title,
            HttpServletRequest request,
            HttpServletResponse response,
            BindingResult result,
            Model model) {
        try {
            if (user == null){
                throw new NullArgumentException("User attribute is null...");
            }
            log.info("UserLoginDetails: {}",userLoginDetails.toString());
            if (result.hasErrors())
            {
                return "error";
            }
            // first search to see if user exists then we authenticate incoming client user
            user = userService.getUser(
                     this.auth.getPrincipal().toString()
            );
            userLoginDetails = new UserLoginDetails(user);
            log.info("\nUser attribute contents: {}, \nUser Login Details: {}, \nBook Details: {}",
                    user,userLoginDetails,title);
            userLoginDetails = new UserLoginDetails(user);
            model.addAttribute("User",user);
            model.addAttribute("userLoginDetails", userLoginDetails);
            model.addAttribute("title", title);
            // now use the authentication token to assign a principal/user to the security context holder
            SecurityContextHolder.getContext().setAuthentication(this.auth);
            log.info("\nSession User: {}",
                    user);
            log.info("\nCurrent Session: {},\nResponse Status: {}\n",
                    request.getSession(),
                    response.getStatus());
        }
        catch (Exception e){
//            SecurityContextHolder.getContext().setAuthentication(null);
            log.error("Failure to load: \n{}\n{}", request.getPathInfo(), e.getMessage());
            return "login";
        }
        return "index";
    }

    @RequestMapping(
            value = {"search_results/{title}", "search_results/"},
            method = {RequestMethod.GET})
    public String searchBooks(
            @RequestParam(value = "title") String title,
            Model model,
            BindingResult result
    ){
        try {
//            if (result.hasErrors())
//            {
//                return "error";
//            }
            log.info("Model attributes: {}", model.toString());
            log.info("Searching for Book Title: {}",title);
            model.addAttribute("book_list", bookService.getBooksByTitle(title));
            // now use the authentication token to assign a principal/user to the security context holder
//            log.info("\nUser Found: {},\nAuthentication Token Assigned to Session: {}\n",
//                    user, getPrincipal(request,response));
//            /// now use the authentication token to assign a principal/user to the security context holder
            SecurityContextHolder.getContext().setAuthentication(this.auth);
            log.info("\nAuthentication Token: {}\n",SecurityContextHolder.getContext().getAuthentication().toString());
            return "search_results";
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return "search_results";
    }
//
//    @RequestMapping(
//            value = {"search_results"},
//            method = {RequestMethod.GET})
//    public String getStudentPage(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            Model model,
//            BindingResult result
//    ){
//        try {
//            log.info("Authenticated User: {}",getPrincipal(request, response));
//        } catch (Exception e) {
//            SecurityContextHolder.getContext().setAuthentication(null);
//            log.error(e.getMessage());
//        }
//        return "search_results";
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
//            SecurityContextHolder.getContext().setAuthentication(null);
            // set the SecurityContextHolder auth token
            SecurityContextHolder.getContext().setAuthentication(this.auth);
            // now retrieve the auth token back from SecurityContextHolder to verify it is in place
            SecurityContext sc = SecurityContextHolder.getContext();
            if (sc  == null){
                throw new SecurityException(
                        "SecurityContextHolder is null. " +
                                "Use SecurityContextHolder.getContext().setAuthentication(token) " +
                                "to set the security context."
                );
            }
            log.info("Security Context Holder is active: {}", sc);
            HttpSession session = request.getSession(true);
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
            session.setAttribute("verified",true);
            response.sendRedirect(request.getRequestURI().split(";")[0]);
            log.info("\nSuccessfully Authenticated: {}\n", this.auth.toString());
//            chain.doFilter(request,response);
        }
        catch (Exception e){
            SecurityContextHolder.getContext().setAuthentication(null);
            log.error("Failure to load /index", e.getMessage());
            throw new NullArgumentException("Authentication token is null or Anonymous.");
        }
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
