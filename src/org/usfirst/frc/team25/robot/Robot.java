package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	// ====== Robot Classes ======
	private AutonController m_autonController;
	private OI m_OI;
	private Drivebase m_drives;
	private Pickup m_pickup;
	private Timer m_timer;

	// ====== Auton Logic ======
	private SendableChooser m_autonChooser;
	private int m_autonChosen;

	public void robotInit() {
		// ===== ROBOT MECHANISMS =====
		m_autonController = AutonController.getInstance();
		m_OI = OI.getInstance();
		m_drives = Drivebase.getInstance();
		m_pickup = Pickup.getInstance();
		m_timer = new Timer();

		// ===== RESETS =====
		m_drives.resetNavX();
		m_drives.resetEncoders();

		// ===== AUTON STUFF ===== TODO: Fix Autons
		m_autonChooser = new SendableChooser();
		m_autonChooser.addObject("0: Do Nothing (Default)", 0);
		m_autonChooser.addObject("1: Low Bar And Score", 1);
		m_autonChooser.addObject("2: Teeter Totter Model", 2);
		m_autonChooser.addObject("3: Slot Two Path", 3);
		m_autonChooser.addObject("4: Go Over Obstacle (Special)", 4);
		SmartDashboard.putData("Auton Key", m_autonChooser);
		SmartDashboard.putNumber("Choose Auton", 0);
	}

	private void update() {
		SmartDashboard.putNumber("Left Encoder", m_drives.getLeftEncoderDistance());
		SmartDashboard.putNumber("Right Encoder", m_drives.getRightEncoderDistance());
		SmartDashboard.putNumber("Pickup Pot", m_pickup.getPot());
		SmartDashboard.putNumber("Gyro", m_drives.getGyroAngle());
		SmartDashboard.putNumber("Roll", m_drives.getGyroRoll());
		SmartDashboard.putNumber("Auton Chosen", SmartDashboard.getNumber("Choose Auton"));
		SmartDashboard.putNumber("NavX Compass Heading", m_drives.getNavXCompass());
		SmartDashboard.putNumber("Ultrasonic", m_drives.getSonicDistance());
		SmartDashboard.putString("Time To Hang", (m_timer.get() > 115.0 ? "**TIME TO HANG**" : ""));
	}

	public void disabledInit() {
		m_timer.stop();
		m_timer.reset();
	}

	public void disabledPeriodic() {
		update();
	}

	public void autonomousInit() {
		m_drives.brakesOff();
		m_autonController.resetStep();
		m_drives.resetStep();
		m_drives.resetNavX();
		m_autonChosen = (int) SmartDashboard.getNumber("Choose Auton");
	}

	@SuppressWarnings("deprecation")
	public void autonomousPeriodic() {
		if (m_autonChosen == 1) {
			m_autonController.lowBarAndScore();
		} else if (m_autonChosen == 2) {
			m_autonController.teeterTotterSlotTwoAndScore();
		} else if (m_autonChosen == 3) {
			m_autonController.slotTwoPath();
		} else if (m_autonChosen == 4) {
			m_autonController.goOverObstacle(0.9);
		}
		update();
	}

	public void teleopInit() {
		m_drives.brakesOff();
		m_timer.start();
		m_timer.reset();
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