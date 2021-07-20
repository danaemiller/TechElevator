package com.techelevator;

import com.techelevator.view.Menu;   // Bring in Menu class provided

import java.io.FileNotFoundException;
import java.io.IOException;

public class VendingMachineApp {
	/*************************************************************************************************************************
	*  This is the application program to instantiate the VendingMachineCLI main program and start it running
	*  2
	*  DO NOT PUT ANY NEW CODE HERE WITHOUT CHECKING WITH YOUR INSTRUCTOR FIRST!
	***************************************************************************************************************************/
		
		public static void main(String[] args) throws IOException {
			Menu appMenu = new Menu(System.in, System.out);                // Instantiate a menu for Vending Machine CLI main program to use
			VendingMachineCLI vendingCli = new VendingMachineCLI(appMenu); // Instantiate the Vending Machine CLI main program passing it the Menu object to use
			vendingCli.run();                                              // Tell the Vending MachineCLI process to start running
		}
	}


