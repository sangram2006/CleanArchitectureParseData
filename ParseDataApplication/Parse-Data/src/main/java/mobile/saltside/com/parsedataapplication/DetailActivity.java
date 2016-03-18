package mobile.saltside.com.parsedataapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sangram.
 */
public class DetailActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        if (savedInstanceState == null) {
            DetailFragment fragment = new DetailFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.detail_activity, fragment, DetailFragment.FRAGMENT_TAG)
                    .disallowAddToBackStack()
                    .commit();
        }
    }
}
