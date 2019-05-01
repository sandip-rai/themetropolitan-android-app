package com.sandiprai.themetropolitan;

import java.util.Date;

public class Comments
{
    String articleID;
    String accountID;
    String body;
    Date created;

    public Comments(String articleID, String accountID, String body)
    {
        this.accountID = accountID;
        this.articleID = articleID;
        this.body = body;
    }

    public String getArticleID()
    {
        return articleID;
    }

    public String getAccountID()
    {
        return accountID;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String newBody)
    {
        body = newBody;
    }
}
