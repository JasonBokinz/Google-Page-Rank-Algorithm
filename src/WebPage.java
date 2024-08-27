/**
 * This class represents a hyperlink document.
 * @author jasonbokinz, ID: 112555537, R: 03
 */
import java.util.ArrayList;
public class WebPage {
	/**
	 * @param index
	 * position in the adjacency matrix
	 * @param rank
	 * order of web pages based on relevance
	 * @param url
	 * url of the web page
	 * @param keywords
	 * keywords describing this web page
	 * @param links
	 * string of the pages links
	 */
	private int index, rank;
	private String url, links;
	private ArrayList<String> keywords;
	/**
	 * This method creates a default construct of a WebPage object.
	 */
	public WebPage() {
		index = 0;
		rank = 0;
		url = null;
		keywords = new ArrayList<String>();
	}
	/**
	 * This method creates a construct of a WebPage object with the given parameters.
	 */
	public WebPage(int index, String url, ArrayList<String> keywords) {
		this.index = index;
//		this.rank = rank;
		this.rank = 0;
		this.url = url;
		this.keywords = keywords;
		this.links = "***";
		
	}
	/**
	 * This method is used to access the index of the web page
	 * @return
	 * index of the web page
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * This method is used to set the index of the web page
	 * @param index
	 * new index of the web page
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * This method is used to access the rank of the web page
	 * @return
	 * rank of the web page
	 */
	public int getRank() {
		return rank;
	}
	/**
	 * This method is used to set the rank of the web page
	 * @param rank
	 * new rank of the web page
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}
	/**
	 * This method is used to access the url of the web page
	 * @return
	 * url of the web page
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * This method is used to set the url of the web page
	 * @param url
	 * new url of the web page
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * This method is used to access the keywords of the web page
	 * @return
	 * keywords of the web page
	 */
	public ArrayList<String> getKeywords() {
		return keywords;
	}
	/**
	 * This method is used to set the keywords of the web page
	 * @param keywords
	 * new keywords of the web page
	 */
	public void setKeywords(ArrayList<String> keywords) {
		this.keywords = keywords;
	}
	/**
	 * This method is used to access the links of the web page
	 * @return
	 * links of the web page
	 */
	public String getLinks() {
		return links;
	}
	/**
	 * This method is used to set the links of the web page
	 * @param links
	 * new links of the web page
	 */
	public void setLinks(String links) {
		this.links = links;
	}
	/**
	 * This method neatly formats the information of the web page
	 */
	public String toString() {
		return String.format("%3d   | %-19s|%5d    | %-18s| %s", index, url, rank, links, keywords.toString().substring(1, keywords.toString().length()-1));
	}
}
