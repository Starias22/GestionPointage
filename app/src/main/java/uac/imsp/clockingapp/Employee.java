package uac.imsp.clockingapp;

public class Employee {



    //Les infos personnelles sur l'employé
    protected int RegistrationNumber;
    protected String Firstname;

    protected String Lastname;
    protected char Gender;
    protected String Birthdate;

    protected String MailAddress;
    protected byte[] Picture;
    protected byte[] QRCode;

    protected String Username;
    protected String Password;


    //Constructeurs


    public Employee(int registrationNumber, String firstname,
                    String lastname, char gender, String birthdate,
                    String mailAddress, byte[] picture, byte[] qRCode, String username, String password) {
        RegistrationNumber = registrationNumber;

        Firstname = firstname;
        Lastname = lastname;
        Gender = gender;
        Birthdate = birthdate;
        MailAddress = mailAddress;
        Picture = picture;
        QRCode = qRCode;
        Username = username;
        Password = password;

    }

    public Employee(int registrationNumber, String firstname, String lastname, char gender, String birthdate,
                    String mailAddress, byte[] qRCode, String username, String password) {

        this(registrationNumber, firstname, lastname, gender, birthdate, mailAddress, null, qRCode, username, password);


    }

    public Employee(int registrationNumber) {
        RegistrationNumber = registrationNumber;
    }

    //getters
    public int getRegistrationNumber() {
        return RegistrationNumber;
    }

    public String getFirstname() {
        return Firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public int getGender() {
        return Gender;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public String getMailAddress() {
        return MailAddress;
    }

    public  byte[] getPicture() {
        return Picture;
    }

    public byte[] getQRCode() {
        return QRCode;
    }
    public  String getUsername(){
        return  Username;
    }
    public  String getPassword(){
        return  Password;
    }


    //Setters
    public void setRegistrationNumber(int registrationNumber) {
        RegistrationNumber = registrationNumber;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;

    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public void setGender(char gender) {
        Gender = gender;
    }

    public void setBirthdate(String birthdate) {
        Birthdate = birthdate;
    }

    public void setMailAddress(String mailAddress) {
        MailAddress = mailAddress;

    }

    public void setPicture(byte[] picture) {
        Picture = picture;
    }

    public void setQRCode(byte[] qRCode) {
        QRCode = qRCode;
    }
}