package android.com.shaunalberts.triplogger;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Holds the MapHolidayFragment
 *
 * Shaun      19-October-2016               Initial
 * Shaun      26-October-2016               Changed reliance in tripId to longitude and latitude values
 */

public class MapHolidayActivity extends SingleFragmentActivity {

    private static final String EXTRA_MAP_LATITUDE = "android.com.shaunalberts.triplogger.trip_map_latitude";
    private static final String EXTRA_MAP_LONGITUDE = "android.com.shaunalberts.triplogger.trip_map_longitude";

    public static Intent newIntent(Context context, double latitude, double longitude) {
        Intent intent = new Intent(context, MapHolidayActivity.class);
        intent.putExtra(EXTRA_MAP_LATITUDE, latitude);
        intent.putExtra(EXTRA_MAP_LONGITUDE, longitude);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        double latitude = (double) getIntent().getSerializableExtra(EXTRA_MAP_LATITUDE);
        double longitude = (double) getIntent().getSerializableExtra(EXTRA_MAP_LONGITUDE);
        return MapHolidayFragment.newInstance(latitude, longitude);
    }
}
