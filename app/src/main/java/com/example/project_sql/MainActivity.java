package com.example.project_sql;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import androidx.annotation.RequiresApi;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Connection connection;
    String ConnectionResult= "";
    Button bt_main2;
    List <Mobile_phone> data;
    ListView listView;
    Adapter pAdapter;
    View v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v = findViewById(com.google.android.material.R.id.ghost_view);
        GetTextFromSql(v);
    }

    public void Next(View v){
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }

    public void enterMobile() {
        pAdapter.notifyDataSetInvalidated();
        listView.setAdapter(pAdapter);
    }


    public void GetTextFromSql(View v){
        data = new ArrayList<Mobile_phone>();
        listView = findViewById(R.id.Mobile_phone);
        pAdapter = new Adapter(MainActivity.this, data);

    try{
        ConnectionHelper connectionHelper = new  ConnectionHelper();
        connection = connectionHelper.connectionClass();
        {
        if(connection!=null)
        {
            String query = "Select * From [Mobile phone]";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                Mobile_phone tempMask = new Mobile_phone
                        (resultSet.getInt("ID"),
                                resultSet.getString("Manufacturer"),
                                resultSet.getString("Model"),
                                Integer.parseInt(resultSet.getString("Storage")),
                                resultSet.getString("Color"),
                                Integer.parseInt(resultSet.getString("Cost")),
                                resultSet.getString("Image")
                        );

                data.add(tempMask);
                pAdapter.notifyDataSetInvalidated();
            }
        }
        connection.close();
        }

    }
    catch(Exception ex)
    {
        Log.e("Error",ex.getMessage());

    }
        enterMobile();
    }


}

