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
import java.sql.Statement;
import java.util.Base64;

public class MainActivity2 extends AppCompatActivity {

    ImageView image;
    EditText Manufacturer,Model,Storage,Color,Cost;
    Mobile_phone mask;
    Connection connection;
    View v;
    String img="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        mask = getIntent().getParcelableExtra("Mobile_phone");
        image = findViewById(R.id.UpPhoto);

        Manufacturer=findViewById(R.id.Ed_Manufacturer);
        Manufacturer.setText(mask.getManufacturer());

        Model=findViewById(R.id.Ed_Model);
        Model.setText(mask.getModel());

        Storage = findViewById(R.id.Ed_Storage);
        Storage.setText(Integer.toString(mask.getStorage()));

        Color=findViewById(R.id.Ed_Color);
        Color.setText(mask.getColor());

        Cost = findViewById(R.id.Ed_Cost);
        Cost.setText(Integer.toString(mask.getCost()));

    }

    private Bitmap getImgBitmap(String encodedImg) {
        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return BitmapFactory.decodeResource(MainActivity2.this.getResources(),
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



    public void Update(View v){

        if (Manufacturer.getText().length()==0 || Model.getText().length()==0  || Storage.getText().length()==0 || Color.getText().length()==0|| Cost.getText().length()==0 )
        {
            Toast.makeText(MainActivity2.this, "???????? ???? ???????????????????? ????????", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            String query="";
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();
            if (connection != null) {
                if(img=="")
                {

                    query = "UPDATE [Mobile phone] Set Manufacturer = '" + Manufacturer.getText() + "', Model = '" + Model.getText() + "', Storage ='" + Storage.getText() + "', Color ='" + Color.getText() +  "', Cost ='" + Cost.getText() + "' WHERE ID= "+mask.getID()+"";

                }
                else
                {

                    query = "UPDATE [Mobile phone] Set Manufacturer = '" + Manufacturer.getText() + "', Model = '" + Model.getText() + "',  Storage ='" + Storage.getText() + "', Color ='" + Color.getText() +  "', Cost ='" + Cost.getText() + "', Image ='" + img + "' WHERE ID= "+mask.getID()+"";
                }
                Statement statement = connection.createStatement();
                Toast.makeText(MainActivity2.this, "???????????? ????????????????", Toast.LENGTH_SHORT).show();
                statement.executeQuery(query);

            }
        }
        catch (Exception ex)
        {

        }
        Next();
    }

    public void Next(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void Delete(View v){
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();
            if (connection != null) {
                String query = "DELETE FROM  [Mobile phone]  WHERE ID= "+mask.getID()+"";
                Statement statement = connection.createStatement();
                statement.executeQuery(query);
            }
        }

        catch (Exception ex)
        {

        }
        Next();
    }
}


