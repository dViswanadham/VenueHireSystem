package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;

public class Reservation {
	private String res_no;
	private LocalDate begin;
	private LocalDate finish;
	private int s_room;
	private int m_room;
	private int l_room;
	private VenueInfo v_info;
	private ArrayList<RoomInfo> list_room;
	
	public Reservation(String res_no, LocalDate begin, LocalDate finish, int s_room, int m_room, int l_room) {
		inputResNo(res_no);
		inputBeginDate(begin);
		inputFinishDate(finish);
		inputSRoom(s_room);
		inputMRoom(m_room);
		inputLRoom(l_room);
		list_room = new ArrayList<RoomInfo>();
	}
	/**
	 * @return the res_no
	 */
	public String obtainResNo() {
		return res_no;
	}
	/**
	 * @param res_no the res_no to set
	 */
	private void inputResNo(String res_no) {
		this.res_no = res_no;
	}
	/**
	 * @return the begin
	 */
	public LocalDate obtainBeginDate() {
		return begin;
	}
	/**
	 * @param begin the begin to set
	 */
	private void inputBeginDate(LocalDate begin) {
		this.begin = begin;
	}
	/**
	 * @return the finish
	 */
	public LocalDate obtainFinishDate() {
		return finish;
	}
	/**
	 * @param finish the finish to set
	 */
	private void inputFinishDate(LocalDate finish) {
		this.finish = finish;
	}
	/**
	 * @return the s_room
	 */
	public int obtainSRoom() {
		return s_room;
	}
	/**
	 * @param s_room the s_room to set
	 */
	private void inputSRoom(int s_room) {
		if (s_room < 0) s_room = 0;
		this.s_room = s_room;
	}
	/**
	 * @return the m_room
	 */
	public int obtainMRoom() {
		return m_room;
	}
	/**
	 * @param m_room the m_room to set
	 */
	private void inputMRoom(int m_room) {
		if (m_room < 0) m_room = 0;
		this.m_room = m_room;
	}
	/**
	 * @return the l_room
	 */
	public int obtainLRoom() {
		return l_room;
	}
	/**
	 * @param l_room the l_room to set
	 */
	private void inputLRoom(int l_room) {
		if (l_room < 0) l_room = 0;
		this.l_room = l_room;
	}
	/**
	 * @return the v_info
	 */
	public VenueInfo obtainVInfo() {
		return v_info;
	}
	/**
	 * @param v_info the v_info to set
	 */
	public void inputVInfo(VenueInfo v_info) {
		this.v_info = v_info;
	}
	/**
	 * @return the list_room
	 */
	public ArrayList<RoomInfo> obtainListRooms() {
		return list_room;
	}
	/**
	 * @return v_info name
	 */
	public String obtainVTitle() {
		return this.v_info.obtainTitle();
	}
	/**
	 * Add room to list_room
	 * @param room
	 */
	private void increaseRCount(RoomInfo room) {
		list_room.add(room);
	}
	/**
	 * Check availability of v_info
	 * calls search<size>RoomInfo which 
	 * return list of free rooms according to size
	 * @param v_info
	 * @return boolean
	 */
	public boolean scanFree(VenueInfo v_info) {
		int freeSmallRooms = v_info.scanSRooms(begin, finish).size();
		int freeMediumRooms = v_info.scanMRooms(begin, finish).size();
		int freeLargeRooms = v_info.scanLRooms(begin, finish).size();
		
		if (s_room <= freeSmallRooms && m_room <= freeMediumRooms && l_room <= freeLargeRooms) return true;
		else return false;
	}
	/**
	 * Add count of room
	 * into list_room 
	 * @param freeRoomList
	 * @param count
	 */
	public void scanFreeR(ArrayList<RoomInfo> freeRoomList, int count) {
		int i = 0;
		if (i < count) {
			for (RoomInfo r: freeRoomList) {
				if (count == i) return;
				increaseRCount(r);
				i++;
			}
		}	
	}
	/**
	 * Add begin and finish date
	 * to room's dateList
	 * @param begin
	 * @param finish
	 */
	public void amendChanges(LocalDate begin, LocalDate finish) {
		for (RoomInfo r : list_room) {
			r.addPeriod(begin, finish);
		}
	}
	/**
	 * Remove begin and finish date
	 * to room's dateList
	 * @param begin
	 * @param finish
	 */
	public void revokeRes(LocalDate begin, LocalDate finish) {
		for (RoomInfo r : list_room) {
			r.remPeriod(begin, finish);
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// DONE Auto-generated method stub
		if (this == obj) return true;
		if (obj == null) return false;
		if (this.getClass() != obj.getClass()) return false;
		Reservation otherObj = (Reservation) obj;
		return this.res_no.equals(otherObj.res_no)
				&& this.begin.isEqual(otherObj.begin) 
				&& this.finish.isEqual(otherObj.finish) 
				&& this.s_room == otherObj.s_room
				&& this.m_room == otherObj.m_room
				&& this.l_room == otherObj.l_room
				&& this.list_room.equals(otherObj.list_room);
	}
	
}
