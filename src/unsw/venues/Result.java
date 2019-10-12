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

import org.json.JSONArray;
import org.json.JSONObject;

public class Result {
	/**
	 * Static variable sole_inst 
	 * of type Result
	 */
	private static Result sole_inst = null;
	
	private Result() {
	}
	/**
	 * Create instance of singleton class 
	 * @return sole_inst
	 */
	public static Result obtainStill() {
		if (sole_inst == null) sole_inst = new Result();
		return sole_inst;
	}
	/**
	 * Add a JSONObject to 
	 * exisiting JSONArray
	 * @param printArr
	 * @param printObj
	 * @return JSONArray
	 */
	public JSONArray insertObj(JSONArray printArr, JSONObject printObj) {
		printArr.put(printObj);
		return printArr;
	}
	/**
	 * Add a JSONArray to
	 * exisiting JSONObject with field
	 * @param printObj
	 * @param field
	 * @param printArr
	 * @return JSONObject
	 */
	public JSONObject insertArr(JSONObject printObj, String field, JSONArray printArr) {
		printObj.put(field, printArr);
		return printObj;
	}
	/**
	 * Add a String to
	 * existing JSONArray
	 * @param printArr
	 * @param data
	 * @return JSONArray
	 */
	public JSONArray editLoc(JSONArray printArr, String data) {
		printArr.put(data);
		return printArr;
	}
	/**
	 * Add a String to
	 * existing JSONObject with field
	 * @param statement
	 * @param field
	 * @param data
	 * @return JSONObject
	 */
	public JSONObject editLoc(JSONObject statement, String field, String data) {
		statement.put(field, data);
		return statement;
	}
	/**
	 * Add a LocalDate to
	 * existing JSONObject with field
	 * @param statement
	 * @param field
	 * @param data
	 * @return JSONObject
	 */
	public JSONObject editLoc(JSONObject statement, String field, LocalDate data) {
		statement.put(field, data);
		return statement;
	}
	/**
	 * Create JSONObject with success message
	 * @return JSONObject
	 */
	public JSONObject confirmInput() {
		JSONObject success = new JSONObject();
		success.put("status", "success");
		return success;
	}
	/**
	 * Add success message to
	 * exisiting JSONObject
	 * @param statement
	 * @return JSONObject
	 */
	public JSONObject confirmInput(JSONObject statement) {
		statement.put("status", "success");
		return statement;
	}
	/**
	 * Create JSONObject with error message
	 * @return JSONObject
	 */
	public JSONObject failureInput() {
		JSONObject error = new JSONObject();
		error.put("status", "rejected");
		return error;
	}
	/**
	 * Print JSONObject
	 * @param statement
	 */
	public void printObj(JSONObject statement) {
		System.out.println(statement.toString());
	}
	/**
	 * Print JSONArray
	 * @param statement
	 */
	public void printArr(JSONArray statement) {
		System.out.println(statement.toString());
	}
}
