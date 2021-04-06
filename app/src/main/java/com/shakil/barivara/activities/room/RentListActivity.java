package com.shakil.barivara.activities.room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.shakil.barivara.R;
import com.shakil.barivara.activities.onboard.MainActivity;
import com.shakil.barivara.adapter.RecyclerRentListAdapter;
import com.shakil.barivara.databinding.ActivityRentListBinding;
import com.shakil.barivara.dbhelper.DbHelperParent;
import com.shakil.barivara.firebasedb.FirebaseCrudHelper;
import com.shakil.barivara.model.room.Rent;
import com.shakil.barivara.utils.UX;

import java.util.ArrayList;

public class RentListActivity extends AppCompatActivity {
    private ActivityRentListBinding activityRentListBinding;
    private RecyclerRentListAdapter recyclerMeterListAdapter;
    private ArrayList<Rent> rentList;
    private TextView noDataTXT;
    private DbHelperParent dbHelperParent;
    private FirebaseCrudHelper firebaseCrudHelper;
    private UX ux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRentListBinding = DataBindingUtil.setContentView(this, R.layout.activity_rent_list);

        activityRentListBinding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //region init UI and perform all UI interactions
        init();
        bindUIWithComponents();
        //endregion
    }

    //region init components
    private void init(){
        rentList = new ArrayList<>();
        dbHelperParent = new DbHelperParent(this);
        firebaseCrudHelper = new FirebaseCrudHelper(this);
        ux = new UX(this);
        noDataTXT = findViewById(R.id.mNoDataMessage);
    }
    //endregion

    //region perform all UI interactions
    private void bindUIWithComponents(){
        setData();

        activityRentListBinding.mAddMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RentListActivity.this, RentDetailsActivity.class));
            }
        });
    }
    //endregion

    //region set recycler data
    private void setData() {
        ux.getLoadingView();
        firebaseCrudHelper.fetchAllRent("rent", new FirebaseCrudHelper.onRentDataFetch() {
            @Override
            public void onFetch(ArrayList<Rent> objects) {
                rentList = objects;
                if (rentList.size() <= 0){
                    noDataTXT.setVisibility(View.VISIBLE);
                    noDataTXT.setText(R.string.no_data_message);
                }
                setRecyclerAdapter();
                ux.removeLoadingView();
            }
        });
    }
    //endregion

    //region set recycler adapter
    private void setRecyclerAdapter(){
        recyclerMeterListAdapter = new RecyclerRentListAdapter(rentList, this);
        activityRentListBinding.mRecylerView.setLayoutManager(new LinearLayoutManager(this));
        activityRentListBinding.mRecylerView.setAdapter(recyclerMeterListAdapter);
        recyclerMeterListAdapter.notifyDataSetChanged();
        recyclerMeterListAdapter.setOnItemClickListener(new RecyclerRentListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Rent rent) {
                startActivity(new Intent(RentListActivity.this, RentDetailsActivity.class).putExtra("rent", rent));
            }
        });
    }
    //endregion

    //region activity components
    @Override
    public void onBackPressed() {
        startActivity(new Intent(RentListActivity.this, MainActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelperParent.close();
    }
    //endregion
}