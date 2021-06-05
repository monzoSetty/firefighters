## How does this work
1. initialize requested number of fighters.
2. Once all fighters are initialized , each one is guaranteed a fire operation, if such operation does exist.
3. As system progresses, the location of each fighter is updated with their recent operation handle. 
4. As new fires come in, we find the nearest driver one such fighter to stop the fire.
5. The algorithm used in this method is BFS. ( breadth first search). 


## Proposed optimizations
1. caching can be done to prevent repetitive distance calculations.
2. real world - all distance calculations and relative distances can be stored in a map while building the city itself. 
this way determining the best driver become fast. 

3.The algorithm to find the nearest driver or best a possible fighter is written under the assumption that fighters dont return to base
i.e 0, 0;  We sophisticate this algorithm by adding more decisive conditions.

Example: among the drivers nearest to the fire, we can choose the one with less miles / distance travelled, so it optimizes each use case.
