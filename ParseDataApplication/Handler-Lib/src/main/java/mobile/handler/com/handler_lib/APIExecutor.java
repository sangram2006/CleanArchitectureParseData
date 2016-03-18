package mobile.handler.com.handler_lib;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by sangram.
 */
public class APIExecutor {
    public Executor mExecutor;
    public Handler mHandler;
    private String mEndPoint = "";

    public APIExecutor() {
        mEndPoint = Constant.endURL;
        mExecutor = Executors.newCachedThreadPool();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public APIExecutor(String endPoint) {

    }

    public void execute(APIListner listener) {
        mExecutor.execute(new Execute(listener, mHandler));
    }

    private class Execute extends RunnableSimple {
        private Handler lHandler;

        public Execute(APIListner listener, Handler handler) {
            super(listener);
            lHandler = handler;
        }

        @Override
        public void run() {
            lHandler.post(new PreExecute(mListener));
            if (mEndPoint == null) {
                lHandler.post(new Failure(mListener));
            } else {
                try {
                    Reader reader = TransportManager.getData(mEndPoint);

                    Type listType = new TypeToken<ArrayList<ServeResponseMessage>>() {
                    }.getType();
                    mResponse = new GsonBuilder().create().fromJson(reader, listType);
                } catch (Exception e) {
                    e.printStackTrace();
                    lHandler.post(new Failure(mListener));
                }
                lHandler.post(new PostExecute(mResponse, mListener));
            }
        }
    }

    private abstract class RunnableSimple implements Runnable {
        protected ArrayList<ServeResponseMessage> mResponse;
        protected APIListner mListener;

        public RunnableSimple() {
        }

        public RunnableSimple(APIListner listener) {
            mListener = listener;
        }
    }

    private class PreExecute extends RunnableSimple {

        public PreExecute(APIListner listener) {
            super(listener);
        }

        @Override
        public void run() {
            mListener.onSimplePreExecute();
        }
    }

    private class Failure extends RunnableSimple {

        public Failure(APIListner listener) {
            super(listener);
        }

        @Override
        public void run() {
            mListener.onFailure("Some Internal error occurred, Please try again.");
        }
    }

    private class PostExecute extends RunnableSimple {

        public PostExecute(ArrayList<ServeResponseMessage> response, APIListner listener) {
            super(listener);
            mResponse = response;
        }

        @Override
        public void run() {
            if (mResponse != null) {
                if (mResponse == null) {
                    mListener.onFailure("Some Internal error occurred, Please try again.");
                } else {
                    mListener.onPostSuccess(mResponse);
                }
            }
        }
    }

}
