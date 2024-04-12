package building;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import building.enums.Direction;
import building.enums.ElevatorSystemStatus;
import elevator.Elevator;
import elevator.ElevatorReport;
import org.junit.Before;
import org.junit.Test;
import scanerzus.Request;

/**
 * Test class for the Building class.
 */
public class BuildingTest {

  private Building validBuilding;

  /**
  * Set up the test.
  */
  @Before
  public void setUp() {
    validBuilding = new Building(10,
        2, 4);
  }

  /**
   * Test the smaller numberOfFloors for the building.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSmallerFloors() {
    Building building = new Building(2,
        2, 4);
  }

  /**
   * Test the larger numberOfFloors for the building.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testLargerFloors() {
    Building building = new Building(31, 2, 4);
  }

  /**
   * Test the smaller numberOfElevators for the building.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSmallerElevators() {
    Building building = new Building(10,
        0, 4);
  }

  /**
   * Test the smaller elevatorCapacity for the building.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSmallerElevatorCapacity() {
    Building building = new Building(10,
        2, 3);
  }

  /**
   * Test the larger elevatorCapacity for the building.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testLargerElevatorCapacity() {
    Building building = new Building(10,
        2, 21);
  }

  /**
   * Test the getElevators method.
   */
  @Test
  public void testGetElevators() {
    assertEquals(2, validBuilding.getElevators().size());
  }

  /**
   * Test the getSystemStatus method.
   */
  @Test
  public void testGetSystemStatus() {
    assertEquals(ElevatorSystemStatus.outOfService, validBuilding.getSystemStatus());
  }

  /**
   * Test the invalid addRequest method.
   */
  @Test(expected = IllegalStateException.class)
  public void testInvalidAddRequest() {
    validBuilding.addRequest("1 2");
  }


  /**
   * Verifies that one request is handled appropriately.
   * Test building correctly store up and down requests in the order that it receives them
   */
  @Test
  public void testAddRequest() {
    validBuilding.startElevatorSystem();
    assertEquals(ElevatorSystemStatus.running,
        validBuilding.getSystemStatus());
    assertTrue(validBuilding.addRequest("1 2"));
    assertTrue(validBuilding.addRequest("7 3 9 0"));
    Request requestOne = new Request(1, 2);
    Request requestTwo = new Request(7, 3);
    Request requestThree = new Request(9, 0);
    assertEquals(requestOne.getStartFloor(),
        validBuilding.getUpRequests().get(0).getStartFloor());
    assertEquals(requestTwo.getEndFloor(),
        validBuilding.getDownRequests().get(0).getEndFloor());
    assertEquals(requestThree.getEndFloor(),
        validBuilding.getDownRequests().get(1).getEndFloor());
  }


  /**
   * Test invalid more upRequest greater than elevator capacity.
   * Building will never allocate more requests to an elevator than its max capacity.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCannotAllocateRequestsMoreThanCapacity() {
    validBuilding.startElevatorSystem();
    validBuilding.addRequest("1 2 3 4 5 6 7 8 9 10 11 12");
  }


  /**
   * Test the startElevatorSystem method.
   */
  @Test
  public void testStartElevatorSystem() {
    assertEquals(ElevatorSystemStatus.outOfService,
        validBuilding.getSystemStatus());
    assertTrue(validBuilding.startElevatorSystem());
    assertEquals(ElevatorSystemStatus.running,
        validBuilding.getSystemStatus());
    for (Elevator elevator : validBuilding.getElevators()) {
        assertTrue(elevator.isTakingRequests());
        assertTrue(elevator.isDoorClosed());
        assertEquals(elevator.getDirection(), Direction.UP);
    }
  }

  /**
   * Test invalid startElevatorSystem method.
   */
  @Test
  public void testInvalidStartElevatorSystem() {
    validBuilding.startElevatorSystem();
    assertFalse(validBuilding.startElevatorSystem());
  }

  /**
   * Test the stopElevatorSystem method.
   */
  @Test
  public void testStopElevatorSystem() {
    validBuilding.startElevatorSystem();
    assertEquals(ElevatorSystemStatus.running,
        validBuilding.getSystemStatus());
    validBuilding.stopElevatorSystem();
    assertEquals(ElevatorSystemStatus.outOfService,
        validBuilding.getSystemStatus());
    for (Elevator elevator : validBuilding.getElevators()) {
      assertFalse(elevator.isTakingRequests());
      assertFalse(elevator.isDoorClosed());
      assertEquals(Direction.STOPPED, elevator.getDirection());
      assertEquals(0, elevator.getCurrentFloor());
    }
  }


  /**
   * Test the getElevatorSystemStatus method.
   */
  @Test
  public void testGetElevatorSystemStatus() {
    assertEquals(validBuilding.getElevatorSystemStatus().getNumElevators(),
        validBuilding.getNumberOfElevators());
    assertEquals(validBuilding.getElevatorSystemStatus().getNumFloors(),
        validBuilding.getNumberOfFloors());
    assertEquals(validBuilding.getElevatorSystemStatus().getElevatorCapacity(),
        validBuilding.getElevatorCapacity());
    assertEquals(validBuilding.getElevatorSystemStatus().getSystemStatus(),
        validBuilding.getSystemStatus());
  }

}
