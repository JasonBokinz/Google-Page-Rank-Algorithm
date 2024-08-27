/**
 * This class implements comparators and compares two web page urls.
 * @author jasonbokinz, ID: 112555537, R: 03
 */
import java.util.Comparator;
public class URLComparator implements Comparator {
	/**
	 * This method compares two web page urls.
	 */
	public int compare(Object o1, Object o2) {
		WebPage p1 = (WebPage) o1;
		WebPage p2 = (WebPage) o2;
		return (p1.getUrl().compareTo(p2.getUrl()));
	}
}
