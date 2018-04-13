package mobile.saltside.com.parsedataapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import mobile.handler.com.handler_lib.ServeResponseMessage;
import mobile.saltside.com.dialogutils.ShowMessage;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, ListOfDataFragment.ListOfDataFragmentListener {
    protected AppState mAppData = new AppState();
    TextView emptySetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getFragmentTransaction();
        mProgressBar = findViewById(R.id.common_loading_display);
        emptySetView = findViewById(R.id.emptyset);
        if (savedInstanceState == null) {
            ListOfDataFragment fragment = ListOfDataFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.my_layout, fragment, ListOfDataFragment.FRAGMENT_TAG)
                    .disallowAddToBackStack()
                    .commit();
        }

    }

    private FragmentTransaction getFragmentTransaction() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager manager = getFragmentManager();
        return manager.beginTransaction();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {
            // Handle the camera action
        } else if (id == R.id.nav_exit) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().registerSticky(this);
    }

    public void onEvent(String message) {
        showLoadingDisplay();
        Log.d("onFailure: ", "onSimplePreExecute");
        EventBus.getDefault().removeStickyEvent(message);

    }

    public void onEvent(ShowMessage message) {
        hideLoadingDisplay();
        showDialog();
        Log.d("onFailure: ", "onSimplePreExecute");
        EventBus.getDefault().removeStickyEvent(message);
    }

    @Override
    public void onItemClicked(ServeResponseMessage response) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.SAVE_DATA, response);
        startActivity(intent);
    }
}
