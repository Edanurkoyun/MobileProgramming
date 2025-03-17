package com.example.finalproject.Models;

public class SetModel {
    String Name;
    public SetModel(String Name) {
        this.Name = Name;
    }



    public void SetName(String newName){
        this.Name=newName;

    }

    public String GetName(){
       return Name;

    }
}
