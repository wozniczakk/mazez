# mazez
The goal of the project is to create a maze generator (for now using one algorithm, but can be extended to more), and then solve the maze using various modes to achieve desired goals.
Currently, all mazes have a starting point at `[0,0]`. All mazes can be enriched with obstacles 🚫 (users can choose how many obstacles appear, 0 is a valid option).

## Modes
In every mode the goal is to reach an exit (a wall on the diagonal from the starting point), but can be enriched by additional tasks like collecting coins 💰 or made harder by adding traps ❌.
1. `COLLECT_COINS` - tries to collect as many coins as possible. Users can choose how many coins appear on the board (but probably not all of them will be reachable due to the obstacles 🚫);
2. `FIND_ALL_EXITS` - finds all possible exists from the maze;
3. `FIND_SHORTEST_PATH` - finds the shortest path to any available exit;
4. `FIND_SHORTEST_PATH_WITH_WALL_JUMP` - finds the shortest path to any available exit, can jump over the walls, but it is associated with additional cost. Normal step costs 1, but the jump costs 30;
5. `TRAPS` - tries to find the shortest path avoiding as many traps as possible (but some of them can be impossible to avoid). Users can choose how many traps appear on the board. 

## Solving algorithms
1. Breadth-First Search (BFS) - finds shortest path to an exit.
2. Depth-First Search (DFS) - finds all possible paths.
3. Dijkstra - finds shortest path considering different weights of cells.
4. Bellman-Ford - finds shortest path considering negative cell values.

## Possible improvements 
1. Add different maze generation algorithms (currently only recursive backtracking).
2. Add more modes (like shortest path when collecting coins).
3. Add support for rectangular-shaped mazes. 
4. Add A* algorithm and compare to the Dijkstra. 
5. Give users more control over the board creation (starting point, coins, traps).
6. Use Java swing to visualise mazes, because ASCII printing has several limitations. 