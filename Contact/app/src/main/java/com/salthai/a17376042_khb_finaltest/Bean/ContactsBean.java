package com.salthai.a17376042_khb_finaltest.Bean;

public class ContactsBean {
    private String name;
    private String number;


    public ContactsBean(String name, String number) {
        this.name = name;
        this.number = number;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


}
