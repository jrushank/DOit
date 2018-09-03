package edu.rjhanjarpurdue.doit;
//Don't copy the package statement
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.MotionEvent;
import android.view.GestureDetector;
import android.support.v4.view.GestureDetectorCompat;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The entry activity of this application
 * It contains the following widgets:
 *   1. A menu with a button (a round +) on it. When the button is clicked, it will go to ItemActivity to add a new todoitem
 *   2. A spinner for selecting category
 *   3. A spinner for selecting sorting rule
 *   4. A listview that holds all todoitems
 * The most part of this activity has been done.
 * You only need to implement refreshTodoList()
 */
public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    public static final String ITEM_KEY = "item_key";
    public static List<TodoItem> allData = new ArrayList<>();
    public static List<TodoItem> showData = new ArrayList<>();
    public static List<String> defaultCategories = new ArrayList<>(Arrays.asList("All","Life","Work"));
    public static List<String> newCategories;
    public static List<String> categories;
    public static List<String> sortByStrings = new ArrayList<>(Arrays.asList("Due date: Closest",
            "Due date: Farthest","Alphabetical: A-Z","Alphabetical: Z-A"));

    private Toolbar toolbar;
    private ListView listView;
    private ImageButton addCategoryBtn;
    private Spinner categorySpinner;
    private Spinner sortSpinner;
    private TodoListAdapter tdAdapter;
    private GestureDetectorCompat detector;

    /**
     * This Method is called when the Activity is launched. It's used for initialization.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        newCategories = FileUtil.readCategories();
        categories = new ArrayList<>();
        categories.addAll(defaultCategories);
        categories.addAll(newCategories);

        allData = FileUtil.readTodoList();

        //Initialize all view widgets
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        listView = (ListView)findViewById(R.id.item_list);
        addCategoryBtn = (ImageButton)findViewById(R.id.add_cat_btn);
        categorySpinner = (Spinner)findViewById(R.id.category_spinner);
        sortSpinner = (Spinner)findViewById(R.id.sort_spinner);


        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Guide");
        alertDialog.setMessage("Right Swipe to add a new TODO item and Left Swipe to add a new Category");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
        //Set OnClickListener for addCategoryBtn
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        //Set Actionbar for this Activity
        setSupportActionBar(toolbar);

        //Initialize Category Spinner
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                categories);
        categorySpinner.setAdapter(spinnerArrayAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //When an option is selected, refresh the todolist based on the choice
                refreshTodoList();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        //Initialize Sorting Spinner
        ArrayAdapter sortArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                sortByStrings);
        sortSpinner.setAdapter(sortArrayAdapter);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //When an option is selected, refresh the todolist based on the choice.
                refreshTodoList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Set Adapter to listView
        tdAdapter = new TodoListAdapter(MainActivity.this, allData);
        listView.setAdapter(tdAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,ItemActivity.class);
                intent.putExtra(ITEM_KEY,position);
                startActivity(intent);

            }
        });

    }

    /**
     * This method refreshes the content of the List showData based on the selected category and sortBy option
     *
     * This method is called when an option is selected in categorySpinner or sortSpinner.
     * This method is also called in onResume() method. See comments about onResume() to see when onResume() will be called.
     */
    public void refreshTodoList(){
        int selectedCategoryPos = categorySpinner.getSelectedItemPosition();
        int selectedSortByPos = sortSpinner.getSelectedItemPosition();
        showData = new ArrayList<>();
        if(selectedCategoryPos==0){
            // Selected "All". newData should contain all items from allData
            showData = allData;
        }
        else {
            /*TODO: Iterate through list allData.
             *      If the todoitem has the same category as the selected category option, add it to showData.
             *      After this else block, showData should only contain TodoItems from the selected category.
             *      HINT: categorySpinner.getSelectedItem().toString() returns the text of the selected option, eg. "Work","Life")
             */
            int i=0;
            while(i<allData.size()){
                if(allData.get(i).getCategory().equals(categorySpinner.getSelectedItem().toString())) {
                    showData.add(allData.get(i));

                }
                i++;
            }
        }

        Comparator comparator = null;

        switch (selectedSortByPos){
            case 0:
                comparator = TodoItem.getClosestDueComparator();
                break;
            case 1:
                comparator = TodoItem.getFarthestDueComparator();
                break;
            case 2:
                comparator = TodoItem.getAtoZComparator();
                break;
            case 3:
                comparator = TodoItem.getZtoAComparator();
                break;
        }
        //Sort the list
        Collections.sort(showData, comparator);
        //Refresh the listview with the new showData.
        tdAdapter.setData(showData);
        tdAdapter.notifyDataSetChanged();
    }

    /************* YOU SHOULD NOT TOUCH THE CODE BELOW ********************************/
    private void refreshCategorySpinner(){
        categories = new ArrayList<>(defaultCategories);
        categories.addAll(newCategories);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                categories);
        categorySpinner.setAdapter(spinnerArrayAdapter);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *When the activity enters the Resumed state, it comes to the foreground, and then the system invokes the onResume()
     *Note that, in the other two activities, when we want to come back to MainActivity, we use onBackPressed() to
     *switch back. onResume() will be called at that time.
     */
    @Override
    protected void onResume() {
        super.onResume();
        refreshTodoList();
        refreshCategorySpinner();
    }


    /**
     * onPause() is where you deal with the user leaving your activity.
     */
    @Override
    protected void onPause() {
        super.onPause();

            FileUtil.writeTodoList(allData);
            FileUtil.writeCategories(newCategories);
        }

    float x1,x2,y1,y2;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1= event.getX();
                y1= event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2= event.getX();
                y2= event.getY();

                if(x1<x2){
                    Intent intent = new Intent(this, ItemActivity.class);
                    startActivity(intent);


                }
                else if(x1>x2){
                    Intent intent = new Intent(this,CategoryActivity.class);
                    startActivity(intent);

                }
                break;
        }
        return false;
    }
}
