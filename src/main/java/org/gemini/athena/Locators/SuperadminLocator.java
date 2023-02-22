package org.gemini.athena.Locators;

import org.openqa.selenium.By;

public class SuperadminLocator {
    public static By toglbtn = By.xpath("//div[@class='p-toolbar-group-left']/em");
    public static By toglst = By.xpath("//div[@role='complementary']//div[contains(@class,'p-sidebar-content')]");
    public static By toglstoptions = By.xpath("//a[contains(@class,'p-panelmenu-header-link')]");
    //    public static By toglclose=By.xpath("//button[@type='button']//child::span[contains(@class,'p-sidebar-close-icon')]");
    public static By toglclose = By.xpath("//div[contains(@class,'p-sidebar-header')]//button");
    public static By createsection = By.xpath("//span[text()='Create Section']");
    public static By sectionlist = By.xpath("//div[@class='p-scrollpanel-content']/div/div");
    public static By listtogldropdown = By.xpath("//div[contains(@class,'p-highlight')]//following-sibling::div//span");
    public static By createsecname = By.id("card1");
    public static By createsecdesc = By.id("card2");
    public static By activetesttbl = By.xpath("//div[@class='p-card-body']");
    public static By campusperftbl = By.xpath("//*[text()='Campus Performance']");
    public static By ongoingevttbl = By.xpath("//*[text()='Ongoing Events']");
    public static By campusDrpdwn = By.xpath("//div[contains(@class,'p-dropdown p-component')]//span[text()='Campus']");
    public static By questTempDwnd = By.xpath("//span[text()='Download Question Template']");
    public static By importQuest = By.xpath("//button[@icon='pi pi-upload']");
    public static By addnew(String name){
        return By.xpath("//p-tabpanel[@header='"+name+"']//span[text()='Add New']");
    }
    public static By testTabSel(String option) {
        return By.xpath("//li[@role='presentation']//span[text()='"+option+"']");
    }
    //pubic static By =By.xpath();

}
