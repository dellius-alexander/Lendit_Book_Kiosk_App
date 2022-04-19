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
import org.thymeleaf.spring5.view.AbstractThymeleafView;

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
    protected final T payLoad = (T) new HashMap<String, Object>();

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
            model.mergeAttributes(hierarchicalCheck((T) model));
            // reset our security context
            SecurityContextHolder.getContext().setAuthentication(null);
            // reset our payLoad object
            payLoad.clear();
            /** Write the response headers for login */
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            log.info(model.toString());
            payLoad.put("userLoginDetails", new UserLoginDetails());
            // merge our attributes
            model.mergeAttributes(payLoad);
            log.info("\nPayload: {}\n", payLoad);
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
                            request.getAttribute("userLoginDetails"),
                            model.getAttribute("userLoginDetails"),
                            userLoginAttempt,
                            userLoginDetails
                    }
            ).get(0);
            ////////////////////////////////////////////////////////////////////////
            log.info("Searching for UserLoginDetails [uld]: {}",uld);
            // first search to see if user exists then we authenticate incoming client user
            loginUser = userService.getUser( uld.getUsername() );
            uld = new UserLoginDetails( loginUser );
            // send userLoginDetails to be authenticated by our  customAuthenticationProvider
            // and retrieve an authentication token
            userAuth = customAuthenticationProvider.authenticate(
                    uld
            );
            // assign all the newly validated objects as our payLoad
            payLoad.put("userLoginDetails",uld);
            payLoad.put("LoginUser",loginUser);
            payLoad.put("usernamePasswordAuthenticationToken",userAuth);
            payLoad.put("Book",new Book());
            ////////////////////////////////////////////////////////////////////////
            // Set our security context for authenticated user
            if(getPrincipal(request) == null ){
                throw new SecurityException(
                        "SecurityContextHolder is null. " +
                                "Use SecurityContextHolder.getContext().setAuthentication(token) " +
                                "to set the security context."
                );
            }
            ////////////////////////////////////////////////////////////////////////
            log.info("Model Attributes: {}",model);
            log.info("\nPayload: {}\n",payLoad);
            ////////////////////////////////////////////////////////////////////////
            // merge our attributes
            model.mergeAttributes(payLoad);
            ////////////////////////////////////////////////////////////////////////
            log.info("\nModel: {}\n", model);
            log.info("\nPayload: {}\n",payLoad);
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
            HttpServletRequest request,
            Model model,
            BindingResult result) {
        try {
            log.info("Model: {}",model);
            if(result.hasErrors()){
                throw new Exception("Resultsbody has errors. Check parameter variables: " + model);
            }
            // check the security context
            payLoad.put("usernamePasswordAuthenticationToken",getPrincipal(request));
            // set new book for searching
            payLoad.put("Book",new Book());
            ////////////////////////////////////////////////////////////////////////
            // merge our attributes
            model.mergeAttributes(payLoad);
            ////////////////////////////////////////////////////////////////////////
            log.info("Payload: {}",payLoad);
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
                            request.getAttribute("Book"),
                            model.getAttribute("Book"),
                            book,
                            payLoad.get("Book"), null
                    }
            ).get(0);
            ////////////////////////////////////////////////////////////////////////
            payLoad.put("Book",book);
            ////////////////////////////////////////////////////////////////////////
            log.info("\nFrom Request PathVariable Book: \n{}", book);
            log.info("\nModel Attributes: {}",model);
            log.info("\nPayload: {}\n",payLoad);
            if(result.hasErrors()){
                throw new Exception("Resultsbody has errors. Check parameter variable: " + book);
            }
            List<Book> bookListSearchResults = searchBook(book);
            // replace our book list with a fresh list
            payLoad.put("book_list",bookListSearchResults);
            // check the security context
            payLoad.put("usernamePasswordAuthenticationToken",getPrincipal(request));
            ///////////////////////////////////////////////////////////////////////
            // merge our attributes
            model.mergeAttributes(payLoad);
            ////////////////////////////////////////////////////////////////////////
            log.info("\nPayload: {}\n",payLoad);

        }
        catch (Exception e){
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
            @RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer,
            @ModelAttribute("book_list") @Valid List<Book> bookList,
            HttpServletRequest request,
            BindingResult result,
            Model model
    ) {
        try {
            log.info("Model: {}",model);
            if( referrer != null ) {
                payLoad.put("previousUrl", referrer);
            }
            if(result.hasErrors()){
                throw new Exception("Resultsbody has errors. Check parameter variable: " + (List<Book>)payLoad.get("book_list"));
            }
            ////////////////////////////////////////////////////////////////////////
            // hierarchical Check return <= [3,2,1]
            payLoad.put("book_list",hierarchicalCheck(
                    new Object[]{
                            model.getAttribute("book_list"),
                            bookList,
                            payLoad.get("book_list")
                    }
            ));
            ////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////
            // now use the authentication token to assign a principal/user to the security context holder
            // check the security context
            payLoad.put("usernamePasswordAuthenticationToken",getPrincipal(request));
            ///////////////////////////////////////////////////////////////////////
            // merge our attributes
            model.mergeAttributes(payLoad);
            ////////////////////////////////////////////////////////////////////////
            log.info("Payload: {}",payLoad);
        }
        catch (Exception e){
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
     * Helper function to fetch books by search term type, i.e. title, author, etc.
     * @param book the book object
     * @return list of books matching the search term(s)
     */
    public List<Book> searchBook(Book book){
        if (book.getTitle() != null) {
            return bookService.getBooksByTitle(book.getTitle());
        }
        if (book.getAuthors() != null){
            return bookService.getBooksByAuthor(book.getAuthors());
        }
        if (book.getGenres() != null){
            return bookService.getBooksByGenres(book.getGenres());
        }
        if (book.getDescription() != null){
            return bookService.getBooksByDescription(book.getDescription());
        }
        return null;
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
            SecurityContextHolder.getContext().setAuthentication( (Authentication) payLoad.get("usernamePasswordAuthenticationToken"));
            // now retrieve the auth token back from SecurityContextHolder to verify it is in place
            SecurityContext sc = SecurityContextHolder.getContext();
            // check the security context
            if (sc  == null){
                throw new SecurityException(
                        "SecurityContextHolder is null. " +
                                "Use SecurityContextHolder.getContext().setAuthentication(token) " +
                                "to set the security context."
                );
            }
            log.info("Security Context Holder is active: {}", sc.getAuthentication());
            HttpSession session = req.getSession(true);
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc.getAuthentication());
            session.setAttribute("verified",true);
            log.info("\nSuccessfully Authenticated: {}\n", payLoad.get("usernamePasswordAuthenticationToken"));
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
     * User only with same DataType Objects to find non-null object in list of objects.
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

    /**
     * Deconstructs Map objects and checks each part for null objects and return all
     * non-null objects into separate Map.
     * @param payLoad
     * @return {@literal  T extends Map<String, ?>}
     */
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


