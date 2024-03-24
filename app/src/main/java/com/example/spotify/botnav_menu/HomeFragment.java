package com.example.spotify.botnav_menu;

import android.graphics.Interpolator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spotify.R;
import com.example.spotify.botnav_menu.homehelper.ListMusicAdapter;
import com.example.spotify.botnav_menu.homehelper.ListMusicClass;
import com.example.spotify.botnav_menu.homehelper.MusicClass;
import com.example.spotify.musichelper.MusicAdapter;
import com.example.spotify.musichelper.MusicHelper;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    RecyclerView recyclerView;
    ArrayList<ListMusicClass> listMusicClassArrayList;
    ArrayList<MusicClass> musicClassArrayList;

    ArrayList<MusicClass> latestList;
    ArrayList<MusicClass> Ranking;
    ArrayList<MusicClass> Suggest;
    ArrayList<Integer>[] Position;

    Context context;
    ArrayList<MusicAdapter> ListSongs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = rootView.findViewById(R.id.rv_parent);
        ListSongs = MusicHelper.getListSongs();
        addControl();

        return rootView;
    }

    private void addControl() {
        //Khởi tạo list album horizontal
        musicClassArrayList = new ArrayList<>();

        latestList = new ArrayList<>();
        Ranking = new ArrayList<>();
        Suggest = new ArrayList<>();

        Position = new ArrayList[3];
        Position[0] = new ArrayList<>();
        Position[1] = new ArrayList<>();
        Position[2] = new ArrayList<>();


        //khởi tạo danh sách list album vertical
        listMusicClassArrayList = new ArrayList<>();
        ListMusicAdapter listMusicAdapter;

        //Đề xuất
        AddSuggest(0);

        //Mới nhất
        AddLatestList(1);

        //Xếp hạng
        AddRankingList(2);

        //List horizontal cho mỗi list nhạc
        CreateHorizontalList();


        listMusicAdapter = new ListMusicAdapter(listMusicClassArrayList, context, Position);


        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //LinearLayoutManager staggeredGridLayoutManager = new LinearLayoutManager(this ,LinearLayoutManager.HORIZONTAL, false);
        //recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(listMusicAdapter);

        listMusicAdapter.notifyDataSetChanged();
    }

    private void AddRankingList(int index) {
        int random = 5;
        int n = random;
        for(int i = 0 ; i < n ; i++) {
            random = new Random().nextInt(ListSongs.size() - 2) + 1;
            int imgID = getIDImg(random);
            if(imgID != 0) {
                Ranking.add(new MusicClass(imgID, "Sơn Tùng M-TP"));
                Position[index].add(ListSongs.get(random).getIDMusic());
            }
        }
    }

    private void CreateHorizontalList() {
        listMusicClassArrayList.add(new ListMusicClass("Đề xuất", Suggest));
        listMusicClassArrayList.add(new ListMusicClass("Album mới nhất", latestList));
        listMusicClassArrayList.add(new ListMusicClass("Bảng xếp hạng", Ranking));
    }

    private void AddLatestList(int index) {
        int random = 5;
        int n = random;
        for(int i = 0 ; i < n ; i++) {
            random = new Random().nextInt(ListSongs.size() - 2) + 1;
            int imgID = getIDImg(random);
            if(imgID != 0) {
                latestList.add(new MusicClass(imgID, "Sơn Tùng M-TP"));
                Position[index].add(ListSongs.get(random).getIDMusic());
            }
        }

    }

    private void AddSuggest(int index) {
        int random = 5;
        int n = random;
        for(int i = 0 ; i < n ; i++) {
            random = new Random().nextInt(ListSongs.size() - 2) + 1;
            int imgID = getIDImg(random);
            if(imgID != 0) {
                Suggest.add(new MusicClass(imgID, "Sơn Tùng M-TP"));
                Position[index].add(ListSongs.get(random).getIDMusic());
            }
        }
    }

    private int getIDImg(int position)
    {
        String ImageFileName = ListSongs.get(position).getImageMusic();
        int IDImage = getResources().getIdentifier(ImageFileName, "drawable", context.getPackageName());
        return IDImage;
    }
}