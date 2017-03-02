package org.usfirst.frc.team5872.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5872.robot.Robot;

public class Auto1 extends CommandGroup {
	
	public void execute() {
		Robot.runMotor(0.5);
    	Robot.delay(1250);
    	Robot.runMotor(0.25);
    	Robot.delay(1250);
    	Robot.runMotor(0);
    	Robot.delay(1500);
    	Robot.runMotor(-0.5);
    	Robot.delay(75);
    	Robot.runMotor(0);
    	Robot.delay(50);
    	Robot.turn(0.3, -0.3);
    	Robot.delay(750);
    	Robot.runMotor(0);
    	Robot.bangBang(0.4136);
    	Robot.delay(1500);
    	Robot.runMotor(0.0);
    	Robot.bangBang(0.4136);
    	Robot.outtake.set(0.5);
    	Robot.delay(250);
    	Robot.bangBang(0.4136);
    	Robot.outtake.set(0);
    	Robot.mixer.set(-1.0);
    	Robot.delay(5000);
    	Robot.runMotor(0);
    	Robot.outtake.set(-0.5);
    	Robot.shooter.set(0);
    	Robot.mixer.set(0);
    	Robot.delay(250);
    	Robot.runMotor(0);
    	Robot.outtake.set(0);
    	Robot.shooter.set(0);
    	Robot.mixer.set(0);
    	Robot.delay(5000);
	}
	 /*public void delay(int milliseconds){
	    	try{
	    		Thread.sleep(milliseconds);
	    	}
	    	catch(Exception e1){
	    		e1.printStackTrace();
	    	}
	    }
	    public void turn(double left,double right)
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
	    }*/

}
