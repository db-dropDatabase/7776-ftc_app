package org.firstinspires.ftc.teamcode.opmodes.pointer;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmodes.RedPilliar;

/**
 * Created by Noah on 12/15/2017.
 */

@Autonomous(name="Red No Block", group="test")
public class RedBallDrive extends RedPilliar {
    public void init(){
        this.justDrive = true;
        this.red = true;
        super.init();
    }
}