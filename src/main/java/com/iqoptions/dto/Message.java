package com.iqoptions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by MiF on 24.06.2017.
 */
public class Message {

    @JsonProperty("0")
    private String text;
    private String[] email;
    private String[] password;
    @JsonProperty("first_name")
    private String[] firstName;
    @JsonProperty("last_name")
    private String[] lastName;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String email() {
        return email[0];
    }

    public void setEmail(String[] email) {
        this.email = email;
    }

    public String password() {
        return password[0];
    }

    public void setPassword(String[] password) {
        this.password = password;
    }

    public String firstName() {
        return firstName[0];
    }

    public void setFirstName(String[] firstName) {
        this.firstName = firstName;
    }

    public String lastName() {
        return lastName[0];
    }

    public void setLastName(String[] lastName) {
        this.lastName = lastName;
    }
}
