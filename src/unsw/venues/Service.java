/**
 * @author Dheeraj Viswanadham z5204820
 *
 * Acknowledgement: have done some external research on sites such as 
 * StackOverflow and Java manuals online, so code may be very similar to them. 
 * Also, have used similar codes in labs.
 * 
 * Started: 28/09/2019 | Last edited: 13/10/2019
 */

package unsw.venues;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;

public interface Service {
    
    /**
     * 
     * Function orders list_booking according to the beginning period,
     * returning an ordered list_booking
     * 
     * @param list_booking
     * @return ArrayList<Reservation>
     */
    public static ArrayList<Reservation> orderPeriod(ArrayList<Reservation> list_booking) {
        Collections.sort(list_booking, new Comparator<Reservation>() {
            
            @Override
            public int compare(Reservation res_one, Reservation res_two) {
                
                return res_one.obtainBeginDate().compareTo(res_two.obtainBeginDate());
            }
        });
        
        return list_booking;
    }
}