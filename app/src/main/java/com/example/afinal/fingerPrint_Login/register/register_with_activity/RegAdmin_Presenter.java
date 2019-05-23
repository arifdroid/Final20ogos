package com.example.afinal.fingerPrint_Login.register.register_with_activity;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;



//22 may notice on update() method, we dont request get method from model,
//instead, we access variable directly.

class RegAdmin_Presenter extends Observable implements  RegAdminPresenter_Interface, Observer {

    private RegAdminViewInterface view;
    private RegAdmin_Model model;

    private boolean modelReturn;
    public static String phoneFinal;


    public RegAdmin_Presenter(RegAdminViewInterface regAdminViewInterface) {


        view = regAdminViewInterface;

        model = new RegAdmin_Model();

        model.addObserver(this);
    }

    @Override
    public boolean checkInputValid(String name, String phone) {
        modelReturn = false;
        if((name!=null&& phone!=null)||(name!="" && phone!="")){

            String checkPhoneinput = phone;

            Character first = Objects.requireNonNull(checkPhoneinput).charAt(0);


            //22 may
            //dash also need to change. //we need to static variable can be accessed once from calling main.
            //call only once.


             phone = phone.replace("-","");


            if(!first.equals("+")){

                //something wrong with input. either return false or add plus +6

                if(first.equals("6")){

                    phone = "+"+phone; //notice we only change data here, not globally

                    phoneFinal = phone;

                    return true;


                }else if(first.equals("0")){

                    phone = "+6"+ phone;

                    phoneFinal = phone;

                    return true;


                }else {

                    return false;
                }



            }else { //assume number start with plus

                phoneFinal = phone;
                return true;

            }
        }
        return false;
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


}
