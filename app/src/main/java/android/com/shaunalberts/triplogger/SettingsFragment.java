package android.com.shaunalberts.triplogger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Shaun      02-October-2016          Initial
 * Shaun      03-October-2016          Add insert and update functionality
 */
public class SettingsFragment extends Fragment {

    private EditText mNameField;
    private EditText mIdNum;
    private EditText mEmail;
    private EditText mGender;
    private EditText mComment;
    private Button mSaveButton;

    public static SettingsFragment newInstance() {
        SettingsFragment settingsFragment = new SettingsFragment();
        return settingsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);


        //get links to EditText fields
        mNameField = (EditText) v.findViewById(R.id.settings_name_edit_text);
        mIdNum = (EditText) v.findViewById(R.id.settings_id_edit_text);
        mEmail = (EditText) v.findViewById(R.id.settings_email_edit_text);
        mGender = (EditText) v.findViewById(R.id.settings_gender_edit_text);
        mComment = (EditText) v.findViewById(R.id.settings_comment_edit_text);


        //get a Setting object
        Setting setting = new Setting();
        try {
            setting = setting.getSetting();//returns null if cursor is null(no value in steeingTable)
            mNameField.setText(setting.getStudentName());
            mIdNum.setText(setting.getIdNum());
            mEmail.setText(setting.getEmail());
            mGender.setText(setting.getGender());
            mComment.setText(setting.getComment());
        } catch (NullPointerException npe) {

        }

        //Save any changes made to User Profile
        mSaveButton = (Button) v.findViewById(R.id.settings_save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setting.setStudentName(mNameField.getText());

                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        });





        return v;
    }

}
