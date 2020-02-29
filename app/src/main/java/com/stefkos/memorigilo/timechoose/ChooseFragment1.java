package com.stefkos.memorigilo.timechoose;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.stefkos.memorigilo.R;

public class ChooseFragment1 extends Fragment {

    Button medicB = null;
    Button otherB = null;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation. 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View v = inflater.inflate(R.layout.choose_fragment1, parent, false);
        medicB = (Button) v.findViewById(R.id.medicineB);
        otherB = (Button) v.findViewById(R.id.othersB);

        ChooseActivity.setAlarmType( null );

        medicB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("type", "medicine" );

                ChooseActivity.setAlarmType( "medicine");

                ChooseFragment2 cf = new ChooseFragment2();
                cf.setArguments( bundle );  // pass parameters through bundle
                ft.replace(R.id.mainFragment, cf );
                ft.addToBackStack(null);
                ft.commit();
            }});

        otherB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("type", "other" );

                ChooseActivity.setAlarmType( "other");

                ChooseFragment2 cf = new ChooseFragment2();
                cf.setArguments( bundle );  // pass parameters through bundle
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