package org.gemini.athena.StepDefinition;

import io.cucumber.java.en.And;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
            reporter("EXCEPTION ERROR", "SOME ERROR OCCURRED", STATUS.FAIL);
        }

    }

    @Then("Validate Login")
    public void validateLogin() {
        try {
            System.out.println(DriverManager.getWebDriver().getTitle());
            if (admindashboard.equals(DriverAction.getCurrentURL())) {
                reporter("Login Successfull", "Navigated to SuperAdmin Dashboard", STATUS.PASS);
            } else
                reporter("Login Unsuccessfull", "Navigation to SuperAdmin Dashboard Failed", STATUS.FAIL);
        } catch (Exception e) {
            reporter("Exception Occure", "Error occured", STATUS.FAIL);
        }

    }

    @Then("Verify options are visible after toggle click")
    public void verifyOptionsAreVisibleOrNot() {
        try {
            DriverAction.click(SuperadminLocator.toglbtn);
            DriverAction.waitUntilElementAppear(SuperadminLocator.toglst, 5);
            if (DriverAction.isExist(SuperadminLocator.toglst))
                reporter("Clicked on Toggle button", "List is displayed", STATUS.PASS);
            else
                reporter("Click failed on toggle button", "List is not displayed", STATUS.PASS);
        } catch (Exception e) {
            reporter("Exception Occure", "Error occured", STATUS.FAIL);
        }

    }


    @Then("Validate data tables on Dashboard")
    public void validateDataTables() {
        try {
            DriverAction.scrollToBottom();
            if (DriverAction.isExist(SuperadminLocator.activetesttbl)) {
                reporter("Table Visible", "Active Test table is visible", STATUS.PASS);
            } else reporter("Failed", "Active Test table is not visible", STATUS.FAIL);
            if (DriverAction.isExist(SuperadminLocator.campusperftbl)) {
                reporter("Table Visible", "Campus Performance table is visible", STATUS.PASS);
            } else reporter("Failed", "Campus Performance table is not visible", STATUS.FAIL);
            if (DriverAction.isExist(SuperadminLocator.ongoingevttbl)) {
//            GemTestReporter.addTestStep("Table Visible","Ongoing Event table is visible",STATUS.PASS);
                reporter("Table visible", "Ongoing Event table is visible", STATUS.PASS);
            } else reporter("Failed", "Ongoing event table is not visible", STATUS.FAIL);
        } catch (Exception e) {
            reporter("Exception Occure", "Error occured", STATUS.FAIL);
        }


    }

    @When("Click on Manage Sections")
    public void clickOnManageSections() {
        try {
            List<WebElement> togglelist = DriverAction.getElements(SuperadminLocator.toglstoptions);
            for (int i = 0; i < togglelist.size(); i++) {
                if (togglelist.get(i).getText().equals("Manage Sections")) {
                    DriverAction.click(togglelist.get(i));
                    DriverAction.waitSec(5);
                    DriverAction.click(SuperadminLocator.toglclose);
                    break;
                }
            }
        } catch (Exception e) {
            reporter("Exception Occure", "Error occured", STATUS.FAIL);
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
        try {
            DriverAction.waitSec(5);
            DriverAction.typeText(By.xpath("//input[@placeholder='Search by Section']"), "Test1");
//No Records Found for the selected search criteria!
            List<WebElement> sectlist = DriverAction.getElements(SuperadminLocator.sectionlist);
            if (sectlist.size() > 0) {
                reporter("Section Found", "Searched Section is visible", STATUS.PASS);
            } else reporter("Section not found", "Searched section not present", STATUS.FAIL);
        } catch (Exception e) {
            reporter("Exception Occure", "Error occured", STATUS.FAIL);
        }
    }


    //----------------Test Scenario-------------------------------------------------------------------------------
    @When("Click on Test Section")
    public void clickOnTestSection() {
        try {
            DriverAction.click(SuperadminLocator.toglbtn);
            List<WebElement> togglelist = DriverAction.getElements(SuperadminLocator.toglstoptions);
            for (int i = 0; i < togglelist.size(); i++) {
                if (togglelist.get(i).getText().equals("Tests")) {
                    DriverAction.click(togglelist.get(i));
                    DriverAction.waitSec(2);
                }
            }
        } catch (Exception e) {
            reporter("Exception Occure", "Error occured", STATUS.FAIL);
        }
    }

    @When("^Select test (.+) options")
    public void selectTestTestOptions(String test) {
        try {
            int flag = 0;
            List<WebElement> list = DriverAction.getElements(SuperadminLocator.listtogldropdown);
            for (int i = 0; i < list.size(); i++) {
                String tstval = list.get(i).getText();
                if (tstval.equals(test)) {
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
        } catch (Exception e) {
            reporter("Exception Occure", "Error occured", STATUS.FAIL);
        }
    }

    @When("^Select university as (.+) from campus dropdown")
    public void selectUniversityFromCampusDropdown(String campus) {
        try {
            int flag = 0;
            DriverAction.click(SuperadminLocator.campusDrpdwn);
            List<WebElement> campuslist = DriverAction.getElements(By.xpath("//ul[@role=\"listbox\"]/p-dropdownitem"));
            for (int i = 0; i < campuslist.size(); i++) {
                System.out.println(campuslist.get(i).getText());
                if (campuslist.get(i).getText().equals(campus)) {
                    DriverAction.click(campuslist.get(i));
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) {
                reporter("Campus found", "Campus value is valid", STATUS.PASS);
            } else {
                reporter("Experience not found", "Experience value is invalid. Kindly check", STATUS.FAIL);
            }
        } catch (Exception e) {
            reporter("Exception Occure", "Error occured", STATUS.FAIL);
        }
    }


    @Then("Validate availability of Test Record")
    public void validateAvailabilityOfTestRecord() {

    }

    //----------------------Questions Scenario--------------------------------------------------------------------
    @When("^Click on test tab (.+)")
    public void clickOnTestTabOption(String option) {
        try {
            DriverAction.click(SuperadminLocator.testTabSel(option));
        } catch (Exception e) {
            reporter("Exception Occure", "Error occured", STATUS.FAIL);
        }
    }

    @Then("Check Download Question Template")
    public void checkDownloadQuestionTemplate() {
        try {
            String toastmessage = "Template has been successfully downloaded. Kindly check the downloaded log file for status.";
            String errortoast = "Section already exists. Please enter a unique section name.";
            DriverAction.click(SuperadminLocator.questTempDwnd);
            WebDriverWait wait = new WebDriverWait(DriverManager.getWebDriver(), 20);
            wait.until(ExpectedConditions.presenceOfElementLocated(AthenaLocator.toast));
            if (DriverAction.getElement(AthenaLocator.toast).getAttribute("innerHTML").contains(toastmessage)) {
                reporter("Download Successful", "Question Template is Downloaded Successfully", STATUS.PASS);
            } else if (DriverAction.getElement(AthenaLocator.toast).getAttribute("innerHTML").contains(errortoast)) {
                reporter("New Section creation failed", "Testname already exist", STATUS.FAIL);
            } else {
                reporter("Download Unsuccessfull", "Question Template not downloaded", STATUS.FAIL);
            }
        } catch (Exception e) {
            reporter("Exception Occure", "Error occured", STATUS.FAIL);
        }
    }

    @Then("Validate Import Question Button")
    public void validateImportQuestionButton() {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getWebDriver(), 20);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(AthenaLocator.toast));
//            DriverAction.waitSec(4);
            DriverAction.click(SuperadminLocator.importQuest);
            DriverAction.waitSec(2);
            if (DriverAction.isExist(By.xpath("//div[contains(@class,'p-dialog-footer')]//span[text()='Cancel']")))
                reporter("Click on Import Question Successful", "Window appeared for Import Questions", STATUS.PASS);
            else reporter("Click on import Unsuccessful", "Window doesn't popup for Import Questions", STATUS.FAIL);
        } catch (Exception e) {
            reporter("Exception Occure", "Error occured", STATUS.FAIL);
        }
    }

    @Then("^Add New (.+) working")
    public void addNewQuestionWorking(String opt) {
        try {
            DriverAction.click(SuperadminLocator.addnew(opt));
        } catch (Exception e) {
            reporter("Exception Occure", "Error occured", STATUS.FAIL);
        }
    }

    @When("Click on Campus")
    public void clickOnCampus() {
        try {
            List<WebElement> togglelist = DriverAction.getElements(SuperadminLocator.toglstoptions);
            for (int i = 0; i < togglelist.size(); i++) {
                if (togglelist.get(i).getText().equals("Campus")) {
                    DriverAction.click(togglelist.get(i));
                    DriverAction.waitSec(5);
                    DriverAction.click(SuperadminLocator.toglclose);
                    break;
                }
            }
        } catch (Exception e) {
            reporter("Exception Occure", "Error occured", STATUS.FAIL);
        }
    }

    @Then("Validate Campus page is displayed")
    public void validateCampusPageIsDisplayed() {
       if(DriverAction.getCurrentURL().equals("https://athena-dev.geminisolutions.com/athena/admin/campus-management/campus_list")){
           reporter("Navigation Successful","Navigated to Campus page",STATUS.PASS);
       }
       else reporter("Navigation Unsuccessful","Navigation failed to Campus page",STATUS.FAIL);
    }

    @When("Click on User Management")
    public void clickOnUserManagement() {
        try {
            List<WebElement> togglelist = DriverAction.getElements(SuperadminLocator.toglstoptions);
            for (int i = 0; i < togglelist.size(); i++) {
                if (togglelist.get(i).getText().equals("User Management")) {
                    DriverAction.click(togglelist.get(i));
                    DriverAction.waitSec(5);
                    DriverAction.click(SuperadminLocator.toglclose);
                    break;
                }
            }
        } catch (Exception e) {
            reporter("Exception Occure", "Error occured", STATUS.FAIL);
        }
    }

    @When("Click on Role Management")
    public void clickOnRoleManagement() {
        try {
            List<WebElement> togglelist = DriverAction.getElements(SuperadminLocator.toglstoptions);
            for (int i = 0; i < togglelist.size(); i++) {
                if (togglelist.get(i).getText().equals("Role Management")) {
                    DriverAction.click(togglelist.get(i));
                    DriverAction.waitSec(5);
                    DriverAction.click(SuperadminLocator.toglclose);
                    break;
                }
            }
        } catch (Exception e) {
            reporter("Exception Occure", "Error occured", STATUS.FAIL);
        }
    }

    @Then("Check Download Campus Template")
    public void checkCampusTemplate() {
        try {
            String toastmessage = "Template has been successfully downloaded. Kindly check the downloaded log file for status.";
            DriverAction.click(SuperadminLocator.campusTempDwnd);
            WebDriverWait wait = new WebDriverWait(DriverManager.getWebDriver(), 20);
            wait.until(ExpectedConditions.presenceOfElementLocated(AthenaLocator.toast));
            if (DriverAction.getElement(AthenaLocator.toast).getAttribute("innerHTML").contains(toastmessage)) {
                reporter("Download Successful", "Campus Template is Downloaded Successfully", STATUS.PASS);
            }
         else{
            reporter("Download Unsuccessfull", "Campus Template not downloaded", STATUS.FAIL);
        }
    }
     catch(
    Exception e)

    {
        reporter("Exception Occure", "Error occured", STATUS.FAIL);
    }
}

    @Then("Check Bulk Campus Upload")
    public void checkBulkCampusUpload() {
        DriverAction.click(By.xpath("//span[text()='Bulk Campus Upload']"));
        DriverAction.waitSec(2);
        if(DriverAction.isExist(By.xpath("//button[contains(@class,'p-button')]/span[text()='Cancel']"))){
            reporter("Validation for Bulk Campus upload successful","Validation successful",STATUS.PASS);
        }
        else reporter("Validation for Bulk Campus upload failed","Popup screen not visible",STATUS.FAIL);

        DriverAction.click(By.xpath("//span[contains(@class,'p-dialog-header-close-icon')]"));
    }

    @And("Click on Register Campus")
    public void clickOnRegisterCampus() {
        DriverAction.click(SuperadminLocator.registerbtn("Register Campus"));
    }

    @Then("Validate Registration page is displayed or not")
    public void validateRegistrationPageIsDisplayedOrNot() {
        if(DriverAction.getCurrentURL().equals("https://athena-dev.geminisolutions.com/athena/admin/campus-management/register_campus")){
reporter("Navigation Successful","Navigated to new registration page",STATUS.PASS);
        }
        else reporter("Navigation Unsuccessful","Navigation Failed",STATUS.FAIL);
    }

    @When("^Enter all the mandatory fields as \"(.+)\" \"(.+)\" \"(.+)\" \"(.+)\" \"(.+)\" \"(.+)\" \"(.+)\" \"(.+)\"$")
    public void enterAllTheMandatoryFields(String cname,String cemail,String location,String university,String tponame,String tpomail,String tpocontact,String desc) {
        DriverAction.waitSec(3);
        DriverAction.typeText(By.id("campusName"),cname);
        DriverAction.typeText(By.id("campusEmail"),cemail);
        DriverAction.typeText(By.id("location"),location);
        DriverAction.typeText(By.id("university"),university);
        DriverAction.typeText(By.id("tpoName"),tponame);
        DriverAction.typeText(By.id("tpoEmail"),tpomail);
        DriverAction.typeText(By.id("tpoContact"),tpocontact);
        DriverAction.typeText(By.id("description"),desc);
    }

    @Then("Validate Campus Registration")
    public void validateCampusRegistration() {
        DriverAction.click(SuperadminLocator.registerbtn("Register"));
        if(DriverAction.getElement(AthenaLocator.toast).getAttribute("innerHTML").equals("")){

        }

    }



    //Complete table://div[@class='p-datatable-wrapper ng-star-inserted']
/*try{

    }catch (Exception e) {
        reporter("Exception Occure", "Error occured", STATUS.FAIL);
    }
    */

}
