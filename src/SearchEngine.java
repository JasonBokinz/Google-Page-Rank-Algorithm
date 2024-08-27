/**
 * This class initializes a WebGraph from the appropriate text files and allow the user to search for keywords in the graph. 
 * @author jasonbokinz, ID: 112555537, R: 03
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class SearchEngine {
	/**
	 * @param PAGES_FILE
	 * file of pages
	 * @param LINKS_FILE
	 * file of links
	 * @param web
	 * web graph being built onto
	 */
	private static String PAGES_FILE = "pages.txt";
	private static String LINKS_FILE = "links.txt";
	private WebGraph web;
	/**
	 * THis method prints the menu
	 */
	public static void printMenu() {
		System.out.println("Menu:\n    " + "(AP) - Add a new page to the graph.\n    "
				+ "(RP) - Remove a page from the graph.\n    " + "(AL) - Add a link between pages in the graph.\n    "
				+ "(RL) - Remove a link between pages in the graph.\n    " + "(P)  - Print the graph.\n    "
				+ "(S)  - Search for pages with a keyword.\n    " + "(Q)  - Quit.");
	}
	/**
	 * This method takes inputs from the user and passes other class methods
	 * @param args
	 * array of strings
	 * @throws IllegalArgumentException
	 * thrown if something is inputed incorrectly
	 * @throws IOException
	 * thrown if IOException is found
	 */
	public static void main(String[] args) throws IllegalArgumentException, IOException {
		Scanner input = new Scanner(System.in);
		String selection = "";
		SearchEngine object = new SearchEngine();
		try {
			object.web = WebGraph.buildFromFiles(PAGES_FILE, LINKS_FILE);
			object.web.updatePageRanks();
			object.web.updateLinks();

			// Load WebGraph from file. PageRanks should be calculated and initialized.
			System.out.println("Loading WebGraph data...");
			System.out.println("Success!\n");
			while (!selection.equals("Q")) {
				printMenu();
				System.out.println("Please select an option: ");
				selection = input.nextLine().toUpperCase();
				switch (selection) {
				case "AP":
					System.out.println("Enter a URL: ");
					String url = input.nextLine();
					System.out.println("Enter keywords (space-separated): ");
					String keywordsStr = input.nextLine();
					String[] keywordsArray = keywordsStr.split(" ");
					ArrayList<String> keywords = new ArrayList<String>();
					for (int i = 0; i < keywordsArray.length; i++) {
						keywords.add(keywordsArray[i]);
					}
					object.web.addPage(url, keywords);
					object.web.updatePageRanks();
					object.web.updateLinks();
					System.out.println("\n" + url + " successuflly added to the WebGraph!\n");
					break;
				case "RP":
					System.out.println("Enter a URL: ");
					url = input.nextLine();
					object.web.removePage(url);
					object.web.updatePageRanks();
					object.web.updateLinks();
					System.out.println("\n" + url + " has been removed from the graph!\n");
					break;
				case "AL":
					System.out.println("Enter a source URL: ");
					String source = input.nextLine();
					System.out.println("Enter a destination URL: ");
					String destination = input.nextLine();
					object.web.addLink(source, destination);
					object.web.updatePageRanks();
					object.web.updateLinks();
					System.out.println("\nLink successfully added from " + source + " to " + destination + "!\n");
					break;
				case "RL":
					System.out.println("Enter a source URL: ");
					source = input.nextLine();
					System.out.println("Enter a destination URL: ");
					destination = input.nextLine();
					object.web.removeLink(source, destination);
					object.web.updatePageRanks();
					object.web.updateLinks();
					System.out.println("\nLink removed from " + source + " to " + destination + "!\n");
					break;
				case "P":
					System.out.println("    (I) Sort based on index (ASC)\n" + "    (U) Sort based on URL (ASC)\n"
							+ "    (R) Sort based on rank (DSC)\n" + "Please select an option: ");
					String printSelection = input.nextLine().toUpperCase();
					ArrayList<WebPage> temp = object.web.getPages();
					ArrayList<WebPage> sorted;
					switch (printSelection) {
					case "I":
						sorted = object.web.sort(object.web.getPages(), "index");
						object.web.printTable(sorted);
						break;
					case "U":
						sorted = object.web.sort(object.web.getPages(), "url");
						object.web.printTable(sorted);
						break;
					case "R":
						sorted = object.web.sort(object.web.getPages(), "rank");
						object.web.printTable(sorted);
						break;
					}
					object.web.setPages(temp);
					break;
				case "S":
					System.out.println("Search keyword: ");
					String keywordSearch = input.nextLine();
					ArrayList<WebPage> searched = object.web.search(keywordSearch);
					if (searched.size() != 0) {
						sorted = object.web.sort(searched, "rank");
						object.web.printSearch(sorted);
					}
					else {
						System.out.println("No search results found for the keyword " + keywordSearch +".");
					}
					
					break;
				case "Q":
					System.out.println("\nGoodbye.");
					break;
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		input.close();
	}
}
