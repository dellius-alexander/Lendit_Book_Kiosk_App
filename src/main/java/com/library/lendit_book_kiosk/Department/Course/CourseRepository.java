package com.library.lendit_book_kiosk.Department.Course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "CourseRepository")
public interface CourseRepository extends JpaRepository<Course, Long> {
}
