package com.poly.duan1.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.poly.duan1.Activities.DangNhapActivity2;
import com.poly.duan1.Activities.DoiMatKhauActivity;
import com.poly.duan1.Activities.KhachHangActivity;
import com.poly.duan1.Activities.MatHangActivity;
import com.poly.duan1.Activities.NhaSanXuatActivity;
import com.poly.duan1.Activities.NhanVienActivity;
import com.poly.duan1.Adapters.ThemAdapter;
import com.poly.duan1.DAO.NhanVienDAO;
import com.poly.duan1.Models.ModelThem;
import com.poly.duan1.Models.NhanVien;
import com.poly.duan1.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class ThemFragment extends Fragment {
    ListView lvThietLapBH,lvKhac;
    ArrayList<ModelThem> list1 = new ArrayList<>();
    ArrayList<ModelThem> list2 = new ArrayList<>();
    ThemAdapter adapter;
    NhanVienDAO dao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_them, container, false);
        lvKhac = v.findViewById(R.id.lvKhac);
        lvThietLapBH = v.findViewById(R.id.lvThietLapBH);

        dao = new NhanVienDAO(getContext());

        // thêm các mục Thiết lập
        list1.add(new ModelThem(R.drawable.ic_baseline_receipt_24,"Mặt hàng"));
        list1.add(new ModelThem(R.drawable.icon_khachhang,"Khách hàng"));
        list1.add(new ModelThem(R.drawable.ic_baseline_new_releases_24,"Nhà sản xuât"));

        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        String user = pref.getString("tenDN","");
        NhanVien nhanVien = dao.getByTK(user);
        if (nhanVien.getVaiTro()==0){
            list1.add(new ModelThem(R.drawable.icon_nhanvien,"Nhân viên"));
        }
        adapter = new ThemAdapter(getActivity(),this,list1);
        lvThietLapBH.setAdapter(adapter);

        // thêm các mục khác
        list2.add(new ModelThem(R.drawable.icon_doimk,"Đổi mật khẩu"));
        list2.add(new ModelThem(R.drawable.ic_baseline_phonelink_erase_24,"Đăng xuất"));
        adapter = new ThemAdapter(getActivity(),ThemFragment.this,list2);
        lvKhac.setAdapter(adapter);

        lvThietLapBH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    Intent i = new Intent(getActivity(), MatHangActivity.class);
                    startActivity(i);
                    // cần nghiên cứu thêm về phần chuyển giữa fragment sang activity activity
//                    startActivity(new Intent(getActivity(), MatHangActivity.class) );

                }
                if (position == 1){
                    startActivity(new Intent(getActivity(), KhachHangActivity.class));
                }
                if (position == 2){
                    startActivity(new Intent(getActivity(), NhaSanXuatActivity.class));
                }
                if (position == 3){
                    startActivity(new Intent(getActivity(), NhanVienActivity.class));
                }
            }
        });

        lvKhac.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    startActivity(new Intent(getActivity(), DoiMatKhauActivity.class));
                }
                if (position == 1){
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("USER_FILE",MODE_PRIVATE).edit();
                    editor.clear();
                    editor.apply();
                    startActivity(new Intent(getActivity(), DangNhapActivity2.class));
                }
            }
        });

        return v;
    }
}
