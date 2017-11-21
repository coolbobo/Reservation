package model;

import java.util.Date;

/**
 * 预约的记录信息
 * 
 * @author coolbobo
 * 
 */
public class ReservationBean extends BaseRecordHisBean
{

	// 日期
	private String reservationDate;
	

	private Integer employeID;
	// 消费套餐ID
	private Integer consumeID;

	

	

	public String getReservationDate()
	{
		return reservationDate;
	}

	public void setReservationDate(String reservationDate)
	{
		this.reservationDate = reservationDate;
	}

	public void setEmployeID(Integer employeID)
	{
		this.employeID = employeID;
	}

	public Integer getEmployeID()
	{
		return employeID;
	}

	public void setEmployeeID(Integer employeID)
	{
		this.employeID = employeID;
	}

	public Integer getConsumeID()
	{
		return consumeID;
	}

	public void setConsumeID(Integer consumeID)
	{
		this.consumeID = consumeID;
	}
}
