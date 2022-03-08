package com.library.lendit_book_kiosk.User;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // @Query provides a way to customize the query to findByUsername
    @Query("SELECT u FROM User u WHERE u.name = ?1")
    Optional<User> findByUsername(String name);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findByEmail(String email);
}
