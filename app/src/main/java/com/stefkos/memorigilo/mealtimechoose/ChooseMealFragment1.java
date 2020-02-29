package com.stefkos.memorigilo.mealtimechoose;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.stefkos.memorigilo.MainActivity;
import com.stefkos.memorigilo.R;
import com.stefkos.memorigilo.SettingsActivity;
import com.stefkos.memorigilo.timechoose.ChooseActivity;
import com.stefkos.memorigilo.timechoose.ChooseFragment2;

public class ChooseMealFragment1 extends Fragment {
/*
    Button breakfastB = null;
    Button secondBreakfastB = null;
    Button dinnerB = null;
    Button preSupperB = null;
    Button supperB = null;
    */
    ListView listMealsView;
    Button returnB = null;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View v = inflater.inflate(R.layout.mealtime_choose_fragment1, parent, false);

        listMealsView = v.findViewById(R.id.listOfMealsSettings);


        returnB = (Button) v.findViewById(R.id.returnB);
        ChooseActivity.setAlarmType( null );

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

        return v;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here

    }
}