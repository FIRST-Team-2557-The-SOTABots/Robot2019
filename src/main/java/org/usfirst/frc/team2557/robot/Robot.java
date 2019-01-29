package org.usfirst.frc.team2557.robot;

import org.usfirst.frc.team2557.robot.subsystems.GyroSwerveDrive;
// import org.usfirst.frc.team2557.robot.subsystems.SwerveDrive;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	public static OI m_oi;
	// public static SwerveDrive swerveDrive;
	public static GyroSwerveDrive gyroSwerveDrive;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser;

	@Override
	public void robotInit() {
		// NOTE: RobotMap MUST be initialized before subsystems
		RobotMap.init();

		// swerveDrive = new SwerveDrive();
		gyroSwerveDrive = new GyroSwerveDrive();

		// NOTE: oi MUST be constructed after subsystems
		m_oi = new OI();
		m_chooser = new SendableChooser<>();
		// m_chooser.addDefault("Default Auto", new ExampleCommand());
		// m_chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
	}

	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();

		/* meant for picking up vision values, needs testing after swerve works */
		// NetworkTable contoursTable = NetworkTableInstance.getDefault().getTable("/vision/contours");
		// String[] labels = {"x", "y", "h", "w", "area", "dist", "rot"};
		// double[][] data = new double[7][0];
		// contoursTable.getEntry("centerX").getDoubleArray(data[0]);
		// contoursTable.getEntry("centerY").getDoubleArray(data[1]);
		// contoursTable.getEntry("height").getDoubleArray(data[2]);
		// contoursTable.getEntry("width").getDoubleArray(data[3]);
		// contoursTable.getEntry("area").getDoubleArray(data[4]);
		// contoursTable.getEntry("distance").getDoubleArray(data[5]);
		// contoursTable.getEntry("rotation").getDoubleArray(data[6]);
		// for (int i = 0; i < data.length; i++) {
		// 	System.out.println(labels[i] + ": " + data[i].toString());
		// }
	}

	@Override
	public void teleopInit() {
		// makes autonomous stop running when teleop starts if you want 
		// autonomous to continue until interrupted remove it/comment out
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	@Override
	public void teleopPeriodic() {
		SmartDashboard.putNumber("joystick axis 5", m_oi.joystick.getRawAxis(5));
		SmartDashboard.putNumber("gyro % 360: ", RobotMap.gyro.getAngle() % 360);
		SmartDashboard.putNumber("gyro: ", RobotMap.gyro.getAngle());

		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}