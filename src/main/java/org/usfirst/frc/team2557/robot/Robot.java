package org.usfirst.frc.team2557.robot;

import java.nio.ByteBuffer;

import org.usfirst.frc.team2557.robot.commands.arm.ArmWithAxis;
import org.usfirst.frc.team2557.robot.commands.arm.PIDarm;
import org.usfirst.frc.team2557.robot.commands.auto.segments.Segment1;
import org.usfirst.frc.team2557.robot.commands.drive.VisionDriveStraightOn;
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
	public static TrajectoryGenerator tg;
	public static GyroSwerveDrive gyroSwerveDrive;
	public static Lift lift;
	public static Intake intake;
	public static Arm arm;
	public static Climber climb;
	public static ArduinoSensors arduinoSensors;
	public static boolean prevArm;
	public static boolean defaultUnlockArm;
	public static String str = "";

	PIDlift ma;
	PIDlift mb;
	PIDlift my;
	PIDlift mx;
	VisionDriveStraightOn vdso;

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

		tg = new TrajectoryGenerator();
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
		my = new PIDlift(RobotMap.highPos);
		mx = new PIDlift(RobotMap.intakePosCargo);

		vdso = new VisionDriveStraightOn();

		pidarm = new PIDarm();
		awa = new ArmWithAxis();

		m_chooser.addOption("Default Auto", null);
		m_chooser.addOption("My Auto", new Segment1());        
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
		awa = new ArmWithAxis();
		awa.start();

		defaultUnlockArm = false;

		lift.init();
		arm.init();
		// RobotMap.armLeft.getSensorCollection().setQuadraturePosition(0, 10);
		RobotMap.armRight.getSensorCollection().setQuadraturePosition(0, 10);
		RobotMap.lift2.getSensorCollection().setQuadraturePosition(0, 10);
		RobotMap.climber.getSensorCollection().setQuadraturePosition(0, 10);

		m_autonomousCommand = m_chooser.getSelected();

		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		
		RobotMap.gyro.reset();
	}

	@Override
	public void autonomousPeriodic() {
		boolean armLock = false;
		if(RobotMap.dsArmLock.get() == Value.kReverse){
			armLock = true;
		}
		SmartDashboard.putBoolean("armLock", armLock);
		if(Robot.m_oi.ma.get()){
			ma.setSetpoint(RobotMap.lowPos);
			ma.start();
		}else{
			ma.cancel();
		}
		if(Robot.m_oi.mb.get()){
			mb.setSetpoint(RobotMap.midPos);
			mb.start();
		}else{
			mb.cancel();
		}
		if(Robot.m_oi.my.get()){
			my.setSetpoint(RobotMap.highPos);
			my.start();
		}else{
			my.cancel();
		}
		if(Robot.m_oi.mx.get()){
			mx.setSetpoint(RobotMap.intakePosCargo);
			mx.start();
		}else{
			mx.cancel();
		}

		// if(m_oi.bumperLeft.get()){
		// 	vdso.start();
		// 	SmartDashboard.putBoolean("VISION", true);
		// }else{
		// 	vdso.cancel();
		// 	SmartDashboard.putBoolean("VISION", false);
		// }

		if(m_oi.joystick2.getPOV() > -1 && !prevArm){
			if(awa != null) { awa.cancel(); }
			pidarm.start();
			prevArm = true;
			defaultUnlockArm = true;
		}else if(m_oi.joystick2.getPOV() == -1 && prevArm && (Robot.m_oi.joystick2.getRawAxis(1) <= -RobotMap.JOYSTICK_DEADBAND 
			|| Robot.m_oi.joystick2.getRawAxis(1) >= RobotMap.JOYSTICK_DEADBAND)){
			if(pidarm != null) { pidarm.cancel(); }
			awa.start();
			prevArm = false;
		}
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		awa.start();
		defaultUnlockArm = false;

		// lift.initialize();
		// arm.initialize();

		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}

		// Robot.tg.trajectory0();
		// System.out.println("trajectory0 written");
	}

	@Override
	public void teleopPeriodic() {
		boolean armLock = false;
		if(RobotMap.dsArmLock.get() == Value.kReverse){
			armLock = true;
		}
		SmartDashboard.putBoolean("armLock", armLock);

		if(Robot.m_oi.mback.get()){
			RobotMap.highPos = RobotMap.highPosCargo;
			RobotMap.midPos = RobotMap.midPosCargo; 
			RobotMap.lowPos = RobotMap.lowPosCargo;
			RobotMap.xPos = RobotMap.backX;
		}else{
			RobotMap.highPos = RobotMap.highPosHatch;
			RobotMap.midPos = RobotMap.midPosHatch; 
			RobotMap.lowPos = RobotMap.lowPosHatch;
			RobotMap.xPos = RobotMap.intakePosCargo;
		}

		// if(m_oi.bumperLeft.get()){
		// 	vdso.start();
		// 	SmartDashboard.putBoolean("VISION", true);
		// }else{
		// 	vdso.cancel();
		// 	SmartDashboard.putBoolean("VISION", false);
		// }
		
		if(Robot.m_oi.ma.get()){
			ma.setSetpoint(RobotMap.lowPos);
			ma.start();
		}else{
			ma.cancel();
		}
		if(Robot.m_oi.mb.get()){
			mb.setSetpoint(RobotMap.midPos);
			mb.start();
		}else{
			mb.cancel();
		}
		if(Robot.m_oi.my.get()){
			my.setSetpoint(RobotMap.highPos);
			my.start();
		}else{
			my.cancel();
		}
		if(Robot.m_oi.mx.get()){
			mx.setSetpoint(RobotMap.intakePosCargo);
			mx.start();
		}else{
			mx.cancel();
		}

		if(m_oi.joystick2.getPOV() > -1 && !prevArm){
			if(awa != null) { awa.cancel(); }
			pidarm.start();
			prevArm = true;
			defaultUnlockArm = true;
		}else if(m_oi.joystick2.getPOV() == -1 && prevArm && (Robot.m_oi.joystick2.getRawAxis(1) <= -RobotMap.JOYSTICK_DEADBAND 
			|| Robot.m_oi.joystick2.getRawAxis(1) >= RobotMap.JOYSTICK_DEADBAND)){
			if(pidarm != null) { pidarm.cancel(); }
			awa.start();
			prevArm = false;
		}

		SmartDashboard.putNumber("Arm target", RobotMap.armTarget);
		SmartDashboard.putBoolean("Touch cargo1", RobotMap.cargo.get());
		SmartDashboard.putBoolean("Touch cargo2", RobotMap.cargo2.get());
		SmartDashboard.putNumber("arm right enc", RobotMap.armRight.getSensorCollection().getQuadraturePosition());
		SmartDashboard.putNumber("lift 2 enc", RobotMap.lift2.getSensorCollection().getQuadraturePosition());
		SmartDashboard.putNumber("climb", RobotMap.climber.getSensorCollection().getQuadraturePosition());

		for(int i = 0; i < 4; i++){
			SmartDashboard.putNumber("Encoder value " + i, RobotMap.swerveMod[i].encoder.pidGet());
			// SmartDashboard.putNumber("spark pos" + i, RobotMap.swerveMod[i].speedMotor.getEncoder().getPosition());
			// SmartDashboard.putNumber("spark velocity" + i, RobotMap.swerveMod[i].speedMotor.getEncoder().getVelocity());
			// SmartDashboard.putNumber("Encoder value degrees " + i, RobotMap.swerveMod[i].encoder.pidGet()*360/RobotMap.SWERVE_ENC_CIRC);
			// SmartDashboard.putNumber("Offset to zero " + i, (360 - RobotMap.swerveMod[i].encoder.pidGet()*360/RobotMap.SWERVE_ENC_CIRC) * RobotMap.SWERVE_ENC_CIRC/360);	
		}
		Scheduler.getInstance().run();

		// if(RobotMap.serial.getBytesReceived() == 0){
		// 	return;
		// }
		// str += RobotMap.serial.readString();
		// String[] tof1Vals = str.split("tof number 1:([0-9]*\n");
		// String[] tof2Vals = str.split("tof number 2:([0-9]*\n");
		// int next = str.lastIndexOf('\n');
		// if(next != -1){
		// 	str = str.substring(next);
		// }
	}

	@Override
	public void testPeriodic() {
	}
}