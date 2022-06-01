package com.example.cafevesuviusapp.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cafevesuviusapp.R;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<MenuItem_Class> {

    Context context;
    int resource;
    List<MenuItem_Class> menuItemsList;

    public OrderAdapter(Context context, int resource, List<MenuItem_Class> menuItemsList)
    {
        super(context, resource, menuItemsList);
        this.context = context;
        this.resource = resource;
        this.menuItemsList = menuItemsList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null, false);
        TextView name = view.findViewById(R.id.order_dish_name);
        Button button = view.findViewById(R.id.order_dish_button);
        MenuItem_Class menuItem = menuItemsList.get(position);
        name.setText(menuItem.name);
        button.setId(position);
        return view;
    }
}
