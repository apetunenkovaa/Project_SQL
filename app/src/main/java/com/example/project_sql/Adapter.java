package com.example.project_sql;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter extends BaseAdapter {

    private Context mContext;

    public Adapter(Context mContext, List<Mobile_phone> maskList) {
        this.mContext = mContext;
        this.maskList = maskList;
    }

    List<Mobile_phone> maskList;
    @Override
    public int getCount() {
        return maskList.size();
    }

    @Override
    public Object getItem(int i) {
        return maskList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return maskList.get(i).getID();
    }
    private Bitmap getUserImage(String encodedImg)
    {

        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
            return null;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = View.inflate(mContext,R.layout.mask,null);

        TextView Manufacturer = v.findViewById(R.id.tx_Manufacturer);
        TextView Model = v.findViewById(R.id.tx_Model);
        TextView Storage = v.findViewById(R.id.tx_Storage);
        TextView Color = v.findViewById(R.id.tx_Color);
        TextView Cost = v.findViewById(R.id.tx_Cost);
        ImageView Image = v.findViewById(R.id.image);


        Mobile_phone
        mask = maskList.get(position);
        Manufacturer.setText(mask.getManufacturer());
        Model.setText(mask.getModel());
        Storage.setText(Integer.toString(mask.getStorage()));
        Color.setText(mask.getColor());
        Cost.setText(Integer.toString(mask.getCost()));
        Image.setImageBitmap(getUserImage(mask.getImage()));

        return v;
    }

}