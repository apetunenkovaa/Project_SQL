package com.example.project_sql;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

public class MainActivity3 extends AppCompatActivity {
    ImageView image;
    EditText Manufacturer,Model,Storage,Color,Cost;
    String img="";
    Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        image = findViewById(R.id.UpPhoto);

        Manufacturer=findViewById(R.id.Ed_Manufacturer);

        Model=findViewById(R.id.Ed_Model);

        Storage = findViewById(R.id.Ed_Storage);

        Color=findViewById(R.id.Ed_Color);

        Cost = findViewById(R.id.Ed_Cost);


    }

    private Bitmap getImgBitmap(String encodedImg) {
        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return BitmapFactory.decodeResource(MainActivity3.this.getResources(),
                R.drawable.zaglushka);
    }


    public void onClickChooseImage(View view)
    {
        getImage();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data!= null && data.getData()!= null)
        {
            if(resultCode==RESULT_OK)
            {
                Log.d("MyLog","Image URI : "+data.getData());
                image.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                encodeImage(bitmap);

            }
        }
    }

    private void getImage()
    {
        Intent intentChooser= new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }

    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            img=Base64.getEncoder().encodeToString(bytes);
            return img;
        }
        return "";
    }

    public void Add(View v){
        if (Manufacturer.getText().length()==0 || Model.getText().length()==0  || Storage.getText().length()==0 || Color.getText().length()==0|| Cost.getText().length()==0)
        {
            Toast.makeText(MainActivity3.this, "Не заполнены поля", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String query="";
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();


            if  (connection != null) {
                if(img==null)
                {


                    query = "INSERT INTO [Mobile phone] (Manufacturer, Model, Storage,Color,Cost) VALUES ('" + Manufacturer.getText() + "', '" + Model.getText() + "', '" + Storage.getText() + "', '" + Color.getText() + "', '" + Cost.getText() + "')";
                }
                else
                {
                    query = "INSERT INTO [Mobile phone]  (Manufacturer, Model, Storage,Color,Cost, Image) VALUES ('" +  Manufacturer.getText() + "', '" + Model.getText() + "','" + Storage.getText() + "', '" + Color.getText() + "', '" + Cost.getText() + "',"+ img + ")";
                }
                Statement statement = connection.createStatement();
                Toast.makeText(MainActivity3.this,"Успешно добавлено", Toast.LENGTH_LONG).show();
                ResultSet result = statement.executeQuery(query);


            }
        }

        catch (Exception ex)
        {
            Toast.makeText(MainActivity3.this,"Произошла ошибка", Toast.LENGTH_LONG).show();
        }
        Manufacturer.setText("");
        Model.setText("");
        Storage.setText("");
        Color.setText("");
        Cost.setText("");

        Next();
    }
    public void Next(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

