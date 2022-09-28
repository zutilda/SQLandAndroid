package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText txtTitle,txtColor,txtCost;
    Connection connection;
    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTitle =findViewById(R.id.txtTitle);
        txtColor =findViewById(R.id.txtColor);
        txtCost =findViewById(R.id.txtCost);
        buttonAdd =findViewById(R.id.buttonAdd);
        EditRow();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.buttonBack:
                startActivity(new Intent(this,OutPut.class));
                break;
            case R.id.buttonAdd:
                AddRow();
                break;
        }
    }

    public void AddRow() {
        try {

            ConnectionHelper ConnectionHelper = new ConnectionHelper();
            connection = ConnectionHelper.connectionClass();

            if (connection != null) {
                String query = "INSERT INTO Hat(title,color,cost) VALUES('"+(txtTitle.getText().toString())+"','"+ txtColor.getText().toString()+"','"+txtCost.getText().toString()+"');";
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);

                Toast.makeText(this, "Запись добавлена", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Проверьте подключение", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Ошибка!", Toast.LENGTH_LONG).show();
        }
    }
    public void EditRow() {

           try {

            ConnectionHelper ConnectionHelper = new ConnectionHelper();
            connection = ConnectionHelper.connectionClass();

            if (connection != null) {
                String query = "UPDATE Hat SET title='"+(txtTitle.getText().toString())+"', color='"+ txtColor.getText().toString()+"', cost='"+txtCost.getText().toString()+"' WHERE id= ;";
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);

                Toast.makeText(this, "Запись изменена", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Проверьте подключение", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Ошибка!", Toast.LENGTH_LONG).show();
        }
    }
}