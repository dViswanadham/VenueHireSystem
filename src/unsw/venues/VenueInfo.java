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

public class VenueInfo {
	private String venue_title;
	private ArrayList<RoomInfo> list_room;
	
	public VenueInfo(String venue_title) {
		inputTitle(venue_title);
		list_room = new ArrayList<RoomInfo>();
	};
	/**
	 * @return the venue_title
	 */
	public String obtainTitle() {
		return venue_title;
	}
	/**
	 * @param venue_title the venue_title to set
	 */
	private void inputTitle(String venue_title) {
		this.venue_title = venue_title;
	}
	/**
	 * @return the list_room
	 */
	public ArrayList<RoomInfo> obtainRList() {
		return list_room;
	}
	/**
	 * Add room to list_room
	 * @param room
	 */
	public void increaseRCount(RoomInfo room) {
		list_room.add(room);
	}
	/**
	 * Returns a list of free small rooms
	 * @param start
	 * @param end
	 * @return ArrayList<RoomInfo>
	 */
	public ArrayList<RoomInfo> scanSRooms(LocalDate start, LocalDate end) {
		ArrayList<RoomInfo> freeSmallRoomList = new ArrayList<RoomInfo>();
		for (int i = 0; i < list_room.size(); i++) {
			RoomInfo r = list_room.get(i);
			if (r.obtainCap().equals("small") && r.scanPeriod(start, end)) freeSmallRoomList.add(r);
		}
		return freeSmallRoomList;
	}
	/**
	 * Returns a list of free medium rooms
	 * @param start
	 * @param end
	 * @return ArrayList<RoomInfo>
	 */
	public ArrayList<RoomInfo> scanMRooms(LocalDate start, LocalDate end) {
		ArrayList<RoomInfo> freeMediumRoomList = new ArrayList<RoomInfo>();
		for (int i = 0; i < list_room.size(); i++) {
			RoomInfo r = list_room.get(i);
			if (r.obtainCap().equals("medium") && r.scanPeriod(start, end)) freeMediumRoomList.add(r);
		}
		return freeMediumRoomList;
	}
	/**
	 * Returns a list of free large rooms
	 * @param start
	 * @param end
	 * @return ArrayList<RoomInfo>
	 */
	public ArrayList<RoomInfo> scanLRooms(LocalDate start, LocalDate end) {
		ArrayList<RoomInfo> freeLargeRoomList = new ArrayList<RoomInfo>();
		for (int i = 0; i < list_room.size(); i++) {
			RoomInfo r = list_room.get(i);
			if (r.obtainCap().equals("large") && r.scanPeriod(start, end)) freeLargeRoomList.add(r);
		}
		return freeLargeRoomList;
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
		VenueInfo otherObj = (VenueInfo) obj;
		return this.venue_title.equals(otherObj.venue_title) 
				&& this.list_room.equals(otherObj.list_room);
	}	
}
