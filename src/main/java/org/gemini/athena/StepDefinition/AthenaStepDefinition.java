package org.gemini.athena.StepDefinition;

import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.gemini.generic.ui.utils.DriverAction;
import com.gemini.generic.ui.utils.DriverManager;
import com.github.dockerjava.api.model.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.jodah.failsafe.internal.util.DelegatingScheduler;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.logging.log4j.core.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import Locators.Locator;

import java.util.List;


public class AthenaStepDefinition {
    Logger logger = LoggerFactory.getLogger(AthenaStepDefinition.class);
    String loginurl = "https://athena-dev.geminisolutions.com/login";
    String aftrlogin = "https://athena-dev.geminisolutions.com/app/candidate/dashboard";
    String profileurl = "https://athena-dev.geminisolutions.com/app/candidate/profile";
    String forgoturl="https://athena-dev.geminisolutions.com/forgot-password";
    String signupurl = "https://athena-dev.geminisolutions.com/register";


    @Given("Navigating to Athena portal")
    public void Navigating_to_Athena_portal() throws Exception {
        try {
            DriverAction.maximizeBrowser();
            String s = DriverAction.getCurrentURL();
            String title = DriverAction.getTitle(s);
            GemTestReporter.addTestStep("Page title", "title= " + title, STATUS.INFO);
            logger.info("Page title = " + title);
            DriverAction.waitSec(5);
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @When("^Entering username (.+) and (.+) password")
    public void Entering_username_and_password(String user, String pass) throws Exception {
        try {
            DriverAction.typeText(Locator.usernm, user);
            DriverAction.typeText(Locator.pswrd, pass);
            DriverAction.click(Locator.lgnbtn);
            DriverAction.waitSec(3);
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @Then("^Count (.+) tests")
    public void Count_active_tests(String test) throws Exception {
        try {
            DriverAction.waitSec(2);
            if (test.equals("Active")) {
                DriverAction.click(Locator.activetst);
                List<WebElement> tests = DriverAction.getElements(Locator.listactivetst);
                String msg = tests.get(0).getText();
                if (msg.equals("No Test Found")) {
                    logger.info("There are no active tests");
                    GemTestReporter.addTestStep("No Test found", "There are no active tests", STATUS.FAIL, DriverAction.takeSnapShot());
                } else {
                    Integer activetest = tests.size();
                    logger.info("Total number of active tests are " + activetest);
                    for (int i = 0; i < activetest; i++) {
                        logger.info(tests.get(i).getText());
                    }
                    DriverAction.waitSec(2);
                    GemTestReporter.addTestStep("Test Found", "Active test validated", STATUS.PASS, DriverAction.takeSnapShot());
                }
            } else if (test.equals("Completed")) {
                DriverAction.click(Locator.completedtst);
                DriverAction.waitSec(2);
                List<WebElement> tests = DriverAction.getElements(Locator.listcompletestst);
                String msg = tests.get(0).getText();
                if (msg.equals("No Test Found")) {
                    logger.info("There are no completed tests");
                    GemTestReporter.addTestStep("No test found", "There are no completed test", STATUS.FAIL, DriverAction.takeSnapShot());
                } else {
                    Integer completedtest = tests.size();
                    logger.info("Total number of completed tests are " + completedtest);
                    List<WebElement> testtitle = DriverAction.getElements(By.xpath("//div[@class=\"control-overflow longer-name\"]"));
                    for (int i = 0; i < completedtest; i++) {
                        logger.info(testtitle.get(i).getText());
                    }
                    DriverAction.waitSec(2);
                    GemTestReporter.addTestStep("Test found", "Completed test validated", STATUS.PASS, DriverAction.takeSnapShot());
                }
            } else {
                logger.info("Not valid tests");
                GemTestReporter.addTestStep("Test error", "Invalid test value", STATUS.FAIL, DriverAction.takeSnapShot());
            }
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    //---------------------------------------------------------------------------------------------------
    @Given("^Navigating to my profile after login (.+) and (.+)")
    public void Navigating_to_my_profile_after_login_and(String user, String pass) throws Exception {
        try {
            DriverAction.maximizeBrowser();
            DriverAction.typeText(Locator.usernm, user);
            DriverAction.typeText(Locator.pswrd, pass);
            DriverAction.click(Locator.lgnbtn);
            DriverAction.waitSec(3);
            DriverAction.click(Locator.profiledropdwn);
            DriverAction.click(Locator.profileclick);
            DriverAction.waitSec(2);
            logger.info("Current URL is " + DriverAction.getCurrentURL());
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @When("Capture the profile data")
    public void Capture_the_profile_data() throws Exception {
        try {
            List<WebElement> head = DriverAction.getElements(By.xpath("//div[@class=\"col-sm-6 p-0 details-heading\"]"));
            List<WebElement> value = DriverAction.getElements((By.xpath("//div[@class=\"col-sm-6 p-0 details-value\"]")));
            logger.info("Following is the Profile data");
            for (int i = 0; i < head.size(); i++) {
                logger.info(head.get(i).getText() + "    " + value.get(i).getText());
            }
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @Then("Click on Explore test and validate url")
    public void Click_on_Explore_test_and_validate_url() throws Exception {
        try {
            DriverAction.click(Locator.explrtst);
            String url = DriverAction.getCurrentURL();
            logger.info("Validating the URL");
            if (url.equals(aftrlogin)) {
                logger.info("URL is correct");
                GemTestReporter.addTestStep("URL validated", "URL is correct", STATUS.PASS, DriverAction.takeSnapShot());
            } else
                GemTestReporter.addTestStep("URL validation failed", "URL is incorrect", STATUS.FAIL, DriverAction.takeSnapShot());

            DriverAction.waitSec(2);
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }
//---------------------------------------------------------------------------------------------------------

    @Given("^Login to Athena portal using username (.+) and (.+) password")
    public void login_to_athena_portal_using_username_and_password(String user, String pass) throws Exception {
        try {
            DriverAction.typeText(Locator.usernm, user);
            DriverAction.typeText(Locator.pswrd, pass);
            DriverAction.click(Locator.lgnbtn);
            DriverAction.waitSec(3);
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @When("^Navigating to change password (.+) and creating new password (.+)")
    public void navigating_to_change_password_and_creating_new_password(String pass, String newpass) {
        try {
            DriverAction.waitSec(3);
            DriverAction.click(Locator.profiledropdwn);
            DriverAction.click(Locator.chngpwd);
            DriverAction.waitSec(2);
            DriverAction.typeText(By.id("oldPassword"), pass);
            DriverAction.typeText(By.id("password"), newpass);
            DriverAction.typeText(By.id("confirmPassword"), newpass);
            DriverAction.click(By.xpath("//button/span[text()=\"Change Password\"]"));
            DriverAction.waitSec(5);
            DriverAction.click(Locator.profiledropdwn);
            DriverAction.click(Locator.logoutbtn);
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @Then("^Setting new password as (.+) and checking (.+) relogin")
    public void setting_new_password_as_and_checking_relogin(String newpass, String user) throws Exception {
        DriverAction.typeText(Locator.usernm, user);
        DriverAction.typeText(Locator.pswrd, newpass);
        DriverAction.click(Locator.lgnbtn);
        DriverAction.waitSec(2);
        if (DriverAction.getCurrentURL().equals(aftrlogin)) {
            GemTestReporter.addTestStep("Validated URL", "Login Successfull", STATUS.PASS, DriverAction.takeSnapShot());
        } else
            GemTestReporter.addTestStep("Validation Error", "Login Unsuccessfull", STATUS.FAIL, DriverAction.takeSnapShot());
    }

    //--------------------------------------------------------------------------------------------------------
    @Given("^enter the invalid username as (.+) and password as (.+)")
    public void enter_the_invalid_username_as_and_password_as(String user, String pass) throws Exception {
        try {
            DriverAction.maximizeBrowser();
            if (user.equals("NULL")) {
                DriverAction.waitSec(3);
                //DriverAction.typeText(By.id("firstname5"), user);
                DriverAction.typeText(By.id("password"), pass);
            } else if (pass.equals("NULL")) {
                DriverAction.waitSec(3);
                DriverAction.typeText(By.id("firstname5"), user);
                //DriverAction.typeText(By.id("password"), pass);
            } else {
                GemTestReporter.addTestStep("Input value error", "Username or password input issue", STATUS.FAIL, DriverAction.takeSnapShot());

            }
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @When("clicking on signin")
    public void clicking_on_signin() throws Exception {
        try {
            DriverAction.click(Locator.lgnbtn);
            DriverAction.waitSec(2);

        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @Then("Validate login")
    public void validate_login() throws Exception {
        try {
            String error = DriverAction.getElementText(By.xpath("//small[text()=\"The Field is required\"]"));
            logger.info("Error message is " + error);
            if (error.equals("The Field is required")) {
                GemTestReporter.addTestStep("Missing field", "Field is required", STATUS.FAIL, DriverAction.takeSnapShot());
            }
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

//    @Then("Click on forgot password")
//    public void click_on_forgot_password() throws Exception {
//        try {
//            DriverAction.click(By.xpath("//a[text()=\"Forgot Password?\"]"));
//            DriverAction.waitSec(2);
//        } catch (Exception e) {
//            logger.info("An exception occurred!", e);
//            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
//        }
//    }
//
//    @Then("Click back to login")
//    public void Click_back_to_login() throws Exception {
//        try {
//            DriverAction.click(By.xpath("//a[@class=\"customText\"] //b"));
//            DriverAction.waitSec(2);
//            if (DriverAction.getCurrentURL().equals("https://athena-dev.geminisolutions.com/login")) {
//                GemTestReporter.addTestStep("Current page validation", "Page is correct", STATUS.PASS, DriverAction.takeSnapShot());
//            } else
//                GemTestReporter.addTestStep("Current page validation", "Page is invalid", STATUS.FAIL, DriverAction.takeSnapShot());
//        } catch (Exception e) {
//            logger.info("An exception occurred!", e);
//            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
//        }
//    }

    //----------------------------------------------------------------------------------------------------------
    @Given("Clicking on signup")
    public void clicking_on_signup() throws Exception {
        try {
            DriverAction.click(Locator.signup);
            DriverAction.waitSec(5);
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @Then("^Entering all details in fields as email(.+) password(.+) confirmpass(.+) firstname(.+) lastname(.+) contact(.+) experience(.+) expcount(.+) and campus(.+)")
    public void entering_all_details_in_fields_as_email_password_confirmpass_firstname_lastname_contact_experience_expcount_campus(String email, String pass, String cnfpass, String fstnm, String lstnm, String cntct, String exp, String expcount, String campus) throws Exception {

        try {
            int flag = 0;
            if (DriverAction.getCurrentURL().equals(signupurl)) {
                GemTestReporter.addTestStep("Navigation successfull", "Signup page displayed", STATUS.PASS, DriverAction.takeSnapShot());
                //Entering common signup details
                DriverAction.typeText(Locator.newemail, email);
                DriverAction.typeText(Locator.newpwd, pass);
                DriverAction.typeText(Locator.newcnfrmpwd, cnfpass);
                DriverAction.typeText(Locator.newfstnm, fstnm);
                DriverAction.typeText(Locator.newlstnm, lstnm);
                DriverAction.typeText(Locator.newcntct, cntct);
                //Selecting Experience
                DriverAction.click(Locator.expdrpdwn);
                List<WebElement> explist = DriverAction.getElements(By.xpath("//ul[@role=\"listbox\"]/p-dropdownitem"));
                for (int i = 0; i < explist.size(); i++) {
                    if (explist.get(i).getText().equals(exp)) {
                        DriverAction.click(explist.get(i));
                        flag = 1;
                        break;
                    }
                }
                if (flag == 1) {
                    GemTestReporter.addTestStep("Experience entered", "Experience value is valid", STATUS.PASS, DriverAction.takeSnapShot());
                } else {
                    GemTestReporter.addTestStep("Experience not found", "Experience value is invalid. Kindly check", STATUS.FAIL, DriverAction.takeSnapShot());
                }

                //Checking for exp value
                if (exp.equals("Fresher")) {
                    DriverAction.click(Locator.campusdrpdwn);
                    List<WebElement> campuslist = DriverAction.getElements(By.xpath("//ul[@role=\"listbox\"]/p-dropdownitem"));
                    for (int i = 0; i < campuslist.size(); i++) {
                        if (campuslist.get(i).getText().equals(campus)) {
                            DriverAction.click(campuslist.get(i));
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 1) {
                        GemTestReporter.addTestStep("Campus found", "Campus value is valid", STATUS.PASS, DriverAction.takeSnapShot());
                    } else {
                        GemTestReporter.addTestStep("Campus not found", "Campus value is invalid. Kindly check", STATUS.FAIL, DriverAction.takeSnapShot());
                    }
                }
                //Entering experience in months
                else if (exp.equals("Experience")) {
                    DriverAction.typeText(Locator.countexp, expcount);
                } else {
                    GemTestReporter.addTestStep("Error value", "Invalid Experience value", STATUS.FAIL, DriverAction.takeSnapShot());
                }

            } else {
                GemTestReporter.addTestStep("Navigation failed", "Signup page not displayed", STATUS.FAIL, DriverAction.takeSnapShot());
            }
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @Then("Validating signup")
    public void validating_signup() throws Exception {
        try {
            DriverAction.click(Locator.sgnupbuttn);

            if (DriverAction.getElement(Locator.signuptoast).isDisplayed()) {
                GemTestReporter.addTestStep("Signup Successfull", "User register successful", STATUS.PASS, DriverAction.takeSnapShot());
            } else if (DriverAction.getElement(Locator.invalidsignuptoast).isDisplayed()) {
                GemTestReporter.addTestStep("Already exist user", "User values already exist", STATUS.FAIL, DriverAction.takeSnapShot());
            } else {
                GemTestReporter.addTestStep("Signup Unsuccessfull", "User not registered", STATUS.FAIL, DriverAction.takeSnapShot());
            }
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }

    }

    //------------------------------------------------------------------------------------------------------------------
    @Given("Clicking on forgot password link")
    public void clicking_on_forgot_password_link() throws Exception {
        try{
        DriverAction.waitSec(2);
        DriverAction.click(Locator.frgtpswrd);
        if(DriverAction.getCurrentURL().equals(forgoturl)){
            GemTestReporter.addTestStep("Navigation Successful","Forgot password page is displayed",STATUS.PASS,DriverAction.takeSnapShot());
        }
        else{
            GemTestReporter.addTestStep("Navigation failed","Page is not displayed. Kindly check",STATUS.FAIL,DriverAction.takeSnapShot());
        }
    }
        catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @When("^Entering the username (.+)")
    public void entering_the_username(String user) throws Exception{
        try {
            DriverAction.typeText(By.id("email"), user);
            DriverAction.click(By.xpath("//span[text()=\"RESET\"]"));
            DriverAction.waitSec(1);
        }
        catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @Then("Validating reset mail sent or not")
    public void validating_reset_mail_sent_or_not() throws Exception{
        try {
            if (DriverAction.getElement(Locator.resetpwdtoast).isDisplayed()) {
                GemTestReporter.addTestStep("Password reset successfull", "Password is reset", STATUS.PASS, DriverAction.takeSnapShot());
            } else if (DriverAction.getElement(Locator.invalidsignuptoast).isDisplayed()) {
                GemTestReporter.addTestStep("Password reset unsuccessfull", "Password is not reset", STATUS.FAIL, DriverAction.takeSnapShot());
            } else
                GemTestReporter.addTestStep("Some error occured", "Invalid action", STATUS.FAIL, DriverAction.takeSnapShot());
        }
        catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }
}