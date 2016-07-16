package com.kylins.aplayer.bean;

import java.io.Serializable;

/**
 * Created by j-zhouliangshun on 2016/7/16.
 */
public class VideoGroup implements Serializable {

    private String id;
    private String title;
    private String face;
    private int update;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }
}
