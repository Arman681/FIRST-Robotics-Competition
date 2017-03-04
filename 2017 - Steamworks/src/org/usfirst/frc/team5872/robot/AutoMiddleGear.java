package org.usfirst.frc.team5872.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5872.robot.Robot;

public class AutoMiddleGear extends CommandGroup {

public void execute() {
	
	Robot.runMotor(0.25, 4.25);
	Robot.runMotor(-0.2, 0.15);
	Robot.runMotor(0, 9.5);
	
	}
	
}
