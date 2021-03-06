package org.firstinspires.ftc.teamcode.opmodes.old2017;

/**
 * Created by Robotics on 3/7/2017.
 */

import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.teamcode.libraries.hardware.BotHardwareOld;

@Autonomous(name="Red Block Auto (Shoot Balls, Defense)", group="Main")
@Disabled
public class BlockAutoRed extends OpMode {
    BotHardwareOld robot = new BotHardwareOld();

    BlockAuto auto = new BlockAuto(this, true);

    @Override
    public void init(){
        auto.init();
    }

    @Override
    public void init_loop(){
        auto.init_loop();
    }

    @Override
    public void start(){
        auto.start();
    }

    @Override
    public void loop(){
        auto.loop();
    }

    @Override
    public void stop(){
        auto.stop();
    }
}

