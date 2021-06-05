package main.firefighters;

import main.api.Building;
import main.api.City;
import main.api.CityNode;
import main.api.Firefighter;
import main.api.exceptions.NoFireFoundException;

public class FirefighterImpl implements Firefighter {

  /**
   *  The city node where fire fighter current operation is being held.
   */
  public CityNode cityNode = null;

  /**
   *  City they are serving
   */
  public City city = null;
  /**
   *  The distance travelled by the fire fighter.
   */
  int distanceTravelled=0;

  DistanceCalculationUtil distanceCalculationUtil;

  public FirefighterImpl(City city) {
    this.city = city;
    this.cityNode = null;
    this.distanceTravelled = 0;
    this.distanceCalculationUtil = new DistanceCalculationUtil(city);

  }
  public FirefighterImpl(CityNode cityNode) {
    this.cityNode = cityNode;
    this.distanceTravelled = 0;
  }

  public void handleFire(CityNode burningCity) throws NoFireFoundException {

    boolean checkBuildingExistence = isValidFireSituation(city, burningCity);
    if(checkBuildingExistence) {
      int distance =  distanceCalculationUtil.findShortestPath(this.getLocation(), burningCity.getX(), burningCity.getY());
      System.out.println("distance"  + distance);
      this.distanceTravelled += distance;
      Building building = city.getBuilding(burningCity);
      building.extinguishFire();
      this.setLocation(burningCity);
    }
  }

  private boolean isValidBuildingLocation(City city, CityNode cityNode) {
    return cityNode.getX() >=0
            &&  cityNode.getX() < city.getXDimension()
            && cityNode.getY() >=0
            && cityNode.getY() < city.getYDimension();
  }
  private boolean isValidFireSituation(City city, CityNode cityNode) {

    // if city node is with in limits and if city is burning - return true.
    return isValidBuildingLocation(city, cityNode) && city.getBuilding(cityNode).isBurning();
  }

  @Override
  public void setLocation(CityNode city) {
    this.cityNode = city;
  }

  @Override
  public CityNode getLocation() {
    return this.cityNode;
  }

  @Override
  public int distanceTraveled() {
    return this.distanceTravelled;
  }
}
