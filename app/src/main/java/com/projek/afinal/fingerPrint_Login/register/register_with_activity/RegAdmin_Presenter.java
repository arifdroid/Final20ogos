package com.projek.afinal.fingerPrint_Login.register.register_with_activity;

import android.util.Log;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;



//22 may notice on update() method, we dont request get method from model,
//instead, we access variable directly.

class RegAdmin_Presenter extends Observable implements  RegAdminPresenter_Interface, Observer {

    private RegAdminViewInterface view;
    private RegAdmin_Model model;

    private boolean modelReturn;
    public  String phoneFinal;

    //9 june

    private HashMap modelHashMap;


    public RegAdmin_Presenter(RegAdminViewInterface regAdminViewInterface) {

        modelHashMap = new HashMap();

        view = regAdminViewInterface;

        model = new RegAdmin_Model();

        model.addObserver(this);
    }

    @Override
    public boolean checkInputValid(String name, String phone) {

        //for log in , we treat name as input.

        Log.i("22 May, ","00 , checkInputValid ");

        modelReturn = false;


        if((name!=null&& phone!=null)||(name!="" && phone!="")) { //still need to check

            String checkPhoneinput = phone;

            char first = phone.charAt(0);


            //22 may
            //dash also need to change. //we need to static variable can be accessed once from calling main.
            //call only once.

            // phone >  6019- 211 2111
            // first > 6


            phone = phone.replace("-", "");
            phone = phone.replace(" ", "");

            // phone >  60192112111
            // first > 6

            //check if phone number too short or too long, return false.

            int lengthPhone = phone.length();

            if (lengthPhone >= 10 && lengthPhone <= 13) {


            if (first != '+') {

                //something wrong with input. either return false or add plus +6

                if (first == '6') {

                    phone = "+" + phone; //notice we only change data here, not globally

                    // phone >  +60192112111

                    phoneFinal = phone;

                    return true;


                } else if (first == '0') {

                    phone = "+6" + phone;

                    phoneFinal = phone;

                    return true;


                } else {


                    Log.i("22MayTest, ", "33 , checkInputValid, phone" + phone);
                    return false;
                }


            } else { //assume number start with plus

                Log.i("22MayTest, ", "44 , checkInputValid, phone" + phone);
                // +601
                String checkFirst = phone.substring(0, 4);

                if (checkFirst.equals("+601")) {

                    phoneFinal = phone;
                    return true;
                } else {

                    return false;
                }
            }



        }// check phone length

        else {
            return false; //phone length is not between 10 and 13, check after trimming spaces " " and dash "-"
        }
        }else {

            Log.i("22MayTest, ","55 , checkInputValid, phone" +phone);
            return false;
        }
    }




    //9 june if logged in

    public Boolean hashDataLogin(String name,String phone, String nameAdmin,String phoneAdmin){

        modelHashMap = model.getIfLogInExist(name,phone,nameAdmin,phoneAdmin);




        if(modelHashMap.isEmpty()) {


            return false;

        }else {


            return true;
        }
    }




    //this is admin name and phone.

    @Override
    public boolean checkFromFirebaseSimulation(String name, String phone) {

        // need observable
        //this is always false, unless exists, then override.

        modelReturn = model.getFromFirebase(name,phone);


        if(modelReturn){    //if this true, means, need updated result.


        }

        return false;
    }

    @Override
    public boolean checkFinalFromFirebase() {

       return modelReturn; //is this always return true
       // return false;
    }

    @Override
    public void resultPresenter(String s) {


    }

    @Override
    public void update(Observable o, Object arg) {

        if(o instanceof  RegAdmin_Model){


            boolean checkHere = ((RegAdmin_Model) o).returnSimulation();

            if(checkHere){ //if now we are getting result back.

                //need to notify change to modelReturn

                modelReturn = true;

                setChanged();
                notifyObservers();


            }else {

                modelReturn = false;
               setChanged();
               notifyObservers();

            }

        }
    }


    public String getPhoneFinal(){
        return phoneFinal;
    }

}
