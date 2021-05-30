package com.mygarden.ui;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mygarden.PlantWateringService;
import com.mygarden.R;
import com.mygarden.ui.PlantTypesAdapter;
import com.mygarden.provider.PlantContract;
import com.mygarden.utils.PlantUtils;

// done
//
// Warning
// dont change the index of ARRAYS plant types
public class AddPlantActivity extends AppCompatActivity {

    private RecyclerView mTypesRecyclerView;
    private PlantTypesAdapter mTypesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        // Plant types are displayed as a recycler view using PlantTypesAdapter
        mTypesAdapter = new PlantTypesAdapter(this);
        mTypesRecyclerView = (RecyclerView) findViewById(R.id.plant_types_recycler_view);

        mTypesRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );
        mTypesRecyclerView.setAdapter(mTypesAdapter);

    }

    public void onPlantTypeClick(View view) {

        // When the chosen plant type is clicked, create a new plant and set the creation time and
        // water time to now
        // Extract the plant type from the tag
        ImageView imgView = (ImageView) view.findViewById(R.id.plant_type_image);
        int plantType = (int) imgView.getTag();

        Log.v("Hello plant",plantType+"");
        long timeNow = System.currentTimeMillis();

        // Insert the new plant into DB
        ContentValues contentValues = new ContentValues();
        contentValues.put(PlantContract.PlantEntry.COLUMN_PLANT_TYPE, plantType);
        contentValues.put(PlantContract.PlantEntry.COLUMN_CREATION_TIME, timeNow);
        contentValues.put(PlantContract.PlantEntry.COLUMN_LAST_WATERED_TIME, timeNow);

        getContentResolver().insert(PlantContract.PlantEntry.CONTENT_URI, contentValues);

        PlantWateringService.startActionUpdatePlantWidgets(this);

        Toast.makeText(getApplicationContext(),"Plant of type "+ PlantUtils.getPlantTypeName(getApplicationContext(),plantType) +" Added Successfully ..!",Toast.LENGTH_SHORT).show();

        // Close this activity
        finish();
    }

    public void onBackButtonClick(View view) {
        finish();
    }
}
