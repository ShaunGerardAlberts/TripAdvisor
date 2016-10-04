package android.com.shaunalberts.triplogger;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * To add a new trip to the list.
 *
 * TO DO: Refactor this code,  Esp. the sdf date lines.
 *
 * Shaun      04-October-2016          Initial
 *
 */
public class TripNewFragment extends Fragment {

    private static final String DIALOG_DATE = "TripNewFragment.DialogDate";//for the dateDialog(DatePickerDialog)
    private static final int REQUEST_DATE = 0;//used when setting target of the dialog

    private Trip mTrip;
    private EditText mTitleField;
    private Button mDateButton;
    private EditText mDestinationField;
    private EditText mDuration;
    private EditText mComment;
    private EditText mGPSLocation;
    private Spinner mTripTypeSpinner;

    private Button mSaveButton;

    public static TripNewFragment newInstance() {
        TripNewFragment fragment = new TripNewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

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

        mTripTypeSpinner = (Spinner) v.findViewById(R.id.trip_type_spinner);

        //pressed save button
        mSaveButton = (Button) v.findViewById(R.id.trip_save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTrip = new Trip();
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
            }
        });

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
//      not updating here, insert when save button pressed
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
            String stringFormattedDate= sdf.format(date);
            mDateButton.setText(stringFormattedDate);

//            mDateButton.setText(date.toString());
//            mTrip.setDate(date);

//            updateDate();
        }
    }

    //Sets the text of the DateButton to the correctly formatted date
    public void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        String setDate = sdf.format(mTrip.getDate());
        mDateButton.setText(setDate);
    }
}
