package com.example.ahmedrizk.clothesownerapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

//import com.squareup.picasso.Picasso;

public class ShowProduct extends AppCompatActivity {
    TextView colors,price,size,idCode,phone,name;
    ImageView image;
    private String productTcolor,productTsize
            ,productTidcode,productTprice,
            productTphone,productTname;
    private Uri productTimage;
    Button call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
        Intent intent = getIntent();
        getIntentT(intent);
        insializecomp();
        createAction();
        showData();
    }

    private void createAction() {
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentcall = new Intent();
                intentcall.setAction(Intent.ACTION_CALL);
                intentcall.setData(Uri.parse("tel:" + productTphone)); // set the Uri
                startActivity(intentcall);
                finish();
            }
        });
    }

    private void getIntentT(Intent intent) {

        //get Data from intent
        productTcolor=intent.getStringExtra("color");
        productTprice=intent.getStringExtra("price");
        productTidcode=intent.getStringExtra("id");
        productTphone=intent.getStringExtra("phone");
        productTname=intent.getStringExtra("name");
        productTimage=Uri.parse(intent.getStringExtra("image"));
        productTsize=intent.getStringExtra("size");

    }

    private void showData(){
        // set data from intent
        colors.setText(productTcolor);
        price.setText(productTprice);
        name.setText(productTname);
        phone.setText(productTphone);
        size.setText(productTsize);
        idCode.setText(productTidcode);
        Picasso.with(ShowProduct.this).load(productTimage).into(image);

    }

    private void  insializecomp(){
        colors=(TextView)findViewById(R.id.colors);
        name=(TextView)findViewById(R.id.name);
        phone=(TextView)findViewById(R.id.phone);
        idCode=(TextView)findViewById(R.id.idCode);
        size=(TextView)findViewById(R.id.size);
        price=(TextView)findViewById(R.id.price);
        image=(ImageView)findViewById(R.id.image);
        call=(Button)findViewById(R.id.call);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();

    }
}
