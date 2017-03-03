package org.usfirst.frc.team5872.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5872.robot.Robot;

public class AutoMiddleGear extends CommandGroup {

public void execute() {
	Robot.runMotor(0.25);
	Robot.delay(4250);
	
	Robot.runMotor(-0.2);
	Robot.delay(150);
	
	Robot.runMotor(0);
	Robot.delay(9500);
	
	}
	
}
