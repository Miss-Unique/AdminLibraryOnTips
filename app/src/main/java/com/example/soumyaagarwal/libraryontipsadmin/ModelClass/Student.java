package com.example.soumyaagarwal.libraryontipsadmin.ModelClass;


public class Student
{
    private String RollNo,Name;
    private String Password;
    private String TotalFine;
//    ArrayList<HistoryBook> CurrentBookList=new ArrayList<>();
//    ArrayList<HistoryBook> HistoryBookList = new ArrayList<>();
//    ArrayList<String> MarkedBookISBN = new ArrayList<>();

    public Student() {
    }

    public Student(String rollNo, String name, String password, String totalFine)
    {
        RollNo = rollNo;
        Name = name;
        Password = password;
        TotalFine = totalFine;
    }

    public String getRollNo() {
        return RollNo;
    }

    public void setRollNo(String rollNo) {
        RollNo = rollNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getTotalFine() {
        return TotalFine;
    }

    public void setTotalFine(String totalFine) {
        TotalFine = totalFine;
    }

}

