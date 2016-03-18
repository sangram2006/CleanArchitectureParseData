package mobile.saltside.com.parsedataapplication;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import mobile.handler.com.handler_lib.ServeResponseMessage;
import mobile.saltside.com.service.BaseService;

/**
 * Created by sangram.
 */
public class ListOfDataFragment extends Fragment {
    public static String FRAGMENT_TAG = "ListOfDataFragment";
    public static String SAVED_DATA = "SAVE_DATA";
    private RecyclerView mRecyclerView;
    private CustomAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private HomeActivity mActivity;
    public ArrayList<ServeResponseMessage> mDataList = new ArrayList<ServeResponseMessage>();

    public interface ListOfDataFragmentListener {
        public void onItemClicked(ServeResponseMessage response);
    }

    public static ListOfDataFragment newInstance() {
        return new ListOfDataFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (HomeActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRetainInstance(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_item, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);

        // Setting the LayoutManager.
        mLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Setting the adapter.
        mAdapter = new CustomAdapter(mActivity);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            //mDataList = savedInstanceState.getParcelableArrayList(SAVED_DATA);
        } else if(!isNwConnected(mActivity)){

        }else {
            // Making the API Call
            BaseService.APICall(mActivity);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putParcelableArrayList(SAVED_DATA, mDataList);
        super.onSaveInstanceState(outState);
    }

    public void onEvent(ArrayList<ServeResponseMessage> response) {
        mActivity.hideLoadingDisplay();
        if (response != null) {
            mActivity.mAppData.setAllData(response);
            mDataList = response;
            mActivity.emptySetView.setVisibility(View.INVISIBLE);
            mAdapter.updateList(mDataList);
        }
        EventBus.getDefault().removeStickyEvent(response);
    }

    public static boolean isNwConnected(Context context) {
        if (context == null) {
            return true;
        }
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
        if (nwInfo != null && nwInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
