package com.example.studentmanagementsystem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentsListAdapter extends Adapter<StudentsListAdapter.StudentListViewHolder>{
    public Contact contact;
    private View myView;
    private  ArrayList<StudentDetails> mStudentsDetails;

    /**
     *
     * set data in list to send view
     * in recycler view
     * @param StudentsDetails list of students
     */
    public StudentsListAdapter(ArrayList<StudentDetails> StudentsDetails, final Contact itemListener)
    {
        this.mStudentsDetails=StudentsDetails;
        contact=itemListener;
    }

    /**  inflates view from parent view
     *
     * @param parent inflates view on parent
     *
     * @return                    viewHolder
     */
    @Override
    public StudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li_studentDetails=LayoutInflater.from(parent.getContext());
        View view= (View) li_studentDetails.inflate(R.layout.student_details,parent,false);
        return new StudentListViewHolder(view);
    }

    /**
     *            binds data from list to view
     * @param studentListViewHolder       object to hold the views
     * @param position    position of adapter
     */
    @Override
    public void onBindViewHolder(@NonNull StudentListViewHolder studentListViewHolder, int position) {


        TextView tv_name = studentListViewHolder.tv_studentName.findViewById(R.id.studentsList_tv_student_name);
        TextView tv_age = studentListViewHolder.tv_studentRollno.findViewById(R.id.studentsList_tv_student_rollno);
        TextView tv_class = studentListViewHolder.tv_studentClass.findViewById(R.id.studentsList_tv_student_class);


        tv_name.setText(String.valueOf(mStudentsDetails.get(position).getName()));
        tv_age.setText(String.valueOf(mStudentsDetails.get(position).getRollNo()));
        tv_class.setText(String.valueOf(mStudentsDetails.get(position).getStudentClass()));


    }

    /**
     *    returns no of items in arraylist
     * @return size of list
     */
    @Override
    public int getItemCount()
    {
        return mStudentsDetails.size();
    }


    public class StudentListViewHolder  extends RecyclerView.ViewHolder
    {
        TextView tv_studentName;
        TextView tv_studentRollno;
        TextView tv_studentClass;

        public StudentListViewHolder(@NonNull View itemView) {
            super(itemView);
            myView=itemView;
            tv_studentName=(TextView)itemView.findViewById(R.id.studentsList_tv_student_name);
            tv_studentRollno=(TextView)itemView.findViewById(R.id.studentsList_tv_student_rollno);
            tv_studentClass=(TextView)itemView.findViewById(R.id.studentsList_tv_student_class);
            myView.setOnClickListener( new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    contact.onItemClicked(v,getAdapterPosition());
                }
            });
        }
    }

    /**
     * interface to get position of adaptor in recycler view
     */
    public interface Contact{
    public void onItemClicked(View v,int position);
    }

}
