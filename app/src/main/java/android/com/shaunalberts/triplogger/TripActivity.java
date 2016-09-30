package android.com.shaunalberts.triplogger;

import android.support.v4.app.Fragment;
import android.os.Bundle;

/**
 * Shaun      29-September-2016        Initial
 */

public class TripActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return TripFragment.newInstance();
    }

}
