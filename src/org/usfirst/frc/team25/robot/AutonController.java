package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.Timer;

public class AutonController {

	private static AutonController m_instance;

	private int m_step;
	private final Drivebase m_drives;
	private final Pickup m_pickup;
	private final Timer m_timer;

	private AutonController() {
		m_drives = Drivebase.getInstance();
		m_pickup = Pickup.getInstance();
		m_timer = new Timer();
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

	/**
	 * General Port Cullis
	 */
	public void portCullisGeneral() {
		if (m_step == 0) {
			m_drives.resetEncoders();
			m_drives.brakesOff();
			m_drives.resetGyro();
			m_timer.start();
			m_timer.reset();
			m_step++;
		} else if (m_step == 1) {
			if (!m_pickup.goTo(Constants.PICKUP_PORT_CULLIS, 1.0) || m_timer.get() > 2.5) {
				m_timer.reset();
				m_pickup.setArmSpeed(0.0, true);
				m_step++;
			}
		} else if (m_step == 2) {
			if (m_drives.driveStraight(60.0, 0.5) || m_timer.get() > 5.0) {
				m_drives.setSpeed(0.0);
				m_drives.resetEncoders();
				m_drives.resetGyro();
				m_timer.stop();
				m_step++;
			}
		} else if (m_step == 3) {
			m_drives.setSpeed(0.35);
			if (!m_pickup.goTo(Constants.PICKUP_PORT_CULLIS_HIGH, 0.75)) {
				m_pickup.setArmSpeed(0.0, true);
				m_drives.resetEncoders();
				m_drives.resetGyro();
				m_step++;
			}
		} else if (m_step == 4) {
			if (m_drives.driveStraight(50.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_step++;
			}
		} else {
			m_drives.setSpeed(0.0);
			m_pickup.setArmSpeed(0.0, true);
		}
	}

	/**
	 * Garage Door- Slot 2 & Score
	 */
	public void portCullisSlotTwoAndScore() {
		if (m_step == 0) {
			m_drives.resetEncoders();
			m_drives.brakesOff();
			m_drives.resetGyro();
			m_step++;
		} else if (m_step == 1) {
			if (!m_pickup.goTo(Constants.PICKUP_PORT_CULLIS, 1.0)) {
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
			m_drives.driveStraight(100.0, 0.4);
			if (!m_pickup.goTo(Constants.PICKUP_PORT_CULLIS_HIGH, 1.0)) {
				m_pickup.setArmSpeed(0.0, true);
				m_drives.setSpeed(0.0);
				m_step++;
			}
		} else if (m_step == 4) {
			if (m_drives.driveStraight(193.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_pickup.setArmSpeed(0.0, true);
				m_step++;
			}
		} else if (m_step == 5) {
			if (m_drives.turnToAngle(63.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_step++;
			}
		} else if (m_step == 6) {
			if (!m_pickup.goTo(Constants.PICKUP_ARM_DOWN, 1.0)) {
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

	/**
	 * Teeter Totter- Slot 2 & Score
	 */
	public void teeterTotterSlotTwoAndScore() {
		if (m_step == 0) {
			m_drives.resetEncoders();
			m_drives.resetGyro();
			m_drives.brakesOff();
			m_step++;
		} else if (m_step == 1) {
			if (!m_pickup.goTo(Constants.PICKUP_RAMPS_HEIGHT, 1.0)) {
				m_pickup.setArmSpeed(0.0, true);
				m_step++;
			}
		} else if (m_step == 2) {
			if (m_drives.driveStraight(47.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if (m_step == 3) {
			if (!m_pickup.goTo(Constants.PICKUP_RAMPS_LOW, 0.5) || m_timer.get() >= 3.0) {
				m_pickup.setArmSpeed(0.0, true);
				m_drives.resetGyro();
				m_drives.resetEncoders();
				m_timer.stop();
				m_step++;
			}
		} else if (m_step == 4) {
			if (m_drives.getLeftEncoderDistance() > 30.0 && m_drives.getLeftEncoderDistance() < 50.0) {
				m_pickup.goTo(Constants.PICKUP_PORT_CULLIS_HIGH, 1.0);
			} else {
				m_pickup.setArmSpeed(0.0, true);
			}
			if (m_drives.driveStraight(210.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_pickup.setArmSpeed(0.0, true);
				m_drives.resetGyro();
				m_step++;
			}
		} else if (m_step == 5) {
			if (m_drives.turnToAngle(63, 0.5)) {
				m_drives.resetEncoders();
				m_drives.resetGyro();
				m_drives.setSpeed(0.0);
				m_step++;
			}
		} else if (m_step == 6) {
			if (!m_pickup.goTo(Constants.PICKUP_ARM_DOWN, 1.0)) {
				m_pickup.setArmSpeed(0.0, true);
				m_step += 2;
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

	/**
	 * General Cross Ramps
	 */
	public void teeterTotterGeneral() {
		if (m_step == 0) {
			m_drives.resetEncoders();
			m_drives.resetGyro();
			m_drives.brakesOff();
			m_step++;
		} else if (m_step == 1) {
			if (!m_pickup.goTo(Constants.PICKUP_RAMPS_HEIGHT, 1.0)) {
				m_pickup.setArmSpeed(0.0, true);
				m_step++;
			}
		} else if (m_step == 2) {
			if (m_drives.driveStraight(47.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if (m_step == 3) {
			if (!m_pickup.goTo(Constants.PICKUP_RAMPS_LOW, 0.5) || m_timer.get() >= 3.0) {
				m_pickup.setArmSpeed(0.0, true);
				m_drives.resetGyro();
				m_drives.resetEncoders();
				m_timer.stop();
				m_step++;
			}
		} else if (m_step == 4) {
			if (m_drives.getLeftEncoderDistance() > 30.0 && m_drives.getLeftEncoderDistance() < 50.0) {
				m_pickup.goTo(Constants.PICKUP_PORT_CULLIS_HIGH, 1.0);
			} else {
				m_pickup.setArmSpeed(0.0, true);
			}
			if (m_drives.driveStraight(160.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_pickup.setArmSpeed(0.0, true);
				m_drives.resetGyro();
				m_step++;
			}
		} else {
			m_drives.setSpeed(0.0);
			m_pickup.setArmSpeed(0.0, true);
		}
	}

	/**
	 * General Cross Obstacle
	 */

	public void generalCrossObstacle() {
		if (m_step == 0) {
			m_drives.resetEncoders();
			m_drives.resetGyro();
			m_drives.brakesOff();
			m_step++;
		} else if (m_step == 1) {
			if (m_drives.driveStraight(150.0, 0.0)) {
				m_drives.setSpeed(0.0);
				m_step++;
			}
		} else {
			m_drives.setSpeed(0.0);
		}
	}

	/**
	 * Teeter Totter-Slot 5 & Score
	 */
	public void teeterTotterSlotFiveAndScore() {
		if (m_step == 0) {
			m_drives.resetEncoders();
			m_drives.resetGyro();
			m_drives.brakesOff();
			m_step++;
		} else if (m_step == 1) {
			if (!m_pickup.goTo(Constants.PICKUP_RAMPS_HEIGHT, 1.0)) {
				m_pickup.setArmSpeed(0.0, true);
				m_step++;
			}
		} else if (m_step == 2) {
			if (m_drives.driveStraight(47.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if (m_step == 3) {
			if (!m_pickup.goTo(Constants.PICKUP_RAMPS_LOW, 0.5) || m_timer.get() >= 3.0) {
				m_pickup.setArmSpeed(0.0, true);
				m_drives.resetGyro();
				m_drives.resetEncoders();
				m_timer.stop();
				m_step++;
			}
		} else if (m_step == 4) {
			if (m_drives.getLeftEncoderDistance() > 30.0 && m_drives.getLeftEncoderDistance() < 50.0) {
				m_pickup.goTo(Constants.PICKUP_PORT_CULLIS_HIGH, 1.0);
			} else {
				m_pickup.setArmSpeed(0.0, true);
			}
			if (m_drives.driveStraight(210.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_pickup.setArmSpeed(0.0, true);
				m_drives.resetGyro();
				m_step++;
			}
		} else if (m_step == 5) {
			if (m_drives.turnToAngle(-73.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_drives.resetGyro();
				m_drives.resetEncoders();
				m_step++;
			}
		} else if (m_step == 6) {
			if (!m_pickup.goTo(Constants.PICKUP_ARM_DOWN, 1.0)) {
				m_pickup.setArmSpeed(0.0, true);
				m_step++;
			}
		} else if (m_step == 7) {
			if (m_drives.driveStraight(24.0, 0.5)) {
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

	/**
	 * Teeter Totter- Slot 4 & Score
	 */
	public void teeterTotterSlotFourAndScore() {

		if (m_step == 0) {
			m_drives.resetEncoders();
			m_drives.resetGyro();
			m_drives.brakesOff();
			m_step++;
		} else if (m_step == 1) {
			if (m_drives.driveStraight(47.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_step++;
			}
		} else if (m_step == 2) {
			if (!m_pickup.goTo(0.783, 0.5)) {
				m_pickup.setArmSpeed(0.0, true);
				m_drives.resetGyro();
				m_drives.resetEncoders();
				m_step++;
			}
		} else if (m_step == 3) {
			if (m_drives.getLeftEncoderDistance() > 30.0 && m_drives.getLeftEncoderDistance() < 50.0) {
				m_pickup.goTo(Constants.PICKUP_PORT_CULLIS_HIGH, 1.0);
			} else {
				m_pickup.setArmSpeed(0.0, true);
			}
			if (m_drives.driveStraight(96.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_pickup.setArmSpeed(0.0, true);
				m_drives.resetGyro();
				m_step++;
			}
		} else if (m_step == 4) {
			if (m_drives.turnToAngle(20.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_drives.resetGyro();
				m_drives.resetEncoders();
				m_step++;
			}
		} else if (m_step == 5) {
			if (m_drives.driveStraight(120.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_drives.resetGyro();
				m_step++;
			}
		} else if (m_step == 6) {
			if (m_drives.turnToAngle(-85.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_drives.resetGyro();
				m_drives.resetEncoders();
				m_step++;
			}
		} else {
			// m_pickup.eject();
			m_pickup.setArmSpeed(0.0, true);
			m_drives.setSpeed(0.0);
		}
	}

	public void lowBarAndScore() {
		if (m_step == 0) {
			m_drives.resetEncoders();
			m_drives.brakesOff();
			m_drives.resetGyro();
			m_timer.start();
			m_timer.reset();
			m_step++;
		} else if (m_step == 1) {
			if (!m_pickup.goTo(Constants.PICKUP_ARM_DOWN + 0.002, 1.0) || m_timer.get() > 4.0) {
				m_pickup.setArmSpeed(0.0, true);
				m_timer.stop();
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

}
