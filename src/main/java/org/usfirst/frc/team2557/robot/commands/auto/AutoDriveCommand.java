package org.usfirst.frc.team2557.robot.commands.auto;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.SwerveModifier;

public class AutoDriveCommand extends Command {
  EncoderFollower[] follower;
  Trajectory tr;
  
  public AutoDriveCommand(Trajectory tr) {
    this.tr = tr;
    requires(Robot.gyroSwerveDrive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    follower = new EncoderFollower[3];

    // The swerve mode to generate will be the 'default' mode, where the 
    // robot will constantly be facing forward and 'sliding' sideways to  follow a curved path.
    SwerveModifier.Mode mode = SwerveModifier.Mode.SWERVE_DEFAULT;
    SwerveModifier modifier = new SwerveModifier(tr);

    // Generate the individual wheel trajectories using the original trajectory as the centre
    modifier.modify(RobotMap.WHEELBASE_WIDTH, RobotMap.WHEELBASE_LENGTH, mode);

    follower[0] = new EncoderFollower(modifier.getFrontRightTrajectory());
    follower[1] = new EncoderFollower(modifier.getBackRightTrajectory());
    follower[2] = new EncoderFollower(modifier.getBackLeftTrajectory());
    follower[3] = new EncoderFollower(modifier.getFrontLeftTrajectory());
    
    RobotMap.gyro.reset();
    for(int i = 0; i < 3; i++){
      follower[i].reset();
      follower[i].configureEncoder(0, (int) (RobotMap.SWERVE_ENC_CIRC * 1000), RobotMap.SWERVE_WHEEL_DIAMETER);
      follower[i].configurePIDVA(1, 0, 0, 1/RobotMap.MAX_VEL, RobotMap.MAX_ACC);
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    for(int i = 0; i < 3; i++){
      double output = follower[i].calculate((int) (RobotMap.swerveMod[i].encoder.pidGet() * 1000));
      double heading = Pathfinder.boundHalfDegrees(Pathfinder.r2d(follower[i].getHeading()));
      RobotMap.swerveMod[i].drive(output, heading);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    for(int i = 0; i < 3; i++){
      if(!follower[i].isFinished()){
        return false;
      }
    }
    return true;
    // return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    for(int i = 0; i < 3; i++){
      RobotMap.swerveMod[i].drive(0, 0);
    }
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    for(int i = 0; i < 3; i++){
      RobotMap.swerveMod[i].drive(0, 0);
    }
  }
}
