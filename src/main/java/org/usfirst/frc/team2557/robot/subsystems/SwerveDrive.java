package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.SwerveDriveCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SwerveDrive extends Subsystem {
	
	// public void drive (double str, double fwd, double rot) {
	public void drive (double d, double r) {
		double speedBR = d;
	    double speedBL = d;
	    double speedFR = d;
	    double speedFL = d;
	    double angleBR = r;
	    double angleBL = r;
	    double angleFR = r;
		double angleFL = r;

		// double a = str - rot * (RobotMap.L / RobotMap.R);
		// double b = str + rot * (RobotMap.L / RobotMap.R);
		// double c = - fwd - rot * (RobotMap.W / RobotMap.R);
    	// double d = - fwd + rot * (RobotMap.W / RobotMap.R);
		
		// double speedBR = Math.sqrt ((a * a) + (d * d));
	    // double speedBL = Math.sqrt ((a * a) + (c * c));
	    // double speedFR = Math.sqrt ((b * b) + (d * d));
	    // double speedFL = Math.sqrt ((b * b) + (c * c));
	    
	    // double angleBR = Math.atan2 (a, d) / Math.PI;
	    // double angleBL = Math.atan2 (a, c) / Math.PI;
	    // double angleFR = Math.atan2 (b, d) / Math.PI;
		// double angleFL = Math.atan2 (b, c) / Math.PI;

	    // RobotMap.swerveModBR.drive (speedBR, angleBR);
	    RobotMap.swerveModBL.drive (speedBL, angleBL);
	    // RobotMap.swerveModFR.drive (speedFR, angleFR);
	    // RobotMap.swerveModFL.drive (speedFL, angleFL);
	}

    // Put methods for controlling this subsystem here

    public void initDefaultCommand() {
    	setDefaultCommand(new SwerveDriveCommand());
    }
}