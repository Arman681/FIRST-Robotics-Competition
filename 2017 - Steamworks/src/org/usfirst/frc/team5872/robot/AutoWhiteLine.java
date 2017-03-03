package org.usfirst.frc.team5872.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5872.robot.Robot;

public class AutoWhiteLine extends CommandGroup {

	public void execute() {
		
		Robot.runMotor(0.5);
		Robot.delay(4000);
		Robot.runMotor(0);
		Robot.delay(11000);
		
	}

	
}
