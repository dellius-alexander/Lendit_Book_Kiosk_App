package com.library.lendit_book_kiosk.WebApp.Login;

import com.library.lendit_book_kiosk.Book.Book;
import com.library.lendit_book_kiosk.Book.BookSelection;
import com.library.lendit_book_kiosk.Book.BookService;
import com.library.lendit_book_kiosk.Fines.Fines;
import com.library.lendit_book_kiosk.Security.Custom.CustomAuthenticationProvider;
import com.library.lendit_book_kiosk.Security.UserDetails.UserLoginDetails;
import com.library.lendit_book_kiosk.Utility.HierarchicalCheck;
import com.library.lendit_book_kiosk.User.User;
import com.library.lendit_book_kiosk.User.UserService;
import org.apache.commons.lang.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    protected  T payLoad = (T) new HashMap<String, Object>();
    // used to clean the payLoad of null objects
    private HierarchicalCheck hck = new HierarchicalCheck();
    // Resources
    protected static UserService userService;
    protected static BookService bookService;
    protected static CustomAuthenticationProvider customAuthenticationProvider;
    protected static UserLoginDetails userLoginDetails;
    protected static Authentication userAuth;
    // keeps user info in session
    protected static User loginUser;
    protected static Fines fines;
    //////////////////////////////////////////////////////////////////////
    /**
     * Login Web MVC Controller
     * @param userSve
     * @param bookSve
     * @param customAuthProvider
     * @param userLogDetails
     * @param userAuthentication
     */
    @Autowired
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
    //////////////////////////////////////////////////////////////////////
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
            // clear out payLoad at login
            // reset our payLoad object
            payLoad.clear();
            // cleanse the model of null objects
            payLoad.putAll(hck.hierarchicalCheck(model.asMap()));
            // reset our security context
            SecurityContextHolder.getContext().setAuthentication(null);
            /*************************************************************
             * https://datatracker.ietf.org/doc/html/rfc7234#section-5.4
             * ***********************************************************
             * Write the response headers for login. The "Pragma" header
             * field allows backwards compatibility with HTTP/1.0 caches,
             * so that clients can specify a "no-cache" request that they
             * will understand (as Cache-Control was not defined until
             * HTTP/1.1).  When the Cache-Control header field is also
             * present and understood in a request, Pragma is ignored.
             ************************************************************/
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Private","no-cache");
            response.setDateHeader("Expires", 0); // no expiration
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
    //////////////////////////////////////////////////////////////////////
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
            LoginController.userLoginDetails = (UserLoginDetails) hck.hierarchicalCheck(
                    new Object[]{
                            request.getAttribute("userLoginDetails"),
                            model.getAttribute("userLoginDetails"),
                            userLoginAttempt
                    }
            ).get(0);
            ////////////////////////////////////////////////////////////////////////
            log.info("Searching for UserLoginDetails: {}",this.userLoginDetails);
            // first search to see if user exists then we authenticate incoming client user
            LoginController.loginUser = LoginController.userService.getUser(LoginController.userLoginDetails.getUsername() );
            LoginController.userLoginDetails = new UserLoginDetails( LoginController.loginUser );
            // send userLoginDetails to be authenticated by our  customAuthenticationProvider
            // and retrieve an authentication token
            LoginController.userAuth = LoginController.customAuthenticationProvider.authenticate(
                    LoginController.userLoginDetails
            );
            ////////////////////////////////////////////////////////////////////////
            // assign all the newly validated objects as our payLoad
            payLoad.put("userLoginDetails",LoginController.userLoginDetails);
            payLoad.put("LoginUser",LoginController.loginUser);
            payLoad.put("usernamePasswordAuthenticationToken",LoginController.userAuth);
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
    //////////////////////////////////////////////////////////////////////
    /**
     * Redirect logout to login
     * @return index
     */
    @GetMapping(
            value = {"index"})
    public String index(
            HttpServletRequest request,
            Book book,
            BindingResult result,
            Model model
            ) {
        try {
            log.info("Model: {}",model);
            if(result.hasErrors()){
                throw new Exception("Resultsbody has errors. Check parameter variables: " + model);
            }
            // check the security context
            payLoad.put("usernamePasswordAuthenticationToken",getPrincipal(request));
            // set new book for searching
            payLoad.put("Book", book);
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
    //////////////////////////////////////////////////////////////////////
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
            BindingResult result,
            Model model
    ) {
        try {
            ////////////////////////////////////////////////////////////////////////
            // hierarchical Check return <= [3,2,1]
            book = (Book) hck.hierarchicalCheck(
                    new Object[]{
                            request.getAttribute("Book"),
                            model.getAttribute("Book"),
                            book,
                            payLoad.get("Book")
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
            log.info(bookListSearchResults.stream().collect(Collectors.toList()).toString());
            List<BookSelection> selections = new ArrayList<>();
            for(Book b : bookListSearchResults)
            {
                selections.add(new BookSelection(b));
            }
            log.info(selections.stream().collect(Collectors.toList()).toString());
            // replace our book list with a fresh list
//            payLoad.put("book_list", bookListSearchResults);
            payLoad.put("selections", selections);
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
    //////////////////////////////////////////////////////////////////////
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
            @ModelAttribute("selections") @Valid List<BookSelection> bookList,
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
                throw new Exception("Resultsbody has errors. Check parameter variable: " +
                        (List<BookSelection>)payLoad.get("selections"));
            }
            ////////////////////////////////////////////////////////////////////////
            // hierarchical Check return <= [3,2,1]
            payLoad.put("selections", (List<BookSelection>) hck.hierarchicalCheck(
                    new Object[]{
                            model.getAttribute("selections"),
                            bookList,
                            payLoad.get("selections")
                    }
            ));
            ////////////////////////////////////////////////////////////////////////
            payLoad.put("Selection",new BookSelection());
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
    //////////////////////////////////////////////////////////////////////
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
            return  bookService.getBooksByTitle(book.getTitle());
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
    @PostMapping(
            value = "selections/{selections}"
    )
    public String postSelections(
             @ModelAttribute("selections") @Valid @RequestParam List<BookSelection> selections,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model,
            BindingResult result){
        try{
            log.info("\nResponse Header: {}\n",response.getHeaderNames().stream().collect(Collectors.toList()));
            selections = hck.hierarchicalCheck(
                    new Object[]{
                            request.getAttribute("selections"),
                            model.getAttribute("selections"),
                            selections
                    }
            );
            log.info("\nBook Selections: {}\n",selections.stream().collect(Collectors.toList()));
            if(result.hasErrors()){
                log.error("Results has error: {}",result);
            }
            log.info(hck.hierarchicalCheck(
                    new Object[]{selections, model}).toString());
            log.info("Results: {}",result.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return "index";
    }
    //////////////////////////////////////////////////////////////////////
    @GetMapping(
            value = "selections/{selections}"
    )
    public String getSelections(
            @PathVariable @ModelAttribute("selections") @Valid @RequestParam List<BookSelection> selections,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model,
            BindingResult result){
        try{
            log.info("\nResponse Header: {}\n",response.getHeaderNames().stream().collect(Collectors.toList()));
            selections = hck.hierarchicalCheck(
                    new Object[]{
                            request.getAttribute("selections"),
                            model.getAttribute("selections"),
                            selections
                    }
            );
            log.info("\nBook Selections: {}\n",selections.stream().collect(Collectors.toList()));
            if(result.hasErrors()){
                log.error("Results has error: {}",result);
            }
            log.info(hck.hierarchicalCheck(
                    new Object[]{selections, model}).toString());
            log.info("Results: {}",result.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return "index";
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
    //////////////////////////////////////////////////////////////////////
}


