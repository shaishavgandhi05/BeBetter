package shaishav.com.bebetter.Receiver;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.ArraySet;
import android.util.Log;

import java.util.Date;
import java.util.Set;

import shaishav.com.bebetter.Data.UsageSource;
import shaishav.com.bebetter.Utils.Constants;

public class PhoneUnlockedReceiver extends BroadcastReceiver {

    public static boolean wasScreenOn = true;
    public long session_time;
    Set<String> set;

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            // do whatever you need to do here

            long lock_time = new Date().getTime();
            Log.e("LOB","Locked at : "+new Date(lock_time).toLocaleString());
            editor.putLong(Constants.LOCKED,lock_time);

            long last_unlock_time = preferences.getLong(Constants.UNLOCKED,0);

            if(last_unlock_time==0) {
                editor.commit();
                return;
            }

            if(dayChanged(lock_time,last_unlock_time)){
                Date previousDate = new Date(last_unlock_time);
                previousDate.setHours(23);
                previousDate.setMinutes(59);

                session_time = previousDate.getTime() - last_unlock_time;

                storeSessionInDb(context,previousDate.getTime(),session_time);

                previousDate.setDate(previousDate.getDate()+1);
                previousDate.setHours(0);
                previousDate.setMinutes(0);

                last_unlock_time = previousDate.getTime();

                session_time = lock_time - last_unlock_time;

                editor.putLong(Constants.UNLOCKED,last_unlock_time);
                editor.putLong(Constants.SESSION,session_time);

            }
            else{
                session_time = lock_time - last_unlock_time;
                long prev_session = preferences.getLong(Constants.SESSION,0);
                editor.putLong(Constants.SESSION,prev_session+session_time);
            }


            editor.commit();

        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            // and do whatever you need to do here
            long last_unlocked = preferences.getLong(Constants.UNLOCKED,0);

            long unlocked = new Date().getTime();
            Log.e("LOB","Unlocked at : "+new Date(unlocked).toLocaleString());
            if(dayChanged(last_unlocked,unlocked) && last_unlocked!=0){
                storeSessionInDb(context,last_unlocked,preferences.getLong(Constants.SESSION,0));
                editor.putLong(Constants.SESSION,0);
            }

            editor.putLong(Constants.UNLOCKED, unlocked);
            editor.commit();

        }else if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            if (keyguardManager.isKeyguardSecure()) {

            }
        }
    }

    private boolean dayChanged(long lock_time,long unlock_time){

        Date lock_date = new Date(lock_time);
        Date unlock_date = new Date(unlock_time);

        return lock_date.getDate()!=unlock_date.getDate();

    }

    public void storeSessionInDb(Context context, long date, long session_time){
        UsageSource usageSource = new UsageSource(context);
        usageSource.open();
        usageSource.createUsage(date,session_time);
        usageSource.close();
    }
}
