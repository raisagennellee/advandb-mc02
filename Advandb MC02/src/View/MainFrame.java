package View;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableModel;

import Controller.Controller;

public class MainFrame extends JFrame {
	private JPanel topPanel;
    private JPanel bottomPanel;
    
    private JScrollPane pane;
    
    private ResultSet rs = null;
    private Controller c;
	
	public MainFrame(Controller c, ResultSet rs) {
		this.c = c;
		this.rs = rs;
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
		JCheckBox cb = new JCheckBox(text);
		cb.addItemListener(new checkBoxListener());
		topPanel.add(cb);
		topPanel.revalidate();
		topPanel.repaint();
	}
	
	public void addBottomChoices(String text) {
		JCheckBox cb = new JCheckBox(text);
		cb.addItemListener(new checkBoxListener());
		bottomPanel.add(cb);
		bottomPanel.revalidate();
		bottomPanel.repaint();
	}
	
	public JPanel createRightPanel() {
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setPreferredSize(new Dimension(rightPanel.getSize().width, (int) (this.getSize().height*0.9) ));
		rightPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		JLabel label = new JLabel("Result", SwingConstants.CENTER);
		label.setFont(new Font("SansSerif", Font.BOLD, 15));
		
		JPanel panelTemp = new JPanel();
		panelTemp.setLayout(new BorderLayout());
		panelTemp.setBorder(BorderFactory.createLineBorder(Color.gray));
		panelTemp.setBackground(Color.lightGray);
		
		//where to get rs
		pane = new JScrollPane(createJTable(rs));
		panelTemp.add(pane, BorderLayout.CENTER);
		
		rightPanel.add(label, BorderLayout.NORTH);
		rightPanel.add(panelTemp, BorderLayout.CENTER);
		return rightPanel;
	}
	
	public JTable createJTable(ResultSet rs) {
		JTable table = new JTable();
		DefaultTableModel dataModel = new DefaultTableModel();
		table.setModel(dataModel);
		
		try {
			ResultSetMetaData mdata = rs.getMetaData();
			int colCount = mdata.getColumnCount();		
			System.out.println(colCount);
			String[] colNames = getColumnNames(colCount, mdata);
			dataModel.setColumnIdentifiers(colNames);
			while (rs.next()) {
				String[] rowData = new String[colCount];
				for (int i = 1; i <= colCount; i++) {
					rowData[i - 1] = rs.getString(i);
				}
				dataModel.addRow(rowData);
			}
		} catch (SQLException e) {}
		
		return table;
	}
	
	public String[] getColumnNames(int colCount, ResultSetMetaData mdata) throws SQLException {
		String[] colNames = new String[colCount];
		for (int i = 1; i <= colCount; i++) {
			String col = mdata.getColumnName(i);
			colNames[i-1] = col;
		}
		return colNames;
	}
	
	private void updateRowHeights(JTable table) {
		try {
			for (int row = 0; row < table.getRowCount(); row++) {
				int rowHeight = table.getRowHeight();

				for (int column = 0; column < table.getColumnCount(); column++) {
					Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
					rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
				}

				table.setRowHeight(row, rowHeight);
			}
		} catch (ClassCastException e) {
		}
	}
	
	public void updateTable(ResultSet rs) {
		pane.removeAll();
		JTable table = createJTable(rs);
		pane.add(table);
		pane.revalidate();
		pane.repaint();
		updateRowHeights(table);
	}
	
	public class checkBoxListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			ArrayList<String> upperChoices = new ArrayList<String>();
			for( Component comp : topPanel.getComponents() ) {
			   if( comp instanceof JCheckBox){
				   if (((JCheckBox)comp).isSelected())
					   upperChoices.add( ((JCheckBox)comp).getText() );
			   }
			}
			ArrayList<String> lowerChoices = new ArrayList<String>();
			for( Component comp : bottomPanel.getComponents() ) {
			   if( comp instanceof JCheckBox){
				   if (((JCheckBox)comp).isSelected())
					   lowerChoices.add( ((JCheckBox)comp).getText() );
			   }
			}
			c.getResult(upperChoices, lowerChoices);
		}
	    
	}
}
