package com.example.dd24client.network;

import com.example.dd24client.model.AcquistaastaribassoModel;
import com.example.dd24client.model.AstaribassoModel;
import com.example.dd24client.model.AstaribassoseguiteModel;
import com.example.dd24client.model.AstasilenziosaModel;
import com.example.dd24client.model.AstasilenziosaseguiteModel;
import com.example.dd24client.model.OffertaastasilenziosaModel;
import com.example.dd24client.model.UtenteModel;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseOfferte;
import com.example.dd24client.utils.MessageResponseRibasso;
import com.example.dd24client.utils.MessageResponseSilenziosa;
import com.example.dd24client.utils.MessageResponseUtente;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/testConnection")
    @Headers("Accept: application/json")
    Call<MessageResponse> ping();

    @POST("/api/utente/login")
    @Headers("Accept: application/json")
    Call<MessageResponse> loginUser(@Body UtenteModel utente);

    @POST("/api/utente/signup")
    @Headers("Accept: application/json")
    Call<MessageResponse> signupUser(@Body UtenteModel utente);

    @POST("/api/utente/recuperopin")
    @Headers("Accept: application/json")
    Call<MessageResponse> recuperoPin(@Body UtenteModel utente);

    @POST("/api/utente/validatepin")
    @Headers("Accept: application/json")
    Call<MessageResponse> validatePin(@Body UtenteModel utente);

    @PUT("/api/utente/updatePassword")
    @Headers("Accept: application/json")
    Call<MessageResponse> updatePassword(@Body UtenteModel utente);

    @GET("/api/astaribasso/richiedi")
    @Headers("Accept: application/json")
    Call<MessageResponseRibasso> richiediAstaRibasso(@Query("parametro1") String parametro1);

    @GET("/api/astasilenziosa/richiedi")
    @Headers("Accept: application/json")
    Call<MessageResponseSilenziosa> richiediAstaSilenziosa(@Query("parametro1") String parametro1);

    @GET("/api/utente/datiutente")
    @Headers("Accept: application/json")
    Call<MessageResponseUtente> datiUtente(@Query("parametro1") String email);

    @PUT("/api/utente/updateProfilo")
    @Headers("Accept: application/json")
    Call<MessageResponse> updateProfilo(@Body UtenteModel utente);

    @POST("/api/astaribasso/crea")
    @Headers("Accept: application/json")
    Call<MessageResponse> creaAstaribasso(@Body AstaribassoModel asta);

    @POST("/api/astasilenziosa/crea")
    @Headers("Accept: application/json")
    Call<MessageResponse> creaAstasilenziosa(@Body AstasilenziosaModel asta);

    @GET("/api/asteribassoacquisto/richiedi")
    @Headers("Accept: application/json")
    Call<MessageResponseRibasso> acquistiAstaRibasso(@Query("parametro1") String parametro1);

    @GET("/api/astesilenziosaofferta/richiedi")
    @Headers("Accept: application/json")
    Call<MessageResponseSilenziosa> acquistiAstaSilenziosa(@Query("parametro1") String parametro1);

    @GET("/api/asteribassoseguite/richiedi")
    @Headers("Accept: application/json")
    Call<MessageResponseRibasso> seguiteAstaRibasso(@Query("parametro1") String parametro1);

    @GET("/api/astesilenziosaseguite/richiedi")
    @Headers("Accept: application/json")
    Call<MessageResponseSilenziosa> seguiteAstaSilenziosa(@Query("parametro1") String parametro1);

    @GET("/api/astesilenziosaofferta/richiediofferte")
    @Headers("Accept: application/json")
    Call<MessageResponseOfferte> richiediOfferteAstaSilenziosa(@Query("parametro1") Integer parametro1);

    @PUT("/api/astesilenziosaofferta/updateOfferta")
    @Headers("Accept: application/json")
    Call<MessageResponse> updateStatoOfferta(@Body OffertaastasilenziosaModel offerta);

    @GET("/api/astaribasso/searchVenditore")
    @Headers("Accept: application/json")
    Call<MessageResponseRibasso> richiediAstaRibassoSearchVenditore(@Query("query") String query,
                                           @Query("categoria") List<String> categoria,
                                           @Query("ordinaper") String ordinaper,
                                           @Query("startvalue") Float startvalue,
                                           @Query("endvalue") Float endvalue,
                                           @Query("email") String email);

    @GET("/api/astasilenziosa/searchVenditore")
    @Headers("Accept: application/json")
    Call<MessageResponseSilenziosa> richiediAstaSilenziosaSearchVenditore(@Query("query") String query,
                                           @Query("data") String data,
                                           @Query("categoria") List<String> categoria,
                                           @Query("ordinaper") String ordinaper,
                                           @Query("email") String email);

    @GET("/api/astaribasso/searchAcquirente")
    @Headers("Accept: application/json")
    Call<MessageResponseRibasso> richiediAstaRibassoSearchAcquirente(@Query("query") String query,
                                                                    @Query("categoria") List<String> categoria,
                                                                    @Query("ordinaper") String ordinaper,
                                                                    @Query("startvalue") Float startvalue,
                                                                     @Query("endvalue") Float endvalue,
                                                                     @Query("email") String email);

    @GET("/api/astasilenziosa/searchAcquirente")
    @Headers("Accept: application/json")
    Call<MessageResponseSilenziosa> richiediAstaSilenziosaSearchAcquirente(@Query("query") String query,
                                                                          @Query("data") String data,
                                                                          @Query("categoria") List<String> categoria,
                                                                           @Query("ordinaper") String ordinaper,
                                                                           @Query("email") String email);
    @GET("/api/astaribasso/richieditutte")
    @Headers("Accept: application/json")
    Call<MessageResponseRibasso> richiediAstaRibassoAcquirente(@Query("parametro1") String email);

    @GET("/api/astasilenziosa/richieditutte")
    @Headers("Accept: application/json")
    Call<MessageResponseSilenziosa> richiediAstaSilenziosaAcquirente(@Query("parametro1") String email);

    @POST("/api/astesilenziosaofferta/creaOfferta")
    @Headers("Accept: application/json")
    Call<MessageResponse> creaOfferta(@Body OffertaastasilenziosaModel offerta);

    @POST("/api/asteribassoacquisto/acquisto")
    @Headers("Accept: application/json")
    Call<MessageResponse> acquisto(@Body AcquistaastaribassoModel acquisto);

    @POST("/api/asteribassoseguite/segui")
    Call<MessageResponse> seguiAstaRibasso(@Body AstaribassoseguiteModel segui);

    @POST("/api/astesilenziosaseguite/segui")
    Call<MessageResponse> seguiAstaSilenziosa(@Body AstasilenziosaseguiteModel segui);

    @GET("/api/asteribassoseguite/seguitabool")
    Call<MessageResponse> seguitaboolAstaRibasso(@Query("parametro1") Integer parametro1, @Query ("parametro2") String parametro2);

    @GET("/api/astesilenziosaseguite/seguitabool")
    Call<MessageResponse> seguitaboolAstaSilenziosa(@Query("parametro1") Integer parametro1, @Query ("parametro2") String parametro2);
}
