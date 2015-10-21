/**
 * @author Nick Wilson
 * @version 9.22.15
 *
 * Contact.java
 * This class represents a Contact for the PhoneBook application. It is a POJO
 * or plain old java object.
 */

public class Contact {

    private int contactID;

    private String name;

    private String companyName;

    private String phoneNumber;

    private String cellNumber;

    private String faxNumber;

    private String email;

    private String primaryCommodity;

    private String notes;

    private Boolean carrier;

    public Contact() {
    }

    public Contact(int key, String conName, String phoneNum) {
        contactID        = key;
        name             = conName;
        phoneNumber      = phoneNum;
    }

    public Contact(int key, String conName, String phoneNum, String company,
                   String faxNum, String cellNum, String commodity, 
                   String conEmail, String conNotes, Boolean isCarrier) {
        contactID        = key;
        name             = conName;
        companyName      = company;
        phoneNumber      = phoneNum;
        cellNumber       = cellNum;
        faxNumber        = faxNum;
        email            = conEmail;
        primaryCommodity = commodity;
        notes            = conNotes;
        carrier          = isCarrier;
    }


    public String toString() {
        String description = "ID = " + contactID + "\n";
        description += "name = " + name + "\n";
        description += "notes = " + notes + "\n";
        description += "email = " + email + "\n";
        description += "commodity = " + primaryCommodity + "\n";
        return description;
    }

    /* getters */
    public int getId() {
        return contactID;
    }

    public String getName() {
        return name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public String email() {
        return email;
    }

    public String getCommodity() {
        return primaryCommodity;
    }

    public String getNotes() {
        return notes;
    }

    public Boolean isCarrier() {
        return carrier;
    }

    /* setters */

    public void setId(int id) {
        contactID = id;
    }

    public void setName(String name) {
        name = name;
    }

    public void setCompanyName(String name) {
        companyName = name;
    }

    public void setPhoneNumber(String number) {
        phoneNumber = number;
    }

    public void setCellNumber(String number) {
        cellNumber = number;
    }

    public void setFaxNumber(String number) {
        faxNumber = number;
    }

    public void setEmail(String email) {
        email = email;
    }

    public void setCommodity(String commodity) {
        primaryCommodity = commodity;
    }

    public void setNotes(String note) {
        note = note;
    }

    public void setCarrier(Boolean isCarrier) {
        carrier = isCarrier;
    }
}
