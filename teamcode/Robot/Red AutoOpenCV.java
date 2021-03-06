/*
 * Copyright (c) 2020 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "Red Auto OpenCV", group = "Auto")
public class Red_AutonOpenCV extends LinearOpMode {
    /* ~~ ~~ */
    OpenCvCamera webcam;
    org.firstinspires.ftc.teamcode.RedPipeline pipeline;
    int stackRings = 0;
    /* ~~ ~~ */

    /* Declare OpMode members. */
    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {


        robot.init(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        pipeline = new org.firstinspires.ftc.teamcode.RedPipeline();
        webcam.setPipeline(pipeline);

        // We set the viewport policy to optimized view so the preview doesn't appear 90 deg
        // out when the RC activity is in portrait. We do our actual image processing assuming
        // landscape orientation, though.
//        webcam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(640, 480, OpenCvCameraRotation.UPSIDE_DOWN);//// was 320 240
            }
        });

        // By calling !isStarted in a while loop, we can constantly update our analysis, which means we will save
        // the ring configuration when the driver presses the start button
        while (!isStarted() && !isStopRequested()) {
            telemetry.addData("Analysis", pipeline.getAnalysis());
            telemetry.addData("Randomization", pipeline.getRandomization());
            telemetry.update();
        }
        // We store the randomization immediately when the driver presses start.
        stackRings = pipeline.getRandomization();


        // This is just for this sample opmode, your actual auto code would be below this.
        while (opModeIsActive()) {




            if (pipeline.getRandomization() == 4) {



                // robot.wobbleLaunch();////   MAKE SURE ARM IS IN THE RIGHT SPOT


                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////////////////////   start movement   ////////////////////////////////////////////////
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                robot.right(0.5);/////// 24 to the wall
                sleep(1900);
                robot.stop();

                robot.backward(0.3);///square against wall
                sleep(300);
                robot.stop();

                robot.left(0.5);///square against wall
                sleep(200);
                robot.stop();

                robot.forward(0.75);//////54 up to line to shoot
                sleep(1250);///was 1600
                robot.forward(0.25);
                sleep(200);
                robot.stop();

                robot.elbow.setPosition(0.7);//// get out of the way
                sleep(500);

                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////////////////////   Spin Up Shooter   ////////////////////////////////////////////////
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                robot.shooter.setVelocity(1650);

                robot.right(0.5);///square against wall
                sleep(600);
                robot.stop();

                robot.elevator.setPower(-1);// make elevator go up
                boolean done = false;
                while (!done && opModeIsActive() && !isStopRequested()) {
                    if (robot.elevator.getCurrentPosition() < -1175) {  ////////////////////////// change encoder value
                        done = true;
                    }
                    telemetry.addData("Encoder Value is: ", robot.elevator.getCurrentPosition());
                    telemetry.update();
                }
                robot.elevator.setPower(-0.2);
                robot.stop();


                robot.lightsaber.setPosition(0.65);
                sleep(300);
                robot.elevator.setPower(0);

                robot.left(0.5);/////// 28 to get in line with the rings
                sleep(1900);
                robot.stop();

                robot.elbow.setPosition(0.7);//// get out of the way
                sleep(500);

                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////////////////////   Flick   /////////////////////////////////////////////////////////////
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                //////shoot 1
                flick();

                //////shoot 2
                flick();

                //////shoot 3
                flick();

                robot.shooter.setVelocity(0);

                // robot.shoulder.setPosition(0);//// move shoulder

                robot.right(0.5);/////// 28 BACK TO WALL
                sleep(2150);
                robot.stop();

                robot.left(0.5);/////// GET OFF WALL
                sleep(100);
                robot.stop();

                robot.elbow.setPosition(0.7);
               // robot.wrist.setPosition(0.65);

                robot.forward(0.75);//////42
                sleep(1400);///162
                robot.stop();


                robot.elbow.setPosition(0.7);
                sleep(500);

                robot.hand.setPosition(1);
                sleep(500);

                robot.backward(0.75);//////42
                sleep(900);///162
                robot.stop();

                sleep(50000);


                telemetry.update();
            }

            if (pipeline.getRandomization() == 1) {


                //robot.wobbleLaunch();////   MAKE SURE ARM IS IN THE RIGHT SPOT

                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////////////////////   start movement   ////////////////////////////////////////////////
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                robot.right(0.5);/////// 24
                sleep(2000);
                robot.stop();

                robot.backward(0.3);///square against wall
                sleep(300);
                robot.stop();

                robot.left(0.5);///get off wall
                sleep(200);
                robot.stop();

                robot.forward(0.75);//////54
                sleep(1250);///was 1600
                robot.forward(0.25);
                sleep(200);
                robot.stop();

                robot.elbow.setPosition(0.7);//// get out of the way
                sleep(500);

                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////////////////////   Spin Up Shooter   ////////////////////////////////////////////////
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                robot.shooter.setVelocity(1650);

                robot.right(0.5);///square against wall
                sleep(600);
                robot.stop();

                robot.elevator.setPower(-1);
                boolean done = false;
                while (!done && opModeIsActive() && !isStopRequested()) {
                    if (robot.elevator.getCurrentPosition() < -1175) {  ////////////////////////// change encoder value
                        done = true;
                    }
                    telemetry.addData("Encoder Value is: ", robot.elevator.getCurrentPosition());
                    telemetry.update();
                }
                robot.elevator.setPower(-0.2);
                robot.stop();


                robot.lightsaber.setPosition(0.65);
                sleep(300);
                robot.elevator.setPower(0);

                robot.left(0.5);/////// 28
                sleep(2000);
                robot.stop();

               // robot.wobbleLaunch();////   MAKE SURE ARM IS IN THE RIGHT SPOT
                robot.elbow.setPosition(0.7);
                sleep(500);

                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////////////////////   Flick   ////////////////////////////////////////////////
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                //////shoot 1
                flick();

                //////shoot 2
                flick();

                //////shoot 3
                flick();

                robot.shooter.setVelocity(0);

                //robot.shoulder.setPosition(0);//// move shoulder

                robot.elbow.setPosition(0.7);
               // robot.wrist.setPosition(0.65);

                robot.left(0.5);///move to wobble position
                sleep(300);// was 1200
                robot.stop();
                sleep(50);

                // robot.rotate(-4, 0.3);//////////////// positive is left ///// negative is right

                robot.forward(0.5);///move to wobble position
                sleep(1100);
                robot.stop();

                robot.elbow.setPosition(0.7);


                robot.hand.setPosition(1);
                sleep(1000);

                robot.backward(0.75);///move to wobble position
                sleep(450);
                robot.stop();



//                    ////////////////////////////////////////////////////////////////////////////////////////////////////
//                    //////////////////////////////////// TEST///////////////////////////////////////////////////////////
//                    //////////////////////////////////////////////////////////////////////////////////////////////////////
//
//                    robot.lightsaber.setPosition(0);
//                    sleep(300);
//
//                    boolean down = false;
//                    robot.elevator.setPower(1);// make elevator go down
//                    while (!down && opModeIsActive() && !isStopRequested()) {
//                        if (robot.elevator.getCurrentPosition() > -20) {  ////////////////////////// change encoder value
//                            down = true;
//                        }
//                        telemetry.addData("Encoder Value is: ", robot.elevator.getCurrentPosition());
//                        telemetry.update();
//                    }
//                    robot.elevator.setPower(0.2);
//                    robot.stop();
//
//                    robot.elevator.setPower(0);
//
//                    /// intake on
//                    robot.intake.setPower(-1);
//
//                    ///// back 29 in
//                    robot.backward(0.5);///move to ring pickup
//                    sleep(2117);
//                    robot.stop();
//
//                    //////sleep for intake
//                    sleep(500);
//
//                    /// intake off
//                    robot.intake.setPower(-1);
//
//                    // elv up
//                    robot.elevator.setPower(-1);
//                    done = false;
//                    while (!done && opModeIsActive() && !isStopRequested()) {
//                        if (robot.elevator.getCurrentPosition() < -1175) {  ////////////////////////// change encoder value
//                            done = true;
//                        }
//                        telemetry.addData("Encoder Value is: ", robot.elevator.getCurrentPosition());
//                        telemetry.update();
//                    }
//                    robot.elevator.setPower(-0.2);
//                    robot.stop();
//
//
//                    robot.lightsaber.setPosition(0.65);
//                    sleep(300);
//                    robot.elevator.setPower(0);
//
//                    /// arm back
//                    robot.shoulder.setPosition(0.66);
//
//                    // left
//                    robot.left(0.5);///move to wobble  pickup
//                    sleep(900);
//                    robot.stop();
//
//
//                    //// back 19 for wobble
//                    robot.backward(0.5);///move to wobble  pickup
//                    sleep(1387);
//                    robot.stop();



                sleep(50000);


            }

            if (pipeline.getRandomization() == 0) {

                //robot.wobbleLaunch();////   MAKE SURE ARM IS IN THE RIGHT SPOT

                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////////////////////   start movement   ////////////////////////////////////////////////
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                robot.right(0.5);/////// 24
                sleep(2000);
                robot.stop();

                robot.backward(0.3);///square against wall
                sleep(300);
                robot.stop();

                robot.left(0.5);///square against wall
                sleep(200);
                robot.stop();

                robot.forward(0.75);//////54 MOVE UP TO LINE
                sleep(1400);///was 1600
                robot.forward(0.25);
                sleep(200);
                robot.stop();

                robot.elbow.setPosition(0.7);
               // robot.wrist.setPosition(0.65);

                sleep(500);

                robot.elbow.setPosition(0.7);


                robot.hand.setPosition(1);
                sleep(1000);

                robot.backward(0.5);
                sleep(200);
                robot.stop();


                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////////////////////   Spin Up Shooter   ////////////////////////////////////////////////
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                robot.shooter.setVelocity(1650);

                robot.right(0.5);///square against wall
                sleep(600);
                robot.stop();

                robot.elevator.setPower(-1);
                boolean done = false;
                while (!done && opModeIsActive() && !isStopRequested()) {
                    if (robot.elevator.getCurrentPosition() < -1175) {  ////////////////////////// change encoder value
                        done = true;
                    }
                    telemetry.addData("Encoder Value is: ", robot.elevator.getCurrentPosition());
                    telemetry.update();
                }
                robot.elevator.setPower(-0.2);
                robot.stop();


                robot.lightsaber.setPosition(0.65);
                sleep(300);
                robot.elevator.setPower(0);

                //  robot.wobbleLaunch();////   MAKE SURE ARM IS IN THE RIGHT SPOT

                robot.left(0.5);/////// 28
                sleep(1800);
                robot.stop();


                //robot.wobbleLaunch();////   MAKE SURE ARM IS IN THE RIGHT SPOT
                robot.elbow.setPosition(0.7);
                sleep(500);

                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////////////////////   Flick   ////////////////////////////////////////////////
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                //////shoot 1
                flick();

                //////shoot 2
                flick();

                //////shoot 3
                flick();

                robot.shooter.setVelocity(0);

                robot.forward(0.5);
                sleep(700);
                robot.stop();

                sleep(50000);


            }


            telemetry.addData("Analysis", pipeline.getAnalysis());
            telemetry.addData("Randomization", pipeline.getRandomization());
            telemetry.update();

            // Don't burn CPU cycles busy-looping in this sample
            sleep(50);
        }
    }

    private void flick() {
        robot.flick.setPosition(0.33);//in
        sleep(300);
        robot.flick.setPosition(0);
        sleep(300);
    }

}
