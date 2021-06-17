package com.company;

/**
 * @author danzlo
 */


public class Person extends java.util.Observable {
    private int id;
    private String fullName;
    private String fullAddress;
    private String phoneNumber;
    private static int count = 0;

    
    public Person(String fullName, String fullAddress, String phoneNumber){
        this.fullName = fullName;
        this.fullAddress = fullAddress;
        this.phoneNumber = phoneNumber;
        setId(++count);
    }

    
    public void setId(int id) {
        this.id = id;
    }

    
    public int getId() {
        return this.id;
    }

    
    public String getFullName() {
        return this.fullName;
    }
    
    public void signIn(){
        System.out.println("super class sign in");
    }

}


