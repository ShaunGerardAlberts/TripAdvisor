package android.com.shaunalberts.triplogger;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Shaun      04-October-2016          Initial
 * Shaun      26-October-2016          Added GPS and Maps
 *
 */
public class TripNewFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks {

    private static final String DIALOG_DATE = "TripNewFragment.DialogDate";//for the dateDialog(DatePickerDialog)
    private static final int REQUEST_DATE = 0;//used when setting target of the dialog
    private static final int REQUEST_PHOTO = 1;

    private Trip mTrip;
    private EditText mTitleField;
    private Button mDateButton;
    private EditText mDestinationField;
    private EditText mDuration;
    private EditText mComment;
    private EditText mGPSLocation;
    private Spinner mTripTypeSpinner;
    //gps
    private Button mGPSButton;
    private GoogleApiClient mClient;

    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private File mPhotoFile;

    private Button mSaveButton;
    private Button mCancelButton;

    public static TripNewFragment newInstance() {
        TripNewFragment fragment = new TripNewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create a client to use Play Services
        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();
        //initialise mTrip here when creating a new Trip
        mTrip = new Trip();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);
        //send off request and listen for the Locations to come back
        LocationServices.FusedLocationApi.requestLocationUpdates(mClient, request, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String gpsCord = location.getLatitude() + ";" + location.getLongitude();
                mGPSLocation.setText(gpsCord);
                mTrip.setGpsLocation(gpsCord);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mClient.disconnect();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    boolean canTakePhoto = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_new_trip, container, false);

        mTitleField = (EditText) v.findViewById(R.id.trip_title_edit_text);
        mDateButton = (Button) v.findViewById(R.id.trip_date_edit_text);

        //initialise the date on the button
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        mDateButton.setText(sdf.format(new Date()));
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "Pressed date dateButton", Toast.LENGTH_SHORT).show();
                FragmentManager manager = getFragmentManager();
                //DatePickerFragment dialog = new DatePickerFragment();
                Date now = new Date();
                DatePickerFragment dialog = DatePickerFragment.newInstance(now);
                dialog.setTargetFragment(TripNewFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mDestinationField = (EditText) v.findViewById(R.id.trip_destination_edit_text);
        mDuration = (EditText) v.findViewById(R.id.trip_duration_edit_text);
        mComment = (EditText) v.findViewById(R.id.trip_comment_edit_text);
        mGPSLocation = (EditText) v.findViewById(R.id.trip_gps_edit_text);
        mGPSLocation.setEnabled(false);

        mTripTypeSpinner = (Spinner) v.findViewById(R.id.trip_type_spinner);

        //Photo capture and display
        //used to determine if the is a camera app available
        PackageManager packageManager = getActivity().getPackageManager();

        mPhotoButton = (ImageButton) v.findViewById(R.id.trip_camera_image_button);
        mPhotoView = (ImageView) v.findViewById(R.id.trip_photo_image_view);

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //can this device take photos
        canTakePhoto = mPhotoButton != null && captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        mPhotoFile = TripLab.get(getActivity()).getPhotoFile(mTrip);
        //when photo button pressed fire off intent
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.fromFile(mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        //Google Maps - When the View Map button is pressed
        mGPSButton = (Button) v.findViewById(R.id.trip_gps_button);
        mGPSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //get longitude and latitude, [0] latitude, [1] longitude
                    String[] gpsLocation = mTrip.getGpsLoction().split(";");
                    Intent mapIntent = MapHolidayActivity.newIntent(getContext(),
                            Double.parseDouble(gpsLocation[0]), Double.parseDouble(gpsLocation[1]));
                    startActivity(mapIntent);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Please change GPS coordinates", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //pressed save button
        mSaveButton = (Button) v.findViewById(R.id.trip_save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mTrip.setTitle(mTitleField.getText().toString());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
                try {
                    Date date = sdf.parse(mDateButton.getText().toString());
                    mTrip.setDate(date);
                } catch (ParseException pe) {
                    pe.printStackTrace();
                }
                mTrip.setTripType(mTripTypeSpinner.getSelectedItemPosition());
                mTrip.setDestination(mDestinationField.getText().toString());
                mTrip.setDuration(mDuration.getText().toString());
                mTrip.setComment(mComment.getText().toString());

                //Now insert the new trip record into the table
                TripLab.get(getActivity()).addTrip(mTrip);
                Toast.makeText(getActivity(), "Saved" , Toast.LENGTH_SHORT).show();
                //return to trips list
                Intent intent = TripListActivity.newIntent(getContext());
                startActivity(intent);
            }
        });

        //cancel button
        mCancelButton = (Button) v.findViewById(R.id.trip_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = TripListActivity.newIntent(getContext());
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        //not updating here, insert when save button pressed
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {//date dialog
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
            String stringFormattedDate= sdf.format(date);
            mDateButton.setText(stringFormattedDate);
        } else if (requestCode == REQUEST_PHOTO) {//photo application
            updatePhotoView();
        }
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }
}
