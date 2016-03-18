package mobile.saltside.com.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import mobile.handler.com.handler_lib.APIListner;
import mobile.handler.com.handler_lib.ModuleAPI;
import mobile.handler.com.handler_lib.ServeResponseMessage;
import mobile.saltside.com.dialogutils.ShowMessage;

/**
 * Created by sangram.k.mohanty on 3/16/2016.
 */
public class BaseService extends Service implements APIListner {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void APICall(Context context) {
        Intent intent = new Intent(context, BaseService.class);
        context.startService(intent);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            Log.d(BaseService.class.getSimpleName(), "onStartCommand(): is NULL");
            return Service.START_NOT_STICKY;
        }

        ModuleAPI mCore = new ModuleAPI();
        mCore.processAPICall(this);
        Log.d("MSL Start: ", "" + System.currentTimeMillis());
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onSimplePreExecute() {
        EventBus.getDefault().postSticky("");
        Log.d("onSimplePreExecute: ", "onSimplePreExecute");
    }

    @Override
    public void onFailure(String message) {
        EventBus.getDefault().postSticky(new ShowMessage());
        Log.d("onFailure: ", "onSimplePreExecute");
    }

    @Override
    public void onPostSuccess(ArrayList<ServeResponseMessage> response) {
        EventBus.getDefault().postSticky(response);
        Log.d("onPostSuccess: ", response.toString());
    }
}
