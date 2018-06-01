package prototypez.github.io.sq.demo.entity;

import android.support.annotation.DrawableRes;

public class Item {

    public String title;

    @DrawableRes
    public int imageRes;

    public int favCount;

    public int commentCount;

    public boolean fav;

    public Item(String title, int imageRes, int favCount, int commentCount, boolean fav) {
        this.title = title;
        this.imageRes = imageRes;
        this.favCount = favCount;
        this.commentCount = commentCount;
        this.fav = fav;
    }
}
