package tk.philipchungtech.salvato;

import java.util.TimerTask;
import javax.sound.sampled.Clip;

/**
 * {@link TimerTask} implementation that will flash the flasher and
 * play a sound
 */
class TickerTask extends TimerTask {
	@Override
	public void run() {
		//Sound the audio beat
		Clip accentbeat = Main.getAccentBeat();
		Clip normalbeat = Main.getNormalBeat();
		if(Main.getShouldTick()) {
			if(Main.getNextBeat() == 1) {
				if(accentbeat != null) {
					accentbeat.stop();
					accentbeat.setFramePosition(0);
					accentbeat.start();
				}
			} else {
				if(normalbeat != null) {
					normalbeat.stop();
					normalbeat.setFramePosition(0);
					normalbeat.start();
				}
			}
		}
		//Show the count
		if(Main.getMainWindow() != null) {
			Main.getMainWindow().getBeatCounter().setText(String.valueOf(Main.getNextBeat()));
		}
		//Finish, go to next beat
		Main.incrementBeat();
	}
}
