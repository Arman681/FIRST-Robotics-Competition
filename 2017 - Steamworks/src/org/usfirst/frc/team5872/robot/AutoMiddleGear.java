package org.usfirst.frc.team5872.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5872.robot.Robot;

public class AutoMiddleGear extends CommandGroup {

public void execute() {
	Robot.runMotor(0.5);
	Robot.delay(1250);
	
	Robot.runMotor(0.25);
	Robot.delay(1250);
	
	Robot.runMotor(0);
	Robot.delay(3000);
	
	Robot.runMotor(-0.5);
	Robot.delay(75);
	
	Robot.runMotor(0);
	Robot.delay(50);
	
	Robot.turn(0.3, -0.3);
	Robot.delay(1250);
	
	Robot.runMotor(0);
	Robot.bangBang(0.4136);
	
	Robot.delay(1500);
	Robot.runMotor(0.0);
	
	Robot.bangBang(0.4136);
	Robot.outtake.set(0.5);
	Robot.delay(250);
	
	Robot.bangBang(0.4136);
	Robot.outtake.set(0);
	Robot.mixer.set(1.0);
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
	Robot.delay(3750);	
	}
	
}
