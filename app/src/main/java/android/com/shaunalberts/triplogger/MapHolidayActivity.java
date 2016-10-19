package android.com.shaunalberts.triplogger;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Shaun      19-October-2016               Initial
 */
public class MapHolidayActivity extends SingleFragmentActivity {

    private static final String EXTRA_MAP_ID = "android.com.shaunalberts.triplogger.trip_map_id";

    public static Intent newIntent(Context context, UUID tripId) {
        Intent intent = new Intent(context, MapHolidayActivity.class);
        intent.putExtra(EXTRA_MAP_ID, tripId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID tripId = (UUID) getIntent().getSerializableExtra(EXTRA_MAP_ID);
        return MapHolidayFragment.newInstance(tripId);
    }
}
