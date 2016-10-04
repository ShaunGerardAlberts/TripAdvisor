package android.com.shaunalberts.triplogger;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Add new trip to list.
 *
 * Shaun      04-October-2016          Initial
 */
public class TripNewActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, TripNewActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return TripNewFragment.newInstance();
    }
}
