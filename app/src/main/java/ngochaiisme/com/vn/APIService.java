package ngochaiisme.com.vn;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ngochaiisme.com.vn.model.model_Sanpham;
import ngochaiisme.com.vn.model.model_doanhthutheoloaisp;
import ngochaiisme.com.vn.model.model_donhang;
import ngochaiisme.com.vn.model.model_item_ctdh;
import ngochaiisme.com.vn.model.model_sanphamtheosoluongbanduoc;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    APIService service = new Retrofit.Builder()
            .baseUrl("http://192.168.43.28/banhang/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    @GET("admin/getsanpham.php")
    Call<List<model_Sanpham>> getAllProducts();

    @GET("admin/getdonhang.php")
    Call<List<model_donhang>> getAllDonhang();

    @GET("admin/get_tenkh.php")
    Call<ResponseBody> getTenKhachHang(@Query("id") String id);

    @DELETE("admin/deletesanpham.php")
    Call<Void> deleteProduct(@Query("id") int id);
    @DELETE("admin/deletedonhang.php")
    Call<Void> deleteDonhang(@Query("id") int id);

    @FormUrlEncoded
    @POST("admin/themsanpham.php")
    Call<Void> addProduct(
            @Field("sp_tensp") String sp_tensp,
            @Field("sp_giatien") float sp_giatien,
            @Field("sp_cauhinh") String sp_cauhinh,
            @Field("sp_soluong") int sp_soluong,
            @Field("sp_linkhinhanh") String sp_linkhinhanh,
            @Field("sp_loaisp") int sp_loaisp
    );



    @FormUrlEncoded
    @POST("admin/get_ctdh.php")
    Call<List<model_item_ctdh>> get_list_sanpham_ctdh(
            @Field ("dh_id") int dh_id
    );

    @FormUrlEncoded
    @POST("admin/get_doanhthutheoloaisp.php")
    Call<List<model_doanhthutheoloaisp>> get_doanhthutheoloaisp(
            @Field ("ngaybatdau") String ngaybatdau,
            @Field ("ngayketthuc") String ngayketthuc
            );


    @FormUrlEncoded
    @POST("admin/get_soluongsanphambanduoc.php")
    Call<List<model_sanphamtheosoluongbanduoc>> get_soluongsanphambanduoc(
            @Field ("ngaybatdau") String ngaybatdau,
            @Field ("ngayketthuc") String ngayketthuc
    );


    @FormUrlEncoded
    @POST("admin/capnhaptrangthaidonhang.php")
    Call<Void> capnhaptrangthaidonhang(
            @Field ("dh_id") int dh_id,
            @Field ("dh_trangthaimoi") String dh_trangthaimoi
    );
    @FormUrlEncoded
    @POST("admin/capnhapsanpham.php")
    Call<Void> UpdateProduct(
            @Field("sp_id") int sp_id,
            @Field("sp_tensp") String sp_tensp,
            @Field("sp_giatien") float sp_giatien,
            @Field("sp_cauhinh") String sp_cauhinh,
            @Field("sp_soluong") int sp_soluong,
            @Field("sp_linkhinhanh") String sp_linkhinhanh,
            @Field("sp_loaisp") int sp_loaisp
    );

}
