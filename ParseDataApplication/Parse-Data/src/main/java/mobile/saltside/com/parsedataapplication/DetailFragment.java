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
    public final static String DETAILS_RESPONSE = "DETAILS_RESPONSE";

    public static DetailFragment newInstance(ServeResponseMessage serverresponse) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DETAILS_RESPONSE, serverresponse);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        response = getArguments().getParcelable(DETAILS_RESPONSE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        //Need to handle the Parcelable object of server response currently hard coded.
        ImageView bindImage = rootView.findViewById(R.id.image);
        TextView title = rootView.findViewById(R.id.detail_title);
        TextView description = rootView.findViewById(R.id.detail_description);
        title.setText(response.getTitle());
        description.setText(response.getBody());
        String pathToFile = "" + response.getId();
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

