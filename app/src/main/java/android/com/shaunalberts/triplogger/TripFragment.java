package android.com.shaunalberts.triplogger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.UUID;
/**
 * save and cancel functionality, date format, think about gps structure
 *
 */

/**
 * Shaun      30-September-2016        Initial
 * Shaun      01-October-2016          Set the text of the TripActivity
 *
 */
public class TripFragment extends Fragment {

    public static final String ARG_TRIP_ID = "trip_id";

    private Trip mTrip;
    private EditText mTitleField;
    private EditText mDateField;
    private EditText mDestinationField;
    private EditText mDuration;
    private EditText mComment;
    private EditText mGPSLocation;
    private Spinner mTripTypeSpinner;

    public static TripFragment newInstance(UUID tripId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRIP_ID, tripId);

        TripFragment fragment = new TripFragment();
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
        mDateField = (EditText) v.findViewById(R.id.trip_date_edit_text);
        mDateField.setOnClickListener(new View.OnClickListener() {//***************************************
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Pressed date EditText", Toast.LENGTH_SHORT).show();
            }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        mDateField.setText(sdf.format(mTrip.getDate()));

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
        mDuration.setText(String.valueOf(mTrip.getDuration()));
        mDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTrip.setDuration(Integer.valueOf(charSequence.toString()));
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

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        TripLab.get(getActivity()).updateTrip(mTrip);
    }

}

























