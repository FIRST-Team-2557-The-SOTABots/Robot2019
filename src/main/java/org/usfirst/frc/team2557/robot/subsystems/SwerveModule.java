package org.usfirst.frc.team2557.robot.subsystems;

import java.lang.Math;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2557.robot.AdjustedEncoder;

public class SwerveModule extends Subsystem {
	private final double[] PID;
	private final double setpointOffset;

	private WPI_TalonSRX angleMotor;
	private CANSparkMax speedMotor;
	private PIDController pidController;
	private AnalogInput encoder;
	private AdjustedEncoder adjEnc;

	private double setpoint;
	public double error;
	public double output;
	public double encPID;
	public double rawSetpoint;
	public double mod;
	public double yOffset;

	// public int sec;
	// public Timer timer;
	
	public SwerveModule(int swerveModIndex, boolean inverted) {
		speedMotor = new CANSparkMax(swerveModIndex, MotorType.kBrushless);
		angleMotor = new WPI_TalonSRX(swerveModIndex);
		angleMotor.setInverted(inverted);
		encoder = new AnalogInput(swerveModIndex);
		adjEnc = new AdjustedEncoder(encoder);

		PID = RobotMap.PIDconst[swerveModIndex];
		setpointOffset = RobotMap.setpointOffset[swerveModIndex];

		pidController = new PIDController(PID[0], PID[1], PID[2], adjEnc, angleMotor, RobotMap.pidLoopTime);
		pidController.setInputRange(0.0, RobotMap.circumference);
		pidController.setOutputRange(-1.0, 1.0);
		pidController.setContinuous(true);
		pidController.setAbsoluteTolerance(RobotMap.toleranceAnglePID);
		pidController.enable();

		angleMotor.configContinuousCurrentLimit(30, 0);
		angleMotor.configPeakCurrentLimit(30, 0);
		angleMotor.configPeakCurrentDuration(100, 0);
		angleMotor.enableCurrentLimit(true);

		// timer = new Timer();
		// timer.reset();
		// timer.start();
		// sec = 2;
	}

	public double getEncoderCount(){
		return encoder.getValue();
	}

	public double getSetpoint(){
		return setpoint;
	}
	
	public void drive (double speed, double angle) { // angle = -1 to 1
		speedMotor.set (speed);

		setpoint = (angle + 1.0) * RobotMap.circumference / 2.0;
		setpoint = setpoint + setpointOffset;
		setpoint %= RobotMap.circumference;
		// System.out.print("setpoint: " + setpoint);

		adjEnc.setSetpoint(setpoint);
		pidController.setSetpoint(setpoint);

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

		// error = pidController.getError();
		// System.out.print(" error: " + error);
		// output = pidController.get();
		// System.out.print(" output: " + output);
		// encPID = encoder.pidGet(); // -2.048 to 2.048
		// System.out.print(" encPID: " + encPID);
		// System.out.println(" time: " + timer.get());

		// if(timer.get() > sec){
		// 	sec += 2;
		// 	if(sec/2 % 2 == 0){
		// 		pidController.setSetpoint(0.0);
		// 	}else{
		// 		pidController.setSetpoint(2.0);
		// 	}
		// }
		// timer.reset();
	}


    public void initDefaultCommand() {
		// NOTE: should be no default command here unless
		// planning to run swerve modules independantly
    }
}