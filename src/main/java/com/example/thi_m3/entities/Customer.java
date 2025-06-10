package com.example.thi_m3.entities;

import java.util.Date;

public class Customer {
    private int id;
    private String name;
    private Date birthday;
    private String phone;
    private String address;
    private String email;

    public Customer(int id, String name, Date birthday, String phone, String address, String email) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public Customer(String name, Date birthday, String phone, String address, String email) {
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
        this.address = address;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
