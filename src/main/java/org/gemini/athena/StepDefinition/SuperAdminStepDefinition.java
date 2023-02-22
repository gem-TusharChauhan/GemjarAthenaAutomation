package org.gemini.athena.StepDefinition;

import io.cucumber.java.en.When;
import org.gemini.athena.Locators.AthenaLocator;
import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.gemini.generic.ui.utils.DriverAction;
import com.gemini.generic.ui.utils.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.commons.codec.binary.Base64;
import org.gemini.athena.Locators.SuperadminLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Driver;
import java.util.List;

public class SuperAdminStepDefinition {
    Logger logger = LoggerFactory.getLogger(SuperAdminStepDefinition.class);
    String admindashboard = "https://athena-dev.geminisolutions.com/athena/admin/dashboard";

    public void reporter(String title, String testdesc, STATUS value) {
        GemTestReporter.addTestStep(title, testdesc, value, DriverAction.takeSnapShot());
    }

    @Given("Login with username and password")
    public void loginUser() {
        try {
            String user = "tushar.chauhan1@geminisolutions.com";
            String pass = "R2VtaW5pQDEyMw==";
//            DriverAction.waitUntilElementAppear(AthenaLocator.usernm, 10);
            DriverAction.waitSec(10);
            DriverAction.typeText(AthenaLocator.usernm, user);
            byte[] decodingString = Base64.decodeBase64(pass);
            String passwordDecoded = new String(decodingString);
            DriverAction.typeText(AthenaLocator.pswrd, passwordDecoded);
            DriverAction.click(AthenaLocator.lgnbtn);
            DriverAction.waitSec(5);
        } catch (Exception e) {
            logger.info("An exception occurred!", e);
            GemTestReporter.addTestStep("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }

    }

    @Then("Validate Login")
    public void validateLogin() {
        System.out.println(DriverManager.getWebDriver().getTitle());
        if (admindashboard.equals(DriverAction.getCurrentURL())) {
            GemTestReporter.addTestStep("Login Successfull", "Navigated to SuperAdmin Dashboard", STATUS.PASS);
        } else
            GemTestReporter.addTestStep("Login Unsuccessfull", "Navigation to SuperAdmin Dashboard Failed", STATUS.FAIL);
    }

    @Then("Verify options are visible after toggle click")
    public void verifyOptionsAreVisibleOrNot() {
        DriverAction.click(SuperadminLocator.toglbtn);
        DriverAction.waitUntilElementAppear(SuperadminLocator.toglst, 5);
        if (DriverAction.isExist(SuperadminLocator.toglst))
            GemTestReporter.addTestStep("Clicked on Toggle button", "List is displayed", STATUS.PASS, DriverAction.takeSnapShot());
        else
            GemTestReporter.addTestStep("Click failed on toggle button", "List is not displayed", STATUS.PASS, DriverAction.takeSnapShot());
    }


    @Then("Validate data tables on Dashboard")
    public void validateDataTables() {
        DriverAction.scrollToBottom();
        if (DriverAction.isExist(SuperadminLocator.activetesttbl)) {
            GemTestReporter.addTestStep("Table Visible", "Active Test table is visible", STATUS.PASS, DriverAction.takeSnapShot());
        } else reporter("Failed", "Active Test table is not visible", STATUS.FAIL);
        if (DriverAction.isExist(SuperadminLocator.campusperftbl)) {
            GemTestReporter.addTestStep("Table Visible", "Campus Performance table is visible", STATUS.PASS);
        } else reporter("Failed", "Campus Performance table is not visible", STATUS.FAIL);
        if (DriverAction.isExist(SuperadminLocator.ongoingevttbl)) {
//            GemTestReporter.addTestStep("Table Visible","Ongoing Event table is visible",STATUS.PASS);
            reporter("Table visible", "Ongoing Event table is visible", STATUS.PASS);
        } else reporter("Failed", "Ongoing event table is not visible", STATUS.FAIL);

    }

    @When("Click on Manage Sections")
    public void clickOnManageSections() {
        List<WebElement> togglelist = DriverAction.getElements(SuperadminLocator.toglstoptions);
        for (int i = 0; i < togglelist.size(); i++) {
            if (togglelist.get(i).getText().equals("Manage Sections")) {
                DriverAction.click(togglelist.get(i));
                DriverAction.waitSec(5);
                DriverAction.click(SuperadminLocator.toglclose);
                break;
            }
        }
    }

    @When("Create New Manage Section")
    public void createNewSection() {
        try {
            DriverAction.click(SuperadminLocator.createsection);
            DriverAction.typeText(SuperadminLocator.createsecname, "Test1");
            DriverAction.typeText(SuperadminLocator.createsecdesc, "Testing");
            DriverAction.click(By.xpath("//span[text()='ADD']"));
        } catch (Exception e) {
            reporter("Exception Occure", "Error occured", STATUS.FAIL);
        }
    }

    @Then("Validate section is created")
    public void validateSectionIsCreated() {
        DriverAction.waitSec(5);
        DriverAction.typeText(By.xpath("//input[@placeholder='Search by Section']"), "Test1");
//No Records Found for the selected search criteria!
        List<WebElement> sectlist = DriverAction.getElements(SuperadminLocator.sectionlist);
        if (sectlist.size() > 0) {
            reporter("Section Found", "Searched Section is visible", STATUS.PASS);
        } else reporter("Section not found", "Searched section not present", STATUS.FAIL);
    }

    @When("Click on Test Section")
    public void clickOnTestSection() {
        DriverAction.click(SuperadminLocator.toglbtn);
        List<WebElement> togglelist = DriverAction.getElements(SuperadminLocator.toglstoptions);
        for (int i = 0; i < togglelist.size(); i++) {
            if (togglelist.get(i).getText().equals("Tests")) {
                DriverAction.click(togglelist.get(i));
                DriverAction.waitSec(2);
            }
        }
    }

    @When("^Select test (.+) options")
    public void selectTestTestOptions(String test) {
        int flag = 0;
        List<WebElement> list = DriverAction.getElements(SuperadminLocator.listtogldropdown);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getText().equals(test)) {
                DriverAction.click(list.get(i));
                DriverAction.waitSec(2);
                DriverAction.click(SuperadminLocator.toglclose);
                flag = 1;
                break;
            }
        }
        if (flag == 1) {
            reporter("Clicked on " + test + " test", "Successfully clicked", STATUS.PASS);
        } else reporter("Loop failed for tests options", "Values doesnt get clicked", STATUS.FAIL);
    }

    @When("^Select university as (.+) from campus dropdown")
    public void selectUniversityFromCampusDropdown(String campus) {
        int flag=0;
        DriverAction.click(SuperadminLocator.campusDrpdwn);
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
            GemTestReporter.addTestStep("Experience not found", "Experience value is invalid. Kindly check", STATUS.FAIL, DriverAction.takeSnapShot());
        }
    }
    //Complete table://div[@class='p-datatable-wrapper ng-star-inserted']

    @Then("Validate availability of Test Record")
    public void validateAvailabilityOfTestRecord() {

    }
}
