package com.example.temp.english_premier_league;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.example.temp.english_premier_league.svg_decoder.SvgDecoder;
import com.example.temp.english_premier_league.svg_decoder.SvgDrawableTranscoder;
import com.example.temp.english_premier_league.svg_decoder.SvgSoftwareLayerSetter;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by temp on 10/09/2017.
 */

public class Controller {

    public static void displayImage(String cURL, ImageView imageBox, Activity activity)

    {
        String extension= "";
        for(int i = cURL.length()-3; i < cURL.length();i++)
            extension = extension + cURL.charAt(i);

        if(extension.equals("svg"))
        {
            GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder = Glide.with(activity)
                    .using(Glide.buildStreamModelLoader(Uri.class, activity), InputStream.class)
                    .from(Uri.class)
                    .as(SVG.class)
                    .transcode(new SvgDrawableTranscoder(), PictureDrawable.class) .sourceEncoder(new StreamEncoder())
                    .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder())) .decoder(new SvgDecoder())
                    .listener(new SvgSoftwareLayerSetter<Uri>());
            requestBuilder .diskCacheStrategy(DiskCacheStrategy.SOURCE) .placeholder(R.mipmap.ic_launcher_round)
                    .load(Uri.parse(cURL)) .into(imageBox);
        }else
        {
            Picasso.with(activity).load(Uri.parse(cURL)).into(imageBox);

        }

    }

    public static String positionCoder(String rawPosition)
    {
        String positionCode;

        switch (rawPosition)
        {
            case "Keeper":
                positionCode =("GK");
                break;
            case "Right-Back":
                positionCode =("RB");
                break;
            case "Centre-Back":
                positionCode =("CB");
                break;
            case "Left-Back":
                positionCode =("LB");
                break;
            case "Defensive Midfield":
                positionCode =("CDM");
                break;
            case "Central Midfield":
                positionCode =("CM");
                break;
            case "Right Midfield":
                positionCode =("RM");
                break;
            case "Attacking Midfield":
                positionCode =("CAM");
                break;
            case "Left Wing":
                positionCode =("LW");
                break;
            case "Right Wing":
                positionCode =("RW");
                break;
            case "Centre-Forward":
                positionCode =("CF");
                break;
            case "Left Midfield":
                positionCode =("LM");
                break;
            case "Secondary Striker":
                positionCode = "SS";
                break;
            default:
                positionCode =(rawPosition);
                break;


        }


        return positionCode;
    }

    public static ArrayList splitter(String s){

        String date="", time="";
        int part = 1;
        ArrayList mArr = new ArrayList();

        for (int i = 0; i < s.length();i++)
        {
            if(!String.valueOf(s.charAt(i)).equals("T") && part == 1){
                date = date + String.valueOf(s.charAt(i));
            }
            else if (String.valueOf(s.charAt(i)).equals("T"))
                part= 2;

            if(!String.valueOf(s.charAt(i)).equals("T") && part == 2 && !String.valueOf(s.charAt(i)).equals("Z")){
                time = time + String.valueOf(s.charAt(i));
            }


        }
        mArr.add(date);
        mArr.add(time);
        return mArr;
    }

    public static boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }

        return hasImage;
    }
}
