package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.SwerveDriveCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SwerveDrive extends Subsystem {
	
	public void drive (double str, double fwd, double rot) {
		double a = str - rot * (RobotMap.SWERVE_LENGTH / RobotMap.SWERVE_RADIUS);
		double b = str + rot * (RobotMap.SWERVE_LENGTH / RobotMap.SWERVE_RADIUS);
		double c = fwd - rot * (RobotMap.SWERVE_WIDTH / RobotMap.SWERVE_RADIUS);
    	double d = fwd + rot * (RobotMap.SWERVE_WIDTH / RobotMap.SWERVE_RADIUS);
		
		double speedBR = Math.sqrt ((a * a) + (d * d));
	    double speedBL = Math.sqrt ((a * a) + (c * c));
	    double speedFR = Math.sqrt ((b * b) + (d * d));
	    double speedFL = Math.sqrt ((b * b) + (c * c));
	    
	    double angleBR = Math.atan2 (a, d) / Math.PI;
	    double angleBL = Math.atan2 (a, c) / Math.PI;
	    double angleFR = Math.atan2 (b, d) / Math.PI;
		double angleFL = Math.atan2 (b, c) / Math.PI;

	    // RobotMap.swerveModBR.drive (speedBR, angleBR);
	    // RobotMap.swerveModBL.drive (speedBL, angleBL);
	    // RobotMap.swerveModFR.drive (speedFR, angleFR);
	    // RobotMap.swerveModFL.drive (speedFL, angleFL);
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new SwerveDriveCommand());
    }
}