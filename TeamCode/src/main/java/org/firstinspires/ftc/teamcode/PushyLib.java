package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Noah on 12/6/2016.
 */

public final class PushyLib {

    //function which handles pushing of beacons
    //assumes robot is in front of the beacon

    public static class pushypushy extends AutoLib.LinearSequence{

        pushypushy(DcMotor[] motors, ColorSensor leftSensor, ColorSensor rightSensor, Servo leftServo, Servo rightServo,
                   double pushPos, double time, boolean red, int colorThresh, float drivePower, float driveTime, int maxDriveLoop){
            //run color detection and pushing
            this.add(new pushyDetect(motors, leftSensor, rightSensor, leftServo, rightServo, pushPos, time, red, colorThresh, drivePower, driveTime, maxDriveLoop));

            //pull servos back to default position
            AutoLib.ConcurrentSequence servoDetract = new AutoLib.ConcurrentSequence();
            servoDetract.add(new AutoLib.TimedServoStep(leftServo, leftServo.getPosition(), time, false));
            servoDetract.add(new AutoLib.TimedServoStep(rightServo, rightServo.getPosition(), time, false));
            this.add(servoDetract);
        }

    }

    private static class pushyDetect extends AutoLib.Step {
        DcMotor[] mMotors;
        ColorSensor mLeftSensor;
        ColorSensor mRightSensor;
        Servo mLeftServo;
        Servo mRightServo;
        final double mPushPos;
        AutoLib.Timer mTime;
        final boolean mRed;
        final int mColorThresh;
        final float mDrivePower;
        final float mDriveTime;
        boolean mLeft;
        int mMaxDriveLoop;

        public pushyDetect(DcMotor[] motors, ColorSensor leftSensor, ColorSensor rightSensor, Servo leftServo, Servo rightServo,
                           double pushPos, double time, boolean red, int colorThresh, float drivePower, float driveTime, int maxDriveLoop){
            //You know what, I think I'm missing some variables
            //oh never mind here they are
            mMotors = motors;
            mLeftSensor = leftSensor;
            mRightSensor = rightSensor;
            mLeftServo = leftServo;
            mRightServo = rightServo;
            mPushPos = pushPos;
            mTime = new AutoLib.Timer(time);
            mRed = red;
            mColorThresh = colorThresh;
            mDrivePower = drivePower;
            mDriveTime = driveTime;
            mMaxDriveLoop = maxDriveLoop;
        }

        public boolean loop(){
            if(firstLoopCall()){
                //compare sensor values
                if(mRed){
                    mLeft = mLeftSensor.red() > mRightSensor.red();
                }
                else{
                    mLeft = mLeftSensor.blue() > mRightSensor.blue();
                }

                //if left side is color, push left, else push right
                if(mLeft) mLeftServo.setPosition(mPushPos);
                else mRightServo.setPosition(mPushPos);

                //start servo timer
                mTime.start();
            }

            if(mTime.done()){
                //check to make sure the pushy worked
                boolean done = false;

                if(mRed){
                    //left side, left sensor
                    if(mLeft && mLeftSensor.red() < mColorThresh) done = true;
                        //right side, right sensor
                    else if(!mLeft && mRightSensor.red() < mColorThresh) done = true;
                }
                else{
                    if(mLeft && mLeftSensor.blue() < mColorThresh) done = true;
                    else if(!mLeft && mRightSensor.blue() < mColorThresh) done = true;
                }

                //if it's done, stop the motors
                if(done || mMaxDriveLoop < 0){
                    for (int i = 0; i < mMotors.length; i++) {
                        mMotors[i].setPower(0);
                    }
                    return false;
                }

                //else, clearly it hasn't, so we run the smash-n-push <code></code>

                mMaxDriveLoop--;

                for (int i = 0; i < mMotors.length; i++) {
                    mMotors[i].setPower(mDrivePower);
                }

                mTime = new AutoLib.Timer(mDriveTime);
                mTime.start();
            }
            return false;
        }
    }
}