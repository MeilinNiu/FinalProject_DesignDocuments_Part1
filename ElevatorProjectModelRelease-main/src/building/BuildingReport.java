package building;

import building.enums.ElevatorSystemStatus;
import elevator.ElevatorReport;
import java.util.List;
import scanerzus.Request;


/**
 * This is the reporting class for the building.
 * Building reports contain the number of floors, the number of elevators,
 * the elevator capacity, the status of the elevators, the up requests,
 * the down requests, and the status of the elevator system.
 * It is used to create and print out
 * the summarized information about the building.
 */
public class BuildingReport {
  int numFloors;
  int numElevators;

  int elevatorCapacity;

  ElevatorReport[] elevatorReports;

  List<Request> upRequests;

  List<Request> downRequests;

  ElevatorSystemStatus systemStatus;

  /**
   * This constructor is used to create a new BuildingReport object.
   *
   * @param numFloors        The number of floors in the building.
   * @param numElevators     The number of elevators in the building.
   * @param elevatorCapacity The capacity of the elevators.
   * @param elevatorsReports The status of the elevators.
   * @param upRequests       The up requests for the elevators.
   * @param downRequests     The down requests for the elevators.
   * @param systemStatus     The status of the elevator system.
   */
  public BuildingReport(int numFloors,
                        int numElevators,
                        int elevatorCapacity,
                        ElevatorReport[] elevatorsReports,
                        List<Request> upRequests,
                        List<Request> downRequests,
                        ElevatorSystemStatus systemStatus) {
    this.numFloors = numFloors;
    this.numElevators = numElevators;
    this.elevatorCapacity = elevatorCapacity;
    this.elevatorReports = elevatorsReports;
    this.upRequests = upRequests;
    this.downRequests = downRequests;
    this.systemStatus = systemStatus;
  }

  /**
   * This method is used to get the number of floors in the building.
   *
   * @return the number of floors in the building
   */
  public int getNumFloors() {
    return this.numFloors;
  }

  /**
   * This method is used to get the number of elevators in the building.
   *
   * @return the number of elevators in the building
   */
  public int getNumElevators() {
    return this.numElevators;
  }

  /**
   * This method is used to get the max occupancy of the elevator.
   *
   * @return the max occupancy of the elevator.
   */
  public int getElevatorCapacity() {
    return this.elevatorCapacity;
  }

  /**
   * This method is used to get the status of the elevators.
   *
   * @return the status of the elevators.
   */
  public ElevatorReport[] getElevatorReports() {
    return this.elevatorReports;
  }

  /**
   * This method is used to get the up requests for the elevators.
   *
   * @return the requests for the elevators.
   */
  public List<Request> getUpRequests() {
    return this.upRequests;
  }

  /**
   * This method is used to get the down requests for the elevators.
   *
   * @return the requests for the elevators.
   */
  public List<Request> getDownRequests() {
    return this.downRequests;
  }

  /**
   * This method is used to get the status of the elevator system.
   *
   * @return the status of the elevator system.
   */
  public ElevatorSystemStatus getSystemStatus() {
    return this.systemStatus;
  }

  /**
   * toString method for the elevator system.
   *
   * @return the string representation of the elevator system.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Building Report:\n");
    sb.append("Number of Floors: ").append(this.getNumFloors()).append("\n");
    sb.append("Number of Elevators: ").append(this.getNumElevators()).append("\n");
    sb.append("Elevator Capacity: ").append(this.getElevatorCapacity()).append("\n");
    sb.append("Elevator System Status: ").append(this.getSystemStatus()).append("\n");
    sb.append("Up Requests: ").append(this.getUpRequests()).append("\n");
    sb.append("Down Requests: ").append(this.getDownRequests()).append("\n");
    sb.append("Elevator Reports: ").append("\n");

    for (int i = 0; i < this.getNumElevators(); i++) {
      String eachReport = "Elevator " + i + ": "
          + this.getElevatorReports()[i].toString();
      sb.append(eachReport).append("\n");
    }
    return sb.toString();
  }

}