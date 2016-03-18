package mobile.handler.com.handler_lib;

/**
 * Created by sangram.
 */
public class ModuleAPI {

    APIExecutor executor = new APIExecutor();

    public void processAPICall(final APIListner listener) {
        executor.execute(listener);
    }
}
