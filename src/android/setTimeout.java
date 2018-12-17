package com.commontime.cordova.settimeout;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class echoes a string called from JavaScript.
 */
public class setTimeout extends CordovaPlugin {

    Timer timer = new Timer();
    Map<Integer, TimerTask> tasks = new HashMap<Integer, TimerTask>();

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (action.equals("setTimeout")) {
            int time = args.getInt(0);
            int timerId = args.getInt(1);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    callbackContext.success();
                }
            };
            tasks.put(timerId, timerTask);
            timer.schedule(timerTask, time);

            return true;
        }
        if (action.equals("setInterval")) {
            int time = args.getInt(0);
            int timerId = args.getInt(1);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    PluginResult a = new PluginResult(PluginResult.Status.OK);
                    a.setKeepCallback(true);
                    callbackContext.sendPluginResult(a);
                }
            };
            tasks.put(timerId, timerTask);
            timer.schedule(timerTask, 0, time);

            return true;
        }
        if (action.equals("clearTimeout")) {
            int timerId = args.getInt(0);
            tasks.remove(timerId).cancel();
            return true;
        }
        if (action.equals("clearInterval")) {
            int timerId = args.getInt(0);
            tasks.remove(timerId).cancel();
            return true;
        }
        return false;
    }
}
