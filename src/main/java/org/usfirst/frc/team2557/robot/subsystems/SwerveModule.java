package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.AdjustedEncoder;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SwerveModule extends Subsystem {
	private final double[] pidConstants;
	private final double setpointOffset;
	public double setpoint;
	public double error;
	public double output;
	public double encCount;

	private WPI_TalonSRX angleMotor;
	private CANSparkMax speedMotor;
	private PIDController pidController;
	// private AdjustedEncoder encoder;
	private AnalogInput encoder;

	public SwerveModule(int swerveModIndex, boolean inverted) {
		speedMotor = new CANSparkMax(swerveModIndex, MotorType.kBrushless);
		angleMotor = new WPI_TalonSRX(swerveModIndex);
		// angleMotor.setInverted(angleMotorInverted);
		// speedMotor.setInverted(angleMotorInverted);
		encoder = new AnalogInput(swerveModIndex);

		pidConstants = RobotMap.SWERVE_PID_CONSTANTS[swerveModIndex];
		setpointOffset = RobotMap.SWERVE_SETPOINT_OFFSET[swerveModIndex];

		// encoder = new AdjustedEncoder(swerveModIndex, inverted, pidConstants[0]);

		// should try messing with loop time to see what it does to the performance
		pidController = new PIDController(pidConstants[0], pidConstants[1], pidConstants[2], 
				encoder, angleMotor, RobotMap.SWERVE_LOOP_TIME);

		pidController.setInputRange(0.0, RobotMap.SWERVE_ENC_CIRC);
		pidController.setOutputRange(-1.0, 1.0);
		pidController.setContinuous(true);
		pidController.setAbsoluteTolerance(RobotMap.SWERVE_PID_TOLERANCE);
		pidController.enable();

		angleMotor.configContinuousCurrentLimit(RobotMap.SWERVE_MAX_CURRENT, 0);
		angleMotor.configPeakCurrentLimit(RobotMap.SWERVE_MAX_CURRENT, 0);
		angleMotor.configPeakCurrentDuration(RobotMap.SWERVE_CURRENT_DUR, 0);
		angleMotor.enableCurrentLimit(true);
	}

	public double getSetpoint(){
		return setpoint;
	}
	
	// angle and speed should be from -1.0 to 1.0, like a joystick input
	public void drive (double speed, double angle) {
		speedMotor.set (speed);

		setpoint = (angle + 1.0) * RobotMap.SWERVE_ENC_CIRC / 2.0;
		setpoint += setpointOffset;
		if(setpoint >= RobotMap.SWERVE_ENC_CIRC){
			setpoint -= RobotMap.SWERVE_ENC_CIRC;
		}
		pidController.setSetpoint(setpoint);

		/* println to console output 
		is for graphing in excel 
		AND REMEMBER only use one swervemod
		at a time with this console output */
		error = pidController.getError();
		output = pidController.get();
		encCount = encoder.pidGet();
		// System.out.println("setpoint: " + setpoint + " error: " + error + " output: " 
				// + output + " encCount: " + encCount + " time: " + timer.get());
	}


    public void initDefaultCommand() {
		// NOTE: no default command unless running swerve modules seperately
    }
}