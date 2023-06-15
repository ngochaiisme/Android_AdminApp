package ngochaiisme.com.vn.model;

import java.text.DecimalFormat;

public class model_doanhthutheoloaisp {
    int loaisp;
    double doanhthu;

    public model_doanhthutheoloaisp() {
    }

    public model_doanhthutheoloaisp(int loaisp, double doanhthu) {
        this.loaisp = loaisp;
        this.doanhthu = doanhthu;
    }

    public int getLoaisp() {
        return loaisp;
    }

    public void setLoaisp(int loaisp) {
        this.loaisp = loaisp;
    }

    public double getDoanhthu() {
        return doanhthu;
    }

    public void setDoanhthu(double doanhthu) {
        this.doanhthu = doanhthu;
    }

    @Override
    public String toString() {
        return loaisp +"\t" +chuyenDoiGiaTri(doanhthu);
    }

    private String chuyenDoiGiaTri(double giaTri) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###đ");
        return decimalFormat.format(giaTri);
    }
}
