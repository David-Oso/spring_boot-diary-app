package com.example.SpringBootDiaryApp.data.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {
        USER_READ("user:read"),
        USER_UPDATE("user:update"),
        USER_CREATE("user:create"),
        USER_DELETE("user:delete"),


        ;

        @Getter
        private final String permission;
}
