package com.poly.duan1.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.poly.duan1.Adapters.DonHangChiTietApdapter;
import com.poly.duan1.DAO.DonHangChiTietDAO;
import com.poly.duan1.DAO.DonHangDAO;
import com.poly.duan1.DAO.NhanVienDAO;
import com.poly.duan1.Models.DonHang;
import com.poly.duan1.Models.DonHangChiTiet;
import com.poly.duan1.Models.NhanVien;
import com.poly.duan1.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangActivity extends AppCompatActivity {

    ConstraintLayout layoutKH;
    Button btnThanhToan;
    TextView tvMaDH,tvKH, tvTongTien;
    ListView lvHDCT;
    Toolbar toolbar;
    TextView mTitle;
    ActionBar ab;

    ArrayList<DonHangChiTiet> list;
    DonHangChiTiet items;
    DonHangChiTietDAO dao;
    DonHangChiTietApdapter adapter;

    DonHang donHang;
    DonHangDAO donHangDAO;
    int mMaCT;

    NhanVienDAO daoNV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        //anh xạ
        layoutKH = findViewById(R.id.layoutKH);
        btnThanhToan = findViewById(R.id.edThanhToan);
        tvMaDH = findViewById(R.id.tvMaDH);
        tvKH = findViewById(R.id.tvKH);
        tvTongTien = findViewById(R.id.tvTongTien);
        lvHDCT = findViewById(R.id.lvHDCT);
        capNhatLv();
        // sét maDH vào tvMaDH
        tvMaDH.setText(donHang.getMaDH()+"");
        //tạo biến tổng tiền = tổng số tiền của các đơn hàng chi tiết có cùng maDH
        int tongTien = dao.getTongTien(String.valueOf(donHang.getMaDH()));
        tvTongTien.setText(currencyFormat(String.valueOf(tongTien))+" VND");
        // set toolbar thay thế actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("");
        mTitle.setText("Giỏ hàng");

        //set icon
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24);
        ab.setDisplayHomeAsUpEnabled(true);

        //nhấn vào icon back bên trái trên màn hình thực hiện onBackPressed();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GioHangActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
            }
        });
        // gửi dữ liệu và chuyên activity
        lvHDCT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                items = list.get(position);
                mMaCT = items.getMaCT();
                Intent intent = new Intent(GioHangActivity.this, DonHangChiTietActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("mMaCT", mMaCT);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
            }
        });

        // cập nhật trạng thái 1:(đã thanh toán) và tạo ra 1 hóa đơn mới với trạng thái 0:(chưa thanh toán)
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tongTien==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                    builder.setTitle("Cảnh báo");
                    builder.setMessage("Vui lòng chọn sản phẩm trước khi thanh toán!");
                    builder.setCancelable(true);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    // lay ma nv
                    SharedPreferences pref = GioHangActivity.this.getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                    String user = pref.getString("tenDN", "");
                    NhanVien nhanVien = daoNV.getByTK(user);
                    // lấy ngày hiện tại
                    java.util.Date date1 = java.util.Calendar.getInstance().getTime();
                    // cập nhật trạng thái:1 đã thanh toán
                    DonHang donHang1 = new DonHang();
                    donHang1.setMaDH(donHang.getMaDH());
                    donHang1.setMaNV(nhanVien.getMaNV());
                    donHang1.setTrangThai(1);
                    donHang1.setTongTien(tongTien);
                    donHang1.setNgayMua(date1);
                    donHangDAO.update(donHang1);
                    // tạo mới hóa đơn trạng thái:0 chưa thanh toán
                    DonHang donHang2 = new DonHang();
                    donHang2.setMaNV(nhanVien.getMaNV());
                    donHang2.setTongTien(0);
                    donHang2.setTrangThai(0);
                    donHang2.setNgayMua(date1);
                    donHangDAO.insert(donHang2);
                    startActivity(new Intent(GioHangActivity.this, MainActivity.class));
                }
            }
        });
    }

    void capNhatLv() {
        donHangDAO = new DonHangDAO(this);
        donHang = donHangDAO.getChuaTT();
        daoNV = new NhanVienDAO(this);
        dao = new DonHangChiTietDAO(this);
        list = (ArrayList<DonHangChiTiet>) dao.getBymaHD(String.valueOf(donHang.getMaDH()));
        adapter = new DonHangChiTietApdapter(this, this, list);
        lvHDCT.setAdapter(adapter);
    }
    //Dinh dang tien te
    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0");
        return formatter.format(Double.parseDouble(amount));
    }

}