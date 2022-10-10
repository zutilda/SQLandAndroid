package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText txtTitle,txtColor,txtCost;
    Connection connection;
    Button buttonAdd;
    ImageView imageView;
    String Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTitle =findViewById(R.id.txtTitle);
        txtColor =findViewById(R.id.txtColor);
        txtCost =findViewById(R.id.txtCost);
        buttonAdd =findViewById(R.id.buttonAdd);
        imageView =findViewById(R.id.imageView);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImg.launch(intent);
        });
        if (OutPut.id != 0)
        {
            GetTextFormSql();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.buttonBack:
                OutPut.id=0;
                startActivity(new Intent(this,OutPut.class));
                break;
            case R.id.buttonAdd:
                if (OutPut.id != 0)
                {
                    EditRow();
                }
                else
                {
                    AddRow();
                }
                break;
        }
    }
    public final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imageView.setImageBitmap(bitmap);
                    Image = OutPut.encodeImage(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    });

    public void AddRow() {
        try {

            ConnectionHelper ConnectionHelper = new ConnectionHelper();
            connection = ConnectionHelper.connectionClass();

            if (connection != null) {
                String query = "INSERT INTO Hat(title,color,cost, image) VALUES('"+(txtTitle.getText().toString())+"','"+ txtColor.getText().toString()+"','"+txtCost.getText().toString()+"','" +Image+ "');";
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

    private Bitmap getImgBitmap(String encodedImg) {
        if (!encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return BitmapFactory.decodeResource(MainActivity.this.getResources(),
                R.drawable.cap);
    }

    public void GetTextFormSql( ) {
        try {

            ConnectionHelper ConnectionHelper = new ConnectionHelper();
            connection = ConnectionHelper.connectionClass();

            if (connection != null) {
                String query = "Select * FROM Hat WHERE id = "+ OutPut.id;
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    txtTitle.setText(resultSet.getString(2));
                    txtColor.setText(resultSet.getString(3));
                    txtCost.setText(resultSet.getString(4));
                    imageView.setImageBitmap(getImgBitmap(resultSet.getString(5)));
                }
            } else {
                Toast.makeText(this, "Проверьте подключение!", Toast.LENGTH_LONG).show();
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
                String query = "UPDATE Hat SET image = '" +Image+ "', title='"+(txtTitle.getText().toString())+"', color='"+ txtColor.getText().toString()+"', cost='"+txtCost.getText().toString()+"' WHERE id= " + OutPut.id  ;
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);

                Toast.makeText(this, "Запись изменена", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Проверьте подключение!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Ошибка!", Toast.LENGTH_LONG).show();
        }
    }
}