import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GUI {
	public static void main(String[] args) {
		DefaultTableModel model = new DefaultTableModel();
		JTable table = new JTable(model);


		Vector data = model.getDataVector();
		Vector row = (Vector) data.elementAt(1);

		int mColIndex = 0;
		List colData = new ArrayList(table.getRowCount());
		for (int i = 0; i < table.getRowCount(); i++) {
			row = (Vector) data.elementAt(i);
			colData.add(row.get(mColIndex));
		}

		// Append a new column with copied data
		model.addColumn("Col3", colData.toArray());

		JFrame f = new JFrame();
		f.setSize(300, 300);
		f.add(new JScrollPane(table));
		f.setVisible(true);
	}
}
