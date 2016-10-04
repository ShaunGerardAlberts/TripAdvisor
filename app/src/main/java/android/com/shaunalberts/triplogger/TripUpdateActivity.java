package android.com.shaunalberts.triplogger;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Shaun      29-September-2016        Initial
 * Shaun      04-October-2016          Renamed class from TripActivity to TripUpdateActivity
 */

public class TripUpdateActivity extends SingleFragmentActivity {

    public static final String EXTRA_CRIME_ID = "android.com.shaunalberts.triplogger.crime_id";

    public static Intent newIntent(Context packageContext, UUID tripId) {
        Intent intent = new Intent(packageContext, TripUpdateActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, tripId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID tripId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return TripUpdateFragment.newInstance(tripId);
    }

}
