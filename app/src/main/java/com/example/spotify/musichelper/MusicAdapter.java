package com.example.spotify.musichelper;

import androidx.annotation.Nullable;

public class MusicAdapter {
    private Integer IDMusic;
    private String MusicName;
    private String FileName;
    private byte[] ImageMusic;

    public MusicAdapter(Integer idMusic, String musicName, String fileName, byte[] imageMusic)
    {
        IDMusic = idMusic;
        MusicName = musicName;
        FileName = fileName;
        ImageMusic = imageMusic;
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
    public byte[] getImageMusic() {
        return ImageMusic;
    }

    public void setImageMusic(byte[] imageMusic) {
        ImageMusic = imageMusic;
    }
}
