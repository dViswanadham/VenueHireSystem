/**
 *
 */
package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONObject;

import unsw.venues.RoomInfo;

/**
 *
 * @author Dheeraj Viswanadham z5204820
 *
 */
public class VenueHireSystem {

    /**
     * Constructs a venue hire system. Initially, the system contains no venues,
     * rooms, or bookings.
     */
	private ArrayList<VenueInfo> list_venue;
	private ArrayList<Reservation> list_booking;
	
    private VenueHireSystem() {
        // DONE Auto-generated constructor stub
    	list_venue = new ArrayList<VenueInfo>();
    	list_booking = new ArrayList<Reservation>();
    }

    private void processCommand(JSONObject json) {
        switch (json.getString("command")) {

        case "room":
            String venue = json.getString("venue");
            String room = json.getString("room");
            String size = json.getString("size");
            
            VenueInfo v = new VenueInfo(venue);
            RoomInfo r = new RoomInfo(room, size);
            Master.increaseRCount(list_venue, v, r);
            break;

        case "request":
            String id = json.getString("id");
            LocalDate start = LocalDate.parse(json.getString("start"));
            LocalDate end = LocalDate.parse(json.getString("end"));
            int small = json.getInt("small");
            int medium = json.getInt("medium");
            int large = json.getInt("large");

            Reservation request = new Reservation(id, start, end, small, medium, large);            
            if (!Master.checkRequest(list_venue, request)) break;
            else Master.addRequest(list_booking, request);
            break;

        // COMPLETED Implement other commands
        case "change":
        	String newId = json.getString("id");
            LocalDate newStart = LocalDate.parse(json.getString("start"));
            LocalDate newEnd = LocalDate.parse(json.getString("end"));
            int newSmall = json.getInt("small");
            int newMedium = json.getInt("medium");
            int newLarge = json.getInt("large");
            
            Reservation newRequest = new Reservation(newId, newStart, newEnd, newSmall, newMedium, newLarge);
            if (Master.checkRequest(list_booking, newId) == null) break;
            else {
            	Reservation oldRequest = Master.checkRequest(list_booking, newId);
            	oldRequest.cancelRoom(oldRequest.getStart(), oldRequest.getEnd());
                if (!Master.checkRequest(list_venue, newRequest)) break;
                else Master.changeRequest(list_booking, oldRequest, newRequest);
            }
        	break;
        	
        case "cancel":
        	String delId = json.getString("id");

        	if (Master.checkRequests(list_booking, delId).isEmpty()) break;
        	else Master.cancelRequest(list_booking, Master.checkRequests(list_booking, delId));        	
        	break;
        	
        case "list":
        	String listVenue = json.getString("venue");

        	if (Master.checkVenue(list_venue, listVenue) == null) break;
        	else Master.listVenue(list_booking, Master.checkVenue(list_venue, listVenue));
        	break;
        }
    }
    
	public static void main(String[] args) {
        VenueHireSystem system = new VenueHireSystem();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!line.trim().equals("")) {
                JSONObject command = new JSONObject(line);
                system.processCommand(command);
            }
        }
        sc.close();
    }
}
