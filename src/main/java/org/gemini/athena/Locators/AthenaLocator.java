package org.gemini.athena.Locators;

import org.openqa.selenium.By;
public class AthenaLocator {

    public static By usernm = By.xpath("//input[@formcontrolname='username']");
    public static By pswrd = By.xpath("//input[@formcontrolname='password']");
    public static By activetst = By.xpath("//span[text()='Active Tests']");
    public static By completedtst = By.xpath("//span[text()='Completed Tests']");
    public static By lgnbtn = By.xpath("//span[@class='p-button-label']");
    public static By listactivetst = By.xpath("//p-tabpanel[@header='Active Tests']/div");
    public static By listcompletestst= By.xpath("//p-tabpanel[@header='Completed Tests']/div");
    public static By profiledropdwn= By.xpath("//span[@class='p-button-icon pi pi-caret-down']");
    public static By profileclick= By.xpath("//span[text()='Profile']");
    public static By frgtpswrd= By.xpath("//a[text()='Forgot Password?']");
    public static By signup= By.xpath("//a[@class='customText']");
    public static By chngpwd=By.xpath("//span[text()='Change Password']");
    public static By explrtst=By.xpath("//*[@id='content2']/div[2]/button");
    public static By logoutbtn=By.xpath("//span[text()='Logout']");

    public static By newemail=By.id("email");
    public static By newpwd=By.id("password");
    public static By newcnfrmpwd=By.id("confirmPassword");
    public static By newfstnm=By.id("firstName");
    public static By newlstnm=By.id("lastname");
    public static By newcntct=By.id("contact");
    public static By countexp=By.id("experience");
    public static By sgnupbuttn=By.xpath("//span[text()='SIGN UP']");
    public static By toast=By.xpath("//div[contains(@class,'p-toast p-component p-toast-top-right')]//child::div[contains(@class,'p-toast-message-text')]/div[2]");
    public static By campusdrpdwn=By.xpath("/html/body/athena-root/div/athena-auth/athena-register/div/div[1]/form/div[3]/div[7]/span/p-dropdown/div");
    public static By expdrpdwn=By.xpath("/html/body/athena-root/div/athena-auth/athena-register/div/div[1]/form/div[3]/div[8]/span/p-dropdown/div");

}
