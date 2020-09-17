package com.nino.ibmsicred.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.nino.ibmsicred.R;
import com.nino.ibmsicred.databinding.LayoutDialogInterestedBinding;
import com.nino.ibmsicred.viewmodels.CheckinInterestedViewModel;

/**********************************************************************************************
 * CheckinInterestedDialog - fragmento do tipo caixa de diálogo utilizado para obter os dados de
 *                           checkin de uma pessoa interessada no evento.
 * HISTÓRICO
 * 16/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
public class CheckinInterestedDialog extends DialogFragment {

    //Identificador do evento.
    private String eventId;

    //Objeto view-model para o checkin da pessoa.
    public static CheckinInterestedViewModel checkinInterestedViewModel;

    //Databinding para acesso aos componentes visuais do layout.
    private static LayoutDialogInterestedBinding layoutDialogInterestedBinding;

    /**********************************************************************************************
     * CheckinInterestedDialog - construtor da classe que instancia o objeto estático.
     * @param eventId - Id dos envento a ser associado a pessoa.
     *********************************************************************************************/
    public static CheckinInterestedDialog newInstance(String eventId) {
        //Instancia o objeto.
        CheckinInterestedDialog frag = new CheckinInterestedDialog();

        //Envia os agumentos
        Bundle args = new Bundle();
        args.putString("event_id", eventId);
        frag.setArguments(args);

        //Retorna a caixa de diálogo
        return frag;
    }

    /**********************************************************************************************
     * onCreateView - evento gerado ao criar a view da caixa de diálogo.
     * @param inflater - Objeto inflater.
     * @param container
     * @param savedInstanceState - Dados da última sessão salva.
     *********************************************************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Instancia o databinding.
        layoutDialogInterestedBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_dialog_interested, null, false);

        //Obtém os argumentos
        eventId = getArguments().getString("event_id", "-1");

        //Instancia o objeto view-model
        checkinInterestedViewModel = ViewModelProviders.of(this).get(CheckinInterestedViewModel.class);
        //Inicia o objeto view-model.
        checkinInterestedViewModel.init();

        //Define o processo de inserção dos dados da pessoa.
        checkinInterestedViewModel.checkinInterestedLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String checkinInterestedResponse) {
                //Retornou alguma coisa?
                if (checkinInterestedResponse != null) {
                    Snackbar.make(layoutDialogInterestedBinding.getRoot() , getResources().getString(R.string.success_send_data_interested), Snackbar.LENGTH_LONG).show();
                }else{
                    Snackbar.make(layoutDialogInterestedBinding.getRoot() , getResources().getString(R.string.error_send_data_interested), Snackbar.LENGTH_LONG).show();
                }
                dismiss();
            }
        });

        //Define o evento ao clicar no botão de cancelar a operação
        layoutDialogInterestedBinding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fecha a caixa de diálogo.
                dismiss();
            }
        });

        //Define o evento ao clicar no botão de inserir a pessoa.
        layoutDialogInterestedBinding.buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutDialogInterestedBinding.edtNameInterested.getText().toString().trim() .length()>0 && layoutDialogInterestedBinding.edtEMail.getText().toString().trim().length()>0){
                    //Inicia o processo de inserção da pessoa.
                    checkinInterestedViewModel.checkinInterested(eventId, layoutDialogInterestedBinding.edtNameInterested.getText().toString(), layoutDialogInterestedBinding.edtEMail.getText().toString());
                    //Fecha a caixa de diálogo.
                    dismiss();
                }else{
                    Snackbar.make(layoutDialogInterestedBinding.getRoot() , getResources().getString(R.string.incomplete_data_interested), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        return layoutDialogInterestedBinding.getRoot();
    }

    /**********************************************************************************************
     * onCreateView - evento gerado após a criação da view da caixa de diálogo.
     * @param view - view criada.
     * @param savedInstanceState - Dados da última sessão salva.
     *********************************************************************************************/
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Força a exibição do tecladinho.
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
