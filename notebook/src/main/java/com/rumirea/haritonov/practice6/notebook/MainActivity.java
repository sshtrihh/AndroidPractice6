package com.rumirea.haritonov.practice6.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText editFileName;
    private EditText editFileText;
    private SharedPreferences preferences;
    private final String FILE_NAME = "file_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editFileName = (EditText) findViewById(R.id.editTextFileName);
        editFileText = (EditText) findViewById(R.id.editTextText);
        preferences = getPreferences(MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String fileName = preferences.getString(FILE_NAME, null);
        if (fileName != null) {
            try (FileInputStream fileInputStream = openFileInput(fileName)) {
                byte[] bytes = new byte[fileInputStream.available()];
                fileInputStream.read(bytes);
                String content = new String(bytes);
                editFileName.setText(fileName);
                editFileText.setText(content);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        String fileName = editFileName.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FILE_NAME, fileName);
        editor.apply();
        try (FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(editFileText.getText().toString().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}