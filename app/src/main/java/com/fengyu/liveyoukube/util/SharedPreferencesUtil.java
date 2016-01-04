package com.fengyu.liveyoukube.util;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {

    private Context context;
    private SharedPreferences sharedPreferences;

    private static SharedPreferencesUtil instance = null;

    private SharedPreferencesUtil(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("com.fengyu.liveyoukube", Context.MODE_PRIVATE);
    }

    public static SharedPreferencesUtil getInstance(Context context) {
        if (instance == null) {
            return new SharedPreferencesUtil(context);
        } else {
            return instance;
        }
    }

    public void saveBooleanValue(String key, Boolean value) {
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public Boolean getBooleanValue(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void saveStringValue(String key, String value) {
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getStringValue(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void saveEncryptValue(String key, String value) {
        Editor editor = sharedPreferences.edit();
        String encryptValue = null;
        try {
            InputStream inPublicKey = context.getResources().getAssets().open("rsa_public_key.pem");
            PublicKey publicKey = RSAUtils.loadPublicKey(inPublicKey);
            byte[] encryput = RSAUtils.encryptData(value.getBytes(), publicKey);
            encryptValue = Base64Utils.encode(encryput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.putString(key, encryptValue);
        editor.commit();
    }

    public String getEncryptValue(String key) {
        String result;
        result = sharedPreferences.getString(key, "");
        try {
            InputStream inprivate = context.getResources().getAssets().open("pkcs8_rsa_private_key.pem");
            PrivateKey privateKey = RSAUtils.loadPrivateKey(inprivate);
            byte[] decryputByte = RSAUtils.decryptData(Base64Utils.decode(result), privateKey);
            result = new String(decryputByte);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return result;
    }

}
