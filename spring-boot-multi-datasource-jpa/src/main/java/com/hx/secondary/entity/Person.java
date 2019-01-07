package com.hx.secondary.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Person {
    @Id
    private String name;
    @Column
    private String passwd;

    public Person() {
    }

    public Person(String name, String passwd) {
        this.name = name;
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }
}
