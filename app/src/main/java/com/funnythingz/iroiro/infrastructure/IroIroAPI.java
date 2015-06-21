package com.funnythingz.iroiro.infrastructure;

import com.funnythingz.iroiro.domain.Iro;
import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface IroIroAPI {
    @GET("/iroiro?access_key=unkounko")
    Observable<ArrayList<Iro>> getIroIro();

    @GET("/iroiro/{id}?access_key=unkounko")
    Observable<Iro> getIro(@Path("id") int iroId);
}