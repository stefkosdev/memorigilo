package com.stefkos.memorigilo.timechoose;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.stefkos.memorigilo.OnSwipeTouchListener;
import com.stefkos.memorigilo.R;

public class ChooseFragment2 extends Fragment {

    ImageSwitcher is = null;
    View v = null;
    String dtype = null;

    public static int imageIDS[] = null;
    int activeID = 0;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        v = inflater.inflate(R.layout.choose_fragment2, parent, false);
        is = v.findViewById(R.id.imageSwitcher);
        is.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView()
            {
                ImageView IV = new ImageView(v.getContext());
                IV.setScaleType(ImageView.ScaleType.FIT_CENTER);
                IV.setLayoutParams(new ImageSwitcher.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
                return IV;
            }
        });

        String type = this.getArguments().getString("type");
        dtype = type;
        if( type == "medicine")
        {
            imageIDS = new int[ 3 ];
            imageIDS[0] = R.drawable.tabletka_pol;
            imageIDS[1] = R.drawable.tabletka_cala;
            imageIDS[2] = R.drawable.tabletka_antybiotyk;
            is.setImageResource( imageIDS[activeID]);
        }
        else
        {
            imageIDS = new int[ 5 ];
            imageIDS[0] = R.drawable.alarm;
            imageIDS[1] = R.drawable.posilek;
            imageIDS[2] = R.drawable.spotkanie;
            imageIDS[3] = R.drawable.telewizja;
            imageIDS[4] = R.drawable.zegar;
            is.setImageResource( imageIDS[activeID]);
        }

        // Declare in and out animations and load them using AnimationUtils class
        Animation in = AnimationUtils.loadAnimation(v.getContext(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(v.getContext(), android.R.anim.slide_out_right);

        // set the animation type to ImageSwitcher
        is.setInAnimation(in);
        is.setOutAnimation(out);

        // check swipe left/right
        is.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {

            public void onSwipeRight() {
                Toast.makeText(getActivity(), "RIGHT SWIPE", Toast.LENGTH_SHORT).show();
                activeID++;
                if( activeID >= imageIDS.length )
                {
                    activeID = 0;
                }
                is.setImageResource( imageIDS[activeID]);
            }

            public void onSwipeLeft() {
                Toast.makeText(getActivity(), "LEFT SWIPE", Toast.LENGTH_SHORT).show();
                activeID--;
                if( activeID < 0 )
                {
                    activeID = imageIDS.length-1;
                }
                is.setImageResource( imageIDS[activeID]);
            }

            public void onDoubleTouch()
            {
                Toast.makeText(getActivity(), "Double", Toast.LENGTH_SHORT).show();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("type", dtype );
                bundle.putInt("selectedIcon", activeID );

                ChooseActivity.setSelectedMedicIcon( activeID );

                ChooseFragment3 cf = new ChooseFragment3();
                cf.setArguments( bundle );  // pass parameters through bundle
                ft.replace(R.id.mainFragment, cf );
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return v;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }
}