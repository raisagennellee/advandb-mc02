import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.*;

public class MainFrame extends JFrame {
	private JPanel topPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
    private JPanel bottomPanel;
    private JPanel topCheckBoxPanel;
    private JPanel bottomCheckBoxPanel;
	
	public MainFrame() {
		JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        getContentPane().add(mainPanel);
        
        createLeftPanel();
        createRightPanel();
        
//        JSplitPane splitPaneV = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
//        mainPanel.add(splitPaneV, BorderLayout.CENTER);
//        splitPaneV.add(leftPanel);
//        splitPaneV.add(rightPanel);
//        splitPaneV.setEnabled(false);
        
//        mainPanel.add(splitPaneV);
        
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        
        setBackground(Color.gray);
        setSize(800, 600);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	
	public void createLeftPanel() {
		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setPreferredSize(new Dimension(250, 600));
		leftPanel.setMinimumSize(new Dimension(250, 600));
        
        createTopPanel();
        createBottomPanel();
        
        leftPanel.add(topPanel, BorderLayout.NORTH);
        leftPanel.add(bottomPanel, BorderLayout.SOUTH);
	}
	
	public void createTopPanel() {
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.setPreferredSize(new Dimension(250, 280));
		topPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JLabel label = new JLabel("Roll-up and Drill Down", SwingConstants.CENTER);
		label.setFont(new Font("SansSerif", Font.BOLD, 15));
		
		topCheckBoxPanel = new JPanel();
		topCheckBoxPanel.setPreferredSize(new Dimension(250, 255));
		topCheckBoxPanel.setLayout(new BoxLayout(topCheckBoxPanel, BoxLayout.Y_AXIS));
		
		//JScrollPane scrollPane = new JScrollPane(topCheckBoxPanel);
		
		createTopCheckBoxElements();
		
		topPanel.add(label, BorderLayout.NORTH);
		//topPanel.add(scrollPane, BorderLayout.SOUTH);
		topPanel.add(topCheckBoxPanel, BorderLayout.SOUTH);
	}
	
	public void createBottomPanel() {
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.setPreferredSize(new Dimension(250, 295));
		bottomPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JLabel label = new JLabel("Slice and Dice", SwingConstants.CENTER);
		label.setFont(new Font("SansSerif", Font.BOLD, 15));
		
		bottomCheckBoxPanel = new JPanel();
		bottomCheckBoxPanel.setPreferredSize(new Dimension(250, 270));
		bottomCheckBoxPanel.setLayout(new BoxLayout(bottomCheckBoxPanel, BoxLayout.Y_AXIS));
		
		//JScrollPane scrollPane = new JScrollPane(bottomCheckBoxPanel);
		
		createBottomCheckBoxElements();
		
		bottomPanel.add(label, BorderLayout.NORTH);
		//bottomPanel.add(scrollPane, BorderLayout.SOUTH);
		bottomPanel.add(bottomCheckBoxPanel, BorderLayout.SOUTH);
	}
	
	public void createTopCheckBoxElements() {
		JCheckBox chkApple = new JCheckBox("Apple");
		JCheckBox chkMango = new JCheckBox("Mango");
	    JCheckBox chkPear = new JCheckBox("Pear");
	    
	    topCheckBoxPanel.add(chkApple);
	    topCheckBoxPanel.add(chkMango);
	    topCheckBoxPanel.add(chkPear);
	}
	
	public void createBottomCheckBoxElements() {
		JCheckBox chkApple = new JCheckBox("Apple");
		JCheckBox chkMango = new JCheckBox("Mango");
	    JCheckBox chkPear = new JCheckBox("Pear");
	    
	    bottomCheckBoxPanel.add(chkApple);
	    bottomCheckBoxPanel.add(chkMango);
	    bottomCheckBoxPanel.add(chkPear);
	}
	
	public void createRightPanel() {
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setPreferredSize(new Dimension(600, 600));
		rightPanel.setMinimumSize(new Dimension(550, 600));
		
		JLabel label = new JLabel("Result", SwingConstants.CENTER);
		label.setFont(new Font("SansSerif", Font.BOLD, 15));
		
		rightPanel.add(label, BorderLayout.NORTH);
		JPanel panelTemp = new JPanel();
		panelTemp.setPreferredSize(new Dimension(550, 280));
		rightPanel.add(panelTemp, BorderLayout.SOUTH);
	}
}
