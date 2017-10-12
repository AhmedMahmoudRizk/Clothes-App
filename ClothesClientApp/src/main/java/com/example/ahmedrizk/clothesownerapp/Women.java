package com.example.ahmedrizk.clothesownerapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link Women#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Women extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private String  items[]={"Shirts","T-Shirts","Trousers","Others"};

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView icons;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private adapterListview listAdapter;
    public Women() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment Women.
     */
    // TODO: Rename and change types and number of parameters
    public static Women newInstance(int sectionNumber) {
        Women fragment = new Women();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private void insializeComponent(View rootView){
        icons=(ListView)rootView.findViewById(R.id.icons);

//        tshirts=(Button)rootView.findViewById(R.id.tshirt);
//        shooses=(Button)rootView.findViewById(R.id.shooses);
//        shirts=(Button)rootView.findViewById(R.id.shirt);
//        trousers=(Button)rootView.findViewById(R.id.trousers);
    }

    private void createAction(){
        listAdapter=new adapterListview(getActivity(),items,"3");
//        Log.i("taniiiiiiiiiiii","********"+prices.size());

        icons.setAdapter(listAdapter);
//        while(images.size()<prices.size()){
//            images.add("hhhhhh");
//        }
        icons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(),ShowAllProducts.class);
                intent.putExtra("sec","3");
                int index=position+1;
                intent.putExtra("data",new String(""+index));
//                intent.putExtra("section",section);
//                intent.putExtra("type",type);
//                intent.putExtra("color",colors.get(position));
//                intent.putExtra("price",prices.get(position));
//                intent.putExtra("size",sizes.get(position));
//                intent.putExtra("image",images.get(position));
//                intent.putExtra("phone",phones.get(position));
//                intent.putExtra("id",ids.get(position));
//                intent.putExtra("name",names.get(position));
                startActivity(intent);

            }
        });
//
//        shirts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getActivity(),ShowAllProducts.class);;
//                intent.putExtra("sec","3");
//                intent.putExtra("data","1");
//                startActivity(intent);
//            }
//        });
//        tshirts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getActivity(),ShowAllProducts.class);;
//                intent.putExtra("sec","3");
//                intent.putExtra("data","2");
//                startActivity(intent);
//            }
//        });
//        trousers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getActivity(),ShowAllProducts.class);;
//                intent.putExtra("sec","3");
//                intent.putExtra("data","3");
//                startActivity(intent);
//            }
//        });
//        shooses.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getActivity(),ShowAllProducts.class);;
//                intent.putExtra("sec","3");
//                intent.putExtra("data","4");
//                startActivity(intent);
//            }
//        });
    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView;
        int num = getArguments().getInt(ARG_SECTION_NUMBER);
        rootView = inflater.inflate(R.layout.fragment_women, container, false);
        insializeComponent(rootView);
        createAction();
        return  rootView;
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_men, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
