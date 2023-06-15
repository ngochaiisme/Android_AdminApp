package ngochaiisme.com.vn.model;

public class model_sanphamtheosoluongbanduoc {
    String sp_tensp;
    int sp_soluong;

    public model_sanphamtheosoluongbanduoc() {
    }

    public model_sanphamtheosoluongbanduoc(String sp_tensp, int sp_soluong) {
        this.sp_tensp = sp_tensp;
        this.sp_soluong = sp_soluong;
    }

    public String getSp_tensp() {
        return sp_tensp;
    }

    public void setSp_tensp(String sp_tensp) {
        this.sp_tensp = sp_tensp;
    }

    public int getSp_soluong() {
        return sp_soluong;
    }

    public void setSp_soluong(int sp_soluong) {
        this.sp_soluong = sp_soluong;
    }

    @Override
    public String toString() {
        return sp_tensp+ "\t" +sp_soluong;
    }
}
