package com.sandiprai.themetropolitan;

public class DummyArticle {
    //This class can be used as reference and this is used to just test the RecycleView
    private String articleTitle;
    private String articleContent;
    private int imageId;

    public static final DummyArticle[]  articles = {
            new DummyArticle("Article 1",  "This is content of article 1",
                    R.drawable.logo2),
            new DummyArticle("Article 2",  "This is content of article 2",
                    R.drawable.logo2),
            new DummyArticle("Article 3",  "This is content of article 3",
                    R.drawable.logo2),
            new DummyArticle("Article 4",  "This is content of article 4",
                    R.drawable.logo2),
            new DummyArticle("Article 5",  "This is content of article 5",
                    R.drawable.logo2),
    };

    private DummyArticle(String articleTitle, String articleContent, int imageId){
        this.articleContent = articleContent;
        this.articleTitle = articleTitle;
        this.imageId = imageId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public int getImageId() {
        return imageId;
    }
}
