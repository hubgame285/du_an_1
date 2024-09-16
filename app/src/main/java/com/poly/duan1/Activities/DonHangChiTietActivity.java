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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.duan1.DAO.DonHangChiTietDAO;
import com.poly.duan1.DAO.SanPhamDAO;
import com.poly.duan1.Models.DonHangChiTiet;
import com.poly.duan1.Models.SanPham;
import com.poly.duan1.R;

import java.util.ArrayList;

public class DonHangChiTietActivity extends AppCompatActivity {

    ImageView imgTru,imgCong;
    TextView tvSoLuong,tvGia;
    EditText edXoa;
    Toolbar toolbar;
    TextView mTitle;
    ActionBar ab;

    ArrayList<DonHangChiTiet> list;
    DonHangChiTietDAO dao;
    DonHangChiTiet items;

    SanPhamDAO sanPhamDAO;
    SanPham sanPham;
    int  mMaHD, mSoLuongMua, mSLG, mMaCT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_chi_tiet);
        //ánh xạ
        imgTru = findViewById(R.id.imgTru);
        imgCong = findViewById(R.id.imgCong);
        tvSoLuong = findViewById(R.id.tvSoLuong);
        tvGia = findViewById(R.id.tvGia);
        edXoa = findViewById(R.id.edXoa);
        // lấy dữ liệu từ activity trước
        Bundle bundle = getIntent().getExtras();
        mMaCT = bundle.getInt("mMaCT");
        //khai báo các lớp dao và arraylist
        dao = new DonHangChiTietDAO(this);
        sanPhamDAO = new SanPhamDAO(this);
        list = (ArrayList<DonHangChiTiet>) dao.getAll();
        // lấy item theo id
        items = dao.getID(String.valueOf(mMaCT));
        mMaHD = items.getMaDH();
        mSLG = items.getSoLuongMua();
        mSoLuongMua = items.getSoLuongMua();
        sanPham = sanPhamDAO.getById(String.valueOf(items.getMaSP()));
        // sét thông tin vào các tv
        tvSoLuong.setText(mSoLuongMua+"");
        tvGia.setText(mSoLuongMua*sanPham.getGiaTien()+"");

        // set toolbar thay thế actionbar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("");
        mTitle.setText(sanPham.getTenSP());
        //set icon
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24);
        ab.setDisplayHomeAsUpEnabled(true);
        //nhấn vào icon back bên trái trên màn hình thực hiện onBackPressed();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DonHangChiTietActivity.this,GioHangActivity.class));
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
            }
        });
        // nhấn vào dấu trừ giảm 1 số lượng
        imgTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSoLuongMua==0){
                } else {
                    mSoLuongMua--;
                    tvSoLuong.setText(mSoLuongMua + "");
                    tvGia.setText(mSoLuongMua * sanPham.getGiaTien() + "");
                }
            }
        });
        // nhấn vào dấu cộng tăng 1 số lượng
        imgCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((sanPhamDAO.getById(String.valueOf(items.getMaSP())).getSoLuong()+items.getSoLuongMua())==mSoLuongMua){
                } else {
                    mSoLuongMua++;
                    tvSoLuong.setText(mSoLuongMua + "");
                    tvGia.setText(mSoLuongMua * sanPham.getGiaTien() + "");
                }
            }
        });

        // xóa hóa đơn chi tiết theo mã
        edXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoa(String.valueOf(mMaCT));
            }
        });
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
                // lưu thay đổi số lượng và giá tiền
                DonHangChiTiet obj = new DonHangChiTiet();
                obj.setMaDH(mMaHD);
                obj.setMaSP(sanPham.getMaSP());
                obj.setSoLuongMua(mSoLuongMua);
                obj.setThanhTien(mSoLuongMua*sanPham.getGiaTien());
                obj.setMaCT(mMaCT);
                dao.update(obj);
                // cập nhật lại số lượng sản phẩm
                SanPham sanPham1 = new SanPham();
                sanPham1.setMaSP(sanPham.getMaSP());
                sanPham1.setTenSP(sanPham.getTenSP());
                sanPham1.setAnhSP(sanPham.getAnhSP());
                sanPham1.setMoTa(sanPham.getMoTa());
                sanPham1.setGiaTien(sanPham.getGiaTien());
                sanPham1.setMaNSX(sanPham.getMaNSX());
                int slmoi = sanPham.getSoLuong() + (mSLG - mSoLuongMua);
                sanPham1.setSoLuong(slmoi);
                sanPhamDAO.update(sanPham1);
                // chuyển activity
                startActivity(new Intent(DonHangChiTietActivity.this,GioHangActivity.class));
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
                }

        return super.onOptionsItemSelected(item);
    }

    // xóa dơn hàng chi tiết theo mã. và hiện dialog thông báo
    public void xoa(final String Id){
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
                SanPham sanPham1 = new SanPham();
                sanPham1.setMaSP(sanPham.getMaSP());
                sanPham1.setTenSP(sanPham.getTenSP());
                sanPham1.setAnhSP(sanPham.getAnhSP());
                sanPham1.setMoTa(sanPham.getMoTa());
                sanPham1.setGiaTien(sanPham.getGiaTien());
                sanPham1.setMaNSX(sanPham.getMaNSX());
                int slmoi = sanPham.getSoLuong() + mSoLuongMua;
                sanPham1.setSoLuong(slmoi);
                sanPhamDAO.update(sanPham1);

                startActivity(new Intent(DonHangChiTietActivity.this,GioHangActivity.class));
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
}