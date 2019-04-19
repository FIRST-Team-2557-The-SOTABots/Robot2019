package org.usfirst.frc.team2557.robot;

import java.util.ArrayList;
import org.usfirst.frc.team2557.robot.commands.arm.ArmWithAxis;
import org.usfirst.frc.team2557.robot.commands.arm.PIDarm;
import org.usfirst.frc.team2557.robot.commands.climb.ClimbCommandGroup;
import org.usfirst.frc.team2557.robot.commands.climb.RetractClimb;
import org.usfirst.frc.team2557.robot.commands.drive.VisionWithGyro;
import org.usfirst.frc.team2557.robot.commands.lift.PIDlift;
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
	// public static TrajectoryGenerator tg;
	public static GyroSwerveDrive gyroSwerveDrive;
	public static Lift lift;
	public static Intake intake;
	public static Arm arm;
	public static Climber climber;
	public static boolean defaultUnlockArm;
	
	boolean prevArm;
	boolean prevClimbWas3;
	String str = "";
	ArrayList<String> list = new ArrayList<String>();
	boolean climbed;
	PIDlift ma;
	PIDlift mb;
	PIDlift my;
	PIDlift mx;
	VisionWithGyro vwg;
	ClimbCommandGroup c3;
	RetractClimb rc3;
	RetractClimb rc2;
	ClimbCommandGroup c2;
	PIDarm pidarm;
	ArmWithAxis awa;
	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser;

	@Override
	public void robotInit() {
		prevArm = false;
		defaultUnlockArm = false;
		climbed = false;
		prevClimbWas3 = true;

		// NOTE: RobotMap MUST be initialized before subsystems
		RobotMap.init();
		// RobotMap.arduino.put("ToFL", 0);
		// RobotMap.arduino.put("ToFR", 0);

		// tg = new TrajectoryGenerator();
		gyroSwerveDrive = new GyroSwerveDrive();
		lift = new Lift();
		intake = new Intake();
		arm = new Arm();
		climber = new Climber();

		// NOTE: oi MUST be constructed after subsystems
		m_oi = new OI();
		m_chooser = new SendableChooser<>();

		ma = new PIDlift(RobotMap.lowPos);
		mb = new PIDlift(RobotMap.midPos);
		my = new PIDlift(RobotMap.highPos);
		mx = new PIDlift(RobotMap.xPos);
		vwg = new VisionWithGyro();
		c3 = new ClimbCommandGroup(RobotMap.climb3);
		rc3 = new RetractClimb(RobotMap.climb03);
		rc2 = new RetractClimb(RobotMap.climb02);
		c2 = new ClimbCommandGroup(RobotMap.climb2);
		pidarm = new PIDarm();
		awa = new ArmWithAxis();

		m_chooser.addOption("Default Auto", null);
		// m_chooser.addOption("My Auto", new Segment1());
		SmartDashboard.putData("Auto mode", m_chooser);

		for(int i = 0; i < 4; i++){
			RobotMap.swerveMod[i].speedMotor.setSmartCurrentLimit(40);
			RobotMap.swerveMod[i].speedMotor.setClosedLoopRampRate(0.15);
			RobotMap.swerveMod[i].speedMotor.setOpenLoopRampRate(0.15);
		}
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
		awa.start();
		defaultUnlockArm = false;
		gyroSwerveDrive.driveStraight(0);
		climber.climb(0);

		for(int i = 0; i < 4; i++) RobotMap.swerveMod[i].speedMotor.getEncoder();
		RobotMap.armRight.getSensorCollection().setQuadraturePosition(0, 10);
		RobotMap.lift2.getSensorCollection().setQuadraturePosition(0, 10);
		RobotMap.climber.getSensorCollection().setQuadraturePosition(0, 10);
		RobotMap.gyro.reset();
		
		m_autonomousCommand = m_chooser.getSelected();
		if (m_autonomousCommand != null) m_autonomousCommand.start();
	}

	@Override
	public void autonomousPeriodic() {
		driverVision();
		manipArm();
		manipLift();
		smartdashboarding();
		Scheduler.getInstance().run();
		// readTofs();
	}

	@Override
	public void teleopInit() {
		if(!awa.isRunning()) awa.start();
		defaultUnlockArm = false;
		gyroSwerveDrive.driveStraight(0);
		if (m_autonomousCommand != null) m_autonomousCommand.cancel();


		// for(int i = 0; i < 4; i++) RobotMap.swerveMod[i].speedMotor.getEncoder().setPosition(0);

		// Robot.tg.trajectory0();
		// System.out.println("trajectory0 written");
	}

	@Override
	public void teleopPeriodic() {
		driverVision();
		driverClimb();
		manipArm();
		manipLift();
		smartdashboarding();
		Scheduler.getInstance().run();
		// readTofs();
	}

	public void driverVision(){
		if(m_oi.joystick1.getRawAxis(2) > 0.5 || m_oi.joystick1.getRawAxis(3) > 0.5 || m_oi.da.get() || m_oi.db.get() || m_oi.dx.get() || m_oi.dy.get()) vwg.start();
		else vwg.cancel();
	}

	public void driverClimb(){
		if(Robot.m_oi.joystick1.getPOV() == 90){
			climber.lock(false);
		}else if(m_oi.dback.get() /* && RobotMap.climber */){ // ** ?? //
			if(prevClimbWas3){
				rc3.start();
			}else{
				rc2.start();
			}
			c3.cancel();
		}else if(Robot.m_oi.joystick1.getPOV() == 0){
			c2.start();
			prevClimbWas3 = false;
		}else if(m_oi.dstart.get()){
			c3.start();
			prevClimbWas3 = true;
		}else if(Robot.m_oi.joystick1.getPOV() == 270){
			c3.cancel();
			rc2.cancel();
			rc3.cancel();
			c2.cancel();
			climber.lock(false);
			RobotMap.climber.set (RobotMap.climberEncoderDirection * 0.75);
		}else{
			c3.cancel();
			rc2.cancel();
			rc3.cancel();
			c2.cancel();
			climber.lock(true);
			RobotMap.climber.set (0);
		}
	}

	public void manipArm(){
		if (m_oi.joystick2.getPOV() > -1 && !prevArm) {
			if (awa != null) {
				awa.cancel();
			}
			pidarm.start();
			prevArm = true;
			defaultUnlockArm = true;
		} else if (m_oi.joystick2.getPOV() == -1 && prevArm && m_oi.mbumperRight.get()) {
			if (pidarm != null) {
				pidarm.cancel();
			}
			awa.start();
			prevArm = false;
		}
	}

	public void manipLift(){
		if (Robot.m_oi.mback.get()) {
			RobotMap.highPos = RobotMap.backY;
			RobotMap.midPos = RobotMap.backB;
			RobotMap.lowPos = RobotMap.backA;
			RobotMap.xPos = RobotMap.backX;
		} else {
			RobotMap.highPos = RobotMap.Y;
			RobotMap.midPos = RobotMap.B;
			RobotMap.lowPos = RobotMap.A;
			RobotMap.xPos = RobotMap.X;
		}

		if (Robot.m_oi.ma.get()) {
			mb.setSetpoint(RobotMap.lowPos);
			ma.start();
		} else ma.cancel();
    
		if (Robot.m_oi.mb.get()) {
			mb.setSetpoint(RobotMap.midPos);
			if(Robot.m_oi.joystick2.getPOV() == 270){
				my.setSetpoint(RobotMap.midPos + 30000);
			}
			mb.start();
		} else mb.cancel();
		
		if (Robot.m_oi.my.get()) {
			my.setSetpoint(RobotMap.highPos);
			if(Robot.m_oi.joystick2.getPOV() == 270){
				my.setSetpoint(RobotMap.highPos + 30000);
			}
			my.start();
		} else my.cancel();

		if (Robot.m_oi.mx.get()) {
			mx.setSetpoint(RobotMap.xPos);
			mx.start();
		} else mx.cancel();
	}

	// public void readTofs(){
	// 	if (RobotMap.serial.getBytesReceived() == 0) return;

	// 	str += RobotMap.serial.readString();
	// 	while (str.indexOf('\n') != -1) {
	// 		list.add(str.substring(0, str.indexOf('\n')));
	// 		str = str.substring(str.indexOf('\n') + 1);
	// 	}

	// 	for (int i = 0; i < list.size(); i++) {
	// 		String temp = list.get(i);
	// 		for (String key : RobotMap.arduino.keySet()) {
	// 			if (temp.contains(key)) {
	// 				String[] arr = temp.split(key);
	// 				parseNumber(arr[1], 0, key);
	// 			}
	// 		}
	// 		list.remove(i);
	// 	}
	// 	RobotMap.tofAngle = Math.toDegrees(Math.atan2((RobotMap.arduino.get("ToFR") - RobotMap.arduino.get("ToFL")),RobotMap.TofDistance));
	// 	RobotMap.tofAngle = (Math.round(RobotMap.tofAngle*100))/100.0;

	// 	SmartDashboard.putString("tof values", RobotMap.arduino.values().toString());
	// }

	public void smartdashboarding(){
		// SmartDashboard.putNumber("Pitch", RobotMap.gyro.getPitch());
		SmartDashboard.putBoolean("dsArmLock", RobotMap.dsArmLock.get() == Value.kReverse);
		SmartDashboard.putNumber("Gyro", RobotMap.gyro.getAngle());
		// SmartDashboard.putNumber("Arm target", RobotMap.armTarget);
		// SmartDashboard.putNumber("Arm axis", m_oi.joystick2.getRawAxis(1));
		// SmartDashboard.putNumber("Lift axis", m_oi.joystick2.getRawAxis(5));
		SmartDashboard.putBoolean("Touch cargo1", RobotMap.cargo.get());
		SmartDashboard.putBoolean("Touch cargo2", RobotMap.cargo2.get());
		SmartDashboard.putNumber("Arm enc", RobotMap.armRight.getSensorCollection().getQuadraturePosition());
		SmartDashboard.putNumber("Lift enc", RobotMap.lift2.getSensorCollection().getQuadraturePosition());
		SmartDashboard.putNumber("Climb enc", RobotMap.climberEncoderDirection * RobotMap.climber.getSensorCollection().getQuadraturePosition());
		SmartDashboard.putBoolean("dsClimbLock", RobotMap.dsClimbLock.get() == Value.kForward);
		for (int i = 0; i < 4; i++) {
			SmartDashboard.putNumber("SwerveMod" + i, RobotMap.swerveMod[i].encoder.pidGet());
			// SmartDashboard.putNumber("Spark" + i, RobotMap.swerveMod[i].speedMotor.getEncoder().getPosition());
			// SmartDashboard.putNumber(("Spark but the power volts"+ i), RobotMap.swerveMod[i].speedMotor.getBusVoltage());
			SmartDashboard.putNumber(("Spark but the power amps" + i), RobotMap.swerveMod[i].speedMotor.getOutputCurrent());
		}
	}

	// public void parseNumber(String str, int num, String key){
	// 	if(str.length() != 0){
	// 		num *= 10;
	// 		parseNumber(str.substring(1), num + str.charAt(0) - 48, key);
	// 	}else{
	// 		RobotMap.arduino.put(key,((int) Math.round((num *0.5 + RobotMap.arduino.get(key)*0.5)/10))*10);
	// 	}
  	// }

	@Override
	public void testPeriodic() {
	}
}
