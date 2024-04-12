package building;

import building.enums.ElevatorSystemStatus;
import elevator.Elevator;
import elevator.ElevatorReport;
import java.util.List;
import scanerzus.Request;

/**
 * This interface is used to represent a building.
 */
public interface BuildingInterface {

  /**
   * This method is used to get the list of elevators in the building.
   *
   * @return the list of elevators in the building
   */
  List<Elevator> getElevators();

  /**
   * This method is used to get the number of floors in the building.
   *
   * @return the number of floors in the building
   */
  int getNumberOfFloors();

  /**
   * This method is used to get the number of elevators in the building.
   *
   * @return the number of elevators in the building
   */
  int getNumberOfElevators();

  /**
   * This method is used to get the elevator capacity in the building.
   * @return the elevator capacity in the building
   */
  int getElevatorCapacity();

  /**
   * This method is used to get the elevator System Status in the building.
   *
   * @return the elevator System Status in the building
   */
  ElevatorSystemStatus getSystemStatus();

  /**
   * Adds a request to the building's elevator system.
   *
   * @param  inputRequest the request user input
   * @return true if the request was successfully added
   * @throws IllegalStateException if the elevator system is stopping or out of service
   */
  boolean addRequest(String inputRequest);

  /**
   * Gets the up requests in the building.
   * @return the list of upRequests in the building
   */
  List<Request> getUpRequests();

  /**
   * Gets the down requests in the building.
   * @return the list of downRequests in the building
   */
  List<Request> getDownRequests();

  /**
   * Allocates list of requests to certain elevator(s) in the building.
   * @throws IllegalStateException if the elevator system is stopping or out of service
   * @throws IllegalArgumentException if the request
   * has up or down requests exceeding the elevator capacity
   */
  void allocateRequest();

  /**
   * Starts the elevator system.
   *
   * @return true if the elevator system successfully starts
   * @throws IllegalStateException if the elevator system is in the stopping state
   */
  boolean startElevatorSystem();

  /**
   * Allocate requests from the building system to the elevator system,
   * and then all the elevator implement the step method.
   *
   * @throws IllegalStateException if the elevator system is not running
   */
  void step();

  /**
   * Stops the elevator system, sending all elevators
   * to the ground floor and preparing to stop servicing requests.
   */
  void stopElevatorSystem();

  /**
   * Gets the status of the elevator system in the building.
   *
   * @return a BuildingReport object containing the status of the building's elevator system
   */
  BuildingReport getElevatorSystemStatus();
}

