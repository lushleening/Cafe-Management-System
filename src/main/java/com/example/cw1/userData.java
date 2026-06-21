package com.example.cw1;

public class userData {
    private Integer acc_id;
    private String username;
    private String password;
    private String role;

    public userData(Integer acc_id, String username, String password, String role) {
        this.acc_id = acc_id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Integer getAcc_id() {
        return acc_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }


}

