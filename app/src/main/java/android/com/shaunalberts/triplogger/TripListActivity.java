package android.com.shaunalberts.triplogger;

import android.support.v4.app.Fragment;

/**
 * Shaun      30 September 2016        Initial
 */
public class TripListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return TripListFragment.newInstance();
    }
}
