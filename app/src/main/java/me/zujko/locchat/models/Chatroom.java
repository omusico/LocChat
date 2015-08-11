package me.zujko.locchat.models;

public class Chatroom {
    private int users;
    private String name;

    public Chatroom() {}

    public Chatroom(int users, String name) {
        this.users = users;
        this.name = name;
    }

    public int getUsers() {
        return users;
    }

    public String getName() {
        return name;
    }
}
