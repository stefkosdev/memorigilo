package com.stefkos.memorigilo.mealtimechoose;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.stefkos.memorigilo.R;

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
