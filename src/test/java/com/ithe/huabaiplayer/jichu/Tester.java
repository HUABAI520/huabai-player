package com.ithe.huabaiplayer.jichu;

public class Tester{
  void test() {
    one:
    for (int i = 0; i < 3; i ++) {
      two: 
      for(int j = 0; j < 3; j++){
        if(i == 1) 
          continue two;
        if (j == 1)
          continue one;
        System.out.println("Values : " + i + "," + j); 
      } 
    }
  }

  public static void main(String[] args){
    new Tester().test();
  } 
}