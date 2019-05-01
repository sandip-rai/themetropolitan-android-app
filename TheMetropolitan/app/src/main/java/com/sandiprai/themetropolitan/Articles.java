package com.sandiprai.themetropolitan;

import java.util.Date;

public class Articles {
    String title;
    String author;
    String category;
    String date;
    Long likes;
    Date created;
    Date updated;
    String excerpt;
    String body;
    String[] tags;

    public Articles() { }

    public Articles(String title, String author, String date, Long likes, String excerpt, String body, String category, String[] tags) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.date = date;
        this.likes = likes;
        this.created = created;
        this.updated = updated;
        this.excerpt = excerpt;
        this.body = body;
        this.tags = tags;
    }

    public String getTitle() {return title;}

    public String getAuthor() {return author;}

    public String getCategory() {return category; }

    public String getDate() {return date;}

    public Long getLikes() {return likes;}

    public Date getCreated() {return created;}

    public Date getUpdated() {return updated;}

    public void setUpdated(Date newTime)
    {
        updated = newTime;
    }

    public void setTitle(String newTitle)
    {
        title = newTitle;
    }

    public void setAuthor(String newAuthor)
    {
        author = newAuthor;
    }

    public void setCategory(String newCategory) { category = newCategory; }

    public void setDate(String newDate)
    {
        date = newDate;
    }

    public void setLikes(long newLikes)
    {
        likes = newLikes;
    }

    public void setExcerpt(String newExcerpt)
    {
        excerpt = newExcerpt;
    }

    public void setBody(String newBody)
    {
        body = newBody;
    }

    public void setTags(String[] newTags)
    {
        tags = newTags;
    }
}
