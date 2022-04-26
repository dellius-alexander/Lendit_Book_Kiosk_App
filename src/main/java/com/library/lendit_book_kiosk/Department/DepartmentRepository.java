package com.library.lendit_book_kiosk.Department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "DepartmentRepository")
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
