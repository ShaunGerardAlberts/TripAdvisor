package android.com.shaunalberts.triplogger;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.UUID;

/**
 * Shaun      19-October-2016               Initial
 */
public class MapHolidayFragment extends SupportMapFragment {

    private static final String ARG_TRIP_ID = "trip_id";

    private Trip mTrip;
    private Location mCurrentLocation;
    private GoogleMap mMap;
    private GoogleApiClient mClient;


    public static MapHolidayFragment newInstance(UUID tripId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRIP_ID, tripId);

        MapHolidayFragment fragment = new MapHolidayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID tripId = (UUID) getArguments().getSerializable(ARG_TRIP_ID);
        mTrip = TripLab.get(getActivity()).getTrip(tripId);

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                updateUI();
            }
        });
    }

    private void updateUI() {
        if (mMap == null) {
            return;
        }

        try {
            String[] strArrLongLat = mTrip.getGpsLoction().split(";");
            Double dblLong = Double.parseDouble(strArrLongLat[0]);
            Double dblLat = Double.parseDouble(strArrLongLat[1]);

            Location buildLocation = new Location("Build Location");
            buildLocation.setLongitude(dblLong);
            buildLocation.setLatitude(dblLat);

            mCurrentLocation = buildLocation;
            //LatLng locationPoint = new LatLng(mCurrentLocation.getLongitude(), mCurrentLocation.getLatitude());
            LatLng locationPoint = new LatLng(dblLat, dblLong);
            MarkerOptions itemMarket = new MarkerOptions().position(locationPoint);
            mMap.clear();
            mMap.addMarker(itemMarket);
            LatLngBounds bounds = new LatLngBounds.Builder().include(locationPoint).build();

            int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
            CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, margin);
            mMap.animateCamera(update);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
