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

import java.util.Iterator;
import java.util.ArrayList;
import java.time.LocalDate;

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
     * 
     * Function returns room_title
     * 
     * @return String
     */
    public String obtainTitle() {
        
        return room_title;
    }
    
    /**
     * 
     * Function sets room_title
     * 
     * @param room_title
     */
    private void inputTitle(String room_title) {
        
        this.room_title = room_title;
    }
    
    /**
     * 
     * Function returns room_capacity (small, medium or large)
     * 
     * @return String
     */
    public String obtainCap() {
        
        return room_capacity;
    }
    
    /**
     * 
     * Function sets room_capacity
     * 
     * @param room_capacity
     */
    private void inputCap(String room_capacity) {
        
        this.room_capacity = room_capacity;
    }
    
    /**
     * 
     * Function appends begin and finish date to list_period
     * 
     * @param begin
     * @param finish
     */
    public void addPeriod(LocalDate begin, LocalDate finish) {
        
        list_period.add(begin);
        list_period.add(finish);
    }
    
    /**
     * 
     * Function clears begin and finish date from list_period
     * 
     * @param begin
     * @param finish
     */
    public void remPeriod(LocalDate begin, LocalDate finish) {
        Iterator<LocalDate> pointer = list_period.iterator();
        
        while(pointer.hasNext()) {
            LocalDate temp = pointer.next();
            
            if (begin.isEqual(temp) || finish.isEqual(temp)) {
                
                pointer.remove();
            }
        } 
    }
    
    /**
     * 
     * Function analyses relative begin and finish dates in list period
     * 
     * @param begin
     * @param finish
     * @return boolean
     */
    public boolean scanPeriod(LocalDate begin, LocalDate finish) {
        if (! list_period.isEmpty()) {
            int a = 0;
            
            while(a < list_period.size()) {
                int b = (a + 1);
                
                if (begin.isAfter(list_period.get(a)) && 
                    begin.isBefore(list_period.get(b))) {
                    
                    return false;
                }
                
                if (finish.isAfter(list_period.get(a)) && 
                    finish.isBefore(list_period.get(b))) {
                    
                    return false;
                }
                
                if (begin.isEqual(list_period.get(a)) || 
                    begin.isEqual(list_period.get(b))) {
                    
                    return false;
                }
                
                if (finish.isEqual(list_period.get(a)) || 
                    finish.isEqual(list_period.get(b))) {
                    
                    return false;
                }
                
                if (list_period.get(a).isAfter(begin) && 
                    list_period.get(b).isBefore(finish)) {
                    
                    return false;
                }
                
                a = (b + 1);
            }
        }
        
        return true;
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
        
        RoomInfo other = (RoomInfo) body;
        
        return this.room_title.equals(other.room_title) && 
               this.room_capacity.equals(other.room_capacity);
    }
}