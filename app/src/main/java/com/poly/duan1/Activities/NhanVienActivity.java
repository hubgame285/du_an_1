package com.poly.duan1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.poly.duan1.Adapters.NhanVienAdapter;
import com.poly.duan1.DAO.NhanVienDAO;
import com.poly.duan1.Models.NhanVien;
import com.poly.duan1.R;

import java.util.ArrayList;

public class NhanVienActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView mTitle;
    ActionBar ab;
    EditText edSearchNV;
    ListView lvNV;

    ArrayList<NhanVien> list;
    ArrayList<NhanVien> listSearch;
    NhanVienDAO dao;
    NhanVienAdapter adapter;
    NhanVien item;
    int mMaNV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);
        //anh xa
        edSearchNV = findViewById(R.id.edSearchNV);
        lvNV = findViewById(R.id.lvNV);
        // khi nhấn vào edittext mới hiện con trỏ
        edSearchNV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        // set toolbar thay thế actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("");
        mTitle.setText("Nhân viên");
        //set icon
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24);
        ab.setDisplayHomeAsUpEnabled(true);
        //nhấn vào icon back bên trái trên màn hình thực hiện onBackPressed();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dao = new NhanVienDAO(this);
        capNhatLv();

        // khi click vào item trong listview chuyển activity ThemSua
        lvNV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (edSearchNV.getText().toString().length() == 0) {
                    item = list.get(position);
                } else {
                    item = listSearch.get(position);
                }
                // truyền dự liệu với mã và loại 1 là update
                mMaNV = item.getMaNV();

                // them log.d de xem maNV kieu int lay duoc ko, ket qua la lay duoc
                Log.d("getmanv: ","manv: "+item.getMaNV());
                Log.d("mã nhân viên: ",mMaNV+"");
                Intent intent = new Intent(NhanVienActivity.this, ThemSuaNhanVienActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("mLoai", 1);
                bundle.putInt("mMaNV", mMaNV);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
            }
        });

        // edittext tìm kiếm
        edSearchNV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //cập nhật lv lên khi text thay đổi
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listSearch = new ArrayList<>();
                for (int i = 0; i <= list.size() - 1; i++) {
                    item = list.get(i);
                    if (item.getHoTen().toUpperCase().contains(s.toString().toUpperCase())) {
                        listSearch.add(item);
                    }
                }
                adapter = new NhanVienAdapter(NhanVienActivity.this, NhanVienActivity.this, listSearch);
                lvNV.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    // set menu và toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quan_ly, menu);
        return true;
    }

    // thực hiện khi click icon trên toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // khi click vào icon thêm góc trên phải màn hình chuyển activity ThemSua
            // truyền dự liệu với mã và loại 0 là thêm mới
            case R.id.action_them:
                Intent intent = new Intent(NhanVienActivity.this, ThemSuaNhanVienActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("mLoai", 0);
                bundle.putInt("mMaNV", 0);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void capNhatLv() {
        list = (ArrayList<NhanVien>) dao.get();
        adapter = new NhanVienAdapter(this, this, list);
        lvNV.setAdapter(adapter);
    }
}