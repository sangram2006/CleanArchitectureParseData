package mobile.saltside.com.parsedataapplication;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import mobile.handler.com.handler_lib.NetworkService;
import mobile.handler.com.handler_lib.ServeResponseMessage;
import mobile.saltside.com.parsedataapplication.contractor.ViewContract;
import mobile.saltside.com.parsedataapplication.presenter.PresenterLayer;
import mobile.saltside.com.service.BaseService;

/**
 * Created by sangram.
 */
public class ListOfDataFragment extends Fragment implements ViewContract, View.OnClickListener {
    public static String FRAGMENT_TAG = "ListOfDataFragment";
    public static String SAVED_DATA = "SAVE_DATA";
    private RecyclerView mRecyclerView;
    private CustomAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private HomeActivity mActivity;
    public ArrayList<ServeResponseMessage> mDataList = new ArrayList<>();
    private Button retro, rx, event;
    private PresenterLayer presenterLayer;

    @Override
    public void onClick(View v) {
        if (isNwConnected(mActivity)) {
            switch (v.getId()) {
                case R.id.retroButton:
                    presenterLayer.loadRetroData();
                    disableButton(event);
                    disableButton(retro);
                    disableButton(rx);
                    break;
                case R.id.rxButton:
                    presenterLayer.loadRxData();
                    disableButton(event);
                    disableButton(retro);
                    disableButton(rx);
                    break;
                case R.id.eventBus:
                    BaseService.APICall(mActivity);
                    disableButton(event);
                    disableButton(retro);
                    disableButton(rx);
                    break;
            }
        }
    }

    private void enbaleButton(View view) {
        view.setEnabled(true);
    }

    private void disableButton(View view) {
        view.setEnabled(false);
    }

    public interface ListOfDataFragmentListener {
        void onItemClicked(ServeResponseMessage response);
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
        mRecyclerView = rootView.findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);

        // Setting the LayoutManager.
        mLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Setting the adapter.
        mAdapter = new CustomAdapter(mActivity);
        mRecyclerView.setAdapter(mAdapter);
        retro = rootView.findViewById(R.id.retroButton);
        rx = rootView.findViewById(R.id.rxButton);
        event = rootView.findViewById(R.id.eventBus);
        retro.setOnClickListener(this);
        rx.setOnClickListener(this);
        event.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenterLayer = new PresenterLayer(this, new NetworkService());
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
        } else if (!isNwConnected(mActivity)) {

        } else {

            // Making the API Call

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
        enbaleButton(event);
        enbaleButton(retro);
        enbaleButton(rx);
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

    @Override
    public void loadRxData(ArrayList<ServeResponseMessage> response) {
        mActivity.hideLoadingDisplay();
        if (response != null) {
            mActivity.mAppData.setAllData(response);
            mDataList = response;
            mActivity.emptySetView.setVisibility(View.INVISIBLE);
            mAdapter.updateList(mDataList);
        }
        enbaleButton(event);
        enbaleButton(retro);
        enbaleButton(rx);

    }

    @Override
    public void loadRetroData(ArrayList<ServeResponseMessage> response) {
        mActivity.hideLoadingDisplay();
        if (response != null) {
            mActivity.mAppData.setAllData(response);
            mDataList = response;
            mActivity.emptySetView.setVisibility(View.INVISIBLE);
            mAdapter.updateList(mDataList);
        }
        enbaleButton(event);
        enbaleButton(retro);
        enbaleButton(rx);
    }

    @Override
    public void showRxInProcess() {
        mActivity.showLoadingDisplay();
    }

    @Override
    public void showRxFailure(Throwable e) {
        mActivity.hideLoadingDisplay();
        Toast.makeText(mActivity, "Something wrong", Toast.LENGTH_LONG);

    }
}
