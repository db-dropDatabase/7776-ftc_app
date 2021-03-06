package org.firstinspires.ftc.teamcode.opmodes.demo;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.libraries.VuforiaBallLib;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Noah on 11/9/2017.
 */

@Autonomous(name="Vuforia Ball Demo", group ="test")
public class VuforiaBallDemo extends VuforiaBallLib {

    @Override
    public void init() {
        initVuforia(true);
        super.startTracking();
    }

    public void init_loop() {
        getBallColor();
    }

    @Override
    public void start() {
        FileOutputStream out = null;
        try{
            out = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/balls.png");
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);

        }
        catch(Exception e) {
            //oops
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void loop() {
        telemetry.addData("Path", Environment.getExternalStorageDirectory().getAbsolutePath() + "/balls.png");
        telemetry.addData("Tracking", isTracking().toString());
        telemetry.addData("Ball Color", getBallColor());
    }

    @Override
    public void stop() {
        super.stopVuforia();
    }
}
