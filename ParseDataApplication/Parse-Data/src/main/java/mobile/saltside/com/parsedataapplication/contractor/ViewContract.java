package mobile.saltside.com.parsedataapplication.contractor;

import java.util.ArrayList;

import mobile.handler.com.handler_lib.ServeResponseMessage;

public interface ViewContract {

    void loadRxData(ArrayList<ServeResponseMessage> response);
    void loadRetroData(ArrayList<ServeResponseMessage> response);

    void showRxInProcess();

    void showRxFailure(Throwable e);
}
