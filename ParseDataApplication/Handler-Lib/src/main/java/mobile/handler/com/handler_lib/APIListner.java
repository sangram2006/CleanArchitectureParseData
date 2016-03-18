package mobile.handler.com.handler_lib;

import java.util.ArrayList;

/**
 * Created by sangram.
 */
public interface APIListner {
    public void onSimplePreExecute();

    public void onFailure(String message);

    public void onPostSuccess(ArrayList<ServeResponseMessage>  response);
}
