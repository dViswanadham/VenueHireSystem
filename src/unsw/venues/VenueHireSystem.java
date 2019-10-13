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

import org.json.JSONObject;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;

import unsw.venues.RoomInfo;

public class VenueHireSystem {
    
    /*
     * A simple venue hire system which initially contains no venues, rooms or reservations.
     */
    private ArrayList<VenueInfo> list_venue;
    private ArrayList<Reservation> list_booking;
    
    private VenueHireSystem() {
        
        list_venue = new ArrayList<VenueInfo>();
        list_booking = new ArrayList<Reservation>();
    }
    
    private void processCommand(JSONObject json) {
        switch (json.getString("command")) {
        
        case "room":
            String where = json.getString("venue");
            String acc = json.getString("room");
            String capacity = json.getString("size");
            
            VenueInfo locale = new VenueInfo(where);
            RoomInfo stay = new RoomInfo(acc, capacity);
            Master.appendR(list_venue, locale, stay);
            
            break;
        
        case "request":
            String res_tag = json.getString("id");
            LocalDate begin = LocalDate.parse(json.getString("start"));
            LocalDate finish = LocalDate.parse(json.getString("end"));
            
            int s_room = json.getInt("small");
            int m_room = json.getInt("medium");
            int l_room = json.getInt("large");
            
            Reservation reserve = new Reservation(res_tag, begin, finish, 
                                                  s_room, m_room, l_room);
            
            if (!Master.scanRes(list_venue, reserve)) {
                break;
                
            } else {
                
                Master.insertRes(list_booking, reserve);
            }
            
            break;
            
        case "list":
            String vList = json.getString("venue");
            
            if (Master.scanValidV(list_venue, vList) == null) {
                break;
                
            } else {
                
                Master.vList(list_booking, Master.scanValidV(list_venue, vList));
            }
            
            break;
            
        case "change":
            String fresh_res_tag = json.getString("id");
            LocalDate fresh_begin_date = LocalDate.parse(json.getString("start"));
            LocalDate fresh_end_date = LocalDate.parse(json.getString("end"));
            
            int fresh_s_room = json.getInt("small");
            int fresh_m_room = json.getInt("medium");
            int fresh_l_room = json.getInt("large");
            
            Reservation fresh_reserve = new Reservation(fresh_res_tag, 
                                                        fresh_begin_date, 
                                                        fresh_end_date, 
                                                        fresh_s_room, 
                                                        fresh_m_room, 
                                                        fresh_l_room);
            
            if (Master.scanRes(list_booking, fresh_res_tag) == null) {
                break;
            
            } else {
                Reservation original_res = Master.scanRes(list_booking, fresh_res_tag);
                original_res.revokeRes(original_res.obtainBeginDate(), 
                                       original_res.obtainFinishDate());
                
                if (!Master.scanRes(list_venue, fresh_reserve)) {
                    break;
                    
                } else {
                    
                    Master.amendRes(list_booking, original_res, fresh_reserve);
                }
            }
            
            break;
            
        case "cancel":
            String revoke_res_tag = json.getString("id");
            
            if (Master.scanMult(list_booking, revoke_res_tag).isEmpty()) {
                break;
            
            } else {
                
                Master.revokeRes(list_booking, Master.scanMult(list_booking, 
                                 revoke_res_tag));
            }
            
            break;
            
        }
    }
    
    public static void main(String[] args) {
        VenueHireSystem process = new VenueHireSystem();
        
        Scanner parser = new Scanner(System.in);
        
        while(parser.hasNextLine()) {
            String sentence = parser.nextLine();
            
            if (!sentence.trim().equals("")) {
                JSONObject call = new JSONObject(sentence);
                
                process.processCommand(call);
            }
        }
        
        parser.close();
    }
}