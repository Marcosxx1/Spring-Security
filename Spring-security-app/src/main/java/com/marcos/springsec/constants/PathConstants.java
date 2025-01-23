package com.marcos.springsec.constants;

public class PathConstants {

    private PathConstants() {
    }

    public static final String ACCOUNT = "/myAccount";
    public static final String BALANCE = "/myBallance";
    public static final String CARDS = "/myCards";
    public static final String CONTACT = "/contacts";
    public static final String LOANS = "/myLoans";
    public static final String NOTICES = "/notices";
    public static final String ERROR = "/error";
    public static final String CUSTOMER = "/customer";
    public static final String INVALID_SESSION = "/invalidSession";
    public static final String REGISTER = CUSTOMER + "/register";
    public static final String USER =  CUSTOMER + "/user";
}
