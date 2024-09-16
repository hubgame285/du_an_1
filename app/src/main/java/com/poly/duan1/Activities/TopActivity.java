package com.poly.duan1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.poly.duan1.Adapters.TopAdapter;
import com.poly.duan1.DAO.BaoCaoDAO;
import com.poly.duan1.Models.Top;
import com.poly.duan1.R;

import java.util.ArrayList;

public class TopActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView mTitle;
    ActionBar ab;
    EditText edSearchSP;
    ListView lvSP;

    ArrayList<Top> list;
    ArrayList<Top> listSearch;
    BaoCaoDAO dao;
    TopAdapter adapter;
    Top item;
    String mMaSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        //anh xa
        edSearchSP = findViewById(R.id.edSearchSP);
        lvSP = findViewById(R.id.lvSP);
        //long click vao item thi hien thi thong tin san pham
        lvSP.setLongClickable(true);
        lvSP.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                mMaSP = item.maSP;
                //check maSP
                Log.d("masptoplongclick: ",mMaSP );
                Intent intent = new Intent(TopActivity.this, DetailsActivity.class);
                intent.putExtra("maSP",mMaSP);
                startActivity(intent);
                return false;
            }
        });
        // khi nhấn vào edittext mới hiện con trỏ
        edSearchSP.setOnTouchListener(new View.OnTouchListener() {
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
        mTitle.setText("Top sản phẩm bán chạy");
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
        dao = new BaoCaoDAO(TopActivity.this);
        capNhatLv();


        lvSP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (edSearchSP.getText().toString().length() == 0) {
                    item = list.get(position);
                } else {
                    item = listSearch.get(position);
                }
            }
        });

//         edittext tìm kiếm
        edSearchSP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // cập nhật lv lên khi text thay đổi
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listSearch = new ArrayList<>();
                for (int i = 0; i <= list.size() - 1; i++) {
                    item = list.get(i);
                    if (item.tenSP.toUpperCase().contains(s.toString().toUpperCase())) {
                        listSearch.add(item);
                    }
                }
                adapter = new TopAdapter(TopActivity.this, TopActivity.this, listSearch);
                lvSP.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void capNhatLv() {
        list = (ArrayList<Top>) dao.get();
        adapter = new TopAdapter(this, this, list);
        lvSP.setAdapter(adapter);
    }
}