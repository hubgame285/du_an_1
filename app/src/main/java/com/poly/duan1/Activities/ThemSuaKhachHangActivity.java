package com.poly.duan1.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.poly.duan1.DAO.KhachHangDAO;
import com.poly.duan1.Models.KhachHang;
import com.poly.duan1.R;

import java.util.ArrayList;

public class ThemSuaKhachHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView mTitle, edXoa;
    ActionBar ab;

    EditText edTenKH, edSDTKH;

    ArrayList<KhachHang> list;
    KhachHangDAO dao;
    KhachHang items;

    int mMaKH,mLoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sua_khach_hang);
        edTenKH = findViewById(R.id.edTenKH);
        edSDTKH = findViewById(R.id.edSDTKH);
        edXoa = findViewById(R.id.edXoa);
        // khi nhấn vào edittext mới hiện con trỏ
        edTenKH.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        edSDTKH.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        // set toolbar thay thế actionbar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("");
        mTitle.setText("Khách hàng");
        //set icon
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24);
        ab.setDisplayHomeAsUpEnabled(true);
        //nhấn vào icon back bên trái trên màn hình thực hiện onBackPressed();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThemSuaKhachHangActivity.this,KhachHangActivity.class));
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
            }
        });
        // xóa dữ liệu
        edXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoa(mMaKH);
            }
        });

        dao = new KhachHangDAO(this);
        list = (ArrayList<KhachHang>) dao.getAll();

        //lấy dữ liệu từ bundle 0: thêm    1:sủa
        Bundle bundle = getIntent().getExtras();
        mLoai = bundle.getInt("mLoai");
        mMaKH = bundle.getInt("mMaKH");
        if (mLoai==1){
            mTitle.setText("Sửa khách hàng");
            // set dữ liệu vào edittext và hiện nút xóa
            for (int i =0; i<=list.size()-1;i++){
                items = list.get(i);
                if (items.maKH==mMaKH){
                    edTenKH.setText(""+items.tenKH);
                    edSDTKH.setText(""+items.SDT);
                    edXoa.setVisibility(View.VISIBLE);
                }
            }
        }else {
            //ẩn nút xóa
            mTitle.setText("Thêm khách hàng");
            edXoa.setVisibility(View.GONE);
        }
    }
    // set menu và toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_them_sua,menu);
        return true;
    }

    // thực hiện khi click icon trên toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            // lưu và chuyển về activity cũ
            case R.id.action_luu:
                if (validate()==0){
                    items = new KhachHang();
                    items.tenKH = edTenKH.getText().toString().trim();
                    items.SDT = edSDTKH.getText().toString().trim();
                    if (mLoai==0){
                        dao.insert(items);
                    }else {
                        items.maKH = mMaKH;
                        dao.update(items);
                    }
                    startActivity(new Intent(ThemSuaKhachHangActivity.this,KhachHangActivity.class));
                    overridePendingTransition(R.anim.in,R.anim.out);
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    // hiện dialog xác nhận xóa
    public void xoa(final int Id){
        //Su dung alert
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc muốn xóa không?");
        builder.setCancelable(true);

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dao.delete(Id+"");
                startActivity(new Intent(ThemSuaKhachHangActivity.this,KhachHangActivity.class));
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.gray));
            }
        });
        alert.show();

    }

    // bắt lỗi khi nhập và hiện dialog cảnh báo
    int validate(){
        int check = 0;
        if (edTenKH.getText().toString().trim().length()==0 | edSDTKH.getText().toString().trim().length()==0 | checkKhoangTrang(edTenKH.getText().toString().trim())==true){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cảnh báo");
            builder.setMessage("Không được để trống thông tin !");
            builder.setCancelable(true);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            check =1;
        } else if (edSDTKH.getText().toString().trim().length()>=12 | edSDTKH.getText().toString().trim().length()<=9){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cảnh báo");
            builder.setMessage("Số điện thoại phải từ 10 đến 11 chữ số !");
            builder.setCancelable(true);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            check =1;
        }
        return  check;
    }

    //kiem tra nhap khoang trang
    public boolean checkKhoangTrang(String chuoi){
        Integer bienTam=0;
        for(int i=0;i<chuoi.length();i++) {
            char c = chuoi.charAt(i);
            char kt = ' ';
            if(c == kt){
                bienTam++;
            }
        }
        if(bienTam==chuoi.length()){
            return true;
        }
        return false;
    }
}