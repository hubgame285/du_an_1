package com.poly.duan1.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

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

import com.poly.duan1.DAO.NhanVienDAO;
import com.poly.duan1.Models.NhanVien;
import com.poly.duan1.R;

import java.util.ArrayList;

public class ThemSuaNhanVienActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView mTitle;
    ActionBar ab;

    EditText edTenNV, edSDTNV, edXoa, edTK, edMK;
    TextView tvVaiTro;
    ConstraintLayout layoutVaiTro;

    ArrayList<NhanVien> list;
    NhanVienDAO dao;
    NhanVien items;

    int mMaNV, mLoai, mVaiTro, mWhich=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sua_nhan_vien);
        tvVaiTro = findViewById(R.id.tvVaiTro);
        layoutVaiTro = findViewById(R.id.layoutVaiTro);
        edTenNV = findViewById(R.id.edTenNV);
        edSDTNV = findViewById(R.id.edSDTNV);
        edXoa = findViewById(R.id.edXoa);
        edTK = findViewById(R.id.edTK);
        edMK = findViewById(R.id.edMK);

        // thêm mặt định 2 vai trò quản lý, nhân viên
        ArrayList<String> listVaiTro = new ArrayList<>();
        listVaiTro.add("Quản lý");
        listVaiTro.add("Nhân viên");
        // khi nhấn vào hiện dialog chọn vai trò
        layoutVaiTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ThemSuaNhanVienActivity.this);
                builder.setTitle("Chọn vai trò");
                builder.setSingleChoiceItems(listVaiTro.toArray(new String[listVaiTro.size()]), mVaiTro, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            mWhich = which;
                    }
                });

                builder.setCancelable(true);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mVaiTro = mWhich;
                        tvVaiTro.setText(listVaiTro.get(mVaiTro));
                        dialog.dismiss();
                    }
                });
                androidx.appcompat.app.AlertDialog alertDialog =builder.create();
                alertDialog.show();
            }
        });
        // khi nhấn vào edittext mới hiện con trỏx
        edTenNV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        edSDTNV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        edTK.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        edMK.setOnTouchListener(new View.OnTouchListener() {
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
        mTitle.setText("Nhân Viên");
        //set icon
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24);
        ab.setDisplayHomeAsUpEnabled(true);
        //nhấn vào icon back bên trái trên màn hình thực hiện onBackPressed();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThemSuaNhanVienActivity.this,NhanVienActivity.class));
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
            }
        });
        // xóa dữ liệu
        edXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoa(mMaNV);
            }
        });

        dao = new NhanVienDAO(this);
        list = (ArrayList<NhanVien>) dao.get();

        //lấy dữ liệu từ bundle 0: thêm    1:sủa
        Bundle bundle = getIntent().getExtras();
        mLoai = bundle.getInt("mLoai");
        mMaNV = bundle.getInt("mMaNV");
        if (mLoai==1){
            // set dữ liệu vào edittext và hiện nút xóa
            mTitle.setText("Sửa nhân viên");
            for (int i =0; i<=list.size()-1;i++){
                items = list.get(i);
                if (items.getMaNV()==mMaNV){
                    edTenNV.setText(""+items.getHoTen());
                    edSDTNV.setText(""+items.getSDT());
                    edTK.setText(""+items.getTaiKhoan());
                    edMK.setText(""+items.getMatKhau());
                    edXoa.setVisibility(View.VISIBLE);
                    if (items.getVaiTro()==0){
                        tvVaiTro.setText("Quản lý");
                    } else {
                        tvVaiTro.setText("Nhân viên");
                    }
                }
            }
        }else {
            //ẩn nút xóa
            mTitle.setText("Thêm nhân viên");
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
                    items = new NhanVien();
                    items.setHoTen(edTenNV.getText().toString().trim()); ;
                    items.setSDT(edSDTNV.getText().toString().trim());
                    items.setTaiKhoan(edTK.getText().toString().trim());
                    items.setMatKhau(edMK.getText().toString().trim());
                    if (mVaiTro==0){
                        items.setVaiTro(0);
                    }
                    if (mVaiTro==1){
                        items.setVaiTro(1);
                    }
                    if (mLoai==0){
                        dao.insert(items);
                    }
                    if (mLoai==1){
                        items.setMaNV(mMaNV);
                        dao.update(items);
                    }
                    startActivity(new Intent(ThemSuaNhanVienActivity.this,NhanVienActivity.class));
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
                startActivity(new Intent(ThemSuaNhanVienActivity.this,NhanVienActivity.class));
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
        if (edTenNV.getText().toString().trim().length()==0 | edSDTNV.getText().toString().trim().length()==0 | edTK.getText().toString().length()==0 | edMK.getText().toString().length()==0 | tvVaiTro.getText().toString().length()==0 | checkKhoangTrang(edTenNV.getText().toString().trim())==true | checkKhoangTrang(edSDTNV.getText().toString().trim())==true){
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
        } else if (edSDTNV.getText().toString().trim().length()>=12 | edSDTNV.getText().toString().trim().length()<=9){
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