package org.usfirst.frc.team5872.robot;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDriveIII;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends SampleRobot {
	public static final Subsystem exampleSubsystem = null;
	RobotDriveIII myRobot;
	int autoLoopCounter;
	
	Joystick stick;
	Joystick stick2;
	
	Spark fl;
	Spark fr;
	Spark bl;
	Spark br;
	Spark outtake;
	Spark shooter;
	Spark intake;
	Spark winch;
	
	DoubleSolenoid dogear;
	DoubleSolenoid shooterextend;
	DoubleSolenoid manipulator;
	DoubleSolenoid blank;
	DoubleSolenoid hook_shoot;
	DoubleSolenoid hook_rotate;
	Compressor c;
	Compressor c2;
	
	//DigitalInput sensor;
	DigitalInput lSwitch;
	CameraServer server;

	Value off = DoubleSolenoid.Value.kOff;
	Value forward = DoubleSolenoid.Value.kForward;
	Value reverse = DoubleSolenoid.Value.kReverse;
	
	int gboxCnt = 0; //starts in high gear
	int shooterCnt = 0; //starts in lowered position
	int maniCnt = 0;//starts in raised position
	int highCnt = 0;
	int medCnt = 0;
	int lowCnt = 0;
	int outCnt = 0;
	int inCnt = 0;
	int lCnt = 0;
	int hookCnt = 0;
	boolean allow = true;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	    	
    	dogear = new DoubleSolenoid(2, 6);
    	shooterextend  = new DoubleSolenoid(5, 1);
    	manipulator = new DoubleSolenoid(7, 3);
    	hook_rotate = new DoubleSolenoid(1, 1, 0);
    	hook_shoot = new DoubleSolenoid(4, 0);
    	
    	stick = new Joystick(0);//Driver
    	stick2 = new Joystick(1);//Operator
    	
    	fl = new Spark(9);
    	fr = new Spark(4);
    	bl = new Spark(2);
    	br = new Spark(5);
    	outtake = new Spark(1);
    	shooter  = new Spark(7);
    	intake = new Spark(8);
    	winch = new Spark(0);
    	
    	bl.setInverted(true);
    	fl.setInverted(true);
    	intake.setInverted(false);
    	outtake.setInverted(true);
    	
    	//sensor=  new DigitalInput(0);
    	lSwitch = new DigitalInput(1);
    	shooterextend.set(off);
    	dogear.set(reverse);//starts low gear
    	manipulator.set(off);
    	hook_rotate.set(reverse);
    	hook_shoot.set(reverse);    	

    	c = new Compressor(0);
    	c2 = new Compressor(1);
    	boolean enabled = c2.enabled();
    	boolean enabled1 = c.enabled();
    	boolean pressureSwitch = c.getPressureSwitchValue();
    	boolean pressureSwitch1 = c.getPressureSwitchValue();
    	//float current = c.getCompressorCurrent();		//No longer works with the 2017 wpilib plugins
    	//float current1 = c.getCompressorCurrent();	//No longer works with the 2017 wpilib plugins
    	c.setClosedLoopControl(true);
    	c2.setClosedLoopControl(true);
    	
    	server = CameraServer.getInstance();
        //server.setQuality(50);						//No longer works with the 2017 wpilib plugins
        //server.startAutomaticCapture("cam0");			//No longer works with the 2017 wpilib plugins
        
        //Counter counter = new Counter(limitSwitch);    	
    	
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomous() {
    	bl.setInverted(true);
    	fl.setInverted(true);
    	fr.setInverted(false);
    	br.setInverted(false);
    	
		//Low bar/ porticulis
	    runMotor(0);
		manipulator.set(reverse);
		delay(7000);
		runMotor(-.75);
		manipulator.set(reverse);
		delay(750);
		runMotor(0);
		delay(750);
		runMotor(-.5);
		delay(3125);	
		runMotor(0);
		manipulator.set(reverse);
		delay(3000);
		
    	//shoot ball
    	
    	/*shooterextend.set(forward);
    	delay(2000);
    	manipulator.set(forward);
    	shooter.set(1);
    	delay(3000);
    	outtake.set(-.7);
    	delay(900);
    	outtake.set(0);
    	delay(500);
    	outtake.set(1);
    	delay(2000);
    	outtake.set(0);
    	shooter.set(0);
    	delay(4750);
    	shooterextend.set(reverse);
    	*/
    	//Rock Wall / Moat /Rough Terrain / ramparts
    	/*
    	manipulator.set(forward);
    	runMotor(-.75);
    	delay(4500);
    	manipulator.set(forward);
    	runMotor(0);
    	delay(10250);
    	*/
    	
    	//chival de frise
    	/*
    	manipulator.set(forward);
    	runMotor(-.5);
    	delay(2000);
    	runMotor(0);
    	manipulator.set(reverse);
    	delay(2000);
    	runMotor(-.75);
    	delay(1000);
    	manipulator.set(forward);
    	delay(5000);
    	runMotor(0);
    	delay(4750);
    	
    	*/
    	
 	    }
    
    public void delay(int milliseconds){
    	try{
    		Thread.sleep(milliseconds);
    	}
    	catch(Exception e1){
    		e1.printStackTrace();
    	}
    }
    public void runMotor(double speed)
    {
    	fl.set(speed);
    	fr.set(speed);
    	bl.set(speed);
    	br.set(speed);
    }
    public void runMotor(double left,double right)
    {
    	fl.set(left);
    	fr.set(right);
    	bl.set(left);
    	br.set(right);
    }	
    
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    }

    /**
     * This function is called periodically during operator control
     */
    public void operatorControl() {
    	while(true)
    	{
    	//Driver controller
    	double l = stick.getRawAxis(1);
    	double r = stick.getRawAxis(5);
    	double tl = stick.getRawAxis(2);
    	double tr = stick.getRawAxis(3);
    	boolean a = stick.getRawButton(1);
    	boolean b = stick.getRawButton(2);
    	boolean x = stick.getRawButton(3);
    	//boolean y = stick.getRawButton(4);
    	boolean bul = stick.getRawButton(5);
    	boolean bur = stick.getRawButton(6);
    	//Operator controller
    	double or = stick2.getRawAxis(5);//intake
    	boolean oa = stick2.getRawButton(1);//low shot
    	boolean ob = stick2.getRawButton(2);//med shot
    	boolean ox = stick2.getRawButton(3);//full intake
    	boolean oy = stick2.getRawButton(4);//highshot
    	boolean obr = stick2.getRawButton(6);//raise shooter
    	double otr = stick2.getRawAxis(6);// lower shooter
    	double otl = stick2.getRawAxis(2);
    	boolean obl = stick2.getRawButton(5);//rotate shoooter
    	boolean test = stick2.getRawButton(8);
    	//Loop control Variables
    	
    	
    	//Drive train
    	if(tl == 0 && tr == 0 && !bur && !bul)// tank drive - squared scaling
    	{	
    		// left analog stick
    		if(l > .05)
	    	{
	    		fl.set(l*l);
	    		bl.set(l*l);
	    	}
	    	else if(l < -.05)
	    	{
	    		fl.set(-1*l*l);
	    		bl.set(-1*l*l);
	    	}
	    	else
	    	{
	    		fl.set(0);
	    		bl.set(0);
	    	}
    		
    		// right analog stick
	    	if(r > .05)
	    	{
	    		fr.set(r*r);
	    		br.set(r*r);
	    	}
	    	else if(r < -.05)
	    	{
	    		fr.set(-1*r*r);
	    		br.set(-1*r*r);
	    	}
	    	else
	    	{
	    		fr.set(0);
	    		br.set(0);
	    	}
    	}
    
	   	else if(tl == 0 && tr != 0 && !bul)//Go forward at value tr uniformly
	   	{
	   		fr.set(tr);
	   		br.set(tr);
	   		fl.set(tr);
	   		bl.set(tr);
	   	}
	   	else if(tr == 0 && tl != 0 && !bul)//Go backward at value tl uniformly
	   	{
	   		fr.set(-tl);
	   		br.set(-tl);
	   		fl.set(-tl);
	   		bl.set(-tl);
	   	}
	   	else if(tr != 0 && tl == 0 && bul)//Rotate Clockwise uniformly at full speed
	   	{
	   		fr.set(-tr);
	   		br.set(-tr);
	   		fl.set(tr);
	   		bl.set(tr);
	   	}
	   	else if(tr == 0 && tl != 0 && bul)//Rotate CounterClorkwise uniformly at full speed
	   	{
	   		fr.set(tl);
	   		br.set(tl);
	   		fl.set(-tl);
	   		bl.set(-tl);
	   	}
    
    
    	// outtake
    	if( -.05 < or && or < .05)
    	{
    		if(outCnt == 0 && !ox)
    		{
    			outtake.set(0);
    		}
    		else if(outCnt == 0 && ox)
    		{
    			outCnt++;
    			outtake.set(1);
    			intake.set(.75);
    		}
    		else if(outCnt == 1 &&  !ox)
    		{
    			outCnt++;
    		}
    		else if(outCnt == 2 && ox)
    		{
    			outCnt++;
    			outtake.set(0);
    			intake.set(.75);
    		}
    		else if(outCnt == 3 && !ox)
    		{
    			outCnt = 0;
    		}
    	  }
    	else
    	{
    		outtake.set(-1*or*or*or);
    		outCnt = 0;
    	}
    	
    	
    	//Shooter
    	if(highCnt == 0 && oy)
    	{
    		shooter.set(1);
    		lowCnt = 0;
    		medCnt = 0;
    		highCnt++;
    	}
    	else if(highCnt == 1 && !oy)
    	{
    		highCnt++;
    	}
    	else if(highCnt == 2 && oy)
    	{
    		shooter.set(0);
    		lowCnt = 0;
    		medCnt = 0;
    		highCnt++;
    	}
    	else if(highCnt == 3 && !oy)
    	{
    		highCnt = 0;
    	}
    	
    	// Speed control - button 'b'
    	if(medCnt == 0 && ob)
    	{
    		shooter.set(.66);
    		highCnt = 0;
    		lowCnt = 0;
    		medCnt++;
    	}
    	else if(medCnt == 1 && !ob)
    	{
    		medCnt++;
    	}
    	else if(medCnt == 2 && ob)
    	{
    		shooter.set(0);
    		highCnt = 0;
    		lowCnt = 0;
    		medCnt++;
    	}
    	else if(medCnt == 3 && !ob)
    	{
    		medCnt = 0;
    	}
    	
    	// Speed control - button 'a'
    	if(lowCnt == 0 && oa)
    	{
    		shooter.set(.75);
    		highCnt = 0;
    		medCnt = 0;
    		lowCnt++;
    	}
    	else if(lowCnt == 1 && !oa)
    	{
    		lowCnt++;
    	}
    	else if(lowCnt == 2 && oa)
    	{
    		shooter.set(0);
    		highCnt = 0;
    		medCnt = 0;
    		lowCnt++;
    	}
    	else if(lowCnt == 3 && !oa)
    	{
    		lowCnt = 0;
    	}
    	
    	//Intake
    	if(ox && inCnt == 0 && or < 0.05 && or > -0.05)
    	{
    		intake.set(.75);
    		outtake.set(1.0);
    		inCnt++;
    	}
    	else if(inCnt == 1 && !ox)
    	{
    		inCnt++;
    	}
    	else if(inCnt == 2 && ox)
    	{
    		inCnt++;
    		intake.set(0);
    		outtake.set(0.0);
    	}
    	else if(inCnt == 3 && !ox)
    	{
    		inCnt = 0;
    	}
    
    	//intake
    	/*(if(test && sensor.get())
    	{
    		intake.set(.75);
    	}
    	*/
    	
    	//test for lswitch function
    	/*if(test && lSwitch.get())
    	{
    		outtake.set(1);
    	}
    */
    	
     	//Pneumatics
    	
    	//dogear
    	if(b == true && gboxCnt == 0)
    	{
    		//sets to high gear(torque)
    		dogear.set(forward);
    		gboxCnt++;
    	}
    	else if(b == false && gboxCnt == 1)
    	{
    		gboxCnt++;
    	}
    	else if(b == true && gboxCnt == 2)
    	{
    		// Set to high gear(speed)
    		dogear.set(reverse);
    		gboxCnt++;
    	}
    	else if(b == false && gboxCnt == 3)
    	{
    		gboxCnt = 0;
    	}
    	
    	// Manipulator
    	if(maniCnt == 0 && bur)
    	{
    		manipulator.set(forward);
    		maniCnt++;
    	}
    	else if(maniCnt == 1 && !bur)
    	{
    		maniCnt++;
    	}
    	else if(maniCnt == 2 && bur)
    		
    	{
    		maniCnt++;
    		manipulator.set(reverse);
    	}
    	else if(maniCnt == 3 && !bur)
    	{
    		maniCnt = 0;
    	}
    	
    	// Lifting shooter
    	if(obr && shooterCnt == 0)
    	{
    		shooterextend.set(forward);
    		shooterCnt++;
    	}
    	else if(!obr && shooterCnt == 1)
    	{
    		shooterCnt++;
    	}
    	else if(obr && shooterCnt == 2)
    	{
    		shooterextend.set(reverse);
    		shooterCnt++;
    	}
    	else if(!obr && shooterCnt == 3)
    	{
    		shooterextend.set(off);
    		shooterCnt = 0;
    	}
    	
    	// Grappling hook
    	if(hookCnt == 0 && obl)
    	{
    		hook_rotate.set(forward);
    		hookCnt++;
    	}
    	else if(hookCnt == 1 && !obl)
    	{
    		hookCnt++;
    	}
    	else if(hookCnt == 2 && obl)
    	{
    		hookCnt++;
    		hook_rotate.set(reverse);
    	}
    	else if(hookCnt == 3 && !obl)
    	{
    		hookCnt = 0;
    	}
    	
    	
    	if(test)
    		hook_shoot.set(forward);
    	else
    		hook_shoot.set(reverse);
    	
    	
    	if(otl != 0)
    		winch.set(1.00);
    	else
    		winch.set(0);
    	
    	
    	} // End while
    	
    	
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
}