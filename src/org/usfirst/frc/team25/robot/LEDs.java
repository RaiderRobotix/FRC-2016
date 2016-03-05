package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.I2C;

public class LEDs {

	private static LEDs m_instance;

	private final I2C m_i2c;
	private byte[] m_dataToSend;
	private boolean m_ledsGoingUp;
	private int m_r, m_g, m_b;

	private LEDs() {
		m_i2c = new I2C(I2C.Port.kOnboard, Constants.I2C_DEVICE_ADDRESS);
		m_dataToSend = new byte[] { 0, 0, 0, 0 };
		m_ledsGoingUp = false;
		m_r = 0;
		m_g = 0;
		m_b = 0;
	}

	public static LEDs getInstance() {
		if (m_instance == null) {
			m_instance = new LEDs();
		}
		return m_instance;
	}

	public void setRed(int r) {
		m_dataToSend[1] = (byte) (r < 128 ? r : -(r - 127));
	}

	public void setGreen(int g) {
		m_dataToSend[2] = (byte) (g < 128 ? g : -(g - 127));
	}

	public void setBlue(int b) {
		m_dataToSend[3] = (byte) (b < 128 ? b : -(b - 127));
	}

	public void setRGB(int r, int g, int b) {
		setRed(r);
		setGreen(g);
		setBlue(b);
	}

	public void update() {
		m_i2c.writeBulk(m_dataToSend);
	}

	public void setOn(boolean b) {
		m_dataToSend[0] = (byte) (b ? 2 : 1);
	}

	public void reset() {
		m_dataToSend[0] = 0;
		update();
		m_dataToSend[0] = 1;
	}

	public void flash(Alliance color) {
		if (color == Alliance.Red) {
			m_r = 255;
			m_g += (m_ledsGoingUp ? 1 : -1);
			m_b = m_g;
		} else if (color == Alliance.Blue) {
			m_b = 255;
			m_r += (m_ledsGoingUp ? 1 : -1);
			m_g = m_r;
		} else {
			m_g = 255;
			m_r += (m_ledsGoingUp ? 1 : -1);
			m_b = m_r;
		}
		setRGB(m_r, m_g, m_b);
		if (m_r % 255 == 0 && m_g % 255 == 0 && m_b % 255 == 0) {
			m_ledsGoingUp = !m_ledsGoingUp;
		}
		update();
	}

}
