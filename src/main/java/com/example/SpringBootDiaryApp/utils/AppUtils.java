package com.example.SpringBootDiaryApp.utils;

public class AppUtils {
    public static final int NUMBER_OF_ITEMS_PER_PAGE = 10;
    public static final String DIARY_TEST_IMAGE = "C:\\Users\\User\\IdeaProjects\\SpringBootDiaryApp\\SpringBootDiaryApp\\src\\main\\resources\\static\\test.jpg";
    public static final String UPDATE_TEST_IMAGE = "C:\\Users\\User\\IdeaProjects\\SpringBootDiaryApp\\SpringBootDiaryApp\\src\\main\\resources\\static\\testUpdate.jpg";
    public static final String EMAIL_REGEX_STRING="^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String PASSWORD_REGEX_STRING = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String USER_NAME_REGEX = "^[A-Za-z]+\\d*$";
    public static final String IMAGE_REGEX = "^.+\\.(?i)(jpg|jpeg|png|gif)$";
}
