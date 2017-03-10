package org.usfirst.frc.team5872.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5872.robot.Robot;

public class AutoSideGear extends CommandGroup {

public void execute() {
	
	Robot.runMotor(0.25, 6.25);
	Robot.runMotor(-0.2, 0.15);
	Robot.runMotor(0, 7.5);
	
	}
	
}
