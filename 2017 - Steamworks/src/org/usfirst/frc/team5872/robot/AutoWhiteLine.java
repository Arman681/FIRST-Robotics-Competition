package org.usfirst.frc.team5872.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoWhiteLine {

	public void execute() {
		
		Robot.runMotor(0.5);
		Robot.delay(5000);
		Robot.runMotor(0);
		Robot.delay(10000);
		
	}

	
}
