package org.usfirst.frc.team5872.robot;

import com.kauailabs.navx.frc.AHRS;

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
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();

    Command autonomousCommand;
    SendableChooser chooser;
    
    //Motor Controller Declarations
    CANTalon fl;
	CANTalon bl;
	CANTalon fr;
	CANTalon br;
    CANTalon shooter;
    CANTalon mixer;
    CANTalon intake;
    CANTalon lifter;
	
    //Essential Declarations
    RobotDrive myRobot;
    Joystick stick;
    Joystick stick2;
    Timer timer;
    
    //Sensor Declarations and Variables
    AnalogGyro gyro;
    AHRS ahrs;
    PIDController turnController;
    double rotateToAngleRate;
    double Kp = 0.03;
    
    //Counters
    int ci = 0;			//Intake Clockwise Counter
    int cj = 0;			//Intake Counterclockwise Counter
    int cs = 0;			//Shooter Counter
    int liftCnt = 0;    //Lifter Counter
	int gyroCnt = 0;	//Gyro Counter
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new ExampleCommand());
        //chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", chooser);
        
        //Motor Controller Assignments
        fl = new CANTalon(3);
        bl = new CANTalon(2);
        fr = new CANTalon(1);
        br = new CANTalon(0);
        shooter = new CANTalon(5);
        mixer = new CANTalon(4);
        intake = new CANTalon(20);
        lifter = new CANTalon(7);
        
        fr.setInverted(true);
        br.setInverted(true);
        intake.setInverted(true);
        
        //Initialize encoders
        fl.configEncoderCodesPerRev(1000);
        bl.configEncoderCodesPerRev(1000);
        fr.configEncoderCodesPerRev(1000);
        br.configEncoderCodesPerRev(1000);
        
        //Camera Init
        CameraServer.getInstance().startAutomaticCapture();
        
        //Essential Assignments
        myRobot = new RobotDrive(3, 1, 2, 0);
        stick = new Joystick(0);
        stick2 = new Joystick(1);
        timer = new Timer();
        
        //Sensor Assignments
        gyro = new AnalogGyro(1);
        ahrs = new AHRS(I2C.Port.kMXP);
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
    }
	
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
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
        
		/*String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		}*/
    	
    	//schedule the autonomous command (example)
        if (autonomousCommand != null) 
        	autonomousCommand.start();
        
        timer.reset(); //Resets the timer to 0
        timer.start(); //Start counting
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomous() {
    	
        Scheduler.getInstance().run();
        gyro.reset();
        
        while (isAutonomous()) {
        	
        	double angle = gyro.getAngle(); //get current heading
        	myRobot.drive(-1.0, -angle*Kp); //drive towards heading 0
        	Timer.delay(0.004);        	
        }
        
        myRobot.drive(0.0, 0.0); 
        
        /*Drive for 2 seconds
        if (timer.get() < 2.0)
             myRobot.drive(-0.5,  0); //drive forwards half speed
        else 
             myRobot.drive(0.0, 0.0); //stop robot*/
    }

    public void teleopInit() {
    	
		//This makes sure that the autonomous stops running when
        //teleop starts running. If you want the autonomous to 
        //continue until interrupted by another command, remove
        //this line or comment it out.
        if (autonomousCommand != null) 
        	autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	while(true) {
    		
    		Scheduler.getInstance().run();
    		myRobot.arcadeDrive(stick); //This line drives the robot using the values of the joystick and the motor controllers selected above
        
    		//Tank Drive Logitech Controller Joystick Declarations and Assignments
    		//Controller Axes
    		double l = stick2.getRawAxis(1);			//Left Joystick
    		double r = stick2.getRawAxis(5);			//Right Joystick
    		//Controller Buttons
    		boolean a = stick2.getRawButton(1);		//Button a
    		boolean b = stick2.getRawButton(2);		//Button b
    		boolean x = stick2.getRawButton(3);		//Button x
    		boolean y = stick2.getRawButton(4);		//Button y
    		boolean lb = stick2.getRawButton(5);		//Left Bumper
    		boolean rb = stick2.getRawButton(6);		//Right Bumper
    		boolean back = stick2.getRawButton(7);	//Button Back 
    		
    		double lt = stick2.getRawAxis(2);		//Right Trigger
    		double rt = stick2.getRawAxis(3);		//Left Trigger
    		
    		//Arcade Drive Joystick Declarations and Assignments
    		//Controller Axes
    		double ay = -stick.getRawAxis(1);		//Y Axis
    		double az = stick.getRawAxis(2);		//Z Axis
    		//Controller Buttons
    		boolean a1 = stick.getRawButton(1);
    		boolean a2 = stick.getRawButton(2);
    		boolean a3 = stick.getRawButton(3);
    		boolean a4 = stick.getRawButton(4);
    		boolean a5 = stick.getRawButton(5);
    		boolean a6 = stick.getRawButton(6);
    		boolean a7 = stick.getRawButton(7);
    		boolean a8 = stick.getRawButton(8);
    		boolean a9 = stick.getRawButton(9);
    		boolean a10 = stick.getRawButton(10);
    		boolean a11 = stick.getRawButton(11);
    		boolean a12 = stick.getRawButton(12);
    	
    		//Drive Train
    		if(ay > 0.05 || ay < -0.05 && az < 0.5 && az > -0.5 && gyroCnt == 0){
    			fl.set(ay);
    			bl.set(ay);
    			fr.set(ay);
    			br.set(ay);
    		}
    		else if(ay < 0.05 && ay > -0.05 && az > 0.5 || az < -0.5 && gyroCnt == 0){
    			fl.set(az);
    			bl.set(az);
    			fr.set(-az);
    			br.set(-az);
    		}
    		//Gyro Stuff
    		else if(ay > 0.05 || ay < -0.05 && az < 0.5 && az > -0.5 && gyroCnt == 2){
    			gyroStraight(ay);
    		}
    		else if(ay < 0.05 && ay > -0.05 && az < 0.05 && az > -0.05){   			
    			stopMotors();
    		}
    		//Gyro Toggle
    		if(a11 && gyroCnt == 0){
    			gyroCnt = 1;
    		}
    		else if(!a11 && gyroCnt == 1){
    			gyroCnt = 2;
    		}
    		else if(a11 && gyroCnt == 2){
    			gyroCnt = 3;
    		}
    		else if(!a11 && gyroCnt == 3){
    			gyroCnt = 0;
    			ahrs.reset();
    		}
        	//Lifter
    		if(a1 && liftCnt == 0){
    			liftCnt = 1;
    		}
    		else if(!a1 && liftCnt == 1){
    			lifter.set(0.5);
    			liftCnt = 2;
    		}
    		else if(a1 && liftCnt == 2){
    			liftCnt = 3;
    		}
    		else if(!a1 && liftCnt == 3){
    			lifter.set(0);
    			liftCnt = 0;
    		}
    		
        	//Shooter
        	if(a && cs == 0) {
        		shooter.set(0.5);
        		cs = 1;
        	}
        	else if (!a && cs == 1) {
        		cs = 2;
        	}
        	else if(a && cs == 2) {
        		shooter.set(0.0);
        		cs = 3;
        	}
        	else if (!a && cs == 3) {
        		cs = 0;
        	}
        	
        	//Mixer
        	if(l > 0.05 || l < -0.05){
        		mixer.set(l);
        	}
        	else if(l < 0.05 && l > -0.05){
        		mixer.set(0);
        	}
        	
        	//Intake Toggle Clockwise (Left Bumper)
        	if (lb && ci == 0) {
        		intake.set(0.5);
        		ci = 1;
        	}
        	else if (!lb && ci == 1) {
        		ci = 2;
        	}
        	else if (lb && ci == 2) {
        		intake.set(0.0);
        		ci = 3;
        	}
        	else if (!lb && ci == 3) {
        		ci = 0;
        	}
        	
        	//Outtake Toggle (Right Bumper)
        	if (rb && cj == 0) {
        		intake.set(-0.5);
        		cj = 1;
        	}
        	else if (!rb && cj == 1) {
        		cj = 2;
        	}
        	else if (rb && cj == 2) {
        		intake.set(0.0);
        		cj = 3;
        	}
        	else if (!rb && cj == 3) {
        		cj = 0;
        	}
        	
        	//Gyro Tests
            /*if(back) {
            	gyroRight(90, 0.5);
            }
            else if(!back) {            
            	gyroRight(0, 0.0);
            }
            if(rt > 0.05) {
            	gyroStraight(rt*0.75);
            }*/
    	}
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    public void gyroStraight(double speed){
        
        if(ahrs.getAngle() == 0) {
        setSpeed(speed, speed);
        }
        else if(ahrs.getAngle() > 0) {
        setSpeed(speed*ahrs.getAngle(), speed);
        }
        else if(ahrs.getAngle() < 0) {
        setSpeed(speed, speed*ahrs.getAngle());
        }
    }
    
    public void gyroRight(int degrees, double speed){
    	if(ahrs.getAngle() < degrees && ahrs.getAngle() == 0){
    		setSpeed(speed,-speed);	
    	}
    	else {
    		stopMotors();
    		ahrs.reset();
    	}
    }
    
    public void gyrotLeft(int degrees, double speed){
    	
    	if(ahrs.getAngle() < degrees && ahrs.getAngle() == 0){
    		setSpeed(-speed,speed);
    	}
    	else{
    		stopMotors();
    		ahrs.reset();
    	}
    }
    
    public void setSpeed(double left, double right) {
    	fl.set(left);
		fr.set(right);
		bl.set(left);
		br.set(right);
    }
    
    public void stopMotors() {
    	fl.set(0);
		fr.set(0);
		bl.set(0);
		br.set(0);
    }
    
    public void resetEncoders() {
    	fl.setEncPosition(0);
    	bl.setEncPosition(0);
    	fr.setEncPosition(0);
    	br.setEncPosition(0);
    }
    
    public void encoderDrive(int rightTicks, int leftTicks, double leftPower, double rightPower, double timeout) {
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
    	while(isAutonomous() && !done) {
    		curFrontRight = fr.getEncPosition();
        	curBackRight = br.getEncPosition();
        	curFrontLeft = fl.getEncPosition();
        	curBackLeft = bl.getEncPosition();
        	done = (Math.abs(targetFrontRight - curFrontRight) < 5 || Math.abs(targetBackRight - curBackRight) < 5 || Math.abs(targetFrontLeft - curFrontLeft) < 5 || Math.abs(targetBackLeft - curBackLeft) < 5);
    	}
    	stopMotors();
    }
}

