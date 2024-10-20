package com.ithe.huabaiplayer.fanshe;

import com.ithe.huabaiplayer.zhujie.Report;
import lombok.Data;

/**
 * @ClassName Fen
 * @Author hua bai
 * @Date 2024/10/9 16:13
 **/
@Data
public class Fen {
    private String name;
    private int age;
    private String address;
    private String phone;
    private String email;
    private String password;

    private Fen(int age,String name){
        this.age = age;
        this.name = name;
    }
    public Fen(){}


    @Report
    public static Fen getInstance(int age,String name){
        return new Fen(age,name);
    }
}
