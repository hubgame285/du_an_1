package com.poly.duan1.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.poly.duan1.DAO.NhanVienDAO;
import com.poly.duan1.Models.NhanVien;
import com.poly.duan1.R;

public class DoiMatKhauActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView mTitle;
    ActionBar ab;

    EditText edPassOld, edPassNew, edRePass;


    NhanVienDAO dao;
    NhanVien items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        edPassOld = findViewById(R.id.edPassOld);
        edPassNew = findViewById(R.id.edPassNew);
        edRePass = findViewById(R.id.edRePass);

        // khong hien con tro tren editex
        edPassOld.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        edPassNew.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        edRePass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("");
        mTitle.setText("Đổi mật khẩu");
        //set icon
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24);
        ab.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        dao = new NhanVienDAO(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_them_sua,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_luu:
                //doc user, pass trong SharedPreferences
                SharedPreferences pref = DoiMatKhauActivity.this.getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = pref.getString("tenDN","");
                if (validate()>0){
                    NhanVien nhanVien = dao.getByTK(user);
                    nhanVien.setMatKhau(edPassNew.getText().toString());
                    dao.updatePass(nhanVien);
                    if(dao.updatePass(nhanVien) > 0){
                        Toast.makeText(DoiMatKhauActivity.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = DoiMatKhauActivity.this.getSharedPreferences("USER_FILE",MODE_PRIVATE).edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(DoiMatKhauActivity.this,DangNhapActivity2.class));
                    }else {
                        Toast.makeText(DoiMatKhauActivity.this, "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }
    public int validate(){
        int check = 1;
        if (edPassOld.getText().length()==0 || edPassNew.getText().length()==0 || edRePass.getText().length()==0){
            Toast.makeText(DoiMatKhauActivity.this, "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            //doc user, pass trong SharedPreferences
            SharedPreferences pref = DoiMatKhauActivity.this.getSharedPreferences("USER_FILE",Context.MODE_PRIVATE);
            String passOld = pref.getString("mk","");
            String pass = edPassNew.getText().toString();
            String rePass = edRePass.getText().toString();
            if (!passOld.equals(edPassOld.getText().toString())){
                Toast.makeText(DoiMatKhauActivity.this, "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!pass.equals(rePass)){
                Toast.makeText(DoiMatKhauActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }

        }
        return check;
    }
}