package com.example.ahmedmohsen.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;

public class ItemDetailsActivity extends AppCompatActivity {

    ScrollableNumberPicker scrollableNumberPicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        scrollableNumberPicker = (ScrollableNumberPicker) findViewById(R.id.numberPicker);
        TextView itemname = (TextView) findViewById(R.id.textView_item_details);
        TextView itemprice = (TextView) findViewById(R.id.textView_itemprice);
        Button addtocart = (Button) findViewById(R.id.button_addtocart);
        String stringitemname = getIntent().getExtras().getString("itemName");
        final int itemid = getIntent().getExtras().getInt("itemID");
        itemname.setText(stringitemname);
        final DBHelper dbHelper = new DBHelper(this);
        String price = dbHelper.SearchforProduct(itemid).getString(2);
        itemprice.setText(price);
        //final ShoppingCartActivity shopcart = new ShoppingCartActivity();
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingCartActivity.list.add(itemid);
                ShoppingCartActivity.list_quantity.add(scrollableNumberPicker.getValue());
                Toast.makeText(getApplicationContext(), "item added to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
