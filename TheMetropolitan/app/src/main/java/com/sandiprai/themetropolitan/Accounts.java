package com.sandiprai.themetropolitan;

import java.util.Date;

public class Accounts
{
    String email;
    String username;
    String[] liked;
    String[] saved;
    Date created;
    Date updated;
    boolean verified;

    public Accounts(String email, String username, String[] liked, String[] saved, boolean verified)
    {
        this.email = email;
        this.username = username;
        this.liked = liked;
        this.saved = saved;
        this.verified = verified;
    }

    public String getEmail()
    {
        return email;
    }

    public String getUsername()
    {
        return username;
    }

    public String[] getLiked()
    {
        return liked;
    }

    public String[] getSaved()
    {
        return saved;
    }

    public boolean getVerified()
    {
        return verified;
    }

    public void setVerified(boolean set)
    {
        verified = set;
    }

    public void setLiked(String[] newLiked)
    {
        liked = newLiked;
    }

    public void setSaved(String[] newSaved)
    {
        saved = newSaved;
    }
}
