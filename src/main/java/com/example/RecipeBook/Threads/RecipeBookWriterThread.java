package com.example.RecipeBook.Threads;


import com.example.RecipeBook.Data.DBWriter;
import com.example.RecipeBook.RestController.RESTControllerImpl;


public class RecipeBookWriterThread extends DBWriter implements Runnable{
    final String gsonInput;

    public RecipeBookWriterThread(String gsonInput) {
        this.gsonInput = gsonInput;
    }
    @Override
    public void run() {

    }




}
