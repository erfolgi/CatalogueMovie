package erfolgi.com.cataloguemovie.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;
import erfolgi.com.cataloguemovie.NavBar;
import erfolgi.com.cataloguemovie.R;
import erfolgi.com.cataloguemovie.UserPreference;
import erfolgi.com.cataloguemovie.notification.AlarmReceiver;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreferenceFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.setting) Button setting;
    @BindView(R.id.alert) Switch alert;
    @BindView(R.id.reminder) Switch reminder;
    UserPreference UserPreference;
    Context con;
    AlarmReceiver alarmReceiver;

    public PreferenceFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((NavBar) getActivity())
                .setActionBarTitle(getString(R.string.preference));

        View view = inflater.inflate(R.layout.fragment_preference, container, false);
        con = getActivity();
        ButterKnife.bind(this, view);
        UserPreference = new UserPreference(con);
        alarmReceiver = new AlarmReceiver();

        alert.setChecked(UserPreference.getAlert());

        reminder.setChecked(UserPreference.getReminder());


        setting.setOnClickListener(this);
        alert.setOnClickListener(this);
        reminder.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.setting){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }else if (v.getId()==R.id.alert){
            Boolean alerting = alert.isChecked();

            if(alerting){
                alarmReceiver.setAlert(con, AlarmReceiver.TYPE_ALERT);
                UserPreference.setAlert(true);
            }else{
                alarmReceiver.cancelAlert(con);
                UserPreference.setAlert(false);
            }


        }else if (v.getId()==R.id.reminder){
            Boolean reminding = reminder.isChecked();
            if(reminding){
                alarmReceiver.setReminder(con, AlarmReceiver.TYPE_DAILY);
                UserPreference.setReminder(true);
                reminder.setChecked(true);
                Log.d("[]_[]", "On Click: "+UserPreference.getReminder() );
            }else{
                alarmReceiver.cancelReminder(con);
                UserPreference.setReminder(false);
                reminder.setChecked(false);
                Log.d("[]_[]", "On Click: "+UserPreference.getReminder() );
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
