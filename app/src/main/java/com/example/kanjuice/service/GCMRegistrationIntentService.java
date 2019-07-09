package com.example.kanjuice.service;

import android.app.IntentService;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.kanjuice.R;
import com.example.kanjuice.util.Logger;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

public class GCMRegistrationIntentService extends IntentService {
    private static final String TAG = "RegIntentService";
    public static final String REGISTRATION_SUCESS = "registrationSucess";
    public static final String REGISTRATION_FAILD = "registrationFailed";
    ;
    Logger logger = Logger.loggerFor(GCMRegistrationIntentService.class);

    public GCMRegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        registerToGCM();
    }

    public void registerToGCM() {

        Intent registrationComplete;
        String token = null;
        try {
            InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
            logger.d("senderId is :" + getString(R.string.senderId));
            token = instanceID.getToken(getString(R.string.senderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            logger.d("GCMIntentService token : " + token);
            registrationComplete = new Intent(REGISTRATION_SUCESS);
            registrationComplete.putExtra("token", token);

        } catch (Exception e) {
            registrationComplete = new Intent(REGISTRATION_FAILD);
            logger.e("GCMIntentService registration failed " + token);
        }
        //send broadcast
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }
}
