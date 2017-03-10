package org.usfirst.frc.team5872.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5872.robot.Robot;

public class Auto1 extends CommandGroup {
	
	public void execute() {
		
		Robot.runMotor(0.5, 1.25);
    	
    	Robot.runMotor(0.25, 1.25);
    	
    	Robot.runMotor(0, 3);
    	
    	Robot.runMotor(-0.5, 0.075);
    	
    	Robot.stopDriveTrain();
    	Robot.turn(0.3, -0.3);
    	Robot.delay(1250);
    	
    	Robot.stopDriveTrain();
    	Robot.bangBang(0.4136);
    	Robot.delay(1500);
    	
    	Robot.stopDriveTrain();
    	Robot.bangBang(0.4136);
    	Robot.delay(250);
    	
    	Robot.bangBang(0.4136);
    	Robot.mixer.set(1.0);
    	Robot.delay(5000);
    	
    	Robot.stopDriveTrain();
    	Robot.shooter.set(0);
    	Robot.mixer.set(0);
    	Robot.delay(250);
    	
    	Robot.stopDriveTrain();
    	Robot.shooter.set(0);
    	Robot.mixer.set(0);
    	Robot.delay(3750);
    	
	}
}
