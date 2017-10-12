package com.example.ahmedrizk.clothesownerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Men extends Fragment {
    private ListView icons;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private adapterListview listAdapter;
    private String  items[]={"Shirts","T-Shirts","Trousers","Others"};


    public static Men newInstance(int sectionNumber) {
        Men fragment = new Men();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    private void insializeComponent(View rootView){

        icons=(ListView)rootView.findViewById(R.id.icons);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView;
        int num = getArguments().getInt(ARG_SECTION_NUMBER);
        rootView = inflater.inflate(R.layout.fragment_men, container, false);
        insializeComponent(rootView);
        createAction();
        return  rootView;

    }


    void createAction(){

        listAdapter=new adapterListview(getActivity(),items,"1");

        icons.setAdapter(listAdapter);
        icons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(),ShowAllProducts.class);
                intent.putExtra("sec","1");
                int index=position+1;
                intent.putExtra("data",new String(""+index));
                startActivity(intent);
            }
        });
    }
}
