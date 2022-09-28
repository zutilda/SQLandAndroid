package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Parcel;
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

public class OutPut extends AppCompatActivity implements View.OnClickListener  {

    Button buttonBackPage;
    Button buttonOutput;
    Connection connection;
    String ConnectionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_put);
        buttonBackPage =findViewById(R.id.buttonBackPage);
        buttonOutput =findViewById(R.id.buttonOutput);
        GetTable();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.buttonBackPage:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.buttonOutput:
                GetTable();
                break;

        }
    }

    public void GetTable() {

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

    /* //открытие
        imageView.setOnClickListener(v -> {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImg.launch(intent);        });


    //отдельный метод для открытия
    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imageView.setImageBitmap(bitmap);
                    encodedImage = encodeImage(bitmap);
                } catch (Exception e) {

                }
            }
        }
    });

    //Из строки в изображение
    private Bitmap getImgBitmap(String encodedImg) {
        if (encodedImg != null) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return BitmapFactory.decodeResource(DeteilsMask.this.getResources(),
                R.drawable.picture);
    }

    //Изображение в строку
    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(bytes);
        }
        return "";
    } */

}