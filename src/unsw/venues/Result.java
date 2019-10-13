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
import java.time.LocalDate;
import org.json.JSONObject;

public class Result {
    private Result() {}
    
    /*
     * Static variable sole_inst which has type Result
     */
    private static Result sole_inst = null;
    
    /**
     * 
     * Function outputs the Object
     * 
     * @param msg
     */
    public void printObj(JSONObject msg) {
        
        System.out.println(msg.toString());
    }
    
    /**
     * 
     * Function outputs the Array
     * 
     * @param msg
     */
    public void printArr(JSONArray msg) {
        
        System.out.println(msg.toString());
    }
    
    /**
     * 
     * Function makes an instance of sole_inst
     * 
     * @return Result
     */
    public static Result obtainStill() {
        if (sole_inst == null) {
            
            sole_inst = new Result();
        }
        
        return sole_inst;
    }
    
    /**
     * 
     * Function adds information to valid Array
     * 
     * @param printArr
     * @param info
     * @return JSONArray
     */
    public JSONArray editLoc(JSONArray printArr, String info) {
        printArr.put(info);
        
        return printArr;
    }
    
    /**
     * 
     * Functions adds info to valid Object with location.
     * 
     * @param msg
     * @param loc
     * @param info
     * @return JSONObject
     */
    public JSONObject editLoc(JSONObject msg, String loc, String info) {
        msg.put(loc, info);
        
        return msg;
    }
    
    /**
     * 
     * Function adds info (date) to valid Object with location.
     * 
     * @param msg
     * @param loc
     * @param info
     * @return JSONObject
     */
    public JSONObject editLoc(JSONObject msg, String loc, LocalDate info) {
        msg.put(loc, info);
        
        return msg;
    }
    
    /**
     * 
     * Function constructs an Object with a confirmation msg.
     * 
     * @return JSONObject
     */
    public JSONObject confirmInput() {
        JSONObject confirmation = new JSONObject();
        confirmation.put("status", "success");
        
        return confirmation;
    }
    
    /**
     * 
     * Function appends the confirmation msg to the Object.
     * 
     * @param msg
     * @return JSONObject
     */
    public JSONObject confirmInput(JSONObject msg) {
        msg.put("status", "success");
        
        return msg;
    }
    
    /**
     * 
     * Function adds Object to valid Array.
     * 
     * @param printArr
     * @param printObj
     * @return JSONArray
     */
    public JSONArray insertObj(JSONArray printArr, JSONObject printObj) {
        printArr.put(printObj);
        
        return printArr;
    }
    
    /**
     * 
     * Functions adds Array to valid Object with location.
     * 
     * @param printObj
     * @param loc
     * @param printArr
     * @return JSONObject
     */
    public JSONObject insertArr(JSONObject printObj, String loc, 
                                JSONArray printArr) {
        printObj.put(loc, printArr);
        
        return printObj;
    }
    
    /**
     * 
     * Function constructs an Object with a failure msg.
     * 
     * @return JSONObject
     */
    public JSONObject failureInput() {
        JSONObject failure = new JSONObject();
        failure.put("status", "rejected");
        
        return failure;
    }
}