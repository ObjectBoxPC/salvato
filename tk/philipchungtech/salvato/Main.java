package tk.philipchungtech.salvato;

//import javax.sound.sampled.AudioFormat;
import java.util.Timer;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.AudioSystem;
import java.io.BufferedInputStream;
import javax.sound.sampled.DataLine;
import javax.swing.UIManager;

/**
 * Main "clearinghouse" containing the resources needed for the program
 */
public class Main {
	static final int INITIAL_TEMPO = 120;
	static final int INITIAL_BEATS_PER_MEASURE = 4;
	static final String ACCENT_BEAT_PATH = "/res/accent.wav";
	static final String NORMAL_BEAT_PATH = "/res/normal.wav";
	static final String ICON_PATH = "/res/icon.png";
	static final int FLASHER_DURATION = 75;
	static final String VERSION_PATH = "/VERSION.txt";
	static final String COPYRIGHT_PATH = "/COPYRIGHT.txt";

	private static MainWindow mainwindow;
	private static AdvancedSettings advsetwindow;
	private static About aboutwindow;
	private static int tempo;
	private static int beatspermeasure;
	private static int nextbeat;
	private static Timer tickerpad;
	private static Timer ticker;
	private static Clip accentbeat;
	private static Clip normalbeat;
	private static boolean shouldtick;

	/**
	 * Do nothing. This class should not be instantiated.
	 */
	private Main() {
		//This class should not be instatiated
	}

	/**
	 * Start the program. This initializes everything needed
	 * for the program to function.
	 * @param args Command-line arguments (ignored)
	 */
	public static void main(String[] args) {
		setAppearance();
		//Create and display the main window
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainwindow = new MainWindow();
				mainwindow.setVisible(true);
			}
		});
		//Create the advanced settings window
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				advsetwindow = new AdvancedSettings();
			}
		});
		//Create the about window
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				aboutwindow = new About();
			}
		});
		setTempo(INITIAL_TEMPO);
		setBeatsPerMeasure(INITIAL_BEATS_PER_MEASURE);
		setMixer(-1);
	}

	/**
	 * Set appearance options.
	 */
	private static void setAppearance() {
		//Set application name on Mac OS
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Salvato");
		//Set application name on X (Unix-like systems)
		java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		try {
			java.lang.reflect.Field appclass = toolkit.getClass().getDeclaredField("awtAppClassName");
			appclass.setAccessible(true);
			appclass.set(toolkit, "Salvato");
		} catch(Exception e) {
		}
		//Set Nimbus look and feel
		UIManager.LookAndFeelInfo[] lafis = UIManager.getInstalledLookAndFeels();
		for(UIManager.LookAndFeelInfo lafi : lafis) {
			if(lafi.getName().contains("Nimbus")) {
				try {
					UIManager.setLookAndFeel(lafi.getClassName());
				} catch(Exception e) {
					//Do nothing; keep default
				}
			}
		}
	}

	/**
	 * Set up the ticker at the current tempo.
	 */
	private static void scheduleTicker() {
		/*
		 * Delay scheduling the ticker to prevent a barrage of clicks
		 * (and possibly a crash) when rapidly changing the tempo
		 * (sliding the slider or spinning the spinner in MainWindow).
		 */
		if(tickerpad != null) {
			tickerpad.cancel();
		}
		tickerpad = new Timer(true);
		tickerpad.schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				if(ticker != null) {
					ticker.cancel();
				}
				tickerpad = new Timer(true);
				ticker = new Timer(true);
				ticker.scheduleAtFixedRate(new TickerTask(), 0, 60000 / getTempo());
			}
		}, 100);
	}

	/**
	 * Get the main window for the program.
	 * @return Main window
	 */
	static MainWindow getMainWindow() {
		return mainwindow;
	}

	/**
	 * Get the advanced settings window for the program.
	 * @return Advanced settings window
	 */
	static AdvancedSettings getAdvancedSettingsWindow() {
		return advsetwindow;
	}

	/**
	 * Get the about window for the program.
	 * @return About window
	 */
	static About getAboutWindow() {
		return aboutwindow;
	}

	/**
	 * Get the current tempo.
	 * @return Current tempo in beats per minute
	 */
	static int getTempo() {
		return tempo;
	}

	/**
	 * Set the tempo.
	 * @param t New tempo in beats per minute
	 */
	static void setTempo(int t) {
		tempo = t;
		nextbeat = 1;
		scheduleTicker();
	}

	/**
	 * Get the current number of beats per measure.
	 * @return Current number of beats per measure
	 */
	static int getBeatsPerMeasure() {
		return beatspermeasure;
	}

	/**
	 * Set the number of beats per measure
	 * @param b New number of beats per measure
	 */
	static void setBeatsPerMeasure(int b) {
		beatspermeasure = b;
		nextbeat = 1;
	}

	/**
	 * Get the number of the next beat (starting with 1).
	 * @return Number of the next beat
	 */
	static int getNextBeat() {
		return nextbeat;
	}

	/**
	 * Increment the beat, resetting to 1 if the end
	 * of the measure is reached.
	 */
	static void incrementBeat() {
		nextbeat++;
		if(nextbeat > getBeatsPerMeasure()) {
			nextbeat = 1;
		}
	}

	/**
	 * Get the accent (first) beat.
	 * This audio clip should be playable on the selected mixer.
	 * @return A clip representing an accent beat
	 */
	static Clip getAccentBeat() {
		return accentbeat;
	}

	/**
	 * Get the normal (not first) beat.
	 * This audio clip should be playable on the selected mixer.
	 * @return A clip representing an accent beat
	 */
	static Clip getNormalBeat() {
		return normalbeat;
	}

	/**
	 * Set the mixer to use and initialize the beat clips.
	 * If the given mixer index is not valid, the system default mixer is used.
	 * @param i Index of the mixer in the array returned by
	 * {@link javax.sound.sampled.AudioSystem#getMixerInfo()}.
	 */
	static void setMixer(int i) {
		Mixer.Info[] mixerinfos = AudioSystem.getMixerInfo();
		Mixer mixer;
		if((0 <= i) && (i < mixerinfos.length)) {
			mixer = AudioSystem.getMixer(mixerinfos[i]);
		} else {
			mixer = AudioSystem.getMixer(null);
		}
		AudioInputStream clipstream;
		try {
			if(accentbeat != null) {
				accentbeat.close();
			}
			clipstream = AudioSystem.getAudioInputStream(new BufferedInputStream(Main.class.getResourceAsStream(ACCENT_BEAT_PATH)));
			accentbeat = (Clip) mixer.getLine(new DataLine.Info(Clip.class, clipstream.getFormat()));
			accentbeat.open(clipstream);
		} catch(Exception e) {
			accentbeat = null;
		}
		try {
			if(normalbeat != null) {
				normalbeat.close();
			}
			clipstream = AudioSystem.getAudioInputStream(new BufferedInputStream(Main.class.getResourceAsStream(NORMAL_BEAT_PATH)));
			normalbeat = (Clip) mixer.getLine(new DataLine.Info(Clip.class, clipstream.getFormat()));
			normalbeat.open(clipstream);
		} catch(Exception e) {
			normalbeat = null;
		}
	}

	/**
	 * Get the user's preference for an audio tick.
	 * @return {@code true} if there should be an audio tick,
	 * {@code false} otherwise
	 */
	static boolean getShouldTick() {
		return shouldtick;
	}

	/**
	 * Set the user's preference for an audio tick.
	 * @param t New preference: {@code true} for a tick,
	 * {@code false} for no tick
	 */
	static void setShouldTick(boolean t) {
		shouldtick = t;
	}
}
