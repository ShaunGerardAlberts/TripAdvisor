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
 * Still to do:  Only allow the SettingTable to contain one record, if it has a record, update when
 * changed are attempted.
 *
 * Shaun      02-October-2016          Initial
 * Shaun      03-October-2016          Add insert and update functionality
 * Shaun      04-October-2016          Only record can be entered, save changes save automatically - no button
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
        //if mSetting is null, there is no record in table, add blank record, the user can then
        //update that.
        if (mSetting == null) {
            mSetting = new Setting();
            mSetting.setStudentName("");
            mSetting.setIdNum("");
            mSetting.setEmail("");
            mSetting.setGender("");
            mSetting.setComment("");
            SettingLab settingLab = SettingLab.get(getActivity());
            settingLab.addSetting(mSetting);
        }
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

        mNameField.setText(mSetting.getStudentName());
        mIdNum.setText(mSetting.getIdNum());
        mEmail.setText((mSetting.getEmail()));
        mGender.setText(mSetting.getGender());
        mComment.setText(mSetting.getComment());

//        this.saveButtonPressed(v);

        //change data as user enters new data
        //Name edit field
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //intentionally blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mSetting.setStudentName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //intentionally blank
            }
        });
        //id edit field
        mIdNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //intentionally blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mSetting.setIdNum(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //intentionally blank
            }
        });

        //email edit field
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //intentionally blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mSetting.setEmail(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //intentionally blank
            }
        });
        //gender edit field
        mGender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //intentionally blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mSetting.setGender(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //intentionally blank
            }
        });
        //comment edit field
        mComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //intentionally blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mSetting.setComment(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //intentionally blank
            }
        });


        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        SettingLab settingLab = SettingLab.get(getActivity());
            settingLab.updateSetting(mSetting);
    }

//    private void saveButtonPressed(View v) {
//        //Save any changes made to User Profile
//        mSaveButton = (Button) v.findViewById(R.id.settings_save_button);
//        mSaveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SettingLab settingLab = SettingLab.get(getActivity());
//                //create a new Setting object get entered text from view.
//                Setting fmSetting = new Setting();
//                fmSetting.setStudentName(mNameField.getText().toString());
//                fmSetting.setIdNum(mIdNum.getText().toString());
//                fmSetting.setEmail(mEmail.getText().toString());
//                fmSetting.setGender(mGender.getText().toString());
//                fmSetting.setComment(mComment.getText().toString());
//                //now insert or update
//                if (existingRecord) {
//                    settingLab.updateSetting(fmSetting);
//                } else {
//                    settingLab.addSetting(fmSetting);
//                }
//                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}
