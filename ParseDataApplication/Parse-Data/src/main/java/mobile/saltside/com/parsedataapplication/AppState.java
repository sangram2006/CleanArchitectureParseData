package mobile.saltside.com.parsedataapplication;

import java.util.ArrayList;

import mobile.handler.com.handler_lib.ServeResponseMessage;

/**
 * Created by sangram.
 */
public class AppState {
    private ArrayList<ServeResponseMessage> allData = null;

    public void setAllData(ArrayList<ServeResponseMessage> allData) {
        this.allData = allData;
    }

    public ArrayList<ServeResponseMessage> getAllData() {
        return this.allData;
    }
}
