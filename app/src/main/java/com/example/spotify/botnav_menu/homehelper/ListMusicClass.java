package com.example.spotify.botnav_menu.homehelper;

import java.util.List;

public class ListMusicClass {

    String title;
    List<MusicClass> albumClassList;

    public ListMusicClass(String title, List<MusicClass> albumClassList) {
        this.title = title;
        this.albumClassList = albumClassList;
    }
    public List<MusicClass> getAlbumList() {
        return albumClassList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MusicClass> getAlbumClassList() {
        return albumClassList;
    }

    public void setAlbumClassList(List<MusicClass> albumClassList) {
        this.albumClassList = albumClassList;
    }

}
