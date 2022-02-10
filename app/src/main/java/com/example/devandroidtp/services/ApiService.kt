package com.example.devandroidtp.services

import com.example.devandroidtp.model.Point
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    //Liste l'ensemble des maps
    @GET("/iut/game-list")
    suspend fun getAllMaps(): Response<MutableList<String>>

    //Renvoie l'ensemble des points correspondant à la map {id}
    @GET("/iut/game/{id}")
    suspend fun getOneMap(@Path("id") id: String): Response<MutableList<Point>>

    //Creation du map avec contenu json dans le body
    @POST("/iut/game/{id}")
    // TO DO
    suspend fun createMap()

    //Supprime la map {id}
    @DELETE("/iut/game/{id}")
    // TO DO
    suspend fun deleteMap()
}