package mobile.saltside.com.parsedataapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mobile.handler.com.handler_lib.ServeResponseMessage;

/**
 * Created by sangram.
 */
public class DetailActivity extends AppCompatActivity {
    public static final String SAVE_DATA = "SAVE_DATA";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        Bundle b = getIntent().getExtras();
        if (savedInstanceState == null) {
            if (b != null) {
                DetailFragment fragment = DetailFragment.newInstance((ServeResponseMessage) b.getParcelable(SAVE_DATA));
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.detail_activity, fragment, DetailFragment.FRAGMENT_TAG)
                        .disallowAddToBackStack()
                        .commit();
            }
        }
    }
}
