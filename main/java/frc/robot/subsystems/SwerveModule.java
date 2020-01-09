package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;

public class SwerveModule extends SubsystemBase {
	private final double[] pidConstants;
	public double error;
	public double output;

	public WPI_TalonSRX angleMotor;
	public CANSparkMax speedMotor;
	public PIDController pidController;
	public AnalogInput encoder;

	public SwerveModule(int swerveModIndex, boolean angleMotorInverted) {
		speedMotor = new CANSparkMax(swerveModIndex + 11, MotorType.kBrushless);
		angleMotor = new WPI_TalonSRX(swerveModIndex);
		angleMotor.setInverted(angleMotorInverted);
		encoder = new AnalogInput(swerveModIndex);

		pidConstants = Constants.SWERVE_PID_CONSTANTS[swerveModIndex];
		pidController = new PIDController(pidConstants[0], pidConstants[1], pidConstants[2], Constants.SWERVE_LOOP_TIME);
		pidController.enableContinuousInput(0, Constants.SWERVE_ENC_CIRC);
		pidController.setTolerance(Constants.SWERVE_PID_TOLERANCE);

		angleMotor.configContinuousCurrentLimit(Constants.SWERVE_MAX_CURRENT, 0);
		angleMotor.configPeakCurrentLimit(Constants.SWERVE_MAX_CURRENT, 0);
		angleMotor.configPeakCurrentDuration(Constants.SWERVE_CURRENT_DUR, 0);
		angleMotor.enableCurrentLimit(true);
	}

	// angle and speed should be from -1.0 to 1.0, like a joystick input
	public void drive (double speed, double angle) {
		// pidController.setSetpoint(angle);
		output = pidController.calculate(encoder.pidGet(), angle);
		angleMotor.set(MathUtil.clamp(output, -1.0, 1.0));

		speedMotor.set (speed);
		error = pidController.getPositionError();
	}


    public void initDefaultCommand() {
			// NOTE: no default command unless running swerve modules seperately
    }
}