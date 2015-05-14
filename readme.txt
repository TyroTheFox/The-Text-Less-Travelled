	


	Project : zuul-better (CZ101 Assignement 2)
	Version : 1.10
	Student : Kieran Clare
	Date    : 08 of March, 2012


 Modifications
 =====================

1. Created a new class called RunZuul with a main()method that constructs a new instance of Game and calls its 
play() method.

2. Created display() method that will take strings and print them to the screen. It is dependent on the boolean 
variable debug_mode.

3. Created look command for interface that will re-display the room description.

4. Created weight variable in the Room class that is initialised in the Game class then printed to console.

5. Created examine command to describe items within a room.

6. Item class allows for the addition of multiple items within a single room.

7. Implemented a back command to allow for a quick way of back tracking through the game world.
   
8. Created a TestGame class to test the games back command.

9. Player class created where the user can switch between any number of different characters to play as. Each
will be able to walk around the game world independent of each other. 

10. Inventory system that allows for the adding and dropping of items. It updates the room class with removed items
and stores all items into the inventory.

11. Added Width Limited Output Stream created that allows for a regular formating on the output to the console.

12. A very silly story has been added as well as a win condition. THe player is looking for the Sword of Zuul.