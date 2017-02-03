
package org.usfirst.frc.team5872.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon.*;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team5872.robot.commands.ExampleCommand;
import org.usfirst.frc.team5872.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;

    Command autonomousCommand;
    SendableChooser chooser;
    
   /* CANTalon fl; //CAN 3
    CANTalon fr; //CAN 2
    CANTalon bl; //CAN 1
    CANTalon br; //CAN 0 */
    
    CANTalon fl = new CANTalon(3); 		/* device IDs here (1 of 2) */
	CANTalon bl = new CANTalon(2);
	CANTalon fr = new CANTalon(1);
	CANTalon br = new CANTalon(0);
    
    //Defines the variables as members of our Robot class
    RobotDrive myRobot;
    Joystick stick;
    Timer timer;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
		oi = new OI();
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new ExampleCommand());
//        chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", chooser);
        
      /*  fl = new CANTalon(3); //CAN 3
        fr = new CANTalon(2); //CAN 2
        bl = new CANTalon(1); //CAN 1
        br = new CANTalon(0); //CAN 0*/
        
        /*fl.changeControlMode(TalonControlMode.Follower);
        bl.changeControlMode(TalonControlMode.Follower);
        fr.changeControlMode(TalonControlMode.Follower);
        br.changeControlMode(TalonControlMode.Follower);
        fl.set(3);
        bl.set(1);
        fr.set(2);
        br.set(0);
        
        fl.setInverted(true);
        bl.setInverted(true);
        */
        myRobot = new RobotDrive(0,1);
        stick = new Joystick(0);
        timer = new Timer();        
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
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) 
        	autonomousCommand.start();
        
        timer.reset(); // Resets the timer to 0
        timer.start(); // Start counting
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomous() {
    	
        Scheduler.getInstance().run();
        
     // Drive for 2 seconds
        if (timer.get() < 2.0)
             myRobot.drive(-0.5,  0); // drive forwards half speed
        else 
             myRobot.drive(0.0, 0.0); // stop robot
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	while(true){
        Scheduler.getInstance().run();
        myRobot.arcadeDrive(stick); //This line drives the robot using the values of the joystick and the motor controllers selected above
        
        double l = -stick.getRawAxis(1);
    	double r = stick.getRawAxis(5);
    	double tl = stick.getRawAxis(2);
    	double tr = stick.getRawAxis(3);
        
        double y = -stick.getRawAxis(1);
        double z = stick.getRawAxis(2);
    	//Tank Drive
    	fl.set(l);
    	fr.set(r);
    	bl.set(l);
    	br.set(r);
    	
        /*//Arcade Drive
        if(y < 0 || y > 0){
        	
        	fl.set(y);
        	bl.set(y);
        	fr.set(y);
        	br.set(y);
        }
        else{
        	
        	fl.set(0);
        	bl.set(0);
        	fr.set(0);
        	br.set(0);
        }
        if(z < 0 || z > 0){
        	
        	fl.set(-z);
        	bl.set(-z);
        	fr.set(z);
        	br.set(z);
        }
        else{
        	
        	fl.set(0);
        	bl.set(0);
        	fr.set(0);
        	br.set(0);
        }*/
    	}
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
}
