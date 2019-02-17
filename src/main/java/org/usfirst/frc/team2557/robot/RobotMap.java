package org.usfirst.frc.team2557.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import org.usfirst.frc.team2557.robot.subsystems.SwerveModule;

public class RobotMap {
	public static AHRS gyro;

	public static final double JOYSTICK_DEADBAND = 0.05;
	public static final double TRIGGER_DEADBAND = 0.2;

	public static SwerveModule[] swerveMod;
	public static final int SWERVE_MAX_CURRENT = 30; // in amps
	public static final int SWERVE_CURRENT_DUR = 100; // in ms
	public static final double SWERVE_LENGTH = 21.5;
	public static final double SWERVE_WIDTH = 21.5;
	public static final double SWERVE_RADIUS = Math.sqrt(Math.pow(SWERVE_LENGTH, 2) + Math.pow(SWERVE_WIDTH, 2));
	public static final double SWERVE_ENC_CIRC = 4.94;
	public static final double SWERVE_LOOP_TIME = 0.100; // in ms (50 ms default)
	public static final double SWERVE_PID_TOLERANCE = SWERVE_ENC_CIRC / 100.0 / 4.0; // .25%
	// public static final double[] SWERVE_SETPOINT_OFFSET = {4.115, 4.788, 3.486, 2.020}; // must be [0, circ)
	// public static final double[] SWERVE_SETPOINT_OFFSET = {1.64, 2.318, 1.016, 2.020};
	public static final double[] SWERVE_SETPOINT_OFFSET = {2.310, 1.725, 3.486, 2.020};
	public static final double kP = 0.85;
	public static final double[][] SWERVE_PID_CONSTANTS = {{kP, 0.0, 0}, {kP, 0.0, 0}, {kP, 0.0, 0}, {kP, 0.0, 0}};
	public static final boolean[] ANGLE_MOTOR_INVERTED = {true, true, false, false};

	public static void init() {
		gyro = new AHRS(SPI.Port.kMXP);

		// FR = 0, BR = 1, BL = 2, FL = 3
		swerveMod = new SwerveModule[4];
		for(int i = 0; i < 4; i++) swerveMod[i] = new SwerveModule(i, ANGLE_MOTOR_INVERTED[i]);
	}
}