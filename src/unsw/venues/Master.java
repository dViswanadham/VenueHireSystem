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

import org.json.JSONArray;
import java.util.ArrayList;
import org.json.JSONObject;
import java.util.Iterator;

public interface Master {
    Result output = Result.obtainStill();
    
    /**
     * 
     * Function determines whether a valid venue is already within the list of venues.
     * If there is, then we appoint that venue as our current venue.
     * and check if there is a valid room within the venue.
     * If not, then we appoint that room to the venue.
     * Otherwise, output the failureInput object
     * 
     * @param list_venue
     * @param where
     * @param r_id
     */
    public static void appendR(ArrayList<VenueInfo> list_venue, VenueInfo where, 
                               RoomInfo r_id) {
        for(VenueInfo ven_one : list_venue) {
            if (ven_one.obtainTitle().equals(where.obtainTitle())) {
                
                where = ven_one;
            }
        }
        
        for(RoomInfo rm_one : where.obtainRList()) {
            if (rm_one.equals(r_id)) {
                
                output.printObj(output.failureInput());
                
                return;
            }
        }
        
        where.increaseRCount(r_id);
        
        if (!list_venue.contains(where)) {
            
            list_venue.add(where);
        }
    }
    
    /**
     * 
     * Function determines venue from list_venue.
     * If there is a valid venue, then call inputVInfo.
     * Otherwise, output the failureInput object.
     * 
     * @param list_venue
     * @param res
     * @return boolean
     */
    public static boolean scanRes(ArrayList<VenueInfo> list_venue, 
                                  Reservation res) {
        VenueInfo where = null;
        
        for(VenueInfo ven_one : list_venue) {
            if (res.scanFree(ven_one)) {
                
                where = ven_one;
                res.inputVInfo(where);
                where.equals(res.obtainVInfo());
                
                break;
            }
        }
        
        if (res.obtainVInfo() == null) {
            output.printObj(output.failureInput());
            
            return false;
        }
        
        if (res.obtainSRoom() >= 0) {
            
            ArrayList<RoomInfo> unusedSmaRm = res.obtainVInfo().scanSRooms(res.obtainBeginDate(), 
                                              res.obtainFinishDate());
            res.scanFreeR(unusedSmaRm, res.obtainSRoom());
        }
        
        if (res.obtainMRoom() >= 0) {
            
            ArrayList<RoomInfo> unusedMedRm = res.obtainVInfo().scanMRooms(res.obtainBeginDate(), 
                                              res.obtainFinishDate());
            res.scanFreeR(unusedMedRm, res.obtainMRoom());
        }
        
        if (res.obtainLRoom() >= 0) {
            
            ArrayList<RoomInfo> unusedLarRm = res.obtainVInfo().scanLRooms(res.obtainBeginDate(), 
                                              res.obtainFinishDate());
            res.scanFreeR(unusedLarRm, res.obtainLRoom());
        }
        
        res.amendChanges(res.obtainBeginDate(), res.obtainFinishDate());
        
        return true;
    }
    
    /**
     * 
     * Function determines whether a reservation is in list_booking.
     * If not, output the failureInput object.
     * 
     * @param list_booking
     * @param res_tag
     * @return Reservation
     */
    public static Reservation scanRes(ArrayList<Reservation> list_booking, 
                                      String res_tag) {
        Reservation res = null;
        
        for(Reservation rm_one : list_booking) {
            if (rm_one.obtainResNo().equals(res_tag)) {
                
                return res = rm_one;
            }
        }
        
        output.printObj(output.failureInput());
        
        return res;
    }
    
    /**
     * 
     * Function inserts the reservation into list_booking.
     * Outputs the object.
     * 
     * @param list_booking
     * @param res
     */
    public static void insertRes(ArrayList<Reservation> list_booking, 
                                 Reservation res) {
        JSONObject event = new JSONObject();
        
        list_booking.add(res);
        
        output.confirmInput(event);
        output.editLoc(event, "venue", res.obtainVTitle());
        
        JSONArray place = new JSONArray();
        
        for(RoomInfo rm_one : res.obtainListRooms()) {
            
            output.editLoc(place, rm_one.obtainTitle());
        }
        
        output.insertArr(event, "rooms", place);
        output.printObj(event);
    }
    
    /**
     * 
     * Function gets rids of outdated reservation and brings in a fresh new one.
     * Outputs the object.
     * 
     * @param list_booking
     * @param outdated
     * @param fresh
     */
    public static void amendRes(ArrayList<Reservation> list_booking, 
                                Reservation outdated, Reservation fresh) {
        JSONObject event = new JSONObject();
        
        list_booking.remove(outdated);
        list_booking.add(fresh);
        
        output.confirmInput(event);
        output.editLoc(event, "venue", fresh.obtainVTitle());
        
        JSONArray place = new JSONArray();
        
        for(RoomInfo rm_one : fresh.obtainListRooms()) {
            
            output.editLoc(place, rm_one.obtainTitle());
        }
        
        output.insertArr(event, "rooms", place);
        output.printObj(event);
    }
    
    /**
     * 
     * Determines whether a reservation is already in list_booking.
     * If not, output the failureInput object.
     * 
     * @param list_booking
     * @param res_tag
     * @return ArrayList<Reservation>
     */
    public static ArrayList<Reservation> scanMult(ArrayList<Reservation> list_booking, 
                                                  String res_tag) {
        ArrayList<Reservation> res_index = new ArrayList<Reservation>();
        
        for(Reservation rm_one : list_booking) {
            if (rm_one.obtainResNo().equals(res_tag)) {
                
                res_index.add(rm_one);
            }
        }
        
        if (res_index.isEmpty()) {
            
            output.printObj(output.failureInput());
        }
        
        return res_index;
    }
    
    /**
     * 
     * Function uses Service.orderPeriod to order list_booking.
     * Outputs the object.
     * 
     * @param list_booking
     * @param where
     */
    public static void vList(ArrayList<Reservation> list_booking, VenueInfo where) {
        JSONArray index_arr = new JSONArray();
        
        ArrayList<Reservation> orderedIndxBk = Service.orderPeriod(list_booking);
        
        for(RoomInfo r_id : where.obtainRList()) {
            JSONObject event = new JSONObject();
            output.editLoc(event, "room", r_id.obtainTitle());
            JSONArray schedule = new JSONArray();
            
            for(int var = 0; var < orderedIndxBk.size(); var = (var + 1)) {
                if (orderedIndxBk.get(var).obtainListRooms().isEmpty()) {
                    
                    continue;
                }
                
                for(RoomInfo r_id_two : orderedIndxBk.get(var).obtainListRooms()) {
                    if (r_id_two.equals(r_id)) {
                        JSONObject appoint = new JSONObject();
                        
                        output.editLoc(appoint, "id", orderedIndxBk.get(var).obtainResNo());
                        output.editLoc(appoint, "start", orderedIndxBk.get(var).obtainBeginDate());
                        output.editLoc(appoint, "end", orderedIndxBk.get(var).obtainFinishDate());
                        output.insertObj(schedule, appoint);
                    }
                }
            }
            
            output.insertArr(event, "reservations", schedule);
            output.insertObj(index_arr, event);
        }
        
        output.printArr(index_arr);
    }
    
    /**
     * 
     * Function expunges the res_index from list_booking.
     * Outputs the object.
     * 
     * @param list_booking
     * @param res_index
     */
    public static void revokeRes(ArrayList<Reservation> list_booking, 
                                 ArrayList<Reservation> res_index) {
        Iterator<Reservation> counter = list_booking.iterator();
        
        while(counter.hasNext()) {
            for(Reservation res : res_index) {
                if (res.equals(counter.next())) {
                    RoomInfo expunge = null;
                    
                    for(RoomInfo rm_one : res.obtainListRooms()) {
                        expunge = rm_one;
                        
                        rm_one.remPeriod(res.obtainBeginDate(), res.obtainFinishDate());
                    }
                    
                    res.obtainListRooms().remove(expunge);
                    
                    if (res.obtainListRooms().isEmpty()) {
                        
                        counter.remove();
                    }
                }
            }
        }
        output.printObj(output.confirmInput());
    }
    
    /**
     * 
     * Function determines if a valid venue is already within list_venue.
     * If not, then output the failureInput object.
     * 
     * @param list_venue
     * @param venueName
     * @return VenueInfo
     */
    public static VenueInfo scanValidV(ArrayList<VenueInfo> list_venue, 
                                       String venueName) {
        VenueInfo where = null;
        
        for(VenueInfo ven_one : list_venue) {
            if (ven_one.obtainTitle().equals(venueName)) {
                
                return where = ven_one;
            }
        }
        
        output.printObj(output.failureInput());
        
        return where;
    }
}