package Locators;

import com.gemini.generic.ui.utils.DriverAction;
import org.openqa.selenium.By;
public class Locator {

    public static By usernm = By.id("firstname5");
    public static By pswrd = By.id("password");
    public static By activetst = By.id("p-tabpanel-0-label");
    public static By completedtst = By.id("p-tabpanel-1-label");
    public static By lgnbtn = By.xpath("//span[@class='p-button-label']");
    public static By listactivetst = By.id("p-tabpanel-0");
    public static By listcompletestst= By.id("p-tabpanel-1");
    public static By profiledropdwn= By.xpath("//span[@class=\"p-button-icon pi pi-caret-down\"]");
    public static By profileclick= By.xpath("//span[text()=\"Profile\"]");
    public static By frgtpswrd= By.xpath("//a[text()=\"Forgot Password?\"]");
    public static By signup= By.xpath("//a[@class=\"customText\"]");
    public static By chngpwd=By.xpath("//span[text()=\"Change Password\"]");
    public static By explrtst=By.xpath("//*[@id=\"content2\"]/div[2]/button");
    public static By logoutbtn=By.xpath("//span[text()=\"Logout\"]");

    public static By newemail=By.id("email");
    public static By newpwd=By.id("password");
    public static By newcnfrmpwd=By.id("confirmPassword");
    public static By newfstnm=By.id("firstName");
    public static By newlstnm=By.id("lastname");
    public static By newcntct=By.id("contact");
    public static By countexp=By.id("experience");
    public static By sgnupbuttn=By.xpath("//span[text()=\"SIGN UP\"]");
    public static By signuptoast=By.xpath("//div[text()=\"p-toast-summary ng-tns-c83-34\"]");
    public static By invalidsignuptoast=By.xpath("//div[text()=\"The username already exist in system\"]");
    public static By campusdrpdwn=By.xpath("/html/body/athena-root/div/athena-auth/athena-register/div/div[1]/form/div[3]/div[7]/span/p-dropdown/div");
    public static By expdrpdwn=By.xpath("/html/body/athena-root/div/athena-auth/athena-register/div/div[1]/form/div[3]/div[8]/span/p-dropdown/div");
    public static By resetpwdtoast=By.xpath("//div[text()=\"Reset password mail sent successfully!\"]");
    public static By invalidresettoast=By.xpath("//div[text()=\"User not found with email hell@gmail.com\"]");


}
