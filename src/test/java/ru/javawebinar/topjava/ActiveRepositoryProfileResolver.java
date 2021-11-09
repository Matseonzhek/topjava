package ru.javawebinar.topjava;

import org.springframework.test.context.ActiveProfilesResolver;

import javax.validation.constraints.NotNull;

public class ActiveRepositoryProfileResolver implements ActiveProfilesResolver {
    //    https://stackoverflow.com/questions/23871255/spring-profiles-simple-example-of-activeprofilesresolver

    @Override
    public @NotNull String[] resolve(@NotNull Class<?> testClass) {
        return new String[] {Profiles.getActiveRepositoryProfile()};
    }



}
