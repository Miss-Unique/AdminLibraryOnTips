package com.example.soumyaagarwal.libraryontipsadmin.ViewBook;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.soumyaagarwal.libraryontipsadmin.R;

import java.util.List;

public class ViewBookAdapter extends RecyclerView.Adapter<ViewBookAdapter.MyViewHolder> implements View.OnClickListener {

    private List<viewbd> bookList;
    private List<viewbd> filtered;

    public ViewBookAdapter(List<viewbd> bookList) {
        this.bookList = bookList;
    }

    @Override
    public void onClick(View v) {
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView book_name,book_author,book_subject;

        public MyViewHolder(View view) {
            super(view);
            book_name = (TextView) view.findViewById(R.id.book_name);
            book_author = (TextView) view.findViewById(R.id.book_author);
            book_subject = (TextView) view.findViewById(R.id.book_subject);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewbook_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final viewbd book = bookList.get(position);
        holder.book_name.setText(book.getBook_name());
        holder.book_author.setText(book.getBook_author());
        holder.book_subject.setText(book.getBook_subject());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

}
