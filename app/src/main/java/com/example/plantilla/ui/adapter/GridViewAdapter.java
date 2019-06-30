package com.example.plantilla.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.plantilla.R;
import com.example.plantilla.ui.models.ImageItem;

import java.util.ArrayList;
import java.util.List;


public class GridViewAdapter extends ArrayAdapter<ImageItem> {
    //<editor-fold desc="Atributos">
    //Atributos
    private Context context;
    private int layoutResourceId;
    private ArrayList<ImageItem> data = new ArrayList<ImageItem>();
    //</editor-fold>

    //Contructor poliformismo
    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<ImageItem> data){
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context=context;
        this.data=data;
    }

    //metodo de vista para crear componentes de usuario
    public View getView(int position, View convertView, ViewGroup parent){
        //Atributos locales
        View row = convertView;
        ViewHolder holder;
        if(row == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imgeTitle = (TextView)row.findViewById(R.id.text);
            holder.image=(ImageView)row.findViewById(R.id.image);
            row.setTag(holder);
        }else {
            holder=(ViewHolder)row.getTag();

        }

        ImageItem item = data.get(position);
        holder.imgeTitle.setText(item.getTitle());
        holder.image.setImageBitmap(item.getImage());

        return row;
    }

    public GridViewAdapter(Context context, int resource) {
        super(context, resource);
    }

    public GridViewAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public GridViewAdapter(Context context, int resource, ImageItem[] objects) {
        super(context, resource, objects);
    }

    public GridViewAdapter(Context context, int resource, int textViewResourceId, ImageItem[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public GridViewAdapter(Context context, int resource, List<ImageItem> objects) {
        super(context, resource, objects);
    }

    public GridViewAdapter(Context context, int resource, int textViewResourceId, List<ImageItem> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    //Clase para lavista y el titulo
    static  class  ViewHolder{
        TextView imgeTitle;
        ImageView image;
    }
}
