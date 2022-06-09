package com.example.cafevesuviusapp.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cafevesuviusapp.R;

import java.util.List;

public class KitchenOrder_Item_Adapter extends ArrayAdapter<MenuItem_Class> {
    Context context;
    int resource;
    List<MenuItem_Class> menuItems;


    public KitchenOrder_Item_Adapter( Context context, int resource, List<MenuItem_Class> menuItems) {
        super(context, resource, menuItems);
        this.context = context;
        this.resource = resource;
        this.menuItems = menuItems;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null, false);
        TextView menuItem_name = view.findViewById(R.id.prepare_order_menuItem_name);
        MenuItem_Class menuItem = menuItems.get(position);
        menuItem_name.setText(menuItem.name);
        return view;
    }
}
