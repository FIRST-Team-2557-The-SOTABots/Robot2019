package org.usfirst.frc.team2557.robot;

import java.util.ArrayList;
import org.usfirst.frc.team2557.robot.commands.arm.ArmWithAxis;
import org.usfirst.frc.team2557.robot.commands.arm.PIDarm;
import org.usfirst.frc.team2557.robot.commands.auto.segments.Segment1;
import org.usfirst.frc.team2557.robot.commands.climb.Climb2CommandGroup;
import org.usfirst.frc.team2557.robot.commands.climb.ClimbCommandGroup;
import org.usfirst.frc.team2557.robot.commands.climb.ClimbCommandGroup2;
import org.usfirst.frc.team2557.robot.commands.climb.LiftClimb;
import org.usfirst.frc.team2557.robot.commands.drive.Straight;
import org.usfirst.frc.team2557.robot.commands.drive.TofDrive;
import org.usfirst.frc.team2557.robot.commands.drive.VisionDriveStraightOn;
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
	public static TrajectoryGenerator tg;
	public static GyroSwerveDrive gyroSwerveDrive;
	public static Lift lift;
	public static Intake intake;
	public static Arm arm;
	public static Climber climber;
	// public static ArduinoSensors arduinoSensors;
	public static boolean prevArm;
	public static boolean defaultUnlockArm;
	public static String str = "";
	public static ArrayList<String> list = new ArrayList<String>();
	public static boolean climbed;

	PIDlift ma;
	PIDlift mb;
	PIDlift my;
	PIDlift mx;
	VisionDriveStraightOn vdso;
	ClimbCommandGroup lc;
	ClimbCommandGroup2 lc2;
	Climb2CommandGroup l2c;
	TofDrive td;

	PIDarm pidarm;
	ArmWithAxis awa;
	Straight straight;

	ClimbCommandGroup ccg;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser;

	@Override
	public void robotInit() {
		prevArm = false;
		defaultUnlockArm = false;
		climbed = false;

		// NOTE: RobotMap MUST be initialized before subsystems
		RobotMap.init();
		RobotMap.arduino.put("ToFL", 0.0);
		RobotMap.arduino.put("ToFR", 0.0);

		tg = new TrajectoryGenerator();
		gyroSwerveDrive = new GyroSwerveDrive();
		lift = new Lift();
		intake = new Intake();
		arm = new Arm();
		climber = new Climber();
		// arduinoSensors = new ArduinoSensors();

		// NOTE: oi MUST be constructed after subsystems
		m_oi = new OI();
		m_chooser = new SendableChooser<>();

		ma = new PIDlift(RobotMap.lowPos);
		mb = new PIDlift(RobotMap.midPos);
		my = new PIDlift(RobotMap.highPos);
		mx = new PIDlift(RobotMap.xPos);

		vdso = new VisionDriveStraightOn();
		ccg = new ClimbCommandGroup();
		lc = new ClimbCommandGroup();
		lc2 = new ClimbCommandGroup2();
		l2c = new Climb2CommandGroup();
		straight = new Straight();
		td = new TofDrive();

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
		SmartDashboard.putNumber("Gyro Reading", RobotMap.gyro.getAngle());
		climber.climb(0);
		boolean armLock = false;
		if (RobotMap.dsArmLock.get() == Value.kReverse) {
			armLock = true;
		}
		SmartDashboard.putBoolean("armLock", armLock);
		if (Robot.m_oi.ma.get()) {
			ma.setSetpoint(RobotMap.lowPos);
			ma.start();
		} else {
			ma.cancel();
		}
		if (Robot.m_oi.mb.get()) {
			mb.setSetpoint(RobotMap.midPos);
			mb.start();
		} else {
			mb.cancel();
		}
		if (Robot.m_oi.my.get()) {
			my.setSetpoint(RobotMap.highPos);
			my.start();
		} else {
			my.cancel();
		}
		if (Robot.m_oi.mx.get()) {
			mx.setSetpoint(RobotMap.xPos);
			mx.start();
		} else {
			mx.cancel();
		}

		// if(m_oi.bumperLeft.get()){
		// vdso.start();
		// SmartDashboard.putBoolean("VISION", true);
		// }else{
		// vdso.cancel();
		// SmartDashboard.putBoolean("VISION", false);
		// }

		if (m_oi.joystick2.getPOV() > -1 && !prevArm) {
			if (awa != null) {
				awa.cancel();
			}
			pidarm.start();
			prevArm = true;
			defaultUnlockArm = true;
		} else if (m_oi.joystick2.getPOV() == -1 && prevArm
				&& (Robot.m_oi.joystick2.getRawAxis(1) <= -RobotMap.JOYSTICK_DEADBAND
						|| Robot.m_oi.joystick2.getRawAxis(1) >= RobotMap.JOYSTICK_DEADBAND)) {
			if (pidarm != null) {
				pidarm.cancel();
			}
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
	
		if(m_oi.back.get()){
			lc2.start();
			lc.cancel();
		}else if(m_oi.dy.get()){
			l2c.start();
		}else if(m_oi.start.get()){
			lc.start();
		}else if(m_oi.dx.get()){
			lc.cancel();
			lc2.cancel();
			l2c.cancel();
			climber.lock(false);
			RobotMap.climber.set (0.5);
		}else{
			lc.cancel();
			lc2.cancel();
			l2c.cancel();
			climber.lock(true);
			RobotMap.climber.set (0);
		}

		// if(m_oi.dy.get()){
		// 	l2c.start();
		// }

		boolean armLock = false;
		if (RobotMap.dsArmLock.get() == Value.kReverse) {
			armLock = true;
		}
		SmartDashboard.putBoolean("armLock", armLock);

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

		// if(m_oi.bumperLeft.get()){
		// vdso.start();
		// SmartDashboard.putBoolean("VISION", true);
		// }else{
		// vdso.cancel();
		// SmartDashboard.putBoolean("VISION", false);
    // }
    
		if (Robot.m_oi.ma.get()) {
			ma.setSetpoint(RobotMap.lowPos);
			ma.start();
		} else {
			ma.cancel();
		}
    
		if (Robot.m_oi.mb.get()) {
		// 	// RobotMap.multplift = 0.75;
		// 	// RobotMap.multilift = 0.0;
		// 	// RobotMap.multdlift = 0.0;
		// 	// SmartDashboard.putNumber("P lift", RobotMap.multplift);
		// 	// SmartDashboard.putNumber("I lift", RobotMap.multilift);
		// 	// SmartDashboard.putNumber("D lift", RobotMap.multdlift);
			mb.setSetpoint(RobotMap.midPos);
			mb.start();
		} else {
			mb.cancel();
		// 	// RobotMap.multplift = 0.4;
		// 	// RobotMap.multilift = 0.0;
		// 	// RobotMap.multdlift = 0.0;
		// 	// SmartDashboard.putNumber("P lift", RobotMap.multplift);
		// 	// SmartDashboard.putNumber("I lift", RobotMap.multilift);
		// 	// SmartDashboard.putNumber("D lift", RobotMap.multdlift);
		}
		
		// if(Robot.m_oi.dx.get()){
		// 	td.start();
		// }else{
		// 	td.cancel();
		// }
		
		if (Robot.m_oi.my.get()) {
			my.setSetpoint(RobotMap.highPos);
			my.start();
		} else {
			my.cancel();
		}
		if (Robot.m_oi.mx.get()) {
			mx.setSetpoint(RobotMap.xPos);
			mx.start();
		} else {
			mx.cancel();
		}


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

		// if(m_oi.joystick2.getPOV() == 180 && prevArm && RobotMap.dsArmLock.get() != Value.kReverse){
		// 	RobotMap.dsArmLock.set(Value.kReverse);
		// }

		// if (m_oi.dy.get() && !climbed) {
		// 	ccg.start();
		// } else if (!m_oi.dy.get()) {
		// 	ccg.cancel();
    // }

    // if (m_oi.dy.get()) {
    //   if (RobotMap.arduino.get("ToFL") - 100 > RobotMap.arduino.get("ToFR")) {
        
    //   } else if (RobotMap.arduino.get("ToFR") - 100 > RobotMap.arduino.get("ToFL")) {

    //   }
	// }
	
	SmartDashboard.putNumber("Gyro Reading", RobotMap.gyro.getAngle());
    SmartDashboard.putNumber("Arm target", RobotMap.armTarget);
	SmartDashboard.putNumber("Arm axis!!!", m_oi.joystick2.getRawAxis(1));
	SmartDashboard.putNumber("Lift axis!!!", m_oi.joystick2.getRawAxis(5));
	SmartDashboard.putBoolean("Touch cargo1", RobotMap.cargo.get());
	SmartDashboard.putBoolean("Touch cargo2", RobotMap.cargo2.get());
	SmartDashboard.putNumber("arm right enc", RobotMap.armRight.getSensorCollection().getQuadraturePosition());
	SmartDashboard.putNumber("lift 2 enc", RobotMap.lift2.getSensorCollection().getQuadraturePosition());
	SmartDashboard.putNumber("climb", RobotMap.climber.getSensorCollection().getQuadraturePosition());
	SmartDashboard.putBoolean("dsClimbLock Teleop", RobotMap.dsClimbLock.get() == Value.kForward);
	for (int i = 0; i < 4; i++) {
		SmartDashboard.putNumber("Encoder value " + i, RobotMap.swerveMod[i].encoder.pidGet());
		SmartDashboard.putNumber("spark pos" + i, RobotMap.swerveMod[i].speedMotor.getEncoder().getPosition());
	}
	Scheduler.getInstance().run();


	// if (RobotMap.serial.getBytesReceived() == 0) return;

	// str += RobotMap.serial.readString();
	// SmartDashboard.putString("tof buffer str", str);
	// while (str.indexOf('\n') != -1) {
	// 	list.add(str.substring(0, str.indexOf('\n')));
	// 	str = str.substring(str.indexOf('\n') + 1);
	// }

	// for (int i = 0; i < list.size(); i++) {
	// 	String temp = list.get(i);
	// 	for (String key : RobotMap.arduino.keySet()) {
	// 		if (temp.contains(key)) {
	// 			String[] arr = temp.split(key);
	// 			parseNumber(arr[1], 0, key);
	// 		}
	// 	}
	// 	list.remove(i);
	// }
	// RobotMap.tofAngle = Math.toDegrees(Math.atan2((RobotMap.arduino.get("ToFR") - RobotMap.arduino.get("ToFL")),RobotMap.TofDistance));

	// SmartDashboard.putString("tof values", RobotMap.arduino.values().toString());
	// SmartDashboard.putNumber("tof angle", RobotMap.tofAngle);
}

public void parseNumber(String str, double num, String key){
	if(str.length() != 0){
		num *= 10;
		parseNumber(str.substring(1), num + str.charAt(0) - 48, key);
	}else{
		RobotMap.arduino.put(key, num);
	}
  }

	@Override
	public void testPeriodic() {
	}
}