package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class OutPut extends AppCompatActivity implements View.OnClickListener {

    Button buttonBackPage;
    Button buttonOutput;
    Connection connection;
    String ConnectionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_put);
        buttonBackPage =findViewById(R.id.buttonBackPage);

        buttonOutput =findViewById(R.id.buttonOutput);   }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.buttonBackPage:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.buttonOutput:
                GetTextFormSql();
                break;

        }
    }

    public void GetTextFormSql() {

        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();

            if (connection != null) {
                String query = "Select * From Hat";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                TableLayout tableOutput = findViewById(R.id.tableOutput);

                tableOutput.removeAllViews();
                while (resultSet.next()) {
                    TableRow outputROW = new TableRow(this);
                    outputROW.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                    TextView ID = new TextView(this);
                    params.weight = 1.0f;
                    ID.setLayoutParams(params);
                    ID.setText(resultSet.getString(1));
                    outputROW.addView(ID);

                    TextView Title = new TextView(this);
                    params.weight = 2.0f;
                    Title.setLayoutParams(params);
                    Title.setText(resultSet.getString(2));
                    outputROW.addView(Title);

                    TextView Color = new TextView(this);
                    params.weight = 2.0f;
                    Color.setLayoutParams(params);
                    Color.setText(resultSet.getString(3));
                    outputROW.addView(Color);

                    TextView Cost = new TextView(this);
                    params.weight = 2.0f;
                    Cost.setLayoutParams(params);
                    Cost.setText(resultSet.getString(4));
                    outputROW.addView(Cost);

                    tableOutput.addView(outputROW);
                }
            } else {
                ConnectionResult = "Check Connection";
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Ошибка!", Toast.LENGTH_LONG).show();
        }
    }

}