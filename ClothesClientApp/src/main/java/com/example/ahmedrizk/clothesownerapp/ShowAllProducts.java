package com.example.ahmedrizk.clothesownerapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;

public class ShowAllProducts extends AppCompatActivity {

    GridView products;
    ArrayList<String>oo=new ArrayList<>();
    ArrayList<Uri> images=new ArrayList<>() ;
    ArrayList<String> prices=new ArrayList<>() ;
    ArrayList<String> sizes=new ArrayList<>() ;
    ArrayList<String> ids=new ArrayList<>() ;
    ArrayList<String> names=new ArrayList<>() ;
    ArrayList<String> phones=new ArrayList<>() ;
    ArrayList<String> colors=new ArrayList<>() ;
    private ItemsRecycleViewAdapter recyclerAdapter;
    private String section,type,sec,data;
    private int global=0;
    private Context context=ShowAllProducts.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_products);
        products=(GridView) findViewById(R.id.products);
        Intent intent=getIntent();
        getIntentT(intent);
        getFromDataBase();
    }

    private void getIntentT(Intent intent) {

        sec=intent.getStringExtra("sec");
        data=intent.getStringExtra("data");

        if(sec.equals("1"))
            section="Men";
        else if(sec.equals("2"))
            section="Children";
        else if(sec.equals("3"))
            section="Women";
        if(data.equalsIgnoreCase("1"))
            type="shirts";
        else if(data.equalsIgnoreCase("2"))
            type="tshirts";
        else if(data.equalsIgnoreCase("3"))
            type="trousers";
        else
            type="shooses";

    }


    private void fetchImage(final String id) {
        // Points to the root reference
        StorageReference storage= FirebaseStorage.getInstance().getReference();
        StorageReference imagesRef=storage.child(section).child(type).child(id).child("photo.jpg");
        imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                images.add(uri);
                global++;
                if(global>=ids.size())
                    updateRecyclerView();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private boolean isOnline()
    {
        try
        {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private void getFromDataBase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if (!isOnline()) {
            Log.i("hhhhhhhhhhhh","offline mode");
            database.goOffline();
            DatabaseReference myRef2 = database.getReference(section).child(type);
            myRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.exists()) {
                            Map<String, Object> map = (Map<String, Object>) data.getValue();
                            names.add((String) map.get("name"));
                            sizes.add(map.get("size").toString());
                            colors.add((String) map.get("colors"));
                            prices.add(map.get("price").toString());
                            phones.add(map.get("phone").toString());
                            ids.add((String) map.get("id"));
                        }
                    }

                    while (images.size() < ids.size()) {
                        images.add(Uri.parse("hhhhh"));
                    }
                    updateRecyclerView();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            /**
             * database offline
             */

            DatabaseReference myRef = database.getReference(section).child(type);
            myRef.keepSynced(true);
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            if (data.exists()) {
                                Map<String, Object> map = (Map<String, Object>) data.getValue();
                                names.add((String) map.get("name"));
                                sizes.add(map.get("size").toString());
                                colors.add((String) map.get("colors"));
                                prices.add(map.get("price").toString());
                                phones.add(map.get("phone").toString());
                                ids.add((String) map.get("id"));
                                images.add(Uri.parse((String)map.get("image")));
                            }
                        }
                        updateRecyclerView();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    private void updateRecyclerView() {

        recyclerAdapter=new ItemsRecycleViewAdapter(ShowAllProducts.this,prices,ids,images);
        products.setAdapter(recyclerAdapter);

        products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(ShowAllProducts.this,ShowProduct.class);
                intent.putExtra("sec",sec);
                intent.putExtra("data",data);
                intent.putExtra("section",section);
                intent.putExtra("type",type);
                intent.putExtra("color",colors.get(position));
                intent.putExtra("price",prices.get(position));
                intent.putExtra("size",sizes.get(position));
                intent.putExtra("image",images.get(position).toString());
                intent.putExtra("phone",phones.get(position));
                intent.putExtra("id",ids.get(position));
                intent.putExtra("name",names.get(position));
                startActivity(intent);
            }
        });
    }
}
