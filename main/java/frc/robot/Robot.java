/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.BasicArmCommand;
import frc.robot.commands.GyroSwerveDriveCommand;
import frc.robot.commands.LiftWithAxis;
import frc.robot.commands.PIDarm;
import frc.robot.subsystems.BasicArmSub;
import frc.robot.subsystems.GyroSwerveDrive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lift;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  SendableChooser<Command> m_chooser;

  private RobotContainer container;

  public static BasicArmSub arm;
  public static Lift lift;
  public static GyroSwerveDrive gyroSwerveDrive;
  public static Intake intake;

  BasicArmCommand bac;
  PIDarm pidarm;
  GyroSwerveDriveCommand gyroSwerveDriveCommand;

  boolean prevArm;
  public static boolean defaultUnlockArm;
  CommandScheduler scheduler = CommandScheduler.getInstance();
  

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser = new SendableChooser<>();
    prevArm = false;

    Constants.init();

    container = new RobotContainer();

    arm = new BasicArmSub();
    lift = new Lift();
    gyroSwerveDrive = new GyroSwerveDrive();
    intake = new Intake();

    bac = new BasicArmCommand();
    pidarm = new PIDarm();
    gyroSwerveDriveCommand = new GyroSwerveDriveCommand();

    m_chooser.setDefaultOption("Default Auto", null);
    SmartDashboard.putData("Auto mode", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

		defaultUnlockArm = false;
		gyroSwerveDrive.driveStraight(0);
		// climber.climb(0);

		for(int i = 0; i < 4; i++) Constants.swerveMod[i].speedMotor.getEncoder();
		Constants.armRight.getSensorCollection().setQuadraturePosition(0, 10);
		Constants.lift2.getSensorCollection().setQuadraturePosition(0, 10);
		Constants.climber.getSensorCollection().setQuadraturePosition(0, 10);
		Constants.gyro.reset();

    // schedule the autonomouts command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    prevArm = false;
    bac.schedule();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

    // System.out.println("ARM ENCODER" + Constants.armRight.getSensorCollection().getQuadraturePosition());

    manipArm();
    // pidarm.schedule();
    // pidarm.armPositions();
    // pidarm.
    // manipArm();
    // bac.execute();
    // System.out.println("teleop periodiccccx");
  }

  public void manipArm(){



		if (RobotContainer.manipulator.getPOV() > -1 && !prevArm) {
			if (bac != null) {
        bac.cancel();
        // System.out.println("bas cacled!");
			}
      pidarm.schedule(true);
      // System.out.println("Pid arm schedule!");
			prevArm = true;
			defaultUnlockArm = true;
		} else if (RobotContainer.manipulator.getPOV() == -1 && prevArm && RobotContainer.mbumperRight.get()) {
			if (pidarm != null) {
        pidarm.cancel();
        // System.out.println("Pid arm ccccanceld!");
			}
			bac.schedule(true);
			prevArm = false;
    }

    // System.out.println("This is prev arm   " + prevArm);
    // System.out.println("Robot mBumperRight   " + prevArm);
    
    // System.out.println("Onle I AM ME you can not be  me");
	}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
