package com.example.recyclerview.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "posts")
public class Post {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userId;
    private String title;
    private final String body;
    private String currentDate;
    private String currentTime;
    public Post(String userId, String title, String body, String currentDate, String currentTime) {
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setCurrentDate(String date) {
        this.currentDate = date;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentTime(String time) {
        this.currentTime = time;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
