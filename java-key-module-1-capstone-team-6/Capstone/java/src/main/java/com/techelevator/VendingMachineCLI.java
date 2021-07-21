package com.techelevator;
/**************************************************************************************************************************
*  This is your Vending Machine Command Line Interface (CLI) class
*
*  It is the main process for the Vending Machine
*
*  THIS is where most, if not all, of your Vending Machine interactions should be coded
*  
*  It is instantiated and invoked from the VendingMachineApp (main() application)
*  
*  Your code vending machine related code should be placed in here
***************************************************************************************************************************/
import com.techelevator.view.Menu;         // Gain access to Menu class provided for the Capstone

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Scanner;

public class VendingMachineCLI {

    // Main menu options defined as constants

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE      = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT          = "Exit";
	private static final String MAIN_MENU_SALES_REPORT		   = "Show Sales Report";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS,
													    MAIN_MENU_OPTION_PURCHASE,
													    MAIN_MENU_OPTION_EXIT,
														MAIN_MENU_SALES_REPORT
													    };

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY,
			PURCHASE_MENU_OPTION_SELECT_PRODUCT,PURCHASE_MENU_OPTION_FINISH

	};

	private Menu vendingMenu;              // Menu object to be used by an instance of this class

	VendingMachine vendingMachine = new VendingMachine();

	DecimalFormat centFormat = new DecimalFormat("###.##");

	public VendingMachineCLI(Menu menu) throws IOException {  // Constructor - user will pass a menu for this class to use
		this.vendingMenu = menu;           // Make the Menu the user object passed, our Menu

	}
	/**************************************************************************************************************************
	*  VendingMachineCLI main processing loop
	*  
	*  Display the main menu and process option chosen
	*
	*  It is invoked from the VendingMachineApp program
	*
	*  THIS is where most, if not all, of your Vending Machine objects and interactions 
	*  should be coded
	*
	*  Methods should be defined following run() method and invoked from it
	*
	***************************************************************************************************************************/

	public void run() throws IOException {

		File inputFile = new File("./vendingmachine.csv");
		Scanner scannerForFile = new Scanner(inputFile);
		String aLine = "";

		while(scannerForFile.hasNextLine()){
			aLine = scannerForFile.nextLine();
			String[] inputInfo = aLine.split("\\|"); // breaks up each line at the pipe delimiter
			vendingMachine.loadSlots(inputInfo[0], new Slot(inputInfo[0], new Product(inputInfo[1], inputInfo[3]),
				Double.parseDouble(inputInfo[2])));
		}




		boolean shouldProcess = true;         // Loop control variable
		
		while(shouldProcess) {                // Loop until user indicates they want to exit

			String choice = (String) vendingMenu.getChoiceFromOptions(MAIN_MENU_OPTIONS);  // Display menu and get choice

			switch (choice) {                  // Process based on user menu choice

				case MAIN_MENU_OPTION_DISPLAY_ITEMS:
					displayItems();           // invoke method to display items in Vending Machine
					break;                    // Exit switch statement

				case MAIN_MENU_OPTION_PURCHASE:
					purchaseItems();
					break;                    // Exit switch statement

				case MAIN_MENU_OPTION_EXIT:
					endMethodProcessing();  // Invoke method to perform end of method processing
					shouldProcess = false;  // Set variable to end loop
					break;                    // Exit switch statement

				case MAIN_MENU_SALES_REPORT:
					getSalesReport();
					break;
			}

		}
	}
/********************************************************************************************************
 * Methods used to perform processing
 ********************************************************************************************************/
	public void displayItems() {      // static attribute used as method is not associated with specific object instance
		vendingMachine.displayItems();
	}
	
	public void purchaseItems() throws IOException {	 // static attribute used as method is not associated with specific object instance


		String userInput = "";
		Scanner keyboard = new Scanner(System.in);

		boolean makePurchaseLoop = true;

		while (makePurchaseLoop) {   // loop as long as shouldPurchaseLoop is true

			System.out.println("Current Money Provided: $" + centFormat.format(vendingMachine.getBalance()));
			String selection = (String)vendingMenu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);


			switch (selection) {
				case PURCHASE_MENU_OPTION_FEED_MONEY:
					System.out.print("Please enter the valid tender >>> $");
					userInput = keyboard.nextLine();
					if (userInput.equals("") || userInput.equals(" ")) {
						System.out.println("Please enter valid tender.");
					} else {
						vendingMachine.feedMoney(Double.parseDouble(userInput));
						vendingMachine.auditLog("FEED MONEY", Double.parseDouble(userInput));
					}

					break;

				case PURCHASE_MENU_OPTION_SELECT_PRODUCT:
					vendingMachine.displayItems();
					System.out.print("Please enter the product number of your selection >>> ");
					userInput = keyboard.nextLine();
					if (vendingMachine.isSlotNumber(userInput.toUpperCase())
						&& !vendingMachine.isSoldOut(userInput.toUpperCase())) {
						vendingMachine.auditLog(userInput.toUpperCase());
					}
					vendingMachine.makePurchase(userInput.toUpperCase());

					break;

				case PURCHASE_MENU_OPTION_FINISH:
					vendingMachine.auditLog("DISPENSED CHANGE");
					vendingMachine.giveChange();
					makePurchaseLoop = false;
					break;
			}
		}
	}
	
	public void endMethodProcessing() { // static attribute used as method is not associated with specific object instance
		vendingMachine.closeFileWriter();
	}

	public void getSalesReport() throws IOException {
		vendingMachine.getSalesReport();
	}
}
