package com.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.entityextraction.EntityAnnotation;
import com.google.mlkit.nl.entityextraction.EntityExtractionParams;
import com.google.mlkit.nl.entityextraction.EntityExtractor;
import com.google.mlkit.nl.entityextraction.EntityExtraction;
import com.google.mlkit.nl.entityextraction.EntityExtractorOptions;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.List;

public class Nlp extends ReactContextBaseJavaModule {

    String base64;

    ReactApplicationContext mContext;
    Nlp(ReactApplicationContext context){
        super(context);
        mContext = context;
    }

    @ReactMethod
    protected void toask(String str){
        Toast.makeText(mContext, str, Toast.LENGTH_LONG).show();
    }

    @ReactMethod
    protected void entity(String str){
        EntityExtractor entityExtraction = EntityExtraction
                .getClient(new EntityExtractorOptions.Builder(EntityExtractorOptions.ENGLISH).build());
        entityExtraction
                .downloadModelIfNeeded()
                .onSuccessTask(ignored -> entityExtraction.annotate(new EntityExtractionParams.Builder("43, bombay bazar").build()))
                .addOnCompleteListener(
                        aVoid -> {
                            Log.d("downloaded", "log : downloaded "+aVoid);
                        }
                )
                .addOnFailureListener(
                        exception -> {
                            exception.printStackTrace();
                            Throwable t = exception.getCause();
                            Log.d("exception", "log : "+t);
                        }
                );
//        entityExtraction.annotate(str)
//                .addOnSuccessListener(new OnSuccessListener<List<EntityAnnotation>>() {
//                    @Override
//                    public void onSuccess(List<EntityAnnotation> entityAnnotations) {
//                        Log.d("length", "log : "+entityAnnotations.size());
//                        // Annotation process was successful, you can parse the EntityAnnotations list here.
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("failed", "log : "+e.getMessage());
//                        // Check failure message here.
//                    }
//                });
    }

    @NonNull
    @Override
    public String getName() {
        return "Nlp";
    }
}
