package com.example.lab3;

public class Customer {
    private String ID;
    private String name;
    private boolean sex;
    private int age;

    public Customer() {
        this("", null, false, 0);
    }

    public Customer(String ID, String n, boolean s, int a) {
        this.ID = ID;
        this.name = n;
        this.sex = s;
        this.setAge(a);
    }

    public void setID(String id) {
        this.ID = id;
    }
    public String getID() {
        return this.ID;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
    public boolean getSex() {
        return this.sex;
    }

    public void setAge(int age) {
        if (age < 0) {
            this.age = 0;
        } else {
            this.age = age;
        }
    }
    public int getAge() {
        return this.age;
    }
}
