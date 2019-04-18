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

    public static final DummyArticle[]  articlesTopStories= {
            new DummyArticle("Top Story Article 1",  "This is content of article 1",
                    R.drawable.logo2),
            new DummyArticle("Top Story Article 2",  "This is content of article 2",
                    R.drawable.logo2),
            new DummyArticle("Top Story Article 3",  "This is content of article 3",
                    R.drawable.logo2),
            new DummyArticle("Top Story Article 4",  "This is content of article 4",
                    R.drawable.logo2),
            new DummyArticle("Top Story Article 5",  "This is content of article 5",
                    R.drawable.logo2),
    };

    public static final DummyArticle[]  articlesNews= {
            new DummyArticle("News Article 1",  "This is content of article 1",
                    R.drawable.news1),
            new DummyArticle("News Article 2",  "This is content of article 2",
                    R.drawable.news1),
            new DummyArticle("News Article 3",  "This is content of article 3",
                    R.drawable.news1),
            new DummyArticle("News Article 4",  "This is content of article 4",
                    R.drawable.news1),
            new DummyArticle("News Article 5",  "This is content of article 5",
                    R.drawable.news1),
    };

    public static final DummyArticle[]  articlesStudentLife = {
            new DummyArticle("Student Life Article 1",  "This is content of article 1",
                    R.drawable.studentlife),
            new DummyArticle("Student Life Article 2",  "This is content of article 2",
                    R.drawable.studentlife),
            new DummyArticle("Student Life Article 3",  "This is content of article 3",
                    R.drawable.studentlife),
            new DummyArticle("Student Life Article 4",  "This is content of article 4",
                    R.drawable.studentlife),
            new DummyArticle("Student Life Article 5",  "This is content of article 5",
                    R.drawable.studentlife),
    };

    public static final DummyArticle[]  articlesOpinion = {
            new DummyArticle("Opinion Article 1",  "This is content of article 1",
                    R.drawable.opinion),
            new DummyArticle("Opinion Article 2",  "This is content of article 2",
                    R.drawable.opinion),
            new DummyArticle("Opinion Article 3",  "This is content of article 3",
                    R.drawable.opinion),
            new DummyArticle("Opinion Article 4",  "This is content of article 4",
                    R.drawable.opinion),
            new DummyArticle("Opinion Article 5",  "This is content of article 5",
                    R.drawable.opinion),
    };

    public static final DummyArticle[]  articlesArts = {
            new DummyArticle("Arts Article 1",  "This is content of article 1",
                    R.drawable.arts),
            new DummyArticle("Arts Article 2",  "This is content of article 2",
                    R.drawable.arts),
            new DummyArticle("Arts Article 3",  "This is content of article 3",
                    R.drawable.arts),
            new DummyArticle("Arts Article 4",  "This is content of article 4",
                    R.drawable.arts),
            new DummyArticle("Arts Article 5",  "This is content of article 5",
                    R.drawable.arts),
    };

    public static final DummyArticle[]  articlesTech = {
            new DummyArticle("Tech Article 1",  "This is content of article 1",
                    R.drawable.tech),
            new DummyArticle("Tech Article 2",  "This is content of article 2",
                    R.drawable.tech),
            new DummyArticle("Tech Article 3",  "This is content of article 3",
                    R.drawable.tech),
            new DummyArticle("Tech Article 4",  "This is content of article 4",
                    R.drawable.tech),
            new DummyArticle("Tech Article 5",  "This is content of article 5",
                    R.drawable.tech),
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
