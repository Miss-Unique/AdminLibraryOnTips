package com.example.soumyaagarwal.libraryontipsadmin.ModelClass;

public class HistoryBook
{
    private String Title,Author,IssuedDate,ReturnDate,BarCode,ISBN_No;
    private String Fine;

    public HistoryBook() {
    }

    public HistoryBook(String title, String author, String issuedDate, String returnDate, String barCode, String ISBN_No) {
        Title = title;
        Author = author;
        IssuedDate = issuedDate;
        ReturnDate = returnDate;
        BarCode = barCode;
        this.ISBN_No = ISBN_No;
    }

    public HistoryBook(String title, String author, String issuedDate, String returnDate, String barCode, String fine, String ISBN_No) {
        Title = title;
        Author = author;
        IssuedDate = issuedDate;
        ReturnDate = returnDate;
        BarCode = barCode;
        Fine = fine;
        this.ISBN_No = ISBN_No;
    }

    public String getFine() {
        return Fine;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public void setFine(String fine) {
        Fine = fine;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getISBN_No() {
        return ISBN_No;
    }

    public void setISBN_No(String ISBN_No) {
        this.ISBN_No = ISBN_No;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getIssuedDate() {
        return IssuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        IssuedDate = issuedDate;
    }

    public String getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(String returnDate) {
        ReturnDate = returnDate;
    }
}
