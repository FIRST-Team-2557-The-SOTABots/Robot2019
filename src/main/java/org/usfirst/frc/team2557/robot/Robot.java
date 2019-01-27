package org.usfirst.frc.team2557.robot;

import org.usfirst.frc.team2557.robot.subsystems.GyroSwerveDrive;
import org.usfirst.frc.team2557.robot.subsystems.SwerveDrive;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	public static OI m_oi;
	public static SwerveDrive swerveDrive;
	// public static GyroSwerveDrive gyroSwerveDrive;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser;

	@Override
	public void robotInit() {
		// NOTE: RobotMap MUST be initialized before subsystems
		RobotMap.init();

		swerveDrive = new SwerveDrive();
		// gyroSwerveDrive = new GyroSwerveDrive();

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
		SmartDashboard.putNumber("encoder BL", RobotMap.swerveModBL.encCount);
		SmartDashboard.putNumber("encoder BR", RobotMap.swerveModBR.encCount);
		SmartDashboard.putNumber("encoder FL", RobotMap.swerveModFL.encCount);
		SmartDashboard.putNumber("encoder FR", RobotMap.swerveModFR.encCount);

		SmartDashboard.putNumber("setpoint BL", RobotMap.swerveModBL.setpoint);
		SmartDashboard.putNumber("setpoint BR", RobotMap.swerveModBR.setpoint);
		SmartDashboard.putNumber("setpoint FL", RobotMap.swerveModFL.setpoint);
		SmartDashboard.putNumber("setpoint FR", RobotMap.swerveModFR.setpoint);

		SmartDashboard.putNumber("adjsetpoint BL", RobotMap.swerveModBL.adjSetpoint);
		SmartDashboard.putNumber("adjsetpoint BR", RobotMap.swerveModBR.adjSetpoint);
		SmartDashboard.putNumber("adjsetpoint FL", RobotMap.swerveModFL.adjSetpoint);
		SmartDashboard.putNumber("adjsetpoint FR", RobotMap.swerveModFR.adjSetpoint);

		SmartDashboard.putNumber("error BL", RobotMap.swerveModBL.error);
		SmartDashboard.putNumber("error BR", RobotMap.swerveModBR.error);
		SmartDashboard.putNumber("error FL", RobotMap.swerveModFL.error);
		SmartDashboard.putNumber("error FR", RobotMap.swerveModFR.error);

		SmartDashboard.putNumber("output BL", RobotMap.swerveModBL.output);
		SmartDashboard.putNumber("output BR", RobotMap.swerveModBR.output); 
		SmartDashboard.putNumber("output FL", RobotMap.swerveModFL.output);
		SmartDashboard.putNumber("output FR", RobotMap.swerveModFR.output);

		SmartDashboard.putNumber("drive angle BL", Robot.swerveDrive.angleBL);
		SmartDashboard.putNumber("drive angle BR", Robot.swerveDrive.angleBR);
		SmartDashboard.putNumber("drive angle FL", Robot.swerveDrive.angleFL);
		SmartDashboard.putNumber("drive angle FR", Robot.swerveDrive.angleFR);

		SmartDashboard.putNumber("drive speed BL", Robot.swerveDrive.speedBL);
		SmartDashboard.putNumber("drive speed BR", Robot.swerveDrive.speedBR);
		SmartDashboard.putNumber("drive speed FL", Robot.swerveDrive.speedFL);
		SmartDashboard.putNumber("drive speed FR", Robot.swerveDrive.speedFR);

		SmartDashboard.putNumber("a", Robot.swerveDrive.a);
		SmartDashboard.putNumber("b", Robot.swerveDrive.b);
		SmartDashboard.putNumber("c", Robot.swerveDrive.c);
		SmartDashboard.putNumber("d", Robot.swerveDrive.d);

		SmartDashboard.putNumber("joystick axis 5", m_oi.joystick.getRawAxis(5));
		SmartDashboard.putNumber("joystick axis 4", m_oi.joystick.getRawAxis(4));
		SmartDashboard.putNumber("gyro: ", RobotMap.gyro.getAngle());

		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}