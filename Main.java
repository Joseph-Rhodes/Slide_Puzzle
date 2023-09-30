public class Main {

    public static void main(String[] args) throws Exception{

        SlidePuzzle test = new SlidePuzzle();

		
		//foo.randomizeState(10000);
		// foo.maxNodes(1000);
        
		// test.maxNodes(1000);
        test.setState("87564231b");
        // test.maxNodes(Integer.MAX_VALUE);
        // test.randomizeState(10);
        test.printState();

        

        // test.aStar("h2");

        test.beam(Integer.MAX_VALUE);
        // test.beam(5);
        // test.beam(Integer.MAX_VALUE);

		
			// long time1 = System.nanoTime();
            // test.aStar("h2");
			// long time2 = System.nanoTime();
			// System.out.printf("The runtime was %d nanoseconds\n",(time2-time1));
		
		
		
		
    }



}