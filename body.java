import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class body extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private final Action action = new SwingAction();
	private final Action action_1 = new SwingAction_1();
	JComboBox<String> comboBox;
	JButton btnNewButton;
	JButton btnNewButton_1;
	JLabel label;
	String name;
	File[] list;
	boolean isEmpty = true;
	static File def = new File("path.txt");
	static Scanner scan;
	static String path;
	private JTextField textField_1;
	private final Action action_2 = new SwingAction_2();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					body frame = new body();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public body() {
		setTitle("MusicSelector v1.0");
		try {
			scan = new Scanner(def);
			scan.useDelimiter("\n");
			path = scan.next();
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		File f = new File(path);
		list = f.listFiles();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		comboBox = new JComboBox<String> ();
		for(int i=0; i<list.length; i++){
			if(list[i].isFile()){
				comboBox.addItem(list[i].getName());
				isEmpty = false;
			}
		}
		if(isEmpty){comboBox.addItem("ファイルがありません");}
		comboBox.setBounds(12, 10, 297, 19);
		panel.add(comboBox);

		textField = new JTextField();
		textField.setBounds(12, 222, 227, 19);
		panel.add(textField);
		textField.setColumns(10);

		btnNewButton = new JButton("New button");
		btnNewButton.setAction(action);
		btnNewButton.setBounds(295, 221, 91, 21);
		panel.add(btnNewButton);

		btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setAction(action_1);
		btnNewButton_1.setBounds(321, 9, 91, 21);
		panel.add(btnNewButton_1);

		label = new JLabel("");
		label.setBounds(128, 51, 284, 26);
		panel.add(label);

		textField_1 = new JTextField(path);
		textField_1.setBounds(12, 193, 227, 19);
		panel.add(textField_1);
		textField_1.setColumns(10);

		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.setAction(action_2);
		btnNewButton_2.setBounds(269, 192, 143, 21);
		panel.add(btnNewButton_2);
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "OK");
			putValue(SHORT_DESCRIPTION, "検索");
		}
		public void actionPerformed(ActionEvent e) {
			String s = textField.getText().toLowerCase();
			String m;
			if(!s.isEmpty()){
				comboBox.removeAllItems();
				for(int i=0; i<list.length; i++){
					m = list[i].getName().toLowerCase();
					if(m.substring(0, s.length()).equals(s)){comboBox.addItem(list[i].getName());}
				}
			}
			else{
				comboBox.removeAllItems();
				for(int i=0; i<list.length; i++){
					comboBox.addItem(list[i].getName());
				}
			}
		}
	}
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "再生");
			putValue(SHORT_DESCRIPTION, "選択されているものを再生");
		}
		public void actionPerformed(ActionEvent e) {
			Runtime rt = Runtime.getRuntime();
			boolean d = true;
			if (comboBox.getSelectedIndex() == -1){
				d = false;
		    }else{
		        name = (String)comboBox.getSelectedItem();

		    }
			try {
				if(d) {
					Process p = rt.exec("cmd /c \"C:\\Users\\zougame\\Desktop\\music 1\\" + name + "\"");
					label.setText("再生中：" + name);
					p.waitFor();
					p.destroy();
				}
			} catch (IOException | InterruptedException e1) {
				// TODO 自動生成された catch ブロック
				label.setText("再生に失敗しました");
			}
		}
	}
	private class SwingAction_2 extends AbstractAction {
		public SwingAction_2() {
			putValue(NAME, "フォルダ変更");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc= new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			File dir=new File(path);
			fc.setCurrentDirectory(dir);
			int d=fc.showOpenDialog(null);
			if(d==JFileChooser.APPROVE_OPTION){
				File F=fc.getSelectedFile();
				path=F.getAbsolutePath();
				StringBuilder sb = new StringBuilder();
				sb.append(path);
				int offset = 0;
				for(int i=0; i<path.length(); i++){
					if(path.charAt(i)=='\\'){sb.insert(i+offset, '\\'); offset++;}
				}
				path = new String(sb);
				System.out.println(path);
				textField_1.setText(path);
				comboBox.removeAllItems();
				File fi = new File(path);
				list = fi.listFiles();
				for(int i=0; i<list.length; i++){
					if(list[i].isFile()){
						comboBox.addItem(list[i].getName());
						isEmpty = false;
					}
				}
				if(isEmpty){comboBox.addItem("ファイルがありません");}
				 try {
					BufferedWriter bw = new BufferedWriter(new FileWriter("path.txt"));
					bw.write(path);
					bw.close();
				} catch (IOException e1) {
					// TODO 自動生成された catch ブロック
					label.setText("フォルダ情報をうまく書き込めませんでした");
				}
			}
		}
	}
}
