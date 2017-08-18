package org.usfirst.frc.team5872.robot;

import org.usfirst.frc.team5872.robot.subsystems.ExampleSubsystem;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.SensorBase;
import java.lang.Object;
import edu.wpi.first.wpilibj.SPI.Port;



public class Robot extends IterativeRobot {

    private static final int SECONDS_TO_MILLISECONDS = 1000;
    		

    public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();


    Command autonomousCommand;

   

    //Motor Controller Declarations
    static CANTalon fl;
    static CANTalon bl;
    static CANTalon fr;
    static CANTalon br;
    static CANTalon shooter;
    static CANTalon mixer;
    static CANTalon intake;     //pool-noodle
    static CANTalon gear_pivot; //gear picker-upper pivot
    static CANTalon lock;
    
    //Essential Declarations
    static RobotDrive myRobot;
    static Joystick stick;
    static Joystick stick2;
    static Timer timer;
    static boolean intakeDown = false;
    
     //Sensor Declarations and Variables
    
    static ADXRS450_Gyro gyro;

   
    static PIDController turnController;
    static double rotateToAngleRate;

    static double Kp = 0.03;
    
    //Limit Switch Declarations and Variables

    static DigitalInput limitswitch_up;

    static DigitalInput limitswitch_down;

    static int limitcounter_up = 0;

    static int limitcounter_down = 0;
    
    static int ci = 0;          //Intake (Clockwise) Counter

    static int cir = 0;         //Intake (Counter-Clokwise) Counter

    static int cj = 0;          //gear_pivot (Counterclockwise) Counter

    static int cs = 0;          //Shooter Counter

    static int lc = 0;          //Lifter Clockwise Counter

    static int lcc = 0;     //Lifter Counterclockwise Counter

    static int gyroCnt = 0; //Gyroscope Counter

    

    static final double kP = 0.3;

    //static final double kI = 0.0;

    

    /**
     * This function is run when the robot is first started up and should be

     * used for any initialization code.
     */
    @Override
    public void robotInit() {
       

        
        //Camera Initialization
     
        
        
        /*visionThread = new VisionThread(camera, new Pipeline(), pipeline -> {
            if (!pipeline.filterContoursOutput().isEmpty()) {
                Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
                synchronized (imgLock) {
                    centerX = r.x + (r.width / 2);
                }
            }
        });
        visionThread.start();*/
        
        //Motor Controller Assignments
        fl = new CANTalon(3);
        bl = new CANTalon(2);
        fr = new CANTalon(1);
        br = new CANTalon(0);
        shooter = new CANTalon(5);
        mixer = new CANTalon(4);
        intake = new CANTalon(8);
        lock = new CANTalon(7);
        gear_pivot = new CANTalon(20);
       
        fr.setInverted(true);
        br.setInverted(true);
        intake.setInverted(true);
        gear_pivot.setInverted(true);
        
        gyro = new ADXRS450_Gyro();
        
        //Initialize encoders
        /*fl.configEncoderCodesPerRev(1000);
        bl.configEncoderCodesPerRev(1000);
        fr.configEncoderCodesPerRev(1000);
        br.configEncoderCodesPerRev(1000);*/
        shooter.configEncoderCodesPerRev(1000);
        
        //Essential Assignments
        myRobot = new RobotDrive(3, 1, 2, 0);
        stick = new Joystick(0);
        stick2 = new Joystick(1);
        timer = new Timer();
        
        //Sensor Assignment
       
        
        //Limit Switches Assignment(s)
        limitswitch_up = new DigitalInput(0);
        limitswitch_down = new DigitalInput(1);
    }
   
    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    @Override
    public void disabledInit() {
    	
    	gyro.calibrate();

    }
    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }
    /**
     * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
     * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
     * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
     * below the Gyro
     *
     * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
     * or additional comparisons to the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {
        
        
        
      
        autonomousCommand.start();
    }
    /**
     * This function is called periodically during autonomous
     * @throws InterruptedException 
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }
    @Override
    public void teleopInit() {
        
        //This makes sure that the autonomous stops running when
        //teleop starts running. If you want the autonomous to 
        //continue until interrupted by another command, remove
        //this line or comment it out.
        if (autonomousCommand != null){
            autonomousCommand.cancel();
        }
    }
    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        while(true) {
            
            Scheduler.getInstance().run();
            myRobot.arcadeDrive(stick); //This line drives the robot using the values of the joystick and the motor controllers selected above
        
            //Tank Drive Logitech Controller Joystick Declarations and Assignments


            
            //Controller 1 Axes
            double dl = stick.getRawAxis(1);            //Left Joystick
            double dr = -stick.getRawAxis(4);           //Right Joystick
            
            //Controller 1 Buttons
            boolean da = stick.getRawButton(1);     //Driver Button a
            boolean db = stick.getRawButton(2);     //Driver Button b
            boolean dx = stick.getRawButton(3);     //Driver Button x
            boolean dy = stick.getRawButton(4);     //Driver Button y
            boolean dlb = stick.getRawButton(5);    //Driver Left Bumper
            boolean drb = stick.getRawButton(6);    //Driver Right Bumper
            boolean dback = stick.getRawButton(7);  //Driver Button Back 
            
            double dlt = stick.getRawAxis(2);       //Driver Left Trigger
            double drt = stick.getRawAxis(3);       //Driver Right Trigger
            
            //Arcade Drive Joystick Declarations and Assignments
            //Controller 2 Axes
            double ol = -stick2.getRawAxis(1);      //Left Axis
            double or = stick2.getRawAxis(5);       //Right Axis
            
            //Controller 2 Buttons
            boolean oa = stick2.getRawButton(1);    //Operator a
            boolean ob = stick2.getRawButton(2);    //Operator b
            boolean ox = stick2.getRawButton(3);    //Operator x
            boolean oy = stick2.getRawButton(4);    //Operator y
            boolean olb = stick2.getRawButton(5);   //Operator Left Bumper
            boolean orb = stick2.getRawButton(6);   //Operator Right Bumper
            boolean oback = stick2.getRawButton(7); //Operator Button Back
           
            double olt = stick2.getRawAxis(2);      //Operator Left Trigger
            double ort = stick2.getRawAxis(3);      //Operator Right Trigger
            
            //Drive Train
            if(dl > 0.05 || dl < -0.05 || dr > 0.05 || dr < -0.05 && gyroCnt == 0) {
                fl.set(dl*.945 + -dr);
                bl.set(dl*.945 + -dr);
                fr.set(dl + dr);
                br.set(dl + dr);
            }
            //For Lifting Clockwise
            else if(dy && !db && gyroCnt == 0) {
                fr.set(1.0);
                br.set(1.0);
                fl.set(0.0);
                bl.set(0.0);
            }
            //For Lifting Counterclockwise
            else if(db && !dy && gyroCnt == 0) {
                fr.set(-1.0);
                br.set(-1.0);
                fl.set(0.0);
                bl.set(0.0);
            }
            else if(dlt < 0.05 || drt < 0.05){
            	
            	driveStraight(dlt+(-drt));
            	
            }
            //Stops All Drive Train Motors
            else if(!dy && !db && dlt < 0.05 && drt < 0.05 && dl < 0.05 && dl > -0.05 && dr < 0.05 && dr > -0.05) {             
                stopMotors();
            }
            
            //Gyroscope Toggle
            
            
            //Lock Clockwise
            if(dlb && !drb)
                lock.set(1.0);
            else if(!dlb && drb)
                lock.set(-1.0);
            else if(!dlb && !drb)
                lock.set(0);
            
            //Shooter Toggle
            if(oa && cs == 0) {
                bangBang(0.4136);
                cs = 1;
            }
            else if (!oa && cs == 1)
                cs = 2;
            else if(oa && cs == 2) {
                shooter.set(0.0);
                cs = 3;
            }
            else if (!oa && cs == 3)
                cs = 0;
            
            //Gear Pivot (Operator Right Analog Stick)
            if((or > 0.05 || or < -0.05) && limitswitch_up.get() == false) {
                gear_pivot.set(or*1/2);
            }
            else if ((or > 0.05 || or < -0.05) && limitswitch_up.get() == true)
                gear_pivot.set(0.01);
            else
                gear_pivot.set(0.01);
            
            //Mixer (Operator Left Analog Stick)
            if(ol > 0.05 || ol < -0.05)
                mixer.set(ol);
            else if(ol < 0.05 && ol > -0.05)
                mixer.set(0);
            
            //Intake Toggle (Operator Left Bumper)
            if (olb && ci == 0) {
                intake.set(0.5);
                ci = 1;
            }
            else if (!olb && ci == 1)
                ci = 2;
            else if (olb && ci == 2) {
                intake.set(0.0);
                ci = 3;
            }
            else if (!olb && ci == 3)
                ci = 0;
            
            //Intake Toggle (Operator Right Bumper)
            if (orb && cir == 0) {
                intake.set(-0.5);
                cir = 1;
            }
            else if (!orb && cir == 1)
                cir = 2;
            else if (orb && cir == 2) {
                intake.set(0.0);
                cir = 3;
            }
            else if (!orb && cir == 3){
                cir = 0;
            }
            
            /*while(limitswitch_up.get()){
                
                Timer.delay(10);
                
            }*/
            
            /*//Closed Loop for Gear Picker Upper
            if(ox && !intakeDown) {
                gear_pivot.set(-.1);
                delay(1);
                gear_pivot.set(0);
                intakeDown = true;
            }
            if(ob && intakeDown) {
                gear_pivot.set(.1);
                delay(1);
                gear_pivot.set(0);
                intakeDown = false;
            }
            if(oy && !intakeDown) {
                intake.set(-1);
                gear_pivot.set(-.1);
                delay(1);
                gear_pivot.set(0);
                intakeDown = true;
            }*/
            
           //Gyro Tests

            
            //Limit Switch Conditions for going up
            /*if (!limitswitch_up.get() && limitcounter_up == 0 && ox == true){
                limitcounter_up = 1;
            }
            else if (!limitswitch_up.get() && limitcounter_up == 1 && !ox){
                gear_pivot.set(-1.0);
                limitcounter_up = 2;
            }
            else if (limitswitch_up.get() == true && limitcounter_up == 2){         //Instantaneously
                gear_pivot.set(0);
                limitcounter_up = 3;
            }
            else if (!limitswitch_up.get() && limitcounter_up == 2 && ox == true){  //Manually

                gear_pivot.set(0);
                limitcounter_up = 3;

            }

            else if (!limitswitch_up.get() && limitcounter_up == 3 && !ox){
403
                gear_pivot.set(0);
404
                limitcounter_up = 0;
405
            }
406
            else if (limitswitch_up.get() == true && limitcounter_up == 3){
407
                limitcounter_up = 0;
408
            }*/
            /*//Limit Switch Conditions for going down
411
            if (!limitswitch_down.get() && limitcounter_down == 0 && oy == true){
412
                limitcounter_down = 1;
413
            }
414
            else if (!limitswitch_down.get() && limitcounter_down == 1 && !oy){
415
                gear_pivot.set(1.0);
416
                limitcounter_down = 2;
417
            }
418
            else if (limitswitch_down.get() == true && limitcounter_down == 2){ //Instantaneously
419
                gear_pivot.set(0);
420
                limitcounter_down = 3;
421
            }
422
            else if (!limitswitch_down.get() && limitcounter_down == 2 && oy == true){ //Manually
423
                gear_pivot.set(0);
424
                limitcounter_down = 3;
425
            }
426
            else if (!limitswitch_down.get() && limitcounter_down == 3 && !oy){
427
                gear_pivot.set(0);
428
                limitcounter_down = 0;
429
            }
430
            else if (limitswitch_down.get() == true && limitcounter_down == 3){
431
                limitcounter_down = 0;
432
            }   */         
        }
    } 
    /**
436
     * This function is called periodically during test mode
437
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    public static void turnByGyro(){
    	
    	
    	
    }
    public static void driveStraight(double Speed){
    	double angle = gyro.getAngle(); // get current heading
    	
    	fl.set(Speed*(angle*2)*Kp);
    	fr.set(Speed*(angle*2)*Kp);
    	bl.set(Speed*(angle*2)*Kp);
    	br.set(Speed*(angle*2)*Kp);
    	
    }
    public static void setSpeed(double left, double right) {
        fl.set(left);
        fr.set(right);
        bl.set(left);
        br.set(right);
    }
    public static void stopMotors() {
        fl.set(0);
        fr.set(0);
        bl.set(0);
        br.set(0);
    }
    public static void resetEncoders() {
        fl.setEncPosition(0);
        bl.setEncPosition(0);
        fr.setEncPosition(0);
        br.setEncPosition(0);
    }
    public static void bangBang(double fTarget) {
        double fVelocityTime = System.nanoTime();
        double fEncoder = shooter.getEncPosition();
        double fLastEncoder = 0;
        double fLastVelocityTime = 0;
        double fVelocity = (double)(fEncoder - fLastEncoder)/(fVelocityTime - fLastVelocityTime);
        
        if(fVelocity >= fTarget)

            shooter.set(0.4136);

        else if(fVelocity < fTarget)

            shooter.set(0.4236);

        

        fLastEncoder = fEncoder;

        fLastVelocityTime = fVelocityTime;

    }
    public static void encoderDrive(int rightTicks, int leftTicks, double leftPower, double rightPower, double timeout) {

        resetEncoders();

        int targetFrontRight = fr.getEncPosition()+rightTicks;
        int targetBackRight = br.getEncPosition()+rightTicks;
        int targetFrontLeft = fl.getEncPosition()+leftTicks;
        int targetBackLeft = bl.getEncPosition()+leftTicks;
        int curFrontRight = fr.getEncPosition();
        int curBackRight = br.getEncPosition();
        int curFrontLeft = fl.getEncPosition();
        int curBackLeft = bl.getEncPosition();
        boolean done = (Math.abs(targetFrontRight - curFrontRight) < 5 || Math.abs(targetBackRight - curBackRight) < 5 || Math.abs(targetFrontLeft - curFrontLeft) < 5 || Math.abs(targetBackLeft - curBackLeft) < 5);
        setSpeed(leftPower,rightPower);
        while(!done) {
            curFrontRight = fr.getEncPosition();
            curBackRight = br.getEncPosition();
            curFrontLeft = fl.getEncPosition();
            curBackLeft = bl.getEncPosition();

            done = (Math.abs(targetFrontRight - curFrontRight) < 5 || Math.abs(targetBackRight - curBackRight) < 5 || Math.abs(targetFrontLeft - curFrontLeft) < 5 || Math.abs(targetBackLeft - curBackLeft) < 5);

        }
        stopMotors();

    }

    public static void delay(double seconds) {

        try {

            Thread.sleep((long) (seconds*SECONDS_TO_MILLISECONDS)); //Thread.sleep() input is in milliseconds

        }

        catch(Exception e1) {

            e1.printStackTrace();

        }
    }

    public static void turn(double left,double right) {

        fl.set(left);

        fr.set(right);

        bl.set(left);

        br.set(right);

    }   

    public static void runMotor(double speed, double seconds) {

        fl.set(speed*.945);

        fr.set(speed);

        bl.set(speed*.945);

        br.set(speed);

        delay(seconds);

    }

    public static void runMotor(double speedLeft,double speedRight, double seconds) {

        fl.set(speedLeft*.945);

        fr.set(speedRight);

        bl.set(speedLeft*.945);

        br.set(speedRight);

        delay(seconds);

    }

    public static void stopDriveTrain() {

        fl.set(0);
        fr.set(0);
        bl.set(0);
        br.set(0);

    }

}


