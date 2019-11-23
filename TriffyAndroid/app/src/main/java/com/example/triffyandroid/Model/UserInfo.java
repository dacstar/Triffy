package com.example.triffyandroid.Model;

public class UserInfo {
    private String user;
    private int id;
    private boolean check_balance;

    public UserInfo(String user, int id, boolean check_balance) {
        this.user = user;
        this.id = id;
        this.check_balance = check_balance;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCheck_balance() {
        return check_balance;
    }

    public void setCheck_balance(boolean check_balance) {
        this.check_balance = check_balance;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "user='" + user + '\'' +
                ", id=" + id +
                ", check_balance=" + check_balance +
                '}';
    }
}
