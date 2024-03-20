package com.example.spotify.musichelper;

import java.io.Serializable;

public class Song implements Serializable {
    private String name;
    private int file;

    public Song(String songName, int file) {
        this.name = songName;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String songName) {
        this.name = songName;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }
}
