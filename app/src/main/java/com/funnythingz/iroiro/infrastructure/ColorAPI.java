package com.funnythingz.iroiro.infrastructure;

import com.funnythingz.iroiro.domain.Color;
import com.funnythingz.iroiro.domain.Iro;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface ColorAPI {
    @GET("/colors?access_key=unkounko")
    Observable<ArrayList<Color>> getColors();

    @GET("/colors/{id}?access_key=unkounko")
    Observable<Color> getColor(@Path("id") int colorId);
}