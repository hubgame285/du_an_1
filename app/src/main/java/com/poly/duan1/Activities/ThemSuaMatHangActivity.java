package com.poly.duan1.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.poly.duan1.DAO.DonHangChiTietDAO;
import com.poly.duan1.DAO.DonHangDAO;
import com.poly.duan1.DAO.NhaSanXuatDAO;
import com.poly.duan1.DAO.SanPhamDAO;
import com.poly.duan1.Models.DonHang;
import com.poly.duan1.Models.DonHangChiTiet;
import com.poly.duan1.Models.NhaSanXuat;
import com.poly.duan1.Models.SanPham;
import com.poly.duan1.R;
import com.poly.duan1.Utility.Constants;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ThemSuaMatHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView mTitle, edXoa;
    ActionBar ab;


    EditText edTenSP, edMaSP, edSoLuong, edGiaTien, edMota;
    TextView tvNSX;
    ConstraintLayout layoutNSX;
    ImageView imgAnhSP;
    ImageButton btn_folder, btn_camera;
    byte[] byte_img;

    ArrayList<SanPham> list;
    SanPhamDAO dao;
    SanPham items;
    DonHangChiTietDAO chiTietDAO;
    DonHangChiTiet chitiet;
    DonHang donHang;
    DonHangDAO donHangDAO;


    ArrayList<NhaSanXuat> listNSX;
    ArrayList<DonHangChiTiet> listChiTiet;
    ArrayList<String> mlistNSX;
    NhaSanXuatDAO NSXdao;
    NhaSanXuat nsx;




    int mLoai, mNSX, mWhich=0;
    String mMaMH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sua_mat_hang);
        tvNSX = findViewById(R.id.tvNSX);
        layoutNSX = findViewById(R.id.layoutNSX);
        edTenSP = findViewById(R.id.edTenSP);
        edMaSP = findViewById(R.id.edMaSP);
        edXoa = findViewById(R.id.edXoa);
        edSoLuong = findViewById(R.id.edSoLuong);
        edGiaTien = findViewById(R.id.edGiaTien);
        edMota = findViewById(R.id.edMota);
        imgAnhSP = findViewById(R.id.img_SP);
        btn_folder = findViewById(R.id.btn_folder_sp);
        btn_camera = findViewById(R.id.btn_camera_sp);

        NSXdao = new NhaSanXuatDAO(this);
        listNSX = (ArrayList<NhaSanXuat>) NSXdao.get();
        mlistNSX = new ArrayList<>();
        for (int i=0; i<=listNSX.size()-1; i++){
            nsx = listNSX.get(i);
            mlistNSX.add(nsx.getTenNSX());
        }



        // Mở camera để người dùng chụp ảnh
        ActivityResultLauncher<Intent> openCamera = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                            imgAnhSP.setImageBitmap(bitmap);
                        }
                    }
                });

        // Mở folder cho người dùng chọn ảnh trong máy
        ActivityResultLauncher<Intent> openFolder = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            try {
                                InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                byte_img = Constants.getBytes(bitmap);
                                imgAnhSP.setImageBitmap(bitmap);

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                openCamera.launch(takePictureIntent);
            }
        });

        btn_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getPictureIntent = new Intent(Intent.ACTION_PICK);
                getPictureIntent.setType("image/*");
                openFolder.launch(getPictureIntent);
            }
        });

        // khi nhấn vào hiện dialog chọn nhà sản xuất
        layoutNSX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ThemSuaMatHangActivity.this);
                builder.setTitle("Chọn nhà sản xuất");
                builder.setSingleChoiceItems(mlistNSX.toArray(new String[mlistNSX.size()]), mNSX, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mWhich = which;
                    }
                });

                builder.setCancelable(true);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mNSX = mWhich;
                        nsx = listNSX.get(mNSX);
                        tvNSX.setText(nsx.getTenNSX());
                        dialog.dismiss();
                    }
                });
                androidx.appcompat.app.AlertDialog alertDialog =builder.create();
                alertDialog.show();
            }
        });
        // khi nhấn vào edittext mới hiện con trỏ
        edTenSP.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        edMaSP.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        edSoLuong.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        edGiaTien.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        edMota.setOnTouchListener(new View.OnTouchListener() {
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
        mTitle.setText("Mặt hàng");
        //set icon
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24);
        ab.setDisplayHomeAsUpEnabled(true);
        //nhấn vào icon back bên trái trên màn hình thực hiện onBackPressed();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThemSuaMatHangActivity.this,MatHangActivity.class));
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
            }
        });
        // xóa dữ liệu
        edXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkGioHang(mMaMH)==true){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ThemSuaMatHangActivity.this);
                    builder.setTitle("Cảnh báo");
                    builder.setMessage("Sản phẩm này đang được chọn trong giỏ hàng!");
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
                    xoa(mMaMH);
                }
            }
        });

        dao = new SanPhamDAO(this);
        list = (ArrayList<SanPham>) dao.get();

        //lấy dữ liệu từ bundle 0: thêm    1:sủa
        Bundle bundle = getIntent().getExtras();
        mLoai = bundle.getInt("mLoai");
        mMaMH = bundle.getString("mMaMH");
        //Thêm log.d để xem có nhận được masp từ MatHangActivity truyền qua ko, kq là ko nhận được
        Log.d("mã mặt hàng ThemSuaActivity: ",mMaMH+"");
        //sua
        if (mLoai==1){
            mTitle.setText("Sửa mặt hàng");
            // set dữ liệu vào edittext, hình và hiện nút xóa
            for (int i =0; i<=list.size()-1;i++){
                items = list.get(i);
                if (items.getMaSP().equals(mMaMH)){
                    edTenSP.setText(""+items.getTenSP());
                    edMaSP.setText(""+items.getMaSP());
                    edMaSP.setEnabled(false);
                    edSoLuong.setText(""+items.getSoLuong());
                    edGiaTien.setText(""+items.getGiaTien());
                    edMota.setText(""+items.getMoTa());
                    for (int j=0; j<=listNSX.size()-1; j++){
                        nsx = listNSX.get(j);
                        if (items.getMaNSX() == nsx.getMaNSX()){
                            tvNSX.setText(""+nsx.getTenNSX());
                        }
                    }
                    try {
                        imgAnhSP.setImageBitmap(Constants.getImage(items.getAnhSP()));
                    }catch (Exception e){

                    }
                }
            }
            edXoa.setVisibility(View.VISIBLE);
        }else {
            //ẩn nút xóa
            mTitle.setText("Thêm mặt hàng");
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
                    items = new SanPham();
                    items.setTenSP(edTenSP.getText().toString().trim());
                    items.setMaSP(edMaSP.getText().toString().trim().toUpperCase());
                    items.setMoTa(edMota.getText().toString());
                    items.setSoLuong(Integer.parseInt(edSoLuong.getText().toString()));
                    items.setGiaTien(Integer.parseInt(edGiaTien.getText().toString()));

                    nsx = listNSX.get(mNSX);
                    items.setMaNSX(nsx.getMaNSX());

                    byte[] image;
                    Bitmap bm=((BitmapDrawable)imgAnhSP.getDrawable()).getBitmap();
                    image = Constants.getBytes(bm);
                    items.setAnhSP(image);


                    if (mLoai==0){
                        dao.insert(items);
                        startActivity(new Intent(ThemSuaMatHangActivity.this,MatHangActivity.class));
                        overridePendingTransition(R.anim.in,R.anim.out);
                        finish();
                    }
                    if (mLoai==1){
                        if(checkGioHang(mMaMH)==true){
                            AlertDialog.Builder builder = new AlertDialog.Builder(ThemSuaMatHangActivity.this);
                            builder.setTitle("Cảnh báo");
                            builder.setMessage("Sản phẩm này đang được chọn trong giỏ hàng!");
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
                            items.setMaSP(edMaSP.getText().toString());
                            dao.update(items);
                            startActivity(new Intent(ThemSuaMatHangActivity.this,MatHangActivity.class));
                            overridePendingTransition(R.anim.in,R.anim.out);
                            finish();
                        }
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }

    // hiện dialog xác nhận xóa
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
                startActivity(new Intent(ThemSuaMatHangActivity.this,MatHangActivity.class));
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
    int validate() {
        int check = 0;
        String tenSP = edTenSP.getText().toString();
        String maSP = edMaSP.getText().toString();
        Integer soLuong = null;
        try {
            soLuong = Integer.parseInt(edSoLuong.getText().toString());
        } catch (Exception ex) {
        }
        Integer giaTien = null;
        try {
            giaTien = Integer.parseInt(edGiaTien.getText().toString());
        } catch (Exception ex) {
        }
        String nsx = tvNSX.getText().toString();
        //lay anh tu imageView
        byte[] image = null;
        try {
            Bitmap bm = ((BitmapDrawable) imgAnhSP.getDrawable()).getBitmap();
            image = Constants.getBytes(bm);
        } catch (Exception ex) {
        }
        ;
        if (tenSP.length() == 0 || maSP.length() == 0 || soLuong == null || giaTien == null || nsx.length() == 0 || checkKhoangTrang(tenSP)==true || checkKhoangTrang(maSP)==true) {
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
            check = 1;
        } else if(image==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cảnh báo");
            builder.setMessage("Vui lòng chọn ảnh!");
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
        } else if(checkTenSP(tenSP.trim())){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cảnh báo");
            builder.setMessage("Tên sản phẩm đã tồn tại!");
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
        } else if(checkMaSP(maSP.trim().toUpperCase())==true){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cảnh báo");
            builder.setMessage("Mã sản phẩm đã tồn tại!");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 999 && resultCode == RESULT_OK && data !=null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(uri);
                Bitmap imgBitmap = BitmapFactory.decodeStream(inputStream);
                imgAnhSP.setImageBitmap(imgBitmap);
            }catch (Exception e){
                Log.e("Loi","");
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //kiem tra trung ma san pham
    public boolean checkMaSP(String maSP){
        List<SanPham> data = dao.get() ;
        if (maSP.length() > 0 && mLoai == 0) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getMaSP().equals(maSP.trim().toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    //kiem tra trung ten san pham
    public boolean checkTenSP(String tenSP){
        List<SanPham> data = dao.get() ;
        if (tenSP.length() > 0 && mLoai == 0) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getTenSP().equals(tenSP.trim())) {
                    return true;
                }
            }
        }
        return false;
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

    //kiem tra gio hang
    public boolean checkGioHang(String maSP){
        donHang = new DonHang();
        donHangDAO = new DonHangDAO(getApplicationContext());
        donHang = donHangDAO.getChuaTT();
        chiTietDAO = new DonHangChiTietDAO(getApplicationContext());
        listChiTiet = new ArrayList<DonHangChiTiet>();
        listChiTiet = (ArrayList<DonHangChiTiet>) chiTietDAO.getBymaHD(String.valueOf(donHang.getMaDH()));
        for(int i=0; i<listChiTiet.size(); i++){
            chitiet = listChiTiet.get(i);
            if(mMaMH.equals(chitiet.getMaSP())){
                return true;
            }
        }
        return false;
    }
}