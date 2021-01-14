# Jiayao Gu
# 260830725 

import random
import matplotlib.pyplot as plt

class Animal:
    
    # Initializer method
    def __init__(self, my_species, row, column):
        """ Constructor method
        Args:
           self (Animal): the object being created
           my_species (str): species name ("Lion" or "Zebra")
           row (int): row for the new animal
           column (int): column for the new animal
        Returns:
           Nothing
        Behavior:
           Initializes a new animal, setting species to my_species
        """               
        self.species = my_species
        self.row = row
        self.col = column
        self.age = 0
        self.time_since_last_meal = 0

    def __str__(self):
        """ Creates a string from an object
        Args:
           self (Animal): the object on which the method is called
        Returns:
           str: String summarizing the object
        """
        s= self.species+" at position ("+str(self.row)+","+str(self.col)+"):, age="+str(self.age)+", time_since_last_meal="+\
           str(self.time_since_last_meal)
        return s
    
    def can_eat(self, other):
        """ Checks if self can eat other
        Args:
           self (Animal): the object on which the method is called
           other (Animal): another animal
        Returns:
           boolean: True if self can eat other, and False otherwise
        """          
        # WRITE YOUR CODE FOR QUESTION 3 HERE
        if self.species == "Lion" and other.species == "zebra":
            return True
        else:
            return False

    def time_passes(self):
        """ Increases age and time_since_last_meal
        Args:
           self (Animal): the object on which the method is called
        Returns:
           Nothing
        Behavior:
           Increments age and time_since_last_meal
        """           
        # WRITE YOUR CODE FOR QUESTION 4 HERE
        self.age += 1
        self.time_since_last_meal += 1
        return 

    def dies_of_old_age(self):
        """ Determines if an animal dies of old age
        Args:
           self (Animal): the object on which the method is called
        Returns:
           boolean: True if animal dies of old age, False otherwise
        """          
        # WRITE YOUR CODE FOR QUESTION 5 HERE
        if (self.species == "Lion" and self.age >= 18) or (self.species == "Zebra" and self.age >= 7):
               return True
        else:
               return False

    def dies_of_hunger(self):
        """ Determines if an animal dies of hunger
        Args:
           self (Animal): the object on which the method is called
        Returns:
           boolean: True if animal dies of hunger, False otherwise
        """           
         # WRITE YOUR CODE FOR QUESTION 6 HERE 
        if self.species == "Lion" and self.time_since_last_meal >= 6:
            return True
        else: 
            return False    
        
    def will_reproduce(self):
        """ Determines if an animal will reproduce this month
        Args:
           self (Animal): the object on which the method is called
        Returns:
           boolean: True if ready to reproduce, False otherwise
        """          
        # WRITE YOUR CODE FOR QUESTION 7 HERE
        if self.species=="Zebra":
            if self.age==3 or self.age==6 and self.dies_of_old_age()==False:
                return True
            else:
                return False
        elif self.species=="Lion":
            if self.age==7 or self.age==14 and self.age<18 and self.dies_of_hunger()==False: 
                return True
            else:
                return False

    # end of Animal class

def initialize_population(grid_size):
    """ Initializes the grid by placing animals onto it.
    Args:
       grid_size (int): The size of the grid
    Returns:
       list of animals: The list of animals in the ecosystem
    """     
    all_animals=[]
    all_animals.append(Animal("Lion",3,5))
    all_animals.append(Animal("Lion",7,4))
    all_animals.append(Animal("Zebra",2,1))     
    all_animals.append(Animal("Zebra",5,8))
    all_animals.append(Animal("Zebra",9,2))
    all_animals.append(Animal("Zebra",4,4))
    all_animals.append(Animal("Zebra",4,8))
    all_animals.append(Animal("Zebra",1,2))
    all_animals.append(Animal("Zebra",9,4))
    all_animals.append(Animal("Zebra",1,8))
    all_animals.append(Animal("Zebra",5,2))
   
    return all_animals
               

def print_grid(all_animals, grid_size):
    """ Prints the grid
    Args:
       all_animals (list of animals): The animals in the ecosystem
       grid_size (int): The size of the grid
    Returns:
       Nothing
    Behavior:
       Prints the grid
    """   
    
    #get the set of tuples where lions and zebras are located
    lions_tuples = { (a.row,a.col) for a in all_animals if a.species=="Lion"}
    zebras_tuples = { (a.row,a.col) for a in all_animals if a.species=="Zebra"}

    print("*"*(grid_size+2))
    for row in range(grid_size):
        print("*",end="")
        for col in range(grid_size):
            if (row,col) in lions_tuples:
                print("L",end="")
            elif (row,col) in zebras_tuples:
                print("Z",end="")
            else:
                print(" ",end="")
        print("*")
    print("*"*(grid_size+2))


def sort_animals(all_animals):
    """ Sorts the animals, left to right and top to bottom
    Args:
       all_animals (list of animals): The animals in the ecosystem
    Returns:
       Nothing
    Behavior:
       Sorts the list of animals
    """   
    def get_key(a):
        return a.row+0.001*a.col
    all_animals.sort(key=get_key)
    
    
def my_random_choice(choices):
    """ Picks ones of the elements of choices
    Args:
       choices (list): the choices to choose from
    Returns:
       One of elements in the list
    """
    if not choices:
        return None
    
    # for debugging purposes, we use this fake_random_choice function
    def getKey(x):
        return x[0]+0.001*x[1]
    return min(choices, key=getKey)    

    # for actual random selection, replace the above this:
    #return random.choice(choices)


def list_neighbors(current_row, current_col, grid_size):
    """ Produces the list of neighboring positions
    Args:
       current_row (int): Current row of the animal
       current_col (int): Current column of the animal
       grid_size (int): The size of the gride
    Returns:
       list of tuples of two ints: List of all position tuples that are 
                                   around the current position, without 
                                   including positions outside the grid
    """
    # WRITE YOUR CODE FOR QUESTION 1 HERE
    i = current_row
    j = current_col
    List = []
    for row_increment in range(-1,2):
        new_row = i + row_increment
        for col_increment in range(-1,2):
            new_col = j + col_increment
            if  new_col == j and new_row == i:
                pass
            elif (new_row > 0 and new_row < grid_size) and (new_col > 0 and new_col < grid_size):
                List.append((new_row, new_col))
    return List
                
                           
def random_neighbor(current_row, current_col, grid_size, only_empty=False, animals=[]):
    """ Chooses a neighboring positions from current position
    Args:
       current_row (int): Current row of the animal
       current_col (int): Current column of the animal
       size (int): Size of the grid
       only_empty (boolean): keyword argument. If True, we only consider 
                             neighbors where there is not already an animal
       animals (list): keyword argument. List of animals present in the ecosystem
    Returns:
       tuple of two int: A randomly chosen neighbor position tuple

    """   
    # WRITE YOUR CODE FOR QUESTION 2 HERE
    all_neighbors = [n for n in list_neighbors(current_row, current_col, grid_size)]
    next_cell = my_random_choice(all_neighbors)
    i = next_cell[0]
    j = next_cell[1]

    if not(only_empty):
        return next_cell
    else:
        for animal in animals:
            if animal.row == i and animal.col == j:
                all_neighbors.remove((animal.row,animal.col))
        if len(all_neighbors) == 0:
            return None
        return next_cell #next_cell does not have an animal


def one_step(all_animals, grid_size):
    """ simulates the evolution of the ecosystem over 1 month
    Args:
       all_animals (list of animals): The animals in the ecosystem
       grid_size (int): The size of the grid
    Returns:
       list fo str: The events that took place
    Behavior:
       Updates the content of animal_grid by simulating one time step
    """      
    sort_animals(all_animals)  # ensures that the animals are listed 
                               # from left to right, top to bottom     
    
    # run time_passes on all animals
    for animal in all_animals:
        animal.time_passes()
    # make animals die of old age
    events = []
    dead = []
    for animal in all_animals:
        if(animal.dies_of_old_age()):
            dead.append(animal)
            events.append("{} dies of old age at position {} {}".format(animal.species, animal.row, animal.col)) 
    # make animals die of hunger
        elif(animal.dies_of_hunger()):
            dead.append(animal)
            events.append("{} dies of hunger at position {} {}".format(animal.species,animal.row, animal.col))

    for a in all_animals:
        if a in dead:
            all_animals.remove(a)
   
    # move animals
    new_row, new_col = random_neighbor(animal.row, animal.col, grid_size, False, all_animals)
    eaten = []
    for animal in all_animals: 
        # search for animal that on the new cell
        present_animal = [n for n in all_animals if n.row == new_row and n.col == new_col and n not in eaten]
        #there is an animal in the new cell
        if present_animal != []:
               # search for the animal at the new_position
                #3 cases:
               # if a lion moves to a cell contains a zebra, lion eats zebra and lion moves to new_position, zebra is removed from the cel
            present_animal = present_animal[0]
            if animal.can_eat(present_animal):
                events.append(animal.species+" moves from "+str(animal.row)+" "+str(animal.col)+" to "+str(new_row)+" "+str(new_col)+" and eats a zebra")
                animal.row = new_row #update position
                animal.col = new_col
                animal.time_since_last_meal = 0 #update the time since last meal
                eaten.append(present_animal)
                # if a zebra moves to a cell contains a lion, zebra is eaten and removed from its position
            elif present_animal.can_eat(animal):
                events.append(animal.species+" moves from "+str(animal.row)+" "+str(animal.col)+" to "+str(new_row)+" "+str(new_col)+ " and is eaten by a lion")
                eaten.append(animal)
                present_animal.time_since_last_meal = 0
                # if the same animals present, animals stay, nothing happens
            else: # two animals are the same species
                events.append(animal.species+" moves from "+str(animal.row)+" "+str(animal.col)+" to "+str(new_row)+" "+str(new_col)+ " but there is an animal of the same species")

        #new cell does not contain an animal
        else:
            events.append(animal.species+" moves from "+str(animal.row)+" "+str(animal.col)+" to "+str(new_row)+" "+str(new_col))
            animal.row = new_row
            animal.col = new_col
      
    for a in all_animals:
        if a in eaten:
            all_animals.remove(a)
    # since animals have moved, we sort the list of animals again, so that
    # we consider them for reproduction in the right order    
    sort_animals(all_animals)

    babies = []
    # reproduce animals
    for animal in all_animals:
        if animal.will_reproduce():
            result =  random_neighbor(animal.row, animal.col, grid_size, False, all_animals)
            if result == None:
                return;
            else: # new cell is empty
                baby_row, baby_col = result
                all_animals.append(Animal(animal.species, baby_row, baby_col)) #add baby to all_animals list
                events.append("A new baby {} is born at {} {}".format(animal.species, baby_row, baby_col))

def run_whole_simulation(grid_size = 10, simulation_duration = 20, image_file_name="population.png"):
    """ Executes the entire simulation
    Args:
       grid_size (int): Size of the grid
       simulation_duration (int): Number of steps of the simulation
       image_file_name (str): name of image to be created.
    Returns:
       Nothing
    Behavior:
       Simulates the evolution of an animal grid
       Generates graph of species abundance and saves it to populations.png
    """       
    # Do not change this; this initializes the animal population
    all_animals = initialize_population(grid_size)
    # WRITE YOUR CODE FOR QUESTION 9 HERE
    lions = []
    zebras = []
    for time in range(simulation_duration):
        num_of_lions = 0
        num_of_zebras = 0
        for animal in all_animals:
            if animal.species == "Lion":
                num_of_lions += 1
            elif animal.species == "Zebra":
                num_of_zebras += 1
        lions.append(num_of_lions)
        zebras.append(num_of_zebras)
        one_step(all_animals, grid_size)

    plt.plot(range(simulation_duration), lions, "b", label="Lions")
    plt.plot(range(simulation_duration), zebras, "r", label = "zebras")
    plt.xlabel("time")
    plt.ylabel("Number of individuals")
    plt.legend(loc = "best")
    plt.savefig(image_file_name)



  
    