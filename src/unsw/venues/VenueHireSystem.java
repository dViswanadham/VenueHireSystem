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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONObject;

import unsw.venues.RoomInfo;


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
            Master.appendR(list_venue, v, r);
            break;

        case "request":
            String id = json.getString("id");
            LocalDate start = LocalDate.parse(json.getString("start"));
            LocalDate end = LocalDate.parse(json.getString("end"));
            int small = json.getInt("small");
            int medium = json.getInt("medium");
            int large = json.getInt("large");

            Reservation request = new Reservation(id, start, end, small, medium, large);            
            if (!Master.scanRes(list_venue, request)) break;
            else Master.insertRes(list_booking, request);
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
            if (Master.scanRes(list_booking, newId) == null) break;
            else {
            	Reservation oldRequest = Master.scanRes(list_booking, newId);
            	oldRequest.revokeRes(oldRequest.obtainBeginDate(), oldRequest.obtainFinishDate());
                if (!Master.scanRes(list_venue, newRequest)) break;
                else Master.amendRes(list_booking, oldRequest, newRequest);
            }
        	break;
        	
        case "cancel":
        	String delId = json.getString("id");

        	if (Master.scanMult(list_booking, delId).isEmpty()) break;
        	else Master.revokeRes(list_booking, Master.scanMult(list_booking, delId));        	
        	break;
        	
        case "list":
        	String vList = json.getString("venue");

        	if (Master.scanValidV(list_venue, vList) == null) break;
        	else Master.vList(list_booking, Master.scanValidV(list_venue, vList));
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
