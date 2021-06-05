package main.firefighters;

import java.util.List;

import main.api.City;
import main.api.CityNode;
import main.api.FireDispatch;
import main.api.Firefighter;
import main.api.exceptions.NoFireFoundException;
import java.util.ArrayList;

public class FireDispatchImpl implements FireDispatch {
  List<Firefighter> firefightersList;
  City currentServingCity = null;
  public FireDispatchImpl(City city) {
    this.currentServingCity = city;
    firefightersList = new ArrayList<>();
  }

  @Override
  public void setFirefighters(int numFirefighters) {
    for(int i=0; i<numFirefighters; i++) {
      Firefighter firefighter = new FirefighterImpl(currentServingCity);
      firefighter.setLocation(new CityNode(0,0));
      firefightersList.add(firefighter);
    }
  }

  @Override
  public List<Firefighter> getFirefighters() {
    return firefightersList;
  }

  @Override
  public void dispatchFirefighters(CityNode... burningBuildings) {
    for(CityNode burningCity : burningBuildings) {
        Firefighter firefighter = findFireFighter(burningCity);
        System.out.println("found fighter" + firefighter.distanceTraveled());
        if(firefighter!=null) {
          try {
            disPatchFighter(firefighter, burningCity);
          } catch (NoFireFoundException ex) {
            firefighter.setLocation(new CityNode(0,0));
          }
        } else {
          System.out.println("unable to find a fire fighter");
      }
    }
  }

  /**
   * Find the best finder available.
   * This function tries to optimize the rotation so each fighter get to handle a fire
   * and if all fighters have already handled fire, then it will be finding the nearest responder.
   * @param burningCity
   * @return
   */
  private Firefighter findFireFighter(CityNode burningCity) {
    Firefighter optimalFighter = null;
    DistanceCalculationUtil distanceCalculationUtil = new DistanceCalculationUtil(currentServingCity);

    for(Firefighter firefighter : firefightersList)
      if(firefighter.distanceTraveled()==0) {
        optimalFighter= firefighter;
        break;
      }
    // this algorithm can be made more sophisticated.
    int min = Integer.MAX_VALUE;
    if(null == optimalFighter)  {

      // finding the fastest responder using fire fighters current location.
      for(Firefighter firefighter : firefightersList) {
        int fighterX = firefighter.getLocation().getX();
        int fighterY = firefighter.getLocation().getY();
        /*
          we could also add one more optimization , among all the drivers, the closest one with minimal miles also can be choosen.
          if(firefighter.distanceTraveled() + distanceCalculationUtil.findShortestPath(burningCity, fighterX, fighterY) < min) {
           optimalFighter = firefighter;
          }
         */

        // alternatively, most real world scenario's use the fighter that can respond fastest.
        if(distanceCalculationUtil.findShortestPath(burningCity, fighterX, fighterY) < min) {
          optimalFighter = firefighter;
        }

      }
    }
    return optimalFighter;
  }

  // method stub to dispatch fighter.
  private void disPatchFighter(Firefighter firefighter, CityNode burningCity) throws NoFireFoundException {
    firefighter.handleFire(burningCity);
  }
}
