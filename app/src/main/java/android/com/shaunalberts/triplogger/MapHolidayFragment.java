package android.com.shaunalberts.triplogger;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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
 * Fragment that creates the marker and google map.
 *
 * Shaun      19-October-2016               Initial
 * Shaun      26-October-2016               Changed newInstance arguments used to use tripId
 */
public class MapHolidayFragment extends SupportMapFragment {

    private static final String ARG_MAP_LATITUDE = "map_lat";
    private static final String ARG_MAP_LONGITUDE = "map_long";

    private GoogleMap mMap;
    private double latitude;
    private double longitude;

    /**
     * Returns an fragment with latitude and longitude set as extras in the Bundle.
     *
     * @param latitude
     * @param longitude
     * @return fragment
     */
    public static MapHolidayFragment newInstance(double latitude, double longitude) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MAP_LATITUDE, latitude);
        args.putSerializable(ARG_MAP_LONGITUDE, longitude);

        MapHolidayFragment fragment = new MapHolidayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Gets latitude and longitude from Bundle that was set in the MapHolidayActivity.  The starts
     * off a Async thread to download the map.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.latitude = (double) getArguments().getSerializable(ARG_MAP_LATITUDE);
        this.longitude = (double) getArguments().getSerializable(ARG_MAP_LONGITUDE);

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                updateUI();
            }
        });
    }

    /**
     * Create a marker and add it to a map.
     */
    private void updateUI() {
        if (mMap == null) {
            return;
        }

        try {
            LatLng locationPoint = new LatLng(latitude, longitude);
            MarkerOptions itemMarket = new MarkerOptions().position(locationPoint);

            mMap.clear();
            mMap.addMarker(itemMarket);

            LatLngBounds bounds = new LatLngBounds.Builder()
                    .include(locationPoint)
                    .build();

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
