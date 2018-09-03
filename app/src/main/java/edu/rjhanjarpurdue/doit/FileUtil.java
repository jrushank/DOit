package edu.rjhanjarpurdue.doit;
//Replace the package name with your package name.
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class is used for read/writeObject on external storage
 * WRITE: The writeCategories and writeTodoList methods are called from onPause() method in MainActivity.
 *        They writeObject user-defined categories and TodoList items to corresponding files.
 * READ:  The readCategories and readTodoList methods are called from onCreate() method in MainActivity.
 *        They read user-defined categories and TodoList items from corresponding files.
 *
 * You need to implement the two helper methods readObject and writeObject.
 * These two methods simply just read/write file as object. Refer to the handout for example.
 */

public class FileUtil {
    private static final String TAG = "FileUtil";
    private static String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static String CATEGORY_PATH = BASE_PATH+"/categories.dat";
    private static String TODO_LIST_PATH = BASE_PATH+"/todolist.dat";

    private static Object readObject(String path)  {
        Object obj=null;
        //TODO: 1. Implement this method to read object using the path
        try {
            FileInputStream fis = new FileInputStream(new File(path));
            ObjectInputStream ois = new ObjectInputStream(fis);

            obj = (Object) ois.readObject();
            ois.close();
            fis.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return obj;

    }

    public static void writeObject(Object obj, String path){
       //TODO: 2. Write Object obj to file path

        try {
            FileOutputStream fos = new FileOutputStream(new File(path));

            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(obj);
            oos.close();
            fos.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    /*************** YOU SHOULD NOT TOUCH THE CODE BELOW ****************/
    public static void writeCategories(List<String> categories) {
        writeObject(categories,CATEGORY_PATH);
    }

    public static void writeTodoList(List<TodoItem> todoList)  {
        writeObject(todoList,TODO_LIST_PATH);
    }

    public static List<String> readCategories()  {
        List<String> categories =  (ArrayList<String>)readObject(CATEGORY_PATH);
        if(categories==null)
            categories = new ArrayList<String>();
        return categories;
    }

    public static List<TodoItem> readTodoList()  {
        List<TodoItem> todoList = (ArrayList<TodoItem>)readObject(TODO_LIST_PATH);
        if(todoList==null)
            todoList = new ArrayList<TodoItem>();
        return todoList;
    }


}
