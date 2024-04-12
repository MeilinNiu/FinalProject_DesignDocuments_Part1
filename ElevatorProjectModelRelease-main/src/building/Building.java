package building;

import building.enums.Direction;
import building.enums.ElevatorSystemStatus;
import elevator.Elevator;
import elevator.ElevatorInterface;
import elevator.ElevatorReport;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import scanerzus.Request;


/**
 * This class represents a building.
 * The building has the number of floors,
 * the number of elevators, the elevator capacity,
 * the system status, the list of elevators,
 * the list of up requests, and the list of down requests.
 * It is used to receive requests and give the requests to the elevators.
 * The class building can receive new requests when the elevators are moving,
 * and start or stop the elevator system.
 */
public class Building implements BuildingInterface {

  private final int numberOfFloors;
  private final int numberOfElevators;
  private final int elevatorCapacity;
  private ElevatorSystemStatus systemStatus;
  private List<Elevator> elevators;
  private List<Request> upRequests;
  private List<Request> downRequests;

  /**
   * The constructor for the building.
   *
   * @param numberOfFloors the number of floors in the building.
   * @param numberOfElevators the number of elevators in the building.
   * @param elevatorCapacity the capacity of the elevators in the building.
   *
   * @throws IllegalArgumentException if the number of floors is less than 3 or greater than 30,
   *                                 if the number of elevators is less than or equal to 0,
   *                                 or if the elevator capacity is less than or equal to 3
   *                                 or greater than 20
   */
  public Building(int numberOfFloors, int numberOfElevators, int elevatorCapacity) {
    if (numberOfFloors < 3 || numberOfFloors > 30) {
      throw new IllegalArgumentException("The number of floors must be "
          + "at least 3 and less than or equal to 30.");
    }

    if (numberOfElevators <= 0) {
      throw new IllegalArgumentException("The number of elevators "
          + "must be greater than 0.");
    }

    if (elevatorCapacity <= 3 || elevatorCapacity > 20) {
      throw new IllegalArgumentException("An elevator must have an occupancy "
          + "that is greater than 3 and less than or equal to 20");
    }

    this.numberOfFloors = numberOfFloors;
    this.numberOfElevators = numberOfElevators;
    this.elevatorCapacity = elevatorCapacity;
    this.systemStatus = ElevatorSystemStatus.outOfService; // Default status

    // Initialize elevators list
    this.elevators = new ArrayList<>();
    for (int i = 0; i < numberOfElevators; i++) {
      this.elevators.add(new Elevator(numberOfFloors, elevatorCapacity));
    }

    // Initialize request lists
    this.upRequests = new ArrayList<>();
    this.downRequests = new ArrayList<>();

  }

  @Override
  public List<Elevator> getElevators() {
    return this.elevators;
  }

  @Override
  public ElevatorSystemStatus getSystemStatus() {
    return this.systemStatus;
  }

  @Override
  public int getNumberOfFloors() {
    return this.numberOfFloors;
  }

  @Override
  public int getNumberOfElevators() {
    return this.numberOfElevators;
  }

  @Override
  public int getElevatorCapacity() {
    return this.elevatorCapacity;
  }

  @Override
  public List<Request> getUpRequests() {
    return this.upRequests;
  }

  @Override
  public List<Request> getDownRequests() {
    return this.downRequests;
  }

  @Override
  public boolean addRequest(String inputRequest) {
    if (systemStatus != ElevatorSystemStatus.running) {
      throw new IllegalStateException("The elevator system is not running, "
          + "so it cannot accept requests.");
    }

    // Split the input request into individual requests
    List<Request> requests = splitRequest(inputRequest);

    // Add each request to the appropriate list (upRequests or downRequests)
    for (Request request : requests) {
      if (request.getStartFloor() < request.getEndFloor()) {
        upRequests.add(request);
        if (upRequests.size() > this.elevatorCapacity) {
          throw new IllegalArgumentException("The number of up requests exceeds the elevator capacity.");
        }
      } else {
        if (downRequests.size() > this.elevatorCapacity) {
          throw new IllegalArgumentException("The number of down requests exceeds the elevator capacity.");
        }
        downRequests.add(request);
      }
    }
    return true; // Indicate that the request was successfully added
  }

  /**
   * This method is used to split the input request into individual requests.
   *
   * @param inputRequest the input request
   * @return the list of individual requests
   * @throws IllegalArgumentException if the input request is invalid
   */
  private List<Request> splitRequest(String inputRequest) {
    String[] parts = inputRequest.split(" ");
    if (parts.length % 2 != 0) {
      throw new IllegalArgumentException("Invalid input. Please ensure you enter values"
          + " in an even number of arguments: <startFloor> <endFloor>");
    }

    List<Request> requests = new ArrayList<>();
    // Parse each pair of start and end floors and create Request objects
    for (int i = 0; i < parts.length; i += 2) {
      int startFloor = Integer.parseInt(parts[i]);
      int endFloor = Integer.parseInt(parts[i + 1]);
      requests.add(new Request(startFloor, endFloor));
    }

    return requests;
  }


  /**
   * distribute requests to elevators
   * that are either on the ground floor or on the top floor.
   */
  @Override
  public void allocateRequest() {
    // if the system is not running, no action is taken
    if (systemStatus != ElevatorSystemStatus.running) {
      throw new IllegalStateException("The elevator system is not running, "
          + "so it cannot accept requests.");
    }

    // If there are no requests, no action is taken
    if (upRequests.isEmpty() && downRequests.isEmpty()) {
      return;
    }

    // If there are no elevators that can take requests, no action is taken
    if (this.getCanGoUpElevator() == null && this.getCanGoDownElevator() == null) {
      return;
    }

    // If there are upRequests, allocate them to the elevator that can go up
    if (!upRequests.isEmpty()) {
      Elevator upElevator = this.getCanGoUpElevator();
      if (upElevator != null) {
        upElevator.processRequests(upRequests);
        upRequests.clear();
      }
    }

    // If there are downRequests, allocate them to the elevator that can go down
    if (!downRequests.isEmpty()) {
      Elevator downElevator = this.getCanGoDownElevator();
      if (downElevator != null) {
        downElevator.processRequests(downRequests);
        downRequests.clear();
      }
    }

  }


  /**
   * This method is used to get elevator which can receive upRequests.
   * @return the elevator which can receive upRequests.
   */
  private Elevator getCanGoUpElevator() {
    for (Elevator elevator : elevators) {
      if (elevator.getDirection() == Direction.UP && elevator.isTakingRequests()) {
        return elevator;
      }
    }
    return null;
  }


  /**
   * This method is used to get elevator which can receive downRequests.
   * @return the elevator which can receive downRequests.
   */
  private Elevator getCanGoDownElevator() {
    for (Elevator elevator : elevators) {
      if (elevator.getDirection() == Direction.DOWN && elevator.isTakingRequests()) {
        return elevator;
      }
    }
    return null;
  }


  /**
   * Implement the option for the building to start processing requests.
   * The elevators are ready to accept requests after implementing start().
   * for each elevator in the building.
   * @return true if the system is started successfully.
   */
  @Override
  public boolean startElevatorSystem() {
    if (systemStatus == ElevatorSystemStatus.running) {
      return false; // System already running, no action taken
    } else if (systemStatus == ElevatorSystemStatus.stopping) {
      throw new IllegalStateException("Cannot start the system while it is stopping.");
    }
    // change the system status from out of service to running
    systemStatus = ElevatorSystemStatus.running;

    // All the elevator doors are closed
    // and the building system is ready to accept requests
    for (Elevator elevator : elevators) {
      elevator.start();
    }

    return true;
  }


  /**
   * step function distribute requests to elevators,
   * that are either on the ground floor or on the top floor.
   */
  @Override
  public void step() {
    if (systemStatus != ElevatorSystemStatus.running) {
      throw new IllegalStateException("The elevator system is not running, "
          + "so it cannot accept requests.");
    }

    this.allocateRequest();
    for (Elevator elevator : elevators) {
      elevator.step();
    }
  }

  /**
   * Implement the option for the building to stop processing requests.
   */
  @Override
  public void stopElevatorSystem() {
    if (systemStatus != ElevatorSystemStatus.running) {
      return; // If the system is not running, there's no need to stop it
    }
    // change the system status to stopping
    systemStatus = ElevatorSystemStatus.stopping;

    // Clear all the recorded requests in building
    upRequests.clear();
    downRequests.clear();

//     Clear all the recorded requests in elevators,
//     Elevators stop taking requests,
//     directions change to down,
//     all elevators are out of service,
//     the timers are set to 0,
    for (Elevator elevator : elevators) {
      elevator.takeOutOfService();
    }
    // all elevators keep going down
    // until the elevators reach the main floor it opens the door
    for (Elevator elevator : elevators) {
      while (elevator.getCurrentFloor() != 0 || elevator.isDoorClosed()) {
        elevator.step();
      }
    }

    // all elevators are changed to out of service
    systemStatus = ElevatorSystemStatus.outOfService;
  }

  @Override
  public BuildingReport getElevatorSystemStatus() {
    // create a new empty array of ElevatorReport
    ElevatorReport[] elevatorReports = new ElevatorReport[numberOfElevators];
    // Assign each ElevatorReport to the array
    int i = 0;
    for (Elevator elevator : elevators) {
      elevatorReports[i] = elevator.getElevatorStatus();
      i++;
    }
    return new BuildingReport(numberOfFloors,
        numberOfElevators,
        elevatorCapacity,
        elevatorReports,
        this.upRequests,
        this.downRequests,
        systemStatus);
  }
}


