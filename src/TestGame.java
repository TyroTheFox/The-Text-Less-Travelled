/**
 * The Test Game Class
 * A class to test various parts of the program. It's only been set up to make sure the back command works.
 * 
 * @author Kieran Clare
 * @version 1.14 (March 2012)
 */
public class TestGame
{
	// Declares each command required by the test() method as Commands. 
	Command south, east;
	
	/**
	 * The constructor that creates new instances of each command.
	 */
    public TestGame()
    {
    	System.out.println("Powered By Boredom Test Systems - Online");
    	south = new Command("go", "south");
    	east = new Command("go", "east");
    }

    /**
     * This method runs the commands for the test game as well as displaying the messages, fake or otherwise.
     */
    public void test(){
    	// An aesthetic loading screen.
        System.out.println("Initiating Back command test...");
        try {
 		Thread.currentThread();
		Thread.sleep(1000);
        } 
        catch (InterruptedException e) {
 		System.out.println("~ Error: 89293838190487123 + X*9218 ~");
 		e.printStackTrace();
        }
        // Creates new instance of Game Class to test in.
        Game testGame;
        testGame = new Game();
        
        // Tests the back command.
        // Move south.
        System.out.println("Test: Moved south");
        System.out.println("");
    	testGame.logic.processTestCommand(south);
    	
    	// Move east
        System.out.println("Test: Moved east");
        System.out.println("");
        testGame.logic.processTestCommand(east);
        
        // Backtrack a room.
        System.out.println("Test: Back");
        System.out.println("");
        testGame.logic.testBack();
    	
    	// Backtrack a room.
        System.out.println("Test: Back");
        System.out.println("");
        testGame.logic.testBack();
    	
    	// Backtrack again to make sure the player cannot backtrack further than the first place he arrived.
        System.out.println("Test: Back");
        System.out.println("");
        testGame.logic.testBack();
    	System.out.println("Test Game Completed - Bowered By Boredom Test Systems - Powering Down");
        
    }
    

}
