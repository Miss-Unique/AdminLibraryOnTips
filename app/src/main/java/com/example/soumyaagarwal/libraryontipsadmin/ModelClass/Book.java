package com.example.soumyaagarwal.libraryontipsadmin.ModelClass;

import java.util.ArrayList;
import java.util.HashMap;

public class Book {
    private String ISBN_No;
    private String Title;
    private String Author;
    private String Subject;
    private String Branch;
    private String ShelfNo;
    private String Ratings;
    private String RatingPeopleNumber;
    private String CopiesNo;
    private String AvailableCopies;
    private String Path;
//    HashMap<String,Integer> RatingPeople;
//    ArrayList<String> BarCodeCopies = new ArrayList<>();

    public Book(String ISBN_No, String title, String author, String subject, String branch, String shelfNo, String ratings, String ratingPeopleNumber, String copiesNo, String availableCopies, String noOfPages, String path) {
        this.ISBN_No = ISBN_No;
        Title = title;
        Author = author;
        Subject = subject;
        Branch = branch;
        ShelfNo = shelfNo;
        Ratings = ratings;
        RatingPeopleNumber = ratingPeopleNumber;
        CopiesNo = copiesNo;
        AvailableCopies = availableCopies;
        NoOfPages = noOfPages;
        Path = path;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }


    public String getShelfNo() {
        return ShelfNo;
    }

    public void setShelfNo(String shelfNo) {
        ShelfNo = shelfNo;
    }


    public String getNoOfPages() {
        return NoOfPages;
    }

    public void setNoOfPages(String noOfPages) {
        NoOfPages = noOfPages;
    }

    private String NoOfPages;

    public void setISBN_No(String ISBN_No) {
        this.ISBN_No = ISBN_No;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public void setRatings(String ratings) {
        Ratings = ratings;
    }

    public void setRatingPeopleNumber(String ratingPeopleNumber) {
        RatingPeopleNumber = ratingPeopleNumber;
    }

    public void setCopiesNo(String copiesNo) {
        CopiesNo = copiesNo;
    }

    public void setAvailableCopies(String availableCopies) {
        AvailableCopies = availableCopies;
    }

    public String getISBN_No() {
        return ISBN_No;
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return Author;
    }

    public String getSubject() {
        return Subject;
    }

    public String getRatings() {
        return Ratings;
    }

    public String getRatingPeopleNumber() {
        return RatingPeopleNumber;
    }

    public String getCopiesNo() {
        return CopiesNo;
    }

    public String getAvailableCopies() {
        return AvailableCopies;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public Book() {
    }
}
