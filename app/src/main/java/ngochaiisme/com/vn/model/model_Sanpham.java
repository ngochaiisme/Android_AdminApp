package ngochaiisme.com.vn.model;

public class model_Sanpham {
    private int id;
    private String tensp;
    private float giatien;
    private String cauhinh;
    private int soluong;
    private String linkhinhanh;
    private int loaisp; //1 dien thoai or 0 laptop

    public model_Sanpham() {
    }

    public model_Sanpham(int id, String tensp, float giatien, String cauhinh, int soluong, String linkhinhanh, int loaisp) {
        this.id = id;
        this.tensp = tensp;
        this.giatien = giatien;
        this.cauhinh = cauhinh;
        this.soluong = soluong;
        this.linkhinhanh = linkhinhanh;
        this.loaisp = loaisp;
    }

    public model_Sanpham(String tensp, float giatien, String cauhinh, int soluong, String linkhinhanh, int loaisp) {
        this.tensp = tensp;
        this.giatien = giatien;
        this.cauhinh = cauhinh;
        this.soluong = soluong;
        this.linkhinhanh = linkhinhanh;
        this.loaisp = loaisp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public float getGiatien() {
        return giatien;
    }

    public void setGiatien(float giatien) {
        this.giatien = giatien;
    }

    public String getCauhinh() {
        return cauhinh;
    }

    public void setCauhinh(String cauhinh) {
        this.cauhinh = cauhinh;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getLinkhinhanh() {
        return linkhinhanh;
    }

    public void setLinkhinhanh(String linkhinhanh) {
        this.linkhinhanh = linkhinhanh;
    }

    public int getLoaisp() {
        return loaisp;
    }

    public void setLoaisp(int loaisp) {
        this.loaisp = loaisp;
    }


    @Override
    public String toString() {
        return "model_Sanpham{" +
                "id=" + id +
                ", tensp='" + tensp + '\'' +
                ", giatien=" + giatien +
                ", cauhinh='" + cauhinh + '\'' +
                ", soluong=" + soluong +
                ", linkhinhanh='" + linkhinhanh + '\'' +
                ", loaisp=" + loaisp +
                '}';
    }
}

