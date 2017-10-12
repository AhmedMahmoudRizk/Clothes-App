package com.example.ahmedrizk.clothesownerapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;

public class Edit extends AppCompatActivity {
    EditText colors,price,size,name,phone;
    ImageView image;
    Button chooseImage,edit,delete;
    Uri imageCaptureUri=null;
    View view;
    DatabaseReference myRef;
    LayoutInflater inflater,inflater2;
    ArrayAdapter<String> adapter;
    String[]items= new String[] {"From Cam", "From SD Cadr"};
    AlertDialog.Builder builder,deleteDialog;
    private final static int PICK_FROM_CAMERA=1;
    private final static int PICK_FROM_FILE=2;
    private String productTcolor,productTsize
            ,productTidcode,productTprice,
            productTphone,productTname;
    private Uri productTimage;
    String section,type,sec,data;
    ProgressDialog progDialog;
//    private boolean imageChange=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        adapter= new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,items);
        inflater=this.getLayoutInflater();
        inflater2=this.getLayoutInflater();
        progDialog=new ProgressDialog(Edit.this);
        Intent intent=getIntent();
        getIntentT(intent);
        insializecomp();
        showData();
        createAction();

    }
    private void deleteFromDatabase() {

        progDialog.setMessage("Deleting...");
        progDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(section).child(type).child(productTidcode);
        myRef.getRef().removeValue();
        StorageReference storage= FirebaseStorage.getInstance().getReference();
        StorageReference imagesRef=storage.child(section).child(type).child(productTidcode).child("photo.jpg");
        imagesRef.delete();
        progDialog.dismiss();
        //set picasso image
    }


    private void setToDataBase() {
        progDialog.setMessage("Uploading...");
        progDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(section).child(type).child(productTidcode);
        myRef.getRef().removeValue();
        getNewData();
        myRef.child("name").setValue(productTname);
        myRef.child("id").setValue(productTidcode);
        myRef.child("colors").setValue(productTcolor);
        myRef.child("price").setValue(productTprice);
        myRef.child("size").setValue(productTsize);
        myRef.child("phone").setValue(productTphone);

//        if (imageCaptureUri != null) {
//            StorageReference storage= FirebaseStorage.getInstance().getReference();
//            StorageReference imagesRef=storage.child(section).child(type).child(productTidcode).child("photo.jpg");
//            Log.i("iiiiiiiiiii", String.valueOf(imageCaptureUri));
//            imagesRef.putFile(imageCaptureUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Log.i("iiiiiiiiiii","uuuuu");
//                            myRef.child("image").setValue(String.valueOf(taskSnapshot.getDownloadUrl()));
//                            Log.i("iiiiiiiiiii",String.valueOf(taskSnapshot.getDownloadUrl()));
//                            progDialog.dismiss();
//                            Toast.makeText(Edit.this, "Uploading Done!!!", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(Edit.this,ShowAllProducts.class);
//                            intent.putExtra("sec",sec);
//                            intent.putExtra("data",data);
//                            startActivity(intent);
//                            finish();
//
//                        }
//                    });
//
//        }else{
                Log.i("iiiiiiiiiii","zzzzzzzzzzz");
                myRef.child("image").setValue(String.valueOf(productTimage));
                Log.i("iiiiiiiiiii",String.valueOf(String.valueOf(productTimage)));
                Toast.makeText(Edit.this, "Uploading Done!!!", Toast.LENGTH_SHORT).show();
                progDialog.dismiss();
                Intent intent = new Intent(Edit.this,ShowAllProducts.class);
                intent.putExtra("sec",sec);
                intent.putExtra("data",data);
                startActivity(intent);
                finish();
//            }
    }

    private void getIntentT(Intent intent) {

        //get Data from intent
        section=intent.getStringExtra("section");
        type=intent.getStringExtra("type");
        sec=intent.getStringExtra("sec");
        data=intent.getStringExtra("data");
        productTcolor=intent.getStringExtra("color");
        productTprice=intent.getStringExtra("price");
        productTidcode=intent.getStringExtra("id");
        productTphone=intent.getStringExtra("phone");
        productTname=intent.getStringExtra("name");
        productTimage=Uri.parse(intent.getStringExtra("image"));
        productTsize=intent.getStringExtra("size");

    }

    private void createAction() {
        builder =new AlertDialog.Builder(this);
        builder.setTitle("Select Image");

        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which==0){
                    Intent intt=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file=new File(Environment.getExternalStorageDirectory(),
                            "stadium_image"+String.valueOf(System.currentTimeMillis())+".jpg");
                    imageCaptureUri= Uri.fromFile(file);
                    try{

                        intt.putExtra(MediaStore.EXTRA_DURATION_LIMIT,imageCaptureUri);
                        intt.putExtra("return Data",true);
                        startActivityForResult(intt,PICK_FROM_CAMERA);
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                    dialog.cancel();

                }else {
                    Intent intt=new Intent();
                    intt.setType("image/*");
                    intt.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intt,"Select Picture"),PICK_FROM_FILE);

                }

            }
        });

        final AlertDialog dialog=builder.create();

//        chooseImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.show();
//            }
//        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToDataBase();
//                Intent intent = new Intent(Edit.this,ShowAllProducts.class);
//                intent.putExtra("sec",sec);
//                intent.putExtra("data",data);
//                startActivity(intent);
            }
        });

        deleteDialog= new AlertDialog.Builder(Edit.this);
        final View customView=inflater.inflate(R.layout.customview,null);
        deleteDialog.setView(customView).
                setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFromDatabase();
                        Intent intent = new Intent(Edit.this,ShowAllProducts.class);
                        intent.putExtra("sec",sec);
                        intent.putExtra("data",data);
                        startActivity(intent);
                    }
                });
        deleteDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog deleteDoalog=deleteDialog.create();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDoalog.show();
            }
        });

    }
    private void  insializecomp(){

        colors=(EditText)findViewById(R.id.colors);
        phone=(EditText)findViewById(R.id.phone);
        name=(EditText)findViewById(R.id.name);
        size=(EditText)findViewById(R.id.size);
        price=(EditText)findViewById(R.id.price);
        image=(ImageView)findViewById(R.id.image);
        edit=(Button)findViewById(R.id.edit);
        delete=(Button)findViewById(R.id.delete);
//        chooseImage=(Button)findViewById(R.id.chooseImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode,resultCode, data);
        if(resultCode != RESULT_OK)
            return;
        Bitmap bitmap=null;
        String path="";
        if(requestCode==PICK_FROM_FILE) {
            imageCaptureUri = data.getData();
            path=getRealPathFromUri(imageCaptureUri);
            if(path==null)
                path =imageCaptureUri.getPath();
            if(path !=null)
                bitmap= BitmapFactory.decodeFile(path);
        }else{
            path=imageCaptureUri.getPath();
            bitmap=BitmapFactory.decodeFile(path);
        }
        image.setImageBitmap(bitmap);

    }



    private String getRealPathFromUri(Uri contentUri){

        String[] proj={MediaStore.Images.Media.DATA};
        Cursor cursor=managedQuery(contentUri,proj,null,null,null);

        if(cursor==null)  return null;
        int columnIndex=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return  cursor.getString(columnIndex);
    }


    private void showData(){
        // set data from intent
        colors.setText(productTcolor);
        price.setText(productTprice);
        name.setText(productTname);
        phone.setText(productTphone);
        size.setText(productTsize);
        Picasso.with(Edit.this).load(productTimage).into(image);

    }

    private void getNewData(){

        // get nae data to save
        productTcolor=colors.getText().toString();
        productTsize=size.getText().toString();
        productTprice=price.getText().toString();
        productTphone=phone.getText().toString();
        productTname=name.getText().toString();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

}
