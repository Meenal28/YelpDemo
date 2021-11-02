package com.rbc.yelp.services.utils;

public class Constants {

    public static final String KEY_BUSINESS_DATA = "business_data";
    public static final String KEY_CATEGORY_NAME = "category_name";
    public static int VIEW_TYPE_LOADING = 0;
    public static int VIEW_TYPE_ITEM = 1;
    public static int VIEW_TYPE_HEADER = 2;

    public enum Status {
        SUCCESS, ERROR, EMPTY, LOADING
    }
}
