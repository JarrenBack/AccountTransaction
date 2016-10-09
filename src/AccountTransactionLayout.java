
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AccountTransactionLayout extends JFrame {

	private static Controller account = new Controller();
	private DefaultTableModel dtm;
	private JTable table;
	private String startFromAccount = "3", startToAccount = "1";
	
	public AccountTransactionLayout() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		
		dtm = account.getCurrentStateOfDB();
		table = new JTable(dtm);
		Dimension smallerSize = new Dimension(450, 50);
		table.setPreferredScrollableViewportSize(smallerSize);
		
		JScrollPane scrollPaneForTable = new JScrollPane(table);
				
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.insets = new Insets(4, 4, 4, 4);
		constraints.fill = GridBagConstraints.BOTH;

		contentPane.add(scrollPaneForTable,constraints);
		
		constraints.gridx = 0;
		constraints.weighty = 0;
		constraints.gridy = GridBagConstraints.RELATIVE;
		constraints.insets = new Insets(2, 4, 2, 4);
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		JLabel toLabel = new JLabel("From:");
		contentPane.add(toLabel,constraints);
		
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		JTextField fromField = new JTextField(startFromAccount,8);
		fromField.setMinimumSize(fromField.getPreferredSize());
		contentPane.add(fromField,constraints);
		
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		JLabel fromLabel = new JLabel("To:");
		contentPane.add(fromLabel,constraints);
		
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		JTextField toField = new JTextField(startToAccount,8);
		toField.setMinimumSize(toField.getPreferredSize());
		contentPane.add(toField,constraints);

		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		JLabel amountLabel = new JLabel("Amount:");
		contentPane.add(amountLabel,constraints);
		
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		JTextField amountField = new JTextField("100",8);
		amountField.setMinimumSize(amountField.getPreferredSize());
		contentPane.add(amountField,constraints);

		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		JButton clearButton = new JButton("Clear");
		contentPane.add(clearButton,constraints);

		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel dtm = (DefaultTableModel) (table.getModel());
				fromField.setText("");
				toField.setText("");
				amountField.setText("");
				
			}
		});
		
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		JButton transferButton = new JButton("Transfer");
		transferButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int accountFrom = Integer.parseInt(fromField.getText());
				int accountTo = Integer.parseInt(toField.getText());
				int amount = Integer.parseInt(amountField.getText());
				if (account.transfer(accountFrom, accountTo, amount)) {
					System.out.println("Transfer succesful");
					dtm = account.getCurrentStateOfDB();
					table.setModel(dtm);
				}
				else {
					System.out.println("transfer failed"); 
					String errorMessage = "Either you entered an account that doesn't exist or the amount is too much";
					JOptionPane.showMessageDialog(getContentPane(), errorMessage);
				}
			}
			
		});
		contentPane.add(transferButton,constraints);
	}

	public static void main(String[] args) {
		JFrame frame = new AccountTransactionLayout();
		frame.pack();
		frame.setVisible(true);
	}
}