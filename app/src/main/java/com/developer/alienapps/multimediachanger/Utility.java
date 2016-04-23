package com.developer.alienapps.multimediachanger;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.io.File;
import java.util.Date;

/**
 * Created by AMIT on 21-Apr-16.
 */
public class Utility {
    public static String REMOVE_SOUND_VIDEO = "-y -i %s -vcodec copy -an %s";
    public static String ADD_SOUND_VIDEO = "-y -i %s -i %s -c:v copy -c:a copy %s";
    public  static String EXTRACT_AUDIO_VIDEO = "-y -i %s -vn %s";
    public  static String IMAGE_FROM_VIDEO = "-y -i %s image%d.jpg";
    public static String CLIP_AUDIO = "-y -i %s -strict experimental -ss %s -t %s %s";
    public static String REMOVE_ADD_AUDIO_TO_VIDEO = "-y -i %s -i %s -c:v copy -map 0:v:0 -map 1:a:0 -c:a copy %s";
    public static void setupFfmpeg(Context context) {
        FFmpeg ffmpeg = FFmpeg.getInstance(context);
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                @Override
                public void onStart() {}

                @Override
                public void onFailure() {}

                @Override
                public void onSuccess() {}

                @Override
                public void onFinish() {}
            });
        } catch (FFmpegNotSupportedException e) {
            // Handle if FFmpeg is not supported by device
            Toast.makeText(context,"Your device does not support", Toast.LENGTH_LONG).show();
        }
    }

    public static String getDuration(String path, Context context) {
        MediaPlayer mp = MediaPlayer.create(context, Uri.parse(path));
        int duration = mp.getDuration();
        mp.release();
        return getTimeForTrackFormat(duration);
    }

    public static String getOutputPath() {
       String path =  Environment.getExternalStorageDirectory().getAbsolutePath() + "/alien/";
        File dir = new File(path);
        try{
            if(dir.mkdir()) {
                System.out.println("Directory created");
            } else {
                System.out.println("Directory is not created");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return path;
    }

    public static String generateFilename() {
        return "output" + String.valueOf(new Date().getTime());
    }

    public static String getTimeForTrackFormat(int duration) {
        long seconds = duration / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        String result = "";
        seconds = seconds % 60;
        minutes = minutes % 60;
        hours = hours % 24;
        if (hours < 10) {
            result = "0" + hours + ":";
        } else {
            result = hours + ":";
        }
        if (minutes < 10) {
            result = "0" + minutes + ":";
        } else {
            result = minutes + ":";
        }
        if (seconds < 10) {
            result += "0" + seconds;
        } else {
            result += seconds;
        }
        return result;
    }
}