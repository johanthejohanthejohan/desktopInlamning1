/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import javax.persistence.*;

/**
 *
 * @author johan
 * Person class for storing persons
 */
@Entity

/*JPQL*/
@NamedQuery(name="Person.readAll",
        query="SELECT p FROM Person p")

public class Person {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private String lastName;
    private String profession;
    private double wage;
    private int age;
    private String skills;
    
    /*Constructor for empty object*/
    public Person(){
        this.firstName = "";
        this.lastName = "";
        this.profession = "";
        this.wage = 0;
        this.age = 0;
        this.skills="";
    }
    
    /*Overloaded constructor for initialazing object when created*/
    public Person(String firstName, String lastName, String profession, double wage, int age, String skills){
        this.firstName =  firstName;
        this.lastName = lastName;
        this.profession = profession;
        this.wage = wage;
        this.age = age;
        this.skills = skills;   
    }
    
    /*Getters*/
    public String getFirstName(){
        return this.firstName;
    }
    
    public String getLastName(){
        return this.lastName;
    }
    
    public String getProfession(){
        return this.profession;
    }
    
    public double getWage(){
        return this.wage;
    }
    
    public int getAge(){
        return this.age;
    }
    
    public String getSkills(){
        return this.skills;
    }


    public int getId() {return id; }


    /*Setters*/
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    public void setProfession(String profession){
        this.profession = profession;
    }
    
    public void setWage(double wage){
        this.wage = wage;
    }
    
    public void setAge(int age){
        this.age = age;
    }
    
    public void setSkills(String skills){
        this.skills = skills;
    }

    public void setId(int id) {this.id = id; }
       
}
