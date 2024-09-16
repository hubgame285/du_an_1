package com.poly.duan1.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.poly.duan1.Adapters.KhachHangAdapter;
import com.poly.duan1.DAO.KhachHangDAO;
import com.poly.duan1.Models.KhachHang;
import com.poly.duan1.R;

import java.util.ArrayList;

public class KhachHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView mTitle;
    ActionBar ab;
    EditText edSearchKH;
    ListView lvKH;
    ArrayList<KhachHang> list;
    ArrayList<KhachHang> listSearch;
    KhachHangDAO dao;
    KhachHangAdapter adapter;
    KhachHang item;
    int mMaKH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);
        //anh xa
        edSearchKH = findViewById(R.id.edSearchKH);
        lvKH = findViewById(R.id.lvKH);

        // khi nhấn vào edittext mới hiện con trỏ
        edSearchKH.setOnTouchListener(new View.OnTouchListener() {
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
        mTitle.setText("Khách hàng");

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


        dao = new KhachHangDAO(this);
        capNhatLv();

        // khi click vào item trong listview chuyển activity ThemSua
        lvKH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (edSearchKH.getText().toString().length() == 0) {
                    item = list.get(position);
                } else {
                    item = listSearch.get(position);
                }
                mMaKH = item.maKH;
                // truyền dự liệu với mã và loại 1 là update
                Intent intent = new Intent(KhachHangActivity.this, ThemSuaKhachHangActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("mLoai", 1);
                bundle.putInt("mMaKH", mMaKH);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
            }
        });

        // edittext tìm kiếm
        edSearchKH.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //cập nhật lv lên khi text thay đổi
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listSearch = new ArrayList<>();
                for (int i = 0; i <= list.size() - 1; i++) {
                    item = list.get(i);
                    if (item.tenKH.toUpperCase().contains(s.toString().toUpperCase())) {
                        listSearch.add(item);
                    }
                }
                adapter = new KhachHangAdapter(KhachHangActivity.this, KhachHangActivity.this, listSearch);
                lvKH.setAdapter(adapter);
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
            case R.id.action_them:
                // khi click vào icon thêm góc trên phải màn hình chuyển activity ThemSua
                // truyền dự liệu với mã và loại 0 là thêm mới
                Intent intent = new Intent(KhachHangActivity.this, ThemSuaKhachHangActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("mLoai", 0);
                bundle.putInt("mMaKH", 0);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void capNhatLv() {
        list = (ArrayList<KhachHang>) dao.getAll();
        adapter = new KhachHangAdapter(this, this, list);
        lvKH.setAdapter(adapter);

    }
}