package org.usfirst.frc.team2557.robot.subsystems;

import java.lang.Math;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SwerveModule extends Subsystem {
	private final double[] PID;
	private final double setpointOffset;

	private WPI_TalonSRX angleMotor;
	private CANSparkMax speedMotor;
	private PIDController pidController;
	private AnalogInput encoder;

	private double setpoint;
	public double error;
	public double output;
	public double encPID;
	public double rawSetpoint;
	public double mod;
	public double yOffset;
	
	public SwerveModule(int swerveModIndex, boolean inverted) {
		speedMotor = new CANSparkMax(swerveModIndex, MotorType.kBrushless);
		angleMotor = new WPI_TalonSRX(swerveModIndex);
		angleMotor.setInverted(inverted);
		encoder = new AnalogInput(swerveModIndex);
		PID = RobotMap.PIDconst[swerveModIndex];
		setpointOffset = RobotMap.setpointOffset[swerveModIndex];

		pidController = new PIDController(PID[0], PID[1], PID[2], encoder, angleMotor);
		pidController.setInputRange(0, RobotMap.circumference);
		pidController.setOutputRange(-1, 1);
		pidController.setContinuous(true);
		// pidController.setAbsoluteTolerance(RobotMap.toleranceAnglePID);
		pidController.enable();

		angleMotor.configContinuousCurrentLimit(30, 0);
		angleMotor.configPeakCurrentLimit(30, 0);
		angleMotor.configPeakCurrentDuration(100, 0);
		angleMotor.enableCurrentLimit(true);

		// System.out.println("is acc ch: " + encoder.isAccumulatorChannel());
		// encoder.initAccumulator();
		// encoder.setAccumulatorInitialValue(0);
		// encoder.resetAccumulator();
	}

	public double getEncoderCount(){
		return encoder.getValue();
	}

	public double getSetpoint(){
		return setpoint;
	}
	
	public void drive (double speed, double angle) { // angle = -1 to 1
		speedMotor.set (speed);

		setpoint = ((angle + 1.0) * RobotMap.circumference * 1000.0 / 2.0);
		setpoint = (setpoint + setpointOffset * 1000.0) % (RobotMap.circumference * 1000.0);
		pidController.setSetpoint(setpoint / 1000.0);

		// double halfCir = RobotMap.circumference/2;
		// setpoint = angle * halfCir; // -2.048 to 2.048
		// setpoint += setpointOffset;
		// rawSetpoint = setpoint;
		// mod = setpoint % halfCir;
		// yOffset = - Math.floor(setpoint/halfCir) * halfCir;
		// setpoint = setpoint % halfCir - Math.floor(setpoint/halfCir) * halfCir; // still -2.048 to 2.048

		// error = setpoint - encPID; // + or - diff 
		// output = PID[0] * error / halfCir; // kP * error / cir = motorPower
		// if (Math.abs(output) < RobotMap.toleranceAnglePID) { output = 0.0; }
		// angleMotor.set(output); // set motor

		error = pidController.getError();
		output = pidController.get();
		encPID = encoder.pidGet(); // -2.048 to 2.048
	}


    public void initDefaultCommand() {
		// NOTE: there should be no default command here 
		// unless you plan to run only one swerve module at a time

    }
}