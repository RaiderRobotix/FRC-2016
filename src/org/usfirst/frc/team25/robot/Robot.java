package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	private AutonController m_autonController;
	private OI m_OI;
	private Drivebase m_drives;
	private Pickup m_pickup;
	private SendableChooser m_autonChooser;
	private int m_autonChosen;

	public void robotInit() {
		// ===== ROBOT MECHANISMS =====
		m_autonController = AutonController.getInstance();
		m_OI = OI.getInstance();
		m_drives = Drivebase.getInstance();
		m_pickup = Pickup.getInstance();

		// ===== RESETS =====
		m_drives.resetGyro();
		m_drives.resetEncoders();

		// ===== AUTON STUFF =====
		m_autonChooser = new SendableChooser();
		m_autonChooser.addObject("0: Do Nothing (Default)", 0);
		m_autonChooser.addObject("1: Low Bar And Score", 1);
		m_autonChooser.addObject("2: Slot 2 Port Cullis and Score", 2);
		m_autonChooser.addObject("3: Slot 2 Teeter Totter and Score", 3);
		m_autonChooser.addObject("4: Slot 4 Teeter Totter and Score", 4);
		m_autonChooser.addObject("5: Slot 5 Teeter Totter and Score", 5);
		m_autonChooser.addObject("6: General Cross Obstacle (Not Added Yet)", 6);
		SmartDashboard.putData("Auton Key", m_autonChooser);
		SmartDashboard.putNumber("Choose Auton", 0);
	}

	private void update() {
		System.out.println("Left Encoder: " + m_drives.getLeftEncoderDistance());
		System.out.println("Right Encoder: " + m_drives.getRightEncoderDistance());
		System.out.println("Pickup Pot: " + m_pickup.getPot());
		System.out.println("Gyro: " + m_drives.getGyroAngle());
		System.out.println("Auton Chosen: " + SmartDashboard.getNumber("Choose Auton"));
	}

	public void disabledInit() {
	}

	public void disabledPeriodic() {
		update();
		if (Utility.getUserButton()) {
			m_drives.calibrateGyro();
		}
	}

	public void autonomousInit() {
		m_drives.brakesOff();
		m_autonController.resetStep();
		m_drives.resetStep();
		m_drives.resetGyro();
		m_autonChosen = (int) SmartDashboard.getNumber("Choose Auton");
	}

	public void autonomousPeriodic() {
		if (m_autonChosen == 1) {
			m_autonController.lowBarAndScore();
		} else if (m_autonChosen == 2) {
			m_autonController.portCullisSlowTwoAndScore();
		} else if (m_autonChosen == 3) {
			m_autonController.teeterTotterSlotTwoAndScore();
		} else if (m_autonChosen == 4) {
			m_autonController.teeterTotterSlotFourAndScore();
		} else if (m_autonChosen == 5) {
			m_autonController.teeterTotterSlotFiveAndScore();
		}
	}

	public void teleopInit() {
		m_drives.brakesOff();
	}

	public void teleopPeriodic() {
		m_OI.enableTeleopControls();
		update();
	}

	public void testInit() {
		m_drives.brakesOff();
	}

	public void testPeriodic() {
		m_drives.brakesOff();
	}

}
