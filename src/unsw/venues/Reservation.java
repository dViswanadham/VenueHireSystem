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

public class Reservation {
    private String res_no;
    private LocalDate begin;
    private LocalDate finish;
    private int s_room;
    private int m_room;
    private int l_room;
    private VenueInfo v_info;
    private ArrayList<RoomInfo> list_room;
    
    public Reservation(String res_no, LocalDate begin, LocalDate finish, 
                       int s_room, int m_room, int l_room) {
                           
        inputResNo(res_no);
        inputBeginDate(begin);
        inputFinishDate(finish);
        inputSRoom(s_room);
        inputMRoom(m_room);
        inputLRoom(l_room);
        
        list_room = new ArrayList<RoomInfo>();
    }
    
    /**
     * 
     * Function returns begin date
     * 
     * @return LocalDate
     */
    public LocalDate obtainBeginDate() {
        
        return begin;
    }
    
    /**
     * 
     * Function sets begin date
     * 
     * @param begin
     */
    private void inputBeginDate(LocalDate begin) {
        
        this.begin = begin;
    }
    
    /**
     * 
     * Function returns finish date
     * 
     * @return LocalDate
     */
    public LocalDate obtainFinishDate() {
        
        return finish;
    }
    
    /**
     * 
     * Function sets finish date
     * 
     * @param finish
     */
    private void inputFinishDate(LocalDate finish) {
        
        this.finish = finish;
    }
    
    /**
     * 
     * Function returns s_room
     * 
     * @return int
     */
    public int obtainSRoom() {
        
        return s_room;
    }
    
    /**
     * 
     * Function sets s_room
     * 
     * @param s_room
     */
    private void inputSRoom(int s_room) {
        if (s_room < 0) {
            
            s_room = 0;
        }
        
        this.s_room = s_room;
    }
    
    /**
     * 
     * Function returns m_room
     * 
     * @return int
     */
    public int obtainMRoom() {
        
        return m_room;
    }
    
    /**
     * 
     * Function sets m_room
     * 
     * @param m_room
     */
    private void inputMRoom(int m_room) {
        if (m_room < 0) {
            
            m_room = 0;
        }
        
        this.m_room = m_room;
    }
    
    /**
     * 
     * Function returns l_room
     * 
     * @return int
     */
    public int obtainLRoom() {
        
        return l_room;
    }
    
    /**
     * 
     * Function sets l_room
     * 
     * @param l_room
     */
    private void inputLRoom(int l_room) {
        if (l_room < 0) {
            
            l_room = 0;
        }
        
        this.l_room = l_room;
    }
    
    /**
     * 
     * Function returns res_no
     * 
     * @return String
     */
    public String obtainResNo() {
        
        return res_no;
    }
    
    /**
     * 
     * Function sets res_no
     * 
     * @param res_no
     */
    private void inputResNo(String res_no) {
        
        this.res_no = res_no;
    }
    
    /**
     * 
     * Function returns v_info
     * 
     * @return VenueInfo
     */
    public VenueInfo obtainVInfo() {
        
        return v_info;
    }
    
    /**
     * 
     * Function sets v_info
     * 
     * @param v_info
     */
    public void inputVInfo(VenueInfo v_info) {
        
        this.v_info = v_info;
    }
    
    /**
     * 
     * Function returns list_room
     * 
     * @return ArrayList<RoomInfo>
     */
    public ArrayList<RoomInfo> obtainListRooms() {
        
        return list_room;
    }
    
    /**
     * 
     * Function returns venue_title
     * 
     * @return String
     */
    public String obtainVTitle() {
        
        return this.v_info.obtainTitle();
    }
    
    /**
     * 
     * Function appends begin and finish date specific to the room
     * 
     * @param begin
     * @param finish
     */
    public void amendChanges(LocalDate begin, LocalDate finish) {
        for(RoomInfo acc : list_room) {
            
            acc.addPeriod(begin, finish);
        }
    }
    
    /**
     * 
     * Function clears the begin and finish date specific to the room
     * 
     * @param begin
     * @param finish
     */
    public void revokeRes(LocalDate begin, LocalDate finish) {
        for(RoomInfo acc : list_room) {
            
            acc.remPeriod(begin, finish);
        }
    }
    
    /**
     * 
     * Function appends acc to list_room
     * 
     * @param acc
     */
    private void increaseRCount(RoomInfo acc) {
        
        list_room.add(acc);
    }
    
    /**
     * 
     * Function determines vacancy of venue.
     * The scan(capacity)Rooms returns a list of vacant rooms,
     * according to their capacity.
     * 
     * @param v_info
     * @return boolean
     */
    public boolean scanFree(VenueInfo v_info) {
        int unusedSmaRm = v_info.scanSRooms(begin, finish).size();
        int unusedMedRm = v_info.scanMRooms(begin, finish).size();
        int unusedLarRm = v_info.scanLRooms(begin, finish).size();
        
        if (s_room <= unusedSmaRm && m_room <= unusedMedRm && l_room <= unusedLarRm) {
            return true;
            
        } else {
            
            return false;
        }
    }
    
    /**
     * 
     * Computes tally of vacant rooms to list_room
     * 
     * @param totalUnusedRms
     * @param tally
     */
    public void scanFreeR(ArrayList<RoomInfo> totalUnusedRms, int tally) {
        int var = 0;
        
        if (var < tally) {
            for(RoomInfo acc: totalUnusedRms) {
                if (tally == var) {
                    
                    return;
                }
                
                increaseRCount(acc);
                
                var = (var + 1);
            }
        }
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
        
        Reservation other = (Reservation) body;
        
        return this.res_no.equals(other.res_no)  && 
               this.begin.isEqual(other.begin)   && 
               this.finish.isEqual(other.finish) && 
               this.s_room == other.s_room       && 
               this.m_room == other.m_room       && 
               this.l_room == other.l_room       && 
               this.list_room.equals(other.list_room);
    }
}