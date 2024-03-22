package com.example.spotify;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotify.musichelper.MusicAdapter;
import com.example.spotify.musichelper.MusicHelper;
import com.example.spotify.musichelper.Song;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {

    //region Variable
    Button btnMP3,btnMP4;
    TextView txtTenBaiHat,txtSongToTal,txtTimeSong;
    ImageButton btnPlay, btnPrevious, btnLast, btnPause;
    ImageView disc;
    SeekBar seekBar;
    ArrayList<MusicAdapter> ListSongs;
    int position = 0;
    MediaPlayer mediaPlayer;
    Animation animation;
    MusicHelper musicHelper;
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music);

        LoadFunction();
        LoadListSongs();
        CreateMediaPlayer();
        LoadBtnAction();
        animation = AnimationUtils.loadAnimation(this,R.anim.disc_rotation);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });
    }
    private void LoadBtnAction() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.icmusic_play);
                    disc.clearAnimation();
                }
                else{
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.icmusic_pause);
                    disc.startAnimation(animation);
                }
                SetTotalTime();
                UpdateTimeSong();
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                CreateMediaPlayer();
                disc.clearAnimation();
                btnPlay.setImageResource(R.drawable.icmusic_play);
            }
        });
        btnLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;

                //song=4 => 0 1 2 3 0
                if(position> ListSongs.size()-1){
                    position=0;
                }
                //neu phat nhac thi dung
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                CreateMediaPlayer();
                mediaPlayer.start();
                disc.startAnimation(animation);
                SetTotalTime();
                UpdateTimeSong();
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;

                //song=4 => 0 1 2 3 0
                if(position<0){
                    position = ListSongs.size()-1;
                }
                //neu phat nhac thi dung
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                CreateMediaPlayer();
                mediaPlayer.start();
                disc.startAnimation(animation);
                SetTotalTime();
                UpdateTimeSong();



            }
        });
    }

    private void CreateMediaPlayer() {
        // Lấy tên tệp từ danh sách ListSongs ở vị trí được chỉ định
        String fileName = ListSongs.get(position).getFileName();
        // Lấy ID của tệp dựa trên tên
        int fileID = getResources().getIdentifier(fileName, "raw", getPackageName());
        // Kiểm tra xem ID có hợp lệ không
        if (fileID != 0) {
            mediaPlayer = MediaPlayer.create(MusicActivity.this, fileID); // Sử dụng ID để tạo MediaPlayer
        } else {
            // Xử lý trường hợp không tìm thấy tệp
        }

        txtTenBaiHat.setText(ListSongs.get(position).getMusicName());
    }

    private void LoadListSongs() {
        ListSongs = MusicHelper.getListSongs();

//        ListSongs.add(new Song("ball in the jals",R.raw.music));
//        ListSongs.add(new Song("nigg",R.raw.music2));
    }

    private void LoadFunction() {
        musicHelper = new MusicHelper(this);
        btnPlay = findViewById(R.id.Music_btnPlay);
        btnPrevious = findViewById(R.id.Music_btnPrevious);
        btnLast = findViewById(R.id.Music_btnLast);
        txtTenBaiHat = findViewById(R.id.Music_TenBaiHat);
        txtSongToTal = findViewById(R.id.Music_SongToTal);
        txtTimeSong = findViewById(R.id.Music_TimeSong);
        btnPause = findViewById(R.id.Music_btnPause);
        seekBar = findViewById(R.id.Music_btnSeekBar);
        disc = findViewById(R.id.Music_disc);
    }
    private  void SetTotalTime(){
        //getduration() tra tong tgian bai hat= m/s=> dinh dang phut/giay =simpledataformat
        SimpleDateFormat fm = new SimpleDateFormat("mm:ss");
        txtSongToTal.setText(fm.format(mediaPlayer.getDuration())+"");
        seekBar.setMax(mediaPlayer.getDuration());
    }
    private  void UpdateTimeSong(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // lay thoi gian bai hat dang phat getCurrentPosition()
                SimpleDateFormat fm = new SimpleDateFormat("mm:ss");
                txtTimeSong.setText(fm.format(mediaPlayer.getCurrentPosition())+"");
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;

                        //song=4 => 0 1 2 3 0
                        if(position > ListSongs.size()-1){
                            position=0;
                        }
                        //neu phat nhac thi dung
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        CreateMediaPlayer();
                        mediaPlayer.start();
                        disc.startAnimation(animation);
                        SetTotalTime();
                        UpdateTimeSong();
                    }
                });
                handler.postDelayed(this,500);
            }
        }, 500);
    }
}