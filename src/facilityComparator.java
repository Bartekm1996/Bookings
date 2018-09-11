import java.util.Comparator;

/**
 * facilityComparator class implements the comparator interface paramatized <facility>
 * compares two facilities based on their names
 * @author bmlyn
 *
 */
public class facilityComparator implements Comparator<facility>{

	@Override
	public int compare(facility _facility1, facility _facility2) {
		
		return _facility1.getFacility().compareTo(_facility2.getFacility());
	}

}
