package com.example.dev.hazikura;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by dev on 2018/01/06.
 */

public class OptionActivity extends AppCompatActivity{

    @Deprecated
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_option);

        Button returnBotton = findViewById(R.id.return_button);
        returnBotton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

    }
}
