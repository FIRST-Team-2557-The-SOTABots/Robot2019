package org.usfirst.frc.team2557.robot.commands.drive;
import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class SwerveDriveCommand extends Command {

    public SwerveDriveCommand () {
    	requires(Robot.swerveDrive);
    }

    protected void initialize() {
        RobotMap.gyro.reset();
    }

    protected void execute() {
        double axis0 = -Robot.m_oi.joystick1.getRawAxis(0);
        double axis1 = -Robot.m_oi.joystick1.getRawAxis(1);
        double axis4 = -Robot.m_oi.joystick1.getRawAxis(4);
        double axis5 = Robot.m_oi.joystick1.getRawAxis(5);

        if (axis0 < RobotMap.JOYSTICK_DEADBAND && axis0 > -RobotMap.JOYSTICK_DEADBAND) axis0 = 0.0;
		if (axis1 < RobotMap.JOYSTICK_DEADBAND && axis1 > -RobotMap.JOYSTICK_DEADBAND) axis1 = 0.0;
		if (axis4 < RobotMap.JOYSTICK_DEADBAND && axis4 > -RobotMap.JOYSTICK_DEADBAND) axis4 = 0.0;
        if (axis5 < RobotMap.JOYSTICK_DEADBAND && axis5 > -RobotMap.JOYSTICK_DEADBAND) axis5 = 0.0;

        // double radius = Math.sqrt(Math.pow(axis0, 2) + Math.pow(axis1, 2));
        // double initAngle = Math.atan2(axis1, axis0);
        // double gyroAngle = RobotMap.gyro.getAngle();
        // double finalAngle = initAngle - gyroAngle;
        // double finalYaxis = radius * Math.sin(finalAngle);
        // double finalXaxis = radius * Math.cos(finalAngle);
        
        // Robot.swerveDrive.drive(axis0 * RobotMap.SWERVE_MULTIPLIER, axis1 * RobotMap.SWERVE_MULTIPLIER, 
                // axis4 * RobotMap.SWERVE_MULTIPLIER);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}