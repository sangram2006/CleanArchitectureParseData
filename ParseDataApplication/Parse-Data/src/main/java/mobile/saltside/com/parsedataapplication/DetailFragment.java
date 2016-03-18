package mobile.saltside.com.parsedataapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

import mobile.handler.com.handler_lib.ServeResponseMessage;

/**
 * Created by sangram..
 */
public class DetailFragment extends Fragment {

    public static String FRAGMENT_TAG = "DetailFragment";
    private static ServeResponseMessage response;

    public static DetailFragment newInstance(ServeResponseMessage serverresponse) {
        DetailFragment fragment = new DetailFragment();
        response = serverresponse;
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        //Need to handle the Parcelable object of server response currently hard coded.
        ImageView bindImage = (ImageView) rootView.findViewById(R.id.image);
        TextView title = (TextView) rootView.findViewById(R.id.detail_title);
        TextView description = (TextView) rootView.findViewById(R.id.detail_description);
        title.setText("terminations map autos sons utilizations");
        description.setText("sterilizer span ticks continuity hubs procurement vision eggs backups cries gap iron conferences torpedo government catchers restaurant destroyers attribute counsel echo overcurrent classes trip environments forecastle giants conspiracies suppression things rope plans bow blots rescuers incline");
        String pathToFile = "http://dummyimage.com/715x350/105B19/907ECC";//response.getImage();
        DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
        downloadTask.execute(pathToFile);
        return rootView;
    }

    private class DownloadImageWithURLTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageWithURLTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String pathToFile = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(pathToFile).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }
}

