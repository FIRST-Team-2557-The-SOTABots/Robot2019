/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class PIDarm extends CommandBase {
  PIDController pidController;
	boolean done = false;
	double factor = 0.000001; //10^-6+4+2
	double multp = 0;
	double multi = 0;
	double multd = 0;
	boolean didntEnd;

  public void armPositions(){
		if (RobotContainer.manipulator.getPOV() == 0) {
			Constants.armTarget = Constants.armForCargo;
			multp = Constants.multparmHigh;
			multi = Constants.multiarmHigh;
			multd = Constants.multdarmHigh;
		} else if (RobotContainer.manipulator.getPOV() == 90) {
			Constants.armTarget = Constants.armIntake;
			multp = Constants.multparm;
			multi = Constants.multiarm;
			multd = Constants.multdarm;
		 }
  }
  
  public PIDarm() {
    addRequirements(Robot.arm);
    didntEnd = false;
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Constants.dsArmLock.set(Value.kForward);
    armPositions();
    pidController = new PIDController(multp*factor, multi*factor, multd*factor);
    pidController.disableContinuousInput();
    pidController.setTolerance(60);
    pidController.reset();
    didntEnd = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(RobotContainer.manipulator.getPOV() == 180){
			didntEnd = true;
    }
    
    double output = pidController.calculate(-Constants.armRight.getSensorCollection().getQuadraturePosition(), Constants.armTarget);
    double enc = Constants.armRight.getSensorCollection().getQuadraturePosition()*Math.PI/10000;
    output*= .9;
    Robot.arm.arm(-output + Constants.pidarmStall*Math.sin(enc));

    armPositions();
    // System.out.println("PID out put  " + -output + Constants.pidarmStall*Math.sin(enc));

    // System.out.println("ARM ENCODER" + Constants.armRight.getSensorCollection().getQuadraturePosition());

    // System.out.print("AT SETPOINT   "  + pidController.atSetpoint());

    // System.out.println("     Arm encoder #   "  + -Constants.armRight.getSensorCollection().getQuadraturePosition() + " Setpoint    " + pidController.getSetpoint());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Constants.armLeft.set(0);
    Constants.armRight.set(0);
    Constants.dsArmLock.set(Value.kReverse);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return pidController.atSetpoint() || didntEnd;
  }
}
