import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class MainFrame extends JFrame {
	private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel topCheckBoxPanel;
    private JPanel bottomCheckBoxPanel;
	
	public MainFrame() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 650);
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setTitle("ADVANDB MCO2 - ");
		this.setLayout(new RelativeLayout(RelativeLayout.X_AXIS));
		
		this.add(createControlPanel(), new Float(1) );
		this.add(createRightPanel(), new Float(3));
		this.setVisible(true);
		this.test();
	}
	
	public void test(){
		ArrayList<String> columns = new ArrayList<String>();
		columns.add(ViewLabels.COLUMN_1);
		columns.add(ViewLabels.COLUMN_2);
		columns.add(ViewLabels.COLUMN_3);
		columns.add(ViewLabels.COLUMN_4);
		columns.add(ViewLabels.COLUMN_5);
		
		for (String text: columns){
			this.addTopChoices(text);
			this.addBottomChoices(text);
		}
	}
	
	public JPanel createControlPanel() {
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(2,0));
		leftPanel.setPreferredSize(new Dimension(250, 600));
		leftPanel.setMinimumSize(new Dimension(250, 600));
		
        createTopPanel();
        createBottomPanel();
        
        leftPanel.add(topPanel);
        leftPanel.add(bottomPanel);
        return leftPanel;
	}
	
	public void createTopPanel() {
		topPanel = new JPanel();
		Border border = BorderFactory.createTitledBorder("Roll Up & Drill Down");
		Border margin = BorderFactory.createEmptyBorder(10,10,10,10);
		topPanel.setBorder(new CompoundBorder(border, margin));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
	}
	
	public void createBottomPanel() {
		bottomPanel = new JPanel();
		Border border = BorderFactory.createTitledBorder("Slice & Dice");
		Border margin = BorderFactory.createEmptyBorder(10,10,10,10);
		bottomPanel.setBorder(new CompoundBorder(border, margin));
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
	}
	
	public void addTopChoices(String text) {
		topPanel.add(new JCheckBox(text));
		topPanel.revalidate();
		topPanel.repaint();
	}
	
	public void addBottomChoices(String text) {
		bottomPanel.add(new JCheckBox(text));
		bottomPanel.revalidate();
		bottomPanel.repaint();
	}
	
	public JPanel createRightPanel() {
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setBackground(Color.green);
		rightPanel.setSize(new Dimension(rightPanel.getSize().width, this.getSize().height));
		
		JLabel label = new JLabel("Result", SwingConstants.CENTER);
		label.setFont(new Font("SansSerif", Font.BOLD, 15));
		
		rightPanel.add(label, BorderLayout.NORTH);
		JPanel panelTemp = new JPanel();
		panelTemp.setPreferredSize(new Dimension(550, 280));
		rightPanel.add(panelTemp, BorderLayout.SOUTH);
		return rightPanel;
	}
}
