package org.usfirst.frc.team2557.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SPI;
import org.usfirst.frc.team2557.robot.subsystems.SwerveModule;

public class RobotMap {
	public static String TRAJECTORY_FOLDER = "/home/lvuser/Trajectories/";

	//practice bot
	public static double multparm = 25;
	public static double multiarm = 0.02; 
	public static double multdarm = 0.0005;
	//real bot
	// public static double multparm = 28;
	// public static double multiarm = 0.02; 
	// public static double multdarm = 0.0005;

	public static double armTarget;
	//practice bot
	public static double driveMult = -1;
	//real bot
	// public static double driveMult = 1;

	public static boolean lastGamepieceWasDisc;
	public static double highPos;
	public static double midPos;
	public static double lowPos;

	//real bot
	// public static double highPosHatch = 450000;
	// public static double midPosHatch = 91500;
	// public static double lowPosHatch = -280000;
	// public static double highPosCargo = 505000;
	// public static double midPosCargo = 200000;
	// public static double lowPosCargo = -173000;
	//practice bot
	public static double highPosHatch = 460000;
	public static double midPosHatch = 205000;
	public static double lowPosHatch = -165000;
	public static double highPosCargo = 495000;
	public static double midPosCargo = 120000;
	public static double lowPosCargo = -260000;

	//real bot
	// public static double armHigh = -6150;
	// public static double armBack = -4750;
	// public static double armFor = 4750;
	// public static double armIntake = 2500;

	//practice bot
	public static double armHigh = -5800;
	public static double armBack = -4800;
	public static double armFor = 5000;
	public static double armIntake = 2250;

	public static double JOYSTICK_DEADBAND = 0.05;
	public static double TRIGGER_DEADBAND = 0.2;

	public static SwerveModule[] swerveMod;
	//practice
	public static double kP = 0.85;
	//real
	// public static double kP = 1;
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
	//practice
	public static double SWERVE_ENC_CIRC = 4.94;
	//real
	// public static double SWERVE_ENC_CIRC = 4.927;
	public static double SWERVE_LOOP_TIME = 0.100; // in ms (50 ms default)
	public static double SWERVE_PID_TOLERANCE = SWERVE_ENC_CIRC / 100.0 / 4.0; // .25%
	//practice bot
	public static final double[] SWERVE_SETPOINT_OFFSET = {2.310, 2.844, 3.486, 2.067};
	//real bot
	// public static final double[] SWERVE_SETPOINT_OFFSET = {0.709, 4.72, 3.2825, 3.4265};
	public static double[][] SWERVE_PID_CONSTANTS = {{kP, 0.0, 0}, {kP, 0.0, 0}, {kP, 0.0, 0}, {kP, 0.0, 0}};
	//practice bot
	public static boolean[] ANGLE_MOTOR_INVERTED = {true, true, false, false};
	//real bot
	// public static boolean[] ANGLE_MOTOR_INVERTED = {true, false, false, false};
	//if wheels twitch, it's a motor power direction issue. (flip the wires)

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

	public static AHRS gyro;
	public static DoubleSolenoid dsLift;
	public static DoubleSolenoid dsIntake;
	public static DoubleSolenoid dsArmLock;
	public static DoubleSolenoid ds12inch;
	public static DoubleSolenoid ds8inch;
	public static Compressor compressor;

	public static DigitalInput cargo;
	public static DigitalInput disc;

	// public static SerialPort serial;

	public static void init() {
		lastGamepieceWasDisc = false;
		highPos = highPosHatch;
		midPos = midPosHatch;
		lowPos = lowPosHatch;
		armTarget = 0;

		lift1 = new WPI_TalonSRX(4);
		lift2 = new WPI_TalonSRX(5);
		lift3 = new WPI_TalonSRX(6);
		armLeft = new WPI_TalonSRX(7);
		armRight = new WPI_TalonSRX(8);
		intake = new WPI_TalonSRX(9);

		gyro = new AHRS(SPI.Port.kMXP);
		compressor = new Compressor(0);
		dsLift = new DoubleSolenoid(1, 0, 1);
		dsIntake = new DoubleSolenoid(1, 2, 3);
		dsArmLock = new DoubleSolenoid(1, 4, 5);
		ds12inch = new DoubleSolenoid(0, 4, 5);
		ds8inch = new DoubleSolenoid(0, 6, 7);

		//practice bot
		disc = new DigitalInput(1);
		cargo = new DigitalInput(0);

		//real bot
		// disc = new DigitalInput(5);
		// cargo = new DigitalInput(6);

		// // FR = 0, BR = 1, BL = 2, FL = 3
		swerveMod = new SwerveModule[4];
		for(int i = 0; i < 4; i++) swerveMod[i] = new SwerveModule(i, ANGLE_MOTOR_INVERTED[i]);

		// serial = new SerialPort(9600, SerialPort.Port.kUSB);
	}
}