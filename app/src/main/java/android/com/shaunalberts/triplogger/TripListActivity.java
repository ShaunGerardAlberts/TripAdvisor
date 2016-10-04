package android.com.shaunalberts.triplogger;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Shaun      30 September 2016        Initial
 */
public class TripListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, TripListActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return TripListFragment.newInstance();
    }
}
