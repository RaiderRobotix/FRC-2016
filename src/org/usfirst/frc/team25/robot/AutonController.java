package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class AutonController {

	private static AutonController m_instance;

	private int m_step;
	private final Drivebase m_drives;
	private final Pickup m_pickup;
	private final Timer m_timer;
	private final Joystick m_switchBox;

	private AutonController() {
		m_drives = Drivebase.getInstance();
		m_pickup = Pickup.getInstance();
		m_switchBox = new Joystick(3);
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

	public int getAutonChosen() {
		int r = 0;
		if (m_switchBox.getRawButton(5)) {
			r += 1;
		}
		if (m_switchBox.getRawButton(12)) {
			r += 2;
		}
		if (m_switchBox.getRawButton(7)) {
			r += 3;
		}
		if (m_switchBox.getRawButton(11)) {
			r += 4;
		}
		if (m_switchBox.getRawButton(6)) {
			r += 5;
		}
		if (m_switchBox.getRawButton(8)) {
			r += 6;
		}
		return r;
	}

	/**
	 * Not finished yet.
	 * 
	 * @param speed
	 *            The speed to run the auton with
	 */
	public void goOverObstacle(double speed) {
		if (m_step == 0) {
			m_drives.resetEncoders();
			m_drives.resetNavX();
			m_drives.brakesOff();
			m_timer.start();
			m_timer.reset();
			m_step++;
		} else if (m_step == 1) {
			if (m_timer.get() > 0.5) {
				m_step++;
			}
		} else if (m_step == 2) {
			if (Math.abs(m_drives.getGyroRoll()) < 0.75) {
				m_drives.driveStraight(1000.0, speed);
			} else {
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if (m_step == 3) {
			if (Math.abs(m_drives.getGyroRoll()) < 0.75 && m_timer.get() > 0.25) {
				m_drives.setSpeed(0.0);
				m_timer.stop();
				m_drives.resetEncoders();
				m_step++;
			} else {
				m_drives.driveStraight(1000.0, speed);
			}
		} else {
			m_drives.setSpeed(0.0);
		}
	}

	/**
	 * After an obstacle in slot two, this will score the boulder.
	 */
	public void slotTwoPath() {
		if (m_step == 0) {
			m_drives.resetEncoders();
			m_drives.resetNavX();
			m_drives.brakesOff();
			m_step++;
		} else if (m_step == 1) {
			if (m_drives.sonicDriveStraight(50.0, 0.65)) {
				m_drives.setSpeed(0.0);
				m_drives.resetNavX();
				m_step++;
			}
		} else if (m_step == 2) {
			if (m_drives.turnToAngle(48.0, 0.55)) {
				m_drives.setSpeed(0.0);
				m_step++;
			}
		} else if (m_step == 3) {
			if (!m_pickup.goTo(Constants.PICKUP_ARM_DOWN, 0.8)) {
				m_pickup.setArmSpeed(0.0, true);
				m_drives.resetEncoders();
				m_drives.resetNavX();
				m_step++;
			}
		} else if (m_step == 4) {
			if (m_drives.driveStraight(36.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_step++;
			}
		} else {
			m_drives.brakesOn();
			m_pickup.eject();
			m_drives.setSpeed(0.0);
			m_pickup.setArmSpeed(0.0, true);
		}
	}

	/**
	 * @deprecated
	 * 
	 * 			This is the model, but still needs fixing.
	 * 
	 *             Teeter Totter- Slot 2 & Score
	 */
	public void teeterTotterSlotTwoAndScore() {
		if (m_step == 0) {
			m_drives.resetEncoders();
			m_drives.resetNavX();
			m_drives.brakesOff();
			m_step++;
		} else if (m_step == 1) {
			if (!m_pickup.goTo(Constants.PICKUP_RAMPS_HEIGHT, 0.7)) {
				m_pickup.setArmSpeed(0.0, true);
				m_step++;
			}
		} else if (m_step == 2) {
			if (m_drives.driveStraight(44.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if (m_step == 3) {
			if (!m_pickup.goTo(Constants.PICKUP_RAMPS_LOW, 0.5) || m_timer.get() >= 3.0) {
				m_pickup.setArmSpeed(0.0, true);
				m_drives.resetNavX();
				m_drives.resetEncoders();
				m_timer.stop();
				m_step++;
			}
		} else if (m_step == 4) {
			if (m_drives.getLeftEncoderDistance() > 30.0 && m_drives.getLeftEncoderDistance() < 50.0) {
				m_pickup.goTo(Constants.PICKUP_ARM_UP, 1.0);
			} else {
				m_pickup.setArmSpeed(0.0, true);
			}
			if (m_drives.driveStraight(210.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_pickup.setArmSpeed(0.0, true);
				m_drives.resetNavX();
				m_step++;
			}
		} else if (m_step == 5) {
			if (m_drives.turnToAngle(63, 0.5)) {
				m_drives.resetEncoders();
				m_drives.resetNavX();
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
	 * The working low bar auton.
	 */
	public void lowBarAndScore() {
		if (m_step == 0) {
			m_drives.resetEncoders();
			m_drives.brakesOff();
			m_drives.resetNavX();
			m_timer.start();
			m_timer.reset();
			m_step++;
		} else if (m_step == 1) {
			if (!m_pickup.goTo(Constants.PICKUP_ARM_DOWN + 0.002, 1.0) || m_timer.get() > 3.0) {
				m_pickup.setArmSpeed(0.0, true);
				m_timer.stop();
				m_step++;
			}
		} else if (m_step == 2) {
			if (m_drives.driveStraight(233.0, 0.65)) {
				m_drives.setSpeed(0.0);
				m_drives.resetNavX();
				m_step++;
			}
		} else if (m_step == 3) {
			if (m_drives.turnToAngle(64, 0.5)) {
				m_drives.setSpeed(0.0);
				m_drives.resetEncoders();
				m_drives.resetNavX();
				m_step++;
			}
		} else if (m_step == 4) {
			if (m_drives.driveStraight(79.0, 0.65)) {
				m_drives.setSpeed(0.0);
				m_pickup.eject();
				m_drives.brakesOff();
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if (m_step == 5) {
			m_pickup.eject();
			if (m_timer.get() > 1.0) {
				m_timer.stop();
				m_step++;
			}
		} else {
			m_pickup.intake(false);
			if (m_pickup.lineBroken()) {
				m_timer.start();
				m_timer.reset();
				m_step--;
			}
		}
	}

	/**
	 * The working low bar auton Special.
	 */
	public void lowBarAndScoreSpecial() {
		if (m_step == 0) {
			m_drives.resetEncoders();
			m_drives.brakesOff();
			m_drives.resetNavX();
			m_timer.start();
			m_timer.reset();
			m_step++;
		} else if (m_step == 1) {
			if (!m_pickup.goTo(Constants.PICKUP_ARM_DOWN + 0.002, 1.0) || m_timer.get() > 3.0) {
				m_pickup.setArmSpeed(0.0, true);
				m_timer.stop();
				m_step++;
			}
		} else if (m_step == 2) {
			if (m_drives.driveStraight(233.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_drives.resetNavX();
				m_pickup.setArmSpeed(0.0, true);
				m_step++;
			}
			if (m_drives.getLeftEncoderDistance() > 75.0) {
				if (!m_pickup.goTo(Constants.PICKUP_ARM_UP, 1.0)) {
					m_pickup.setArmSpeed(0.0, true);
				}
			}
		} else if (m_step == 3) {
			if (m_drives.turnToAngle(64, 0.5)) {
				m_drives.setSpeed(0.0);
				m_drives.resetEncoders();
				m_drives.resetNavX();
				m_step++;
			}
		} else if (m_step == 4) {
			if (!m_pickup.goTo(Constants.PICKUP_ARM_DOWN, 0.8)) {
				m_pickup.setArmSpeed(0.0, true);
				m_drives.resetEncoders();
				m_drives.resetNavX();
				m_step++;
			}
		} else if (m_step == 5) {
			if (m_drives.driveStraight(79.0, 0.5)) {
				m_drives.setSpeed(0.0);
				m_pickup.eject();
				m_drives.brakesOff();
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if (m_step == 6) {
			m_pickup.eject();
			if (m_timer.get() > 1.0) {
				m_timer.stop();
				m_step++;
			}
		} else {
			m_pickup.intake(false);
			if (m_pickup.lineBroken()) {
				m_timer.start();
				m_timer.reset();
				m_step--;
			}
		}
	}

	/**
	 * Go over obstacle and dont score
	 */
	public void goOverObstacleNoScore() {
		if (m_step == 0) {
			m_drives.brakesOff();
			m_drives.resetEncoders();
			m_drives.resetNavX();
			m_timer.start();
			m_timer.reset();
			m_step++;
		} else if (m_step == 1) {
			if (m_timer.get() < 5.0) {
				m_drives.driveStraight(1000.0, 0.8);
			} else if (m_timer.get() < 10.0) {
				m_drives.sonicDriveStraight(48.0, 0.8);
			} else {
				m_timer.stop();
				m_drives.setSpeed(0.0);
				m_step++;
			}
		} else {
			m_drives.setSpeed(0.0);
		}
	}

}
