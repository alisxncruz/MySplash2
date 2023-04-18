package com.example.splash.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.splash.R;
import com.example.splash.infoPass;

import java.io.Serializable;
import java.util.List;

public class MyAdapter extends BaseAdapter implements Serializable
{
    private List<infoPass> list;
    private Context context;
    private LayoutInflater layoutInflater;
    public static String TAG = "Hola";
    public MyAdapter(List<infoPass> list, Context context){
        this.list = list;
        this.context = context;
        if(context != null){
            layoutInflater = LayoutInflater.from(context);
        }
    }

    public boolean isEmptyorNull (){
        return list == null || list.size() == 0;
    }

    @Override
    public int getCount() {
        if(isEmptyorNull()){
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        if(isEmptyorNull()){
            return null;
        }
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView = null;
        TextView textView1 = null;
        ImageView blue = null;
        byte[] imagenBytes = list.get(i).getData();
        Bitmap imagenBitmap = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);
        view = layoutInflater.inflate(R.layout.activity_list_view, null);
        textView = view.findViewById(R.id.redS);
        textView1 = view.findViewById(R.id.contra);
        textView.setText(String.valueOf(list.get(i).getRed()));
        textView1.setText(String.valueOf((list.get(i).getContra())));
        blue = view.findViewById(R.id.blue);
        blue.setImageBitmap(imagenBitmap);
        return view;
    }
}
