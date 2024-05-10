package com.example.libirary_mgt;

public class Member {
    private String memberId;
    private String name;
    private String gender;
    private String email;

    public Member(String memberId, String name, String gender, String email) {
        this.memberId = memberId;
        this.name = name;
        this.gender = gender;
        this.email = email;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
