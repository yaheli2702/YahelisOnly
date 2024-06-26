package com.example.yahelis;

public class User {
    //מטרת המחלקה לייצג משתמש.

    public String username;
    public String email;
    private int age;
    private int phone;
    private int iden;
    private String info;

    private String uid;

    public User(String username, String email, int age, int phone, int iden) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.phone = phone;
        this.iden = iden;
        this.info="";
        this.uid="";
    }

    public User(String username, String email, int age, int phone, int iden, String uid) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.phone = phone;
        this.iden = iden;
        this.uid = uid;
    }

    public User(String username, String email, int age, int phone, int iden, String info, String uid) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.phone = phone;
        this.iden = iden;
        this.info = info;
        this.uid = uid;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getInfo(){return info;}

    public void setAge(int age) {
        this.age = age;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setIden(int iden) {
        this.iden = iden;
    }

    public void setInfo(String info){this.info=info;}
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
