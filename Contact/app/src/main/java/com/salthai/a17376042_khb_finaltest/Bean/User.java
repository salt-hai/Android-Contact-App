package com.salthai.a17376042_khb_finaltest.Bean;

public class User {
    private int id;
    private String username;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    private User(String username, String password, int id) {
        this.id = id;
        this.password = password;
        this.username = username;

    }

}
