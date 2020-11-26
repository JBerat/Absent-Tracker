package com.beratucgul.at2.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GirisYapData {



    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("phoneToken")
    @Expose
    public String phoneToken;
    @SerializedName("token")
    @Expose
    public String token;



    public GirisYapData(String token ,String email, String password) {
        this.token = token;
        this.email = email;
        this.password = password;

    }
    public Integer getUserId() {
        return  id;
    }

    public String getToken() {
        return token;
    }

    public String getPhoneToken() {
        return phoneToken;
    }
}
