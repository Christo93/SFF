package com.example.software.library;

public class Member {

    String name;
    String role_id;

    public Member(String name, String role){
        this.name = name;
        role_id = role;
    }
    public String getName() {
        return name;
    }
    public String getRole_id() {
        return role_id;
    }

    @Override
    public String toString() {
        return name;
    }


}
