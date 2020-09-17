package com.nino.ibmsicred.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.nino.ibmsicred.R;

/**********************************************************************************************
 * EventsActivity - classe da activity principal do aplicativo.
 * HISTÓRICO
 * 16/09/2020 - Luiz Guilherme - Versão inicial.
 **********************************************************************************************/
public class EventsActivity extends AppCompatActivity {

    public static Context context;

    /**********************************************************************************************
     * OnCreate - evento gerado ao criar a activity.
     * @param savedInstanceState - Dados da última sessão salva.
     *********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        context = getApplicationContext();
    }
}