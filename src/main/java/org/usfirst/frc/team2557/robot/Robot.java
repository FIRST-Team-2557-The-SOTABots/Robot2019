package org.usfirst.frc.team2557.robot;

import org.usfirst.frc.team2557.robot.subsystems.Arm;
import org.usfirst.frc.team2557.robot.subsystems.Climber;
import org.usfirst.frc.team2557.robot.subsystems.GyroSwerveDrive;
import org.usfirst.frc.team2557.robot.subsystems.Intake;
import org.usfirst.frc.team2557.robot.subsystems.Lift;
import org.usfirst.frc.team2557.robot.subsystems.SwerveDrive;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	public static OI m_oi;
	public static SwerveDrive swerveDrive;
	public static GyroSwerveDrive gyroSwerveDrive;
	public static Lift lift;
	public static Intake intake;
	public static Arm arm;
	public static Climber climb;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser;

	@Override
	public void robotInit() {
		// NOTE: RobotMap MUST be initialized before subsystems
		RobotMap.init();

		swerveDrive = new SwerveDrive();
		gyroSwerveDrive = new GyroSwerveDrive();
		lift = new Lift();
		intake = new Intake();
		arm = new Arm();
		climb = new Climber();


		RobotMap.armLeft.getSensorCollection().setQuadraturePosition(0, 10);
		RobotMap.armRight.getSensorCollection().setQuadraturePosition(0, 10);
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
	}

	@Override
	public void teleopInit() {
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	@Override
	public void teleopPeriodic() {

		//setting 12 to foward also seems to make the top ones go up?
		// RobotMap.ds12inch.set(Value.kForward);

		//8 inch is the top one. And reverse goes up, maybe
		// RobotMap.ds8inch.set(Value.kReverse);

		//the arm does lock. Reverse is locked. Foward is unlocked
		// RobotMap.dsArmLock.set(Value.kForward);  

		//high gear up. position when in low gearlow gear when climb
		// RobotMap.dsLift.set(Value.kReverse);

		//forward shoots in out. Reverse retracts
		// RobotMap.dsIntake.set(Value.kForward);

		SmartDashboard.putNumber("arm left", RobotMap.armLeft.getSensorCollection().getQuadraturePosition());

		SmartDashboard.putNumber("arm right", RobotMap.armRight.getSensorCollection().getQuadraturePosition());

		RobotMap.armLeft.getSensorCollection().getQuadraturePosition();
		RobotMap.armLeft.getSensorCollection().getQuadraturePosition();



		SmartDashboard.putNumber("joystick axis 5", m_oi.joystick1.getRawAxis(5));
		SmartDashboard.putNumber("gyro % 360: ", RobotMap.gyro.getAngle() % 360);

		for(int i = 0; i < 4; i++){
			SmartDashboard.putNumber("Encoder value" + i, RobotMap.swerveMod[i].encoder.pidGet());
			SmartDashboard.putNumber("Encoder value degrees" + i, RobotMap.swerveMod[i].encoder.pidGet()*360/RobotMap.SWERVE_ENC_CIRC);
			SmartDashboard.putNumber("Offset to zero" + i, (360 - RobotMap.swerveMod[i].encoder.pidGet()*360/RobotMap.SWERVE_ENC_CIRC) * RobotMap.SWERVE_ENC_CIRC/360);	
		}
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}