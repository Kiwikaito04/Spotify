package com.example.spotify.botnav_menu.homehelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.MusicActivity;
import com.example.spotify.R;

import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {


    List<MusicClass> AlbumClassList;
    Context context;
    ArrayList<Integer>[] Position;
    int index;

    public MusicAdapter(List<MusicClass> albumClassList, Context context,
                        ArrayList<Integer>[] Position,
                        int index) {
        this.AlbumClassList = albumClassList;
        this.context = context;
        this.index = index;
        this.Position = Position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.album_rv_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.iv_album_image.setImageResource(AlbumClassList.get(position).image);

        holder.iv_album_name.setText(AlbumClassList.get(position).name);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MusicActivity.class);
                String idMusic = String.valueOf(Position[index].get(position));
                intent.putExtra("selectedSong",idMusic);
                context.startActivity(intent);
//                Log.w("test", String.format("%s",position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return AlbumClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_album_image;
        TextView iv_album_name;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_album_image = itemView.findViewById(R.id.iv_album_item);
            iv_album_name = itemView.findViewById(R.id.name_album);
            cardView = itemView.findViewById(R.id.cv_album_item);
        }
    }
}
