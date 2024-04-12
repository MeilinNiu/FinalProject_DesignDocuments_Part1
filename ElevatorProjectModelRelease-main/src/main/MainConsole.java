package main;

import building.Building;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import scanerzus.Request;
import building.enums.ElevatorSystemStatus;

/**
 * The driver for the elevator system.
 * This class will create the elevator system and run it.
 * this is for testing the elevator system.
 * <p>
 * It provides a user interface to the elevator system.
 */
public class MainConsole {

  Scanner scanner = new Scanner(System.in);
  String[] args = new String[] {scanner.next()};

  /**
   * The main method for the elevator system.
   * This method creates the elevator system and runs it.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    // Introduction text to the user
    String[] introText = {
        "Welcome to the Elevator System!",
        "This system will simulate the operation of an elevator system.",
        "Please enter the number of floors in the building (>=3 and <= 30),",
        "the number of elevators in the building (>0),",
        "and the max number of people in each elevator (>3 and <=20)",
        "in the format of 'numFloors numElevators numPeople':"
    };

    for (String line : introText) {
      System.out.println(line);
    }

    // Reading user input
    String inputThreeNum = scanner.nextLine();
    String[] parts = inputThreeNum.split(" ");

    // Validate input
    if (parts.length != 3) {
      System.out.println("Invalid input. Please ensure "
          + "you enter values in the correct format: "
          + "<numFloors> <numElevators> <numPeople>");
      return; // Exit the program
    }

    int numFloors = Integer.parseInt(parts[0]);
    int numElevators = Integer.parseInt(parts[1]);
    int numPeople = Integer.parseInt(parts[2]);

    // Create the building with the specified parameters
    // throw expected IllegalArgumentException if the input is invalid
    Building building = new Building(numFloors, numElevators, numPeople);

    // Print the system initialization parameters
    System.out.println("The system will be initialized with the following parameters:\n"
        + "Number of floors: " + numFloors + "\n"
        + "Number of elevators: " + numElevators + "\n"
        + "Number of people: " + numPeople + "\n");

    // Start the elevator system
    if (building.startElevatorSystem()) {
      System.out.println("The elevator system is now "
          + building.getSystemStatus());
    } else {
      System.out.println("The elevator system could not be started.");
      return; // Exit the program
    }

    // enter the startFloor and endFloor
    System.out.println("Please enter the start floor "
        + "and end floor for the first request\n"
        + "in the format of 'startFloor endFloor', starting from floor 0: ");
    String inputRequest = scanner.nextLine();
    // add requests to building
    if (building.addRequest(inputRequest)) {
      System.out.println("The request has been added.");
      } else {
      System.out.println("The request could not be added.");
      return; // Exit the program
    }



    // allocate requests to available elevators
    // by calling the allocateRequest method in class Building
    // and let elevators process the requests
    building.allocateRequest();

    System.out.println("Please enter how many times you wan to implement the step method: \n");
    int stepCount = scanner.nextInt();
    scanner.nextLine();

    // use the step method in class Building to move the elevator
    for (int i = 0; i < stepCount; i++) {
      building.step();
      System.out.println(building.getElevatorSystemStatus().toString());
    }

    // show how the stop elevator system function works
    System.out.println("Press enter to stop the building system.\n");
    scanner.nextLine();

    building.stopElevatorSystem();
    System.out.println(building.getElevatorSystemStatus().toString());

    scanner.close(); // Close the scanner
  }

}