package com.example.thi_m3.entities;

import java.util.Date;

public class Employee {
    private int id;
    private String name;
    private Date birthday;
    private String address;

    public Employee(int id, String name, Date birthday, String address) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.address = address;
    }

    public Employee(String name, Date birthday, String address) {
        this.name = name;
        this.birthday = birthday;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
