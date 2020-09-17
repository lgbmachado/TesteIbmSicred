package com.nino.ibmsicred.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nino.ibmsicred.repositories.EventsRepository;

/**********************************************************************************************
 * EventsRepository - classe view-model que faz o checkin de determinada pessoa associada a um evento.
 * HISTÓRICO
 * 16/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
public class CheckinInterestedViewModel extends AndroidViewModel {

    //Repositório para acesso aos dados.
    private EventsRepository eventsRepository;
    //Dados da resposta obtida.
    private LiveData<String> checkinInterestedLiveData;

    /**********************************************************************************************
     * EventDetailsViewModel - construtor da classe.
     *********************************************************************************************/
    public CheckinInterestedViewModel(@NonNull Application application) {
        super(application);
    }

    /**********************************************************************************************
     * init - método que instancia os objetos.
     *********************************************************************************************/
    public void init() {
        eventsRepository = new EventsRepository();
        checkinInterestedLiveData = eventsRepository.getCheckinInterestedLiveData();
    }

    /**********************************************************************************************
     * checkinInterested - método que chama a API que insere uma pessoa associada a um evento.
     * @param eventId - Identificador do evento.
     * @param name - Nome da pessoa.
     * @param email - e-mail da pessoa.
     *********************************************************************************************/
    public void checkinInterested(String eventId, String name,  String email) {
        eventsRepository.checkinInterested(eventId, name,  email);
    }

    /**********************************************************************************************
     * checkinInterestedLiveData - método que retorna o resultado da inserção.
     *********************************************************************************************/
    public LiveData<String> checkinInterestedLiveData() {
        return checkinInterestedLiveData;
    }
}
