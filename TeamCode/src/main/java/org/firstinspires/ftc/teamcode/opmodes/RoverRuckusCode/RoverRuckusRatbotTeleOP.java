package org.firstinspires.ftc.teamcode.opmodes.RoverRuckusCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.libraries.hardware.RoverRuckusRatbotHardware;

import java.text.DecimalFormat;

/**
 * Created by bremm on 10/26/18.
 */

@TeleOp(name="Rover Ruckus TeleOp")



public class RoverRuckusRatbotTeleOP extends OpMode{
    RoverRuckusRatbotHardware robot =  new RoverRuckusRatbotHardware();

    double left, right;
    double speedFactor = 0.5;
    double liftSpeed = 1;
    DecimalFormat printFormat = new DecimalFormat ("#.###");

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad1.left_bumper && !gamepad1.right_bumper){
            speedFactor = 1; } //Fast Speed (Left Bumper, Gamepad 1)
        else if(gamepad1.right_bumper && !gamepad1.left_bumper){
            speedFactor = 0.25; } //Slow Speed (Right Bumper, Gamepad 1)
        else if(!gamepad1.right_bumper && !gamepad1.left_bumper){
            speedFactor = 0.5; } //Medium Speed (Default Mode (No Bumpers), Gamepad 1)

        if(gamepad2.left_bumper && !gamepad2.right_bumper){
            robot.lift.setPower(liftSpeed); } //Raise LIft (Left Bumper, Gamepad 2)
        else if(gamepad2.right_bumper && !gamepad2.left_bumper){
            robot.lift.setPower(-1 * liftSpeed); } //Lower Lift (Right Bumper, Gamepad 2)
        else if(!gamepad2.right_bumper && !gamepad2.left_bumper){
            robot.lift.setPower(0); }


        left = (-1) * Math.pow(gamepad1.left_stick_y, 3) * speedFactor;
        right = (-1) * Math.pow(gamepad1.right_stick_y, 3) * speedFactor;

        robot.fl.setPower(left);
        robot.bl.setPower(left);
        robot.fr.setPower(right);
        robot.br.setPower(right);

        telemetry.addData("Drive Speed: ", printFormat.format(speedFactor));
        telemetry.addData("Left: ", printFormat.format(left));
        telemetry.addData("Right: ", printFormat.format(right));
        telemetry.addData("Lift Motor: ", printFormat.format(robot.lift.getPower()));

    }
}
