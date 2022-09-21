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

public class MainActivity extends AppCompatActivity {
    Connection connection;
    String ConnectionResult= "";
    Button bt_main2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Next(View view) //переход на новую активность
    {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }


    public void GetTextFromSql(View v){
        TextView ID = findViewById(R.id.tv_ID);
        TextView Manufacturer = findViewById(R.id.tv_Manufacturer);
        TextView Model = findViewById(R.id.tv_Model);
        TextView Storage = findViewById(R.id.tv_Storage);
        TextView Color = findViewById(R.id.tv_Color);
        TextView Cost = findViewById(R.id.tv_Cost);

    try{
        ConnectionHelper connectionHelper = new  ConnectionHelper();
        connection = connectionHelper.connectionClass();

        if(connection!=null)
        {
            String query = "Select * From [Mobile phone]";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                ID.setText(resultSet.getString(1));
                Manufacturer.setText(resultSet.getString(2));
                Model.setText(resultSet.getString(3));
                Storage.setText(resultSet.getString(4));
                Color.setText(resultSet.getString(5));
                Cost.setText(resultSet.getString(6));
            }

        }
        else
        {
            ConnectionResult = "Сheck Connection";
        }
    }
    catch(Exception ex)
    {
        Log.e("Error",ex.getMessage());
    }
    }
}