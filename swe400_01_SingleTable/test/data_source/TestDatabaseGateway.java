package data_source;
import static org.junit.Assert.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import data_source.InventoryItemGateway;

/**
 * @author Alec Waddelow and Drew Rife 
 *
 * Test DatabaseGateway
 */
public class TestDatabaseGateway 
{

	/**
	 * Test Creation of DatabaseGateway 
	 */
	@Test
	public void testCreation() 
	{
		InventoryItemGateway dg = new InventoryItemGateway();
		assertTrue(dg instanceof InventoryItemGateway);
	}
	
	/**
	 * Tests UPCs of PowerTools
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void testGettingPowerToolUPCs() throws ClassNotFoundException, SQLException
	{
		List<DataTransferObject> dtoList = InventoryItemGateway.getAllPowerTools();
		String[] upcArray = {"1231231234", "4445553333", "7657896543", "9993458585", "7654564848", "7784452828"};
	
		int i = 0;
		for(DataTransferObject dto : dtoList)
		{
			assertEquals(upcArray[i], dto.getUpc());
			i++;
		}
	}
	
	/**
	 * Tests UPCS of StripNails
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void testGettingStripNailUPCs() throws ClassNotFoundException, SQLException
	{
		List<DataTransferObject> dtoList = InventoryItemGateway.getAllStripNails();
		String[] upcArray = {"5453432345", "4343434543", "9876784727", "6565459876", "4343432345"};
	
		int i = 0;
		for(DataTransferObject dto : dtoList)
		{
			assertEquals(upcArray[i], dto.getUpc());
		}
	}
	
	/**
	 * Tests for inputing a bad UPC that isn't in the database
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void testBadUPCInput() throws ClassNotFoundException, SQLException
	{
		ResultSet rSet = InventoryItemGateway.retrieveUPC("000000000000000", "nail");
		assertFalse(rSet.next());
		rSet.close();
		InventoryItemGateway.closeStatements();
	}
}