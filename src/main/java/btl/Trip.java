package btl;

import java.sql.Date;

import lombok.Data;

@Data
public class Trip {
	private long id;
	private Employee driver;
	private Employee subdriver;
	private Car car;
	private Route route;
	private int numberofpassengers;
	private float price;
	private Date createdat;
}