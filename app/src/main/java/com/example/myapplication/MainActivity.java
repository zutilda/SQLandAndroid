package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    Connection connection;
    String ConnectionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void GetTextFormSql(View v) {

        TextView ID = findViewById(R.id.txtTitle);
        TextView Title = findViewById(R.id.txtColor);
        TextView Color = findViewById(R.id.txtCost);


        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();

            if (connection != null) {
                String query = "Select + From Masks";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);


                while (resultSet.next()) {

                    ID.setText(resultSet.getString(1));
                    Title.setText(resultSet.getString(2));
                    Color.setText(resultSet.getString(3));

                }
            } else {
                ConnectionResult = "Check Connection";
            }

        } catch (Exception ex) {


        }
    }
}
