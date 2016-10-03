package android.com.shaunalberts.triplogger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Still to do:  Only allow the SettingTable to contain one record, if it has a record, update when
 * changed are attempted.
 *
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

    private Setting mSetting;

    public static SettingsFragment newInstance() {
        SettingsFragment settingsFragment = new SettingsFragment();
        return settingsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSetting = SettingLab.get(getActivity()).getSetting("1");//put this in a static method
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

        if (mSetting != null) {
            mNameField.setText(mSetting.getStudentName());
            mIdNum.setText(mSetting.getIdNum());
            mEmail.setText((mSetting.getEmail()));
            mGender.setText(mSetting.getGender());
            mComment.setText(mSetting.getComment());
        }

        //Save any changes made to User Profile
        mSaveButton = (Button) v.findViewById(R.id.settings_save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingLab settingLab = SettingLab.get(getActivity());
                Setting fmSetting = new Setting();
                fmSetting.setStudentName(mNameField.getText().toString());
                fmSetting.setIdNum(mIdNum.getText().toString());
                fmSetting.setEmail(mEmail.getText().toString());
                fmSetting.setGender(mGender.getText().toString());
                fmSetting.setComment(mComment.getText().toString());
                settingLab.addSetting(fmSetting);
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        });





        return v;
    }

}
