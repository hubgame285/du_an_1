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

import com.poly.duan1.DAO.NhaSanXuatDAO;
import com.poly.duan1.Models.NhaSanXuat;
import com.poly.duan1.Models.SanPham;
import com.poly.duan1.R;

import java.util.ArrayList;
import java.util.List;

public class ThemSuaNhaSanXuatActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView mTitle;
    ActionBar ab;

    EditText edTenNSX, edXoa;

    ArrayList<NhaSanXuat> list;
    NhaSanXuatDAO dao;
    NhaSanXuat items;

    int mMaNSX,mLoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sua_nha_san_xuat);
        edTenNSX = findViewById(R.id.edTenNSX);
        edXoa = findViewById(R.id.edXoa);
        // khi nhấn vào edittext mới hiện con trỏ
        edTenNSX.setOnTouchListener(new View.OnTouchListener() {
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
        mTitle.setText("Nhà sản xuất");
        //set icon
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24);
        ab.setDisplayHomeAsUpEnabled(true);
        //nhấn vào icon back bên trái trên màn hình thực hiện onBackPressed();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThemSuaNhaSanXuatActivity.this,NhaSanXuatActivity.class));
                finish();
            }
        });
        // xóa dữ liệu
        edXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoa(mMaNSX);
            }
        });

        dao = new NhaSanXuatDAO(this);
        list = (ArrayList<NhaSanXuat>) dao.get();

        //lấy dữ liệu từ bundle 0: thêm    1:sủa
        Bundle bundle = getIntent().getExtras();
        mLoai = bundle.getInt("mLoai");
        mMaNSX = bundle.getInt("mMaNSX");
        if (mLoai==1){
            // set dữ liệu vào edittext và hiện nút xóa
            mTitle.setText("Sửa nhà sản xuất");
            for (int i =0; i<=list.size()-1;i++){
                items = list.get(i);
                if (items.getMaNSX()==mMaNSX){
                    edTenNSX.setText(""+items.getTenNSX());
                    edXoa.setVisibility(View.VISIBLE);
                }
            }
        }else {
            //ẩn nút xóa
            mTitle.setText("Thêm nhà sản xuất");
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
                items = new NhaSanXuat();
                items.setTenNSX(edTenNSX.getText().toString().trim().toUpperCase());
                if (mLoai==0){
                    dao.insert(items);
                }else {
                    items.setMaNSX(mMaNSX);
                    dao.update(items);
                }
                startActivity(new Intent(ThemSuaNhaSanXuatActivity.this,NhaSanXuatActivity.class));
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
                dao.delete(Id);
                startActivity(new Intent(ThemSuaNhaSanXuatActivity.this,NhaSanXuatActivity.class));
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
        if (edTenNSX.getText().toString().trim().length()==0 || checkKhoangTrang(edTenNSX.getText().toString())==true){
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
        } else if (checkTenNSX(edTenNSX.getText().toString().trim().toUpperCase())){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cảnh báo");
            builder.setMessage("Tên nhà sản xuất đã tồn tại!");
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

    //kiem tra trung ten nha san xuat
    public boolean checkTenNSX(String tenNSX){
        List<NhaSanXuat> data = dao.get() ;
        if (tenNSX.length() > 0 && mLoai == 0) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getTenNSX().equals(tenNSX.trim().toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }
}