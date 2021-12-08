package com.danicode.microblogging.domain;

import java.util.Objects;

public class User {
    private int idUser;
    private String name, lastName, email, username, password;

    public User() { }

    public User(String name, String lastName, String email, String username, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(int idUser, String name, String lastName, String email, String username, String password) {
        this(name, lastName, email, username, password);
        this.idUser = idUser;
    }

    public void setIdUser(int idUser) { this.idUser = idUser; }

    public void setName(String name) { this.name = name; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setEmail(String email) { this.email = email; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public int getIdUser() { return this.idUser; }

    public String getName() { return this.name; }

    public String getLastName() { return this.lastName; }

    public String getEmail() { return this.email; }

    public String getUsername() { return this.username; }

    public String getPassword() { return this.password; }

    @Override
    public boolean equals(Object userToCompare) {
        if (this == userToCompare) return true;
        if (userToCompare == null || this.getClass() != userToCompare.getClass()) return false;
        User user = (User) userToCompare;
        return this.email.equals(user.email) && this.username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.email, this.username);
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + this.idUser +
                ", name='" + this.name + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", email='" + this.email + '\'' +
                ", username='" + this.username + '\'' +
                ", password='" + this.password + '\'' +
                '}';
    }
}
