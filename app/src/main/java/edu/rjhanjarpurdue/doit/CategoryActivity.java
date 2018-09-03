package edu.rjhanjarpurdue.doit;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Calendar;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CategoryActivity";

    private EditText noteEditText;

    private Button newCategoryBtn;
    private Button cancelBtn;
    private TextView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //TODO: 1. Initialize all views using findViewById()
        //TODO: 2. Register OnClickListener for the two buttons

        titleView = (TextView)findViewById(R.id.title);
        noteEditText = (EditText)findViewById(R.id.category_edittext);
        cancelBtn = (Button)findViewById(R.id.cancel_btn);
        newCategoryBtn = (Button) findViewById(R.id.action_add);

        cancelBtn.setOnClickListener(this);
        newCategoryBtn.setOnClickListener(this);
        titleView.setText("Add New Category");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
                //TODO: 3. When cancel button is clicked, go back to MainActivity
                /*
                TODO: 4. When add is clicked, you need to check the following conditions
                      (1) If category is empty, popup a dialog says 'Category cannot be empty'
                      (2) If category already exists, popup a dialog says 'Category exists'
                      (3) If a valid category is input, add it to MainActivity.newCategories and go back to MainActivity using onBackPressed()
                */
            case R.id.cancel_btn:
                onBackPressed();
                break;

            case R.id.action_add:

                int a=0;
                for(int i=0;i<MainActivity.categories.size();i++){
                    if(noteEditText.getText().toString().toLowerCase().equals((MainActivity.categories.get(i)).toLowerCase())){
                       a=1;
                       break;
                    }
                }

                if(noteEditText.getText().toString().isEmpty()){
                    AlertDialog alertDialog = new AlertDialog.Builder(CategoryActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Category cannot be empty");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else if(a==1){
                    AlertDialog alertDialog = new AlertDialog.Builder(CategoryActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Category exists");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else if(!noteEditText.getText().toString().isEmpty() && a==0){
                    MainActivity.newCategories.add(noteEditText.getText().toString());
                    onBackPressed();
                }
                break;

        }
    }
}
