package org.usfirst.frc.team2557.robot.commands.auto;

import java.io.File;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

public class AutoDriveCommand extends Command {
  String name;
  EncoderFollower[] follower;
  Trajectory br;
  Trajectory bl;
  Trajectory fr;
  Trajectory fl;
  
  public AutoDriveCommand(String name) {
    requires(Robot.gyroSwerveDrive);
    this.name = name;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    follower = new EncoderFollower[4];

    // The swerve mode to generate will be the 'default' mode, where the 
    // robot will constantly be facing forward and 'sliding' sideways to  follow a curved path.
    // SwerveModifier.Mode mode = SwerveModifier.Mode.SWERVE_DEFAULT;
    // SwerveModifier modifier = new SwerveModifier(tr);

    // Generate the individual wheel trajectories using the original trajectory as the centre
    // modifier.modify(RobotMap.WHEELBASE_WIDTH, RobotMap.WHEELBASE_LENGTH, mode);

    br = Pathfinder.readFromFile(new File(RobotMap.TRAJECTORY_FOLDER + "br" + name + ".t"));
    bl = Pathfinder.readFromFile(new File(RobotMap.TRAJECTORY_FOLDER + "bl" + name + ".t"));
    fr = Pathfinder.readFromFile(new File(RobotMap.TRAJECTORY_FOLDER + "fr" + name + ".t"));
    fl = Pathfinder.readFromFile(new File(RobotMap.TRAJECTORY_FOLDER + "fl" + name + ".t"));

    follower[0] = new EncoderFollower(fr);
    follower[1] = new EncoderFollower(br);
    follower[2] = new EncoderFollower(bl);
    follower[3] = new EncoderFollower(fl);
    
    RobotMap.gyro.reset();
    for(int i = 0; i < 4; i++){
      follower[i].reset();
      // follower[i].configureEncoder(0, (int) (RobotMap.SWERVE_ENC_CIRC * 1000), RobotMap.SWERVE_WHEEL_DIAMETER);
      follower[i].configureEncoder(0, (int) RobotMap.swerveMod[i].speedMotor.getEncoder().getPosition(), RobotMap.SWERVE_WHEEL_DIAMETER);
      follower[i].configurePIDVA(1, 0, 0, 1/RobotMap.MAX_VEL, RobotMap.MAX_ACC);
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    for(int i = 0; i < 4; i++){
      double output = follower[i].calculate((int) RobotMap.swerveMod[i].speedMotor.getEncoder().getPosition());
      double heading = Pathfinder.boundHalfDegrees(Pathfinder.r2d(follower[i].getHeading()))/360;
      double encCount = RobotMap.swerveMod[i].encoder.pidGet();
      heading = (heading + 1) * RobotMap.SWERVE_ENC_CIRC / 2 + RobotMap.SWERVE_SETPOINT_OFFSET[i]; 
      if(heading > RobotMap.SWERVE_ENC_CIRC) heading -= RobotMap.SWERVE_ENC_CIRC;

      double degreesBeforeFlip = 90.0;
      if(Math.abs(encCount - heading) > RobotMap.SWERVE_ENC_CIRC / 360 * degreesBeforeFlip) {
        heading = getOppositeAngle(i);
        output *= -1;
      }
      RobotMap.swerveMod[i].drive(output, heading);
      SmartDashboard.putNumber("spark" + i, RobotMap.swerveMod[i].speedMotor.getEncoder().getPosition());
    }
  }

  public double getOppositeAngle(int heading){
    double opp = heading;
    if(opp < RobotMap.SWERVE_ENC_CIRC/2) opp += RobotMap.SWERVE_ENC_CIRC/2;
    else opp -= RobotMap.SWERVE_ENC_CIRC/2;
    return opp;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    for(int i = 0; i < 4; i++){
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
    for(int i = 0; i < 4; i++){
      RobotMap.swerveMod[i].drive(0, 0);
    }
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    for(int i = 0; i < 4; i++){
      RobotMap.swerveMod[i].drive(0, 0);
    }
  }
}
