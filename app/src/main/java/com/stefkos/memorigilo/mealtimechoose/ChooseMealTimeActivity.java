package com.stefkos.memorigilo.mealtimechoose;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;

import com.stefkos.memorigilo.R;
import com.stefkos.memorigilo.timechoose.ChooseFragment1;
import com.stefkos.memorigilo.util.FootTimeEntry;

public class ChooseMealTimeActivity extends FragmentActivity {

    //
    //
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_food_time);

        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.add(R.id.mainMealChooseFragment, new ChooseMealFragment1());
        // Complete the changes added above
        ft.commit();
    }
}
