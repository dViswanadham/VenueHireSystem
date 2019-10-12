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

public interface Command {
	JsonOutput print = JsonOutput.getInstance();
	/**
	 * Checks if venue exists in list_venue
	 * if venue exists set the venue to the existing venue
	 * then check if room exist in venue 
	 * if room doens't exist add room to venue's arrayList<Room>
	 * else print JSON errorMessage
	 * @param list_venue the list_venue from the VenueInfoHireSystem
	 * @param venue 
	 * @param room
	 */
	public static void increaseRCount(ArrayList<VenueInfo> list_venue, VenueInfo venue, Room room){
		for (VenueInfo v : list_venue) {
			if (v.getName().equals(venue.getName())) venue = v;
		}
		for (Room r : venue.getRoomList()) {
			if (r.equals(room)) {
				print.jsonObject(print.errorMessage());
				return;
			}
		}
		venue.increaseRCount(room);
		if (! list_venue.contains(venue)) list_venue.add(venue);
	}
	/**
	 * Check available VenueInfo from list_venue
	 * if venue available setVenueInfo
	 * else print JSON errorMessage
	 * requests size < 0 increaseRCount to request according to size
	 * @param list_venue the list_venue from the VenueInfoHireSystem
	 * @param request
	 * @return boolean
	 */
	public static boolean scanRes(ArrayList<VenueInfo> list_venue, Request request) {
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
			print.jsonObject(print.errorMessage());
			return false;
		}
		if (request.getSmall() >= 0) {
    		ArrayList<Room> freeSmallRooms = request.getVenueInfo().searchSmallRoom(request.getStart(), request.getEnd());
    		request.checkRoom(freeSmallRooms, request.getSmall());
    	}
		if (request.getMedium() >= 0) {
    		ArrayList<Room> freeMediumRooms = request.getVenueInfo().searchMediumRoom(request.getStart(), request.getEnd());
    		request.checkRoom(freeMediumRooms, request.getMedium());
    	}
    	if (request.getLarge() >= 0) {
    		ArrayList<Room> freeLargeRooms = request.getVenueInfo().searchLargeRoom(request.getStart(), request.getEnd());
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
	public static void insertRes(ArrayList<Request> list_booking, Request request){
		JSONObject result = new JSONObject();
    	list_booking.add(request);
    	print.successMessage(result);
    	print.setField(result, "venue", request.getVenueInfoName());
    	JSONArray rooms = new JSONArray();
    	for (Room r : request.getRoomList()) {
    		print.setField(rooms, r.getName());
    	}
    	print.addJSONArray(result, "rooms", rooms);
    	print.jsonObject(result);
	}
	/**
	 * Remove oldRequest and add newRequest to list_booking
	 * print JSON statement
	 * @param list_booking the list_booking from the VenueInfoHireSystem
	 * @param oldRequest to remove
	 * @param newRequest to add
	 */
	public static void amendRes(ArrayList<Request> list_booking, Request oldRequest, Request newRequest) {
		JSONObject result = new JSONObject();
		list_booking.remove(oldRequest);
		list_booking.add(newRequest);
		print.successMessage(result);
		print.setField(result, "venue", newRequest.getVenueInfoName());
		JSONArray rooms = new JSONArray();
    	for (Room r : newRequest.getRoomList()) {
    		print.setField(rooms, r.getName());
    	}
    	print.addJSONArray(result, "rooms", rooms);
    	print.jsonObject(result);
	}
	/**
	 * Check if request exist in list_booking
	 * else print JSON errorMessage 
	 * @param list_booking the list_booking from the VenueInfoHireSystem
	 * @param id of a request
	 * @return request based on id
	 */
	public static Request scanRes(ArrayList<Request> list_booking, String id) {
		Request request = null;
		for (Request r : list_booking) {
			if (r.getId().equals(id)) return request = r;
		}
		print.jsonObject(print.errorMessage());
		return request;
	}
	/**
	 * Check if request exist in list_booking
	 * else print JSON errorMessage
	 * @param list_booking the list_booking from the VenueInfoHireSystem
	 * @param id of a request
	 * @return  ArrayList<Request> based on id
	 */
	public static ArrayList<Request> scanRes(ArrayList<Request> list_booking, String id) {
		ArrayList<Request> requestList = new ArrayList<Request>();
		for (Request r : list_booking) {
			if (r.getId().equals(id)) requestList.add(r);
		}
		if (requestList.isEmpty()) print.jsonObject(print.errorMessage());
		return requestList;
	}
	/**
	 * Remove requestList from list_booking
	 * print JSON statement
	 * @param list_booking the list_booking from the VenueInfoHireSystem
	 * @param requestList
	 */
	public static void revokeRes(ArrayList<Request> list_booking, ArrayList<Request> requestList) {
		Iterator<Request> iterator = list_booking.iterator();
		while (iterator.hasNext()) {
			for (Request request : requestList) {
				if (request.equals(iterator.next())) {
					Room toDel = null;
					for (Room r : request.getRoomList()) {
						toDel = r;
						r.delDate(request.getStart(), request.getEnd());
					}
					request.getRoomList().remove(toDel);
					if (request.getRoomList().isEmpty()) iterator.remove();
				}
			}
		}
		print.jsonObject(print.successMessage());
	}
	/**
	 * Check if venue exist in list_venue
	 * else print JSON errorMessage
	 * @param list_venue the list_venue from the VenueInfoHireSystem
	 * @param venueName
	 * @return venue based on venueName
	 */
	public static VenueInfo scanValidV(ArrayList<VenueInfo> list_venue, String venueName) {
		VenueInfo venue = null;
		for (VenueInfo v : list_venue) {
			if (v.getName().equals(venueName)) return venue = v;
		}
		print.jsonObject(print.errorMessage());
		return venue;
	}
	/**
	 * use Service.orderPeriod on list_booking
	 * print JSON statement
	 * @param list_booking the list_booking from the VenueInfoHireSystem
	 * @param venue
	 */
	public static void vList(ArrayList<Request> list_booking, VenueInfo venue) {
		JSONArray array = new JSONArray();
		ArrayList<Request> sortedBookingList = Service.orderPeriod(list_booking);
		for (Room room : venue.getRoomList()) {
			JSONObject result = new JSONObject();
			print.setField(result, "room", room.getName());
			JSONArray bookings = new JSONArray();
			for (int i = 0; i < sortedBookingList.size(); i++) {
				if (sortedBookingList.get(i).getRoomList().isEmpty()) continue;
				for (Room room2 : sortedBookingList.get(i).getRoomList()) {
					if (room2.equals(room)) {
						JSONObject booking = new JSONObject();
						print.setField(booking, "id", sortedBookingList.get(i).getId());
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
