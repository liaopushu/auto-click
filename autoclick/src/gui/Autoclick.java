package gui;

import com.autoclick.main.ConfigHandler;
import com.autoclick.main.impl.ConfigHandlerImp;
import com.autoclick.utils.ConfigEntry;
import com.autoclick.utils.RobotClick;
import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class Autoclick{
	private static JFrame frame;
	private static JLabel textField;
	private static JTextField textField_1;
	private static JCheckBox rdbtnNewRadioButton;
	private static JCheckBox chckbxShift;
	private static JButton button;
	private static JSpinner spinner;
	private static final ConfigHandler ch = ConfigHandlerImp.getInstance();
	private static final AtomicBoolean isRunning = new AtomicBoolean(false);
	private static Thread robotClick;
	private static boolean isBindingHotKey;

	private static final boolean toggleStatus(boolean command) {
		isRunning.compareAndSet(isRunning.get(), command);
		return isRunning.get();
	}

	public static final AtomicBoolean getStatus() {
		return isRunning;
	}
	
	private static final void runThread(ConfigEntry ce) {
		robotClick = new Thread(new RobotClick(ce.getMouseButton(), ce.getDelay()));
		robotClick.start();
	}

	private static final void stopThread() {
		if (robotClick != null) {
			Thread.yield();
		}
		robotClick=null;
	}

	public static final void registerTheHotKey(ConfigEntry ce) {
		int shiftCode = 0;
		int ctrlCode = 0;
		if (ce.isShiftKey()) {
			shiftCode = 4;
		}
		if (ce.isControlKey()) {
			ctrlCode = 2;
		}
		char key = ce.getKey();
		key = Character.toUpperCase(key);
		JIntellitype.getInstance().registerHotKey(0, shiftCode + ctrlCode, key);
	}
	
	public static final ConfigEntry packConfigEntry() {
		ConfigEntry ce = new ConfigEntry();
		boolean isShift = chckbxShift.isSelected();
		boolean isCtrl = rdbtnNewRadioButton.isSelected();
		int delayValue = (Integer) spinner.getValue();
		String letterString = textField_1.getText();
		char letterChar = letterString.charAt(0);
		ce.setControlKey(isCtrl);
		ce.setShiftKey(isShift);
		ce.setDelay(delayValue);
		ce.setKey(letterChar);
		return ce;
	}
	
	public static final void lockAllInput() {
		chckbxShift.setEnabled(false);
		rdbtnNewRadioButton.setEnabled(false);
		textField_1.setEnabled(false);
		spinner.setEnabled(false);
	}

	public static final void unlockAllInput() {
		chckbxShift.setEnabled(true);
		rdbtnNewRadioButton.setEnabled(true);
		textField_1.setEnabled(true);
		spinner.setEnabled(true);
	}
	
	public final static void setButtonText(String text) {
		button.setText(text);
	}
	
	public static final boolean checkInput(JTextField letter, JSpinner delayTime) {
		return (Integer) delayTime.getValue() > 0 && letter.getText() != null && letter.getText().length() == 1;
	}
	
	public static final void initKeyListener(ConfigEntry ce) {
	      if (ce != null) {
	         registerTheHotKey(ce);
	         if (!isBindingHotKey) {
	            JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
	            	public void onHotKey(int markCode) {
	            		switch(markCode) {
	            	      case 0:
	            	         if (isRunning.get()) {
	            	        	toggleStatus(false);
	            	        	stopThread();
	            	            unlockAllInput();
	            	            setButtonText("运行");
	            	         } else {
	            	            lockAllInput();
	            	            setButtonText("停止");
	            	            toggleStatus(true);
	            	            runThread(ce);
	            	         }
	            	      }
					}
	            });
	            }
	         }
	         isBindingHotKey = true;
	      }
	
	public static final void updateTheHotKey(ConfigEntry ce) {
		JIntellitype.getInstance().unregisterHotKey(0);
		initKeyListener(ce);
	}
	
	public static final void cleanHotKeyInstance() {
		JIntellitype.getInstance().cleanUp();
	}

	public final static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
   }

	private static final void initialize() {
      frame = new JFrame();
      frame.setBounds(100, 100, 450, 300);
      frame.setDefaultCloseOperation(3);
      frame.getContentPane().setLayout((LayoutManager)null);
      textField = new JLabel();
      textField.setBounds(35, 22, 70, 16);
      frame.getContentPane().add(textField);
      textField.setFont(new Font("????", 0, 14));
      textField.setText("快捷键设置");
      rdbtnNewRadioButton = new JCheckBox("ctrl");
      rdbtnNewRadioButton.setBounds(35, 52, 121, 23);
      frame.getContentPane().add(rdbtnNewRadioButton);
      chckbxShift = new JCheckBox("shift");
      chckbxShift.setBounds(35, 80, 103, 23);
      frame.getContentPane().add(chckbxShift);
      textField_1 = new JTextField();
      textField_1.setToolTipText("hot key");
      textField_1.setBounds(63, 116, 66, 21);
      frame.getContentPane().add(textField_1);
      textField_1.setColumns(10);
      JLabel label = new JLabel("字母");
      label.setBounds(35, 119, 30, 15);
      frame.getContentPane().add(label);
      JLabel label_1 = new JLabel("点击延迟设置");
      label_1.setBounds(288, 23, 87, 15);
      frame.getContentPane().add(label_1);
      button = new JButton("运行");
      button.setBackground(SystemColor.control);
      button.setBounds(162, 175, 113, 39);
      frame.getContentPane().add(button);
      spinner = new JSpinner();
      spinner.setBounds(264, 53, 76, 22);
      frame.getContentPane().add(spinner);
      JLabel label_2 = new JLabel("毫秒");
      label_2.setBounds(350, 56, 54, 15);
      frame.getContentPane().add(label_2);
      JLabel lblkimball = new JLabel("作者:Kimball  微信：lps51458   QQ:416517019");
      lblkimball.setBounds(91, 236, 284, 15);
      frame.getContentPane().add(lblkimball);
      JSeparator separator = new JSeparator();
      separator.setBounds(10, 224, 414, 2);
      frame.getContentPane().add(separator);
      ConfigEntry orginConfig = ch.getConfigFromFile();
      chckbxShift.setSelected(orginConfig.isShiftKey());
      rdbtnNewRadioButton.setSelected(orginConfig.isControlKey());
      spinner.setValue(orginConfig.getDelay());
      textField_1.setText(String.valueOf(orginConfig.getKey()));
      button.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			 if (isRunning.get()) {
		         toggleStatus(false);
		         stopThread();
		         unlockAllInput();
		         setButtonText("运行");
		      } else if (checkInput(textField_1,spinner)) {
		         ConfigEntry ce = packConfigEntry();
		         if (ch.applyConfig(ce)) {
		            lockAllInput();
		            setButtonText("停止");
		            updateTheHotKey(ce);
		            toggleStatus(true);
		            runThread(ce);
		         }
		      } else {
		         JOptionPane.showMessageDialog(frame,"输入错误", "错误", 0);
		      }
		}
	});
   }
}