package sms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.SystemColor;

/**
 * The class that manages the front-end part of the application and holds the
 * actions performed out there
 * 
 * @author Artiom
 *
 */
public class Application {

	/**
	 * The contents of the connection window where you have to connect to a database
	 */
	private JFrame connectionFrame;

	/**
	 * The contents of the management window where you perform actions on the
	 * database
	 */
	private JFrame managementFrame;

	/**
	 * The text field that stores the login the user has written
	 */
	private JTextField loginField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.connectionFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Application() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		connectionFrame = new JFrame();
		connectionFrame.setBounds(100, 100, 640, 480);
		connectionFrame.setResizable(false);
		connectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		connectionFrame.setTitle("Student Management System");

		// The blue-colored panel in the top part of the application
		JPanel topPanel = new JPanel();
		topPanel.setBackground(SystemColor.textHighlight);
		connectionFrame.getContentPane().add(topPanel, BorderLayout.NORTH);

		// The text that informs the user that they have to connect to a database
		JLabel connectText = new JLabel("Connect to a database:");
		connectText.setForeground(new Color(255, 255, 255));
		connectText.setFont(new Font("Tahoma", Font.PLAIN, 50));
		topPanel.add(connectText);

		// The panel in the bottom part of the application
		JPanel bottomPanel = new JPanel();
		connectionFrame.getContentPane().add(bottomPanel, BorderLayout.CENTER);

		// The text that informs the user where they have to type the login
		JLabel loginText = new JLabel("Login:");
		loginText.setBounds(95, 135, 55, 25);
		loginText.setFont(new Font("Tahoma", Font.PLAIN, 20));

		// The text that informs the user where they have to type the password
		JLabel passwordText = new JLabel("Password:");
		passwordText.setBounds(95, 175, 100, 25);
		passwordText.setFont(new Font("Tahoma", Font.PLAIN, 20));

		// Initializes the text field where user writes the login
		loginField = new JTextField();
		loginField.setBounds(200, 140, 330, 20);
		loginField.setColumns(10);

		// Initializes the text field where user writes the password
		passwordField = new JPasswordField();
		passwordField.setBounds(200, 180, 330, 20);

		// The button to press after the login and password were written
		JButton connectButton = new JButton("Connect");
		connectButton.setBounds(240, 290, 140, 35);
		connectButton.setFont(new Font("Tahoma", Font.PLAIN, 20));

		// Execute connection and create a table when "Connect" button pressed
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// If one of the fields are empty then warn user about it
				if (loginField.getText().equals("") || passwordField.getText().equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), "Fields are empty!", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					// Get login and password from fields and set them for database handler
					DBHandler.setLogin(loginField.getText());
					DBHandler.setPassword(passwordField.getText());

					// Show an input dialog in order to get a name for the table
					String tableName;
					tableName = JOptionPane.showInputDialog(new JFrame(),
							"Type a name for your table(if you already have one, just type the same name):");

					// If no text was written then warn user about that
					if (tableName == null || tableName.equals("")) {
						JOptionPane.showMessageDialog(new JFrame(),
								"Table hasn't been created or imported! Please fill in the fields properly! ", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					// If table has\hasn't been successfully created then inform the user about that
					if (DBHandler.createTable(tableName)) {
						JOptionPane.showMessageDialog(new JFrame(), "Table has been successfully created or imported!",
								"Success", JOptionPane.INFORMATION_MESSAGE);

						// Change the contents of the window in order to manage the database
						managementFrame = new JFrame();
						managementFrame.setBounds(100, 100, 860, 540);
						managementFrame.setResizable(false);
						managementFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						managementFrame.setTitle("Student Management System");

						connectionFrame.setVisible(false);
						managementFrame.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(new JFrame(),
								"Table hasn't been created or imported! Check your database credentials!", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});

		bottomPanel.setLayout(null);
		bottomPanel.add(passwordText);
		bottomPanel.add(loginText);
		bottomPanel.add(passwordField);
		bottomPanel.add(loginField);
		bottomPanel.add(connectButton);
		
	}
}
