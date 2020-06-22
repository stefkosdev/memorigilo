package com.stefkos.memorigilo.mealtimechoose;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.stefkos.memorigilo.MainActivity;
import com.stefkos.memorigilo.R;
import com.stefkos.memorigilo.timechoose.ChooseActivity;

public class ChooseMealFragment1 extends Fragment {

    Button breakfastB = null;
    Button secondBreakfastB = null;
    Button lunchB = null;
    Button preSupperB = null;
    Button supperB = null;

    //ListView listMealsView;
    Button returnB = null;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        //View v = inflater.inflate(R.layout.mealtime_choose_fragment1, parent, false);
        View v = inflater.inflate(R.layout.ekran07_pory_posilkow, parent, false);

        breakfastB = v.findViewById(R.id.breakfastB);
        secondBreakfastB = v.findViewById(R.id.secondBreakfast);
        lunchB = v.findViewById(R.id.lunchB);
        preSupperB = v.findViewById(R.id.preSupperrB);
        supperB = v.findViewById(R.id.supperB);

        //returnB = (Button) v.findViewById(R.id.returnB);
        ChooseActivity.setAlarmType( null );

        breakfastB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(0 );
            }
        });

        secondBreakfastB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(1 );
            }
        });

        lunchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(2 );
            }
        });

        preSupperB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(3 );
            }
        });

        supperB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(4 );
            }
        });
/*
        listMealsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                MainActivity.mealTimeChoose = position;
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

                ChooseMealFragment2 cf = new ChooseMealFragment2();
                ft.replace(R.id.mainMealChooseFragment, cf );
                ft.addToBackStack(null);
                ft.commit();
            }});

        returnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.mealTimeChoose = 4;

                Intent intent = new Intent(view.getContext(), SettingsActivity.class);
                view.getContext().startActivity(intent);
            }});
*/
        return v;
    }

    //
    //
    //

    void changeFragment( int pos )
    {
        MainActivity.mealTimeChoose = pos;
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

        ChooseMealFragment2 cf = new ChooseMealFragment2();
        ft.replace(R.id.mainMealChooseFragment, cf );
        ft.addToBackStack(null);
        ft.commit();
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here

    }
}