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
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.File;
import java.util.Random;


public class Create extends AppCompatActivity {
    EditText colors,price,size,name,phone;
    ImageView image;
    Button chooseImage,create;
    Uri imageCaptureUri;
    View view;
    DatabaseReference myRef;
    LayoutInflater inflater,inflater2;
    ArrayAdapter<String> adapter;
    String[]items= new String[] {"From Cam", "From SD Cadr"};
    AlertDialog.Builder builder;
    private final static int PICK_FROM_CAMERA=1;
    private final static int PICK_FROM_FILE=2;
    private String productTcolor,productTsize
            ,productTidcode,productTprice,
            productTphone,productTname;
    String section,type,sec,data;
    ProgressDialog progDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        adapter= new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,items);
        inflater=this.getLayoutInflater();
        inflater2=this.getLayoutInflater();
        progDialog=new ProgressDialog(Create.this);
        insializecomp();
        Intent intent=getIntent();
        getIntentT(intent);
        createAction();

    }

    private void chooseId(){
        int min = 0;
        int max = 1000000;
        Random r = new Random();
        int i1 = r.nextInt(max - min + 1);

        min =65 ;
        max = 90;
        r = new Random();
        int i2 = r.nextInt(max - min)+min;
        char letter=(char)i2;

        productTidcode=Integer.toString(i1)+letter;

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
                    imageCaptureUri=Uri.fromFile(file);
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

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToDataBase();
//                Intent intent = new Intent(Create.this,ShowAllProducts.class);
//                intent.putExtra("sec",sec);
//                intent.putExtra("data",data);
//                startActivity(intent);
            }
        });

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


    private void  insializecomp(){

        colors=(EditText)findViewById(R.id.colors);
        phone=(EditText)findViewById(R.id.phone);
        name=(EditText)findViewById(R.id.name);
        size=(EditText)findViewById(R.id.size);
        price=(EditText)findViewById(R.id.price);
        image=(ImageView)findViewById(R.id.image);
        create=(Button)findViewById(R.id.create);
        chooseImage=(Button)findViewById(R.id.chooseImage);

    }

    private void setToDataBase() {
        progDialog.setMessage("Uploading...");
        progDialog.show();
        chooseId();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(section).child(type).child(productTidcode);
        getNewData();
        myRef.child("name").setValue(productTname);
        myRef.child("id").setValue(productTidcode);
        myRef.child("colors").setValue(productTcolor);
        myRef.child("price").setValue(productTprice);
        myRef.child("size").setValue(productTsize);
        myRef.child("phone").setValue(productTphone);
        if(imageCaptureUri==null){
            progDialog.dismiss();
            myRef.removeValue();
            Toast.makeText(Create.this, "Choose Image pleaseeeeeee....", Toast.LENGTH_SHORT).show();

        }

        else
        {
            StorageReference storage= FirebaseStorage.getInstance().getReference();
            StorageReference imagesRef=storage.child(section).child(type).child(productTidcode).child("photo.jpg");
            Log.i("iiiiiiiiiii", String.valueOf(imageCaptureUri));
            imagesRef.putFile(imageCaptureUri)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.i("iiiiiiiiiii","uuuuu");
                    myRef.child("image").setValue(String.valueOf(taskSnapshot.getDownloadUrl()));
                    Log.i("iiiiiiiiiii",String.valueOf(taskSnapshot.getDownloadUrl()));
                    progDialog.dismiss();
                    Toast.makeText(Create.this, "Uploading Done!!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Create.this,ShowAllProducts.class);
                    intent.putExtra("sec",sec);
                    intent.putExtra("data",data);

                    startActivity(intent);
                    finish();

                }
            });
        }
        //set picasso image
    }


    private void getNewData(){

        // get nae data to save
        productTcolor=colors.getText().toString();
        productTsize=size.getText().toString();
        productTprice=price.getText().toString();
        productTphone=phone.getText().toString();
        productTname=name.getText().toString();
    }

    private void getIntentT(Intent intent) {

        //get Data from intent
        section=intent.getStringExtra("section");
        type=intent.getStringExtra("type");
        sec=intent.getStringExtra("sec");
        data=intent.getStringExtra("data");

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}
