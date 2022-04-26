package com.library.lendit_book_kiosk.Runner;

import com.library.lendit_book_kiosk.Listener.CustomListener;
import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomRunner {
    private void testRunner(Map<String, String> testngParams) {

        // Running TestNG programmatically
        // http://testng.org/doc/documentation-main.html#running-testng-programmatically

        //Create an instance on TestNG
        TestNG testNG = new TestNG();

        //Create an instance of XML Suite and assign a name for it.
        XmlSuite suite = getXmlSuite();

        //Create an instance of XmlTest and assign a name for it.
        XmlTest test = getXmlTest(suite);

        //Add any parameters that you want to set to the Test.
        test.setParameters(testngParams);

        //Create a list which can contain the classes that you want to run.
        List<XmlClass> classes = getXmlClasses();

        //Assign that to the XmlTest Object created earlier.
        test.setXmlClasses(classes);

        //Create a list of XmlTests and add the Xmltest you created earlier to it.
        List<XmlTest> tests = new ArrayList<XmlTest>();
        tests.add(test);

        //add the list of tests to your Suite.
        suite.setTests(tests);

        //Add the suite to the list of suites.
        List<XmlSuite> suites = new ArrayList<XmlSuite>();
        suites.add(suite);

        //Set the list of Suites to the testNG object you created earlier.
        testNG.setXmlSuites(suites);

        //invoke run() - this will run your class.
        testNG.run();

    }

    private XmlSuite getXmlSuite() {
        XmlSuite suite = new XmlSuite();
        suite.setName("Test Suite");
        return suite;
    }

    private XmlTest getXmlTest(XmlSuite suite) {
        XmlTest test = new XmlTest(suite);
        test.setName("test_with_firefox");
        return test;
    }

    private List<XmlClass> getXmlClasses() {
        List<XmlClass> classez = new ArrayList<XmlClass>();
        classez.add(new XmlClass("com.sayem.pom.scripts.LoginTest"));
        return classez;
    }

//    public static void main(String args[]) {
//
//        Program program = new Program();
//        Map<String,String> params = new HashMap<String,String>();
//        params.put("browserName", "Firefox");
//        params.put("remoteUrl", "");
//        params.put("domain", "http://www.google.com");
//        program.testRunner(params);
//    }
//    public static void main(String[] args) {
//
//        TestNG testng = new TestNG();
//        testng.setTestClasses(
//                new Class[] {
//                        com.library.lendit_book_kiosk.Selenium.ParallelTests.class,
//
//                });
//        List<Class<? extends ITestNGListener>> listeners = new ArrayList<>();
//        listeners.add(CustomListener.class);
//        testng.setListenerClasses(listeners);
//        testng.run();
//    }
}