package erfolgi.com.cataloguemovie;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {
    private String KEY_REMINDER = "Reminder";
    private String KEY_ALERT = "Alert";
    private String KEY_FIRST = "First Run";

    private SharedPreferences preferences;

    public UserPreference(Context context) {
        String PREFS_NAME = "UserPref";
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    public void setReminder(Boolean remind) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_REMINDER, remind);
        editor.apply();
    }

    public Boolean getReminder() {
        return preferences.getBoolean(KEY_REMINDER, true);
    }

    public void setAlert(Boolean alert) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_ALERT, alert);
        editor.apply();
    }

    public Boolean getAlert() {
        return preferences.getBoolean(KEY_ALERT, true);
    }

    public void doneFirst() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_FIRST, false);
        editor.apply();
    }

    public Boolean getFirst(){
        return preferences.getBoolean(KEY_FIRST, true);
    }
}
