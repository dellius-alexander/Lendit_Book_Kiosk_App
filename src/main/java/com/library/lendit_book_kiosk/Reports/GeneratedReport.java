package com.library.lendit_book_kiosk.Reports;

import com.library.lendit_book_kiosk.Student.Major;
import com.library.lendit_book_kiosk.Student.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class GeneratedReport implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(GeneratedReport.class);
    private Report report = new Report();

    GeneratedReport(){}

    Report getReport(){
        return this.report;
    }

    public static void main(String[] args) {
        GeneratedReport r = new GeneratedReport();
        r.getReport().set("dion", new Student(true, Set.of(new Major("CSCI"))));
        Student dion = (Student) r.getReport().get("dion");
        log.info(dion.toString());
    }
}
