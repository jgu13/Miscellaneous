# Dog_Shelter

A `DogShelter` class that uses heap and BST to represent a dog shelter. It inserts, delete dog according to their ages and number of days stayed in the shelter. 

The method `shelter(Dog d)` insert a dog into the tree and upheap until the order of the tree is restored.

`adopt(Dog d)` remove a specific dog from the shelter

`adopt()` remove a dog that has highest priority , which is also a dog that has stayed in the shelter for the longest time.

 `budgetVetExpenses(int numDays)` returns the expected vet cost the shelter has to incur in the next `numDays` days

`getVetSchedule()` traverse the tree and return an `arraylist` of dogs that need to see the vet next week.

To run the tester, go to `Java\Dog_Shelter\src\test\java\assignment3` From the folder, run `Minitester.java` 


