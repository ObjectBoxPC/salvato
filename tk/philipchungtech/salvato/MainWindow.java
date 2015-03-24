package tk.philipchungtech.salvato;

/**
 * Main window
 */
class MainWindow extends javax.swing.JFrame {
	private javax.swing.JButton aboutbutton;
	private javax.swing.JButton advsetbutton;
	private javax.swing.JCheckBox audiotickcheck;
	private javax.swing.JLabel beatcounter;
	private javax.swing.JLabel beatslabel;
	private javax.swing.JSpinner beatspinner;
	private javax.swing.JLabel tempolabel;
	private javax.swing.JSlider temposlider;
	private javax.swing.JSpinner tempospinner;
	private javax.swing.JButton tempotap;
	private java.util.Timer taptimer;
	private java.util.Queue<Integer> taptimes;
	private int timesincetap;
	private static final int TAP_TIMER_INTERVAL = 10;
	private static final int MAX_TIME_SINCE_TAP = 2000;
	private static final int MAX_TAP_COUNT = 10;

	/**
	 * Create new main window.
	 */
	public MainWindow() {
		//Set the icon
		try {
			this.setIconImage(javax.imageio.ImageIO.read(MainWindow.class.getResourceAsStream(Main.ICON_PATH)));
		} catch(Exception e) {
			//Do nothing
		}
		initComponents();
		startTapSystem();
	}

	/**
	 * Set up the tap system.
	 */
	private void startTapSystem() {
		taptimes = new java.util.LinkedList<Integer>();
		taptimer = new java.util.Timer(true);
		taptimer.scheduleAtFixedRate(new java.util.TimerTask() {
			@Override
			public void run() {
				if(timesincetap < MAX_TIME_SINCE_TAP) {
					timesincetap += TAP_TIMER_INTERVAL;
				} else {
					timesincetap = 0;
					taptimes.clear();
				}
			}
		}, 0, TAP_TIMER_INTERVAL);
	}

	/**
	 * Update the tempo as displayed by the slider and spinner.
	 * @param t New tempo in beats per minute
	 * @see Main#setTempo(int)
	 */
	private void updateTempo(int t) {
		Main.setTempo(t);
		temposlider.setValue(Main.getTempo());
		tempospinner.setValue(Main.getTempo());
	}

	/**
	 * Get the beat counter in this window.
	 * @return Beat counter
	 */
	javax.swing.JLabel getBeatCounter() {
		return beatcounter;
	}

	/**
	 * Initialize the form components. This is automatically generated
	 * by NetBeans.
	 */
	@SuppressWarnings("unchecked")
	private void initComponents() {
		temposlider = new javax.swing.JSlider();
		tempospinner = new javax.swing.JSpinner();
		tempolabel = new javax.swing.JLabel();
		tempotap = new javax.swing.JButton();
		advsetbutton = new javax.swing.JButton();
		beatspinner = new javax.swing.JSpinner();
		beatslabel = new javax.swing.JLabel();
		audiotickcheck = new javax.swing.JCheckBox();
		aboutbutton = new javax.swing.JButton();
		beatcounter = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Salvato");

		temposlider.setMaximum(250);
		temposlider.setMinimum(50);
		temposlider.setValue(120);
		temposlider.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				tempoSliderChanged(evt);
			}
		});

		tempospinner.setModel(new javax.swing.SpinnerNumberModel(120, 50, 250, 1));
		tempospinner.setName("");
		tempospinner.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				tempoSpinnerChanged(evt);
			}
		});

		tempolabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		tempolabel.setText("Tempo (beats per minute)");
		tempolabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		tempotap.setText("Tap");
		tempotap.setToolTipText("Click to tap the tempo");
		tempotap.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tempoTapClicked(evt);
			}
		});

		advsetbutton.setText("Advanced Settings");
		advsetbutton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				advancedButtonClicked(evt);
			}
		});

		beatspinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(4), Integer.valueOf(1), null, Integer.valueOf(1)));
		beatspinner.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				beatSpinnerChanged(evt);
			}
		});

		beatslabel.setText("Beats per measure");

		audiotickcheck.setText("Audio Tick");
		audiotickcheck.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				audioTickCheckChanged(evt);
			}
		});

		aboutbutton.setText("About");
		aboutbutton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				aboutButtonClicked(evt);
			}
		});

		beatcounter.setFont(new java.awt.Font("Dialog", 1, 36));
		beatcounter.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		beatcounter.setText("0");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
						.addComponent(advsetbutton)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(aboutbutton))
					.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addGroup(layout.createSequentialGroup()
								.addComponent(beatcounter, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
									.addComponent(temposlider, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
									.addComponent(tempolabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
							.addGroup(layout.createSequentialGroup()
								.addComponent(audiotickcheck)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(beatslabel)
								.addGap(18, 18, 18)))
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
							.addComponent(beatspinner)
							.addComponent(tempotap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(tempospinner))))
				.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
						.addGap(13, 13, 13)
						.addComponent(beatcounter, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
					.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(tempospinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(temposlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(tempotap)
							.addComponent(tempolabel))))
				.addGap(18, 18, 18)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
					.addComponent(beatspinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
					.addComponent(beatslabel)
					.addComponent(audiotickcheck))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 35, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
					.addComponent(advsetbutton)
					.addComponent(aboutbutton))
				.addContainerGap())
		);

		pack();
	}

	/**
	 * Handle a change in the tempo slider by updating the tempo.
	 * @param evt Event information
	 * @see #updateTempo(int)
	 */
	private void tempoSliderChanged(javax.swing.event.ChangeEvent evt) {
		updateTempo(temposlider.getValue());
	}

	/**
	 * Handle a change in the tempo spinner by updating the tempo.
	 * @param evt Event information
	 * @see #updateTempo(int)
	 */
	private void tempoSpinnerChanged(javax.swing.event.ChangeEvent evt) {
		updateTempo((Integer) tempospinner.getValue());
	}

	/**
	 * Handle a change in the tick checkbox by updating the audio tick preference.
	 * @param evt Event information
	 * @see Main#setShouldTick(boolean)
	 */
	private void audioTickCheckChanged(java.awt.event.ItemEvent evt) {
		Main.setShouldTick(audiotickcheck.isSelected());
	}

	/**
	 * Handle a change in the beats-per-measure spinner by updating the tempo.
	 * @param evt Event information
	 * @see Main#setBeatsPerMeasure(int)
	 */
	private void beatSpinnerChanged(javax.swing.event.ChangeEvent evt) {
		Main.setBeatsPerMeasure((Integer) beatspinner.getValue());
	}

	/**
	 * Handle a click on the tap button by calculating the tapped tempo.
	 * @param evt Event information
	 */
	private void tempoTapClicked(java.awt.event.ActionEvent evt) {
		if(taptimes.size() >= MainWindow.MAX_TAP_COUNT) {
			taptimes.remove();
		}
		taptimes.add(timesincetap);
		timesincetap = 0;
		java.util.Iterator<Integer> timesiter = taptimes.iterator();
		int totaltime = 0;
		while(timesiter.hasNext()) {
			totaltime += timesiter.next();
		}
		updateTempo(60000 / totaltime * taptimes.size());
	}

	/**
	 * Handle a click on the advanced settings button by showing the advanced settings window.
	 * @param evt Event information.
	 * @see Main#getAdvancedSettingsWindow()
	 */
	private void advancedButtonClicked(java.awt.event.ActionEvent evt) {
		Main.getAdvancedSettingsWindow().setVisible(true);
	}

	/**
	 * Handle a click on the about button by showing the about window.
	 * @param evt Event information
	 */
	private void aboutButtonClicked(java.awt.event.ActionEvent evt) {
		Main.getAboutWindow().setVisible(true);
	}
}
