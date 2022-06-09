package com.example.cafevesuviusapp.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cafevesuviusapp.R;

import java.util.List;

public class KitchenOrder_Main_Adapter  extends ArrayAdapter<Order_Class> {
    Context context;
    int resource;
    List<Order_Class> orderList;
    KitchenOrder_Item_Adapter setupOrder;

    public KitchenOrder_Main_Adapter(Context context, int resource, List<Order_Class> orderList) {
        super(context, resource, orderList);
        this.context = context;
        this.resource = resource;
        this.orderList = orderList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null, false);
        TextView orderId = view.findViewById(R.id.kitchen_order_OrderId);
        ListView orderItems = view.findViewById(R.id.kitchen_order_OrderLV);
        Order_Class order = orderList.get(position);
        //setupOrder = new KitchenOrder_Item_Adapter(context, R.layout.prepare_order_item, order.dishes);
        orderId.setText(order.id);
        //orderItems.setAdapter(setupOrder);
        return view;
    }
}
