package shaishav.com.bebetter.Service;

import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;

import shaishav.com.bebetter.Utils.NetworkRequests;
import shaishav.com.bebetter.Utils.Notification;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){

        Map<String,String> data = remoteMessage.getData();
        NetworkRequests networkRequests = NetworkRequests.getInstance(getApplicationContext());
        networkRequests.getImage(data);
        Log.d("BeBetterGCM", "From: " + remoteMessage.getFrom());

    }


}