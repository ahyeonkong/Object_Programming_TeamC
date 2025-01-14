package ref_demo;

import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.refrigerator.RefMgr;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

public class Submit {
	JDialog frame;
	String filepath;
	File selectedfdfile;
	JTextField txt_fdfile;
	JTextField txt_refcode;
	JTextArea textArea;
	public Submit() {
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		frame = new JDialog(GUIMain.getInstance().getframe(),"회원가입");
		frame.setBounds(100, 100, 364, 326);
		addComponentsToPane(frame.getContentPane());
		frame.pack();
		frame.setVisible(true);

	}
	private void addComponentsToPane(Container pane) {


		JLabel lbluserinfo = new JLabel("사용자 정보(#로 구분해서 작성)");
		lbluserinfo.setFont(new Font("굴림", Font.PLAIN, 14));
		
		textArea = new JTextArea();
		textArea.setColumns(45);
		textArea.setRows(3);
		
		JButton btngetfood_1 = new JButton("회원 가입");
		btngetfood_1.setFont(new Font("굴림", Font.PLAIN, 14));
		pane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lbl_submit = new JLabel("회원가입");
		lbl_submit.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_submit.setFont(new Font("굴림", Font.BOLD, 17));
		pane.add(lbl_submit);
		
		JPanel panel_1 = new JPanel();
		pane.add(panel_1);
		
		JLabel lblref = new JLabel("냉장고 번호 : ");
		lblref.setFont(new Font("굴림", Font.PLAIN, 14));
		panel_1.add(lblref);
		
		txt_refcode = new JTextField();
		txt_refcode.setFont(new Font("굴림", Font.PLAIN, 14));
		txt_refcode.setColumns(10);
		panel_1.add(txt_refcode);
		pane.add(lbluserinfo);
		pane.add(textArea);
		
		JPanel panel = new JPanel();
		pane.add(panel);
		
		JLabel lblfood = new JLabel("식료품 정보 : ");
		lblfood.setFont(new Font("굴림", Font.PLAIN, 14));
		panel.add(lblfood);
		
		txt_fdfile = new JTextField();
		txt_fdfile.setEditable(false);
		txt_fdfile.setColumns(10);
		panel.add(txt_fdfile);
		
		JButton btngetfood = new JButton("파일 찾기");
		btngetfood.setFont(new Font("굴림", Font.PLAIN, 14));
		panel.add(btngetfood);
		pane.add(btngetfood_1);
		
		btngetfood.addActionListener(new btnSubmitEvent());
		btngetfood_1.addActionListener(new btnSubmitEvent());
	}
	
	class btnSubmitEvent implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton)e.getSource();
			JOptionPane j = new JOptionPane();
			if(source.getText().equals("파일 찾기")) {
				JFileChooser file = new JFileChooser();
				int result = file.showOpenDialog(frame);
				if(result == JFileChooser.APPROVE_OPTION) {
					selectedfdfile = file.getSelectedFile();
					filepath = selectedfdfile.toString();
					txt_fdfile.setText(filepath);
				}
			}
			if(source.getText().equals("회원 가입")) {
				String refcode = txt_refcode.getText();
				String[] userinfo = textArea.getText().split("#");
				if(RefMgr.getInstance().find(refcode)==null) {
					int result = RefMgr.getInstance().addRef(refcode,userinfo,filepath);
					if(result == 0) {
						j.showMessageDialog(frame, "회원가입 완료", "", JOptionPane.PLAIN_MESSAGE);
						frame.setVisible(false);
					}else {
						j.showMessageDialog(null, "등록 실패", "오류", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					j.showMessageDialog(frame, "냉장고 번호 중복", "오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
	}
	
}
