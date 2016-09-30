package android.com.shaunalberts.triplogger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

/**
 * Shaun      30-September-2016        Initial
 *
 */
public class TripFragment extends Fragment {

    public static final String ARG_TRIP_ID = "trip_id";

    private Trip mTrip;

    public static TripFragment newInstance(UUID tripId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRIP_ID, tripId);

        TripFragment fragment = new TripFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID tripId = (UUID) getArguments().getSerializable(ARG_TRIP_ID);
        if (tripId != null) {
            mTrip = TripLab.get(getActivity()).getTrip(tripId);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        TripLab.get(getActivity()).updateTrip(mTrip);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
        View v = inflater.inflate(R.layout.fragment_trip_detail, container, false);
        return v;
    }


}
