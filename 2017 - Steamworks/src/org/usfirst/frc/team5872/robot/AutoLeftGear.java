package org.usfirst.frc.team5872.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5872.robot.Robot;

public class AutoLeftGear extends CommandGroup {

public void execute() {
	
	Robot.runMotor(0.25, 4.25);
	Robot.runMotor(-.3,.3, 1);
	Robot.runMotor(0.25, 2.75);
	Robot.runMotor(-0.2, 0.15);
	Robot.runMotor(0, 6.85);
	
	}
	
}
