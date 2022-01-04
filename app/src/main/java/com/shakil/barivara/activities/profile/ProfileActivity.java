package com.shakil.barivara.activities.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shakil.barivara.R;
import com.shakil.barivara.activities.onboard.MainActivity;
import com.shakil.barivara.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding activityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        //region init objects and perform all UI operations
        initUI();
        bindUiWithComponents();
        //endregion
    }

    //region init objects
    private void initUI() {

    }
    //endregion

    //region perform all UI operations
    private void bindUiWithComponents() {
        activityBinding.backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        activityBinding.editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityBinding.Name.setFocusable(true);
                activityBinding.Address.setFocusable(true);
                activityBinding.DOB.setFocusable(true);
                activityBinding.Name.setFocusableInTouchMode(true);
                activityBinding.Address.setFocusableInTouchMode(true);
                activityBinding.DOB.setFocusableInTouchMode(true);
                activityBinding.editIcon.setVisibility(View.GONE);
                activityBinding.saveCancelLayout.setVisibility(View.VISIBLE);
            }
        });

        activityBinding.cancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityBinding.editIcon.setVisibility(View.VISIBLE);
                activityBinding.saveCancelLayout.setVisibility(View.GONE);
                activityBinding.Name.setFocusable(false);
                activityBinding.Address.setFocusable(false);
                activityBinding.DOB.setFocusable(false);
            }
        });
    }
    //endregion

    //region activity components
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
    }
    //endregion
}