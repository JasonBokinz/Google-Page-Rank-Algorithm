/**
 * This class implements comparators and compares two web page ranks.
 * @author jasonbokinz, ID: 112555537, R: 03
 */
import java.util.Comparator;
public class RankComparator implements Comparator {
	/**
	 * This method compares two web page ranks.
	 */
	public int compare(Object o1, Object o2) {
		WebPage p1 = (WebPage) o1;
		WebPage p2 = (WebPage) o2;
		if (p1.getRank() == p2.getRank())
            return 0;
        else if (p1.getRank() > p2.getRank())
            return 1;
        else
            return -1;
	}
}
