package com.nino.ibmsicred.models;

import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.SerializedName;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

/**********************************************************************************************
 * Event - classe do modelo de dados de determinado evento.
 * HISTÓRICO
 * 16/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
public class Event {

    //Lista das pessoas associadas ao evento.
    @SerializedName("people")
    private List<People> people;

    //Data do evento
    @SerializedName("date")
    private Long date;

    //Descrição do evento.
    @SerializedName("description")
    private String description;

    //Url da imagem do evento.
    @SerializedName("image")
    private String image;

    //Longitude da localização do evento.
    @SerializedName("longitude")
    private Double longitude;

    //Latitude da localização do evento.
    @SerializedName("latitude")
    private Double latitude;

    //Preço do evento.
    @SerializedName("price")
    private Double price;

    //Título do evento.
    @SerializedName("title")
    private String title;

    //Identificador do evento.
    @SerializedName("id")
    private String id;

    /**********************************************************************************************
     * getId - método para obter o identificador do evento.
     *********************************************************************************************/
    public String getId() {
        return id;
    }

    /**********************************************************************************************
     * getTitle - método para obter o título do evento.
     *********************************************************************************************/
    public String getTitle() {
        return title;
    }

    /**********************************************************************************************
     * getDate - método para obter a data formatada do evento.
     *********************************************************************************************/
    public String getDate() {
        return DateFormat.format("dd/MM/yyyy HH:mm", new Date(date)).toString();
    }

    /**********************************************************************************************
     * getDate - método para obter a descrição do evento.
     *********************************************************************************************/
    public String getDescription() {
        return description;
    }

    /**********************************************************************************************
     * getImage - método para obter a Url da imagem do evento.
     *********************************************************************************************/
    public String getImage() {
        return image;
    }

    /**********************************************************************************************
     * loadImageList - método para obter a imagem do evento no formato circular para ser usado no recycle-view.
     * @param view - view onde será exibida a imagem.
     * @param imageUrl - Urls da imagem.
     *********************************************************************************************/
    @BindingAdapter("imageList")
    public static void loadImageList(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl).apply(new RequestOptions().circleCrop())
                .into(view);
    }

    /**********************************************************************************************
     * loadImageDetail - método para obter a imagem do evento no formato retangular para ser usado nos detalhes.
     * @param view - view onde será exibida a imagem.
     * @param imageUrl - Urls da imagem.
     *********************************************************************************************/
    @BindingAdapter("imageDetail")
    public static void loadImageDetail(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl).apply(new RequestOptions().centerCrop())
                .into(view);
    }

    /**********************************************************************************************
     * getLatitude - método para obter a latitude da localização do evento.
     *********************************************************************************************/
    public Double getLatitude() {
        return latitude;
    }

    /**********************************************************************************************
     * getLongitude - método para obter a longitude da localização do evento.
     *********************************************************************************************/
    public Double getLongitude() {
        return longitude;
    }

    /**********************************************************************************************
     * getPrice - método para obter o preço do localização do evento.
     *********************************************************************************************/
    public String getPrice() {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        return format.format(price);
    }

    /**********************************************************************************************
     * getPeople - método para obter a lista de pessoas associadas ao evento.
     *********************************************************************************************/
    public List<People> getPeople() {
        return people;
    }
}
