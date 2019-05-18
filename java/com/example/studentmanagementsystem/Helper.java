package com.example.studentmanagementsystem;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Pattern;

import static com.example.studentmanagementsystem.StudentsListActivity.sAl_StudentsDetails;
import static com.example.studentmanagementsystem.StudentsListActivity.sAl_StudentsDetails;
import static java.lang.Integer.valueOf;

public class Helper extends AppCompatActivity
{
    private  static String  mName;
    private static  String mClass;
    private static String mRollno;
    private static final Pattern mValidatesName =                                                          //validation pattern for name
            Pattern.compile("[a-zA-Z][a-zA-Z ]+[a-zA-Z]$");
    private static final Pattern mValidatesClass=Pattern.compile("[a-zA-Z]+$");

    /**
     *
     * @param view     for snackbar to display error (if any)
     * @param et_name  name EditText to validate name
     * @return          true if no input error and false for error
     */
    public static boolean validateName(View view,EditText et_name)
    {
        mName = et_name.getEditableText().toString().trim();
        if (mName.isEmpty()) {
            displaySnackbar(view, R.string.error_enter_name);
            et_name.requestFocus();
            return false;
        }
        else if (!mValidatesName.matcher(mName).matches()) {
           displaySnackbar(view, R.string.error_invalid_name);
           et_name.requestFocus();
            return false;
        }
        return true;
    }

    /**
     *
     * @param view        for snackbar to display error (if any)
     * @param et_rollno   rollno editText to validate rollno
     * @return            true if no input error and false for error
     */
    public static boolean validateRollno(View view, EditText et_rollno)
    {
         mRollno=et_rollno.getEditableText().toString().trim();
        if(mRollno.isEmpty())
        {
            displaySnackbar(view,R.string.error_enter_rollno);
            et_rollno.requestFocus();
            return false;
        }
        else
            return true;
    }
    public static boolean validateClass(View view,EditText et_class)
    {
        mClass = et_class.getEditableText().toString().trim();
        if (mClass.isEmpty()) {
           displaySnackbar(view, R.string.error_enter_class);
            et_class.requestFocus();
            return false;
        }
        else if (!mValidatesClass.matcher(mClass).matches()) {
           displaySnackbar(view,R.string.error_invalid_class);
           et_class.requestFocus();
            return false;
        }
        return true;
    }


    public static void sort(final int  option)
    {
        Collections.sort(sAl_StudentsDetails, new Comparator<StudentDetails>()       //sorting customers according to names using comparator
        {
            @Override
            public int compare(StudentDetails c1,StudentDetails c2)
            {
                if(option==1)
                    return String.valueOf(c1.getName()).compareTo(c2.getName());
                else
                    return (c1.getRollNo()).compareTo(c2.getRollNo());
            }
        });
    }

    /**
     *                        displays error messages
     * @param view            error message will be displayed on this view
     * @param displayStr      error message
     */
    public static void displaySnackbar(View view, int displayStr) {
            Snackbar.make(view, displayStr, Snackbar.LENGTH_SHORT).show();
        }


}
