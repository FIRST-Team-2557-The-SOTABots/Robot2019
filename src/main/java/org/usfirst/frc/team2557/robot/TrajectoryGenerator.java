package org.usfirst.frc.team2557.robot;

import java.io.File;
import java.io.IOException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.SwerveModifier;

public class TrajectoryGenerator {
    public Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
            Trajectory.Config.SAMPLES_HIGH, 0.02, RobotMap.MAX_VEL, RobotMap.MAX_ACC, 60.0);
    public SwerveModifier.Mode mode = SwerveModifier.Mode.SWERVE_DEFAULT;

    public void write(String file, Trajectory tr) {
        File trfile = new File(file);
        try {
            trfile.createNewFile();
            Pathfinder.writeToFile(trfile, tr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void trajectory(String name, Waypoint[] points){
        Trajectory trajectory = Pathfinder.generate(points, config);
        SwerveModifier modifier = new SwerveModifier(trajectory);
        modifier.modify(RobotMap.WHEELBASE_WIDTH, RobotMap.WHEELBASE_LENGTH, mode);

        Trajectory fl = modifier.getFrontLeftTrajectory(); // Get the Front Left wheel
        Trajectory fr = modifier.getFrontRightTrajectory(); // Get the Front Right wheel
        Trajectory bl = modifier.getBackLeftTrajectory(); // Get the Back Left wheel
        Trajectory br = modifier.getBackRightTrajectory(); // Get the Back Right wheel

        write(RobotMap.TRAJECTORY_FOLDER + "fl" + name + ".t", fl);
        write(RobotMap.TRAJECTORY_FOLDER + "fr" + name + ".t", fr);
        write(RobotMap.TRAJECTORY_FOLDER + "bl" + name + ".t", bl);
        write(RobotMap.TRAJECTORY_FOLDER + "br" + name + ".t", br);
        System.out.println("IN TRAJECTORY " + name + " WRITE");
    }

    public void trajectory0() {
        String name = "trajectory0";
        Waypoint[] points = new Waypoint[] {
            new Waypoint(-4, -1, Pathfinder.d2r(-45)), // Waypoint @ x=-4, y=-1, exit
            // angle=-45 degrees
            new Waypoint(-2, -2, 0), // Waypoint @ x=-2, y=-2, exit angle=0 radians
            new Waypoint(0, 0, 0) // Waypoint @ x=0, y=0, exit angle=0 radians
        };
        trajectory(name, points);
    }
}