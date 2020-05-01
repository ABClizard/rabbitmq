package com.example.demo.mq;


import java.io.Serializable;

public class Order implements Serializable{

    private String id;
    private String name;
    private String messageID;

    public Order() {
    }

    public Order(String id, String name, String messageID) {
        this.id = id;
        this.name = name;
        this.messageID = messageID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }
}

