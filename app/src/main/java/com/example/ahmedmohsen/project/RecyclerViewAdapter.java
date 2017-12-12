package com.example.ahmedmohsen.project;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmedmohsen on 12/10/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>  {
    public List<String> myValues;
    public cardlistener mcardlistener;
    public List<Integer> quantity;
    ShoppingCartActivity shoppingCartActivity = new ShoppingCartActivity();
    public RecyclerViewAdapter (List<String> myValues, List<Integer> quantity, Context context){
        this.myValues= myValues;
        mcardlistener=(cardlistener) context;
        this.quantity = quantity;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button button_remove;
        public ViewHolder(View v) {
            super(v);
            button_remove = (Button) v.findViewById(R.id.button2);
        }
    }
    public void refresh(){
        myValues.clear();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewitem, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.myTextView.setText(myValues.get(position));
        holder.button_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcardlistener.remove_card(position);
                //shoppingCartActivity.updatetotalprice();
            }
        });
        holder.scrollableNumberPicker.setValue(quantity.get(position));
        holder.button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingCartActivity.list_quantity.set(position,holder.scrollableNumberPicker.getValue());
                Toast.makeText((Context) mcardlistener, "quantity updated", Toast.LENGTH_SHORT).show();
                //shoppingCartActivity.updatetotalprice();
            }
        });
    }


    @Override
    public int getItemCount() {
        return myValues.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView myTextView;
        private ScrollableNumberPicker scrollableNumberPicker;
        Button button_remove;
        Button button_update;
        public MyViewHolder(View itemView) {
            super(itemView);
            myTextView = (TextView)itemView.findViewById(R.id.text_card);
            button_remove = (Button) itemView.findViewById(R.id.button2);
            scrollableNumberPicker = (ScrollableNumberPicker) itemView.findViewById(R.id.numberPicker_horizontal);
            button_update = (Button) itemView.findViewById(R.id.button_updateQuantity);
        }
    }

    public interface cardlistener {
        public void remove_card(int position);
    }
}