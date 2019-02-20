package org.usfirst.frc.team2557.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SwerveModule extends Subsystem {
	private final double[] pidConstants;
	public double error;
	public double output;

	public WPI_TalonSRX angleMotor;
	public CANSparkMax speedMotor;
	public PIDController pidController;
	public AnalogInput encoder;

	public SwerveModule(int swerveModIndex, boolean angleMotorInverted) {
		speedMotor = new CANSparkMax(swerveModIndex, MotorType.kBrushless);
		angleMotor = new WPI_TalonSRX(swerveModIndex);
		angleMotor.setInverted(angleMotorInverted);
		encoder = new AnalogInput(swerveModIndex);

		pidConstants = RobotMap.SWERVE_PID_CONSTANTS[swerveModIndex];
		// pidController = new PIDController(pidConstants[0], pidConstants[1], pidConstants[2], 
		// 		encoder, angleMotor, RobotMap.SWERVE_LOOP_TIME);

		// pidController.setInputRange(0.0, RobotMap.SWERVE_ENC_CIRC);
		// pidController.setOutputRange(-1.0, 1.0);
		// pidController.setContinuous(true);
		// pidController.setAbsoluteTolerance(RobotMap.SWERVE_PID_TOLERANCE);
		// pidController.enable();

		// angleMotor.configContinuousCurrentLimit(RobotMap.SWERVE_MAX_CURRENT, 0);
		// angleMotor.configPeakCurrentLimit(RobotMap.SWERVE_MAX_CURRENT, 0);
		// angleMotor.configPeakCurrentDuration(RobotMap.SWERVE_CURRENT_DUR, 0);
		// angleMotor.enableCurrentLimit(true);
	}

	// angle and speed should be from -1.0 to 1.0, like a joystick input
	public void drive (double speed, double angle) {
		// pidController.setSetpoint(angle);
		angleMotor.set(angle);

		speedMotor.set (speed);
		// error = pidController.getError();
		// output = pidController.get();
	}


    public void initDefaultCommand() {
		// NOTE: no default command unless running swerve modules seperately
    }
}