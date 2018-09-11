import java.util.Comparator;
/**
 * comparator compares bookings based on dates, if two dates are equal than
 * it compares them based on booking times and sorts them in that order in a TreeSet
 * @author bmlyn
 *
 */
public class BookingCompare implements Comparator<Booking> {

	@Override
	public int compare(Booking booking1, Booking booking2) {
		if(!booking1.getDates().equals(booking2.getDates())) {
			return booking1.getDates().compareTo(booking2.getDates());
		}else
		{
			String time1 = Integer.toString(booking1.getStartTime());
			String time2 = Integer.toString(booking2.getStartTime());
			
			return time2.compareTo(time1);
					
		}
	}


}
