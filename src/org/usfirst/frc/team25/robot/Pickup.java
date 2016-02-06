package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Talon;

public class Pickup {

	private static Pickup m_instance;
	private final Talon m_rollers;
	private final Talon m_arm;
	private final AnalogPotentiometer m_pot;

	private Pickup() {
		m_rollers = new Talon(Constants.PICKUP_ROLLERS_PWM);
		m_arm = new Talon(Constants.PICKUP_ARM_PWM);

		m_pot = new AnalogPotentiometer(Constants.PICKUP_POT_PWM);
	}

	public static Pickup getInstance() {
		if (m_instance == null) {
			m_instance = new Pickup();
		}
		return m_instance;
	}

	public void intake() {
		m_rollers.set(1.0);
	}

	public void eject() {
		m_rollers.set(-1.0);
	}

	public void stopRollers() {
		m_rollers.set(0.0);
	}

	public boolean goTo(double value) {
		double difference = m_pot.get() - value;
		if(Math.abs(difference) <= 0.005) {
			//If very close, stop
			m_arm.set(0.0);
			return false;
		}
		boolean down = difference < 0.0;
		double slowSpeed = 0.2;
		if(value == Constants.PICKUP_ARM_UP) {
			slowSpeed = 0.4;
		}
		if(Math.abs(difference) <= slowSpeed) {
			//Slow down if in close zone
			m_arm.set(down ? 0.5 : -0.5);
		} else {
			m_arm.set(down ? 1.0 : -1.0);
		}
		return true;
	}
	
	public void setArmSpeed(double speed, boolean override) {
		if(override) {
			m_arm.set(speed / 3.0);
			return;
		}
		if ((m_pot.get() <= Constants.PICKUP_ARM_DOWN && speed > 0.0)
				|| (m_pot.get() >= Constants.PICKUP_ARM_UP && speed < 0.0)) {
			m_arm.set(0.0);
		} else {
			if ((m_pot.get() > Constants.PICKUP_ARM_UP - 0.04 && speed < 0.0)
					|| (m_pot.get() < Constants.PICKUP_ARM_DOWN + 0.02 && speed > 0.0)) {
				m_arm.set(speed / 2.0);
			} else {
				m_arm.set(speed);
			}
		}
	}

	public double getPot() {
		return m_pot.get();
	}

}