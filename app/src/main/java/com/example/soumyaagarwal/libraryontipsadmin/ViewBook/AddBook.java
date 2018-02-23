package com.example.soumyaagarwal.libraryontipsadmin.ViewBook;

/**
 * Created by Soumya on 2/23/2018.
 */

public class AddBook {

    String Title, Author, ShelfNo, Subject, Ratings, RatingPeopleNumber, CopiesNo, AvailableCopies, Branch;

    public AddBook() {
    }

    public AddBook(String title, String author, String shelfNo, String subject, String ratings, String ratingPeopleNumber, String copiesNo, String availableCopies, String branch) {
        Title = title;
        Author = author;
        ShelfNo = shelfNo;
        Subject = subject;
        Ratings = ratings;
        RatingPeopleNumber = ratingPeopleNumber;
        CopiesNo = copiesNo;
        AvailableCopies = availableCopies;
        Branch = branch;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getShelfNo() {
        return ShelfNo;
    }

    public void setShelfNo(String shelfNo) {
        ShelfNo = shelfNo;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getRatings() {
        return Ratings;
    }

    public void setRatings(String ratings) {
        Ratings = ratings;
    }

    public String getRatingPeopleNumber() {
        return RatingPeopleNumber;
    }

    public void setRatingPeopleNumber(String ratingPeopleNumber) {
        RatingPeopleNumber = ratingPeopleNumber;
    }

    public String getCopiesNo() {
        return CopiesNo;
    }

    public void setCopiesNo(String copiesNo) {
        CopiesNo = copiesNo;
    }

    public String getAvailableCopies() {
        return AvailableCopies;
    }

    public void setAvailableCopies(String availableCopies) {
        AvailableCopies = availableCopies;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }
}
