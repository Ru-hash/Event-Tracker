package com.rurab.eventtrack;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView t; EditText docid,type,location,fees,appointment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void load(View v){

        MyDBHandler db=new MyDBHandler(this,null,null,1);
        t=findViewById(R.id.t);
        docid=findViewById(R.id.editText3);
        type=findViewById(R.id.editText4);
        location=findViewById(R.id.editText);
        fees=findViewById(R.id.editText2);
        appointment=findViewById(R.id.editText6);

        String pass=db.loadHandler();
        Intent i=new Intent(getApplicationContext(),Docres.class);
        Bundle b=new Bundle();
        b.putString("key",pass);
        i.putExtras(b);
        startActivity(i);
        //t.setText(db.loadHandler());
        docid.setText("");
        type.setText("");
        location.setText("");
        fees.setText("");
        appointment.setText("");


    }

    public void add(View v){
        try {
            MyDBHandler db=new MyDBHandler(this,null,null,1);
            t=findViewById(R.id.t);
            docid=findViewById(R.id.editText3);
            type=findViewById(R.id.editText4);
            location=findViewById(R.id.editText);
            fees=findViewById(R.id.editText2);
            appointment=findViewById(R.id.editText6);

            int id=Integer.parseInt(docid.getText().toString());
            String ty=type.getText().toString();
            String loc=location.getText().toString();
            int fee=Integer.parseInt(fees.getText().toString());
            int app=Integer.parseInt(appointment.getText().toString());

            doctor doc =new doctor(id,ty,loc,fee,app);
            db.addHandler(doc);
            docid.setText("");
            type.setText("");
            location.setText("");
            fees.setText("");
            appointment.setText("");

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    public void find(View v){
        String pass;
        MyDBHandler db=new MyDBHandler(this,null,null,1);
        t=findViewById(R.id.t);
        docid=findViewById(R.id.editText3);
        type=findViewById(R.id.editText4);
        location=findViewById(R.id.editText);
        fees=findViewById(R.id.editText2);
        appointment=findViewById(R.id.editText6);

        doctor doc=db.findHandler(location.getText().toString(),type.getText().toString());
        if(doc!=null){
            pass="Doctor ID:"+doc.getid()+" Location: "+doc.getlocation()+" Fees: "+doc.getfees();
            Intent i=new Intent(getApplicationContext(),Docres.class);
            Bundle b=new Bundle();
            b.putString("key",pass);
            i.putExtras(b);
            startActivity(i);
            //t.setText(doc.getid()+" "+doc.getlocation()+" "+doc.getfees());
            docid.setText("");
            type.setText("");
            location.setText("");
            fees.setText("");
            appointment.setText("");

        }
        else {

            t.setText("No match found");
            docid.setText("");
            type.setText("");
            location.setText("");
            fees.setText("");
            appointment.setText("");


        }
    }
    public void delete(View v){
        try {
            MyDBHandler db=new MyDBHandler(this,null,null,1);
            t=findViewById(R.id.t);
            docid=findViewById(R.id.editText3);
            type=findViewById(R.id.editText4);
            location=findViewById(R.id.editText);
            fees=findViewById(R.id.editText2);
            appointment=findViewById(R.id.editText6);

            boolean result=db.deleteHandler(Integer.parseInt(docid.getText().toString()));
            if(result){
                docid.setText("");
                type.setText("");
                location.setText("");
                fees.setText("");
                appointment.setText("");

                t.setText("Record Removed");
            }
            else {
                docid.setText("No Match");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


    }
    public void update(View v){
        try {
            MyDBHandler db=new MyDBHandler(this,null,null,1);
            t=findViewById(R.id.t);
            docid=findViewById(R.id.editText3);
            type=findViewById(R.id.editText4);
            location=findViewById(R.id.editText);
            fees=findViewById(R.id.editText2);
            appointment=findViewById(R.id.editText6);

            boolean result=db.updateHandler(Integer.parseInt(docid.getText().toString()),type.getText().toString(),location.getText().toString(),Integer.parseInt(fees.getText().toString()),Integer.parseInt(appointment.getText().toString()));
            if(result){
                docid.setText("");
                type.setText("");
                location.setText("");
                fees.setText("");
                appointment.setText("");

                t.setText("Record Updated");
            }
            else {
                docid.setText("No Match");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


    }
}
class MyDBHandler extends SQLiteOpenHelper {
    //information  of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DB.db";
    public static final String TABLE_NAME = "Doctor";
    public static final String COLUMN_ID = "DoctorID";
    public static final String COLUMN_TYPE = "Type";
    public static final String COLUMN_ADDRESS = "Address";
    public static final String COLUMN_FEES = "Fees";
    public static final String COLUMN_APPOINTMENT = "Appointment";


    //initialize the database
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}
    public String loadHandler() {
        String result = "";
        String query = "Select * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + result_1 +System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }

    public void addHandler(doctor doctor) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, doctor.getid());
        values.put(COLUMN_TYPE, doctor.gettype());
        values.put(COLUMN_ADDRESS, doctor.getlocation());
        values.put(COLUMN_FEES, doctor.getfees());
        values.put(COLUMN_APPOINTMENT, doctor.getapointment());


        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public doctor findHandler(String location,String type) {

        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_ADDRESS + " = " + "'" + location + "'"+" AND "+COLUMN_TYPE+"=" + "'"+type+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        doctor doctor = new doctor();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            doctor.setid(Integer.parseInt(cursor.getString(0)));
            doctor.settype(cursor.getString(1));
            doctor.setlocation(cursor.getString(2));
            doctor.setfees(Integer.parseInt(cursor.getString(3)));
            doctor.setappointment(Integer.parseInt(cursor.getString(4)));

            cursor.close();
        } else {
            doctor = null;
        }
        db.close();
        return doctor;
    }




    public boolean deleteHandler(int docid) {

        boolean result = false;
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + String.valueOf(docid) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        doctor doctor = new doctor();
        if (cursor.moveToFirst()) {
            doctor.setid(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_NAME, COLUMN_ID + "=?",new String[] {
                    String.valueOf(doctor.getid())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    public boolean updateHandler(int docid, String type,String location,int fees,int appointment) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_ID, docid);
        args.put(COLUMN_TYPE, type);
        args.put(COLUMN_ADDRESS, location);
        args.put(COLUMN_FEES, fees);
        args.put(COLUMN_APPOINTMENT,appointment);

        return db.update(TABLE_NAME, args, COLUMN_ID + "=" + docid, null) > 0;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"( "+COLUMN_ID+" INTEGER PRIMARY KEY,"+COLUMN_TYPE+" TEXT,"+COLUMN_ADDRESS+" TEXT,"+COLUMN_FEES+" INTEGER ,"+COLUMN_APPOINTMENT+" INTEGER )";
        db.execSQL(CREATE_TABLE);
    }
}
class doctor {
    private String type, location;
    private int docid, fees, appointment;

    public doctor() {
    }

    public doctor(int docid, String type, String location, int fees, int appointment) {
        this.docid = docid;
        this.type = type;
        this.location = location;
        this.fees = fees;
        this.appointment = appointment;

    }

    public void setid(int id) {
        this.docid = id;
    }

    public int getid() {
        return this.docid;
    }

    public void settype(String type) {
        this.type = type;
    }

    public String gettype() {
        return this.type;
    }

    public void setfees(int fees) {
        this.fees = fees;
    }

    public int getfees() {
        return this.fees;
    }

    public void setlocation(String location) {
        this.location = location;
    }

    public String getlocation() {
        return this.location;
    }

    public void setappointment(int appointment) {
        this.appointment = appointment;
    }

    public int getapointment() {
        return this.appointment;
    }


}
