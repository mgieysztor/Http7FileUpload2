package com.sdacademy.gieysztor.michal.http7fileupload2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import okhttp3.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {
    Button mUploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUploadButton = (Button) findViewById(R.id.upload_button);

        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();
            }
        });


    }

    private void selectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        uploadFile(uri);
        Log.i("TEST", "uri "+uri);
    }
    private void uploadFile(Uri uri) {
        Request.Builder builder = new Request.Builder();
        builder.url("https://content.dropboxapi.com/2/files/upload");
        builder.addHeader("Authorization", "Bearer dDMgJ7xRgDAAAAAAAAAAFlo5Xr4cFa7E8x1P-sz3C7H6acYCupXe2d8TSWobo3g9");
        builder.addHeader("Dropbox-API-Arg", "{\"path\":\"/pliki/keyboard.jpg\"}");
        builder.post(new StreamRequestBody(getApplicationContext(), uri));
        Request request = builder.build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TEST", "fail", e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("TEST", "onResponse "+ response.body().string());
            }
        });
    }
}
