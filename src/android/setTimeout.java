package com.commontime.cordova.settimeout;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.SystemClock;

/**
 * This class echoes a string called from JavaScript.
 */
public class setTimeout extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("setTimeout")) {
            int time = args.getInt(0);
            this.doTimeout(time, callbackContext);
            return true;
        }
        return false;
    }

    private void doTimeout(final int time, final CallbackContext callbackContext) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(time);    
                callbackContext.success();
            }
        }).start();
    }
}
