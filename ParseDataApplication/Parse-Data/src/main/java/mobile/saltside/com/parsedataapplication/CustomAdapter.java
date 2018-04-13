package mobile.saltside.com.parsedataapplication;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mobile.handler.com.handler_lib.ServeResponseMessage;

/**
 * Created by sangram.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.RecyclerViewHolder> {

    ListOfDataFragment.ListOfDataFragmentListener mListner;
    private ArrayList<ServeResponseMessage> itemsData;

    public CustomAdapter(ListOfDataFragment.ListOfDataFragmentListener listner) {
        this.itemsData = new ArrayList<>();
        mListner = listner;
    }

    public CustomAdapter(ArrayList<ServeResponseMessage> itemsData) {
        this.itemsData = itemsData;
    }

    public void updateList(ArrayList<ServeResponseMessage> data) {
        itemsData = data;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CustomAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_data, null);

        // create ViewHolder

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData

        viewHolder.txtViewTitle.setText(itemsData.get(position).getTitle());
        viewHolder.txtDescription.setText(itemsData.get(position).getBody());
        viewHolder.setItem(itemsData.get(position));


    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ServeResponseMessage item;
        public TextView txtViewTitle;
        public TextView txtDescription;

        public RecyclerViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);
            txtViewTitle = itemLayoutView.findViewById(R.id.title);
            txtDescription = itemLayoutView.findViewById(R.id.description);
        }

        public void setItem(ServeResponseMessage item) {
            this.item = item;
        }

        @Override
        public void onClick(View view) {
            mListner.onItemClicked(item);
            Log.d("Sangram", "onClick " + getPosition() + " " + item);
        }
    }
}

