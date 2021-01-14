package assignment3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DogShelter implements Iterable<Dog> {
	public DogNode root;
	public int size;

	public DogShelter(Dog d) {
		this.root = new DogNode(d);
		this.size =0;
	}

	private DogShelter(DogNode dNode) {
		this.root = dNode;
	}


	// add a dog to the shelter
	public void shelter(Dog d) {
		if (root == null) 
			root = new DogNode(d);
		else
			root = root.shelter(d);
			this.size++;
	}

	// removes the dog who has been at the shelter the longest
	public Dog adopt() {
		if (root == null)
			return null;

		Dog d = root.d;
		root =  root.adopt(d);
		size --;
		return d;
	}
	
	// overload adopt to remove from the shelter a specific dog
	public void adopt(Dog d) {
		if (root != null) {
			root = root.adopt(d);
		}
	}


	// get the oldest dog in the shelter
	public Dog findOldest() {
		if (root == null)
			return null;
		
		return root.findOldest();
	}

	// get the youngest dog in the shelter
	public Dog findYoungest() {
		if (root == null)
			return null;
		
		return root.findYoungest();
	}
	
	// get dog with highest adoption priority with age within the range
	public Dog findDogToAdopt(int minAge, int maxAge) {
		return root.findDogToAdopt(minAge, maxAge);
	}

	// Returns the expected vet cost the shelter has to incur in the next numDays days
	public double budgetVetExpenses(int numDays) {
		if (root == null)
			return 0;
		
		return root.budgetVetExpenses(numDays);
	}
	
	// returns a list of list of Dogs. The dogs in the list at index 0 need to see the vet in the next week. 
	// The dogs in the list at index i need to see the vet in i weeks. 
	public ArrayList<ArrayList<Dog>> getVetSchedule() {
		if (root == null)
			return new ArrayList<ArrayList<Dog>>();
			
		return root.getVetSchedule();
	}


	public Iterator<Dog> iterator() {
		return new DogShelterIterator();
	}


	public class DogNode {
		public Dog d;
		public DogNode younger;
		public DogNode older;
		public DogNode parent;

		public DogNode(Dog d) {
			this.d = d;
			this.younger = null;
			this.older = null;
			this.parent = null;
		}

		public DogNode shelter (Dog d) {
            // ADD YOUR CODE HERE
			//BSTadd determines a node where the d should be inserted
			if(root == null){
				DogNode newDog = new DogNode(d);
				root = newDog;
				return root;
			}
			else{
				//recursively insert the dog into the BST
				insert(d, root);
			}

            return root; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
		}

		private void insert(Dog key, DogNode root){
			if(key.compareTo(root.d) < 0){
				if(root.younger == null){
					root.younger = new DogNode(key);
					root.younger.parent = root;
					upHeap(root.younger);
					return;
				}
				else{ //insert in the left subtree
					insert(key, root.younger);
				}
			}
			else { //key > root.d
				if(root.older == null){
					root.older = new DogNode(key);
					root.older.parent = root;
					upHeap(root.older);
					return;
				}
				else{ //insert in the right subtree
					insert(key, root.older);
				}
			}
		}

		private void upHeap(DogNode node){
			if(node == root){ //if it is the dog with the max priority, do nothing
				return;
			}
			else{
				while(node != root){ //if it is not at the root and it stays longer than its parent,
					Dog parentDog = node.parent.d; //update parentDog
					if(compareDays(node.d, parentDog) > 0) {
						//if the node is on the left
						//right rotate
						if (node.parent.younger == node) {  //if parent.left = node
							right_rotation(node.parent, node);
						}
						//if the node is on the right
						//left rotate
						else if (node.parent.older == node) {  //if parent.right = node
							left_rotation(node.parent, node);
						}
					}
					else{ node = node.parent;}
				}
			}
		}

		private void right_rotation(DogNode parent, DogNode leftChild){
			DogNode P = parent.parent;
			//DogNode successor = parent.older;
			parent.younger = leftChild.older;  //parent.left = leftChild.right;
			if(leftChild.older != null) {
				leftChild.older.parent = parent;  //leftChild.right.parent = parent
			}
			leftChild.older = parent; //leftChild.right = parent
			leftChild.parent = P;  //leftChild.parent = P
			parent.parent = leftChild; //parent.parent = leftChild
			//updating P.child
			if(P != null) {  //if node is not the root
				if (P.younger == parent) {  //if(P.left == parent)
					P.younger = leftChild;
				} else {
					P.older = leftChild;
				}
			}else{  //if P == null, make the leftChild the new root
				root = leftChild;
			}
		}
		private void left_rotation(DogNode parent, DogNode rightChild){
			DogNode P = parent.parent; //superior
			//DogNode successor = parent.older;
			parent.older = rightChild.younger;  //parent.right = rightChild.left;
			if(rightChild.younger != null) {
				rightChild.younger.parent = parent;  //rightChild.left.parent = parent
			}
			rightChild.younger = parent; //rightChild.left = parent
			rightChild.parent = P;  //rightChild.parent = P
			parent.parent = rightChild; //parent.parent = rightChild

			//updating P.child
			if(P != null) {  //if node is not the root
				if (P.younger == parent) {  //if(P.left == parent)
					P.younger = rightChild;
				} else {
					P.older = rightChild;
				}
			}else{  //P == null, then make the rightchild root
				root = rightChild; //If P== null, this means righChild is the new root
			}
		}
		private int compareDays(Dog d, Dog parent){
			return d.getDaysAtTheShelter() - parent.getDaysAtTheShelter();
		}
		/*private void setYounger(Dog key, DogNode root){
			root.younger = new DogNode(key);
		}
		private void setOlder(Dog key, DogNode root){
			root.older = new DogNode(key);
		}*/

		public DogNode adopt(Dog d) {
            // ADD YOUR CODE HERE
			//recursively delete the dog
			DogNode start = null;  //which node to start downHeap
			DogNode newRoot = remove(d, this);  //remove the key dog, also find the starting root

            return newRoot;
		}
		/*private DogNode find(Dog key, DogNode root){
			if(root == null) return null;
			if(key.compareTo(root.d) == 0) return root;
			if(key.compareTo(root.d) < 0){  //if the key is smaller,go to left
				return find(key, root.younger);
			}
			else{  //if the key is larger, go to right
				return find(key, root.older);
			}
		}*/
		private DogNode remove(Dog key, DogNode node){
			if(node == null) return node;
			else if(key.compareTo(node.d) < 0){  //if the key is smaller than root
				node.younger = remove(key, node.younger);
			}else if(key.compareTo(node.d) > 0){  //if the key is larger than the root
				node.older = remove(key, node.older);
			}
			else {//root.d == key
				if (node.younger == null) {
					if (node.older != null) node.older.parent = node.parent;
					DogNode rightTree = node.older;
					node.parent = null; //clear target's parent pointer
					node.older = null; //clear target's right pointer
					node = rightTree;
				} else if (node.older == null) {
					if (node.younger != null) node.younger.parent = node.parent;
					DogNode leftTree = node.younger;
					node.parent = null; //clear target's parent pointer
					node.younger = null; //clear target's left pointer
					node = leftTree;
				} else {
					//root has both left and right child
					Dog oldD = (node.younger).findOldest();  //find the oldest of left subtree
					node.d = oldD; //change the element to oldD, not changing its parent
					node.younger = remove(node.d, node.younger);   //remove the duplicated leaf node
					node = downHeap(node);
				}
			}
			return node;
		}
		private DogNode downHeap(DogNode root){
			if(root.older == null && root.younger == null) return root; //we have leaf node
			else {
				DogNode child = root.older;
				//if the left node has days more than the right node
				if (root.younger != null) {
					if(root.older != null) {
						if (compareDays(root.younger.d, root.older.d) > 0)
							child = root.younger;
					}
					else{
						child = root.younger;
					}
				}
				if (compareDays(root.d, child.d) < 0) { //if the start node has less days that child node
					if (child == root.younger) {
						//if the left child has less days, do right rotation
						right_rotation(root, child);
					}
					else {
						//if the right child has less days, do left rotation
						left_rotation(root, child);
					}
					downHeap(root);
					root = child;
				}
			}
			return root;
		}
		/*private int leftIndex(int i){
			return i*2;
		}
		private int rightIndex(int i){
			return i*2+1;
		}
		private int parentIndex(int i){
			return i/2;
		}*/
		public Dog findOldest() {
            // ADD YOUR CODE HERE
			if(this.older == null) return this.d;
			else{
				return this.older.findOldest();
			}
		}

		public Dog findYoungest() {
            // ADD YOUR CODE HERE
			if(this.younger == null) return this.d;
			else{
				return this.younger.findYoungest();
			}
		}

		public Dog findDogToAdopt(int minAge, int maxAge) {
            // ADD YOUR CODE HERE
			return findDogToAdoptHelper(root, minAge, maxAge);
		}

		public Dog findDogToAdoptHelper(DogNode root, int minAge, int maxAge) {
			// ADD YOUR CODE HERE
			if(root == null)
				return null; //no such dog exists
			if(root.d.getAge() >= minAge && root.d.getAge() <= maxAge)
				return root.d;
			Dog d_younger = findDogToAdoptHelper(root.younger, minAge, maxAge); //first dog in the age range in the younger dogs
			Dog d_older = findDogToAdoptHelper(root.older, minAge, maxAge); //first dog in the age range in the older dogs
			Dog d;
			if(d_older == null) {
				d = d_younger; //no such dog in the older dogs
			}
			else if(d_younger == null){
				d = d_older; //no such dog in the younger dogs
			}
			else{
				//compare the older dogs with the younger dogs for their priority
				if(d_younger.getDaysAtTheShelter() > d_older.getDaysAtTheShelter())
					d = d_younger;
				else
					d = d_older;
			}
			return d;
		}
		
		public double budgetVetExpenses(int numDays) {
            // ADD YOUR CODE HERE
			if(this == null) return 0.0;
			return helperBudget(this, numDays);
		}

		private double helperBudget(DogNode root, int numDays){
			if(root == null) return 0.0;
			double sum = helperBudget(root.younger, numDays) + helperBudget(root.older, numDays);
			if(root.d.getDaysToNextVetAppointment() <= numDays)
				sum += root.d.getExpectedVetCost();
			return sum;
		}

		public ArrayList<ArrayList<Dog>> getVetSchedule() {
            // ADD YOUR CODE HERE
			int initialCapacity = 10;
			ArrayList<ArrayList<Dog>> list = new ArrayList<ArrayList<Dog>>(initialCapacity); //list to put in lists of dogs
			//intialize ArrayList with size ArrayList
			list.add(new ArrayList<Dog>());
			/*for(int i=0; i<=10; i++){
				list.add(new ArrayList<Dog>());
			}
			*/
			DogShelterIterator iterator = new DogShelterIterator();
			while(iterator.hasNext()){
				Dog nextDog = iterator.next();
				int index = nextDog.getDaysToNextVetAppointment()/7;
				if(index < list.size())list.get(index).add(nextDog);
				else{
					//index out of range --> create larger list
					ArrayList<ArrayList<Dog>> newList = new ArrayList<ArrayList<Dog>>(index+1);
					newList.addAll(list); //add all lists from the old list
					for(int i=0; i<(index+1)-list.size(); i++){ //initialize the other half with new ArrayLists
						newList.add(new ArrayList<Dog>());
					}
					list = newList; //update list to the larger newList
					list.get(index).add(nextDog);
				}
			}
            return list;
		}

		public String toString() {
			String result = this.d.toString() + "\n";
			if (this.younger != null) {
				result += "younger than " + this.d.toString() + " :\n";
				result += this.younger.toString();
			}
			if (this.older != null) {
				result += "older than " + this.d.toString() + " :\n";
				result += this.older.toString();
			}
			/*if (this.parent != null) {
				result += "parent of " + this.d.toString() + " :\n";
				result += this.parent.d.toString() +"\n";
			}*/
			return result;
		}

	}


	private class DogShelterIterator implements Iterator<Dog> {
		// HERE YOU CAN ADD THE FIELDS YOU NEED
		ArrayList<DogNode> left_nodes;

		private DogShelterIterator() {
			//YOUR CODE GOES HERE
			left_nodes = new ArrayList<DogNode>();
			DogNode rt = root;
			this.init_left(rt); //initialize the stack with all left nodes starting from root
		}
		private void init_left(DogNode rt){
			while(rt!=null){
				left_nodes.add(rt);
				rt = rt.younger;
			}
		}

		public Dog next(){
            //YOUR CODE GOES HERE
			if(left_nodes.size() == 0){
				throw new NoSuchElementException("No more dogs!");
			}
			DogNode leftmostDog = left_nodes.remove(left_nodes.size()-1); //the last dog is the leftmost Dog
			if(leftmostDog.older != null) {
				this.init_left(leftmostDog.older); //initialize the all the left nodes of the right node
			}
			return leftmostDog.d;
		}

		public boolean hasNext() {
            //YOUR CODE GOES HERE
            return !left_nodes.isEmpty(); // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
		}

	}
	/*public static void main(String[] args){
		Dog R = new Dog("Rex", 8, 100, 5, 50.0);
		Dog S = new Dog("Stella", 5, 50, 2, 250.0);
		Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
		Dog L = new Dog ("Lessie", 3, 70, 9, 25.0 );
		Dog B = new Dog ("Bella", 1, 55, 15, 120.0 );
		Dog C = new Dog ("Cloud", 4, 10, 23, 80.0 );
		Dog D = new Dog ("Daisy", 7, 15, 12, 35.0 );

		DogShelter test = new DogShelter(R);

		System.out.println("Testing shelter() #1 [0 tree rotation]... ");
		test.shelter(L);
		test.shelter(P);
		test.shelter(B);
		test.shelter(S);
		test.shelter(C);
		test.shelter(D);

		//System.out.println(test.root.toString());

		//testFindDogToAdopt(test.root);
		//testFindYoungest(test.root);
		//testFindOldest(test.root);
		//testVetExpence(test.root);
		//testVetSchedule(test.root);
		testAdopt(test.root, S);
	}

	public static void testFindDogToAdopt(DogNode root){
		Dog dog =(root.findDogToAdopt(8,10));
		System.out.println(dog);
	}
	public static void testFindYoungest(DogNode root){
		System.out.println(root.findYoungest());
	}
	public static void testFindOldest(DogNode root){
		System.out.println(root.findOldest());
	}
	public static void testVetExpence(DogNode root){
		System.out.println("The expense is: " + root.budgetVetExpenses(10));
	}
	public static void testVetSchedule(DogNode root){
		System.out.println("Schedule:\n" + root.getVetSchedule());
	}
	public static void testAdopt(DogNode root, Dog key){
		root = root.adopt(key);
		System.out.println(root.toString());
	}*/


}
