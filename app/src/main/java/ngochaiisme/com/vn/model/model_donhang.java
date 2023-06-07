package ngochaiisme.com.vn.model;

import java.util.Date;

public class model_donhang {

    private int dh_id;
    private String kh_id;
    private double dh_tongtien;
    private String dh_diachi;
    private String dh_trangthai;
    private String dh_thoigiandathang;

    public model_donhang() {
    }

    public model_donhang(int dh_id, String kh_id, double dh_tongtien, String dh_diachi, String dh_trangthai, String dh_thoigiandathang) {
        this.dh_id = dh_id;
        this.kh_id = kh_id;
        this.dh_tongtien = dh_tongtien;
        this.dh_diachi = dh_diachi;
        this.dh_trangthai = dh_trangthai;
        this.dh_thoigiandathang = dh_thoigiandathang;
    }

    public model_donhang(String kh_id, double dh_tongtien, String dh_diachi, String dh_trangthai, String dh_thoigiandathang) {
        this.kh_id = kh_id;
        this.dh_tongtien = dh_tongtien;
        this.dh_diachi = dh_diachi;
        this.dh_trangthai = dh_trangthai;
        this.dh_thoigiandathang = dh_thoigiandathang;
    }

    public int getDh_id() {
        return dh_id;
    }

    public void setDh_id(int dh_id) {
        this.dh_id = dh_id;
    }

    public String getKh_id() {
        return kh_id;
    }

    public void setKh_id(String kh_id) {
        this.kh_id = kh_id;
    }

    public double getDh_tongtien() {
        return dh_tongtien;
    }

    public void setDh_tongtien(double dh_tongtien) {
        this.dh_tongtien = dh_tongtien;
    }

    public String getDh_diachi() {
        return dh_diachi;
    }

    public void setDh_diachi(String dh_diachi) {
        this.dh_diachi = dh_diachi;
    }

    public String getDh_trangthai() {
        return dh_trangthai;
    }

    public void setDh_trangthai(String dh_trangthai) {
        this.dh_trangthai = dh_trangthai;
    }

    public String getDh_thoigiandathang() {
        return dh_thoigiandathang;
    }

    public void setDh_thoigiandathang(String dh_thoigiandathang) {
        this.dh_thoigiandathang = dh_thoigiandathang;
    }
}
