package com.library.lendit_book_kiosk.Listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.Serializable;

public class CustomListener implements Serializable, ITestListener {
    private static final Logger log = LoggerFactory.getLogger(CustomListener.class);

    @Override
    public void onFinish(ITestContext arg0) {
        log.info("\nCompleted Entire Execution: {}",arg0);
    }

    @Override
    public void onStart(ITestContext arg0) {
        log.info("\nStarting TestNG Execution: {}",arg0);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
        log.error("\nFailed Test: {}", arg0);
    }

    @Override
    public void onTestFailure(ITestResult arg0) {
        log.error("\nInside onTestFailure: {}",arg0);
    }

    @Override
    public void onTestSkipped(ITestResult arg0) {
        log.info("\nSkipped Tests: {}", arg0);
    }

    @Override
    public void onTestStart(ITestResult arg0) {
        log.info("Starting test case " + arg0.getMethod().getMethodName());

    }

    @Override
    public void onTestSuccess(ITestResult arg0) {
        log.info("Test success , test case name : " + arg0.getMethod().getMethodName());

    }
}
