package com.poly.duan1.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.poly.duan1.R;
import com.poly.duan1.Fragments.BanHangFragment;
import com.poly.duan1.Fragments.BaoCaoFragment;
import com.poly.duan1.Fragments.HoaDonFragment;
import com.poly.duan1.Fragments.ThemFragment;

public class MainActivity extends AppCompatActivity {
    private long back_press_time;
    Toast mtoast;
    Toolbar toolbar;
    TextView mTitle;
    ActionBar ab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anh_xa();
        //set toolbar thay the cho actionbar
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("");
        //set icon
//        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_accessibility_new_24);
        ab.setDisplayHomeAsUpEnabled(false);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new BanHangFragment()).commit();
    }

    public BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.nav_btt1:
                    mTitle.setText("Bán hàng");
                    selectedFragment = new BanHangFragment();
                    break;
                case R.id.nav_btt2:
                    mTitle.setText("Hóa đơn");
                    selectedFragment = new HoaDonFragment();
                    break;
                case R.id.nav_btt3:
                    mTitle.setText("Báo cáo");
                    selectedFragment = new BaoCaoFragment();
                    break;
                case R.id.nav_btt4:
                    mTitle.setText("Thông tin");
                    selectedFragment = new ThemFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();
            return true;
        }
    };

    @Override
    public void onBackPressed() {
// viết onBackPress ko cho thoát ứng dụng nếu chỉ bấm 1 lần
        if (back_press_time + 2000 > System.currentTimeMillis()){
            mtoast.cancel();
            super.onBackPressed();
            return;
        } else {
            mtoast = Toast.makeText(this,"Nhấn thêm lần nữa nếu muốn thoát",Toast.LENGTH_SHORT);
            mtoast.show();
        }
        back_press_time = System.currentTimeMillis();
    }

    private void anh_xa(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
    }

}