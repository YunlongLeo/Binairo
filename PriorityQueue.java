public class PriorityQueue{
	
	private Node first;
	
	public PriorityQueue() {
		first = null;
	}
	
	public Node getFirst() {
		return first;
	}
	
	public void setFirst(Node f) {
		first = f;
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
	public void enQueue(Node n) {
		if(first == null) {
			first = n;
		}else {
			Node curr = first;
			Node prev = null;
			while(curr!=null&&n.getPriority()>curr.getPriority()) {
				prev = curr;
				curr = curr.getNext();
			}
			if(prev == null) {
				n.setNext(first);
				first = n;
			}else {
				n.setNext(curr);
				prev.setNext(n);
			}
		}
	}
	
	public Node deQueue() {
		Node curr = null;
		if(first!=null) {
			curr = first;
			curr.setNext(null);
			first = first.getNext();
		}
		return curr;
	}
	
	
	
}