package org.usfirst.frc.team2557.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import org.usfirst.frc.team2557.robot.subsystems.SwerveModule;
	
public class RobotMap {
	public static AHRS gyro;

	public static SwerveModule swerveModBR, swerveModBL, swerveModFR, swerveModFL;
	public static final double L = 21.5;
	public static final double W = 21.5;
	public static final double R = Math.sqrt((RobotMap.L * RobotMap.L) + (RobotMap.W * RobotMap.W));
	public static final double circumference = 4.096;
	// public static final double toleranceAnglePID = circumference/100/4;
	public static final double deadbandJoystickInput = 0.05;
	public static final double[] setpointOffset = {2.0, 2.5, 1.4, 0.0};
	public static final double[][] PIDconst = {{0.8, 0, 0}, 
												{0.8, 0, 0}, 
												{0.8, 0, 0}, 
												{0.8, 0, 0}};
	
	public static void init() {
		gyro = new AHRS(SPI.Port.kMXP);

		swerveModFR = new SwerveModule (0, true);
		swerveModBR = new SwerveModule (1, true);
		swerveModBL = new SwerveModule (2, false);
		swerveModFL = new SwerveModule (3, false);
	}
}