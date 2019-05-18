package com.example.studentmanagementsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.studentmanagementsystem.Helper.sort;
import static java.lang.Integer.valueOf;

public class StudentsListActivity extends AppCompatActivity implements StudentsListAdapter.Contact {
    private RecyclerView mStudentsListRecyclerView;
    private RecyclerView.Adapter mStudentAdapter;
    private boolean mChange=false;
    public static ArrayList<StudentDetails> sAl_StudentsDetails = new ArrayList<>();
    private String mStudentName,mStudentClass,mStudentRollno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitys_student_list);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mStudentAdapter=new StudentsListAdapter(sAl_StudentsDetails,this);
        mStudentsListRecyclerView =(RecyclerView)findViewById(R.id.student_list_recylerView);
        mStudentsListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mStudentsListRecyclerView.setAdapter(mStudentAdapter);

    }
    @Override
    /**
     * inflates sort menu and return true value
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi_rootMenu= getMenuInflater();
        mi_rootMenu.inflate(R.menu.sort_menu,menu);
        return true;
    }

    /**
     *             add students details object to list of student on cliking add button
     *
     * @param name             name of student
     * @param rollno           rollno of student
     * @param sClass           class of student
     */
    public void addData(String name,String rollno,String sClass)
    {

        TextView tv_noData=(TextView)findViewById(R.id.id_studentListActivity_tv_noData);
        tv_noData.setVisibility(View.INVISIBLE);

        sAl_StudentsDetails.add(new StudentDetails(name,rollno,sClass));
        mStudentAdapter.notifyDataSetChanged();
    }

    /**
     *                       send info to studentdetailsactivity to operations view ,edit operations
     * @param Option          tells edit item is selected or view from dialogbox
     * @param position        position of clicked view
     */
    public void performDialogOperations(View view,int Option,int position)
    {

        Intent moveToStudentActivity=new Intent(StudentsListActivity.this, StudentDetailsActivity.class);
        if(Option==Constant.VIEW_OPTION)
        {
            moveToStudentActivity.putExtra(Constant.FUNCTION,Constant.VIEW);
        }
        if(Option==Constant.EDIT_OPTION)
        {
            moveToStudentActivity.putExtra(Constant.FUNCTION,Constant.EDIT);
        }
        moveToStudentActivity.putExtra(Constant.POSSITION,Integer.toString(position));
        setResult(RESULT_OK,moveToStudentActivity);
        startActivityForResult(moveToStudentActivity,Constant.VALUE_ONE);
    }

    /**
     *            sorts student list according to name or roll no as selected from menu
     * @param item          item selected from menu
     */
    public void menuItemSelected(MenuItem item)
    {
        int id =item.getItemId();View view;
        switch (id) {
            case R.id.id_menu_sort_by_name:
                sort(Constant.SORT_BY_NAME);
                mStudentAdapter.notifyDataSetChanged();
                break;
            case R.id.id_menu_sort_by_rollno:
                sort(Constant.SORT_BY_ROLL);
                mStudentAdapter.notifyDataSetChanged();
        }
    }

    /**
     *             change gridview to listview and vice versa also changes button from list to grid and grid to list
     * @param view   to show details of students
     */
    public void changeView(View view)
    {
        Button btn_changeView=(Button)findViewById(R.id.id_toolbar_btn_changeView) ;

        if(!mChange)
        {
            btn_changeView.setText(R.string.toolbar_btn_listText);
            mStudentsListRecyclerView.setLayoutManager(new GridLayoutManager(this, Constant.GRID_SPAN_COUNT));
        }
        else
        {
            btn_changeView.setText(R.string.toolbar_btn_gridText);
            mStudentsListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        mChange=!mChange;
    }


    @Override
    /**
     * get data sent from studentsDetailsActivity and add or set data
     * in list according to value recieved
     */
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constant.VALUE_ONE)
            if(resultCode==RESULT_OK)
            {
                mStudentName=data.getStringExtra(Constant.STUDENT_NAME);
                mStudentRollno=(data.getStringExtra(Constant.STUDENT_ROLLNO));
                mStudentClass=data.getStringExtra(Constant.STUDENT_CLASS);
                if(data.getStringExtra(Constant.FUNCTION).equals(Constant.ADD))
                addData(mStudentName,mStudentRollno,mStudentClass);
                else{
                    int position=Constant.VALUE_ZERO;
                    position = data.getIntExtra(Constant.POSSITION,position);
                    setData(mStudentName,mStudentRollno,mStudentClass,position);
                }
            }
    }

    /**                     replace data from list with argumented data
     *
     * @param name              name of student
     * @param rollno             rollno of student
     * @param studentClass       class of student
     * @param position           position at which details will be updated
     */
    private void setData(String name, String rollno, String studentClass,int position) {
        sAl_StudentsDetails.set(position,new StudentDetails(name,rollno,studentClass));
        mStudentAdapter.notifyDataSetChanged();
    }

    /**  creates a dialog box containing list
     * and performs edit view or delete operation as item selected
     *
     * @param v                 view to show dialog box
     * @param position         position of selected view
     */
    public void onItemClicked(View v, final int position){
        final View view=null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.studentList_DialogBox_Title);

        String[] dialogOptions = {getString(R.string.Dialog_Option_View), getString(R.string.Dialog_Option_Edit), getString(R.string.Dialog_Option_Delete)};
        builder.setItems(dialogOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int option) {
                switch (option) {
                    case 0:
                        performDialogOperations(view,Constant.VIEW_OPTION,position);
                        break;
                    case 1:
                        performDialogOperations(view,Constant.EDIT_OPTION,position);
                        mStudentAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        sAl_StudentsDetails.remove(position);
                        mStudentAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     *
     * move to studentDetailsActivity with extra info
     */
    public void moveToStudentDetails(View view)
    {
        Intent moveToStudentActivity=new Intent(StudentsListActivity.this, StudentDetailsActivity.class);
        moveToStudentActivity.putExtra(Constant.FUNCTION,Constant.ADD);
        startActivityForResult(moveToStudentActivity,Constant.VALUE_ONE);
    }

}

