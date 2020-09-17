package com.nino.ibmsicred.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.nino.ibmsicred.R;
import com.nino.ibmsicred.adapters.EventsAdapter;
import com.nino.ibmsicred.databinding.FragmentEventsBinding;
import com.nino.ibmsicred.models.Event;
import com.nino.ibmsicred.viewmodels.EventsViewModel;

import java.util.List;

/**********************************************************************************************
 * EventsFragment - classe do fragment que lista os eventos.
 * HISTÓRICO
 * 16/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
public class EventsFragment extends Fragment {

    //Objeto view-model que gerencia a obteção da lista de eventos disponíveis.
    public static EventsViewModel eventsViewModel;
    //Databinding para acessar os componentes declarados no layout do fragment.
    public static FragmentEventsBinding fragmentEventsBinding;
    //Adapter que define os itens do recycler-view.
    private EventsAdapter eventsAdapter;

    /**********************************************************************************************
     * OnCreate - evento gerado ao criar o fragment.
     * @param savedInstanceState - Dados da última sessão salva.
     *********************************************************************************************/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Instancia o objeto view-model.
        eventsViewModel = ViewModelProviders.of(this).get(EventsViewModel.class);
        //Inicia o objeto view-model.
        eventsViewModel.init();

        //Define o processo de busca dos eventos.
        eventsViewModel.getEventsLiveData().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> eventsResponse) {
                //Retornou alguma coisa?
                if (eventsResponse != null) {
                    //Sim, associa a lista obtida com o adapter do recycler-view.
                    eventsAdapter = new EventsAdapter(eventsResponse);
                    fragmentEventsBinding.recyclerViewEvents.setAdapter(eventsAdapter);
                }else{
                    Snackbar.make(fragmentEventsBinding.getRoot() , getResources().getString(R.string.error_get_events), Snackbar.LENGTH_LONG).show();
                }

                //Esconde o cursor de espera.
                fragmentEventsBinding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    /**********************************************************************************************
     * onCreateView - evento gerado ao criara a view do fragment.
     * @param savedInstanceState - Dados da última sessão salva.
     *********************************************************************************************/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Instancia o databinding.
        fragmentEventsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_events, container, false);

        //Define o gerenciador do recycler view.
        fragmentEventsBinding.recyclerViewEvents.setLayoutManager(new LinearLayoutManager(getContext()));

        //Inicia o processo de busca dos eventos.
        eventsViewModel.getEvents();

        return fragmentEventsBinding.getRoot();
    }
}
