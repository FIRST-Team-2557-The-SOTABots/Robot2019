package org.usfirst.frc.team2557.robot;

import org.usfirst.frc.team2557.robot.commands.arm.ArmWithAxis;
import org.usfirst.frc.team2557.robot.commands.arm.PIDarm;
import org.usfirst.frc.team2557.robot.commands.auto.segments.Segment1;
import org.usfirst.frc.team2557.robot.commands.lift.PIDlift;
import org.usfirst.frc.team2557.robot.subsystems.ArduinoSensors;
import org.usfirst.frc.team2557.robot.subsystems.Arm;
import org.usfirst.frc.team2557.robot.subsystems.Climber;
import org.usfirst.frc.team2557.robot.subsystems.GyroSwerveDrive;
import org.usfirst.frc.team2557.robot.subsystems.Intake;
import org.usfirst.frc.team2557.robot.subsystems.Lift;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	public static OI m_oi;
	public static GyroSwerveDrive gyroSwerveDrive;
	public static Lift lift;
	public static Intake intake;
	public static Arm arm;
	public static Climber climb;
	public static ArduinoSensors arduinoSensors;
	public static boolean prevArm;
	public static boolean defaultUnlockArm;

	PIDlift ma;
	PIDlift mb;
	PIDlift mx;
	PIDlift my;

	PIDarm pidarm;
	ArmWithAxis awa;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser;

	@Override
	public void robotInit() {
		prevArm = false;
		defaultUnlockArm = false;

		// NOTE: RobotMap MUST be initialized before subsystems
		RobotMap.init();

		gyroSwerveDrive = new GyroSwerveDrive();
		lift = new Lift();
		intake = new Intake();
		arm = new Arm();
		climb = new Climber();
		arduinoSensors = new ArduinoSensors();

		// NOTE: oi MUST be constructed after subsystems
		m_oi = new OI();
		m_chooser = new SendableChooser<>();

		ma = new PIDlift(RobotMap.lowPos);
		mb = new PIDlift(RobotMap.midPos);
		mx = new PIDlift(RobotMap.defPos);
		my = new PIDlift(RobotMap.highPos);

		pidarm = new PIDarm();
		awa = new ArmWithAxis();

		RobotMap.ds8inch.set(Value.kForward);
		RobotMap.ds12inch.set(Value.kForward);

		m_chooser.addDefault("Default Auto", null);
		// m_chooser.addObject("My Auto", new Segment1());        
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
		awa = new ArmWithAxis();
		awa.start();

		defaultUnlockArm = false;

		lift.initialize();
		arm.initialize();

		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	@Override
	public void teleopPeriodic() {
		if(Robot.m_oi.mback.get()){
			RobotMap.highPos = 495000; //?
			RobotMap.midPos = 120000; //*
			RobotMap.lowPos = -260000; //*
			RobotMap.defPos = 0; //
		}else{
			RobotMap.highPos = 460000; //?
			RobotMap.midPos = 205000; //*
			RobotMap.lowPos = -165000; //*
			RobotMap.defPos = 0; //
		}

		if(Robot.m_oi.ma.get()){
			ma.setSetpoint(RobotMap.lowPos);
			ma.start();
		}else{
			ma.cancel();
			// ma.close();
		}
		if(Robot.m_oi.mb.get()){
			mb.setSetpoint(RobotMap.midPos);
			mb.start();
		}else{
			mb.cancel();
			// mb.close();
		}
		if(Robot.m_oi.mx.get()){
			mx.setSetpoint(RobotMap.defPos);
			mx.start();
		}else{
			mx.cancel();
			// mx.close();
		}
		if(Robot.m_oi.my.get()){
			my.setSetpoint(RobotMap.highPos);
			my.start();
		}else{
			my.cancel();
			// my.close();
		}

		// Robot.m_oi.my.whileHeld(new PIDlift(RobotMap.highPos));
		// Robot.m_oi.mb.whileHeld(new PIDlift(RobotMap.midPos));
		// Robot.m_oi.mx.whileHeld(new PIDlift(RobotMap.defPos));
		// Robot.m_oi.ma.whileHeld(new PIDlift(RobotMap.lowPos));

		// SmartDashboard.putNumber("dpad val", Robot.m_oi.joystick2.getPOV());
		// SmartDashboard.putNumber("arm target val", RobotMap.armTarget);
		if(m_oi.joystick2.getPOV() > -1 && !prevArm){
			if(awa != null) { awa.cancel(); }
			pidarm.start();
			// SmartDashboard.putString("armCmd", "pidarm");
			prevArm = true;
			defaultUnlockArm = true;
		}else if(m_oi.joystick2.getPOV() == -1 && prevArm && (Robot.m_oi.joystick2.getRawAxis(1) <= -RobotMap.JOYSTICK_DEADBAND 
			|| Robot.m_oi.joystick2.getRawAxis(1) >= RobotMap.JOYSTICK_DEADBAND)){
			if(pidarm != null) { pidarm.cancel(); }
			awa.start();
			// SmartDashboard.putString("armCmd", "axis");
			prevArm = false;
		}
		SmartDashboard.putBoolean("Touch disc", RobotMap.disc.get());
		SmartDashboard.putBoolean("Touch cargo", RobotMap.cargo.get());

		// SmartDashboard.putNumber("getting POV", Robot.m_oi.joystick1.getPOV());
		// SmartDashboard.putNumber("getting POV stick2 ", Robot.m_oi.joystick2.getPOV());

		// SmartDashboard.putNumber("Get direction radians", Robot.m_oi.joystick1.getDirectionRadians());
		// SmartDashboard.putNumber("Get direction radians", Robot.m_oi.joystick1.getDirectionRadians());

		SmartDashboard.putNumber("arm left enc", RobotMap.armLeft.getSensorCollection().getQuadraturePosition());
		SmartDashboard.putNumber("arm right enc", RobotMap.armRight.getSensorCollection().getQuadraturePosition());
		
		SmartDashboard.putNumber("lift 2 enc", RobotMap.lift2.getSensorCollection().getQuadraturePosition());

		SmartDashboard.putNumber("high pos target", RobotMap.highPos);

		// SmartDashboard.putNumber("joystick axis 5", m_oi.joystick1.getRawAxis(5));
		// SmartDashboard.putNumber("gyro % 360: ", RobotMap.gyro.getAngle() % 360);

		// for(int i = 0; i < 4; i++){
		// 	SmartDashboard.putNumber("Encoder value " + i, RobotMap.swerveMod[i].encoder.pidGet());
		// 	SmartDashboard.putNumber("Encoder value degrees " + i, RobotMap.swerveMod[i].encoder.pidGet()*360/RobotMap.SWERVE_ENC_CIRC);
		// 	SmartDashboard.putNumber("Offset to zero " + i, (360 - RobotMap.swerveMod[i].encoder.pidGet()*360/RobotMap.SWERVE_ENC_CIRC) * RobotMap.SWERVE_ENC_CIRC/360);	
		// }
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}