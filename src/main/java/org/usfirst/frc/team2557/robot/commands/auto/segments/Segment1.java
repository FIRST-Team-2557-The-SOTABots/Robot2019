package org.usfirst.frc.team2557.robot.commands.auto.segments;

// import org.usfirst.frc.team2557.robot.TrajectoryGenerator;
import org.usfirst.frc.team2557.robot.commands.auto.AutoDriveCommand;
import org.usfirst.frc.team2557.robot.commands.drive.SpinToWin;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Segment1 extends CommandGroup {
  public Segment1() {
    addSequential(new SpinToWin(1440));
    // addSequential(new AutoDriveCommand(TrajectoryGenerator.trajectory()));
    // addSequential();
  }
}