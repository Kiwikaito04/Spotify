package com.example.spotify.botnav_menu;

import static java.util.Arrays.*;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.spotify.MusicActivity;
import com.example.spotify.R;
import com.example.spotify.musichelper.MusicAdapter;
import com.example.spotify.musichelper.MusicHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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


    ListView lv;
    EditText search;

    Context context;
    ArrayAdapter<String> arrayAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        lv = view.findViewById(R.id.MusicList);
        search=view.findViewById(R.id.searchFragment);
        context = getContext();
        LoadDataFromDatabase();
        EventSearch();



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy tên bài hát tương ứng
                String selectedSong = String.valueOf(position);

                // Xử lý khi người dùng nhấn vào một mục trong danh sách
                goToSelectedSong(selectedSong);
            }
        });

        return  view;

    }

    private void EventSearch() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SearchFragment.this.arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void LoadDataFromDatabase() {
        ArrayList<MusicAdapter> songs = MusicHelper.getListSongs();
        ArrayList<String> songNames = new ArrayList<>();
        ArrayList<String> Img_music = new ArrayList<>();
        for (int i = 0 ; i < songs.size() ; i++) {
            songNames.add(songs.get(i).getMusicName());
        }
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, songNames);

        lv.setAdapter(arrayAdapter);

    }

    private void goToSelectedSong(String selectedSong) {
        // Thực hiện hành động chuyển đến bài hát tương ứng
        Intent intent = new Intent(getActivity(), MusicActivity.class);
        intent.putExtra("selectedSong", selectedSong);
        startActivity(intent);
    }
}
