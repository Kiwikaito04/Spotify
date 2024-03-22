package com.example.spotify.musichelper;

import android.graphics.Bitmap;

public class MusicAdapter {
    private Integer IDMusic;
    private String MusicName;
    private String FileName;

    public MusicAdapter(Integer idMusic, String musicName, String fileName)
    {
        IDMusic = idMusic;
        MusicName = musicName;
        FileName = fileName;
    }

    public Integer getIDMusic() {
        return IDMusic;
    }
    public void setIDMusic(Integer ID_Music) {
        IDMusic = ID_Music;
    }
    public String getMusicName() {
        return MusicName;
    }
    public void setMusicName(String musicName) {
        MusicName = musicName;
    }
    public String getFileName() {
        return FileName;
    }
    public void setFileName(String fileName) {
        FileName = fileName;
    }
}