package prototypez.github.io.sq.demo.util

import prototypez.github.io.sq.demo.entity.User

object LoginInfo {

    var currentLoginUser: User? = null

    val isLogin: Boolean
        get() = currentLoginUser != null
}
