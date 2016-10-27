package domain_layer;

/**
 * @author Drew Rife & Alec Waddelow
 *
 * Fields that all data mappers will share 
 */
public abstract class DBMapper
{
	protected int id;
	protected String upc;
	protected int manufacturerID;
	protected int price;
	protected String className;

	/**
	 * Creates the DBMapper object
	 *
	 * @param id
	 * @param upc
	 * @param manufacturerID
	 * @param price
	 * @param className
	 */
	public DBMapper(int id, String upc, int manufacturerID, int price, String className)
	{
		this.id = id;
		this.upc = upc;
		this.manufacturerID = manufacturerID;
		this.price = price;
		this.className = className;
	}

	/**
	 * Constructor that calls the generic Object.class's constructor
	 */
	public DBMapper()
	{
		super();
	}

	/**
	 * @return the item's ID
	 */
	public int getId()
	{
		return this.id;
	}

	/**
	 * sets the item's ID
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * @return the item's UPC
	 */
	public String getUpc()
	{
		return this.upc;
	}

	/**
	 * set the item's UPC
	 * @param upc
	 */
	public void setUpc(String upc)
	{
		this.upc = upc;
	}

	/**
	 * @return the item's manufacturer ID
	 */
	public int getManufacturerID()
	{
		return this.manufacturerID;
	}

	/**
	 * set the item's manufacturer ID
	 * @param manufacturerID
	 */
	public void setManufacturerID(int manufacturerID)
	{
		this.manufacturerID = manufacturerID;
	}

	/**
	 * @return the item's price
	 */
	public int getPrice()
	{
		return this.price;
	}

	/**
	 * sets the item's price
	 * @param price
	 */
	public void setPrice(int price)
	{
		this.price = price;
	}

	/**
	 * @return the item's class name
	 */
	public String getClassName()
	{
		return this.className;
	}

	/**
	 * set the item's class name
	 * @param className
	 */
	public void setClassName(String className)
	{
		this.className = className;
	}
}
