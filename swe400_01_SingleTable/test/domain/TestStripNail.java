package domain;
import static org.junit.Assert.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import domain.PowerTool;
import domain.StripNail;
import enums.StripNails;
import exceptions.ItemNotFoundException;
import other.DBTest;

/**
 * @author Drew Rife & Alec Waddelow
 *
 * Test class for StripNail class
 */
public class TestStripNail extends DBTest
{
	int loadCounter = 0;
	
	/**
	 * Mock stripNail class for testing 
	 *
	 */
	public class MockStripNail extends StripNail
	{
		public MockStripNail(int i) throws ClassNotFoundException, SQLException, ItemNotFoundException 
		{
			super(i);
		}
		
		public MockStripNail() 
		{
			super();
		}

		@Override
		public void load() throws ClassNotFoundException, SQLException, ItemNotFoundException
		{
			TestStripNail.this.loadCounter++;
			super.load();
		}
	}
	
	/**
	 * Tests creating a new object StripNail
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void testCreationConstructor() throws ClassNotFoundException, SQLException 
	{
		String upc = "1324567890";
		int manufacturerID = 32020;
		int price = 30;
		double length = 20.325;
		int numberInStrip = 24;
		
		StripNail stripNail = new StripNail(upc, manufacturerID, price, length, numberInStrip, "StripNail");
		assertEquals(upc, stripNail.getUpc());
		assertEquals(manufacturerID, stripNail.getManufacturerID());
		assertEquals(price, stripNail.getPrice());
		assertEquals(length, stripNail.getLength(), 0.001);
		assertEquals(numberInStrip, stripNail.getNumberInStrip());
		assertEquals("StripNail", stripNail.getClassName());
	}
	
	/**
	 * Tests finder constructor 
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ItemNotFoundException 
	 */
	@Test
	public void testFinderConstructor() throws ClassNotFoundException, SQLException, ItemNotFoundException
	{
		StripNail stripNail = new StripNail(11);
		assertEquals(StripNails.ROUND_HEAD_NAIL_STRIP.getUpc(), stripNail.getUpc());
		assertEquals(StripNails.ROUND_HEAD_NAIL_STRIP.getManufacturerID(), stripNail.getManufacturerID());
		assertEquals(StripNails.ROUND_HEAD_NAIL_STRIP.getPrice(), stripNail.getPrice());
		assertEquals(StripNails.ROUND_HEAD_NAIL_STRIP.getLength(), stripNail.getLength(), 0.001);
		assertEquals(StripNails.ROUND_HEAD_NAIL_STRIP.getNumberInStrip(), stripNail.getNumberInStrip());
		assertEquals("StripNail", stripNail.getClassName());
	}
	
	/**
	 * Tests setters 
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void testSetters() throws ClassNotFoundException, SQLException
	{
		StripNail stripNail = new StripNail(null, 0, 0, 0, 0, null);
		stripNail.setUpc("101");
		stripNail.setManufacturerID(10);
		stripNail.setPrice(15);
		stripNail.setLength(10);
		stripNail.setNumberInStrip(10);
		stripNail.setClassName("StripNail");
		
		assertEquals("101", stripNail.getUpc());
		assertEquals(10, stripNail.getManufacturerID());
		assertEquals(15, stripNail.getPrice());
		assertEquals(10.0, stripNail.getLength(), 0.001);
		assertEquals(10, stripNail.getNumberInStrip());
		assertEquals("StripNail", stripNail.getClassName());
	}
	
	/**
	 * Tests catching ClassNotFoundException
	 * 
	 * @throws SQLException
	 * @throws ItemNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	@Test 
	public void testStripNailNotFoundException() throws SQLException, ItemNotFoundException, ClassNotFoundException
	{
		
			new StripNail(30);
		
	}
	
	/**
	 * Tests toString 
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ItemNotFoundException 
	 */
	@Test
	public void testToString() throws ClassNotFoundException, SQLException, ItemNotFoundException
	{
		StripNail stripNail = new StripNail(11);
		assertEquals("StripNail [upc=5453432345, manufacturerID=13, price=1099, length=2.5, numberInStrip=50]", stripNail.toString());
	}
	
	/**
	 * Tests getList lazy load method 
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ItemNotFoundException 
	 */
	@Test
	public void testGetList() throws ClassNotFoundException, SQLException, ItemNotFoundException
	{
		MockStripNail stripNail = new MockStripNail(11);
		HashMap<Integer, PowerTool> myList = stripNail.getPowerToolList();
		
		/*Asserts that load method is called upon invoking getStripNailList()  */
		assertEquals(1, this.loadCounter);			
		
		HashMap<Integer, PowerTool> newList = new HashMap<Integer, PowerTool>();
		newList.put(16, new PowerTool(16));
		newList.put(17, new PowerTool(17));
		
		for(Integer key : myList.keySet())
		{
			assertEquals(myList.get(key).getUpc(), newList.get(key).getUpc());
			assertEquals(myList.get(key).getManufacturerID(), newList.get(key).getManufacturerID());
			assertEquals(myList.get(key).getPrice(), newList.get(key).getPrice());
			assertEquals(myList.get(key).getDescription(), newList.get(key).getDescription());
			assertFalse(myList.get(key).isBatteryPowered());
			assertEquals(myList.get(key).getClassName(), newList.get(key).getClassName());
		}
	}
	
	/**
	 * Tests adding a powerTool to list
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ItemNotFoundException 
	 */
	@Test
	public void addPowerToolToList() throws ClassNotFoundException, SQLException, ItemNotFoundException
	{
		MockStripNail stripNail = new MockStripNail(11);
		PowerTool ptAdd = new PowerTool(16);
		
		stripNail.addPowerToolToList(ptAdd);
		HashMap<Integer, PowerTool> myList = stripNail.getPowerToolList();
		
		PowerTool pt = new PowerTool(16);
		PowerTool pt2 = new PowerTool(17);
		
		HashMap<Integer, PowerTool> newList = new HashMap<Integer, PowerTool>();
		newList.put(pt.getId(), pt);
		newList.put(pt2.getId(), pt2);
		newList.put(ptAdd.getId(), ptAdd);
	
		for(Integer key : myList.keySet())
		{
			assertEquals(myList.get(key).getUpc(), newList.get(key).getUpc());
			assertEquals(myList.get(key).getManufacturerID(), newList.get(key).getManufacturerID());
			assertEquals(myList.get(key).getPrice(), newList.get(key).getPrice());
			assertEquals(myList.get(key).getDescription(), newList.get(key).getDescription());
			assertFalse(myList.get(key).isBatteryPowered());
			assertEquals(myList.get(key).getClassName(), newList.get(key).getClassName());
		}
	}
	
	/**
	 * Tests empty constructor 
	 */
	@Test
	public void testEmptySuper()
	{
		MockStripNail test = new MockStripNail();
		assertTrue(test instanceof StripNail);
	}
	
	/**
	 * Tests removing a powerTool
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ItemNotFoundException 
	 */
	@Test
	public void testRemovePowerTool() throws ClassNotFoundException, SQLException, ItemNotFoundException
	{
		StripNail stripNail = new StripNail(11);
		HashMap<Integer, PowerTool> powerTools = stripNail.getPowerToolList();
		assertEquals(2, powerTools.size());
		
		PowerTool powerTool = powerTools.get(16);
		stripNail.removePowerToolFromList(powerTool);
		
		powerTools = stripNail.getPowerToolList();
		assertEquals(1, powerTools.size());
	}
	
}