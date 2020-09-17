package com.nino.ibmsicred.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nino.ibmsicred.models.Event;
import com.nino.ibmsicred.repositories.EventsRepository;

/**********************************************************************************************
 * EventDetailsViewModel - classe view-model que obtém detalhes de um evento.
 * HISTÓRICO
 * 16/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
public class EventDetailsViewModel  extends AndroidViewModel {

    //Repositório para acesso aos dados.
    private EventsRepository eventsRepository;
    //Dados da resposta obtida.
    private LiveData<Event> eventDetailLiveData;

    /**********************************************************************************************
     * EventDetailsViewModel - construtor da classe.
     *********************************************************************************************/
    public EventDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    /**********************************************************************************************
     * init - método que instancia os objetos.
     *********************************************************************************************/
    public void init() {
        eventsRepository = new EventsRepository();
        eventDetailLiveData = eventsRepository.getEventDetailLiveData();
    }

    /**********************************************************************************************
     * getEventDetail - método que chama a API que obtém os eventos.
     * @param id - Identificador do evento.
     *********************************************************************************************/
    public void getEventDetail(String id) {
        eventsRepository.getEventDetail(id);
    }

    /**********************************************************************************************
     * getEventDetailLiveData - método que retorna os detalhes do evento obtidos.
     *********************************************************************************************/
    public LiveData<Event> getEventDetailLiveData() {
        return eventDetailLiveData;
    }
}