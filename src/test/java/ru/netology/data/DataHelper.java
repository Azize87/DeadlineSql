package ru.netology.data;

import lombok.Value;

@Value
public class DataHelper {

    private DataHelper() {
    }


    public static User getUser() {
        return new User("vasya", "qwerty123");
    }


    @Value
    public static class User {
        private String login;
        private String password;
    }
}
