package com.example.doctorfinder;

public class Doctor {
    private String qualification;
    private int age;
    private int experience;
    private String phoneNumber;

    public Doctor(String qualification, int age, int experience, String phoneNumber) {
        this.qualification = qualification;
        this.age = age;
        this.experience = experience;
        this.phoneNumber = phoneNumber;
    }

    public String getQualification() {
        return qualification;
    }

    public int getAge() {
        return age;
    }

    public int getExperience() {
        return experience;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}