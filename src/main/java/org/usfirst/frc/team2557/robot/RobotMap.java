package org.usfirst.frc.team2557.robot;

import java.util.HashMap;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;

import org.usfirst.frc.team2557.robot.subsystems.SwerveModule;

public class RobotMap {
	public static String TRAJECTORY_FOLDER = "/home/lvuser/Trajectories/";
	public static HashMap<String, Double> arduino;
	
	//practice bot !!!
	public static double multparm = 240;
	public static double multiarm = 0.075; 
	public static double multdarm = 0.00225;
	public static double multplift = 0.45; //.19
	public static double multilift = 0.005; //0
	public static double multdlift = 0.0; //0.004
	public static double driveMult = -1;
	public static double Y = 430000;
	public static double B = 130000;
	public static double A = -240000; //-165000
	public static double X = -270000;
	public static double backY = 500000;
	public static double backB = 120000;
	public static double backA = -260000;
	public static double backX = 80000;
	// public static double armBackCargo = -5800;
	// public static double armBack = -4800;
	public static double armForward = 4600;
	public static double armHigh = 6750;
	public static double kP = 0.85;
	public static double SWERVE_ENC_CIRC = 4.94;
	public static final double[] SWERVE_SETPOINT_OFFSET = {2.310, 2.744, 3.486, 2.027};
	public static double[][] SWERVE_PID_CONSTANTS = {{kP, 0.0, 0.01}, {kP, 0.0, 0}, {kP, 0.0, 0}, {kP, 0.0, 0}};
	public static boolean[] ANGLE_MOTOR_INVERTED = {true, true, false, false};

	// //real bot !!!
	// public static double multparm = 250;
	// public static double multiarm = 0.17; 
	// public static double multdarm = 0.007;
	// public static double multplift = 0.8;
	// public static double multilift = 0.003;
	// public static double multdlift = 0.0;
	// public static double driveMult = 1;
	// public static double Y = 465000;
	// public static double B = 85000; 
	// public static double A = -278000;
	// public static double X = -307000;
	// public static double backY = 508000;
	// public static double backB = 203000;
	// public static double backA = -137000;
	// public static double backX = 80000;
	// // public static double armBackCargo = -3300;
	// // public static double armBack = -4900;
	// public static double armForCargo = 6200;
	// public static double armIntake = 4960;
	// public static double kP = 1;
	// public static double SWERVE_ENC_CIRC = 4.927;
	// // public static final double[] SWERVE_SETPOINT_OFFSET = {0.709, 4.72, 3.2825, 3.482};
	// public static final double[] SWERVE_SETPOINT_OFFSET = {0.681, 4.72, 3.2825, 3.482};
	// public static double[][] SWERVE_PID_CONSTANTS = {{kP * 1.2, 0.0, 0.0}, {kP, 0.0, 0}, {kP, 0.0, 0}, {kP, 0.0, 0}};
	// public static boolean[] ANGLE_MOTOR_INVERTED = {true, false, false, false};

	// //if wheels twitch, it's a motor power direction issue. (flip the wires)

	//Constants
	public static double JOYSTICK_DEADBAND = 0.05;
	public static double TRIGGER_DEADBAND = 0.2;

	public static SwerveModule[] swerveMod;

	public static double MAX_VEL = 10;
    public static double MAX_ACC = 5;
	public static double WHEELBASE_WIDTH = 0.8;
	public static double WHEELBASE_LENGTH = 0.8;
	public static double SWERVE_WHEEL_DIAMETER = 0.05; // in m?
	public static int SWERVE_MAX_CURRENT = 30; // in amps
	public static int SWERVE_CURRENT_DUR = 100; // in ms
	public static double SWERVE_LENGTH = 21.5;
	public static double SWERVE_WIDTH = 21.5;
	public static double SWERVE_RADIUS = Math.sqrt(Math.pow(SWERVE_LENGTH, 2) + Math.pow(SWERVE_WIDTH, 2));

	public static double SWERVE_LOOP_TIME = 0.100; // in ms (50 ms default)
	public static double SWERVE_PID_TOLERANCE = SWERVE_ENC_CIRC / 100.0 / 4.0; // .25%
	
	public static double highPos;
	public static double midPos;
	public static double lowPos;
	public static double xPos;
	
	public static double armTarget;

	public static int tof1;
	public static int tof2;
	public static int IR1;
	public static int IR2;
	public static int IR3;

	public static WPI_TalonSRX lift1;
	public static WPI_TalonSRX lift2;
	public static WPI_TalonSRX lift3;
	public static WPI_TalonSRX armLeft;
	public static WPI_TalonSRX armRight;
	public static WPI_TalonSRX intake;
	public static WPI_TalonSRX climber;

	public static WPI_TalonSRX climb;

	public static AHRS gyro;
	public static DoubleSolenoid dsLift;
	public static DoubleSolenoid dsIntake;
	public static DoubleSolenoid dsArmLock;
	public static Compressor compressor;

	public static DigitalInput cargo;
	public static DigitalInput cargo2;
	public static SerialPort serial;
	// public static I2C tof;

	public static void init() {
		arduino = new HashMap<String, Double>();
		
		highPos = Y;
		midPos = B;
		lowPos = A;
		xPos = X;
		armTarget = 0;

		lift1 = new WPI_TalonSRX(4);
		lift2 = new WPI_TalonSRX(5);
		lift3 = new WPI_TalonSRX(6);
		armLeft = new WPI_TalonSRX(7);
		armRight = new WPI_TalonSRX(8);
		intake = new WPI_TalonSRX(9);
		climber = new WPI_TalonSRX(10);

		climb = new WPI_TalonSRX(666);

		gyro = new AHRS(SPI.Port.kMXP);
		compressor = new Compressor(0);
		dsLift = new DoubleSolenoid(1, 0, 1);
		dsIntake = new DoubleSolenoid(1, 2, 3);
		dsArmLock = new DoubleSolenoid(1, 4, 5);

		//real bot
		cargo = new DigitalInput(5);
		cargo2 = new DigitalInput(6);

		// // FR = 0, BR = 1, BL = 2, FL = 3
		swerveMod = new SwerveModule[4];
		for(int i = 0; i < 4; i++) swerveMod[i] = new SwerveModule(i, ANGLE_MOTOR_INVERTED[i]);

		serial = new SerialPort(9600, SerialPort.Port.kUSB);
	}
}