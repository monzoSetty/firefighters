package main.firefighters;

import main.api.City;
import main.api.CityNode;

import java.util.LinkedList;
import java.util.Queue;

public class DistanceCalculationUtil {
    private  City city;

    public DistanceCalculationUtil(City city) {
        this.city = city;
    }

    public int findShortestPath(CityNode cityNode, int x, int y) {
        int nodeX = cityNode.getX();
        int nodeY = cityNode.getY();
        Queue<CityNode> bfs = new LinkedList<>();
        bfs.add(new CityNode(x, y));
        int distance = 0;
        boolean solutionFound = false;
        while(!bfs.isEmpty()) {
            int size = bfs.size();
            for(int i=0; i<size; i++) {
                // processing all it's neighbours at once.
                CityNode point = bfs.poll();
                if(point.getX()==nodeX && point.getY()==nodeY) {
                    solutionFound = true;
                    break;
                }

                if(isValidBuildingLocation(point.getX(), point.getY() + 1))
                    bfs.add(new CityNode(point.getX(), point.getY()+1));

                if(isValidBuildingLocation(point.getX()+1, point.getY()))
                    bfs.add(new CityNode(point.getX()+1, point.getY()));

                if(isValidBuildingLocation(point.getX(), point.getY() - 1))
                    bfs.add(new CityNode(point.getX(), point.getY() -1));

                if(isValidBuildingLocation(point.getX()-1, point.getY()))
                    bfs.add(new CityNode(point.getX()-1, point.getY()));
            }
            // incrementing distance by 1 in BFS only if no solution found.
            if(!solutionFound)
                distance++;
            else
                break;
        }
        return distance;
    }

    private boolean isValidBuildingLocation(int x, int y) {
        int a = city.getXDimension();
        int b = city.getYDimension();
        return x >=0 &&  x < a && y >=0 && y < b;
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
}
