package android.com.shaunalberts.triplogger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Shaun      30 September 2016        Initial
 */
public class TripListFragment extends Fragment {

    private Button mLogButton;

    private RecyclerView mTripRecyclerView;
    private TripAdapter mAdapter;

    public static TripListFragment newInstance() {
        TripListFragment fragment = new TripListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedStateInstance) {
        View v = inflater.inflate(R.layout.fragment_trip_list, container, false);

        //Log Button - Take user to clean TripActivity screen, where they can add a new trip
        mLogButton = (Button) v.findViewById(R.id.list_log_button);
        mLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), TripActivity.class);//change this, encapsulate it
                startActivity(intent);
            }
        });

        mTripRecyclerView = (RecyclerView) v.findViewById(R.id.trip_recycler_view);
        mTripRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        TripLab tripLab = TripLab.get(getActivity());
        List<Trip> trips = tripLab.getTrips();
        if (mAdapter == null) {
            mAdapter = new TripAdapter(trips);
            mTripRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setTrips(trips);
            mAdapter.notifyDataSetChanged();
        }
    }

    //TripHolder class - extends viewHolder
    private class TripHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDestinationTextView;
        private TextView mDate;

        private Trip mTrip;

        public TripHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            //get links to the id's of the list items
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_trip_title_text_view);
            mDestinationTextView = (TextView) itemView.findViewById(R.id.list_item_trip_destination_text_view);
            mDate = (TextView) itemView.findViewById(R.id.list_item_trip_date_text_view);
        }

        private void bindTrip(Trip trip) {
            mTrip = trip;
            mTitleTextView.setText(mTrip.getTitle());
            mDestinationTextView.setText(mTrip.getDestination());
            mDate.setText(mTrip.getDate().toString());
        }

        @Override
        public void onClick(View v) {
            //when user click a item in the list, get the trip id and intent
            Intent intent = TripActivity.newIntent(getActivity(), mTrip.getId());
            startActivity(intent);
        }
    }

    //TripAdapter class - extends Adapter
    private class TripAdapter extends RecyclerView.Adapter<TripHolder> {
        private List<Trip> mTrips;

        public TripAdapter(List<Trip> trips) {
            mTrips = trips;
        }

        @Override
        public TripHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_trip, parent, false);
            return new TripHolder(view);
        }

        @Override
        public void onBindViewHolder(TripHolder holder, int position) {
            Trip trip = mTrips.get(position);
            holder.bindTrip(trip);
        }

        @Override
        public int getItemCount() {
            return mTrips.size();
        }

        public void setTrips(List<Trip> trips) {
            mTrips = trips;
        }

    }


}
























