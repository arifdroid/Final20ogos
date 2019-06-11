package com.example.afinal.fingerPrint_Login.register.register_with_activity;

import java.util.HashMap;

public interface RegAdminModel_Interface {

    boolean getFromSimulation(String name, String phone);

    HashMap getIfLogInExist(String name, String phone, String adminname, String adminphone);

    boolean returnSimulation();

    boolean getFromFirebase(String name, String phone);
}
