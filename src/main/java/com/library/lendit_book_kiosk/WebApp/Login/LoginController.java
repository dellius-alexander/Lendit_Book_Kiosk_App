package com.library.lendit_book_kiosk.WebApp.Login;

import com.library.lendit_book_kiosk.Book.Book;
import com.library.lendit_book_kiosk.Book.BookService;
import com.library.lendit_book_kiosk.Security.Custom.CustomAuthenticationProvider;
import com.library.lendit_book_kiosk.Security.UserDetails.UserLoginDetails;
import com.library.lendit_book_kiosk.User.User;
import com.library.lendit_book_kiosk.User.UserService;
import org.apache.commons.lang.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller(value = "LoginController")
@RequestMapping( value = {"/"}, path = {"/"})
public class LoginController<T extends Map<String, Object>> implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    // payLoad should be used to manage DOM objects passed between server and client
    protected static Map<String, Object> payLoad =  new HashMap<>();

    @Autowired
    protected static UserService userService;
    @Autowired
    protected static BookService bookService;
    @Autowired
    protected static CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
    protected static UserLoginDetails userLoginDetails;
    @Autowired
    protected static Authentication userAuth;

    protected static User loginUser;

    /**
     * Login Web MVC Controller
     * @param userSve
     * @param bookSve
     * @param customAuthProvider
     * @param userLogDetails
     * @param userAuthentication
     */
    public LoginController(
            UserService userSve,
            BookService bookSve,
            CustomAuthenticationProvider customAuthProvider,
            UserLoginDetails userLogDetails,
            Authentication userAuthentication) {
        this.userService = userSve;
        this.bookService = bookSve;
        this.customAuthenticationProvider = customAuthProvider;
        this.userLoginDetails = userLogDetails;
        this.userAuth = userAuthentication;
    }



    /**
     * Initial login
     * @param model
     * @return login page
     */
    @GetMapping(
            value = {"login"})
    public String login(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        try{
            // reset our security context
            SecurityContextHolder.getContext().setAuthentication(null);
            // reset our payLoad
            payLoad.clear();
            /** Write the response headers */
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            log.info(model.toString());
            payLoad.put("userLoginDetails", new UserLoginDetails());
            // merge our attributes
            model.mergeAttributes(payLoad);
            log.info("\nPayload: {}\n",
                    payLoad);
        } catch (Exception e){
            SecurityContextHolder.getContext().setAuthentication(null);
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return "login";
    }


    /**
     * User login form verification
     * @param request
     * @param result
     * @param model
     * @return
     */
    @PostMapping(
            value = {"index"})
    public String verify(
            @ModelAttribute("userLoginDetails") @Valid UserLoginDetails userLoginAttempt,
            HttpServletRequest request,
            HttpServletResponse response,
            BindingResult result,
            Model model
    ) {
        try{
            if (result.hasErrors())
            {
                return "redirect:error";
            }
            log.info("\nModel Attributes: {}",
                    model.toString()
                    );
            ////////////////////////////////////////////////////////////////////////
            // hierarchical Check return <= [3,2,1]
            UserLoginDetails uld = (UserLoginDetails) hierarchicalCheck(
                    new Object[]{
                            (UserLoginDetails)request.getAttribute("userLoginDetails"),
                            (UserLoginDetails)model.getAttribute("userLoginDetails"),
                            userLoginAttempt,
                            userLoginDetails, null
                    }
            ).get(0);
            ////////////////////////////////////////////////////////////////////////
            log.info("Searching for UserLoginDetails [uld]: {}",uld);
            // first search to see if user exists then we authenticate incoming client user
            loginUser = userService.getUser( uld.getUsername() );
            userLoginDetails = new UserLoginDetails( loginUser );
            // send userLoginDetails to be authenticated by our  customAuthenticationProvider
            // and retrieve an authentication token
            userAuth = customAuthenticationProvider.authenticate(
                    userLoginDetails
            );
            // assign all the newly validated objects as our payLoad
            payLoad.put("userLoginDetails",userLoginDetails);
            payLoad.put("LoginUser",loginUser);
            payLoad.put("usernamePasswordAuthenticationToken",getPrincipal(request));
            payLoad.put("Book",new Book());
            ////////////////////////////////////////////////////////////////////////
            // hierarchical Check return <= [3,2,1]
            loginUser = (User) hierarchicalCheck(
                    new Object[]{
                            payLoad.get("LoginUser") ,
                            (UserLoginDetails)request.getAttribute("LoginUser"),
                            (UserLoginDetails)model.getAttribute("LoginUser"),
                            loginUser, null
                    }
            ).get(0);
            ////////////////////////////////////////////////////////////////////////
            payLoad.put("LoginUser",loginUser);
            ////////////////////////////////////////////////////////////////////////
            // hierarchical Check return <= [3,2,1]
            userAuth = (UsernamePasswordAuthenticationToken) hierarchicalCheck(
                    new Object[]{
                            payLoad.get("usernamePasswordAuthenticationToken"),
                            (UserLoginDetails)request.getAttribute("usernamePasswordAuthenticationToken"),
                            (UserLoginDetails)model.getAttribute("usernamePasswordAuthenticationToken"),
                            userAuth, null
                    }
            ).get(0);
            ////////////////////////////////////////////////////////////////////////
            payLoad.put("usernamePasswordAuthenticationToken", userAuth);
            ////////////////////////////////////////////////////////////////////////
            // hierarchical Check return <= [3,2,1]
            userLoginDetails = (UserLoginDetails) hierarchicalCheck(
                    new Object[]{
                            payLoad.get("userLoginDetails"),
                            (UserLoginDetails)request.getAttribute("userLoginDetails"),
                            (UserLoginDetails)model.getAttribute("userLoginDetails"),
                            userLoginDetails, null
                    }
            ).get(0);
            ////////////////////////////////////////////////////////////////////////
            payLoad.put("userLoginDetails",userLoginDetails);
            log.info("Model Attributes: {}",model);
            ////////////////////////////////////////////////////////////////////////
            // merge our attributes
            model.mergeAttributes(payLoad);
            SecurityContextHolder.getContext().setAuthentication((UsernamePasswordAuthenticationToken)payLoad.get("usernamePasswordAuthenticationToken"));

            ////////////////////////////////////////////////////////////////////////
            log.info("\nModel: {}\n", model);
            log.info("\nPayload: {}\n",payLoad.values().stream().collect(Collectors.toList()));
        } catch (Exception e) {
            log.error("Failure in autoLogin. Redirection back to /login. \n{}", e.getMessage());
            e.printStackTrace();
            return "redirect:login";
        }
        return "index";
    }
    /**
     * Redirect logout to login
     * @return index
     */
    @GetMapping(
            value = {"index"})
    public String index(
            @RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer,
            Model model,
            BindingResult result) {
        try {
            log.info("Model: {}",model);
            if( referrer != null ) {
                model.addAttribute("previousUrl", referrer);
            }
//            if(result.hasErrors()){
//                throw new Exception("Resultsbody has errors. Check parameter variables: " + model);
//            }
            ////////////////////////////////////////////////////////////////////////
            // hierarchical Check return <= [3,2,1]
            loginUser = (User) hierarchicalCheck(
                    new Object[]{
                            payLoad.get("LoginUser") ,
                            (UserLoginDetails)model.getAttribute("LoginUser"),
                            loginUser, null
                    }
            ).get(0);
            ////////////////////////////////////////////////////////////////////////
            payLoad.put("LoginUser",loginUser);
            ////////////////////////////////////////////////////////////////////////
            // hierarchical Check return <= [3,2,1]
            userAuth = (UsernamePasswordAuthenticationToken) hierarchicalCheck(
                    new Object[]{
                            payLoad.get("usernamePasswordAuthenticationToken"),
                            (UserLoginDetails)model.getAttribute("usernamePasswordAuthenticationToken"),
                            userAuth, null
                    }
            ).get(0);
            ////////////////////////////////////////////////////////////////////////
            payLoad.put("usernamePasswordAuthenticationToken", userAuth);
            ////////////////////////////////////////////////////////////////////////
            // hierarchical Check return <= [3,2,1]
            userLoginDetails = (UserLoginDetails) hierarchicalCheck(
                    new Object[]{
                            payLoad.get("userLoginDetails"),
                            (UserLoginDetails)model.getAttribute("userLoginDetails"),
                            userLoginDetails, null
                    }
            ).get(0);
            ////////////////////////////////////////////////////////////////////////
            payLoad.put("userLoginDetails",userLoginDetails);
            log.info("Model Attributes: {}",model);
            ////////////////////////////////////////////////////////////////////////
            payLoad.put("Book",new Book());
            ////////////////////////////////////////////////////////////////////////
            // merge our attributes
            model.mergeAttributes(payLoad);
            SecurityContextHolder.getContext().setAuthentication((UsernamePasswordAuthenticationToken)payLoad.get("usernamePasswordAuthenticationToken"));
            ////////////////////////////////////////////////////////////////////////
            log.info("Payload: {}",payLoad.values().stream().collect(Collectors.toList()));
        }
        catch (Exception e){
            log.error("\nFailure to load: {}\n", e.getMessage());
            return "login";
        }
        return "index";
    }

    /**
     * Search book by title @{/searchbookby/{book}}
     * @param book
     * @param result
     * @param model
     * @return
     */
    @GetMapping(
            value = {"searchbookby/{book}"})
    public String searchbookbyTitle(
            @ModelAttribute("Book") @Valid Book book,
            HttpServletRequest request,
            HttpServletResponse response,
            BindingResult result,
            Model model
    ) {
        try {// clean our payload
//            payLoad.replaceAll((BiFunction<? super String, ? super Object, ?>) hierarchicalCheck(payLoad));
            ////////////////////////////////////////////////////////////////////////
            // hierarchical Check return <= [3,2,1]
            book = (Book) hierarchicalCheck(
                    new Object[]{
                            (Book)request.getAttribute("Book"),
                            (Book)model.getAttribute("Book"),
                            book,
                            (Book)payLoad.get("Book"), null
                    }
            ).get(0);
            ////////////////////////////////////////////////////////////////////////
            payLoad.put("Book",book);
            ////////////////////////////////////////////////////////////////////////
            log.info("\nFrom Request PathVariable Book: \n{}", book);
            log.info("\nModel Attributes: {}",model);
            log.info("\nPayload: {}\n",payLoad.values().stream().collect(Collectors.toList()));
            if(result.hasErrors()){
                throw new Exception("Resultsbody has errors. Check parameter variable: " + book);
            }
            List<Book> bookListSearchResults = bookService.getBooksByTitle(book.getTitle());
            // replace our book list with a fresh list
            payLoad.put("book_list",bookListSearchResults);
            ///////////////////////////////////////////////////////////////////////
            // merge our attributes
            model.mergeAttributes(payLoad);
            SecurityContextHolder.getContext().setAuthentication((UsernamePasswordAuthenticationToken)payLoad.get("usernamePasswordAuthenticationToken"));
//
            ////////////////////////////////////////////////////////////////////////
            log.info("\nPayload: => KeySet:{}\nValues: {}\n",payLoad.keySet(), payLoad.values().stream().collect(Collectors.toList()));

        }
        catch (Exception e){
//            SecurityContextHolder.getContext().setAuthentication(null);
            log.error("\nFailure to load: {}\n", e.getMessage());
            return "login";
        }
        return "book_result";
    }
    /**
     * Return book_results
     * @param result
     * @param model
     * @return
     */
    @GetMapping(
            value = {"book_result"})
    public String book_result(
            @ModelAttribute("book_list") @Valid List<Book> bookList,
            HttpServletRequest request,
            HttpServletResponse response,
            BindingResult result,
            Model model
    ) {
        try {
            log.info("\nModel: \n{}", model.toString());
            if(result.hasErrors()){
                throw new Exception("Resultsbody has errors. Check parameter variable: " + (List<Book>)payLoad.get("book_list"));
            }
            ////////////////////////////////////////////////////////////////////////
            // hierarchical Check return <= [3,2,1]
            bookList = (List<Book>) hierarchicalCheck(
                    new Object[]{
                            (List<Book>)request.getAttribute("book_list"),
                            (List<Book>)model.getAttribute("book_list"),
                            bookList,
                            (List<Book>)payLoad.get("book_list")
                    }
            );
            ////////////////////////////////////////////////////////////////////////
            payLoad.put("book_list",bookList);
            ////////////////////////////////////////////////////////////////////////
            // now use the authentication token to assign a principal/user to the security context holder
            SecurityContextHolder.getContext().setAuthentication(
                    (UsernamePasswordAuthenticationToken)payLoad.get("usernamePasswordAuthenticationToken")
            );
            ///////////////////////////////////////////////////////////////////////
            // merge our attributes
            model.mergeAttributes(payLoad);
            model.addAttribute("book_list",bookList);
            SecurityContextHolder.getContext().setAuthentication((UsernamePasswordAuthenticationToken)payLoad.get("usernamePasswordAuthenticationToken"));
//            request.startAsync(request,response);
            ////////////////////////////////////////////////////////////////////////
            log.info("Payload: {}",payLoad.values().stream().collect(Collectors.toList()));
        }
        catch (Exception e){
//            SecurityContextHolder.getContext().setAuthentication(null);
            log.error("\nFailure to load: {}\n", e.getMessage());
            return "login";
        }
        return "book_result";
    }
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

    //////////////////////////////////////////////////////////////////////
    /**
     * Sets the
     * Gets the Authentication token from the SecurityContextHolder.
     * @param req HttpServletRequest
     * @return the Authentication token of current authenticated user
     */
    protected Authentication getPrincipal(HttpServletRequest req) {
        try {
            // set the SecurityContextHolder auth token
            SecurityContextHolder.getContext().setAuthentication(userAuth);
            // now retrieve the auth token back from SecurityContextHolder to verify it is in place
            SecurityContext sc = SecurityContextHolder.getContext();
            userAuth = sc.getAuthentication();
            loginUser = userService.getUser(userAuth.getName());
//            userLoginDetails = new UserLoginDetails(loginUser);
            if (sc  == null){
                throw new SecurityException(
                        "SecurityContextHolder is null. " +
                                "Use SecurityContextHolder.getContext().setAuthentication(token) " +
                                "to set the security context."
                );
            }
            log.info("Security Context Holder is active: {}", sc);
            HttpSession session = req.getSession(true);
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
            session.setAttribute("verified",true);
            log.info("\nSuccessfully Authenticated: {}\n", userAuth.toString());
//            response.sendRedirect(request.getRequestURI().split("")[0]);
        }
        catch (Exception e){
            SecurityContextHolder.getContext().setAuthentication(null);
            log.error("Failure to load /index", e.getMessage());
            throw new NullArgumentException("Authentication token is null or Anonymous.");
        }
        return SecurityContextHolder.getContext().getAuthentication();
    }
    //////////////////////////////////////////////////////////////////////
    /**
     * Checks if an object or list of objects are null and returns the first non null object.
     * @param object a single Object, a list or array
     * @return the first Not_Null object
     */
    private  List<? extends Object> hierarchicalCheck(Object[] object){
        log.info("Object[]: {}",object);
        List<Object> list = new ArrayList<>();
        // check if object if an array of objects
        if (object instanceof Object[] && ((Object[]) object).length > 1){
            for (Object o : (Object[]) object ) {
                log.info("ForEach[]: {}", o);
                if (o == null){continue;}
                log.info("Object Found: {}", o);
                list.addAll(List.of(hierarchicalCheck(o)));
            }
        }
        // return the first Not_Null object we find
        else if(object != null){
            log.info("Not_Null[] Object Found: {}",object);
            return List.of(object);
        }
        else {
            return null;
        }
        return list;
    }
    //////////////////////////////////////////////////////////////////////
    /**
     * Checks if an object is null and returns the Not_Null object.
     * @param object a single Object
     * @return the first Not_Null object
     */
    private  Object hierarchicalCheck(Object object){
        log.info("Object: {}",object);
        if (object instanceof Object[] && ((Object[]) object).length > 1){
            log.info("Found Object[] list: {}",object);
            return hierarchicalCheck((Object[])  object);
        }
        else if(object != null){
            log.info("Not_Null Object Found: {}",object);
            return object;
        }
        return null;
    }
    private T  hierarchicalCheck(T payLoad){
        log.info("Payload (T): {}",payLoad.values().stream().collect(Collectors.toList()));
        int cnt=0;
        if (  payLoad.size() > 0 ){
            Set set = payLoad.entrySet();  // convert to set so we can traverse Set
            Iterator it = set.iterator();  // get iterator of Set to iterate over each set
            while (it.hasNext()){ // check if we have any objects in the set
                // get an entry set if we have objects in the set
                T.Entry entry = (T.Entry) it.next();
                log.info("\nObject (T) {}: =>\nKey: {}\nValue: {}\n", cnt, entry.getKey(), entry.getValue());
                // replace the old object with new object if its not null
                payLoad.replace( entry.getKey().toString(),entry.getValue(),hierarchicalCheck( entry.getValue()));
            }
        }
        return payLoad;
    }
//    /////////////////////////////////////////////////////////////////////////////////
//    public static void main(String[] args) {
//        try {
//            SearchBook sb = new SearchBook("title","author","course");
//            LoginController lg = new LoginController(
//                    userService,
//                    bookService,
//                    customAuthenticationProvider,
//                    userLoginDetails,
//                    userAuth);
//            lg.payLoad.put("objects", new Object[]{
//                    "Hello World 1",
//                    userService,
//                    sb,
//                    bookService,
//                    customAuthenticationProvider,
//                    userLoginDetails,
//                    userAuth,
//                    "Hello World 2"});
//            log.info("Dirty List: {}", lg.payLoad.values().stream().collect(Collectors.toList()));
//            lg.payLoad = lg.hierarchicalCheck(lg.payLoad);
//            log.info("Cleaned List: {}", lg.payLoad.values().stream().collect(Collectors.toList()));
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            e.printStackTrace();
//        }
//    }
}


