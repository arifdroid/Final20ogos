package com.projek.afinal.fingerPrint_Login.register.register_as_admin_add_userList;

class UserFromAdmin {

    private String name;
    private String phone;

    public UserFromAdmin(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
