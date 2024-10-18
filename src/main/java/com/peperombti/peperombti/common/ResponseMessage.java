package com.peperombti.peperombti.common;

public interface ResponseMessage {
    //HTTP 200
    String SUCCESS = "success";

    //HTTP 400
    String INVALID_INPUT = "invalid input";

    //HTTP 500
    String DATABASE_ERROR = "database error";
}
