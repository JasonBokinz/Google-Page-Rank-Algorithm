/**
 * This class organizes the WebPage objects as a directed graph.
 * @author jasonbokinz, ID: 112555537, R: 03
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
public class WebGraph {
	/**
	 * @param MAX_PAGES
	 * max numbers of pages
	 * @param pages
	 * array list of web pages
	 * @param edges
	 * 2D array list of edges between pages
	 */
	private final int MAX_PAGES = 40;
	private ArrayList<WebPage> pages;
	private ArrayList<ArrayList<Integer>> edges;
	/**
	 * This method is used to access the pages array list
	 * @return
	 * array list of web pages
	 */
	public ArrayList<WebPage> getPages() {
		return pages;
	}
	/**
	 * This method is used to set a new array list pages
	 * @param pages
	 * new array list of pages
	 */
	public void setPages(ArrayList<WebPage> pages) {
		this.pages = pages;
	}
	/**
	 * This is a constructor that creates a web graph object
	 * @param pages
	 * constructs the pages array list from the pages file
	 * @param edges
	 * constructs the edges 2D array list from the pages file
	 */
	public WebGraph(ArrayList<WebPage> pages, int[][] edges) {
		this.pages = new ArrayList<WebPage>();
		for (int i = 0; i < pages.size(); i++) {
			this.pages.add(pages.get(i));
		}
		this.edges = new ArrayList<ArrayList<Integer>>(MAX_PAGES);
		for (int i = 0; i < edges.length; i++) {
			for (int j = 0; j < edges[i].length; j++) {
				this.edges.add(new ArrayList<Integer>(MAX_PAGES));
				this.edges.get(i).add(edges[i][j]);
			}
		}
	}
	/**
	 * This method adds a new page to the pages array list
	 * @param url
	 * url of the new page being added
	 * @param keywords
	 * keywords of the new page being added
	 * @throws IllegalArgumentException
	 * thrown if the url already exists in pages
	 */
	public void addPage(String url, ArrayList<String> keywords) throws IllegalArgumentException {
		int index = pages.size();
		WebPage newPage = new WebPage(index, url, keywords);
		for (int i = 0; i < pages.size(); i++) {
			if (pages.get(i).getUrl().equals(url)) {
				throw new IllegalArgumentException("Error: " + url + " already exists in the WebGraph. Could not add new WebPage.");
			}
		}
		pages.add(newPage);
	}
	/**
	 * This methods adds a new link to edges
	 * @param source
	 * source url of the link
	 * @param destination
	 * destination url of the link
	 * @throws IllegalArgumentException
	 * thrown if the source link cannot be found
	 */
	public void addLink(String source, String destination) throws IllegalArgumentException {
		int srcIndex = -1;
		int destIndex = -1;
		for (int i = 0; i < pages.size(); i++) {
    		if (pages.get(i).getUrl().equals(source)) {
    			srcIndex = pages.get(i).getIndex();
    		}
    		
    		if (pages.get(i).getUrl().equals(destination)) {
    			destIndex = pages.get(i).getIndex();
    		}
    	}
    		if (srcIndex == -1) {
    			throw new IllegalArgumentException("Error: " + source + " could not be found in the WebGraph.");
    		}
    		else if (edges.get(srcIndex).get(destIndex) == 1) {
    			throw new IllegalArgumentException("Error: link was already established");
    		}
    		else {
    			edges.get(srcIndex).set(destIndex, 1);
    		}
	}
	/**
	 * This method removes a page from pages and updates the indices of the pages
	 * @param url
	 * url of the page you want to remove
	 */
	public void removePage(String url) {
		int removedIndex = 0;
		for (int i = 0; i < pages.size(); i++) {
			if (pages.get(i).getUrl().equals(url)) {
				removedIndex = i;
			}
		}
		for (int k = 0; k < pages.size(); k++) {
			for (int j = 0; j < pages.size(); j++) {
				if (j == removedIndex) {
					edges.get(k).remove(j);
				}
			}
		}
		edges.remove(removedIndex);
		for (int i = removedIndex; i < pages.size(); i++) {
			int currentIndex = pages.get(i).getIndex();
			pages.get(i).setIndex(currentIndex - 1);
		}
		pages.remove(removedIndex);
		
	}
	/**
	 * This methods removes a link to edges
	 * @param source
	 * source url of the link
	 * @param destination
	 * destination url of the link
	 */
	public void removeLink(String source, String destination) {
		int srcIndex = -1, destIndex = -1;
		for (int i = 0; i < pages.size(); i++) {
			if (pages.get(i).getUrl().equals(source)) {
				srcIndex = i;
			}
			if (pages.get(i).getUrl().equals(destination)) {
				destIndex = i;
			}
		}
		edges.get(srcIndex).set(destIndex, 0);
	}
	/**
	 * This method is used to update the page ranks
	 */
	public void updatePageRanks() {
		for (int i = 0; i < pages.size(); i++) {
			int colTotal = 0;
			for (int j = 0; j < pages.size(); j++) {
				colTotal += edges.get(j).get(i);
			}
			pages.get(i).setRank(colTotal);
		}
	}
	/**
	 * This method is used to print the web graph neatly
	 * @param pages
	 * array list of pages being printed
	 */
	public void printTable(ArrayList<WebPage> pages) {
		System.out.println(String.format("\n%s%8s%23s%7s%23s", "Index", "URL", "PageRank", "Links", "Keywords"));
		System.out.println("--------------------------------------------------------------------------------------");
		for (int i = 0; i < this.pages.size(); i++) {
			System.out.println(pages.get(i));
		}
		
	}
	/**
	 * This method updates the links for each page
	 */
	public void updateLinks() {
		for (int i = 0; i < pages.size(); i++) {
			String links = "";
			for (int j = 0; j < pages.size(); j++) {
				if (j != i) {
					if (edges.get(i).get(j) == 1) {
						links += j + ", ";
					}
				}
			}
			if (links.contains(",")) {
				links = links.substring(0, links.lastIndexOf(",")).trim();
			}
			pages.get(i).setLinks(links);
		}
	}
	/**
	 * This method creates a web graph from the given pages file and links file
	 * @param pagesFile
	 * file to create pages array
	 * @param linksFile
	 * file to create edges 2D array
	 * @return
	 * newly consstructed web graph
	 * @throws IllegalArgumentException
	 * thrown if a file has an incorrect format
	 * @throws FileNotFoundException
	 * thrown if the file is not found
	 */
	public static WebGraph buildFromFiles(String pagesFile, String linksFile) throws IllegalArgumentException, FileNotFoundException {
		int index = 0;
		Scanner scanner = new Scanner(new File(pagesFile));
		ArrayList<WebPage> pages = new ArrayList<WebPage>();
		//Iterator through pages
	    while (scanner.hasNext()) {
	    	String next = scanner.nextLine();
	    	if (next.equals(null)) {
	    		throw new IllegalArgumentException("Error: invalid text file");
	    	}
			ArrayList<String> keywords = new ArrayList<String>();
	    	String[] lineSplit = next.trim().split(" ");
	    	String url = lineSplit[0];
	    	for (int i = 1; i < lineSplit.length; i++) {
	    		keywords.add(lineSplit[i]);
	    	}
	    	for (int k = 0; k < pages.size(); k++) {
	    		if (pages.get(k).getUrl().equals(url)) {
	    			throw new IllegalArgumentException("Error: page already exists");
	    		}
	    	}
	    	if (pages.size() == 40) {
	    		throw new IllegalArgumentException("Error: max page size reached");
	    	}
	    	WebPage newPage = new WebPage(index++, url, keywords);
	    	pages.add(newPage);
	    }
	    int[][] edges = new int[40][40];
	    scanner = new Scanner(new File(linksFile));
	  //Iterator through links
	    while (scanner.hasNext()) {
	    	String next = scanner.nextLine();
	    	if (next.equals(null)) {
	    		throw new IllegalArgumentException("Error: invalid text file");
	    	}
	    	int srcIndex = -1;
	    	String[] lineSplit = next.trim().split(" ");
	    	if (lineSplit.length != 2) {
	    		throw new IllegalArgumentException("Error: invalid text file");
	    	}
	    	String source = lineSplit[0];
	    	//Find source index
	    	for (int i = 0; i < pages.size(); i++) {
	    		if (pages.get(i).getUrl().equals(source)) {
	    			srcIndex = pages.get(i).getIndex();
	    		}
	    	}
	    	int destIndex = -1;
	    	//For each destination
	    		for (int j = 0; j < pages.size(); j++) {
	    			WebPage eachPage = pages.get(j);
	    			if (lineSplit[1].equals(eachPage.getUrl())) {
	    				destIndex = eachPage.getIndex();
	    			}
	    		}
	    		if (srcIndex != -1 && destIndex != -1)
	    			edges[srcIndex][destIndex] = 1;
	    		else {
	    			throw new IllegalArgumentException("Error: URL not found");
	    		}
	    }
	    WebGraph graph = new WebGraph(pages, edges);
		return graph;
	}
	/**
	 * This method sorts an array list based on the comparator needed
	 * @param pages
	 * array list of pages
	 * @param type
	 * type of comparator needed
	 * @return
	 * newly sorted array list
	 */
	public ArrayList<WebPage> sort(ArrayList<WebPage> pages, String type) {
		int i, j;
		WebPage item;
			switch (type) {
			case "index":
				for (i = 1; i < pages.size(); i++) {
					item = pages.get(i);  
					j = i;
					while (j > 0 &&  new IndexComparator().compare(pages.get(j-1), item) == -1) {
						pages.set(j, pages.get(j-1));
						j--;
					}
					pages.set(j, item);
				}
				break;
				
			case "rank":
				for (i = 1; i < pages.size(); i++) {
					item = pages.get(i);  
					j = i;
					while (j > 0 && new RankComparator().compare(pages.get(j-1), item) == -1) {
						pages.set(j, pages.get(j-1));
						j--;
					}
					pages.set(j, item);
				}
				break;
			case "url":
				for (i = 1; i < pages.size(); i++) {
					item = pages.get(i);  
					j = i;
					while (j > 0 && new URLComparator().compare(pages.get(j-1), item) > 0) {
						pages.set(j, pages.get(j-1));
						j--;
					}
					pages.set(j, item);
				}
				break;
		}
			return pages;
	}
	/**
	 * This method is used to create an array list of pages with a certain keyword
	 * @param keywordSearch
	 * keyword in search of
	 * @return
	 * array list of pages that contains the keyword
	 */
	public ArrayList<WebPage> search(String keywordSearch) {
		ArrayList<WebPage> containsKeyword = new ArrayList<WebPage>();
		for (int i = 0; i < pages.size(); i++) {
			if (pages.get(i).getKeywords().contains(keywordSearch)) {
				containsKeyword.add(pages.get(i));
			}
		}
		return containsKeyword;
		
	}
	/**
	 * This method is used to print out the info from the search keyword
	 * @param search
	 * array list of pages that contains the keyword
	 */
	public void printSearch(ArrayList<WebPage> search) {
		System.out.println(String.format("%s%11s%7s", "Rank", "PageRank", "URL"));
		System.out.println("---------------------------------------------");
		for (int i = 0; i < search.size(); i++) {
			System.out.println(String.format("%3d  |%5d     | %s", i+1 , search.get(i).getRank(), search.get(i).getUrl()));
		}
	}
}
