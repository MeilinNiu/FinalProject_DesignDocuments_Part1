package building;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import building.enums.Direction;
import building.enums.ElevatorSystemStatus;
import elevator.ElevatorReport;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import scanerzus.Request;

/**
 * Test class for the BuildingReport class.
 */
public class BuildingReportTest {
  private BuildingReport buildingReportOne;

  /**
   * Set up the test.
   */
  @Before
  public void setUp() {
    buildingReportOne = new BuildingReport(10, 2,
        4, new ElevatorReport[2], new ArrayList<Request>(),
        new ArrayList<Request>(), ElevatorSystemStatus.running);
  }

  /**
   * Test getNumFloors in the building.
   */
  @Test
  public void testGetNumFloors() {
    assertEquals(10, buildingReportOne.getNumFloors());
  }

  /**
   * Test getNumElevators in the building.
   */
  @Test
  public void testGetNumElevators() {
    assertEquals(2, buildingReportOne.getNumElevators());
  }

  /**
   * Test getElevatorCapacity in the building.
   */
  @Test
  public void testElevatorCapacity() {
    assertEquals(4, buildingReportOne.getElevatorCapacity());
  }

  /**
   * Test getElevatorReports in the building.
   */
  @Test
  public void testGetElevatorReports() {
    assertArrayEquals(new ElevatorReport[2], buildingReportOne.getElevatorReports());
  }

  /**
   * Test getUpRequests in the building.
   */
  @Test
  public void testGetUpRequests() {
    assertEquals(new ArrayList<Request>(), buildingReportOne.getUpRequests());
  }

  /**
   * Test getDownRequests in the building.
   */
  @Test
  public void testGetDownRequests() {
    assertEquals(new ArrayList<Request>(), buildingReportOne.getDownRequests());
  }

  /**
   * Test getSystemStatus in the building.
   */
  @Test
  public void testGetSystemStatus() {
    assertEquals(ElevatorSystemStatus.running, buildingReportOne.getSystemStatus());
  }

  /**
   * Test toString method.
   */
  @Test
  public void testToString() {
    ElevatorReport[] elevatorReports = new ElevatorReport[2];
    ElevatorReport elevatorReportOne = new ElevatorReport(0, 0, Direction.UP,
        false, new boolean[10], 5, 5, false, true);
    ElevatorReport elevatorReportTwo = new ElevatorReport(1, 0, Direction.UP,
        false, new boolean[10], 5, 5, false, true);
    elevatorReports[0] = elevatorReportOne;
    elevatorReports[1] = elevatorReportTwo;

    buildingReportOne = new BuildingReport(10, 2,
        4, elevatorReports, new ArrayList<Request>(),
        new ArrayList<Request>(), ElevatorSystemStatus.running);
    String expected = "Building Report:\nNumber of Floors: 10"
        + "\nNumber of Elevators: 2"
        + "\nElevator Capacity: 4"
        + "\nElevator System Status: Running\nUp Requests: []"
        + "\nDown Requests: []\nElevator Reports: \n"
        + "Elevator 0: Waiting[Floor 0, Time 5]"
        + "\nElevator 1: Waiting[Floor 0, Time 5]\n";


    assertEquals(expected, buildingReportOne.toString());
  }
}
