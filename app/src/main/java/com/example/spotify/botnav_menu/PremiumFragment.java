package com.example.spotify.botnav_menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.spotify.R;
import com.example.spotify.botnav_menu.premiumhelper.GoiMiniActivity;
import com.example.spotify.botnav_menu.premiumhelper.GoiPreIndiActivity;
import com.example.spotify.botnav_menu.premiumhelper.GoiPreStuActivity;
import com.example.spotify.botnav_menu.premiumhelper.UserPreActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PremiumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PremiumFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PremiumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PremiumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PremiumFragment newInstance(String param1, String param2) {
        PremiumFragment fragment = new PremiumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Context context ;
    Button buttonMini ;
    Button buttonPreIndi;
    Button buttonPreStu ;
    Button buttonUsePre ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        View view = inflater.inflate(R.layout.fragment_premium, container, false);
        buttonMini = view.findViewById(R.id.GoiMini);
        buttonPreIndi = view.findViewById(R.id.GoiPremiumIndividual);
        buttonPreStu = view.findViewById(R.id.GoiPremiumStudent);
        buttonUsePre = view.findViewById(R.id.UsePremium);
        addBtnAction();
        return view;
    }

    private void addBtnAction() {
        buttonMini.setOnClickListener(v -> {
            Intent intent = new Intent(context, GoiMiniActivity.class);
            startActivity(intent);
        });
        buttonPreIndi.setOnClickListener(v -> {
            Intent intent = new Intent(context, GoiPreIndiActivity.class);
            startActivity(intent);
        });

        buttonPreStu.setOnClickListener(v -> {
            Intent intent = new Intent(context, GoiPreStuActivity.class);
            startActivity(intent);
        });

        buttonUsePre.setOnClickListener(v -> {
            Intent intent = new Intent(context, UserPreActivity.class);
            startActivity(intent);
        });
    }

}