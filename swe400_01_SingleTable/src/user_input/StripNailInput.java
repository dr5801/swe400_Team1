package user_input;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import data_source.*;
import domain.*;
import exceptions.ItemNotFoundException;

/**
 * @author Drew Rife and Alec Waddelow 
 * 
 * StripNail Input class 
 *
 */
public class StripNailInput 
{
	/**
	 * Creates a new StripNail and adds to the Database 
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ItemNotFoundException 
	 */
	public static void createStripNail(Scanner sc) throws ClassNotFoundException, SQLException, ItemNotFoundException 
	{
		System.out.println("Please enter UPC code \n");
		String upc = sc.nextLine();
		
		System.out.println("Please enter Manufacturer ID  \n");
		String manufacturerID = sc.nextLine();
		int manufacturerIDParse = Integer.parseInt(manufacturerID);
		
		System.out.println("Please enter Price \n");
		String price = sc.nextLine();
		int priceParse = Integer.parseInt(price);
		
		System.out.println("Please enter length \n");
		String length = sc.nextLine();
		double lengthParse = Double.parseDouble(length);
		
		System.out.println("Please enter number in strip \n");
		String numberInStrip = sc.nextLine();
		int numberInStripParse = Integer.parseInt(numberInStrip);
	
		StripNail stripNail = new StripNail(upc, manufacturerIDParse, priceParse, lengthParse, numberInStripParse, "StripNail");
		
		stripNailRelationPrompt(sc, stripNail);
		
		System.out.println("Item added");
		System.out.println(stripNail.toString());
	}
	
	/**
	 * Updates a stripnail object
	 * 
	 * @param sc
	 * @param item
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 * @throws ItemNotFoundException 
	 */
	public static void updateStripNail(Scanner sc, int item) throws SQLException, ClassNotFoundException, ItemNotFoundException 
	{
		StripNail stripNail = (StripNail) UserInput.validUPCRequest(sc, item);
		
		System.out.println("\nWarning... You are about to update this item, if you don't want certain values to change, retype the same value");
		
		System.out.println("Plase enter the UPC:");
		String upc = sc.nextLine();
		
		System.out.println("Please enter the manufacturerID:");
		String manufacturerID = sc.nextLine();
		int manufacturerIDParse = Integer.parseInt(manufacturerID);
		
		System.out.println("Please enter the price of the item:");
		String price = sc.nextLine();
		int priceParse = Integer.parseInt(price);
		
		System.out.println("Please enter length \n");
		String length = sc.nextLine();
		double lengthParse = Double.parseDouble(length);
		
		System.out.println("Please enter Number in Strip \n");
		String numberInStrip = sc.nextLine();
		int numberInStripParse = Integer.parseInt(numberInStrip);
		
		stripNail.update(upc, manufacturerIDParse, priceParse, lengthParse, numberInStripParse);
		
		boolean valid = false;
		while(!valid)
		{
			System.out.println("Would you like to ");
			System.out.println("1. Add a compatible powertool?");
			System.out.println("2. Remove a compatible powertool?");
			System.out.println("3. Go back to main prompt");
			String input = sc.nextLine();
			
			HashMap<Integer, Runnable> command = new HashMap<Integer, Runnable>();
			command.put(1, ()->{
				try {
					UserInput.addCompatibles(sc, stripNail);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ItemNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
			command.put(2, ()->{
				try {
					removeCompatibilities(sc, stripNail);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ItemNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
			if(Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= 3)
			{
				if(Integer.parseInt(input) != 3)
				{
					command.get(Integer.parseInt(input)).run();
				}
				valid = true;
			}
			else
			{
				System.out.println("Error: Not a valid option\n");
			}
		}
		System.out.println("\nItem updated:");
		System.out.println(stripNail.toString());
	}
	
	/**
	 * Removes compatibilities from the list
	 * 
	 * @param sc
	 * @param powerTool
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws ItemNotFoundException 
	 */
	public static void removeCompatibilities(Scanner sc, StripNail stripNail) throws ClassNotFoundException, SQLException, ItemNotFoundException
	{
		boolean done = false;
		while(!done)
		{
			System.out.println("Which one would you like to remove? (enter the UPC only)");
			
			HashMap<Integer, PowerTool> powerToolList = stripNail.getPowerToolList();
			
			if(!powerToolList.isEmpty())
			{
				System.out.println("\nWorks with:");
				
				for(Integer key : powerToolList.keySet())
				{
					System.out.println(powerToolList.get(key).toString());
				}
				
				System.out.println("\n");
				String input = sc.nextLine();
				int powerToolID = InventoryItemGateway.getID(input, "PowerTool");
				
				stripNail.removeCompatiblePowerTool(powerToolID);
				
				System.out.println("Would you like to remove another relation? (Y/N)");
				input = sc.nextLine();
				
				if(input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no"))
				{
					done = true;
				}
			}	
			else
			{
				System.out.println("There are no compatibilities\n");
				done = true;
			}
		}
	}

	/**
	 * Adds a PowerTool relation for the stripNail
	 * 
	 * @param sc
	 * @param powerTool
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ItemNotFoundException 
	 */
	public static void updateCompatibilities(Scanner sc, StripNail stripNail) throws ClassNotFoundException, SQLException, ItemNotFoundException 
	{
		boolean done = false;
		while(!done)
		{
			HashMap<Integer, InventoryItemDTO> dtoList = InventoryItemGateway.getAllPowerTools();

			for(Integer key : dtoList.keySet())
			{
				System.out.println(dtoList.get(key).getUpc());
			}
//			for(InventoryItemDTO dto : dtoList)
//			{
//				System.out.println(dto.getUpc());
//			}
			System.out.println("Which one would you like to add :");
			String input = sc.nextLine();
			int powerToolID = InventoryItemGateway.getID(input, "PowerTool");
			
			stripNail.addCompatiblePowerTool(powerToolID);
			
			System.out.println("Would you like to add another relation? (Y/N)");
			input = sc.nextLine();
			
			if(input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no"))
			{
				done = true;
			} 
		}
	}
	

	/**
	 * Sets the relationship for the created PowerTool
	 * 
	 * @param sc
	 * @param powerTool
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ItemNotFoundException 
	 */
	public static void stripNailRelationPrompt(Scanner sc, StripNail stripNail) throws ClassNotFoundException, SQLException, ItemNotFoundException 
	{
		boolean run = true;
		InventoryItem item = stripNail;
		
		while(run)
		{
			System.out.println("Would you like to add compatible powertools? (Y/N)");
			String input = sc.nextLine();
			
			if(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes"))
			{
				UserInput.addCompatibles(sc, item);
				run = false;
			}
			else if(input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no"))
			{
				run = false;
			}
			else 
			{
				System.out.println("Error: invalid input");
			}
		}		
	}
}