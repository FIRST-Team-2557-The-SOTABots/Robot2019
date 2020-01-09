// package frc.robot.commands;

// import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.Constants;
// import frc.robot.Robot;
// import frc.robot.RobotContainer;

// public class IntakeCommand extends CommandBase {
//   public IntakeCommand() {
//     addRequirements(Robot.intake);
//   }

//   @Override
//   public void initialize() {
//     // Robot.intake.speed(0);
//   }

//   // forward shoots in out. Reverse retracts
//   @Override
//   public void execute() {
//     double mtrr = RobotContainer.manipulator.getRawAxis(3);
//     double mtrl = RobotContainer.manipulator.getRawAxis(2);
//     // double dtrr = Robot.m_oi.joystick1.getRawAxis(3);
//     // double dtrl = Robot.m_oi.joystick1.getRawAxis(2);

//     double mtr = Math.max(mtrr, mtrl);
//     // double dtr = Math.max(dtrr, dtrl);

//     if (mtr > 0.2) {
//       if (mtrr > 0.2) {
//         Robot.intake.speed(mtrr);
//         Constants.dsIntake.set(Value.kReverse);
//       } else if (mtrl > 0.2) {
//         Constants.dsIntake.set(Value.kForward);
//         Robot.intake.speed(-mtrl);
//         // Robot.gyroSwerveDrive.driveStraight(0.15);
//       }
//     } else if (RobotContainer.manipulator.getPOV() == 135) {
//       Robot.intake.speed(0.9);
//     } else if (RobotContainer.manipulator.getPOV() == 225) {
//       Robot.intake.speed(-0.5);
//     } else {
//       Robot.intake.speed(0);
//     }

//   }

//   @Override
//   public void end(boolean interrupted) {
//     Robot.intake.speed(0);
//   }

//   @Override
//   public boolean isFinished() {
//     return false;
//   }
// }
