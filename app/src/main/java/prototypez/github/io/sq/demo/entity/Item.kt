package prototypez.github.io.sq.demo.entity

import android.support.annotation.DrawableRes

import java.io.Serializable

class Item(
        var title: String,
        @DrawableRes var imageRes: Int,
        var favCount: Int,
        var commentCount: Int,
        var fav: Boolean
)
