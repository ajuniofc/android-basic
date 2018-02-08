package br.com.android.androidbasico.agenda.application.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 08/02/2018.
 */

public class UserPreferences {
    private static final String USER_PREFERENCES = "agenda.application.preferences.UserPreferences";
    private static final String URL_BASE = "URL_BASE";
    private Context context;
    private SharedPreferences preferences;

    public UserPreferences(Context context) {
        this.context = context;
        this.preferences = this.context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void saveURLBase(String urlBase) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(URL_BASE, urlBase);
        editor.commit();
    }

    public String getUrlBase(){
        return getPreferences().getString(URL_BASE, null);
    }

    private SharedPreferences.Editor getEditor(){
        return this.preferences.edit();
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }
}
