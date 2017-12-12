package com.example.ahmedmohsen.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity implements RecyclerViewAdapter.cardlistener{
    public static List<Integer> list = new ArrayList<Integer>();
    public static List<Integer> list_quantity = new ArrayList<Integer>();
    public static List<String> list_itemname = new ArrayList<String>();
    RecyclerViewAdapter adapter;
    DBHelper dbHelper;
    TextView totalPrice;
    Button checkout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        dbHelper = new DBHelper(this);
        int i = 0;
        list_itemname.clear();
        while (i<list.size()) {
            list_itemname.add(dbHelper.SearchforProduct(list.get(i)).getString(1));
            i++;
        }
         adapter = new RecyclerViewAdapter(list_itemname, list_quantity, this);
        final RecyclerView myView =  (RecyclerView)findViewById(R.id.recyclerview);
        myView.setHasFixedSize(true);
        myView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myView.setLayoutManager(llm);
        checkout = (Button)  findViewById(R.id.button_checkout);
        totalPrice = (TextView) findViewById(R.id.textView_total_price);

        updatetotalprice();

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = 0;
                //while (index< list_quantity.size()) {

                //}
                Intent intent = new Intent(ShoppingCartActivity.this, DeleviryLocationActivity.class);
                startActivity(intent);
            }
        });
    }

    public void updatetotalprice() {
        dbHelper = new DBHelper(this);
        int i = 0;
        int price = 0;
        while (i<list.size()) {
            price += dbHelper.SearchforProduct(list.get(i)).getInt(2) * list_quantity.get(i);
            i++;
        }
        totalPrice.setText(String.valueOf(price));
        dbHelper.close();
    }

    @Override
    public void remove_card(int position) {
        adapter.myValues.remove(position);
        adapter.notifyItemRemoved(position);
        list.remove(position);
        list_quantity.remove(position);
    }
}
