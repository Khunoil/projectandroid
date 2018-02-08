package com.example.oil.finalandroid;

/**
 * Created by OIL on 2/7/2018.
 */

class RegisModel {
    private Integer rgCodeCard;
    private String rgfirstname;
    private String rglastname;
    private String rgpassword;
    private String rgemail;
    private String rgphone;
    private String rglicense;
    private String rgcarbrand;
    private String rgcarcolor;
    private String rgcountry;

    public  RegisModel(int codecard, String firstname, String lastname, String password, String email
            , String phone, String license, String carbrand, String carcolor, String country) {

       rgCodeCard = codecard;
       rgfirstname = firstname;
       rglastname = lastname;
       rgpassword = password;
       rgemail = email;
       rgphone = phone;
       rglicense = license;
       rgcarbrand = carbrand;
       rgcarcolor = carcolor;
       rgcountry = country;

    }
}

