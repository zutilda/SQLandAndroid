package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Parcel;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

public class OutPut extends AppCompatActivity implements View.OnClickListener  {

    Button buttonBackPage;
    Button buttonOutput;
    Connection connection;
    String ConnectionResult;
    EditText searchHat;
    Spinner spinner;

    public static int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_put);
        buttonBackPage =findViewById(R.id.buttonBackPage);
        buttonOutput =findViewById(R.id.buttonOutput);
        searchHat=findViewById(R.id.searchHat);
        spinner =findViewById(R.id.spinner);
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
    public static String encodeImage(Bitmap bitmap) {
        int prevW = 1000;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        int a = bitmap.getHeight();
        int c = bitmap.getWidth();

        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(bytes);
        }
        return "";
    }

    private Bitmap getImgBitmap(String encodedImg) {
        if (!encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return BitmapFactory.decodeResource(OutPut.this.getResources(),
                R.drawable.cap);
    }
    public void GetTable() {

        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();

            if (connection != null) {
                String query = "Select * From Hat WHERE title LIKE '%" + searchHat.getText().toString() + "%'";
                switch (spinner.getSelectedItemPosition()) {
                    case 0:
                        break;
                    case 1:
                        query += " ORDER BY title DESC";
                        break;
                    case 2:
                        query += " ORDER BY title ASC";
                        break;

                }
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                TableLayout tableOutput = findViewById(R.id.tableOutput);

                tableOutput.removeAllViews();
                while (resultSet.next()) {
                    TableRow outputROW = new TableRow(this);
                    outputROW.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    TableRow.LayoutParams params = new TableRow.LayoutParams(200, 200);



                    Button buttonDelete = new Button(this);
                    buttonDelete.setOnClickListener(this);

                    TextView ID = new TextView(this);
                    params.weight = 1.0f;
                    ID.setLayoutParams(params);
                    ID.setText(resultSet.getString(1));
                    outputROW.addView(ID);

                    ImageView imageView = new ImageView(this);
                    params.weight = 1.0f;
                    imageView.setLayoutParams(params);
                    imageView.setImageBitmap(getImgBitmap(resultSet.getString(5)));
                    outputROW.addView(imageView);

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

                    buttonDelete.setText("del");
                    buttonDelete.setId(Integer.parseInt(resultSet.getString(1)));
                    buttonDelete.setOnClickListener((view -> {
                        DeleteRow(buttonDelete.getId());
                    }));

                    outputROW.addView(buttonDelete);
                    outputROW.setId(Integer.parseInt(resultSet.getString(1)));
                    outputROW.setOnClickListener((view -> {

                        id = outputROW.getId();
                        startActivity(new Intent(OutPut.this, MainActivity.class));

                    }));

                    tableOutput.addView(outputROW);
                }
            } else {
                ConnectionResult = "Check Connection";
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Ошибка подключения!", Toast.LENGTH_LONG).show();
        }
    }
    public  void DeleteRow(int ID)
    {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();

            connection = connectionHelper.connectionClass();
            if (connection != null) {
                String query = "DELETE FROM Hat WHERE id = " + ID;
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);

                Toast.makeText(this, "Товар удалён", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Проверьте подключение!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Ошибка при удалении!", Toast.LENGTH_LONG).show();
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