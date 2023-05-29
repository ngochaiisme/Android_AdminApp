package ngochaiisme.com.vn.model;

import java.util.Date;

public class model_donhang {

    private int dh_id;
    private String dh_tenkhachhang;
    private double tongtien;
    private String dh_ngaydathang;
    private String dh_trangthai;

    public model_donhang(int dh_id, String dh_tenkhachhang, double tongtien, String dh_ngaydathang, String dh_trangthai) {
        this.dh_id = dh_id;
        this.dh_tenkhachhang = dh_tenkhachhang;
        this.tongtien = tongtien;
        this.dh_ngaydathang = dh_ngaydathang;
        this.dh_trangthai = dh_trangthai;
    }

    public int getDh_id() {
        return dh_id;
    }

    public void setDh_id(int dh_id) {
        this.dh_id = dh_id;
    }

    public String getDh_tenkhachhang() {
        return dh_tenkhachhang;
    }

    public void setDh_tenkhachhang(String dh_tenkhachhang) {
        this.dh_tenkhachhang = dh_tenkhachhang;
    }

    public double getTongtien() {
        return tongtien;
    }

    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }

    public String getDh_ngaydathang() {
        return dh_ngaydathang;
    }

    public void setDh_ngaydathang(String dh_ngaydathang) {
        this.dh_ngaydathang = dh_ngaydathang;
    }

    public String getDh_trangthai() {
        return dh_trangthai;
    }

    public void setDh_trangthai(String dh_trangthai) {
        this.dh_trangthai = dh_trangthai;
    }
}
