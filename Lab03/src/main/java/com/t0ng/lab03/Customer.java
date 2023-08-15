package com.t0ng.lab03;

public class Customer {
    private String ID;
    private String name;
    private Boolean sex;
    private Integer age;

    Customer(String ID, String n, boolean s, int a){
        this.setID(ID);
        this.setName(n);
        this.setSex(s ? "Male" : "Female");
        this.setAge(a);
    }

    Customer(){
        this("", null, false, 0);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        if (this.sex) {
            return "Male";
        } else {
            return "Female";
        }
    }

    public void setSex(String sex) {
        sex = sex.toLowerCase();
        this.sex = sex.equals("male");
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        if (age < 0) {
            age = 0;
        }
        this.age = age;
    }
}
