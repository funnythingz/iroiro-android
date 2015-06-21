package com.funnythingz.iroiro.infrastructure;

import com.funnythingz.iroiro.domain.Iro;
import java.util.ArrayList;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

public interface IroIroAPI {
    @GET("/iroiro?access_key=unkounko")
    Observable<ArrayList<Iro>> getIroIro();

    @GET("/iroiro/{id}?access_key=unkounko")
    Observable<Iro> getIro(@Path("id") int iroId);

    @POST("/iroiro?access_key=unkounko")
    Response postNewIro(@Field("iro[content]") String content, @Field("iro[color_id]") int colorId);

    // TODO: re_iro_id
    //void postNewIro(@Field("iro[content]") String content, @Field("iro[color_id]") int colorId, @Field("iro[re_iro_id]") int reIroId);
}