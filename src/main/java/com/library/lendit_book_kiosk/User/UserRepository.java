package com.library.lendit_book_kiosk.User;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Data Access Layer
 * @Context "com.library.lendit_book_kiosk.User.UserRepository"
 */
@Repository(value = "com.library.lendit_book_kiosk.User.UserRepository")  // Provide value for @Autowired dependency injection instead of using @Component
//@Component(value = "UserRepository")  // Use Component scan for @Autowired dependency injection
public interface UserRepository extends JpaRepository<User, Long> {
    // @Query provides a way to customize the query to findByUsername
    @Query("SELECT u FROM User u WHERE u.name LIKE %?1%")
    Optional<User> findUserByName(String name);

    @Query("SELECT u FROM User u WHERE u.email LIKE %?1%")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    Optional<User> findUserById(Long id);


}
