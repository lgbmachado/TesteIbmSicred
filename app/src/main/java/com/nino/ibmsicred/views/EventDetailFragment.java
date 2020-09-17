package com.nino.ibmsicred.views;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.nino.ibmsicred.R;
import com.nino.ibmsicred.databinding.FragmentEventDetailBinding;
import com.nino.ibmsicred.databinding.LayoutPeopleBinding;
import com.nino.ibmsicred.models.Event;
import com.nino.ibmsicred.models.People;
import com.nino.ibmsicred.viewmodels.EventDetailsViewModel;

import java.text.NumberFormat;

/**********************************************************************************************
 * EventDetailFragment - classe do fragment que lista os eventos.
 * HISTÓRICO
 * 16/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
public class EventDetailFragment extends Fragment implements OnMapReadyCallback {

    //Identificador do evento.
    private String eventId;

    //Objeto view-model para obter os detalhes do evento.
    public static EventDetailsViewModel eventDetailsViewModel;

    //Databinding para acesso aos componentes visuais do layout.
    private static FragmentEventDetailBinding fragmentEventDetailBinding;

    //View que vai receber o fragment.
    private ViewGroup container;

    //Objeto que gerencia os mapas e a loccalização.
    private SupportMapFragment mapFragment;

    /**********************************************************************************************
     * OnCreate - evento gerado ao criar o fragment.
     * @param savedInstanceState - Dados da última sessão salva.
     *********************************************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Obtém o id do evento.
        eventId = EventDetailFragmentArgs.fromBundle(getArguments()).getIdEvent();

        //Instancia o objeto view-model.
        eventDetailsViewModel = ViewModelProviders.of(this).get(EventDetailsViewModel.class);
        //Inicia o objeto view-model.
        eventDetailsViewModel.init();

        //Define o processo de busca dos detalhes do evento.
        eventDetailsViewModel.getEventDetailLiveData().observe(this, new Observer<Event>() {
            @Override
            public void onChanged(Event eventDetailsResponse) {
                //Retornou alguma coisa?
                if (eventDetailsResponse != null) {
                    //Sim atualiza a tela
                    fragmentEventDetailBinding.setEvent(eventDetailsResponse);

                    //Obtém a Url da imagem do evento.
                    String url = eventDetailsResponse.getImage();
                    //Tem "https" na Url?
                    if (!url.contains("https")){
                        //Não, ajusta a Url
                        url = url.replace("http", "https");
                    }

                    //Chama o método que carrega a imagem e exibe a localização
                    loadMapPeople();

                    //Obtém a imagem do evento.
                    fragmentEventDetailBinding.setImageUrl(url);
                }else{
                    Snackbar.make(fragmentEventDetailBinding.getRoot() , getResources().getString(R.string.error_get_events), Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    /**********************************************************************************************
     * onCreateView - evento gerado ao criara a view do fragment.
     * @param savedInstanceState - Dados da última sessão salva.
     *********************************************************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Obtém o container do fragment.
        this.container = container;

        //Instancia o databinding.
        fragmentEventDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_detail, container, false);

        //Exibe o cursor de espera.
        fragmentEventDetailBinding.progressBar.setVisibility(View.VISIBLE);

        //Define ícone do botão de retorno.
        fragmentEventDetailBinding.detailToolbar.setNavigationIcon(R.drawable.ic_back);

        //Define o evento ao clicar no botão de retorno.
        fragmentEventDetailBinding.detailToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Volta para a tela anterior.
                getActivity().onBackPressed();
            }
        });

        //Inicia o processo de busca dos detalhes do evento.
        eventDetailsViewModel.getEventDetail(eventId);

        //Define o evento a clicar no botão par inserir interessado
        fragmentEventDetailBinding.btnAddPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckinInterestedDialog checkinInterestedDialog = CheckinInterestedDialog.newInstance(eventId);
                checkinInterestedDialog.show(getActivity().getSupportFragmentManager(), "Cadastrar Interessados");
            }
        });
        return fragmentEventDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapEvent);
    }

    private void loadMapPeople(){
        mapFragment.getMapAsync(this);

        if (fragmentEventDetailBinding.getEvent().getPeople().size()>0){
            for (People people : fragmentEventDetailBinding.getEvent().getPeople()){
                LayoutPeopleBinding layoutPeopleBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_people, this.container, false);
                layoutPeopleBinding.setPeople(people);

                layoutPeopleBinding.setImageUrl(people.getPicture());

                fragmentEventDetailBinding.layPeoples.addView(layoutPeopleBinding.getRoot());
            }
            fragmentEventDetailBinding.layPeoples.setVisibility(View.VISIBLE);
        }else{
            fragmentEventDetailBinding.layPeoples.setVisibility(View.INVISIBLE);
        }

        fragmentEventDetailBinding.progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Define a posição.
        LatLng position = new LatLng(fragmentEventDetailBinding.getEvent().getLatitude(), fragmentEventDetailBinding.getEvent().getLongitude());
        //Inclui o marcador.
        googleMap.addMarker(new MarkerOptions().position(position).title(fragmentEventDetailBinding.getEvent().getTitle()));
        //Posiciona e define o zoom.
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,14));
    }
}