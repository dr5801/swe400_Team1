package other;
import static org.junit.Assert.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.NamingException;
import org.junit.Test;
import data_source.CreateInventoryItemTable;
import data_source.CreateLinkTable;
import domain.InventoryItem;
import runner.*;
import enums.InsertEnumData;
import enums.Nails;
import enums.PowerTools;
import enums.StripNails;
import enums.Tools;
import exceptions.ItemNotFoundException;

/**
 * @author Alec Waddelow & Drew Rife
 * 
 *	Tests for creating a new ArrayList of objects pulled from the database
 */
public class TestArrayList
{
	private static int uniqueTestID = 1;
	private static int indexOfArrayList = 0;

	/**
	 * Tests retrieving InventoryItems from the arraylist
	 *
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws NamingException
	 * @throws ItemNotFoundException 
	 */
	@Test
	public void testInventoryItemsInArrayList() throws ClassNotFoundException, SQLException, NamingException, ItemNotFoundException
	{
		CreateLinkTable.dropTableBeforeCreation();
		CreateInventoryItemTable.dropTableBeforeCreation();
		CreateInventoryItemTable.createTable();
		InsertEnumData.insertNailsIntoTable();
		InsertEnumData.insertToolsIntoTable();
		InsertEnumData.insertStripNailsIntoTable();
		InsertEnumData.insertPowerToolsIntoTable();
		Runner.createList();
		CreateLinkTable.createTable();
		CreateLinkTable.createRelationships();
		testRetrieveNailsFromArrayList();
		testRetrieveToolsFromArrayList();
		testRetrieveStripNailsFromArrayList();
		testRetrievePowerToolsFromArrayList();
	}

	/**
	 * Tests getting the nail objects from the array list
	 *
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void testRetrieveNailsFromArrayList() throws ClassNotFoundException, SQLException
	{
		HashMap<Integer, InventoryItem> returnSet = Runner.getList();
		
		for(Nails nail : Nails.values())
		{
			assertEquals(nail.getUpc(), returnSet.get(uniqueTestID).getUpc());
			assertEquals(nail.getManufacturerID(), returnSet.get(uniqueTestID).getManufacturerID());
			assertEquals(nail.getPrice(), returnSet.get(uniqueTestID).getPrice());
			uniqueTestID++;
		}
	}

	/**
	 * Tests getting the Tools objects from the array list
	 *
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void testRetrieveToolsFromArrayList() throws ClassNotFoundException, SQLException
	{
		HashMap<Integer, InventoryItem> returnSet = Runner.getList();
		
		for(Tools tool : Tools.values())
		{
			assertEquals(tool.getUpc(), returnSet.get(uniqueTestID).getUpc());
			assertEquals(tool.getManufacturerID(), returnSet.get(uniqueTestID).getManufacturerID());
			assertEquals(tool.getPrice(), returnSet.get(uniqueTestID).getPrice());
			uniqueTestID++;
		}
	}

	/**
	 * Tests getting the Strip Nails objects from the array list
	 *
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void testRetrieveStripNailsFromArrayList() throws ClassNotFoundException, SQLException
	{
		HashMap<Integer, InventoryItem> returnSet = Runner.getList();
		
		for(StripNails stripNail : StripNails.values())
		{
			assertEquals(stripNail.getUpc(), returnSet.get(uniqueTestID).getUpc());
			assertEquals(stripNail.getManufacturerID(), returnSet.get(uniqueTestID).getManufacturerID());
			assertEquals(stripNail.getPrice(), returnSet.get(uniqueTestID).getPrice());
			uniqueTestID++;
		}
	}

	/**
	 * Tests getting the Strip Nails objects from the array list
	 *
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void testRetrievePowerToolsFromArrayList() throws ClassNotFoundException, SQLException
	{
		HashMap<Integer, InventoryItem> returnSet = Runner.getList();

		for(PowerTools powerTool : PowerTools.values())
		{
			assertEquals(powerTool.getUpc(), returnSet.get(uniqueTestID).getUpc());
			assertEquals(powerTool.getManufacturerID(), returnSet.get(uniqueTestID).getManufacturerID());
			assertEquals(powerTool.getPrice(), returnSet.get(uniqueTestID).getPrice());
			uniqueTestID++;
		}
	}
}