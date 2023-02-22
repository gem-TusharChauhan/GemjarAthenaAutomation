package org.gemini.athena.StepDefinition;

import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.gemini.generic.ui.utils.DriverAction;
import com.gemini.generic.ui.utils.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.gemini.athena.Locators.AthenaLocator;

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
            String s = DriverAction.getCurrentURL();
            String title = DriverAction.getTitle(s);
            if (DriverAction.isExist(AthenaLocator.usernm)) {
                GemTestReporter.addTestStep("Page title", "title= " + title, STATUS.INFO);
                logger.info("Page title = " + title);
            }
            else GemTestReporter.addTestStep("Page not found","Navigation failed",STATUS.FAIL,DriverAction.takeSnapShot());
            //DriverAction.waitSec(5);
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @When("^Entering username (.+) and (.+) password")
    public void Entering_username_and_password(String user, String pass) throws Exception {
        try {
                DriverAction.waitUntilElementAppear(AthenaLocator.usernm,5);
                DriverAction.typeText(AthenaLocator.usernm, user);
                DriverAction.typeText(AthenaLocator.pswrd, pass);
                DriverAction.click(AthenaLocator.lgnbtn);
                DriverAction.waitSec(3);
            }
        catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @Then("^Verify (.+) tests")
    public void Verify_tests(String test) throws Exception {
        try {
            DriverAction.waitUntilElementAppear(AthenaLocator.activetst,5);
            if (test.equals("Active")) {
                DriverAction.click(AthenaLocator.activetst);
                List<WebElement> tests = DriverAction.getElements(AthenaLocator.listactivetst);
                String msg = tests.get(0).getText();
                if (msg.equals("No Test Found")) {
                    logger.info("There are no active tests");
                    GemTestReporter.addTestStep("No Test found", "There are no active tests", STATUS.FAIL, DriverAction.takeSnapShot());
                } else {
                    int activetest = tests.size();
                    logger.info("Total number of active tests are " + activetest);
                    for(WebElement webElement : tests) {
                        logger.info(webElement.getText());
                    }

                    DriverAction.waitSec(2);
                    GemTestReporter.addTestStep("Test Found", "Active test validated", STATUS.PASS, DriverAction.takeSnapShot());
                }
            } else if (test.equals("Completed")) {
                DriverAction.click(AthenaLocator.completedtst);
                DriverAction.waitSec(2);
                List<WebElement> tests = DriverAction.getElements(AthenaLocator.listcompletestst);
                String msg = tests.get(0).getText();
                if (msg.equals("No Test Found")) {
                    logger.info("There are no completed tests");
                    GemTestReporter.addTestStep("No test found", "There are no completed test", STATUS.FAIL, DriverAction.takeSnapShot());
                } else {
                    int completedtest = tests.size();
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
    @Given("^Navigate to my profile after login (.+) and (.+)")
    public void Navigate_to_my_profile_after_login_and(String user, String pass) throws Exception {
        try {
            DriverAction.waitUntilElementAppear(AthenaLocator.usernm,5);
            DriverAction.typeText(AthenaLocator.usernm, user);
            DriverAction.typeText(AthenaLocator.pswrd, pass);
            DriverAction.click(AthenaLocator.lgnbtn);
            DriverAction.waitUntilElementAppear(AthenaLocator.profiledropdwn,3);
            DriverAction.click(AthenaLocator.profiledropdwn);
            DriverAction.click(AthenaLocator.profileclick);

            logger.info("Current URL is " + DriverAction.getCurrentURL());
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @When("Validating User")
    public void validating_user() throws Exception {
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

    @Then("Click on Explore test and validate URL")
    public void Click_on_Explore_test_and_validate_url() throws Exception {
        try {
            DriverAction.click(AthenaLocator.explrtst);
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
            DriverAction.typeText(AthenaLocator.usernm, user);
            DriverAction.typeText(AthenaLocator.pswrd, pass);
            DriverAction.click(AthenaLocator.lgnbtn);
            DriverAction.waitSec(3);
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @When("^Navigating to change password (.+) and creating new password (.+)")
    public void navigating_to_change_password_and_creating_new_password(String pass, String newpass) {
        try {
                DriverAction.waitUntilElementAppear(AthenaLocator.profiledropdwn,5);
                DriverAction.click(AthenaLocator.profiledropdwn);
                DriverAction.click(AthenaLocator.chngpwd);
                DriverAction.waitUntilElementAppear(By.id("oldPassword"),3);
                DriverAction.typeText(By.id("oldPassword"), pass);
                DriverAction.typeText(By.id("password"), newpass);
                DriverAction.typeText(By.id("confirmPassword"), newpass);
                DriverAction.click(By.xpath("//button/span[text()=\"Change Password\"]"));
                DriverAction.waitSec(5);
                DriverAction.click(AthenaLocator.profiledropdwn);
                DriverAction.click(AthenaLocator.logoutbtn);
        }catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @Then("^Setting new password as (.+) and checking (.+) relogin")
    public void setting_new_password_as_and_checking_relogin(String newpass, String user) throws Exception {
        DriverAction.typeText(AthenaLocator.usernm, user);
        DriverAction.typeText(AthenaLocator.pswrd, newpass);
        DriverAction.click(AthenaLocator.lgnbtn);
        DriverAction.waitSec(2);
        if (DriverAction.getCurrentURL().equals(aftrlogin)) {
            GemTestReporter.addTestStep("Validated URL", "Login Successfull", STATUS.PASS, DriverAction.takeSnapShot());
        } else
            GemTestReporter.addTestStep("Validation Error", "Login Unsuccessfull", STATUS.FAIL, DriverAction.takeSnapShot());
    }

    //--------------------------------------------------------------------------------------------------------
    @Given("^Enter username as (.+) and password as (.+)")
    public void enter_username_as_and_password_as(String user, String pass) throws Exception {
        try {
            if (user.equals("NULL")) {
                DriverAction.waitSec(3);
                DriverAction.typeText(By.id("password"), pass);
                String error=DriverAction.getElementText(By.id("username-help"));
                GemTestReporter.addTestStep("Username is not present","Error message is 'The Field is required'" +error,STATUS.PASS);
            } else if (pass.equals("NULL")) {
                DriverAction.waitSec(3);
                DriverAction.typeText(By.id("firstname5"), user);
                GemTestReporter.addTestStep("Password is not present","Error message is 'The Field is required'",STATUS.PASS);
            } else {
                GemTestReporter.addTestStep("Input value error", "Username or password input issue", STATUS.FAIL, DriverAction.takeSnapShot());

            }
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @When("Click on signin")
    public void clicking_on_signin() throws Exception {
        try {
            DriverAction.click(AthenaLocator.lgnbtn);
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
                GemTestReporter.addTestStep("Missing field", "Field is required", STATUS.PASS);
            }
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }


    //----------------------------------------------------------------------------------------------------------
    @Given("Click on signup")
    public void clicking_on_signup() throws Exception {
        try {
            DriverAction.click(AthenaLocator.signup);
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @Then("^Entering all details in fields as email(.+) password(.+) confirmpass(.+) firstname(.+) lastname(.+) contact(.+) experience(.+) expcount(.+) and campus(.+)")
    public void entering_all_details_in_fields_as_email_password_confirmpass_firstname_lastname_contact_experience_expcount_campus(String email, String pass, String cnfpass, String fstnm, String lstnm, String cntct, String exp, String expcount, String campus) throws Exception {

        try {
            int flag = 0;
            DriverAction.waitUntilElementAppear(AthenaLocator.newemail,5);
            if (DriverAction.getCurrentURL().equals(signupurl)) {
                GemTestReporter.addTestStep("Navigation successfull", "Signup page displayed", STATUS.PASS, DriverAction.takeSnapShot());
                //Entering common signup details
                DriverAction.typeText(AthenaLocator.newemail, email);
                DriverAction.typeText(AthenaLocator.newpwd, pass);
                DriverAction.typeText(AthenaLocator.newcnfrmpwd, cnfpass);
                DriverAction.typeText(AthenaLocator.newfstnm, fstnm);
                DriverAction.typeText(AthenaLocator.newlstnm, lstnm);
                DriverAction.typeText(AthenaLocator.newcntct, cntct);
                //Selecting Experience
                DriverAction.click(AthenaLocator.expdrpdwn);
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
                flag=0;
                //Checking for exp value
                if (exp.equals("Fresher")) {
                    DriverAction.click(AthenaLocator.campusdrpdwn);
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
                    DriverAction.typeText(AthenaLocator.countexp, expcount);
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
            DriverAction.click(AthenaLocator.sgnupbuttn);
            WebDriverWait wait = new WebDriverWait(DriverManager.getWebDriver(), 20);
            wait.until(ExpectedConditions.presenceOfElementLocated(AthenaLocator.toast));

            if (DriverAction.getElement(AthenaLocator.toast).getAttribute("innerHTML").contains("User registered successfully")) {
                GemTestReporter.addTestStep("Signup Successfull", "User register successful", STATUS.PASS, DriverAction.takeSnapShot());
            } else if (DriverAction.getElement(AthenaLocator.toast).getAttribute("innerHTML").contains("The username already exist in system")) {
                GemTestReporter.addTestStep("Already exist user", "User values already exist", STATUS.PASS, DriverAction.takeSnapShot());
            } else {
                GemTestReporter.addTestStep("Signup Unsuccessfull", "User not registered", STATUS.FAIL, DriverAction.takeSnapShot());
            }
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    @Given("Click on forgot password link")
    public void click_on_forgot_password_link() throws Exception {
        try{
        DriverAction.waitSec(2);
        DriverAction.click(AthenaLocator.frgtpswrd);
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
            DriverAction.click(By.xpath("//span[text()='RESET']"));
        }
        catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

    @Then("^Validating reset mail sent or not to (.+)")
    public void validating_reset_mail_sent_or_not_to_(String user) throws Exception{
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getWebDriver(), 20);
            wait.until(ExpectedConditions.presenceOfElementLocated(AthenaLocator.toast));

            if (DriverAction.getElement(AthenaLocator.toast).getAttribute("innerHTML").contains("Reset password mail sent successfully!")) {
                GemTestReporter.addTestStep("RESET Successful", "Reset mail is sent on email", STATUS.PASS, DriverAction.takeSnapShot());
            } else if (DriverAction.getElement(AthenaLocator.toast).getAttribute("innerHTML").contains("User not found with email "+ user)) {
                GemTestReporter.addTestStep("Invalid Username", "Username not found with " +user, STATUS.PASS, DriverAction.takeSnapShot());
            } else {
                GemTestReporter.addTestStep("Signup Unsuccessfull", "User not registered", STATUS.PASS, DriverAction.takeSnapShot());
            }
        }
        catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }
    }

}