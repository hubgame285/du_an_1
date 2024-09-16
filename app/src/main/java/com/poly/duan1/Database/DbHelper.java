package com.poly.duan1.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.poly.duan1.Utility.Constants.*;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // tao bang NhaSanXuat
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NHASANXUAT
                + " ( " + COLUMN_NHASANXUAT_MA_NHA_SAN_XUAT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NHASANXUAT_TEN_NHA_SAN_XUAT + " TEXT ) ";
        db.execSQL(query);

        // tao bang SanPham
        query = "CREATE TABLE IF NOT EXISTS " + TABLE_SANPHAM
                + " ( " + COLUMN_SANPHAM_MA_SAN_PHAM + " TEXT PRIMARY KEY, "
                + COLUMN_SANPHAM_TEN_SAN_PHAM + " TEXT, "
                + COLUMN_SANPHAM_MO_TA + " TEXT, "
                + COLUMN_SANPHAM_SO_LUONG + " INTEGER, "
                + COLUMN_SANPHAM_GIA_TIEN + " REAL, "
                + "anhSP BLOB, "
                + COLUMN_SANPHAM_MA_NHA_SAN_XUAT + " INTEGER, FOREIGN KEY ( " + COLUMN_SANPHAM_MA_NHA_SAN_XUAT + " ) REFERENCES " + TABLE_NHASANXUAT + " ( " + COLUMN_NHASANXUAT_MA_NHA_SAN_XUAT + " ) ) ";
        db.execSQL(query);

        // tao bang NhanVien
        query = "CREATE TABLE IF NOT EXISTS " + TABLE_NHANVIEN
                + " ( " + COLUMN_NHANVIEN_MA_NHAN_VIEN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NHANVIEN_HO_TEN + " TEXT, "
                + COLUMN_NHANVIEN_SO_DIEN_THOAI + " TEXT, "
                + COLUMN_NHANVIEN_TEN_DANG_NHAP + " TEXT, "
                + COLUMN_NHANVIEN_MAT_KHAU + " TEXT, "
                + COLUMN_NHANVIEN_VAI_TRO + " INTEGER )";
        db.execSQL(query);

        // tao bang DonHang
        query = "CREATE TABLE IF NOT EXISTS " + TABLE_DONHANG
                + " ( " + COLUMN_DONHANG_MA_DON_HANG + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DONHANG_TONG_TIEN+ " REAL, "
                + COLUMN_DONHANG_TRANG_THAI + " INTEGER, "
                + COLUMN_DONHANG_NGAY_MUA + " LONG, "
                + COLUMN_DONHANG_MA_NHAN_VIEN + " INTEGER, FOREIGN KEY ( " + COLUMN_DONHANG_MA_NHAN_VIEN + " ) REFERENCES " + TABLE_NHANVIEN + " ( " + COLUMN_NHANVIEN_MA_NHAN_VIEN + " ) ) ";
        db.execSQL(query);

        // tao bang DonHangChiTiet
        query = "create table if not exists DonHangChiTiet " +
                "( maCT integer primary key autoincrement, " +
                "maDH integer references "+TABLE_DONHANG+"("+COLUMN_DONHANG_MA_DON_HANG+"), " +
                "maSP text references "+TABLE_SANPHAM+"("+COLUMN_SANPHAM_MA_SAN_PHAM+"), " +
                "soLuongMua integer, " +
                "thanhTien integer) ";
        db.execSQL(query);

        // tao bang KhachHang
        query = "create table if not exists KhachHang ( maKH integer primary key autoincrement, tenKH text, SDT text)";
        db.execSQL(query);

        // them 20 du lieu mau
        //them du lieu mau bang nha san xuat
        String dulieumau =
                "insert into NHA_SAN_XUAT (MA_NHA_SAN_XUAT, TEN_NHA_SAN_XUAT) values (1, 'Ford'), (2, 'Mazda'), (3, 'Buick'), (4, 'GMC'), (5, 'Ford'), (6, 'Land Rover'),(7, 'Dodge'),(8, 'Nokia'),(9, 'Ford'),(10, 'Lexus'), (11, 'Apple'),(12, 'Samsung')";
        db.execSQL(dulieumau);
        //them du lieu mau bang nhan vien
        dulieumau=
                "insert into NHAN_VIEN (MA_NHAN_VIEN, HO_TEN, SO_DIEN_THOAI, TEN_DANG_NHAP, MAT_KHAU, VAI_TRO) values " +
                        "(1, 'Admin', '0987654321', 'admin', '123', 0)," +
                        "(2, 'Dom Cherrett', '1975817130', 'nv002', '12345', 1)," +
                        "(3, 'Cassy Dodimead', '8028379808', 'nv003', '12345', 0)," +
                        "(4, 'Cecily Daines', '8862431126', 'nv004', '12345', 1)," +
                        "(5, 'Aleece Watmore', '8239967142', 'nv005', '12345', 1)," +
                        "(6, 'Denys Ortler', '4047256628', 'nv006', '12345', 1)," +
                        "(7, 'Thelma McGlaud', '5972811850', 'nv007', '12345', 1)," +
                        "(8, 'Aimee Wilshere', '9695017515', 'nv008', '12345', 0)," +
                        "(9, 'Vassili Morrant', '2242070637', 'nv009', '12345', 0)," +
                        "(10, 'Arlie Ahren', '4164527033', 'nv010', '12345', 1)," +
                        "(11, 'Sergei Kirckman', '3346667276', 'nv0011', '12345', 0)," +
                        "(12, 'Chase MacCallister', '8875473691', 'nv012', '12345', 0)," +
                        "(13, 'Ashia Cobain', '2184500352', 'nv013', '12345', 1)," +
                        "(14, 'Obadiah McConnachie', '1458032212', 'nv014', '12345', 0)," +
                        "(15, 'Giustino Hammerich', '7016762686', 'nv015', '12345', 0)," +
                        "(16, 'Sammy Eite', '6829800109', 'nv016', '12345', 1)," +
                        "(17, 'Odella Kraft', '3386653169', 'nv017', '12345', 0)," +
                        "(18, 'Tiff Silliman', '9038436319', 'nv018', '12345', 1)," +
                        "(19, 'Ikey Shillito', '4352518314', 'nv019', '12345', 0)," +
                        "(20, 'Gaby Lindemann', '4415791918', 'nv020', '12345', 1)";
        db.execSQL(dulieumau);
        String q1 ="insert into "+TABLE_DONHANG+" ("+COLUMN_DONHANG_MA_NHAN_VIEN+","+COLUMN_DONHANG_TRANG_THAI+") values ('1','0')";
        db.execSQL(q1);

        // them du lieu mau bang khach hang

        dulieumau = "insert into KhachHang (maKH, tenKH, SDT) values " +
                "(1, 'Lynsey Stanistrete', '2339134342')," +
                "(2, 'Cherise Westmancoat', '5822623741')," +
                "(3, 'Feliza Massie', '1988103885')," +
                "(4, 'Roslyn Duggon', '2397630076')," +
                "(5, 'Bev Gainor', '2995306641')," +
                "(6, 'Korrie Chidzoy', '3285969012')," +
                "(7, 'Ad Klemz', '3612104576')," +
                "(8, 'Betta Ghiriardelli', '3836692679')," +
                "(9, 'Edlin Ruzicka', '8063821497')," +
                "(10, 'Merissa Wegman', '3167458012')," +
                "(11, 'Wain Luetkemeyer', '7186243913')," +
                "(12, 'Torie Rousel', '6168085144')," +
                "(13, 'Merrili McKnockiter', '1901545403')," +
                "(14, 'Vinnie Bree', '4177748446')," +
                "(15, 'Gib Yerby', '1957217225')," +
                "(16, 'Jennee Papierz', '9397993770')," +
                "(17, 'Barbara-anne Grafom', '7051465287')," +
                "(18, 'Brose Rothera', '1911862415')," +
                "(19, 'Boonie Blanche', '6097241117')," +
                "(20, 'Annalise Billsberry', '9529031391')";
        db.execSQL(dulieumau);

        // them du lieu mau bang san pham

        dulieumau = "insert into SAN_PHAM (MA_SAN_PHAM, TEN_SAN_PHAM, GIA_TIEN, MO_TA, SO_LUONG, SAN_PHAM_MA_NHA_SAN_XUAT) values " +
                "('SP1', 'Bread - Raisin Walnut Pull', 47600, 'vehicula condimentum curabitur in libero ut massa volutpat convallis morbi odio odio elementum', 176, 8)," +
                "('SP2', 'Soup - Campbells Chili Veg', 13600, 'aliquam erat volutpat in congue etiam justo etiam pretium iaculis justo in hac habitasse platea dictumst etiam faucibus cursus', 155, 3)," +
                "('SP3', 'Beef - Striploin', 22200, 'ligula sit amet eleifend pede libero quis orci nullam molestie nibh in lectus pellentesque at nulla suspendisse potenti cras in', 58, 4)," +
                "('SP4', 'Country Roll', 4100, 'eu felis fusce posuere felis sed lacus morbi sem mauris laoreet ut rhoncus aliquet', 74, 11)," +
                "('SP5', 'Bag Stand', 43700, 'mauris non ligula pellentesque ultrices phasellus id sapien in sapien iaculis congue vivamus metus arcu', 171, 8)," +
                "('SP6', 'Kohlrabi', 47500, 'donec semper sapien a libero nam dui proin leo odio porttitor id consequat in', 70, 11)," +
                "('SP7', 'Bar - Sweet And Salty Chocolate', 70300, 'et tempus semper est quam pharetra magna ac consequat metus', 69, 12)," +
                "('SP8', 'Bay Leaf Ground', 31300, 'habitasse platea dictumst etiam faucibus cursus urna ut tellus nulla ut erat id mauris vulputate elementum nullam varius nulla', 85, 2)," +
                "('SP9', 'Cleaner - Comet', 69000, 'vivamus metus arcu adipiscing molestie hendrerit at vulputate vitae nisl aenean lectus pellentesque', 81, 8)," +
                "('SP10', 'Dill - Primerba, Paste', 36100, 'sed justo pellentesque viverra pede ac diam cras pellentesque volutpat dui maecenas tristique est', 163, 9)," +
                "('SP11', 'Curry Paste - Madras', 85000, 'ipsum dolor sit amet consectetuer adipiscing elit proin interdum mauris non ligula pellentesque ultrices phasellus', 134, 10)," +
                "('SP12', 'Trueblue - Blueberry', 55000, 'sed accumsan felis ut at dolor quis odio consequat varius integer ac leo pellentesque ultrices mattis', 145, 11)," +
                "('SP13', 'Lemonade - Island Tea, 591 Ml', 44800, 'turpis adipiscing lorem vitae mattis nibh ligula nec sem duis aliquam convallis nunc proin at turpis a pede', 57, 12)," +
                "('SP14', 'Apple - Delicious, Red', 33300, 'nec euismod scelerisque quam turpis adipiscing lorem vitae mattis nibh ligula nec sem duis aliquam convallis nunc', 184, 10)," +
                "('SP15', 'Salmon - Canned', 86000, 'ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae nulla dapibus dolor vel est donec', 170, 1)," +
                "('SP16', 'Container - Clear 32 Oz', 69800, 'quisque id justo sit amet sapien dignissim vestibulum vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere', 130, 2)," +
                "('SP17', 'Thermometer Digital', 21500, 'bibendum felis sed interdum venenatis turpis enim blandit mi in porttitor pede justo eu massa donec dapibus duis at velit', 187, 3)," +
                "('SP18', 'Food Colouring - Orange', 87200, 'nulla eget eros elementum pellentesque quisque porta volutpat erat quisque erat eros viverra eget congue eget semper', 184, 3)," +
                "('SP19', 'Beef - Rib Roast, Cap On', 27900, 'venenatis turpis enim blandit mi in porttitor pede justo eu massa donec dapibus', 90, 5)," +
                "('SP20', 'Pasta - Angel Hair', 97800, 'faucibus cursus urna ut tellus nulla ut erat id mauris vulputate elementum nullam varius nulla facilisi cras', 82, 5)";
        db.execSQL(dulieumau);




    }




    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int upgrade = oldVersion + 1;
        while (upgrade <= newVersion) {
            switch (upgrade) {
                case 2:

                case 3:

                    break;
                default:

                    break;
            }
            upgrade++;
        }
    }
}
