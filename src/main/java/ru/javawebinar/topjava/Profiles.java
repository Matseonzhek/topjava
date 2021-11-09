package ru.javawebinar.topjava;

import org.springframework.util.ClassUtils;

import java.util.Objects;

public class Profiles {
    public static final String
            JDBC = "jdbc",
            JPA = "jpa",
            DATAJPA = "datajpa";

    public static final String REPOSITORY_IMPLEMENTATION = DATAJPA;

    public static final String
            POSTGRES_DB = "postgres",
            HSQL_DB = "hsqldb";

    //  Get DB profile depending of DB driver in classpath
    public static String getActiveDbProfile() {
        if (ClassUtils.isPresent("org.postgresql.Driver", null)) {
            return POSTGRES_DB;
        } else if (ClassUtils.isPresent("org.hsqldb.jdbcDriver", null)) {
            return HSQL_DB;
        } else {
            throw new IllegalStateException("Could not find DB driver");
        }
    }

    public static String getActiveRepositoryProfile() {
        final String activeProfile = System.getProperty("spring.profiles.active");
        if (Objects.equals(activeProfile, "jpa")) {
            return JPA;
        } else if (Objects.equals(activeProfile, "jdbc")) {
            return JDBC;
        } else {
            return activeProfile == null ? REPOSITORY_IMPLEMENTATION : activeProfile;
        }
    }
}
