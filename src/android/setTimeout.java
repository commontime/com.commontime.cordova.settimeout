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
    Map<Integer, CallbackContext> callbacks = new HashMap<Integer, CallbackContext>();

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (action.equals("setTimeout")) {
            final int time = args.getInt(0);
            final int timerId = args.getInt(1);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    callbackContext.success();
                    tasks.remove(timerId);
                }
            };
            tasks.put(timerId, timerTask);
            callbacks.put(timerId, callbackContext);
            timer.schedule(timerTask, time);

            return true;
        }
        if (action.equals("setInterval")) {
            final int time = args.getInt(0);
            final int timerId = args.getInt(1);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if(tasks.containsKey(timerId)) {
                        PluginResult a = new PluginResult(PluginResult.Status.OK);
                        a.setKeepCallback(true);
                        callbackContext.sendPluginResult(a);
                    }
                }
            };
            tasks.put(timerId, timerTask);
            callbacks.put(timerId, callbackContext);
            timer.schedule(timerTask, 0, time);

            return true;
        }
        if (action.equals("clearTimeout")) {
            final int timerId = args.getInt(0);
            tasks.remove(timerId).cancel();
            callbacks.remove(timerId).sendPluginResult(new PluginResult(PluginResult.Status.NO_RESULT));
            return true;
        }
        if (action.equals("clearInterval")) {
            final int timerId = args.getInt(0);
            tasks.remove(timerId).cancel();
            callbacks.remove(timerId).sendPluginResult(new PluginResult(PluginResult.Status.NO_RESULT));
            return true;
        }
        return false;
    }
}
