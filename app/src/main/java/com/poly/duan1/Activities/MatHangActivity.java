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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.poly.duan1.Adapters.SanPhamAdapter;
import com.poly.duan1.DAO.SanPhamDAO;
import com.poly.duan1.Models.SanPham;
import com.poly.duan1.R;

import java.util.ArrayList;

public class MatHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView mTitle;
    ActionBar ab;
    EditText edSearchMH;
    GridView lvMH;

    ArrayList<SanPham> list;
    ArrayList<SanPham> listSearch;
    SanPhamDAO dao;
    SanPhamAdapter adapter;
    SanPham item;
    String mMaMH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_hang);
        //anh xa
        edSearchMH = findViewById(R.id.edSearchMH);
        lvMH = findViewById(R.id.lvMH);
        // khi nhấn vào edittext mới hiện con trỏ
        edSearchMH.setOnTouchListener(new View.OnTouchListener() {
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
        mTitle.setText("Mặt hàng");
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

        dao = new SanPhamDAO(this);
        capNhatLv();

        // khi click vào item trong listview chuyển activity ThemSua
        lvMH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (edSearchMH.getText().toString().length() == 0) {
                    item = list.get(position);
                } else {
                    item = listSearch.get(position);
                }
                // truyền dự liệu với mã và loại 1 là update
                mMaMH = item.getMaSP();
                // them log.d de xem maSP kieu Text lay duoc ko, ket qua la null (khong lay duoc)
                Log.d("getmasp: ","masp: "+item.getMaSP());
                Log.d("mã mặt hàng MatHangActivity: ",mMaMH+"");
                Intent intent = new Intent(MatHangActivity.this, ThemSuaMatHangActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("mLoai", 1);
                bundle.putString("mMaMH", mMaMH);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
            }
        });

        // edittext tìm kiếm
        edSearchMH.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // cập nhật lv lên khi text thay đổi
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listSearch = new ArrayList<>();
                for (int i = 0; i <= list.size() - 1; i++) {
                    item = list.get(i);
                    if (item.getTenSP().toUpperCase().contains(s.toString().toUpperCase())) {
                        listSearch.add(item);
                    }
                }
                adapter = new SanPhamAdapter(MatHangActivity.this, MatHangActivity.this, listSearch);
                lvMH.setAdapter(adapter);
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
                Intent intent = new Intent(MatHangActivity.this, ThemSuaMatHangActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("mLoai", 0);
                bundle.putString("mMaMH", "");
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void capNhatLv() {
        list = (ArrayList<SanPham>) dao.get();
        adapter = new SanPhamAdapter(this, this, list);
        lvMH.setAdapter(adapter);
    }
}