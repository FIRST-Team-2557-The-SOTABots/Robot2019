package org.usfirst.frc.team2557.robot.commands;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class SwerveDriveCommand extends Command {

    public SwerveDriveCommand () {
    	requires(Robot.swerveDrive);
    }

    protected void initialize() {
    }

    protected void execute() {
        double axis0 = Robot.m_oi.joystick.getRawAxis(0);
        double axis1 = Robot.m_oi.joystick.getRawAxis(1);
        double axis4 = Robot.m_oi.joystick.getRawAxis(4);
        double axis5 = Robot.m_oi.joystick.getRawAxis(5);

        if (axis0 < RobotMap.JOYSTICK_DEADBAND && axis0 > -RobotMap.JOYSTICK_DEADBAND) { axis0 = 0.0; }
		if (axis1 < RobotMap.JOYSTICK_DEADBAND && axis1 > -RobotMap.JOYSTICK_DEADBAND) { axis1 = 0.0; }
		if (axis4 < RobotMap.JOYSTICK_DEADBAND && axis4 > -RobotMap.JOYSTICK_DEADBAND) { axis4 = 0.0; }
        if (axis5 < RobotMap.JOYSTICK_DEADBAND && axis5 > -RobotMap.JOYSTICK_DEADBAND) { axis5 = 0.0; }
        
        Robot.swerveDrive.drive(axis1, axis5);
        // Robot.swerveDrive.drive(axis0, axis1, axis4);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}