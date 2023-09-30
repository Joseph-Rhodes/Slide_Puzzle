import java.util.*;
import java.io.*;

public class SlidePuzzle {
    // Current state of the puzzle
    private BState curState;
    
    // Maximum number of states (not fully clear what this represents without context)
    private int maxStates;

    // Constructor initializes the puzzle with an initial state and a maximum state count
    public SlidePuzzle() {
        curState = new BState("b12345678", 0, null);
        maxStates = Integer.MAX_VALUE; // Set the maximum state count to the maximum possible value
    }


    // This is a private class that is used to hold the state of the puzzle (The parent and children states)
    private class BState implements Comparable<BState> {
        // Function value associated with the state
        int funcValue;
        
        // String representation of the state
        String myState;
        
        // Parent state (previous state)
        BState pState;
        
        // Position of the blank tile in the state
        int myBlankTile;
    
        // Constructor to initialize the state
        private BState(String myState, int funcValue, BState pState) {
            this.funcValue = funcValue;
            this.myState = myState;
            this.pState = pState;
    
            // Determine the position of the blank tile ('b') in the state
            for (int i = 0; i < 9; i++) {
                if (myState.charAt(i) == 'b') {
                    myBlankTile = i;
                }
            }
        }
    
        // Compare two states based on their function values
        public int compareTo(BState o) {
            return this.funcValue - o.funcValue;
        }
    
        // This method generates child states for a puzzle state based on heuristic H1. Heuristic H1 is used to estimate the cost from the current state to the goal state.
        public List<BState> getH1Child(){
            List<BState> childList = new ArrayList<BState>();
            BState temp = new BState(this.myState, this.funcValue, this.pState);

            temp = moveUp(temp);
            if(!temp.myState.equals(this.myState)){
                int depth = funcValue - h1(myState) + 1;
                childList.add(new BState(temp.myState, depth + h1(temp.myState), this));
                temp = new BState(this.myState, this.funcValue, this.pState);
            }

            temp = moveDown(temp);
            if(!temp.myState.equals(this.myState)){
                int depth = funcValue - h1(myState) + 1;
                childList.add(new BState(temp.myState, depth + h1(temp.myState), this));
                temp = new BState(this.myState, this.funcValue, this.pState);
            }

            temp = moveLeft(temp);
            if(!temp.myState.equals(this.myState)){
                int depth = funcValue - h1(myState) + 1;
                childList.add(new BState(temp.myState, depth + h1(temp.myState), this));
                temp = new BState(this.myState, this.funcValue, this.pState);
            }

            temp = moveRight(temp);
            if(!temp.myState.equals(this.myState)){
                int depth = funcValue - h1(myState) + 1;
                childList.add(new BState(temp.myState, depth + h1(temp.myState), this));
                temp = new BState(this.myState, this.funcValue, this.pState);
            }

            return childList;
        }

// This method generates child states for a puzzle state based on heuristic H2. Heuristic H2 is used to estimate the cost from the current state to the goal state.
        public List<BState> getH2Child(){
            List<BState> childList = new ArrayList<BState>();
            BState temp = new BState(this.myState, this.funcValue, this.pState);

            temp = moveUp(temp);
            if(!temp.myState.equals(this.myState)){
                int depth = funcValue - h2(myState) + 1;
                childList.add(new BState(temp.myState, depth + h2(temp.myState), this));
                temp = new BState(this.myState, this.funcValue, this.pState);
            }

            temp = moveDown(temp);
            if(!temp.myState.equals(this.myState)){
                int depth = funcValue - h2(myState) + 1;
                childList.add(new BState(temp.myState, depth + h2(temp.myState), this));
                temp = new BState(this.myState, this.funcValue, this.pState);
            }

            temp = moveLeft(temp);
            if(!temp.myState.equals(this.myState)){
                int depth = funcValue - h2(myState) + 1;
                childList.add(new BState(temp.myState, depth + h2(temp.myState), this));
                temp = new BState(this.myState, this.funcValue, this.pState);
            }

            temp = moveRight(temp);
            if(!temp.myState.equals(this.myState)){
                int depth = funcValue - h2(myState) + 1;
                childList.add(new BState(temp.myState, depth + h2(temp.myState), this));
                temp = new BState(this.myState, this.funcValue, this.pState);
            }

            return childList;
        }

        // This method generates child states for a puzzle state based on beam. beam is used to estimate the cost from the current state to the goal state.
        public List<BState> getBeamChild(){
            List<BState> childrenList = new ArrayList<BState>();
			BState temp = new BState(this.myState,this.funcValue,this.pState);
			
			temp = moveUp(temp);
			if(!temp.myState.equals(this.myState)) {
				childrenList.add(new BState(temp.myState,h2(temp.myState),this));
				temp = new BState(this.myState,this.funcValue,this.pState);
			}
			
			
			temp = moveDown(temp);
			if(!temp.myState.equals(this.myState)) {
				childrenList.add(new BState(temp.myState,h2(temp.myState),this));
				temp = new BState(this.myState,this.funcValue,this.pState);
			}
			
			temp = moveLeft(temp);
			if(!temp.myState.equals(this.myState)) {
				childrenList.add(new BState(temp.myState,h2(temp.myState),this));
				temp = new BState(this.myState,this.funcValue,this.pState);
			}
			
			temp = moveRight(temp);
			if(!temp.myState.equals(this.myState)) {
				childrenList.add(new BState(temp.myState,h2(temp.myState),this));
				temp = new BState(this.myState,this.funcValue,this.pState);
			}
			
			return childrenList;
        }

    }

// This method is setting the State of the board 
    public String setState(String state){
        if(!isValidState(state)){
            throw new IllegalArgumentException();
        }

        this.curState.myState = state;
        this.curState.myBlankTile = getBlankTile();

        return this.curState.myState;
    }

// This method will print the state of my board
    public void printState(){
        System.out.println(curState.myState);
    }

    public String getState(){
        return this.curState.myState;
    }


// Helper method used in setState() checking in the state is valid
    private boolean isValidState(String state){
        if(state.length() != 9){
            return false;
        }

        List<Character> validList = new ArrayList<Character>();
        validList.add('b');
        validList.add('1');
        validList.add('2');
        validList.add('3');
        validList.add('4');
        validList.add('5');
        validList.add('6');
        validList.add('7');
        validList.add('8');


        for(int i = 0; i < state.length(); i++){
            char temp = state.charAt(i);

            if(!validList.contains(temp)){
                return false;
            }

            int index = validList.indexOf(temp);
                validList.remove(index);
        }

        return true;

    }

// Helper method used in setState() and it returns the index of the Blank title in the state/puzzle
    private int getBlankTile(){
        for(int i = 0; i < 9; i++){
            if(curState.myState.charAt(i) == 'b'){
                return i;
            }
        }
        return 0;
    }


    public void maxNodes(int n) {
        // Check if the input value 'n' is less than 0
        if (n < 0) {
            // If 'n' is negative, throw an IllegalArgumentException with an error message
            throw new IllegalArgumentException("n can't be < 0");
        }
        // Set the maximum number of nodes (or states) to the input value 'n'
        maxStates = n;
    }
    

    public void randomizeState(int n) {
        // Initialize the current state with a fixed state and blank tile position
        curState.myState = "b12345678";
        curState.myBlankTile = 0;
    
        // Check if the input value 'n' is less than 0
        if (n < 0) {
            // If 'n' is negative, throw an IllegalArgumentException with an error message
            throw new IllegalArgumentException("n cannot be < 0.");
        }
    
        // Define an array of legal move directions
        String[] moves = {"up", "down", "left", "right"};
        String temp;
    
        // Loop 'n' times to perform random moves
        for (int i = 0; i < n; i++) {
            // Generate a random index to select a move from 'legalMoves' array
            int randomIndex = (int) (4 * Math.random());
            // Get the move direction at the random index
            temp = moves[randomIndex];
            // Perform the selected move
            move(temp);
        }
    }


    public void move(String move) {
        // Convert the input 'move' string to lowercase for case-insensitive comparison
    
        if (move.toLowerCase().equals("up")) {
            // If 'move' is "up," execute the 'moveUp' method and update the 'curState'
            this.curState = moveUp(curState);
        }
    
        if (move.toLowerCase().equals("down")) {
            // If 'move' is "down," execute the 'moveDown' method and update the 'curState'
            this.curState = moveDown(curState);
        }
    
        if (move.toLowerCase().equals("left")) {
            // If 'move' is "left," execute the 'moveLeft' method and update the 'curState'
            this.curState = moveLeft(curState);
        }
    
        if (move.toLowerCase().equals("right")) {
            // If 'move' is "right," execute the 'moveRight' method and update the 'curState'
            this.curState = moveRight(curState);
        }
    }

// The way this works is seeing if we can move the blank tile rather than moving the title
private BState moveUp(BState state) {
    // Check if the blank tile is already in the top row (rows numbered from 0 to 2)
    if (state.myBlankTile < 3) {
        // If the blank tile is in the top row, no movement is possible, so return the current state
        return state;
    }

    // Swap the blank tile with the tile above it (3 positions up)
    state = swap(state.myBlankTile, state.myBlankTile - 3, state);
    // Update the blank tile position accordingly
    state.myBlankTile -= 3;
    // Return the updated state
    return state;
}

private BState moveDown(BState state) {
    // Check if the blank tile is already in the bottom row (rows numbered from 0 to 2)
    if (state.myBlankTile >= 6) {
        // If the blank tile is in the bottom row, no movement is possible, so return the current state
        return state;
    }

    // Swap the blank tile with the tile below it (3 positions down)
    state = swap(state.myBlankTile, state.myBlankTile + 3, state);
    // Update the blank tile position accordingly
    state.myBlankTile += 3;
    // Return the updated state
    return state;
}

private BState moveRight(BState state) {
    // Check if the blank tile is already in the rightmost column (columns numbered from 0 to 2)
    if ((state.myBlankTile + 1) % 3 == 0) {
        // If the blank tile is in the rightmost column, no movement is possible, so return the current state
        return state;
    }

    // Swap the blank tile with the tile to its right (1 position to the right)
    state = swap(state.myBlankTile, state.myBlankTile + 1, state);
    // Update the blank tile position accordingly
    state.myBlankTile++;
    // Return the updated state
    return state;
}

private BState moveLeft(BState state) {
    // Check if the blank tile is already in the leftmost column (columns numbered from 0 to 2)
    if (state.myBlankTile % 3 == 0) {
        // If the blank tile is in the leftmost column, no movement is possible, so return the current state
        return state;
    }

    // Swap the blank tile with the tile to its left (1 position to the left)
    state = swap(state.myBlankTile, state.myBlankTile - 1, state);
    // Update the blank tile position accordingly
    state.myBlankTile--;
    // Return the updated state
    return state;
}





public BState swap(int x, int y, BState state) {
    // Create a temporary string to hold the current state
    String tempState = state.myState;

    // Get the characters at positions 'x' and 'y' in the state string
    char c1 = tempState.charAt(x);
    char c2 = tempState.charAt(y);

    // Create a StringBuilder to build the new state string
    StringBuilder temp = new StringBuilder();

    // Iterate through the characters of the state string
    for (int i = 0; i < 9; i++) {
        if (i == x) {
            // If the current position is 'x', append the character from position 'y'
            temp.append(c2);
        } else if (i == y) {
            // If the current position is 'y', append the character from position 'x'
            temp.append(c1);
        } else {
            // For other positions, append the character from the original state
            temp.append(tempState.charAt(i));
        }
    }

    // Convert the StringBuilder back to a string and update the state's 'myState' field
    tempState = temp.toString();
    state.myState = tempState;

    // Return the updated state
    return state;
}




// This method calculates the first hueristic of the number of missplaced tiles from the goal state
    public int h1(String curState){
        char[] goalState = {'b','1', '2', '3', '4', '5', '6', '7', '8'};
        int numOfMissplaced = 0;

        for(int i = 0; i < 9; i++){
            if(goalState[i] != curState.charAt(i)){
                numOfMissplaced++;
            }
        }
        return numOfMissplaced;
    }

// This method is going to calculate the manhattan distance
    public int h2(String state){
        int distance = 0;
		char temp;
		for(int i = 0; i < 9; i++) {
			temp = state.charAt(i);
			if(temp == 'b') 
				distance += (i-0);
			else
				distance += Math.abs(i-(temp-48));
		}
		return distance;
    }


    public void aStar(String heuristic) throws Exception {
        // Priority queue to store states to explore
        PriorityQueue<BState> pq = new PriorityQueue<BState>();
        // Set to track finished states
        HashSet<String> finished = new HashSet<String>();
        // List to store explored states
        List<BState> myList = new ArrayList<BState>();
    
        // Another set of data structures for a different heuristic
        PriorityQueue<BState> pq2 = new PriorityQueue<BState>();
        HashSet<String> finished2 = new HashSet<String>();
        List<BState> myList2 = new ArrayList<BState>();
    
        // Check which heuristic is selected
        if (heuristic.equals("h1")) {
            // Initialize the priority queue with the initial state
            pq.add(new BState(curState.myState, 0, null));
    
            int count = 0;
            while (!finished.contains("b12345678") && !pq.isEmpty() && count <= maxStates) {
                BState current = pq.remove();
    
                if (finished.contains(current.myState)) {
                    continue;
                }
                finished.add(current.myState);
                myList.add(current);
    
                // Generate child states based on heuristic H1
                List<BState> tempList = current.getH1Child();
                for (int i = 0; i < tempList.size(); i++) {
                    BState temp = tempList.get(i);
    
                    if (!finished.contains(temp.myState)) {
                        pq.add(temp);
                    }
                }
                count++;
            }
    
            if (count > maxStates) {
                throw new Exception("Limit has been hit");
            }
            BState dest = null;
            if (finished.contains("b12345678")) {
                for (int i = 0; i < myList.size(); i++) {
                    if (myList.get(i).myState.equals("b12345678")) {
                        dest = myList.get(i);
                    }
                }
            }
    
            // Reconstruct the path to the goal state
            Stack<BState> path = new Stack<BState>();
            while (dest != null) {
                path.push(dest);
                dest = dest.pState;
            }
    
            int numberOfMoves = 0;
    
            // Print the path and calculate the number of moves
            while (!path.isEmpty()) {
                printBState(path.pop().myState);
                numberOfMoves++;
            }
    
            System.out.printf("The slide puzzle was solved in %d moves and considered %d nodes\n", numberOfMoves - 1, count);
        } else if (heuristic.equals("h2")) {
            // Similar logic for heuristic H2
            // (Note: The code structure is similar to the H1 section with minor differences in heuristic and output messages)
            pq2.add(new BState(curState.myState, 0, null));

            int count = 0;
            while(!finished2.contains("b12345678") && !pq2.isEmpty() && count <= maxStates){
                BState current = pq2.remove();

                if(finished2.contains(current.myState)){
                    continue;
                }
                finished2.add(current.myState);
                myList2.add(current);

                List<BState> tempList = current.getH2Child();
                for(int i = 0; i < tempList.size(); i++){
                    BState temp = tempList.get(i);

                    if(!finished2.contains(temp.myState)){
                        pq2.add(temp);
                    }
                }
                count++;
            }

            if(count > maxStates){
                throw new Exception("Limit has been hit");
            }
            BState dest = null;
            if(finished2.contains("b12345678")){
                for(int i = 0; i < myList2.size(); i++){
                    if(myList2.get(i).myState.equals("b12345678")){
                        dest = myList2.get(i);
                    }
                }
            }

            Stack<BState> path = new Stack<BState>();
            while(dest != null){
                path.push(dest);
                dest = dest.pState;
            }

            int numberOfMoves = 0;
            
            while(!path.isEmpty()){
                printBState(path.pop().myState);
                numberOfMoves++;
            }

            System.out.printf("The slide puzzle could not be solved in %d moves and considered %d nodes.\n", numberOfMoves, count);
        }
    }


    public void beam(int k) throws Exception {
        // Initialize data structures
        List<BState> myList = new ArrayList<BState>();
        PriorityQueue<BState> best = new PriorityQueue<BState>();
        HashSet<String> finished = new HashSet<String>();
        BState goal = null;
    
        // Add the initial state to the list
        myList.add(this.curState);
    
        // Check if the initial state is the goal state
        if (this.curState.myState.equals("b12345678")) {
            System.out.println("The puzzle is completed!");
            System.out.println("We used 0 moves and considered 0 nodes.");
            return;
        }
    
        int count = 0; // Count the total number of nodes considered
        boolean found = false; // Flag to indicate if the goal state is found
    
        // Start the main loop for beam search
        while (!myList.isEmpty() && !found && count <= maxStates) {
            best = new PriorityQueue<BState>(); // Initialize the priority queue for best states
    
            // Explore child states of current states
            for (int i = 0; i < myList.size(); i++) {
                BState curr = myList.get(i);
                finished.add(curr.myState);
                List<BState> tempList = curr.getBeamChild();
    
                // Add unexplored child states to the priority queue
                for (int j = 0; j < tempList.size(); j++) {
                    BState temp = tempList.get(j);
                    if (finished.contains(temp.myState))
                        continue;
                    best.add(temp);
                }
            }
    
            myList = new ArrayList<BState>(); // Clear the current list of states
    
            // Add the top k states from the priority queue to the current list
            for (int i = 0; i < k && !best.isEmpty(); i++) {
                myList.add(best.remove());
                if (myList.get(i).myState.equals("b12345678")) {
                    found = true; // Goal state found
                    goal = myList.get(i);
                }
            }
    
            count += 8; // Increment the count by k
        }
    
        // Check if the maximum state limit has been reached
        if (count > maxStates)
            throw new Exception("Maximum limit has been reached");
    
        boolean solved = false;
    
        // Check if a solution was found
        if (goal != null) {
            System.out.println("We solved the puzzle!");
            solved = true;
        }
    
        Stack<BState> path = new Stack<BState>();
    
        // Backtrack and store the path to the goal state
        while (goal != null) {
            path.push(goal);
            goal = goal.pState;
        }
    
        int numMoves = 0;
    
        // Print the path and count the number of moves
        while (!path.isEmpty()) {
            printBState(path.pop().myState);
            numMoves++;
        }
    
        if (solved)
            System.out.printf("We solved the puzzle and used %d moves and considered %d nodes.\n", numMoves, count);
        else
            System.out.printf("We couldn't solve the puzzle and used %d moves and considered %d nodes.\n", numMoves, count);
    }
    


    public void read(String file) throws Exception {

        // Create a BufferedReader to read from the specified file
        BufferedReader br = new BufferedReader(new FileReader(new File(file)));
        String line;
    
        // Read each line from the file
        while ((line = br.readLine()) != null) {
    
            // Check if the line starts with "move"
            if (line.substring(0, 4).equals("move")) {
                // Call the move method with the substring starting from the 5th character
                move(line.substring(5));
            }
            // Check if the line starts with "setState"
            else if (line.substring(0, 8).equals("setState")) {
                // Extract a substring to get the state information and call setState
                String s = line.substring(9, 12) + line.substring(13, 16) + line.substring(17, 20);
                setState(s);
            }
            // Check if the line starts with "maxNodes"
            else if (line.substring(0, 8).equals("maxNodes")) {
                // Parse the integer after "maxNodes" and call maxNodes method
                maxNodes(Integer.parseInt(line.substring(9)));
            }
            // Check if the line starts with "solve beam"
            else if (line.substring(0, 10).equals("solve beam")) {
                // Parse the integer after "solve beam" and call beam method
                beam(Integer.parseInt(line.substring(11)));
            }
            // Check if the line starts with "solve A-Star"
            else if (line.substring(0, 12).equals("solve A-Star")) {
                // Call aStar method with the substring starting from the 13th character
                aStar(line.substring(13));
            }
            // Check if the line starts with "ramdomizeState"
            else if (line.substring(0, 14).equals("ramdomizeState")) {
                // Parse the integer after "ramdomizeState" and call randomizeState method
                randomizeState(Integer.parseInt(line.substring(15)));
            }
        }
    
    }
    

// This method pints the current board states in a 3 X 3 format to make it easier to visualize the puzzle board. 
    public void printBState(String State){
        System.out.printf("%s\n", State.substring(0, 3));
        System.out.printf("%s\n", State.substring(3, 6));
        System.out.printf("%s\n", State.substring(6, 9));
        System.out.println();

    }

}



	