package com.nino.ibmsicred.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.nino.ibmsicred.R;
import com.nino.ibmsicred.databinding.LayoutEventItemBinding;
import com.nino.ibmsicred.models.Event;
import com.nino.ibmsicred.views.EventsFragment;
import com.nino.ibmsicred.views.EventsFragmentDirections;

import java.util.List;

/**********************************************************************************************
 * EventsAdapter - classe adapter que define os itens do recycler-view que exibe os eventos.
 * HISTÓRICO
 * 16/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>{
    //Lista dos eventos obtida.
    private List<Event> eventList;
    //Objeto infater para instaciar os componentes declarados no layout.
    private LayoutInflater layoutInflater;

    /**********************************************************************************************
     * EventsAdapter - construtor da classe.
     * @param eventList - Lista dos enventos a ser exibida.
     *********************************************************************************************/
    public EventsAdapter(List<Event> eventList) {
        //Obtém a lista de eventos
        this.eventList = eventList;
    }

    /**********************************************************************************************
     * onCreateViewHolder - evento gerado ao criar o adapter.
     * @param parent - View pai do item.
     * @param i - Tipo da View.
     *********************************************************************************************/
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //O objeto layoutInflater foi instanciado?
        if (layoutInflater == null) {
            //Não, instancia ele.
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        //Instancia o databinding.
        LayoutEventItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_event_item, parent, false);

        return new ViewHolder(binding);
    }

    /**********************************************************************************************
     * onBindViewHolder - evento gerado ao exibir determinado item.
     * @param holder - Objeto que representa o ítem a ser exibido.
     * @param position - Índice do item na lista.
     *********************************************************************************************/
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Obtém os dados do evento a ser exibido.
        Event event = eventList.get(position);

        //Obtém a Url da imagem do evento.
        String url = event.getImage();
        //Tem "https" na Url?
        if (!url.contains("https")){
            //Não, ajusta a Url
            url = url.replace("http", "https");
        }
        //Obtém a imagem do evento.
        holder.binding.setImageUrl(url);

        //Define o evento para tratar a seleção do evento.
        holder.bind(event, createClickListener(event.getId()));
    }

    /**********************************************************************************************
     * OnClickListener - evento que trata a seleção de determinado evento.
     * @param eventId - Id do evento selecionado.
     *********************************************************************************************/
    private View.OnClickListener createClickListener(String eventId) {
        //Instancia o evento.
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Exibe o cursor de espera.
                EventsFragment.fragmentEventsBinding.progressBar.setVisibility(View.VISIBLE);

                //Abre o fragment que exibe os detalhes do evento.
                NavDirections direction = EventsFragmentDirections.actionEventsFragmentToEventDetailFragment(eventId);
                Navigation.findNavController(view).navigate(direction);
            }
        };
    }

    /**********************************************************************************************
     * getItemCount - método de exibe a quantidade de itens a ser exibida.
     *********************************************************************************************/
    @Override
    public int getItemCount() {
        return eventList.size();
    }

    /**********************************************************************************************
     * ViewHolder - classe local que define o ítem a ser exido.
     * HISTÓRICO
     * 16/09/2020 - Luiz Guilherme - Versão inicial.
     **********************************************************************************************/
    class ViewHolder extends RecyclerView.ViewHolder {

        //Databinding para acessar os componentes declarados no layout do adapter.
        LayoutEventItemBinding binding;

        /**********************************************************************************************
         * ViewHolder - construtor da classe.
         * @param binding - Databinding para acessar os componentes declarados no layout do adapter.
         *********************************************************************************************/
        ViewHolder(@NonNull LayoutEventItemBinding binding) {
            super(binding.getRoot());

            //Define o databing.
            this.binding = binding;
        }

        /**********************************************************************************************
         * bind - método que associa o evento de clicar no ítem.
         * @param event - Evento selecionado.
         * @param clickListener - Listener que vai monitorar o evento.
         *********************************************************************************************/
        void bind(Event event, View.OnClickListener clickListener) {
            binding.setClickListener(clickListener);
            binding.setEvent(event);
            binding.executePendingBindings();
        }
    }
}