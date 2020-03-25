package com.example.onlineshop.storage;

import android.content.Context;

import com.example.onlineshop.model.Product;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Internal {


    private static final String FILE = "LabStorage.txt";



    public static void setProducts(Context context, ArrayList<Product> products) throws Exception{
        File file = new File(context.getFilesDir(), FILE);
        boolean created = file.exists()  &&  file.createNewFile();

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(products);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public static ArrayList<Product> getProducts(Context context) throws Exception{

        File file = new File(context.getFilesDir(), FILE);
        if(!file.exists()) return new ArrayList<>();
        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
        return (ArrayList<Product>) stream.readObject();


    }




}
