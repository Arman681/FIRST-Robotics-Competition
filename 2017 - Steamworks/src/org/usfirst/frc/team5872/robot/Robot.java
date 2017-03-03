package org.usfirst.frc.team5872.robot;

import com.kauailabs.navx.frc.AHRS;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team5872.robot.commands.ExampleCommand;
import org.usfirst.frc.team5872.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	
	private VisionThread visionThread;
	private double centerX = 0.0;
	
	
	private final Object imgLock = new Object();

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();

    Command autonomousCommand;
    SendableChooser chooser = new SendableChooser();
    
    //Motor Controller Declarations
    static CANTalon fl;
	static CANTalon bl;
	static CANTalon fr;
	static CANTalon br;
	static CANTalon shooter;
	static CANTalon mixer;
	static CANTalon intake;
	static CANTalon outtake;
	static CANTalon lock;
    
	
    //Essential Declarations
    static RobotDrive myRobot;
    static Joystick stick;
    static Joystick stick2;
    static Timer timer;
    
    //Sensor Declarations and Variables
    
    static AHRS ahrs;
    static PIDController turnController;
    static double rotateToAngleRate;
    static double Kp = 0.03;
    
    //Counters
    static int ci = 0;			//Intake (Clockwise) Counter
    static int cj = 0;			//Outtake (Counterclockwise) Counter
    static int cs = 0;			//Shooter Counter
    static int lc = 0;    		//Lifter Clockwise Counter
    static int lcc = 0;		//Lifter Counterclockwise Counter
	static int gyroCnt = 0;	//Gyroscope Counter
	
	static final double kP = 0.3;
	//static final double kI = 0.0;
	

    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	@Override
    public void robotInit() {
    	
        chooser.addDefault("Auto Gear With Shot", new Auto1());
        chooser.addObject("Do Nothing", new AutoDoNothing());
        chooser.addObject("Middle Gear No Shot", new AutoMiddleGear());
        chooser.addObject("Drive to White Line", new AutoWhiteLine());
        SmartDashboard.putData("Auto mode", chooser);
        
        //Camera Initialization
        UsbCamera cameragear = CameraServer.getInstance().startAutomaticCapture();
        UsbCamera camerashooter = CameraServer.getInstance().startAutomaticCapture();
        cameragear.setResolution(IMG_WIDTH, IMG_HEIGHT);
        camerashooter.setResolution(IMG_WIDTH, IMG_HEIGHT);
        
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
        intake = new CANTalon(20);
        lock = new CANTalon(7);
        outtake = new CANTalon(8);
        
        
        fr.setInverted(true);
        br.setInverted(true);
        intake.setInverted(true);
        outtake.setInverted(true);
        
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
        ahrs = new AHRS(I2C.Port.kMXP);
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    @Override
    public void disabledInit(){
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
        autonomousCommand = (Command) chooser.getSelected();
        
		String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
        
        timer.reset(); //Resets the timer to 0
        ahrs.reset();
        timer.start(); //Start counting
        autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     * @throws InterruptedException 
     */
    @Override
    public void autonomousPeriodic(){
    	
        Scheduler.getInstance().run();
        
        	
        	
        /*Drive for 2 seconds
        if (timer.get() < 2.0)
             myRobot.drive(-0.5,  0); //drive forwards half speed
        else 
             myRobot.drive(0.0, 0.0); //stop robot*/
    }
    
    
    @Override
    public void teleopInit() {
    	
		//This makes sure that the autonomous stops running when
        //teleop starts running. If you want the autonomous to 
        //continue until interrupted by another command, remove
        //this line or comment it out.
        if (autonomousCommand != null) 
        	autonomousCommand.cancel();
        ahrs.reset();
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
    		//Controller Axes
    		double dl = stick.getRawAxis(1);			//Left Joystick
    		double dr = -stick.getRawAxis(4);			//Right Joystick
    		//Controller Buttons
    		boolean da = stick.getRawButton(1);		//Driver Button a
    		boolean db = stick.getRawButton(2);		//Driver Button b
    		boolean dx = stick.getRawButton(3);		//Driver Button x
    		boolean dy = stick.getRawButton(4);		//Driver Button y
    		boolean dlb = stick.getRawButton(5);	//Driver Left Bumper
    		boolean drb = stick.getRawButton(6);	//Driver Right Bumper
    		boolean dback = stick.getRawButton(7);	//Driver Button Back 
    		
    		double dlt = stick.getRawAxis(2);		//Driver Left Trigger
    		double drt = stick.getRawAxis(3);		//Driver Right Trigger
    		
    		//Arcade Drive Joystick Declarations and Assignments
    		//Controller Axes
    		double ol = -stick2.getRawAxis(1);		//Y Axis
    		double or = stick2.getRawAxis(5);		//Z Axis
    		//Controller Buttons
    		boolean oa = stick2.getRawButton(1);	//Operator a
    		boolean ob = stick2.getRawButton(2);	//Operator b
    		boolean ox = stick2.getRawButton(3);	//Operator x
    		boolean oy = stick2.getRawButton(4);	//Operator y
    		boolean olb = stick2.getRawButton(5);	//Operator Left Bumper
    		boolean orb = stick2.getRawButton(6);	//Operator Right Bumper
    		boolean oback = stick2.getRawButton(7);	//Operator Button Back
    		
    		double olt = stick2.getRawAxis(2);		//Operator Left Trigger
    		double ort = stick2.getRawAxis(3);		//Operator Right Trigger
    		
    		//Drive Train
    		if(dl > 0.05 || dl < -0.05 || dr > 0.05 || dr < -0.05 && gyroCnt == 0){
    			fl.set(dl*.945 + -dr);
    			bl.set(dl*.945 + -dr);
    			fr.set(dl + dr);
    			br.set(dl + dr);
    		}
    		//For Lifting Clockwise
    		else if(dy && !db && gyroCnt == 0){
    			fr.set(1.0);
    			br.set(1.0);
    			fl.set(0.0);
    			bl.set(0.0);
    		}
    		//For Lifting Counterclockwise
    		else if(db && !dy && gyroCnt == 0){
    			fr.set(-1.0);
    			br.set(-1.0);
    			fl.set(0.0);
    			bl.set(0.0);
    		}
    		//Stops All Drive Train Motors
    		else if(!dy && !db && dlt < 0.05 && drt < 0.05 && dl < 0.05 && dl > -0.05 && dr < 0.05 && dr > -0.05){   			
    			stopMotors();
    		}
    		//Gyroscope Toggle
    		if(da && gyroCnt == 0){
    			gyroCnt = 1;
    		}
    		else if(!da && gyroCnt == 1){
    			gyroCnt = 2;
    		}
    		else if(da && gyroCnt == 2){
    			gyroCnt = 3;
    		}
    		else if(!da && gyroCnt == 3){
    			gyroCnt = 0;
    			ahrs.reset();
    		}
    		//Lock Clockwise
    		if(dlb && !drb){
    			lock.set(1.0);
    		}
    		else if(!dlb && drb){
    			lock.set(-1.0);
    		}
    		else if(!dlb && !drb){
    			lock.set(0);
    		}
        	//Shooter Toggle
        	if(oa && cs == 0){
        		bangBang(0.4136);
        		cs = 1;
        	}
        	else if (!oa && cs == 1){
        		cs = 2;
        	}
         	else if(oa && cs == 2){
        		shooter.set(0.0);
        		cs = 3;
        	}
        	else if (!oa && cs == 3){
        		cs = 0;
        	}
        	//Door Lock
        	if(or > 0.05 || or < -0.05){
        		outtake.set(or);	
        	}
        	else{
        		outtake.set(0.0);
        	}
        	//Mixer
        	if(ol > 0.05 || ol < -0.05){
        		mixer.set(ol);
        	}
        	else if(ol < 0.05 && ol > -0.05){
        		mixer.set(0);
        	}
        	//Intake Toggle (Operator Left Bumper)
        	if (olb && ci == 0){
        		intake.set(0.5);
        		ci = 1;
        	}
        	else if (!olb && ci == 1) {
        		ci = 2;
        	}
        	else if (olb && ci == 2) {
        		intake.set(0.0);
        		ci = 3;
        	}
        	else if (!olb && ci == 3) {
        		ci = 0;
        	}
        	//Outtake Toggle (Operator Right Bumper)
        	if (orb && cj == 0) {
        		intake.set(-0.5);
        		cj = 1;
        	}
        	else if (!orb && cj == 1) {
        		cj = 2;
        	}
        	else if (orb && cj == 2) {
        		intake.set(0.0);
        		cj = 3;
        	} 
        	else if (!orb && cj == 3) {
        		cj = 0;
        	}
        	//Gyro Tests
            if(dback) {
            	gyroRight(15, 0.5);
            }
            else if(!dback && ahrs.getAngle() >= 90 ) {            
            	gyroRight(0, 0.0);
            }
    	}
    } 
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    public static void gyroStraight(double speed){
        
        double target = ahrs.getAngle();
        
        while(gyroCnt == 2){   	
        	setSpeed((speed + target)/100 , (speed + target)/100);
        }
    }
    public static void gyroRight(int degrees, double speed){
    	if(ahrs.getAngle() < degrees && ahrs.getAngle() == 0){
    		setSpeed(speed,-speed);	
    	}
    	else if(ahrs.getAngle() >= degrees){
    		stopMotors();
    		ahrs.reset();
    	}
    }
    public static void gyrotLeft(int degrees, double speed){	
    	if(ahrs.getAngle() < degrees && ahrs.getAngle() == 0){
    		setSpeed(-speed,speed);
    	}
    	else{
    		stopMotors();
    		ahrs.reset();
    	}
    }
    public static void setSpeed(double left, double right){
    	fl.set(left);
		fr.set(right);
		bl.set(left);
		br.set(right);
    }
    public static void stopMotors(){
    	fl.set(0);
		fr.set(0);
		bl.set(0);
		br.set(0);
    }
    /*public static void resetEncoders(){
    	fl.setEncPosition(0);
    	bl.setEncPosition(0);
    	fr.setEncPosition(0);
    	br.setEncPosition(0);
    }*/
    public static void bangBang(double fTarget){
    	double fVelocityTime = System.nanoTime();
    	double fEncoder = shooter.getEncPosition();
    	double fLastEncoder = 0;
		double fLastVelocityTime = 0;
		double fVelocity = (double)(fEncoder - fLastEncoder)/(fVelocityTime - fLastVelocityTime);
    	
    	if(fVelocity >= fTarget){
    		shooter.set(0.4136);
    	}
    	else if(fVelocity < fTarget){
    		shooter.set(0.4236);
    	}
    	fLastEncoder = fEncoder;
    	fLastVelocityTime = fVelocityTime;
    }
    public static void turnByGyro(double power, int degrees) throws InterruptedException {

        double constantOfDegrees = (2/3);

        int s = -1;
        boolean turnComplete = false;
        double initialPosition = ahrs.getAngle();
        ahrs.reset();

        while (!turnComplete) {
        	
            double currentPosition = ahrs.getAngle();
            double target = initialPosition + (degrees);

            if ((Math.abs(target)) > currentPosition){
            	fl.set(0.3);
            	bl.set(0.3);
            	fr.set(-0.3);
            	br.set(-0.3);
                }
            else
                turnComplete = true;
            
            fl.set(0);
        	bl.set(0);
        	fr.set(0);
        	br.set(0);
        }
    }
    /*public static void encoderDrive(int rightTicks, int leftTicks, double leftPower, double rightPower, double timeout) {
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
    }*/
    public static void delay(int milliseconds){
    	try{
    		Thread.sleep(milliseconds);
    	}
    	catch(Exception e1){
    		e1.printStackTrace();
    	}
    }
    public static void turn(double left,double right)
    {
    	fl.set(left);
    	fr.set(right);
    	bl.set(left);
    	br.set(right);
    }	
    
    public static void runMotor(double speed)
    {
    	fl.set(speed*.945);
    	fr.set(speed);
    	bl.set(speed*.945);
    	br.set(speed);
    }
}

