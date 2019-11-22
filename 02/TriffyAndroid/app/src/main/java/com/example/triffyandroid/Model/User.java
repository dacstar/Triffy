package com.example.triffyandroid.Model;

public class User {
    private String username;
    private String password;
    private int age;
    private String gender;
    private String ssn;
    private String account;

    public User(String username, String password, int age, String gender, String ssn, String account) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.ssn = ssn;
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }


}
