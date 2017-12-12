package com.example.ahmedmohsen.project;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ahmedmohsen on 11/30/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    SQLiteDatabase onlineshopingDB;
    public DBHelper(Context context) {
        super(context, "onlineshopingDB", null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Customers(CustID integer primary key autoincrement, email text not null, CustName text not null,Username text not null,Password text not null,Gender text not null,Birthdate date not null,job text not null)");
        sqLiteDatabase.execSQL("create table Categories(CatID integer primary key, CatName text not null)");
        sqLiteDatabase.execSQL("create table Orders(OrdID integer primary key autoincrement, OrdDate date not null, Address text not null, CustID integer, FOREIGN kEY(CustID) REFERENCES Customers (CustID))");
        sqLiteDatabase.execSQL("create table Products(ProID integer primary key, ProName text not null, Price integer, Quantity integer not null, CatID, FOREIGN KEY(CatID) REFERENCES Categoreis (CatID))");
        sqLiteDatabase.execSQL("create table OrderDetails(OrdID integer, ProID integer, Quantity integer not null, FOREIGN kEY(OrdID) REFERENCES Orders(OrdID), FOREIGN KEY(ProID) REFERENCES Products(ProID), Primary Key(OrdID, ProID))");

        ContentValues contentValues = new ContentValues();
        contentValues.put("CustID",1);
        contentValues.put("email", "ahmedmohsen0888@gmail.com");
        contentValues.put("CustName", "ahmed mohsen");
        contentValues.put("Username", "ahmedmohsen08");
        contentValues.put("Password", "01203052224");
        contentValues.put("Gender", "male");
        contentValues.put("Birthdate", "01/09/96");
        contentValues.put("Job", "developer");
        sqLiteDatabase.insert("Customers", null, contentValues);
        contentValues = new ContentValues();
        contentValues.put("CustID",2);
        contentValues.put("email", "a@gmail.com");
        contentValues.put("CustName", "ahmed mohsen");
        contentValues.put("Username", "ahmedmohsen08");
        contentValues.put("Password", "123");
        contentValues.put("Gender", "male");
        contentValues.put("Birthdate", "01/09/96");
        contentValues.put("Job", "developer");
        sqLiteDatabase.insert("Customers", null, contentValues);

        contentValues = new ContentValues();
        contentValues.put("CatID",1);
        contentValues.put("CatName","elec_mobile");
        sqLiteDatabase.insert("Categories", null, contentValues);
        contentValues = new ContentValues();
        contentValues.put("CatID",2);
        contentValues.put("CatName","elec_laptop");
        sqLiteDatabase.insert("Categories", null, contentValues);
        contentValues = new ContentValues();
        contentValues.put("CatID",3);
        contentValues.put("CatName","cloth_men");
        sqLiteDatabase.insert("Categories", null, contentValues);
        contentValues = new ContentValues();
        contentValues.put("CatID",4);
        contentValues.put("CatName","cloth_women");
        sqLiteDatabase.insert("Categories", null, contentValues);


        contentValues = new ContentValues();
        contentValues.put("ProID", 1);
        contentValues.put("ProName","samsung");
        contentValues.put("Price", 10000);
        contentValues.put("Quantity", 50);
        contentValues.put("CatID", 1);
        sqLiteDatabase.insert("Products", null, contentValues);
        contentValues = new ContentValues();
        contentValues.put("ProID", 2);
        contentValues.put("ProName","hp");
        contentValues.put("Price", 5000);
        contentValues.put("Quantity", 20);
        contentValues.put("CatID", 2);
        sqLiteDatabase.insert("Products", null, contentValues);
        contentValues = new ContentValues();
        contentValues.put("ProID", 3);
        contentValues.put("ProName","levis");
        contentValues.put("Price", 1000);
        contentValues.put("Quantity", 700);
        contentValues.put("CatID", 3);
        sqLiteDatabase.insert("Products", null, contentValues);
        contentValues = new ContentValues();
        contentValues.put("ProID", 4);
        contentValues.put("ProName","fem");
        contentValues.put("Price", 2000);
        contentValues.put("Quantity", 90);
        contentValues.put("CatID", 4);
        sqLiteDatabase.insert("Products", null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists Customers");
        sqLiteDatabase.execSQL("drop table if exists Categories");
        sqLiteDatabase.execSQL("drop table if exists Orders");
        sqLiteDatabase.execSQL("drop table if exists Products");
        sqLiteDatabase.execSQL("drop table if exists OrderDetails");
        onCreate(sqLiteDatabase);
    }

    public void createNewCustomer(String email, String CustName, String Username, String Password, String Gender, String birthdate, String job) {
        ContentValues row = new ContentValues();
        row.put("email",email);
        row.put("CustName",CustName);
        row.put("Username",Username);
        row.put("Password",Password);
        row.put("Gender",Gender);
        row.put("birthdate",birthdate);
        row.put("job",job);
        onlineshopingDB=getWritableDatabase();
        onlineshopingDB.insert("Customers", null, row);
        onlineshopingDB.close();
    }

    public String SearchbyEmail(String email) {
        onlineshopingDB=getReadableDatabase();
        String[] row = {email};
        Cursor cursor = onlineshopingDB.rawQuery("select Password from Customers where email like ?",row);
        if((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            onlineshopingDB.close();
            return cursor.getString(0);
        }
        else {
            onlineshopingDB.close();
            return "";
        }
    }

    public boolean SearchforEmailbyEmail(String email) {
        onlineshopingDB=getReadableDatabase();
        String[] row = {email};
        Cursor cursor = onlineshopingDB.rawQuery("select email from Customers where email like ?",row);
        cursor.moveToFirst();
        onlineshopingDB.close();
        if((cursor != null) && (cursor.getCount() > 0)) {
            if (cursor.getString(0).equals(email)) {
                return false;
            } else {
                return true;
            }
        }
        else {
            return true;
        }
    }

    public Cursor SearchforItemsByProName (String proname) {
        onlineshopingDB = getReadableDatabase();
        Cursor cursor = onlineshopingDB.rawQuery("select ProName from Products where ProName like ?", new String[] {"%"+proname+"%"});
        if(cursor != null) {
            cursor.moveToFirst();
        }
        onlineshopingDB.close();
        return cursor;
    }

    public int SearchforCatIDbyCatName (String catname) {
        onlineshopingDB = getReadableDatabase();
        String[] row = {catname};
        Cursor cursor = onlineshopingDB.rawQuery("select CatID from Categories where catName like ?", row);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        //onlineshopingDB.close();
        return Integer.parseInt(cursor.getString(0));
    }

    public Cursor SearchforItemsByCatName (String catname) {
        onlineshopingDB = getReadableDatabase();
        int catID = SearchforCatIDbyCatName(catname);
        String[] row = {String.valueOf(catID)};
        Cursor cursor = onlineshopingDB.rawQuery("select ProName from Products where CatID like ?", row);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        onlineshopingDB.close();
        return cursor;
    }

    public int SEarchforProIDbyProName (String proname) {
        onlineshopingDB=getReadableDatabase();
        Cursor cursor = onlineshopingDB.rawQuery("select ProID from Products where ProName like ?", new String[]{proname});
        cursor.moveToFirst();
        onlineshopingDB.close();
        return Integer.parseInt(cursor.getString(0));
    }

    public Cursor SearchforProduct (int ID) {
        onlineshopingDB=getReadableDatabase();
        Cursor cursor = onlineshopingDB.rawQuery("select * from Products where ProID like ?", new String[]{String.valueOf(ID)});
        cursor.moveToFirst();
        onlineshopingDB.close();
        return cursor;
    }

    public Cursor SearchforCustomer (String email) {
        onlineshopingDB=getReadableDatabase();
        Cursor cursor = onlineshopingDB.rawQuery("select * from Customers where email like ?", new String[]{email});
        cursor.moveToFirst();
        onlineshopingDB.close();
        return cursor;
    }

    public Cursor SearchforCustomerbyID (int ID) {
        onlineshopingDB=getReadableDatabase();
        Cursor cursor = onlineshopingDB.rawQuery("select * from Customers where CustID like ?", new String[]{String.valueOf(ID)});
        cursor.moveToFirst();
        onlineshopingDB.close();
        return cursor;
    }
}
