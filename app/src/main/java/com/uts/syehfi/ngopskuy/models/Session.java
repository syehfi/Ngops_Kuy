package com.uts.syehfi.ngopskuy.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private static final String USERNAME_KEY = "key_username";
    private static final String TOKEN_KEY = "key_token";

    private SharedPreferences preferences;

    public Session(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setSession(String token) {
        preferences.edit().putString(TOKEN_KEY, token)
                .apply();
    }

    public boolean isLoggedIn() {
        String token = preferences.getString(TOKEN_KEY, null);
        return (token != null);
    }

    public boolean validate(String username, String password) {
        if (username.equals("syehfi") && password.equals("1234")) {
            setSession(username);
            return true;
        }
        return false;
    }

    public void logout() {
        preferences.edit().remove(TOKEN_KEY)
                .apply();
    }
}
