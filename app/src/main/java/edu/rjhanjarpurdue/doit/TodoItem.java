package edu.rjhanjarpurdue.doit;
//Don't copy the package statement

import java.io.Serializable;
import java.util.Comparator;

/**
 * This Class is the data model of a todoitem (An entry of the todolist).
 * You need to implement 4 methods to return different Comparators.
 * A Comparator defines how two objects are compared. Comparators can be used for sorting a list.
 *
 * Search online to find (1) How to compare two date Strings in Java (HINT: Use SimpleDateFormat)
 *                           Note: The date String is in the format of "MM/dd/yy"
 *                       (2) How to compare two Strings alphabetically (String has a built-in method to do it)
 */

public class TodoItem implements Serializable{


    private String category;
    private String note;
    private String date;

    public TodoItem(String category, String note, String date) {
        this.category = category;
        this.note = note;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    static Comparator<TodoItem> getClosestDueComparator() {
        return new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem item1, TodoItem item2) {
                //TODO: 1. Returns 1 if item2's due date is closer than item1's
                //         Returns -1 if item1's due date is closer than item2's
                //         Returns 0 if they have the same due date
                int day1 = Integer.parseInt(item1.getDate().substring(3,5));
                int month1 = Integer.parseInt(item1.getDate().substring(0,2));
                int year1 = Integer.parseInt(item1.getDate().substring(6,8));

                int day2 = Integer.parseInt(item2.getDate().substring(3,5));
                int month2 = Integer.parseInt(item2.getDate().substring(0,2));
                int year2 = Integer.parseInt(item2.getDate().substring(6,8));

                if(year1>year2){
                    return 1;
                }
                else if(year1<year2){
                    return -1;
                }
                else if(month1>month2){
                    return 1;
                }
                else if(month1<month2){
                    return -1;
                }
                else if(day1>day2){
                    return 1;
                }
                else if(day1<day2){
                    return -1;
                }

                return 0;
            }
        };
    }

    static Comparator<TodoItem> getFarthestDueComparator() {
        return new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem item1, TodoItem item2) {
                //TODO: 2. Returns 1 if item1's due date is closer than item2's
                //         Returns -1 if item2's due date is closer than item1's
                //         Returns 0 if they have the same due date
                int day1 = Integer.parseInt(item1.getDate().substring(3,5));
                int month1 = Integer.parseInt(item1.getDate().substring(0,2));
                int year1 = Integer.parseInt(item1.getDate().substring(6,8));

                int day2 = Integer.parseInt(item2.getDate().substring(3,5));
                int month2 = Integer.parseInt(item2.getDate().substring(0,2));
                int year2 = Integer.parseInt(item2.getDate().substring(6,8));

                if(year1<year2){
                    return 1;
                }
                else if(year1>year2){
                    return -1;
                }
                else if(month1<month2){
                    return 1;
                }
                else if(month1>month2){
                    return -1;
                }
                else if(day1<day2){
                    return 1;
                }
                else if(day1>day2){
                    return -1;
                }


                return 0;
            }
        };
    }

    static Comparator<TodoItem> getAtoZComparator() {
        return new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem item1, TodoItem item2) {
                //TODO: 3. Returns 1 if item1's note is alphabetically lager than item2's
                //         Returns -1 if item1's note is alphabetically smaller than item2's
                //         Returns 0 if they are equal
                int length;
                if(item1.getNote().length()<item2.getNote().length()){
                    length= item1.getNote().length();
                }
                else length = item2.getNote().length();

                for(int i=0;i<length;i++){
                    int int1= item1.getNote().toLowerCase().charAt(i);
                    int int2= item2.getNote().toLowerCase().charAt(i);

                    if(int1>int2){
                        return 1;
                    }
                    else if(int1<int2){
                        return -1;
                    }
                }
                if(item1.getNote().length()>item2.getNote().length()){
                    return 1;
                }
                else if(item1.getNote().length()<item2.getNote().length()){
                    return -1;
                }


                return 0;
            }
        };
    }

    static Comparator<TodoItem> getZtoAComparator() {
        return new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem item1, TodoItem item2) {
                //TODO: 4. Returns 1 if item1's note is alphabetically smaller than item2's
                //         Returns -1 if item1's note is alphabetically larger than item2's
                //         Returns 0 if they are equal
                int length;
                if(item1.getNote().length()<item2.getNote().length()){
                    length= item1.getNote().length();
                }
                else length = item2.getNote().length();

                for(int i=0;i<length;i++){
                    int int1= item1.getNote().toLowerCase().charAt(i);
                    int int2= item2.getNote().toLowerCase().charAt(i);

                    if(int1<int2){
                        return 1;
                    }
                    else if(int1>int2){
                        return -1;
                    }
                }
                if(item1.getNote().length()<item2.getNote().length()){
                    return 1;
                }
                else if(item1.getNote().length()>item2.getNote().length()){
                    return -1;
                }

                return 0;
            }
        };
    }

}
