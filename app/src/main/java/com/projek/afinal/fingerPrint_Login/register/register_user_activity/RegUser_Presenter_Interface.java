package com.projek.afinal.fingerPrint_Login.register.register_user_activity;

interface RegUser_Presenter_Interface {

    void checkUserDoc(String name, String phone, String adminName, String adminPhone);

    boolean checkInputValid(String name, String phone);

    boolean checkInputValid(String name, String phone,String code);
}
