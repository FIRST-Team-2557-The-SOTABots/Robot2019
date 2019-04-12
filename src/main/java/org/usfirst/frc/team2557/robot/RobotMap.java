package org.usfirst.frc.team2557.robot;

import java.util.HashMap;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import org.usfirst.frc.team2557.robot.subsystems.SwerveModule;

public class RobotMap {
	//if wheels twitch, it's a motor power direction issue. (flip the wires)
	public static String TRAJECTORY_FOLDER = "/home/lvuser/Trajectories/";
	public static HashMap<String, Integer> arduino;
	public static double tofAngle = 0;
	
	// practice bot !!!
	public static double multparm = 208;
	public static double multiarm = 0.0; 
	public static double multdarm = 0.00;
	public static double multparmHigh = 200;
	public static double multiarmHigh = 0.0; 
	public static double multdarmHigh = 0.0;
	public static double multplift = 0.4; //.19
	public static double multilift = 0.00; //0
	public static double multdlift = 0.39; //0.004
	public static double driveMult = -1;
	public static double Y = 455000;
	public static double B = 102000;
	public static double A = -275000; //-165000
	public static double X = -295000;
	public static double backY = 470000;
	public static double backB = 178000;
	public static double backA = -191000;
	public static double backX = 0;
	public static double armClimb = 4200;
	public static double armForCargo = 6650;
	public static double armIntake = 4550;
	public static double TofDistance = 1397;
	public static double kP = 0.85;
	public static double SWERVE_ENC_CIRC = 4.94;
	public static final double[] SWERVE_SETPOINT_OFFSET = {2.291259531, 2.792, 3.496093392, 2.021484168}; //2.291259531,0.269775363,3.496093392,2.021484168 //2.29, 2.807, 3.486, 1.98
	public static double[][] SWERVE_PID_CONSTANTS = {{kP, 0.0, 0.0}, {kP, 0.0, 0.0}, {kP, 0.0, 0.0}, {kP, 0.0, 0.0}};
	public static boolean[] ANGLE_MOTOR_INVERTED = {true, true, false, false};
	public static double pidarmStall = 0.05;
	public static double pidliftStall = -0.2;
	public static double climberEncoderDirection = -1;
	public static double driveDirection = -1;
	public static double kProt = 0.0025;
	public static double kIrot = 0.0000175;
	public static double kDrot = 0.0000;
	public static double kProtBig = 0.0025;
	public static double kIrotBig = 0.0000;
	public static double kDrotBig = 0.0000;
	public static double tolerance = 0.01;
	public static double kPstr = 0.0074;
	public static double kIstr = 0.000000125;
	public static double kDstr = 0.0000028;
	public static double tolerancestr = 0.01;
	public static double climbHigh = 16000;
	public static double climbLow = 5500;
	public static double climbRetract = 2000;
	public static int climb3 = 3;
	public static int climb2 = 2;
	public static int climb0 = 0;
	public static double kPch = 0.15; // 0.001==kinda bad
	public static double kIch = 0.00;
	public static double kDch = 0.00;
	public static double kPcl = 0.25; //?
	public static double kIcl = 0.00;
	public static double kDcl = 0.00;

	// //real bot !!!
	// public static double multparm = 190;
	// public static double multiarm = 0.0; 
	// public static double multdarm = 0.00;
	// public static double multparmHigh = 225;
	// public static double multiarmHigh = 0.0; 
	// public static double multdarmHigh = 0.1;
	// public static double multplift = 0.41;
	// public static double multilift = 0.00;
	// public static double multdlift = 0.0;
	// public static double driveMult = 1;
	// public static double Y = 425000;
	// public static double B = 94000; 
	// public static double A = -273000;
	// public static double X = -345000;
	// public static double backY = 518000;
	// public static double backB = 203000;
	// public static double backA = -117000;
	// public static double backX = 80000;
	// public static double armClimb = 4400;
	// public static double armForCargo = 6600;
	// public static double armIntake = 4550;
	// public static double TofDistance = 1635;
	// public static double kP = 1;
	// public static double SWERVE_ENC_CIRC = 4.927;
	// public static final double[] SWERVE_SETPOINT_OFFSET = {0.7, 4.72, 3.2825, 3.456}; //.
	// public static double[][] SWERVE_PID_CONSTANTS = {{kP * 1.2, 0.0, 0.0}, {kP, 0.0, 0}, {kP, 0.0, 0}, {kP, 0.0, 0}};
	// public static boolean[] ANGLE_MOTOR_INVERTED = {true, false, false, false};
	// public static double pidarmStall = 0.054;
	// public static double pidliftStall = -0.07;
	// public static double climberEncoderDirection = 1;
	// public static double driveDirection = 1;
	// public static double kProt = 0.00265;
	// public static double kIrot = 0.000012;
	// public static double kDrot = 0.0000;
	// public static double tolerance = 0.01;
	// public static double kPstr = 0.00735;
	// public static double kIstr = 0.000000125;
	// public static double kDstr = 0.0000028;
	// public static double tolerancestr = 0.01;
	// public static double climbHigh = 16000;
	// public static double climbLow = 5500;
	// public static double climbRetract = 2000;
	// public static int climb3 = 3;
	// public static int climb2 = 2;
	// public static int climb0 = 0;
	// public static double kPch = 0.15; // 0.001==kinda bad
	// public static double kIch = 0.00;
	// public static double kDch = 0.00;
	// public static double kPcl = 0.25; //?
	// public static double kIcl = 0.00;
	// public static double kDcl = 0.00;

	//Constants
	public static double JOYSTICK_DEADBAND = 0.05;
	public static double TRIGGER_DEADBAND = 0.2;

	//swerve
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
	
	// lift
	public static double highPos;
	public static double midPos;
	public static double lowPos;
	public static double xPos;
	public static WPI_TalonSRX lift1;
	public static WPI_TalonSRX lift2;
	public static WPI_TalonSRX lift3;
	public static WPI_TalonSRX armLeft;
	public static WPI_TalonSRX armRight;
	public static WPI_TalonSRX intake;
	public static WPI_TalonSRX climber;

	// arm
	public static double armTarget;

	// pneumatics
	public static DoubleSolenoid dsLift;
	public static DoubleSolenoid dsIntake;
	public static DoubleSolenoid dsArmLock;
	public static DoubleSolenoid dsClimbLock;
	public static Compressor compressor;

	// sensors
	public static AHRS gyro;
	public static DigitalInput cargo;
	public static DigitalInput cargo2;
	public static SerialPort serial;

	public static void init() {
		arduino = new HashMap<String, Integer>();
		
		highPos = Y;
		midPos = B;
		lowPos = A;
		xPos = X;
		lift1 = new WPI_TalonSRX(4);
		lift2 = new WPI_TalonSRX(5);
		lift3 = new WPI_TalonSRX(6);
		armLeft = new WPI_TalonSRX(7);
		armRight = new WPI_TalonSRX(8);
		intake = new WPI_TalonSRX(9);
		climber = new WPI_TalonSRX(10);
		climber.setNeutralMode(NeutralMode.Brake);

		armTarget = 0;

		compressor = new Compressor(1);
		dsLift = new DoubleSolenoid(1, 0, 1);
		dsIntake = new DoubleSolenoid(1, 2, 3);
		dsArmLock = new DoubleSolenoid(1, 4, 5);
		dsClimbLock = new DoubleSolenoid(1, 6, 7);
		
		cargo = new DigitalInput(5);
		cargo2 = new DigitalInput(6);

		// FR = 0, BR = 1, BL = 2, FL = 3
		swerveMod = new SwerveModule[4];
		for(int i = 0; i < 4; i++) swerveMod[i] = new SwerveModule(i, ANGLE_MOTOR_INVERTED[i]);

		gyro = new AHRS(SPI.Port.kMXP);
		// serial = new SerialPort(9600, SerialPort.Port.kUSB);
	}
}