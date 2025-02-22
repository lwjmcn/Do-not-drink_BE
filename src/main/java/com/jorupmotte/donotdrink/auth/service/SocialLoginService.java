package com.jorupmotte.donotdrink.auth.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class SocialLoginService {
    private final String HTTP_REQUEST = "https://kapi.kakao.com/v2/user/me";

    public String getUserInfo(String accessToken) {
        try{
            String jsonData = "";

            URL url = new URL(HTTP_REQUEST+"?access_token="+accessToken);

            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
            String line;
            while((line = bf.readLine()) != null){
                jsonData += line;
            }
            bf.close();
            return jsonData;
        } catch (Exception e) {
            return "success";
        }
    }

}
