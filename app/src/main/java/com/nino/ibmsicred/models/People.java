package com.nino.ibmsicred.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.SerializedName;

/**********************************************************************************************
 * People - classe do modelo de dados de determinada pessoa associda a um evento.
 * HISTÓRICO
 * 16/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
public class People {

    //Identificador da pessoa.
    @SerializedName("id")
    private int id;

    //Nome da pessoa
    @SerializedName("name")
    private String name;

    //Identificador do evento associada a pessoa.
    @SerializedName("eventId")
    private int eventId;

    //Url da imagem da pessoa
    @SerializedName("picture")
    private String picture;

    /**********************************************************************************************
     * getId - método para obter o identificador da pessoa.
     *********************************************************************************************/
    public int getId() {
        return id;
    }

    /**********************************************************************************************
     * getId - método para obter o nome da pessoa.
     *********************************************************************************************/
    public String getName() {
        return name;
    }

    /**********************************************************************************************
     * getEventId - método para obter o identificador do evento associada a pessoa.
     *********************************************************************************************/
    public int getEventId() {
        return eventId;
    }

    /**********************************************************************************************
     * getId - método para obter a Url da imagem da pessoa.
     *********************************************************************************************/
    public String getPicture() {
        return picture;
    }

    /**********************************************************************************************
     * loadImagePeople - método para obter a imagem da pessoa evento no formato circular.
     * @param view - view onde será exibida a imagem.
     * @param imageUrl - Urls da imagem.
     *********************************************************************************************/
    @BindingAdapter("picturePeople")
    public static void loadImagePeople(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl).apply(new RequestOptions().circleCrop())
                .into(view);
    }
}
