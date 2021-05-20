package com.example.springTask.models;

import javax.persistence.*;

@Entity
@Table(name = "teacher_flyway", schema = "db_spring")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "salary")
    private Float salary;
    @Column(name = "degree")
    private Integer degree;

    public Teacher() {

    }

    public Teacher(Integer id, String name, String email, Float salary, Integer degree) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.degree = degree;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }


}
