package com.android.img.crop.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.android.img.crop.listener.GenerateListener;
import com.android.img.crop.model.ConfigModel;
import com.android.img.crop.utils.ConfigUtils;
import com.android.img.crop.utils.ConsoleUtils;
import com.android.img.crop.utils.FileUtils;
import com.android.img.crop.utils.GenerateBuilder;
import com.android.img.crop.utils.ImageUtils;

public class ImageCropMainFrame extends JFrame implements GenerateListener {
	public JPanel jPanel;
	public JLabel jLabel, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6;
	public JButton jButton, jButton2, jButton3, jButton4;
	public JTextField jField, jField2, jField3, jField4;
	public JComboBox jcb1;
	public JPanel panel_category_select;
	public int index = 0;
	public List<JCheckBox> checkBoxs = new ArrayList<JCheckBox>();
	// public String all[] = { "ldpi", "mdpi" , "hdpi","xhdpi", "xxhdpi"};
	public List<ConfigModel> models;
	public String iconName;

	public static void main(String[] args) {
		ImageCropMainFrame thisClass = new ImageCropMainFrame();
		thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		thisClass.setVisible(true);
	}

	private void loadConfig() {
		//InputStream is=this.getClass().getResourceAsStream("/resource/res.txt");
		try {
			models = ConfigUtils.getConfigModels(this.getClass().getResourceAsStream("/xml/default_config.xml"));
			iconName = ConfigUtils.getIconName(this.getClass().getResourceAsStream("/xml/default_config.xml"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ImageCropMainFrame() {
		super();
		this.setTitle("Android图片裁剪");
		loadConfig();
		init();
		initListener();
		// setUndecorated(true);
		// setIconImage(Toolkit.getDefaultToolkit().createImage(LoginFrame.class.getResource("/png/zhuye.png")));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = screenSize.width / 2;
		int centerY = screenSize.height / 2;
		int w = 600;
		int h = 362;
		setBounds(centerX - w / 2, centerY - h / 2, w, h);
		setVisible(true);
		this.setResizable(false);
	}

	void init() {
		setLayout(null);
		jPanel = new JPanel();
		jPanel.setLayout(null);
		jPanel.setBounds(new Rectangle(0, 0, 600, 362));
		jLabel = new JLabel();
		jLabel.setBounds(0, 0, 600, 362);
		ImageIcon img = new ImageIcon(this.getClass().getResource(
				"/png/bg_android_image_crop.png"));
		jLabel.setIcon(img);
		jLabel2 = new JLabel();
		ImageIcon jLabel2_img = new ImageIcon(this.getClass().getResource(
				"/png/img_android_logo.png"));
		jLabel2.setBounds(0, 40, 208, 281);
		jLabel2.setIcon(jLabel2_img);
		jButton = new JButton("选择");
		jButton.setBounds(490, 30, 80, 30);
		jField = new JTextField();
		jField.setBounds(220, 30, 230, 30);
		jcb1 = new JComboBox<>(new DefaultComboBoxModel());
		jcb1.setBounds(220, 70, 120, 30);
		jLabel3 = new JLabel("宽度：");
		jLabel3.setForeground(Color.white);
		jLabel3.setBounds(350, 70, 40, 30);
		jField2 = new JTextField();
		jField2.setBounds(390, 70, 70, 30);
		jLabel4 = new JLabel("高度：");
		jLabel4.setForeground(Color.white);
		jLabel4.setBounds(470, 70, 40, 30);
		jField3 = new JTextField();
		jField3.setBounds(510, 70, 70, 30);
		jLabel5 = new JLabel("生成尺寸：");
		jLabel5.setForeground(Color.white);
		jLabel5.setBounds(220, 120, 70, 30);
		jField4 = new JTextField();
		jField4.setBounds(295, 110, 120, 30);
		panel_category_select = new JPanel();
		panel_category_select.setLayout(new GridLayout(1, models.size()));
		panel_category_select.setBounds(220, 150, 360, 30);
		jLabel6 = new JLabel("注：如果选择了源尺寸将会覆盖源文件");
		jLabel6.setForeground(Color.red);
		jLabel6.setFont(new Font("Dialog", 1, 12));
		jLabel6.setBounds(220, 200, 240, 30);
		jButton2 = new JButton("修改配置文件");
		jButton2.setBounds(220, 245, 120, 30);
		jButton3 = new JButton("马上生成");
		jButton3.setForeground(Color.red);
		jButton3.setBounds(370, 245, 120, 30);
		jButton4 = new JButton("恢复默认设置");
		jButton4.setBounds(220, 285, 120, 30);
		jPanel.add(jButton4);
		jPanel.add(jButton3);
		jPanel.add(jButton2);
		jPanel.add(jLabel6);
		jPanel.add(panel_category_select);
		jPanel.add(jField4);
		jPanel.add(jLabel5);
		jPanel.add(jField3);
		jPanel.add(jLabel4);
		jPanel.add(jField2);
		jPanel.add(jLabel3);
		jPanel.add(jcb1);
		jPanel.add(jField);
		jPanel.add(jButton);
		jPanel.add(jLabel2);
		jPanel.add(jLabel);
		add(jPanel);
		boolean select = false;
		for (ConfigModel model : models) {
			JCheckBox checkBox = new JCheckBox(model.getName());
			checkBoxs.add(checkBox);
			if (!select) {
				checkBox.setSelected(true);
				select = false;
			}
			panel_category_select.add(checkBox);
		}
		if (models != null) {
			for (int i = 0; i < models.size(); i++) {
				jcb1.addItem(models.get(i).getName());
				if(i==0)
				{
					jField2.setText(models.get(i).getWidth()+"");
					jField3.setText(models.get(i).getHeight()+"");
					checkBoxs.get(0).setSelected(false);
				}
			}
		}
			jField4.setText(iconName);
	}

	private void initListener() {
		jcb1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					int index = jcb1.getSelectedIndex();
					ImageCropMainFrame.this.index = index;
					String content = jcb1.getSelectedItem().toString();
					System.out.println("index222=" + index + ", content="
							+ content);
					jField2.setText(models.get(index).getWidth() + "");
					jField3.setText(models.get(index).getHeight() + "");
					jField2.setEditable(false);
					jField3.setEditable(false);
					checkBoxs.get(index).setSelected(false);
				}
			}
		});
		jButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if (file != null) {
					jField.setText(file.getAbsolutePath());
				}
			}
		});
		jButton3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int count = models.size();
				List<ConfigModel> useable = new ArrayList<ConfigModel>();
				for (int i = 0; i < count; i++) {
					JCheckBox checkBox = checkBoxs.get(i);
					if (checkBox.isSelected()) {
						useable.add(models.get(i));
					}
				}
				if (useable.size() > 0) {
					String path = jField.getText();
					ConfigModel currentModel = models.get(index);
					String iconName = jField4.getText();
					GenerateBuilder builder = new GenerateBuilder();
					builder.setCurrentMode(currentModel)
							.setGenerateModels(useable).setRootPath(path)
							.setIconName(iconName)
							.setGenerateListener(ImageCropMainFrame.this)
							.generate();
				}
			}
		});
	}

	@Override
	public void generateSuccess() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "生成成功!",  "提示",JOptionPane.DEFAULT_OPTION); 
	}

	@Override
	public void generateFail(String message) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, message,  "失败",JOptionPane.ERROR_MESSAGE); 
	}

	@Override
	public void generateStart() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "正在生成中，请耐心等待,点击确认继续!",  "提示",JOptionPane.DEFAULT_OPTION); 
	}

	@Override
	public void generating(String filename) {
		// TODO Auto-generated method stub
		
	}
}
