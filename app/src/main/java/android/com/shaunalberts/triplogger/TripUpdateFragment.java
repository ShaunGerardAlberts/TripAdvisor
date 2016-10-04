package android.com.shaunalberts.triplogger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
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
 *
 */
public class TripUpdateFragment extends Fragment {

    private static final String ARG_TRIP_ID = "trip_id";
    private static final String DIALOG_DATE = "DialogDate";//for the dateDialog(DatePickerDialog)
    private static final int REQUEST_DATE = 0;//used when setting target of the dialog

    private Trip mTrip;
    private EditText mTitleField;
    private Button mDateButton;
    private EditText mDestinationField;
    private EditText mDuration;
    private EditText mComment;
    private EditText mGPSLocation;
    private Spinner mTripTypeSpinner;

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
        UUID tripId = (UUID) getArguments().getSerializable(ARG_TRIP_ID);
        mTrip = TripLab.get(getActivity()).getTrip(tripId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
        View v = inflater.inflate(R.layout.fragment_trip_detail, container, false);

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
        mGPSLocation.setText(mTrip.getGpsLoction());


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
        }
    }

    //Sets the text of the DateButton to the correctly formatted date
    public void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        String setDate = sdf.format(mTrip.getDate());
        mDateButton.setText(setDate);
    }

}

























