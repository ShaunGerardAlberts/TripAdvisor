package android.com.shaunalberts.triplogger;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Shaun      02-October-2016               Initial
 */
public class SettingsActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, SettingsActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        //still to implement
        return SettingsFragment.newInstance();
    }
}
