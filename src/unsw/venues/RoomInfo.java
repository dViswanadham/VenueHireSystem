/**
 * @author Dheeraj Viswanadham z5204820
 *
 * Acknowledgement: have done some external research on sites such as StackOverflow 
 * and Java manuals online, so code may be very similar to them. 
 * Also, have used similar codes in labs.
 * 
 * Started: 28/09/2019 | Last edited: 13/10/2019
 */

package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class RoomInfo {
	private String room_title;
	private String room_capacity;
	private ArrayList<LocalDate> list_period;
	
	public RoomInfo(String room_title, String room_capacity) {
		inputTitle(room_title);
		inputCap(room_capacity);
		list_period = new ArrayList<LocalDate>();
	}
	/**
	 * @return the room_title
	 */
	public String obtainTitle() {
		return room_title;
	}
	/**
	 * @param room_title the room_title to set
	 */
	private void inputTitle(String room_title) {
		this.room_title = room_title;
	}
	/**
	 * @return the room_capacity
	 */
	public String obtainCap() {
		return room_capacity;
	}
	/**
	 * @param room_capacity the room_capacity to set
	 */
	private void inputCap(String room_capacity) {
		this.room_capacity = room_capacity;
	}
	/**
	 * Compare start and end dates
	 * in list_period
	 * @param start
	 * @param end
	 * @return boolean
	 */
	public boolean scanPeriod(LocalDate start, LocalDate end) {
		if (! list_period.isEmpty()) {
			int i = 0;
			while(i < list_period.size()) {
				int j = i + 1;
				if (start.isAfter(list_period.get(i)) && start.isBefore(list_period.get(j))) return false;
				if (end.isAfter(list_period.get(i)) && end.isBefore(list_period.get(j))) return false;
				if (start.isEqual(list_period.get(i)) || start.isEqual(list_period.get(j))) return false;
				if (end.isEqual(list_period.get(i)) || end.isEqual(list_period.get(j))) return false;
				if (list_period.get(i).isAfter(start) && list_period.get(j).isBefore(end)) return false;
				i = j + 1;
			}
		}
		return true;
	}
	/**
	 * Add start and end date
	 * to list_period
	 * @param start
	 * @param end
	 */
	public void addPeriod(LocalDate start, LocalDate end) {
		list_period.add(start);
		list_period.add(end);
	}
	/**
	 * Remove start and end date
	 * to list_period
	 * @param start
	 * @param end
	 */
	public void remPeriod(LocalDate start, LocalDate end) {
		Iterator<LocalDate> it = list_period.iterator();
		while (it.hasNext()) {
			LocalDate temp = it.next();
			if (start.isEqual(temp) || end.isEqual(temp)) it.remove();
		} 
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// DONE Auto-generated method stub
		if (this == obj) return true;
		if (obj == null) return false;
		if (this.getClass() != obj.getClass()) return false;
		RoomInfo otherObj = (RoomInfo) obj;
		return this.room_title.equals(otherObj.room_title) 
				&& this.room_capacity.equals(otherObj.room_capacity);
	}
}
