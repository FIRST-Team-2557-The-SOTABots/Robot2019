package org.usfirst.frc.team2557.robot;

import org.usfirst.frc.team2557.robot.commands.arm.ArmWithAxis;
import org.usfirst.frc.team2557.robot.commands.arm.PIDarm;
import org.usfirst.frc.team2557.robot.commands.auto.AutoDriveCommand;
import org.usfirst.frc.team2557.robot.commands.auto.segments.Segment1;
import org.usfirst.frc.team2557.robot.subsystems.Arm;
import org.usfirst.frc.team2557.robot.subsystems.Climber;
import org.usfirst.frc.team2557.robot.subsystems.GyroSwerveDrive;
import org.usfirst.frc.team2557.robot.subsystems.Intake;
import org.usfirst.frc.team2557.robot.subsystems.Lift;
import org.usfirst.frc.team2557.robot.subsystems.SwerveDrive;
// import org.usfirst.frc.team2557.robot.TrajectoryGenerator;
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

	PIDarm pidarm;
	ArmWithAxis awa;

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

		// NOTE: oi MUST be constructed after subsystems
		m_oi = new OI();
		m_chooser = new SendableChooser<>();

		RobotMap.ds8inch.set(Value.kForward);
		RobotMap.ds12inch.set(Value.kForward);

		// m_chooser.addDefault("Default Auto", new Segment1());
		// m_chooser.addObject("My Auto", new Segment1());
		SmartDashboard.putData("Auto mode", m_chooser);

	}

	@Override
	public void disabledInit() {
		// pidarm.close();
		// awa.cancel();
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
		awa = new ArmWithAxis();
		
		RobotMap.lift1.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		RobotMap.lift2.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		RobotMap.lift3.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);

		RobotMap.armRight.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
		RobotMap.armLeft.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);

		// RobotMap.ds8inch.set(Value.kForward);
		// RobotMap.armLeft.getSensorCollection().setQuadraturePosition(0, 10);
		// RobotMap.armRight.getSensorCollection().setQuadraturePosition(0, 10);
		RobotMap.lift2.getSensorCollection().setQuadraturePosition(0, 10);
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	@Override
	public void teleopPeriodic() {

		SmartDashboard.putNumber("dpad val", Robot.m_oi.joystick2.getPOV());
		SmartDashboard.putNumber("arm target val", RobotMap.armTarget);
		if(m_oi.joystick2.getPOV() > -1){
			if(awa != null) { awa.cancel(); }
			pidarm = new PIDarm();
			pidarm.start();
			SmartDashboard.putString("armCmd", "pidarm");
	
		}else{
			if(pidarm != null) { pidarm.cancel(); }
			awa = new ArmWithAxis();
			awa.start();
			SmartDashboard.putString("armCmd", "axis");
		}

		// setting 12 to foward also seems to make the top ones go up?
		//foward extends outwards. Reverse to go inward
		// RobotMap.ds12inch.set(Value.kForward);
		// RobotMap.ds12inch.set(Value.kReverse);

		//8 inch is the top one. And reverse goes up, maybe
		//reverse made the lower retract
		// RobotMap.ds8inch.set(Value.kForward);
		// RobotMap.ds8inch.set(Value.kReverse);


		//the arm does lock. Reverse is locked. Foward is unlocked
		// RobotMap.dsArmLock.set(Value.kReverse);  

		//high gear is kFoward when you want to lift. position when in low gearlow gear when climb
		//low gear is when you move. High gear is kFoward and when it wants to go slower
		// RobotMap.dsLift.set(Value.kForward);

		//forward shoots in out. Reverse retracts
		// RobotMap.dsIntake.set(Value.kForward);

		// if(m_oi.joystick2.getPOV() != -1)new This();

		SmartDashboard.putBoolean("1", RobotMap.touch1.get());
		SmartDashboard.putBoolean("2", RobotMap.touch2.get());

		SmartDashboard.putNumber("getting POV", Robot.m_oi.joystick1.getPOV());
		SmartDashboard.putNumber("getting POV stick2 ", Robot.m_oi.joystick2.getPOV());

		// SmartDashboard.putNumber("POV inttin", Robot.m_oi.joystick1.getPOV(0));
		// SmartDashboard.putNumber("POV inttin", Robot.m_oi.joystick2.getPOV(0));

		SmartDashboard.putNumber("Get direction radians", Robot.m_oi.joystick1.getDirectionRadians());
		SmartDashboard.putNumber("Get direction radians", Robot.m_oi.joystick1.getDirectionRadians());

		SmartDashboard.putNumber("arm left", RobotMap.armLeft.getSensorCollection().getQuadraturePosition());

		SmartDashboard.putNumber("arm right", RobotMap.armRight.getSensorCollection().getQuadraturePosition());
		
		SmartDashboard.putNumber("lift 2", RobotMap.lift2.getSensorCollection().getQuadraturePosition());

		RobotMap.armLeft.getSensorCollection().getQuadraturePosition();
		RobotMap.armLeft.getSensorCollection().getQuadraturePosition();

		SmartDashboard.putNumber("joystick axis 5", m_oi.joystick1.getRawAxis(5));
		SmartDashboard.putNumber("gyro % 360: ", RobotMap.gyro.getAngle() % 360);

		for(int i = 0; i < 4; i++){
			SmartDashboard.putNumber("Encoder value " + i, RobotMap.swerveMod[i].encoder.pidGet());
			SmartDashboard.putNumber("Encoder value degrees " + i, RobotMap.swerveMod[i].encoder.pidGet()*360/RobotMap.SWERVE_ENC_CIRC);
			SmartDashboard.putNumber("Offset to zero " + i, (360 - RobotMap.swerveMod[i].encoder.pidGet()*360/RobotMap.SWERVE_ENC_CIRC) * RobotMap.SWERVE_ENC_CIRC/360);	
		}
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}