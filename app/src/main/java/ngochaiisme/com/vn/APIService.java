package ngochaiisme.com.vn;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ngochaiisme.com.vn.model.model_Sanpham;
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

    @GET("getsanpham.php")
    Call<List<model_Sanpham>> getAllProducts();

    @DELETE("deletesanpham.php")
    Call<Void> deleteProduct(@Query("id") int id);


    @FormUrlEncoded
    @POST("themsanpham.php")
    Call<Void> addProduct(
            @Field("sp_tensp") String sp_tensp,
            @Field("sp_giatien") float sp_giatien,
            @Field("sp_cauhinh") String sp_cauhinh,
            @Field("sp_soluong") int sp_soluong,
            @Field("sp_linkhinhanh") String sp_linkhinhanh,
            @Field("sp_loaisp") int sp_loaisp
    );

}
