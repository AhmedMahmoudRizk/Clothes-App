package com.example.ahmedrizk.clothesownerapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ahmed Rizk on 21/08/2017.
 */
public class adapterListview  extends BaseAdapter {
    String items[];
    String type;
    Context context;
    public adapterListview(Context context,String items[], String type) {
        this.items = items;
        this.context=context;
        this.type=type;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int i) {
        return items[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View Convertview, ViewGroup viewGroup) {
        View view = View.inflate(context,R.layout.adapterlistview,null);
        ImageView image=(ImageView)view.findViewById(R.id.iconImage);
        TextView text = (TextView) view.findViewById(R.id.type);
        text.setText(items[i]);
        if(type=="1"){
            if(i==0)
                image.setImageResource(R.drawable.shirtmen);
            else if(i==1)
                image.setImageResource(R.drawable.tshirtmen);
            else if(i==2)
                image.setImageResource(R.drawable.tmen);
            else if(i==3)
                image.setImageResource(R.drawable.othermen);

        }
        else if(type=="2"){
            if(i==0)
                image.setImageResource(R.drawable.shirtchild);
            else if(i==1)
                image.setImageResource(R.drawable.tshirtchild);
            else if(i==2)
                image.setImageResource(R.drawable.tchild);

            else if(i==3)
                image.setImageResource(R.drawable.otherchild);

        }
        else if(type=="3"){
            {
                if(i==0)
                    image.setImageResource(R.drawable.shirtwom);
                else if(i==1)
                    image.setImageResource(R.drawable.tshirtwom);
                else if(i==2)
                    image.setImageResource(R.drawable.twom);

                else if(i==3)
                    image.setImageResource(R.drawable.otherwom);

            }
        }
        return view;
    }
}
