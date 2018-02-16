package com.example.soumyaagarwal.libraryontipsadmin.AddBook;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.soumyaagarwal.libraryontipsadmin.R;

import java.util.List;

import com.example.soumyaagarwal.libraryontipsadmin.ModelClass.CopyBook;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> implements View.OnClickListener {

    private List<CopyBook> bookList;

    public BookAdapter(List<CopyBook> bookList) {
        this.bookList = bookList;
    }

    @Override
    public void onClick(View v) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView barcode,price,dop,status,issuedate,duedate;

        public MyViewHolder(View view) {
            super(view);
            barcode = (TextView) view.findViewById(R.id.rowbarcode);
            price = (TextView) view.findViewById(R.id.rowprice);
            dop = (TextView) view.findViewById(R.id.rowdop);
            issuedate = (TextView) view.findViewById(R.id.rowissuedate);
            duedate = (TextView) view.findViewById(R.id.rowduedate);
            status = (TextView)view.findViewById(R.id.rowstatus);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.copybook_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CopyBook book = bookList.get(position);
        holder.barcode.setText(book.getBarCode());
        holder.price.setText(book.getPrice());
        holder.issuedate.setText(book.getDateIssued());
        holder.duedate.setText(book.getDueDate());
        holder.dop.setText(book.getDateOfPurchase());
        holder.status.setText(book.getIssuedStatus());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

}
