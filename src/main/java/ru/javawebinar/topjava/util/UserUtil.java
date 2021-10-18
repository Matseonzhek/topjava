package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UserUtil {
    public static final List<User> users = Arrays.asList(
            new User (null,"Admin", "admin@mail.ru", "rewdsf", Role.USER),
            new User(null, "Ivanov", "ivanov@mail.ru", "123", Role.USER),
            new User(null,"Petrov", "petrov@mail.ru","321",Role.USER),
            new User(null,"Galya", "galya@mail.ru", "qwerty", Role.USER),
            new User(null, "Vasya","vasya@mail.ru","ytrewq",Role.USER),
            new User (null, "Guzel"," guzel@mail.ru", "fghj",Role.USER),
            new User (null, "Kristina", "kristina@mail.ru", "kjh", Role.USER)
    );


}
