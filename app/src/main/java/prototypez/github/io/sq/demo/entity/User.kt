package prototypez.github.io.sq.demo.entity

import java.io.Serializable

class User : Serializable {
    var id: String
    var nickName: String
    var phone: String

    // more information
    var address: String? = null
    var age: Int = 0
    var hasAuth: Boolean? = null

    constructor(id: String, nickName: String, phone: String) {
        this.id = id
        this.nickName = nickName
        this.phone = phone
    }

    constructor(id: String, nickName: String, phone: String, address: String, age: Int, hasAuth: Boolean) {
        this.id = id
        this.nickName = nickName
        this.phone = phone
        this.address = address
        this.age = age
        this.hasAuth = hasAuth
    }
}
