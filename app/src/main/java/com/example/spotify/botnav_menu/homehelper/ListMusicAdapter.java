package com.example.spotify.botnav_menu.homehelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.R;

import java.util.ArrayList;
import java.util.List;

public class ListMusicAdapter extends RecyclerView.Adapter<ListMusicAdapter.ViewHolder> {


    List<ListMusicClass> ListAlbumClassList;
    Context context;

    ArrayList<Integer>[] Position;

    public ListMusicAdapter(List<ListMusicClass> listAlbumClassList, Context context,
                            ArrayList<Integer>[] Position) {
        this.ListAlbumClassList = listAlbumClassList;
        this.context = context;
        this.Position = Position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_album_rv_layout, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_album_title.setText(ListAlbumClassList.get(position).title);

        MusicAdapter albumAdapter;
        albumAdapter = new MusicAdapter(ListAlbumClassList.get(position).albumClassList, context, Position, position);
        holder.rv_album.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        //holder.rv_album.setLayoutManager(new GridLayoutManager(context,2));
        holder.rv_album.setAdapter(albumAdapter);
        albumAdapter.notifyDataSetChanged();


    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return ListAlbumClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView rv_album;
        TextView tv_album_title;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_album = itemView.findViewById(R.id.rv_album);
            tv_album_title = itemView.findViewById(R.id.tv_album_title);
        }
    }
}
