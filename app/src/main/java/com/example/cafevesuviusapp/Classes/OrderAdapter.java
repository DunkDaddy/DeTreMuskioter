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
import com.example.cafevesuviusapp.Order;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<MenuItem_Class> {


    Context context;
    int resource;
   public List<MenuItem_Class> menuItemsList;

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
        Button addToOrder = view.findViewById(R.id.order_dish_button);
        MenuItem_Class menuItem = menuItemsList.get(position);
        name.setText(menuItem.name);
        addToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menuItem.category_id == 2)
                {
                    newOrder.assign_Drink(new MenuItem_Class(menuItem.getID(), menuItem.name, menuItem.price, menuItem.description, menuItem.category_id));
                }
                else {
                    newOrder.assign_Dish(new MenuItem_Class(menuItem.getID(), menuItem.name, menuItem.price, menuItem.description, menuItem.category_id));
                }
            }
        });

        return view;




    }
}
