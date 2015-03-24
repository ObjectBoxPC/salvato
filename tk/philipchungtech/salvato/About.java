package tk.philipchungtech.salvato;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * About window
 */
public class About extends javax.swing.JFrame {
	private javax.swing.JScrollPane copyrightscroll;
	private javax.swing.JTextArea copyrighttext;
	private javax.swing.JLabel versionlabel;

	/**
	 * Create new about window.
	 */
	public About() {
		initComponents();
		//Set the icon
		try {
			this.setIconImage(javax.imageio.ImageIO.read(MainWindow.class.getResourceAsStream(Main.ICON_PATH)));
		} catch(Exception e) {
			//Do nothing
		}
		//Set information from files
		java.io.InputStream infostream;
		BufferedReader inforeader;
		String infoline;
		infostream = About.class.getResourceAsStream(Main.VERSION_PATH);
		String version;
		try {
			inforeader = new BufferedReader(new InputStreamReader(infostream));
			if((infoline = inforeader.readLine()) != null) {
				version = infoline;
			} else {
				version = "[version info missing]";
			}
		} catch(Exception e) {
			version = "[version info missing]";
		}
		versionlabel.setText(versionlabel.getText()+version);
		infostream = About.class.getResourceAsStream(Main.COPYRIGHT_PATH);
		try {
			inforeader = new BufferedReader(new InputStreamReader(infostream));
			while((infoline = inforeader.readLine()) != null) {
				copyrighttext.append(infoline+System.getProperty("line.separator"));
			}
		} catch(Exception e) {
			//Do nothing
		}
		//Move cursor to top
		copyrighttext.select(0, 0);
	}

	/**
	 * Initialize the form components. This is automatically generated
	 * by NetBeans.
	 */
	@SuppressWarnings("unchecked")

	private void initComponents() {

		versionlabel = new javax.swing.JLabel();
		copyrightscroll = new javax.swing.JScrollPane();
		copyrighttext = new javax.swing.JTextArea();

		setTitle("About - Salvato");

		versionlabel.setText("Salvato Version ");

		copyrighttext.setEditable(false);
		copyrighttext.setColumns(50);
		copyrighttext.setLineWrap(true);
		copyrighttext.setWrapStyleWord(true);
		copyrightscroll.setViewportView(copyrighttext);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(copyrightscroll, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
					.addGroup(layout.createSequentialGroup()
						.addComponent(versionlabel)
						.addGap(0, 0, Short.MAX_VALUE)))
				.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addComponent(versionlabel)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(copyrightscroll, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
				.addContainerGap())
		);

		pack();
	}
}
