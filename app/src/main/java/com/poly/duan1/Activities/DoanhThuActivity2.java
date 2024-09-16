package com.poly.duan1.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.poly.duan1.DAO.BaoCaoDAO;
import com.poly.duan1.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DoanhThuActivity2 extends AppCompatActivity {
    TextView tv1, tv2, tv3;
    Button button;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    int mYear, mMonth, mDay;
    Calendar c1, c2;
    Long tuNgay,denNgay;

    Toolbar toolbar;
    TextView mTitle;
    ActionBar ab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu2);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        button = findViewById(R.id.button);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("");
        mTitle.setText("Doanh thu");
        //set icon
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24);
        ab.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });


        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1 = Calendar.getInstance();
                mYear = c1.get(Calendar.YEAR);
                mMonth = c1.get(Calendar.MONTH);
                mDay = c1.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d= new DatePickerDialog(DoanhThuActivity2.this,0,mDateTuNgay,mYear, mMonth,mDay);
                d.show();

            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c2 = Calendar.getInstance();
                mYear = c2.get(Calendar.YEAR);
                mMonth = c2.get(Calendar.MONTH);
                mDay = c2.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d= new DatePickerDialog(DoanhThuActivity2.this,0,mDateDenNgay,mYear, mMonth,mDay);
                d.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(denNgay==null || tuNgay==null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DoanhThuActivity2.this);
                    builder.setTitle("Cảnh báo");
                    builder.setMessage("Vui lòng chọn ngày cần tính doanh thu!");
                    builder.setCancelable(true);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else if(denNgay<=tuNgay){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DoanhThuActivity2.this);
                    builder.setTitle("Cảnh báo");
                    builder.setMessage("Vui lòng chọn ngày đến sau ngày bắt đầu ít nhất 1 ngày!");
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
                    BaoCaoDAO thongKeDAO = new BaoCaoDAO(DoanhThuActivity2.this);
                    tv3.setText(currencyFormat(String.valueOf(thongKeDAO.getDoanhThu2(String.valueOf(tuNgay), String.valueOf(denNgay)))) + " VNĐ");
                }
            }
        });

    }

    DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear,mMonth,mDay);
            tuNgay=c.getTimeInMillis();
            tv1.setText(sdf.format(c.getTime()));
        }
    };

    DatePickerDialog.OnDateSetListener mDateDenNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear,mMonth,mDay);
            denNgay=c.getTimeInMillis();
            tv2.setText(sdf.format(c.getTime()));
        }
    };

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0");
        return formatter.format(Double.parseDouble(amount));
    }
}