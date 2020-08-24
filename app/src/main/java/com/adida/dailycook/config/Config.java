package com.adida.dailycook.config;

public class Config {

//    public static String BASE_URL = "http://localhost:3000";

    //Using this base url for emulator using
    public static String BASE_URL = "https://cookdi.herokuapp.com";
    public static int LOG_ROUND_SALT = 12;

    public static class SHARED_PREFERENCES {
        public static class USER {
            public static String SP_NAME = "User";
            public static String ID = "ID";
            public static String NAME = "Name";
            public static String EMAIL = "Email";
            public static String AGE = "Age";
        }
    }

    ;

}
