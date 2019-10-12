/**
 * @author Dheeraj Viswanadham z5204820
 *
 * Acknowledgement: have done some external research on sites such as StackOverflow 
 * and Java manuals online, so code may be very similar to them. 
 * Also, have used similar codes in labs.
 * 
 * Started: 28/09/2019 | Last edited: 12/10/2019
 */

package unsw.venues;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public interface Master {
	Result print = Result.obtainStill();
	/**
	 * Checks if venue exists in list_venue
	 * if venue exists set the venue to the existing venue
	 * then check if room exist in venue 
	 * if room doens't exist add room to venue's arrayList<RoomInfo>
	 * else print JSON failureInput
	 * @param list_venue the list_venue from the VenueInfoHireSystem
	 * @param venue 
	 * @param room
	 */
	public static void appendR(ArrayList<VenueInfo> list_venue, VenueInfo venue, RoomInfo room){
		for (VenueInfo v : list_venue) {
			if (v.obtainTitle().equals(venue.obtainTitle())) venue = v;
		}
		for (RoomInfo r : venue.obtainRList()) {
			if (r.equals(room)) {
				print.printObj(print.failureInput());
				return;
			}
		}
		venue.increaseRCount(room);
		if (! list_venue.contains(venue)) list_venue.add(venue);
	}
	/**
	 * Check available VenueInfo from list_venue
	 * if venue available setVenueInfo
	 * else print JSON failureInput
	 * requests size < 0 increaseRCount to request according to size
	 * @param list_venue the list_venue from the VenueInfoHireSystem
	 * @param request
	 * @return boolean
	 */
	public static boolean scanRes(ArrayList<VenueInfo> list_venue, Reservation request) {
		VenueInfo venue = null;
		for (VenueInfo v : list_venue) {
			if (request.checkAvailability(v)) {
				venue = v;
				request.setVenueInfo(venue);
				venue.equals(request.getVenueInfo());
				break;
			}
		}
		if (request.getVenueInfo() == null) {
			print.printObj(print.failureInput());
			return false;
		}
		if (request.getSmall() >= 0) {
    		ArrayList<RoomInfo> freeSmallRooms = request.getVenueInfo().searchSmallRoom(request.getStart(), request.getEnd());
    		request.checkRoom(freeSmallRooms, request.getSmall());
    	}
		if (request.getMedium() >= 0) {
    		ArrayList<RoomInfo> freeMediumRooms = request.getVenueInfo().searchMediumRoom(request.getStart(), request.getEnd());
    		request.checkRoom(freeMediumRooms, request.getMedium());
    	}
    	if (request.getLarge() >= 0) {
    		ArrayList<RoomInfo> freeLargeRooms = request.getVenueInfo().searchLargeRoom(request.getStart(), request.getEnd());
    		request.checkRoom(freeLargeRooms, request.getLarge());
    	}
    	request.updateRoom(request.getStart(), request.getEnd());
		return true;
	}
	/**
	 * Add request to bookingist
	 * print JSON statement
	 * @param list_booking the list_booking from the VenueInfoHireSystem
	 * @param request
	 */
	public static void insertRes(ArrayList<Reservation> list_booking, Reservation request){
		JSONObject result = new JSONObject();
    	list_booking.add(request);
    	print.confirmInput(result);
    	print.setField(result, "venue", request.getVenueInfoName());
    	JSONArray rooms = new JSONArray();
    	for (RoomInfo r : request.obtainListRooms()) {
    		print.setField(rooms, r.obtainTitle());
    	}
    	print.addJSONArray(result, "rooms", rooms);
    	print.printObj(result);
	}
	/**
	 * Remove oldRequest and add newRequest to list_booking
	 * print JSON statement
	 * @param list_booking the list_booking from the VenueInfoHireSystem
	 * @param oldRequest to remove
	 * @param newRequest to add
	 */
	public static void amendRes(ArrayList<Reservation> list_booking, Reservation oldRequest, Reservation newRequest) {
		JSONObject result = new JSONObject();
		list_booking.remove(oldRequest);
		list_booking.add(newRequest);
		print.confirmInput(result);
		print.setField(result, "venue", newRequest.getVenueInfoName());
		JSONArray rooms = new JSONArray();
    	for (RoomInfo r : newRequest.obtainListRooms()) {
    		print.setField(rooms, r.obtainTitle());
    	}
    	print.addJSONArray(result, "rooms", rooms);
    	print.printObj(result);
	}
	/**
	 * Check if request exist in list_booking
	 * else print JSON failureInput 
	 * @param list_booking the list_booking from the VenueInfoHireSystem
	 * @param id of a request
	 * @return request based on id
	 */
	public static Reservation scanRes(ArrayList<Reservation> list_booking, String id) {
		Reservation request = null;
		for (Reservation r : list_booking) {
			if (r.obtainResNo().equals(id)) return request = r;
		}
		print.printObj(print.failureInput());
		return request;
	}
	/**
	 * Check if request exist in list_booking
	 * else print JSON failureInput
	 * @param list_booking the list_booking from the VenueInfoHireSystem
	 * @param id of a request
	 * @return  ArrayList<Reservation> based on id
	 */
	public static ArrayList<Reservation> scanMult(ArrayList<Reservation> list_booking, String id) {
		ArrayList<Reservation> requestList = new ArrayList<Reservation>();
		for (Reservation r : list_booking) {
			if (r.getId().equals(id)) requestList.add(r);
		}
		if (requestList.isEmpty()) print.printObj(print.failureInput());
		return requestList;
	}
	/**
	 * Remove requestList from list_booking
	 * print JSON statement
	 * @param list_booking the list_booking from the VenueInfoHireSystem
	 * @param requestList
	 */
	public static void revokeRes(ArrayList<Reservation> list_booking, ArrayList<Reservation> requestList) {
		Iterator<Reservation> iterator = list_booking.iterator();
		while (iterator.hasNext()) {
			for (Reservation request : requestList) {
				if (request.equals(iterator.next())) {
					RoomInfo toDel = null;
					for (RoomInfo r : request.obtainListRooms()) {
						toDel = r;
						r.delDate(request.getStart(), request.getEnd());
					}
					request.obtainListRooms().remove(toDel);
					if (request.obtainListRooms().isEmpty()) iterator.remove();
				}
			}
		}
		print.printObj(print.confirmInput());
	}
	/**
	 * Check if venue exist in list_venue
	 * else print JSON failureInput
	 * @param list_venue the list_venue from the VenueInfoHireSystem
	 * @param venueName
	 * @return venue based on venueName
	 */
	public static VenueInfo scanValidV(ArrayList<VenueInfo> list_venue, String venueName) {
		VenueInfo venue = null;
		for (VenueInfo v : list_venue) {
			if (v.obtainTitle().equals(venueName)) return venue = v;
		}
		print.printObj(print.failureInput());
		return venue;
	}
	/**
	 * use Service.orderPeriod on list_booking
	 * print JSON statement
	 * @param list_booking the list_booking from the VenueInfoHireSystem
	 * @param venue
	 */
	public static void vList(ArrayList<Reservation> list_booking, VenueInfo venue) {
		JSONArray array = new JSONArray();
		ArrayList<Reservation> sortedBookingList = Service.orderPeriod(list_booking);
		for (RoomInfo room : venue.obtainRList()) {
			JSONObject result = new JSONObject();
			print.setField(result, "room", room.obtainTitle());
			JSONArray bookings = new JSONArray();
			for (int i = 0; i < sortedBookingList.size(); i++) {
				if (sortedBookingList.get(i).obtainListRooms().isEmpty()) continue;
				for (RoomInfo room2 : sortedBookingList.get(i).obtainListRooms()) {
					if (room2.equals(room)) {
						JSONObject booking = new JSONObject();
						print.setField(booking, "id", sortedBookingList.get(i).obtainResNo());
						print.setField(booking, "start", sortedBookingList.get(i).getStart());
						print.setField(booking, "end", sortedBookingList.get(i).getEnd());
						print.addJSONObject(bookings, booking);
					}
				}
			}
			print.addJSONArray(result, "reservations", bookings);
			print.addJSONObject(array, result);
		}
		print.jsonArray(array);
	}
}
