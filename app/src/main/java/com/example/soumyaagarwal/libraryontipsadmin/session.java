package com.example.soumyaagarwal.libraryontipsadmin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class session {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int mode=0;

    String prefname="SESSION";
    private String is_login="IsLoggedIn";
    private String is_signup="IsSignIn";
    public String username="name";
    public String password="pass";

    public session(Context context)
    {
        this._context=context;
        pref= _context.getSharedPreferences(prefname,mode);
        editor=pref.edit();
    }

    public void createSignupsession(String email)
    {
        editor.putBoolean(is_login,true);
        editor.putString(username,email);
        editor.commit();
    }

    public void createloginsession(String email)
    {
        editor.putBoolean(is_login,true);
        editor.putString(username,email);
        editor.commit();
    }

    public boolean isloggedin()
    {
        return pref.getBoolean(is_login,false);
    }


    public boolean issignedup()
    {
        return pref.getBoolean(is_signup,false);
    }

    public void checklogin()
    {
        if(!this.isloggedin())
        {
            Intent intent =new Intent(_context,log_in.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(intent);

        }
    }


    public void checksignup()
    {
        if(!this.issignedup())
        {
            Intent intent =new Intent(_context,sign_up.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(intent);
        }
        else
        {
            Intent intent =new Intent(_context,log_in.class);
            _context.startActivity(intent);
        }
    }
    public HashMap<String, String> getuserdetails()
    {
        HashMap<String,String> details = new HashMap<String, String>();
        details.put(username,pref.getString(username,null));
        details.put(password,pref.getString(password,null));

        return details;
    }

    public void clearloginsession()
    {
        editor.clear();
        editor.commit();
        Intent intent=new Intent(_context,log_in.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(intent);
    }

    public void clearsignupsession()
    {
        editor.clear();
        editor.commit();
        Intent intent=new Intent(_context,sign_up.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(intent);
    }
}
