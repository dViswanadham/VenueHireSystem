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

import java.util.ArrayList;
import java.time.LocalDate;

public class VenueInfo {
    private String venue_title;
    private ArrayList<RoomInfo> list_room;
    
    public VenueInfo(String venue_title) {
        inputTitle(venue_title);
        
        list_room = new ArrayList<RoomInfo>();
    };
    
    /**
     * 
     * Function returns venue_title
     * 
     * @return String
     */
    public String obtainTitle() {
        
        return venue_title;
    }
    
    /**
     * 
     * Function sets venue_title
     * 
     * @param venue_title
     */
    private void inputTitle(String venue_title) {
        
        this.venue_title = venue_title;
    }
    
    /**
     * 
     * Function returns unusedSRmIndx (vacant rooms)
     * 
     * @param start
     * @param end
     * @return ArrayList<RoomInfo>
     */
    public ArrayList<RoomInfo> scanSRooms(LocalDate start, LocalDate end) {
        ArrayList<RoomInfo> unusedSRmIndx = new ArrayList<RoomInfo>();
        
        for(int a = 0; a < list_room.size(); a = (a + 1)) {
            RoomInfo acc = list_room.get(a);
            
            if (acc.obtainCap().equals("small") && acc.scanPeriod(start, end)) {
                
                unusedSRmIndx.add(acc);
            }
        }
        
        return unusedSRmIndx;
    }
    
    /**
     * 
     * Function returns unusedMRmIndx (vacant rooms)
     * 
     * @param start
     * @param end
     * @return ArrayList<RoomInfo>
     */
    public ArrayList<RoomInfo> scanMRooms(LocalDate start, LocalDate end) {
        ArrayList<RoomInfo> unusedMRmIndx = new ArrayList<RoomInfo>();
        
        for(int a = 0; a < list_room.size(); a = (a + 1)) {
            RoomInfo acc = list_room.get(a);
            
            if (acc.obtainCap().equals("medium") && acc.scanPeriod(start, end)) {
                
                unusedMRmIndx.add(acc);
            }
        }
        
        return unusedMRmIndx;
    }
    
    /**
     * 
     * Function returns unusedLRmIndx (vacant rooms)
     * 
     * @param start
     * @param end
     * @return ArrayList<RoomInfo>
     */
    public ArrayList<RoomInfo> scanLRooms(LocalDate start, LocalDate end) {
        ArrayList<RoomInfo> unusedLRmIndx = new ArrayList<RoomInfo>();
        
        for(int a = 0; a < list_room.size(); a = (a + 1)) {
            RoomInfo acc = list_room.get(a);
            
            if (acc.obtainCap().equals("large") && acc.scanPeriod(start, end)) {
                
                unusedLRmIndx.add(acc);
            }
        }
        
        return unusedLRmIndx;
    }
    
    
    
    /**
     * 
     * Function returns list_room
     * 
     * @return ArrayList<RoomInfo>
     */
    public ArrayList<RoomInfo> obtainRList() {
        
        return list_room;
    }
    
    /**
     * 
     * Function appends acc to list_room
     * 
     * @param acc
     */
    public void increaseRCount(RoomInfo acc) {
        
        list_room.add(acc);
    }
    
    
    /**
     * Equals method
     */
    @Override
    public boolean equals(Object body) {
        if (this == body) {
            
            return true;
        }
        
        if (body == null) {
            
            return false;
        }
        
        if (this.getClass() != body.getClass()) {
            
            return false;
        }
        
        VenueInfo other = (VenueInfo) body;
        
        return this.venue_title.equals(other.venue_title) && 
               this.list_room.equals(other.list_room);
    }
}