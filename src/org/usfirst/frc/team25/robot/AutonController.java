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

	/**
	 * Portculli, position 2
	 */
	public void iPickThingsUpAndPutThemDownIn() {
		if (m_step == 0) {
			m_drives.resetEncoders();
			m_drives.brakesOff();
			m_drives.resetGyro();
			m_step++;
		} else if (m_step == 1) {
			if (!m_pickup.goTo(Constants.PICKUP_PORT_CULLIS)) {
				m_pickup.setArmSpeed(0.0, true);
				m_step++;
			}
		} else if (m_step == 2) {
			if (m_drives.driveStraight(60.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_drives.resetEncoders();
				m_drives.resetGyro();
				m_step++;
			}
		} else if (m_step == 3) {
			m_drives.driveStraight(100.0, 0.4); // drive beyond the distance
												// needed. This step ends when
												// arm is up.
			if (!m_pickup.goTo(Constants.PICKUP_PORT_CULLIS_HIGH)) {
				m_pickup.setArmSpeed(0.0, true);
				m_drives.setSpeed(0.0);
				m_step++;
			}
		} else if (m_step == 4) {
			// Continue driving a to clear the outworks.
			if (m_drives.driveStraight(193.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_pickup.setArmSpeed(0.0, true);
				m_step++;
			}
		} else if (m_step == 5) {
			if (m_drives.turnToAngle(63, 0.5)) {
				m_drives.setSpeed(0.0);
				m_step++;
			}
		} else if (m_step == 6) {
			if (!m_pickup.goTo(Constants.PICKUP_ARM_DOWN)) {
				m_pickup.setArmSpeed(0.0, true);
				m_drives.resetEncoders();
				m_drives.resetGyro();
				m_step++;
			}
		} else if (m_step == 7) {
			if (m_drives.driveStraight(18.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_drives.brakesOn();
				m_step++;
			}
		} else {
			m_pickup.eject();
			m_drives.setSpeed(0.0);
			m_pickup.setArmSpeed(0.0, true);
		}
	}

	public void teeterTotter() {
		if (m_step == 0) {
			m_drives.resetEncoders();
			m_drives.brakesOff();
			m_drives.resetGyro();
			m_drives.brakesOff();
			m_step++;
		} else if (m_step == 1) {
			if (!m_pickup.goTo(Constants.PICKUP_RAMPS)) {
				m_pickup.setArmSpeed(0.0, true);
				m_drives.resetGyro();
				m_drives.resetEncoders();
				m_step++;
			}
		} else {
			m_pickup.setArmSpeed(0.0, true);
			m_drives.setSpeed(0.0);
		}
	}

	public void lowBarAndScore() {
		if (m_step == 0) {
			m_drives.resetEncoders();
			m_drives.brakesOff();
			m_drives.resetGyro();
			m_step++;
		} else if (m_step == 1) {
			if (!m_pickup.goTo(Constants.PICKUP_ARM_DOWN + 0.002)) { // TODO:
																		// Remove
				m_pickup.setArmSpeed(0.0, true);
				m_step++;
			}
		} else if (m_step == 2) {
			if (m_drives.driveStraight(227.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_drives.resetGyro();
				m_step++;
			}
		} else if (m_step == 3) {
			if (m_drives.turnToAngle(64, 0.5)) {
				m_drives.setSpeed(0.0);
				m_drives.resetEncoders();
				m_drives.resetGyro();
				m_step++;
			}
		} else if (m_step == 4) {
			if (m_drives.driveStraight(73.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_pickup.eject();
				m_drives.brakesOff();
				m_step++;
			}
		} else {
			m_pickup.eject();
			m_drives.setSpeed(0.0);
		}
	}

	public void slotTwoTerrain() {
		if (m_step == 0) {
			m_drives.resetEncoders();
			m_drives.resetGyro();
			m_drives.brakesOff();
			m_step++;
		} else if (m_step == 1) {
			if (m_drives.driveStraight(252.0, 0.7)) {
				m_drives.setSpeed(0.0);
				m_drives.resetGyro();
				m_step++;
			}
		} else {
			m_drives.setSpeed(0.0);
		}
	}

}
