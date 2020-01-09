/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.MathUtil;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class BasicArmCommand extends CommandBase {
  /**
   * Creates a new BasicArmCommand.
   */
  public BasicArmCommand() {
    addRequirements(Robot.arm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.arm.arm(0);
    if(Robot.defaultUnlockArm){ Constants.dsArmLock.set(Value.kForward); }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double pow = RobotContainer.manipulator.getRawAxis(1);
    if(MathUtil.inDeadband(pow)) pow=0;
    Robot.arm.arm(pow);
    // System.out.println(pow);
    // System.out.println("Arm with axising");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Robot.arm.arm(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
