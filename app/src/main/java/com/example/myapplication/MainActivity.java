package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.goods.GoodsActivity;
import com.example.myapplication.partners.PartnerActivity;
import com.example.myapplication.receiving.ReceivingActivity;
import com.example.myapplication.sale.AddSaleActivity;
import com.example.myapplication.sale.SaleActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // инициируем кнопку продажи товара
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);

        // инициируем кнопку продажи "товары"
        Button btnGoods = (Button) findViewById(R.id.button_goods);
        btnGoods.setOnClickListener(this);

        // инициируем кнопку продажи "контрагенты"
        Button btnPartners = (Button) findViewById(R.id.button_partners);
        btnPartners.setOnClickListener(this);

        // инициируем кнопку продажи "контрагенты"
        Button btnReceiving = (Button) findViewById(R.id.buttonReceiving);
        btnReceiving.setOnClickListener(this);

        // инициируем кнопку продажи "контрагенты"
        Button btnSales = (Button) findViewById(R.id.button_sale);
        btnSales.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_goods:
                 Intent intent = new Intent(this, GoodsActivity.class);
                 startActivity(intent);
                 break;
            case R.id.floatingActionButton:
                 Intent intent2 = new Intent(MainActivity.this, AddSaleActivity.class);
                 startActivity(intent2);
                 break;
            case R.id.button_partners:
                Intent intent3 = new Intent(MainActivity.this, PartnerActivity.class);
                startActivity(intent3);
                break;
            case R.id.buttonReceiving:
                Intent intent4 = new Intent(MainActivity.this, ReceivingActivity.class);
                startActivity(intent4);
                break;
            case R.id.button_sale:
                Intent intent5 = new Intent(MainActivity.this, SaleActivity.class);
                startActivity(intent5);
                break;
        }
    }
}
