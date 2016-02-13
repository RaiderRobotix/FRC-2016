package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	private AutonController m_autonController;
	private OI m_OI;
	private Drivebase m_drives;
	private Pickup m_pickup;

	public void robotInit() {
		m_autonController = AutonController.getInstance();
		m_OI = OI.getInstance();
		m_drives = Drivebase.getInstance();
		m_pickup = Pickup.getInstance();
		m_drives.resetGyro();
	}

	private void updateDashboard() {
		SmartDashboard.putNumber("Left Encoder", Math.abs(m_drives.getLeftEncoderDistance()));
		SmartDashboard.putNumber("Right Encoder", Math.abs(m_drives.getRightEncoderDistance()));
		SmartDashboard.putNumber("Arm Pot", m_pickup.getPot());
		SmartDashboard.putNumber("Gyro", m_drives.getGyroAngle());
	}

	private void printStats() {
		System.out.println("Left Encoder: " + m_drives.getLeftEncoderDistance());
		System.out.println("Right Encoder: " + m_drives.getRightEncoderDistance());
		System.out.println("Pickup Pot: " + m_pickup.getPot());
		System.out.println("Gyro: " + m_drives.getGyroAngle());
	}

	public void disabledInit() {
	}

	public void disabledPeriodic() {
		printStats();
		updateDashboard();
		if (Utility.getUserButton()) {
			m_drives.resetEncoders();

		}
	}

	public void autonomousInit() {
		m_drives.brakesOff();
		m_autonController.resetStep();
		m_drives.resetStep();
		m_drives.resetGyro();
	}

	public void autonomousPeriodic() {
		m_autonController.lowBarAndScore();
		// m_autonController.turn(68.5);
	}

	public void teleopInit() {
		m_drives.brakesOff();
	}

	public void teleopPeriodic() {
		m_OI.enableTeleopControls();
		updateDashboard();
		printStats();
	}

	public void testInit() {
		m_drives.brakesOff();
	}

	public void testPeriodic() {
		m_drives.brakesOff();
	}

}
