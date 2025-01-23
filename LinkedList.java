import java.util.function.Function;

/**
 * Represents a list of Nodes.
 */
public class LinkedList {

	private Node first; // pointer to the first element of this list
	private Node last; // pointer to the last element of this list
	private int size; // number of elements in this list

	/**
	 * Constructs a new list.
	 */
	public LinkedList() {
		first = null;
		last = first;
		size = 0;
	}

	/**
	 * Gets the first node of the list
	 * 
	 * @return The first node of the list.
	 */
	public Node getFirst() {
		return this.first;
	}

	/**
	 * Gets the last node of the list
	 * 
	 * @return The last node of the list.
	 */
	public Node getLast() {
		return this.last;
	}

	/**
	 * Gets the current size of the list
	 * 
	 * @return The size of the list.
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Gets the node located at the given index in this list.
	 * 
	 * @param index
	 *              the index of the node to retrieve, between 0 and size
	 * @throws IllegalArgumentException
	 *                                  if index is negative or greater than the
	 *                                  list's size
	 * @return the node at the given index
	 */
	public Node getNode(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		Node currentBlockNode = this.first;
		for (int i = 0; i < index; i++) {
			currentBlockNode = currentBlockNode.next;
		}
		return currentBlockNode;
	}

	/**
	 * Creates a new Node object that points to the given memory block,
	 * and inserts the node at the given index in this list.
	 * <p>
	 * If the given index is 0, the new node becomes the first node in this list.
	 * <p>
	 * If the given index equals the list's size, the new node becomes the last
	 * node in this list.
	 * <p>
	 * The method implementation is optimized, as follows: if the given
	 * index is either 0 or the list's size, the addition time is O(1).
	 * 
	 * @param block
	 *              the memory block to be inserted into the list
	 * @param index
	 *              the index before which the memory block should be inserted
	 * @throws IllegalArgumentException
	 *                                  if index is negative or greater than the
	 *                                  list's size
	 */
	public void add(int index, MemoryBlock block) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		size++;
		Node newNode = new Node(block);
		if (index == 0) { // first
			newNode.next = first;
			first = newNode;
			if (this.last == null) {
				this.last = first;
			}
			return;
		}
		if (index == size - 1) { // last
			last.next = newNode;
			last = newNode;
			return;
		}
		Node currentNode = this.getNode(index - 1);
		newNode.next = currentNode.next;
		currentNode.next = newNode;
	}

	/**
	 * Creates a new node that points to the given memory block, and adds it
	 * to the end of this list (the node will become the list's last element).
	 * 
	 * @param block
	 *              the given memory block
	 */
	public void addLast(MemoryBlock block) {
		this.add(size, block);
	}

	/**
	 * Creates a new node that points to the given memory block, and adds it
	 * to the beginning of this list (the node will become the list's first
	 * element).
	 * 
	 * @param block
	 *              the given memory block
	 */
	public void addFirst(MemoryBlock block) {
		this.add(0, block);
	}

	/**
	 * Gets the memory block located at the given index in this list.
	 * 
	 * @param index
	 *              the index of the retrieved memory block
	 * @return the memory block at the given index
	 * @throws IllegalArgumentException
	 *                                  if index is negative or greater than or
	 *                                  equal to size
	 */
	public MemoryBlock getBlock(int index) {
		return this.getNode(index).block;
	}

	/**
	 * Gets the index of the node pointing to the given memory block.
	 * 
	 * @param block
	 *              the given memory block
	 * @return the index of the block, or -1 if the block is not in this list
	 */
	public int indexOf(MemoryBlock block) {
		return this.indexOf(checkedBlock -> block.equals(checkedBlock));
	}

	public int indexOf(Function<MemoryBlock, Boolean> callback) {
		int index = 0;
		Node currentNode = this.first;
		while (currentNode != null && !callback.apply(currentNode.block)) {
			index++;
			currentNode = currentNode.next;
		}

		if (currentNode == null) {
			return -1;
		}

		return index;
	}

	/**
	 * Removes the given node from this list.
	 * 
	 * @param node the node that will be removed from this list
	 * @throws NullPointerException when the given node is null
	 */
	public void remove(Node node) {
		if (node == null) {
			throw new NullPointerException();
		}
		Node currentNode = this.first, prevNode = null;
		while (currentNode != null && !currentNode.equals(node)) {
			prevNode = currentNode;
			currentNode = currentNode.next;
		}

		if (currentNode == null) { // node was not found
			return;
		}

		if (prevNode == null) { // given node is first
			if (size == 1) {
				this.last = null;
			}
			this.first = first.next;
			size--;
			return;
		}

		size--;
		prevNode.next = currentNode.next;

		if (prevNode.next == null) { // change last if last was removed
			this.last = prevNode;
		}
	}

	/**
	 * Removes from this list the node which is located at the given index.
	 * 
	 * @param index the location of the node that has to be removed.
	 * @throws IllegalArgumentException
	 *                                  if index is negative or greater than or
	 *                                  equal to size
	 */
	public void remove(int index) {
		remove(this.getNode(index));
	}

	/**
	 * Removes from this list the node pointing to the given memory block.
	 * 
	 * @param block the memory block that should be removed from the list
	 * @throws IllegalArgumentException
	 *                                  if the given memory block is not in this
	 *                                  list
	 */
	public void remove(MemoryBlock block) {
		int index = this.indexOf(block);
		this.remove(index);
	}

	/**
	 * returns a new LinkedList of memory blocks which are filtered by the given callback.
	 * @param callback a function which returns true for every wanted block.
	 */
	public LinkedList filter(Function<MemoryBlock, Boolean> callback) {
		LinkedList list = new LinkedList();
		ListIterator iterator = this.iterator();
		while (iterator.hasNext()) {
			MemoryBlock block = iterator.next();
			if (callback.apply(block)) {
				list.addLast(block);
			}
		}
		return list;
	}

	/**
	 * returns the first memory block in the list which is returned with true by the given callback function. returns null if no block is found.
	 * @param callback a function that is applied on every block in the list.
	 */
	public MemoryBlock getFirstWhich(Function<MemoryBlock, Boolean> callback) {
		ListIterator iterator = this.iterator();
		while (iterator.hasNext()) {
			MemoryBlock block = iterator.next();
			if (callback.apply(block)) {
				return block;
			}
		}
		return null;
	}

	/**
	 * Returns an iterator over this list, starting with the first element.
	 */
	public ListIterator iterator() {
		return new ListIterator(first);
	}

	/**
	 * A textual representation of this list, for debugging.
	 */
	public String toString() {
		String result = "";
		ListIterator iterator = this.iterator();
		while (iterator.hasNext()) {
			result += iterator.next() + " ";
		}
		return result;
	}
}