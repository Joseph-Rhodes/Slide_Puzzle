import org.junit.Test;
import static org.junit.Assert.*;

public class SlidePuzzleTester{
    
    @Test
    public void setStateTest(){
        SlidePuzzle test = new SlidePuzzle();
        String real = "123b45687";

        try {
            test.setState("b1234567890");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        try {
            test.setState("128");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        try {
            test.setState("b1234567b");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            test.setState("abcdes");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        assertTrue(real.equals("123b45687"));
    }


    @Test
    public void moveTest(){
        SlidePuzzle test = new SlidePuzzle();


        test.move("up");
        test.move("left");
        assertTrue(test.getState().equals("b12345678"));

        test.move("right");

        assertTrue(test.getState().equals("1b2345678"));

        test.move("down");
        assertTrue(test.getState().equals("1423b5678"));

    }

    
}