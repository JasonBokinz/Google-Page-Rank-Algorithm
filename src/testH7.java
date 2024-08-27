import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class testH7 {
	private static String PAGES_FILE = "pages.txt";
	private static String LINKS_FILE = "links.txt";
	public static void main(String[] args) throws IllegalArgumentException, FileNotFoundException {
		WebGraph graph = WebGraph.buildFromFiles(PAGES_FILE, LINKS_FILE);
		ArrayList<WebPage> pages = graph.getPages();
		int s = graph.getPages().size();
		Object[][] array = new Object [5][s];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (j == 0) {
					array[i][j] = pages.get(i).getIndex();
				}
				else if (j == 1) {
					array[i][j] = pages.get(i).getUrl();
				}
				else if (j == 2){
					array[i][j] = pages.get(i).getRank();
				}
				else if (j == 3) {
					array[i][j] = pages.get(i).getLinks();
				}
				else {
					array[i][j] = pages.get(i).getKeywords().toString().substring(1, pages.get(i).getKeywords().toString().length()-1);
				}
 //				System.out.print(array[i][j]);
			}
//			System.out.println();
		}
		
		JFrame frame = new JFrame();
		frame.setTitle("Click Header to  Sort: ");
		String [] columns = {"URL", "Index", "PageRank", "Links", "Keywords"};
		JTable table = new JTable(array, columns);
		table.setBounds(30,40,200,300);
		JScrollPane scrollPane = new JScrollPane(table);
		frame.add(scrollPane);
		frame.setSize(500,200);
		frame.setVisible(true);
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(new DefaultTableModel());
		table.setRowSorter(sorter);
		table.setAutoCreateRowSorter(true);
	}	
}
