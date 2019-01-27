package org.usfirst.frc.team2557.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import org.usfirst.frc.team2557.robot.subsystems.SwerveModule;
	
public class RobotMap {
	public static AHRS gyro;

	public static SwerveModule swerveModBR, swerveModBL, swerveModFR, swerveModFL;

	public static final double JOYSTICK_DEADBAND = 0.05;

	public static final int SWERVE_MAX_CURRENT = 30; // in amps
	public static final int SWERVE_CURRENT_DUR = 100; // in ms

	// public static final double SWERVE_MULTIPLIER = 1.0;
	public static final double SWERVE_LENGTH = 21.5;
	public static final double SWERVE_WIDTH = 21.5;
	public static final double SWERVE_RADIUS = Math.sqrt(Math.pow(SWERVE_LENGTH, 2) + Math.pow(SWERVE_WIDTH, 2));
	public static final double SWERVE_ENC_CIRC = 4.94;
	public static final double SWERVE_LOOP_TIME = 0.100; // in ms (50 ms default)
	public static final double SWERVE_PID_TOLERANCE = SWERVE_ENC_CIRC / 100.0 / 4.0; // .25%
	// public static final double[] SWERVE_SETPOINT_OFFSET = {4.115, 4.788, 3.486, 2.020}; // wrong
	public static final double[] SWERVE_SETPOINT_OFFSET = {1.627, 2.311, 1.016, 2.002}; // must be [0, circ/2) ?
	public static final double[][] SWERVE_PID_CONSTANTS = {{1, 0, 0}, 
															{1, 0, 0}, 
															{1, 0, 0}, 
															{1, 0, 0}};
	
	public static void init() {
		gyro = new AHRS(SPI.Port.kMXP);

		swerveModFR = new SwerveModule (0, true, false);
		swerveModBR = new SwerveModule (1, true, false);
		swerveModBL = new SwerveModule (2, false, false);
		swerveModFL = new SwerveModule (3, false, true);
	}
}