# mazez
The goal of the project is to create a maze generator (for now using one algorithm, but can be extended to more), and then solve the maze using various modes to achieve desired goals.
Currently, all mazes have a starting point at `[0,0]`. All mazes can be enriched with obstacles 🚫 (users can choose how many obstacles appear, 0 is a valid option).

## Modes
In every mode the goal is to reach an exit (a wall on the diagonal from the starting point), but can be enriched by additional tasks like collecting coins 💰 or made harder by adding traps ❌.
1. `COLLECT_COINS` - tries to collect as many coins as possible. Users can choose how many coins appear on the board (but probably not all of them will be reachable due to the obstacles 🚫);
2. `FIND_ALL_EXITS` - finds all possible exists from the maze;
3. `FIND_SHORTEST_PATH` - finds the shortest path to any available exit;
4. `FIND_SHORTEST_PATH_WITH_WALL_JUMP` - finds the shortest path to any available exit, can jump over the walls, but it is associated with additional cost. Normal step costs 1, but the jump costs 30;
5. `TRAPS` - tries to find the shortest path avoiding as many traps (which give negative points) as possible (but some of them can be impossible to avoid). Users can choose how many traps appear on the board. 

## Solving algorithms
1. *Breadth-First Search (BFS)* - finds shortest path to an exit.
2. *Depth-First Search (DFS)* - finds all possible paths.
3. *Dijkstra* - finds shortest path considering different weights of cells.
4. *Bellman-Ford* - finds shortest path considering negative cell values.

## Example maze
1. Sample maze 15*15 with `COLLECT_COINS` mode active. Number of obstacles: 7. Number of coins: 7.
```
 _____________________________
|_______ *|    _________  |  *|
|_| | |  _|_|_  |  _   _|___| |
| | |_______  | |_  |_|    _| |
| |_  |  _______|  _|  _|_____|
|___| |_|   |_____  |_____|_| |
|    _|  _|*|  _  |___|_| |_  |
| |_|  _| |___| |___  |_|   | |
|  ___|  ___   _|  ___|_|_|_| |
| |* ___|   |_|  _|_ * _____  |
|_  |  ___|_  |*|   |___|   | |
|___|*|_|  _| |  _|___  | | |_|
|   |   | |  _|_____  |___|_  |
| | |_| | | |_   _  | |_|___| |
| |_____|  _|  _| | |_|   |_| |
|X|___________|_________|_____|

Reachable coins 4
```
2. Sample maze 15*15 with `FIND_SHORTEST_PATH` mode active (the path is marked on the maze). Number of obstacles: 4. 

```
 _____________________________
|  _____|    _|_|_  | |_|  _  |
|   |   | |_____  | |_|_____| |
| |___|___|  _____|_  |  _  | |
|_|   |_| |_  |   | | | | |_  |
|_| | | |  _|___| | | | |   | |
|___|_| | |  _____| |___| |_| |
|   |_|_| |_  |_|_____    |  _|
| |_  |___|  _| |  ___| |_| | |
| | |___  |   | | |   |_  | | |
| |  _____|_| |  _| |_  | |_  |
| |___  |   | | |  _|_____  | |
|  _  |___|___| | |  _  |___| |
|___| |  _  |  _| | | | |   | |
|  ___| | | |___|___| | | |___|
|X|_______|___|_|_____|_______|

 _____________________________
|  _____|  ()_|_|_  | |_|  _  |
|   |   | |()()()() |_|_____| |
| |___|___|()()()()_  |  _  | |
|_|   |_| |()()()() | | | |_  |
|_| | | |  _|()()() | | |   | |
|___|_| | |()()()() |___| |_| |
|()()_|_| |()()_|_____    |  _|
|()()()___|()() |  ___| |_| | |
|() |()()()()() | |   |_  | | |
|()()()()()_|()  _| |_  | |_  |
|()()()()()()() |  _|_____  | |
|()()()()()()() | |  _  |___| |
|___|()  _  |  _| | | | |   | |
|()()() | | |___|___| | | |___|
|X|_______|___|_|_____|_______|

Shortest path length is 59.
```
3. Sample maze 15*15 with `TRAPS` mode active. Number of obstacles 6. Spider traps (`S`): 5. Number of spike traps (`^`): 5. 
 
```
 _____________________________
|_   _____  |_   _____    |_  |
|  _|  _  |_  | |   |  _|_  |^|
|  ___|  _| | | | |___|  _|_  |
| |   | |^ ___| | |___    |  _|
| | |___| |_|_  |___  | |___| |
| |_ S|_|___  |___|  _|___|   |
|_  | |_    |___|___|_ S|_|_| |
|___|_  |_|___|_|_|  S| |___  |
|  _____|  _____ S| |___|  ___|
|   |  ___|  _  | |___   _| |_|
| |___|   |_  | |  _  |_  |_  |
|_____ S|___| | |___|_  |_  |^|
|  _  |_|^  | |_____  | | | | |
|_  |_____|___|    _|___| |^| |
|X__|___________|_____________|

 _____________________________
|_ ()()()()()_   _____    |_  |
|()()()()()_  | |   |  _|_  |^|
|()()()()() | | | |___|  _|_  |
| |()()()^ ___| | |___    |  _|
| |()()() |_|_  |___  | |___| |
| |()()_|___  |___|  _|___|   |
|_  |()_    |___|___|_ S|_|_| |
|___|()()_|___|_|_|  S| |___  |
|()()()()()()()()() |___|  ___|
|()()()()()()()()()___   _| |_|
| |()()   |()()()()()()_  |_  |
|_____ S|___|()()___|()()_  |^|
|()()()_|()()()()()()()() | | |
|()()()()()()()    _|()() |^| |
|()()___________|_____________|

Shortest path length is 78
Best score is -60
```

## Possible improvements 
1. Add different maze generation algorithms (currently only recursive backtracking).
2. Add more modes (like shortest path when collecting coins).
3. Add support for rectangular-shaped mazes. 
4. Add A* algorithm and compare to the Dijkstra. 
5. Give users more control over the board creation (e.g. choosing a starting point).
6. Use Java swing to visualise mazes, because ASCII printing has several limitations. 