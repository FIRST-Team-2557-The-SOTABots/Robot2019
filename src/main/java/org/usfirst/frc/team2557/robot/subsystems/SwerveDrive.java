package org.usfirst.frc.team2557.robot.subsystems;

import java.lang.Math;
import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.SwerveDriveCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SwerveDrive extends Subsystem {
	public void drive (double str, double fwd, double rot) {
		// double radius = Math.sqrt(Math.pow(fwd, 2) + Math.pow(str, 2));
		// double initAngle = Math.atan2(fwd, str);
		// double gyroAngle = Math.toRadians(RobotMap.gyro.getAngle());
		// double error = initAngle - gyroAngle;
		// double correction = error * 0.25;

		// double a = str - rot * (RobotMap.SWERVE_LENGTH / RobotMap.SWERVE_RADIUS);
		// double b = str + rot * (RobotMap.SWERVE_LENGTH / RobotMap.SWERVE_RADIUS);
		// double c = - fwd - rot * (RobotMap.SWERVE_WIDTH / RobotMap.SWERVE_RADIUS);
    	// double d = - fwd + rot * (RobotMap.SWERVE_WIDTH / RobotMap.SWERVE_RADIUS);

		double a = str - rot * (RobotMap.SWERVE_LENGTH / RobotMap.SWERVE_RADIUS);
		double b = str + rot * (RobotMap.SWERVE_LENGTH / RobotMap.SWERVE_RADIUS);
		double c = fwd - rot * (RobotMap.SWERVE_WIDTH / RobotMap.SWERVE_RADIUS);
    	double d = fwd + rot * (RobotMap.SWERVE_WIDTH / RobotMap.SWERVE_RADIUS);
		
		// double speedBR = Math.sqrt ((a * a) + (d * d));
	    // double speedBL = Math.sqrt ((a * a) + (c * c));
	    // double speedFR = Math.sqrt ((b * b) + (d * d));
	    // double speedFL = Math.sqrt ((b * b) + (c * c));
	    
	    // double angleBR = Math.atan2 (a, d) / Math.PI;
	    // double angleBL = Math.atan2 (a, c) / Math.PI;
	    // double angleFR = Math.atan2 (b, d) / Math.PI;
		// double angleFL = Math.atan2 (b, c) / Math.PI;

		double speedFR = Math.sqrt ((a * a) + (c * c));
	    double speedFL = Math.sqrt ((a * a) + (d * d));
	    double speedBR = Math.sqrt ((b * b) + (c * c));
	    double speedBL = Math.sqrt ((b * b) + (d * d));

		double angleFR = Math.atan2 (a, c) / Math.PI;
	    double angleFL = Math.atan2 (a, d) / Math.PI;
	    double angleBR = Math.atan2 (b, c) / Math.PI;
		double angleBL = Math.atan2 (b, d) / Math.PI;

	    RobotMap.swerveModBR.drive (speedBR, angleBR);
	    RobotMap.swerveModBL.drive (speedBL, angleBL);
	    RobotMap.swerveModFR.drive (speedFR, angleFR);
	    RobotMap.swerveModFL.drive (speedFL, angleFL);
	}

    // Put methods for controlling this subsystem here

    public void initDefaultCommand() {
    	setDefaultCommand(new SwerveDriveCommand());
    }
}