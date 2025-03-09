package com.ithe.huabaiplayer.jichu;

public class Sample{
  final int x;
  Sample(int a){
     x = a;
  }
  final int getX(){
    return x;
  }

  public static void main(String[] args){
    System.out.println(new Sample(2).x);
  }
}