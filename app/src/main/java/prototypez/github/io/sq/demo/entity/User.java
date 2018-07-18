package prototypez.github.io.sq.demo.entity;

import java.io.Serializable;

public class User implements Serializable {
    public String id;
    public String nickName;
    public String phone;

    // more information
    public String address;
    public int age;

    public User(String id, String nickName, String phone) {
        this.id = id;
        this.nickName = nickName;
        this.phone = phone;
    }

    public User(String id, String nickName, String phone, String address, int age) {
        this.id = id;
        this.nickName = nickName;
        this.phone = phone;
        this.address = address;
        this.age = age;
    }
}
