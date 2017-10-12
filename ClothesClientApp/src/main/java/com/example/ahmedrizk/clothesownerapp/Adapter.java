package com.example.ahmedrizk.clothesownerapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ahmed Rizk on 19/08/2017.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private ArrayList<product> products=new ArrayList<>();

    public Adapter(Context context, ArrayList<product>products) {
        this.context = context;
        this.products = products;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.gridviewitem,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String priceS=products.get(position).getPrice();
        String imageS=products.get(position).getImage();
        String idS=products.get(position).getId();

        holder.price.setText(priceS);
        holder.id.setText(idS);
//        Picasso.with().load(images.get(position)).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView price;
        TextView id ;
        public ViewHolder(View view) {
            super(view);
            image=(ImageView)view.findViewById(R.id.image);
            price = (TextView) view.findViewById(R.id.price);
            id = (TextView) view.findViewById(R.id.idCode);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   /* Intent intent = new Intent(context,Edit.class);
                    intent.putExtra("sec",sec);
                    intent.putExtra("data",data);
                    intent.putExtra("section",section);
                    intent.putExtra("type",type);
                    intent.putExtra("color",products.get(position));
                    intent.putExtra("price",prices.get(position));
                    intent.putExtra("size",sizes.get(position));
                    intent.putExtra("image",images.get(position));
                    intent.putExtra("phone",phones.get(position));
                    intent.putExtra("id",ids.get(position));
                    intent.putExtra("name",names.get(position));
                    startActivity(intent);*/

                }
            });

        }
    }
}
