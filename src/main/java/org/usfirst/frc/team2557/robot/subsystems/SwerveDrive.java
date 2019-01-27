package org.usfirst.frc.team2557.robot.subsystems;

import java.lang.Math;
import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.SwerveDriveCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SwerveDrive extends Subsystem {
	public double speedFR;
	public double speedFL;
	public double speedBR;
	public double speedBL;
	public double angleFR;
	public double angleFL;
	public double angleBR;
	public double angleBL;
	public double a;
	public double b;
	public double c;
	public double d;

	public void drive (double str, double fwd, double rot) {
		// double radius = Math.sqrt(Math.pow(fwd, 2) + Math.pow(str, 2));
		// double initAngle = Math.atan2(fwd, str);
		// double gyroAngle = Math.toRadians(RobotMap.gyro.getAngle());
		// double error = initAngle - gyroAngle;
		// double correction = error * 0.25;

		a = str - rot * RobotMap.SWERVE_LENGTH / RobotMap.SWERVE_RADIUS;
		// a *= -1;
		b = str + rot * RobotMap.SWERVE_LENGTH / RobotMap.SWERVE_RADIUS;
		c = fwd - rot * RobotMap.SWERVE_WIDTH / RobotMap.SWERVE_RADIUS;
    	d = fwd + rot * RobotMap.SWERVE_WIDTH / RobotMap.SWERVE_RADIUS;

		speedFR = Math.sqrt (Math.pow(a, 2) + Math.pow(d, 2));
	    speedFL = Math.sqrt (Math.pow(a, 2) + Math.pow(c, 2));
	    speedBR = Math.sqrt (Math.pow(b, 2) + Math.pow(d, 2));
	    speedBL = Math.sqrt (Math.pow(b, 2) + Math.pow(c, 2));

		angleFR = Math.atan2 (a, d) / Math.PI;
	    angleFL = Math.atan2 (a, c) / Math.PI; //
	    angleBR = Math.atan2 (b, d) / Math.PI;
		angleBL = Math.atan2 (b, c) / Math.PI; //

		// if(d == 0.0){
		// 	angleFR = 0;
		// 	angleBR = 0;
		// }
		// if(c == 0.0){
		// 	angleFL = 0;
		// 	angleBL = 0;
		// }

	    RobotMap.swerveModBR.drive (speedBR, angleBR);
	    RobotMap.swerveModBL.drive (speedBL, angleBL);
	    RobotMap.swerveModFR.drive (speedFR, angleFR);
		RobotMap.swerveModFL.drive (speedFL, angleFL);

		// RobotMap.swerveModBR.drive (0,0);
	    // RobotMap.swerveModBL.drive (0,0);
	    // RobotMap.swerveModFR.drive (0,0);
		// RobotMap.swerveModFL.drive (0,0);
		
		// RobotMap.swerveModBR.drive (fwd/5, rot/5);
	    // RobotMap.swerveModBL.drive (fwd/5, rot/5);
	    // RobotMap.swerveModFR.drive (fwd/5, rot/5);
		// RobotMap.swerveModFL.drive (fwd/5, rot/5);
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new SwerveDriveCommand());
    }
}