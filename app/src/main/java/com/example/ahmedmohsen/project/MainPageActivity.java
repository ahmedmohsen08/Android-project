package com.example.ahmedmohsen.project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    View nvigationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nvigationList=getLayoutInflater().inflate(R.layout.nav_header_main_page,null,false);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        final EditText searchfield = (EditText) findViewById(R.id.editText_searchfield);
        Button searchbutton = (Button) findViewById(R.id.button_searchbytext);
        ImageButton voicesearchbutton = (ImageButton) findViewById(R.id.button_searchbyvoice);
        ListView listViewitems = (ListView) findViewById(R.id.listview_items);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1);
        listViewitems.setAdapter(arrayAdapter);
        final DBHelper dbHelperobj = new DBHelper(this);

        setnavmenudata();

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = dbHelperobj.SearchforItemsByProName(searchfield.getText().toString());
                arrayAdapter.clear();
                while (!cursor.isAfterLast()) {
                    arrayAdapter.add(cursor.getString(0));
                    cursor.moveToNext();
                }
            }
        });

        listViewitems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainPageActivity.this, ItemDetailsActivity.class);
                String itemname = ((TextView)view).getText().toString();
                intent.putExtra("itemName", itemname);
                intent.putExtra("itemID", dbHelperobj.SEarchforProIDbyProName(itemname));
                startActivity(intent);
            }
        });
    }

    private void setnavmenudata() {
        DBHelper dbHelper = new DBHelper(this);
        int ID = getIntent().getExtras().getInt("custID");
        String email = dbHelper.SearchforCustomerbyID(ID).getString(1);
        String username = dbHelper.SearchforCustomerbyID(ID).getString(3);
        TextView usernametextview = (TextView) nvigationList.findViewById(R.id.textView_username);
        TextView emailtextview = (TextView) nvigationList.findViewById(R.id.textView_email);
        usernametextview.setText("username");
        emailtextview.setText("email");
        //Log.d("mohsen",usernametextview.getText().toString());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        ListView listViewitems = (ListView) findViewById(R.id.listview_items);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1);
        listViewitems.setAdapter(arrayAdapter);
        arrayAdapter.clear();
        int id = item.getItemId();
        DBHelper dbHelperobj = new DBHelper(this);
        if (id == R.id.nav_elec_mobile) {
            Cursor cursor = dbHelperobj.SearchforItemsByCatName("elec_mobile");
            while (!cursor.isAfterLast()) {
                arrayAdapter.add(cursor.getString(0));
                cursor.moveToNext();
            }
        } else if (id == R.id.nav_elec_laptop) {
            Cursor cursor = dbHelperobj.SearchforItemsByCatName("elec_laptop");
            while (!cursor.isAfterLast()) {
                arrayAdapter.add(cursor.getString(0));
                cursor.moveToNext();
            }
        } else if (id == R.id.nav_cloth_men) {
            Cursor cursor = dbHelperobj.SearchforItemsByCatName("cloth_men");
            while (!cursor.isAfterLast()) {
                arrayAdapter.add(cursor.getString(0));
                cursor.moveToNext();
            }
        } else if (id == R.id.nav_cloth_women) {
            Cursor cursor = dbHelperobj.SearchforItemsByCatName("cloth_women");
            while (!cursor.isAfterLast()) {
                arrayAdapter.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }
        else if(id == R.id.nav_cart) {
            Intent intent = new Intent(MainPageActivity.this, ShoppingCartActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
