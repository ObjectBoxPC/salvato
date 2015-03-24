package tk.philipchungtech.salvato;

import javax.sound.sampled.Mixer;
import javax.sound.sampled.AudioSystem;

/**
 * Advanced settings window
 */
class AdvancedSettings extends javax.swing.JFrame {
	private javax.swing.JLabel devicelabel;
	private javax.swing.JList mixerlist;
	private javax.swing.JScrollPane mixerlistscroll;
	private javax.swing.DefaultListModel mixermodel;

	/**
	 * Create new advanced settings window.
	 */
	public AdvancedSettings() {
		//Set the icon
		try {
			this.setIconImage(javax.imageio.ImageIO.read(MainWindow.class.getResourceAsStream(Main.ICON_PATH)));
		} catch(Exception e) {
			//Do nothing
		}
		mixermodel = new javax.swing.DefaultListModel();
		initComponents();
		populateMixerList();
	}

	/**
	 * Populate the list of mixers.
	 */
	private void populateMixerList() {
		Mixer.Info[] mixerinfos = AudioSystem.getMixerInfo();
		Mixer.Info mixerinfo;
		for(int i = 0; i < mixerinfos.length; i++) {
			mixerinfo = mixerinfos[i];
			mixermodel.addElement(mixerinfo.getName());
			if(mixerinfo.equals(AudioSystem.getMixer(null).getMixerInfo())) {
				mixerlist.setSelectedIndex(i);
			}
		}
	}

	/**
	 * Initialize the form components. This is automatically generated
	 * by NetBeans.
	 */
	@SuppressWarnings("unchecked")
	private void initComponents() {
		mixerlistscroll = new javax.swing.JScrollPane();
		mixerlist = new javax.swing.JList();
		devicelabel = new javax.swing.JLabel();

		setTitle("Advanced Settings - Salvato");

		mixerlist.setModel(mixermodel);
		mixerlist.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		mixerlist.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
			public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
				mixerListValueChanged(evt);
			}
		});
		mixerlistscroll.setViewportView(mixerlist);

		devicelabel.setText("Audio Output Device");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(mixerlistscroll, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
					.addGroup(layout.createSequentialGroup()
						.addComponent(devicelabel)
						.addGap(0, 0, Short.MAX_VALUE)))
				.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addGap(15, 15, 15)
				.addComponent(devicelabel)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(mixerlistscroll, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
				.addContainerGap())
		);

		pack();
	}

	/**
	 * Handle a change in the selection in the mixer list.
	 * @param evt Event information
	 * @see Main#setMixer(int)
	 */
	private void mixerListValueChanged(javax.swing.event.ListSelectionEvent evt) {
		if(!evt.getValueIsAdjusting()) {
			Main.setMixer(mixerlist.getSelectedIndex());
		}
	}
}
