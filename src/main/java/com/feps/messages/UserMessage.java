package com.feps.messages;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("UserMessage")
public class UserMessage implements IMessage {
    private String name;
    private String lastName;

    public UserMessage(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public UserMessage(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public void prova(String t) {
        System.out.println(t);
    }
}

