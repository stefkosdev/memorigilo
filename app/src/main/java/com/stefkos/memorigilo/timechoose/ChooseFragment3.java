package com.stefkos.memorigilo.timechoose;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.stefkos.memorigilo.R;

public class ChooseFragment3 extends Fragment {

    ImageButton setByTimeB = null;
    ImageButton setByFoodB = null;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View v = inflater.inflate(R.layout.choose_fragment3, parent, false);
        setByTimeB = (ImageButton) v.findViewById(R.id.setByTimeB);
        setByFoodB = (ImageButton) v.findViewById(R.id.setByFoodB);

        setByTimeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChooseActivity.setTimeType("byTime");
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ChooseFragment2_1 cf = new ChooseFragment2_1();
                ft.replace(R.id.mainFragment, cf );
                ft.addToBackStack(null);
                ft.commit();
            }});

        setByFoodB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChooseActivity.setTimeType("byFood");
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ChooseFragment2_2 cf = new ChooseFragment2_2();
                ft.replace(R.id.mainFragment, cf );
                ft.addToBackStack(null);
                ft.commit();
            }});

        return v;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here

        /*
        Intent intent = new Intent(this, YourClass.class);
 intent.putString("key1", var1);// if its string type
 Intent.putExtra("key2", var2);// if its int type
 startActivity(intent);
         */
    }
}