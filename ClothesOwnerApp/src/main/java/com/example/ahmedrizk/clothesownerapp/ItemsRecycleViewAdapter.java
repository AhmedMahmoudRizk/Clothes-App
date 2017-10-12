package com.example.ahmedrizk.clothesownerapp;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by power on 09/07/2017.
 */
public class ItemsRecycleViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> prices=new ArrayList<>();
    private ArrayList<String> ids=new ArrayList<>();
    private ArrayList<Uri> images=new ArrayList<>();

    public ItemsRecycleViewAdapter(Context context, ArrayList<String> pricess
            , ArrayList<String> ids, ArrayList<Uri> images) {
        this.prices=pricess;
        this.ids=ids;
        this.images=images;
        this.context=context;
    }

    @Override
    public int getCount() {
        return prices.size();
    }

    @Override
    public Object getItem(int i) {
        return prices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View view = View.inflate(context,R.layout.gridviewitem,null);
        ImageView image=(ImageView)view.findViewById(R.id.image);
        TextView price = (TextView) view.findViewById(R.id.price);
        TextView id = (TextView) view.findViewById(R.id.idCode);
        price.setText(prices.get(i));
        id.setText(ids.get(i));
        Picasso.with(view.getContext()).load(images.get(i)).placeholder(R.drawable.giphy)
                .error(R.drawable.notfound).into(image);

        return view;
    }
}
