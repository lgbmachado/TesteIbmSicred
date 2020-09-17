package com.nino.ibmsicred.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nino.ibmsicred.apis.EventService;
import com.nino.ibmsicred.models.Event;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventsRepository {
    //Timeout em milisegundos para conexão.
    private static final int TIMEOUT_CONNECT = 10;
    //Timeout em milisegundos para escrita.
    private static final int TIMEOUT_WRITE = 30;
    //Timeout em milisegundos para leitura.
    private static final int TIMEOUT_READ = 30;

    //Url base para acesso à API.
    private static final String BASE_URL = "https://5f5a8f24d44d640016169133.mockapi.io/api/";

    private EventService eventsService;

    //Lista eventos obtidos
    private MutableLiveData<List<Event>> eventsLiveData;

    //Detalhes de evento obtido
    private MutableLiveData<Event> eventDetailLiveData;

    //Resultado da inserção.
    private MutableLiveData<String> checkinInterestedLiveData;

    public EventsRepository() {

        //Instancia a lista de eventos.
        eventsLiveData = new MutableLiveData<>();
        //Instacia o objeto com os detalhes do evento.
        eventDetailLiveData = new MutableLiveData<>();
        //Instancia a string com o resultado da inserção.
        checkinInterestedLiveData = new MutableLiveData<>();

        //Instancia o objeto que fará a conexão.
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        //Instancia o serviço que envia a requisição e trata a resposta recebida.
        eventsService = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EventService.class);
    }


    public LiveData<List<Event>> getEventListLiveData() {
        return eventsLiveData;
    }

    public void getEventsList() {
        //Chama o serviço.
        eventsService.getEventList()
                .enqueue(new Callback<List<Event>>() {
                    @Override
                    //Chegou algo?
                    public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                        //Sim, mas deu tudo certo?
                        if (response.isSuccessful()){
                            //Sim, mas tem conteúdo?
                            if (response.body() != null) {
                                //Sim, retorna.
                                eventsLiveData.postValue(response.body());
                            }
                        }else{
                            onFailure(null, new Throwable());
                        }
                    }

                    //Deu erro?
                    @Override
                    public void onFailure(Call<List<Event>> call, Throwable t) {
                        //Sim, retorna nulo.
                        eventsLiveData.postValue(null);
                    }
                });
    }


    public LiveData<Event> getEventDetailLiveData() {
        return eventDetailLiveData;
    }


    public void getEventDetail(String id) {
        //Chama o serviço.
        eventsService.getEventDetail(id)
                .enqueue(new Callback<Event>() {
                    @Override
                    //Chegou algo?
                    public void onResponse(Call<Event> call, Response<Event> response) {
                        //Sim, mas deu tudo certo?
                        if (response.isSuccessful()){
                            //Sim, mas tem conteúdo?
                            if (response.body() != null) {
                                //Sim, retorna.
                                eventDetailLiveData.postValue(response.body());
                            }
                        }else{
                            onFailure(null, new Throwable());
                        }
                    }

                    @Override
                    //Deu erro?
                    public void onFailure(Call<Event> call, Throwable t) {
                        //Sim, retorna nulo.
                        eventDetailLiveData.postValue(null);
                    }
                });
    }

    public LiveData<String> getCheckinInterestedLiveData() {
        return checkinInterestedLiveData;
    }

    public void checkinInterested(String eventId, String name,  String email) {
        eventsService.checkinInterested(eventId, name, email)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        //Sim, mas deu tudo certo?
                        if (response.isSuccessful()){
                            //Sim, mas tem conteúdo?
                            if (response.body() != null) {
                                //Sim, retorna.
                                checkinInterestedLiveData.postValue(response.body());
                            }
                        }else{
                            checkinInterestedLiveData.postValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        checkinInterestedLiveData.postValue(null);
                    }
                });
    }
}
