package android.com.shaunalberts.triplogger;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.UUID;

/**
 * Shaun      29-September-2016        Initial
 * Shaun      04-October-2016          Renamed class from TripActivity to TripUpdateActivity
 * Shaun      18-October-2016          Added GPS
 * Shaun      19-October-2016          Added Google Maps
 */

public class TripUpdateActivity extends SingleFragmentActivity {

    public static final String EXTRA_CRIME_ID = "android.com.shaunalberts.triplogger.crime_id";
    //for gps location services not available
    private static final int REQUEST_ERROR = 0;

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

    @Override
    public void onResume() {
        super.onResume();

        //Make sure that the app has Play store installed
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();

        int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode, this, REQUEST_ERROR,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            //Leave if services are unavailable
                            finish();
                        }
                    });
            errorDialog.show();
        }

    }

}
