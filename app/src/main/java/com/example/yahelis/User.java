package com.example.yahelis;

public class User {

    public String username;
    public String email;
    private int age;
    private int phone;
    private int iden;

    public User(String username, String email, int age, int phone, int iden) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.phone = phone;
        this.iden = iden;
    }

    public User(){
    }
    public String getUsername(){return username;}
    public void setUsername(String username){this.username=username;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email=email;}
    public int getAge(){return age;}
    public int getPhone(){return phone;}
    public int getIden(){return iden;}


}
