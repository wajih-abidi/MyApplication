package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;
    Button addBtn, getBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        addBtn = findViewById(R.id.addTextBtn);
        getBtn = findViewById(R.id.getTextBtn);

        if (!checkExternalStorage())
            addBtn.setEnabled(false);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = editText.getText().toString();
                writeToFile("file.txt", content);
            }
        });

        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileReader fr = null;
                File myExternalFile = new File(getExternalFilesDir("myFileDir"), "file.txt");
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    fr = new FileReader(myExternalFile);
                    BufferedReader br = new BufferedReader(fr);
                    String line = br.readLine();
                    while(line != null) {
                        stringBuilder.append(line).append("\n");
                        line = br.readLine();
                    }
                } catch(Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                } finally {
                    String fileContents = "File contents \n" + stringBuilder.toString();
                    textView.setText(fileContents);
                }
            }
        });
    }

    public void writeToFile(String fileName, String content){
        File myExternalFile = new File(getExternalFilesDir("myFileDir"), fileName);
        try {
            FileOutputStream writer = new FileOutputStream(myExternalFile);
            writer.write(content.getBytes());
            writer.close();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkExternalStorage() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}