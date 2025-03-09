package com.ithe.huabaiplayer.jichu;

class Base{
  Base(int i){
    System.out.println("base constructor");
  }
  Base(){}
}

public class Sub extends Base{
  public static void main(String args[]){
  Sub s= new Sub();
    //One
  }

  Sub(){
    //Two
    super(10);
  }

  public void derived(){
    //Three
  }
}