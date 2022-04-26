package com.library.lendit_book_kiosk.Department.Course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;

@Transactional
@Service
public class CourseService implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(CourseService.class);
    @Autowired
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


}
