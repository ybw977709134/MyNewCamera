package drawable.anyan_client_demo.tmp;

import android.media.MediaRecorder;

import java.io.IOException;

/**
 * Created by flin on 2015/10/10.
 */
public class MediaRecorderClass {
    private static MediaRecorder mMediaRecorder;

    public static void start() {
        mMediaRecorder = new MediaRecorder();
        /* setAudioSource/setVedioSource*/
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置麦克风
        /* 设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default
         * THREE_GPP(3gp格式，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
        */
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        /* 设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default */
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        try{
            mMediaRecorder.prepare();
            mMediaRecorder.start();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static void stop() {
        if (mMediaRecorder != null) {
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }

}
