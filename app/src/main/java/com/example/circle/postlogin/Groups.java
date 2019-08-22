package com.example.circle.postlogin;

public class Groups {

  private String name;
  private String year;

  public Groups(String year,String name) {
    this.name = name;
    this.year=year;

  }

  public String getname() {
    return name;
  }

  public void setname(String name) {
    this.name = name;
  }

  public String getYear(){
    return year;
  }
  public void setYear(){
    this.year=year;
  }
}
