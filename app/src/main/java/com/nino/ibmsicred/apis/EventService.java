package com.nino.ibmsicred.apis;

import com.google.gson.JsonObject;
import com.nino.ibmsicred.models.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**********************************************************************************************
 * EventService - interface que faz as chamadas da API.
 * HISTÓRICO
 * 16/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
public interface EventService {

    /**********************************************************************************************
     * getEventList - chama o método GET que lista os eventos.
     *********************************************************************************************/
    @GET("/events")
    Call<List<Event>> getEventList();

    /**********************************************************************************************
     * getEventList - chama o método GET que obtém detalher de determinado evento.
     * @param id - Identificador do evento a ser pesquisado.
     *********************************************************************************************/
    @GET("events/{id}")
    Call<Event> getEventDetail(@Path("id") String id);

    /**********************************************************************************************
     * getEventList - chama o método POST que envia dados de determinada pessoa interessada no evento.
     * @param eventId - Identificador do evento.
     * @param name - Nome da pessoa.
     * @param email - e-mail da pessoa.
     *********************************************************************************************/
    @Headers("Content-Type: text/html")
    @FormUrlEncoded
    @POST("/checkin")
    Call<String> checkinInterested(@Field("eventId") String eventId,
                                   @Field("name") String name,
                                   @Field("email") String email);
}
