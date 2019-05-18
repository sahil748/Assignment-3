package com.example.studentmanagementsystem;

public class StudentDetails {
    private String mName;
    String mRollNo;
    private String mClass;
    StudentDetails(String mName, String mRollNo, String mClass)
    {
        this.mName=mName;
        this.mRollNo=mRollNo;
        this.mClass=mClass;
    }

    /**
     *
     * @return name of student
     */
    public String getName() {
        return mName;
    }

    /**
     *
     * @return  rollno of student
     */
    public String getRollNo() {
        return mRollNo;
    }

    /**
     *
     * @return class of student
     */
    public String getStudentClass() {
        return mClass;
    }
}
