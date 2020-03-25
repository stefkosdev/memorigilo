package com.stefkos.memorigilo.util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.stefkos.memorigilo.R;
import com.stefkos.memorigilo.SettingsActivity;

import java.util.Vector;

public class EventArrayAdapter extends ArrayAdapter{
    private final Context context;
    private final Vector<FoodTimeEntry> values;

    public EventArrayAdapter(Context context, Vector<FoodTimeEntry> values) {
        //super(context, -1);
        super(context, 0 , values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.event_row_layout, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.label);
        Button delete = (Button) rowView.findViewById(R.id.deleteEntry);
        textView.setText( values.get(position).getName() + " ID " + values.get(position).getEventID() );

        DeleteClickListener dcl = new DeleteClickListener( values.get(position).getEventID(), position, this );

        delete.setOnClickListener( dcl );
        /*
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //StatusApplication sapp = (StatusApplication)getApplication();

                //sapp.show_Notification( "Title", "Going to settings");

                Intent intent = new Intent(view.getContext(), SettingsActivity.class);
                view.getContext().startActivity(intent);}
        });
        // change the icon for Windows and iPhone
*/
        return rowView;
    }
}
