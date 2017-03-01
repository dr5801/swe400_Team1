package domain;
import static org.junit.Assert.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import enums.PowerTools;
import exceptions.ItemNotFoundException;
import other.DBTest;

/**
 * @author Drew Rife & Alec Waddelow
 *
 * Test class for the PowerTool class
 */
public class TestPowerTool extends DBTest
{
	int loadCounter = 0;
	
	/**
	 * MockPowerTool class for testing 
	 *
	 */
	public class MockPowerTool extends PowerTool
	{
		public MockPowerTool(int i) throws ClassNotFoundException, SQLException, ItemNotFoundException 
		{
			super(i);
			
		}
		
		public MockPowerTool() 
		{
			super();
		}

		@Override
		public void load() throws ClassNotFoundException, SQLException, ItemNotFoundException
		{
			TestPowerTool.this.loadCounter++;
			super.load();
		}
	}
	
	
	/**
	 * Tests creating a new object PowerTool
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void testCreationConstructor() throws ClassNotFoundException, SQLException 
	{
		String upc = "323122222";
		int manufacturerID = 555;
		int price = 02;
		String description = "This is a test PowerTool";
		boolean batteryPowered = true;
		
		PowerTool powerTool = new PowerTool(upc, manufacturerID, price, description, batteryPowered, "PowerTool");
		assertEquals(upc, powerTool.getUpc());
		assertEquals(manufacturerID, powerTool.getManufacturerID());
		assertEquals(price, powerTool.getPrice());
		assertEquals(description, powerTool.getDescription());
		assertTrue(powerTool.isBatteryPowered());
		assertEquals("PowerTool", powerTool.getClassName());
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
		PowerTool powerTool = new PowerTool(16);
		
		assertEquals(PowerTools.HITACHI_PNEUMATIC_NAILER.getUpc(), powerTool.getUpc());
		assertEquals(PowerTools.HITACHI_PNEUMATIC_NAILER.getManufacturerID(), powerTool.getManufacturerID());
		assertEquals(PowerTools.HITACHI_PNEUMATIC_NAILER.getPrice(), powerTool.getPrice());
		assertEquals(PowerTools.HITACHI_PNEUMATIC_NAILER.getDescription(), powerTool.getDescription());
		assertFalse(powerTool.isBatteryPowered());
		assertEquals("PowerTool", powerTool.getClassName());
	}
	
	/**
	 * Tests Setters 
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void testSetters() throws ClassNotFoundException, SQLException
	{
		PowerTool powerTool = new PowerTool(null, 0, 0, null, false, null);
		
		powerTool.setUpc("101");
		powerTool.setManufacturerID(10);
		powerTool.setPrice(15);
		powerTool.setDescription("test");
		powerTool.setBatteryPowered(true);
		powerTool.setClassName("PowerTool");
		powerTool.setId(1);
		
		assertEquals("101", powerTool.getUpc());
		assertEquals(10, powerTool.getManufacturerID());
		assertEquals(15, powerTool.getPrice());
		assertEquals("test", powerTool.getDescription());
		assertEquals(true, powerTool.isBatteryPowered());
		assertEquals("PowerTool", powerTool.getClassName());
		assertEquals(1, powerTool.getId());
	}
	
	/**
	 * Tests catching ClassNotFoundException
	 * 
	 * @throws SQLException
	 * @throws ItemNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	@Test 
	public void testPowerToolNotFoundException() throws SQLException, ItemNotFoundException, ClassNotFoundException
	{
		new PowerTool(25);
	}
	
	/**
	 * Tests toString()
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ItemNotFoundException 
	 */
	@Test
	public void testToString() throws ClassNotFoundException, SQLException, ItemNotFoundException
	{
		PowerTool powerTool = new PowerTool(16);
		assertEquals("PowerTool [upc=1231231234, manufacturerID=13, price=39900, description=Pheumatic Nail Gun, batteryPowered=false]", powerTool.toString());
	}
	
	/**
	 * Tests getting list of StripNails lazyLoad method 
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ItemNotFoundException 
	 */
	@Test
	public void testGetList() throws ClassNotFoundException, SQLException, ItemNotFoundException
	{
		MockPowerTool testClass = new MockPowerTool(16);
		HashMap<Integer, StripNail> myList = testClass.getStripNailList();
		
		/*Asserts that load method is called upon invoking getStripNailList()  */
		assertEquals(1, this.loadCounter);			
		
		HashMap<Integer, StripNail> newStripList = new HashMap<Integer, StripNail>();
		newStripList.put(11, new StripNail(11));
		newStripList.put(12, new StripNail(12));
		newStripList.put(13, new StripNail(13));
		newStripList.put(14, new StripNail(14));
		newStripList.put(15, new StripNail(15));
		
		for(Integer key : myList.keySet())
		{
			assertEquals(myList.get(key).getId(), newStripList.get(key).getId());
			assertEquals(myList.get(key).getUpc(), newStripList.get(key).getUpc());
			assertEquals(myList.get(key).getManufacturerID(), newStripList.get(key).getManufacturerID());
			assertEquals(myList.get(key).getPrice(), newStripList.get(key).getPrice());
			assertEquals(myList.get(key).getLength(), newStripList.get(key).getLength(), 0.0001);
			assertEquals(myList.get(key).getNumberInStrip(), newStripList.get(key).getNumberInStrip());
			assertEquals(myList.get(key).getClassName(), newStripList.get(key).getClassName());
			
		}
	}
	
	/**
	 * Tests adding StripNail to list 
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ItemNotFoundException 
	 */
	@Test
	public void addStripNailToList() throws ClassNotFoundException, SQLException, ItemNotFoundException
	{
		MockPowerTool pt = new MockPowerTool(16);
		StripNail snAdd = new StripNail(11);
		
		pt.addStripNailToList(snAdd);
		HashMap<Integer, StripNail> myList = pt.getStripNailList();
		
		StripNail sn = new StripNail(11);
		StripNail sn2 = new StripNail(12);
		
		HashMap<Integer, StripNail> newStripList = new HashMap<Integer, StripNail>();
		newStripList.put(sn.getId(), sn);
		newStripList.put(sn2.getId(), sn2);
		
		for(Integer key : myList.keySet())
		{
			assertEquals(myList.get(key).getUpc(), newStripList.get(key).getUpc());
			assertEquals(myList.get(key).getManufacturerID(), newStripList.get(key).getManufacturerID());
			assertEquals(myList.get(key).getPrice(), newStripList.get(key).getPrice());
			assertEquals(myList.get(key).getLength(), newStripList.get(key).getLength(), 0.001);
			assertEquals(myList.get(key).getNumberInStrip(), newStripList.get(key).getNumberInStrip());
			assertEquals(myList.get(key).getClassName(), newStripList.get(key).getClassName());
		}
	}
	
	/**
	 * Tests empty constructor  
	 */
	@Test
	public void testEmptySuper()
	{
		MockPowerTool test = new MockPowerTool();
		assertTrue(test instanceof PowerTool);
	}
	
	/**
	 * Tests removing a stripnail
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ItemNotFoundException 
	 */
	@Test
	public void testRemoveStripNail() throws ClassNotFoundException, SQLException, ItemNotFoundException
	{
		PowerTool powerTool = new PowerTool(21);
		HashMap<Integer, StripNail> stripNails = powerTool.getStripNailList();
		assertEquals(2, stripNails.size());
		
		StripNail stripNail = stripNails.get(15);
		powerTool.removeStripNailFromList(stripNail);
		
		stripNails = powerTool.getStripNailList();
		assertEquals(1, stripNails.size());
	}
}