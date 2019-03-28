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
    
    // RobotMap.gyro.reset();
    for(int i = 0; i < 4; i++){
      follower[i].reset();
      follower[i].configureEncoder((int) (1000* RobotMap.swerveMod[i].speedMotor.getEncoder().getPosition()), (int) (1000* 5.41), RobotMap.SWERVE_WHEEL_DIAMETER);
      follower[i].configurePIDVA(1, 0, 0, 1/RobotMap.MAX_VEL, RobotMap.MAX_ACC);
    }
  }

  double[] angle;
  double[] speed;
  // double[] angles = new double[4];
  // double[] speeds = new double[4];

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    angle = new double[4];
    speed = new double[4];
    // angles = new double[4];
    // speeds = new double[4];
    for(int i = 0; i < 4; i++){
      speed[i] = follower[i].calculate((int) (1000 *RobotMap.swerveMod[i].speedMotor.getEncoder().getPosition()));
      angle[i] = Pathfinder.boundHalfDegrees(Pathfinder.r2d(follower[i].getHeading()))*RobotMap.SWERVE_ENC_CIRC/360;
      // angle[i] = follower[i].getHeading();
    }

    SmartDashboard.putNumberArray("drive angles", angle);
    SmartDashboard.putNumberArray("drive speeds", speed);
    // Robot.gyroSwerveDrive.autoDrive(angle, speed);
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
