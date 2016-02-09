package org.usfirst.frc.team25.robot;

public class AutonController {

	private static AutonController m_instance;
	private int m_step;
	private final Drivebase m_drives;
	private final Pickup m_pickup;

	private AutonController() {
		m_drives = Drivebase.getInstance();
		m_pickup = Pickup.getInstance();
		m_step = 0;
	}

	public static AutonController getInstance() {
		if (m_instance == null) {
			m_instance = new AutonController();
		}
		return m_instance;
	}

	public void resetStep() {
		m_step = 0;
	}

	public void turn(double angle) {
		if (m_step == 0) {
			m_drives.resetEncoders();
			m_drives.brakesOff();
			m_step++;
		} else if (m_step == 1) {
			if (m_drives.turnToAngle(angle, 0.5)) {
				m_drives.setSpeed(0.0);
				m_step++;
			}
		} else {
			m_drives.setSpeed(0.0);
		}
	}

	public void lowBarAndScore() {
		if (m_step == 0) {
			m_drives.resetEncoders();
			m_drives.brakesOff();
			m_step++;
		} else if (m_step == 1) {
			if (!m_pickup.goTo(Constants.PICKUP_ARM_DOWN)) {
				m_pickup.setArmSpeed(0.0, true);
				m_step++;
			}
		} else if (m_step == 2) {
			if (m_drives.driveStraight(180.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_step++;
			}
		} else if (m_step == 3) {
			if (m_drives.turnToAngle(68.5, 0.5)) {
				m_drives.setSpeed(0.0);
				m_step++;
			}
		} else {
			m_drives.setSpeed(0.0);
		}
	}

}
