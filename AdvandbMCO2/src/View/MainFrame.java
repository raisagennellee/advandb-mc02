package View;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
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
import java.util.Collections;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableModel;

import Controller.Controller;
import Model.Column;
import Model.ComboBoxConstants;

public class MainFrame extends JFrame {
	private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel panelTemp;
    private JButton btnAdd;
    private JButton btnSearch;
    
    private JScrollPane pane;
    
    private ResultSet rs = null;
    private Controller c;
	
	public MainFrame(Controller c, ResultSet rs) {
		this.c = c;
		this.rs = rs;
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 650);
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setTitle("ADVANDB MCO2 - ");
		this.setLayout(new RelativeLayout(RelativeLayout.X_AXIS));
		
		this.add(createControlPanel(), new Float(2) );
		this.add(createRightPanel(), new Float(3));
		this.setVisible(true);
		this.setCheckBoxOptions();
	}
	
	public void setCheckBoxOptions(){
		ArrayList<Column> columns = ComboBoxConstants.OPTIONS_QUERY;
		for (Column col: columns){
			this.addTopChoices(col.getName());
		}
	}
	
	public JPanel createControlPanel() {
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(2,0));
		leftPanel.setPreferredSize(new Dimension(250, 600));
		leftPanel.setMinimumSize(new Dimension(250, 600));
		
        createTopPanel();
        
        leftPanel.add(topPanel);
        leftPanel.add(createBottomPanel());
        return leftPanel;
	}
	
	public void createTopPanel() {
		topPanel = new JPanel();
		Border border = BorderFactory.createTitledBorder("Roll Up & Drill Down");
		Border margin = BorderFactory.createEmptyBorder(10,10,10,10);
		topPanel.setBorder(new CompoundBorder(border, margin));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
	}
	
	public JPanel createBottomPanel() {
		JPanel panel = new JPanel();
		Border border = BorderFactory.createTitledBorder("Slice & Dice");
		Border margin = BorderFactory.createEmptyBorder(10,10,10,10);
		panel.setBorder(new CompoundBorder(border, margin));
		panel.setLayout(new BorderLayout());
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		panel.add(bottomPanel, BorderLayout.CENTER);
		
		JPanel addBtnContainer = new JPanel();
		addBtnContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnAdd = new JButton("+");
		btnAdd.addActionListener(new filteringListener());
		addBtnContainer.add(btnAdd);
		panel.add(addBtnContainer, BorderLayout.NORTH);
		
		JPanel searchBtnContainer = new JPanel();
		searchBtnContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnSearch = new JButton("SEARCH");
		btnSearch.addActionListener(new filteringListener());
		searchBtnContainer.add(btnSearch);
		panel.add(searchBtnContainer, BorderLayout.SOUTH);
		
		return panel;
	}
	
	public void addTopChoices(String text) {
		JCheckBox cb = new JCheckBox(text);
		cb.addItemListener(new checkBoxListener());
		topPanel.add(cb);
		topPanel.revalidate();
		topPanel.repaint();
	}
	
	private void addFilteringOption(){
		ArrayList<Column> columns = ComboBoxConstants.OPTIONS_QUERY;
	    JPanel filterOption = new JPanel();
	    filterOption.setLayout(new RelativeLayout(RelativeLayout.X_AXIS));
	    if (bottomPanel.getComponents().length != 0){
	    	JComboBox opList = new JComboBox(new String[] {"AND" , "OR"});
		    opList.setSelectedIndex(0);
		    filterOption.add(opList, new Float(1));
	    }
	    
	    ArrayList<String> cols = new ArrayList<String>();
	    for (Column c: columns){
	    	cols.add(c.getName());
	    }
	    JComboBox colList = new JComboBox(cols.toArray());
	    colList.setSelectedIndex(0);
	    filterOption.add(colList, new Float(2));
	    
	    JComboBox funcList = new JComboBox(getFunctions());
	    colList.setSelectedIndex(0);
	    filterOption.add(funcList, new Float(2));
	    
	    JTextField text = new JTextField(10);
	    filterOption.add(text, new Float(2));
	    
	    JButton btnRemove = new JButton("-");
	    btnRemove.addActionListener(new filteringListener());
	    filterOption.add(btnRemove, new Float (1));
	    
	    bottomPanel.add(filterOption);
	    bottomPanel.revalidate();
	    bottomPanel.repaint();
	}
	
	private String[] getFunctions(){
		ArrayList<String> funcList = new ArrayList<String>();
		funcList.add("=");
		funcList.add(">");
		funcList.add("<");
		funcList.add(">=");
		funcList.add("<=");
		funcList.add("NOT EQUAL");
		return funcList.stream().toArray(String[]::new);
	}
	
	public class filteringListener implements ActionListener{
	    @Override
		public void actionPerformed(ActionEvent e) {
	    	JButton button = (JButton) e.getSource();
	    	if (button == btnAdd){
				addFilteringOption();
	    	}
	    	else if (button == btnSearch){
	    		runQuery();
	    	}
	    	else {
	    		JPanel panel = (JPanel)button.getParent();
	    		removeFilteringOption(panel);
	    	}
		}
	}
	
	private void removeFilteringOption(JPanel panel){
		if (bottomPanel.getComponentZOrder(panel) == 0 && bottomPanel.getComponentCount() > 1){
			((JPanel)bottomPanel.getComponent(1)).remove(((JPanel)bottomPanel.getComponent(1)).getComponent(0));
		}
		bottomPanel.remove(panel);
		bottomPanel.revalidate();
		bottomPanel.repaint();
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
		
		panelTemp = new JPanel();
		panelTemp.setLayout(new BorderLayout());
		panelTemp.setBorder(BorderFactory.createLineBorder(Color.gray));
		panelTemp.setBackground(Color.lightGray);
		
		//where to get rs
		JScrollPane pane = new JScrollPane(createJTable(rs));
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
		panelTemp.removeAll();
		JTable table = createJTable(rs);
		JScrollPane pane = new JScrollPane(table);
		updateRowHeights(table);
		panelTemp.add(pane, BorderLayout.CENTER);
		panelTemp.revalidate();
		panelTemp.repaint();
	}
	
	public class checkBoxListener implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent arg0) {
			runQuery();
		}
	}
	
	public void runQuery(){
		ArrayList<String> upperChoices = new ArrayList<String>();
		for( Component comp : topPanel.getComponents() ) {
		   if( comp instanceof JCheckBox){
			   if (((JCheckBox)comp).isSelected())
				   upperChoices.add( ((JCheckBox)comp).getText() );
		   }
		}
		ArrayList<String> lowerChoices = new ArrayList<String>();
		for( Component comp : bottomPanel.getComponents() ) {
			String condition = "";
			for ( Component c : ((Container) comp).getComponents()){
				String text = "";
				if( c instanceof JComboBox){
					text = (String)((JComboBox)c).getSelectedItem();
				   	if (!(text.equals("AND") || text.equals("OR"))){
				   		try{
				   			text = ComboBoxConstants.findColumn(text).getColName();
				   		}
				   		catch (NullPointerException e){}
				   	}
				}
				else if (c instanceof JTextField){
					text = "'" + (String) ((JTextField)c).getText() + "'";
				}
				condition += text + " ";
			}
			if (!(condition.contains("AND") || condition.contains("OR"))){
				condition = "AND " + condition;
			}
			lowerChoices.add(condition);
		}
		this.c.getResult(upperChoices, lowerChoices);
	}
}
