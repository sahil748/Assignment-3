package com.example.studentmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StudentDetailsActivity extends AppCompatActivity {

    private int mPosition=0;
    private String mName,mPos;
    public int flag =Constant.VALUE_ONE;
    private EditText et_name,et_rollno,et_class;
    private Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        View view=null;
        Intent intent=getIntent();
        mName=intent.getStringExtra(Constant.FUNCTION);                                              //get values from StudentslistActivity sent through intent
        mPos=(intent.getStringExtra(Constant.POSSITION));
        if(mPos!=null)
        {
           mPosition=Integer.parseInt(mPos);
        }
        performSelectedFunction();
    }

    /**
     * perform selected function
     */
    private void performSelectedFunction() {
        switch (mName){
            case Constant.ADD:
                break;
            case Constant.VIEW:
                viewFunction();
                break;
            case Constant.EDIT:
                editFunction();
        }
    }

    /**
     * update data to list which is updated by user
     * flag is used to send different values to Studentlist activity to differentiate between update and add
     */
    private void editFunction() {
        flag=Constant.VALUE_ONE;
        getEditText();
        setEditText();
        addButton.setText(R.string.studentDetailsActivity_btn_updateText);
        flag=Constant.VALUE_ZERO;

    }

    /**
     * get edittext to set data
     */
    public void getEditText()
    {
        et_name=(EditText)findViewById(R.id.id_studentDetails_et_name);
        et_rollno=(EditText)findViewById(R.id.id_studentDetails_et_rollno);
        et_class=(EditText)findViewById(R.id.id_studentDetails_et_class);
        addButton=(Button)findViewById(R.id.id_studentDetails_btn_add);
    }
     /**
      * set data to edittext
      *
      */
    public void setEditText()
    {
        et_name.setText(StudentsListActivity.sAl_StudentsDetails.get(mPosition).getName());
        et_rollno.setText(StudentsListActivity.sAl_StudentsDetails.get(mPosition).getRollNo());
        et_class.setText(StudentsListActivity.sAl_StudentsDetails.get(mPosition).getStudentClass());
    }

    /**
     * show data of selected view in recycler view
     *
     */
    public void viewFunction()
    {
        getEditText();
        setEditText();
        addButton.setVisibility(View.GONE);
        et_name.setEnabled(false);
        et_rollno.setEnabled(false);
        et_class.setEnabled(false);

    }

    /**
     * send extra data to Student list activity as per button clicked after validating all input in edittext
     * @param view
     */
    public void sendDetails(View view) {


        getEditText();
        String studentClass = et_class.getText().toString();

        String rollno = (et_rollno.getText().toString());
        String name = et_name.getText().toString();

        if (Helper.validateName(view, et_name) &&Helper.validateRollno(view,et_rollno)&&Helper.validateClass(view, et_class)) {
            Intent intent = new Intent();
            if(flag==0)
            {
                intent.putExtra(Constant.FUNCTION,Constant.EDIT);
                intent.putExtra(Constant.POSSITION,mPosition);
            }
            else {
                    intent.putExtra(Constant.FUNCTION,Constant.ADD);
            }
            intent.putExtra(Constant.STUDENT_NAME, name);
            intent.putExtra(Constant.STUDENT_ROLLNO, rollno);
            intent.putExtra(Constant.STUDENT_CLASS, studentClass);
            setResult(RESULT_OK, intent);
            finish();
        }

    }

}
