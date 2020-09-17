package com.nino.ibmsicred.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nino.ibmsicred.models.Event;
import com.nino.ibmsicred.repositories.EventsRepository;

import java.util.List;

/**********************************************************************************************
 * EventDetailsViewModel - classe view-model que obtém a lista de eventos.
 * HISTÓRICO
 * 16/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
public class EventsViewModel extends AndroidViewModel {

    //Repositório para acesso aos dados.
    private EventsRepository eventsRepository;
    //Dados da resposta obtida.
    private LiveData<List<Event>> eventsLiveData;

    /**********************************************************************************************
     * EventDetailsViewModel - construtor da classe.
     *********************************************************************************************/
    public EventsViewModel(@NonNull Application application) {
        super(application);
    }

    /**********************************************************************************************
     * init - método que instancia os objetos.
     *********************************************************************************************/
    public void init() {
        eventsRepository = new EventsRepository();
        eventsLiveData = eventsRepository.getEventListLiveData();
    }

    /**********************************************************************************************
     * init - método que chama a API que obtém os detalhes do evento.
     *********************************************************************************************/
    public void getEvents() {
        eventsRepository.getEvents();
    }

    /**********************************************************************************************
     * init - método que retorna os eventos obtidos.
     *********************************************************************************************/
    public LiveData<List<Event>> getEventsLiveData() {
        return eventsLiveData;
    }

}
