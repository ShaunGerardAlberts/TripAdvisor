package android.com.shaunalberts.triplogger;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import android.provider.MediaStore;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
/**
 * TripUpdateFragment, gets added to the TripUpdateActivity.  Links with the fragment_trip_detail.xml.
 *
 *
 *Still to do: save and cancel functionality, think about gps structure
 *
 */

/**
 * Shaun      30-September-2016        Initial
 * Shaun      01-October-2016          Set the text of the TripUpdateActivity
 * Shaun      02-October-2016          Add datePicker dialog with dialogs
 * Shaun      04-October-2016          Duration causing app to crash, needs to be changed to String
 * Shaun      04-October-2016          Renamed class from TripFragment to TripUpdateFragment
 * Shaun      09-October-2016          Add photo functionality
 * Shaun      18-October-2016          Started with gps work
 * Shaun      19-October-2016          Started with maps
 * Shaun      26-October-2016          Cleaned up code a bit
 *
 */
public class TripUpdateFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks {

    private static final String ARG_TRIP_ID = "trip_id";
    private static final String DIALOG_DATE = "DialogDate";//for the dateDialog(DatePickerDialog)
    private static final int REQUEST_DATE = 0;//used when setting target of the dialog
    private static final int REQUEST_PHOTO = 1;
    //gps
    private static final String TAG = "LocatorFragment";

    private Trip mTrip;
    private EditText mTitleField;
    private Button mDateButton;
    private EditText mDestinationField;
    private EditText mDuration;
    private EditText mComment;
    private EditText mGPSLocation;//changed to a button
    private Spinner mTripTypeSpinner;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private File mPhotoFile;
    //gps
    private Button mGPSButton;
    private GoogleApiClient mClient;

    //delete
    private Button mDeleteButton;

    public static TripUpdateFragment newInstance(UUID tripId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRIP_ID, tripId);

        TripUpdateFragment fragment = new TripUpdateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID tripId = (UUID) getArguments().getSerializable(ARG_TRIP_ID);
        mTrip = TripLab.get(getActivity()).getTrip(tripId);
        //get location to photo file
        mPhotoFile = TripLab.get(getActivity()).getPhotoFile(mTrip);

        //create a client to use Play Services
        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();
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
    public void onConnectionSuspended(int i) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
        View v = inflater.inflate(R.layout.fragment_trip_detail, container, false);

        addItemAndListeners(v);

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        TripLab.get(getActivity()).updateTrip(mTrip);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mTrip.setDate(date);

           updateDate();
        } else if (requestCode == REQUEST_PHOTO) {
            updatePhotoView();
        }
    }

    //Sets the text of the DateButton to the correctly formatted date
    public void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        String setDate = sdf.format(mTrip.getDate());
        mDateButton.setText(setDate);
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }

    private void addItemAndListeners(View v) {
        //Title - EditText
        mTitleField = (EditText) v.findViewById(R.id.trip_title_edit_text);
        mTitleField.setText(mTrip.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTrip.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //intentionally left blank
            }
        });

        //Date - EditText
        mDateButton = (Button) v.findViewById(R.id.trip_date_edit_text);
        //display DatePickerDialog when user pressed button
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "Pressed date dateButton", Toast.LENGTH_SHORT).show();
                FragmentManager manager = getFragmentManager();
                //DatePickerFragment dialog = new DatePickerFragment();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mTrip.getDate());
                dialog.setTargetFragment(TripUpdateFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });
        updateDate();

        //spinner item, tripType is an int -  representing an index value in the spinner list
        mTripTypeSpinner = (Spinner) v.findViewById(R.id.trip_type_spinner);
        mTripTypeSpinner.setSelection(mTrip.getTripType());

        mTripTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mTrip.setTripType(mTripTypeSpinner.getSelectedItemPosition());
//                Toast.makeText(getContext(), "you clicked : " + mTripTypeSpinner
//                              .getSelectedItemPosition(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //intentionally left blank
            }
        });


        //Destination - EditText
        mDestinationField = (EditText) v.findViewById(R.id.trip_destination_edit_text);
        mDestinationField.setText(mTrip.getDestination());
        mDestinationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTrip.setDestination(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //blank
            }
        });

        //Duration - EditText
        mDuration = (EditText) v.findViewById(R.id.trip_duration_edit_text);
        mDuration.setText(mTrip.getDuration());
        mDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTrip.setDuration(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //blank
            }
        });

        //Comment - EditText
        mComment = (EditText) v.findViewById(R.id.trip_comment_edit_text);
        mComment.setText(mTrip.getComment());
        mComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTrip.setComment(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //blank
            }
        });

        //GPSLocation - EditText
        mGPSLocation = (EditText) v.findViewById(R.id.trip_gps_edit_text);
        try {
            mGPSLocation.setText(mTrip.getGpsLoction().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mGPSButton = (Button) v.findViewById(R.id.trip_gps_button);
        mGPSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getGPSLocation();
                //not sure how to start up the map
                Intent mapIntent = MapHolidayActivity.newIntent(getContext(), mTrip.getId());
                startActivity(mapIntent);
            }
        });


        //Delete button
        mDeleteButton = (Button) v.findViewById(R.id.trip_delete_button);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TripLab.get(getActivity()).deleteTrip(mTrip);
                //take user back to TripListActivity
                Intent intent = TripListActivity.newIntent(getContext());
                startActivity(intent);
            }
        });

        //Photo capture and display

        //used to determine if the is a camera app available
        PackageManager packageManager = getActivity().getPackageManager();

        mPhotoButton = (ImageButton) v.findViewById(R.id.trip_camera_image_button);
        mPhotoView = (ImageView) v.findViewById(R.id.trip_photo_image_view);

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = mPhotoButton != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        //if camera app exists, tell intent to store photo in provided location
        if (canTakePhoto) {
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        //when photo button pressed fire off intent
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
//                Toast.makeText(getContext(), "pressed", Toast.LENGTH_SHORT).show();
            }
        });
        //display photo if available
        updatePhotoView();

    }

}

























