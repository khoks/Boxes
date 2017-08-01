package khoks;

public class BinaryTree {

	public static void main(String[] args) {
		Node nRoot = new Node(10);
		Node n1 = new Node(5);
		Node n2 = new Node(15);
		Node n3 = new Node(2);
		Node n4 = new Node(6);
		Node n5 = new Node(7);
		Node n6 = new Node(8);
		Node n7 = new Node(12);
		Node n8 = new Node(17);
		Node n9 = new Node(11);
		Node n10 = new Node(14);
		Node n11 = new Node(13);
		Node n12 = new Node(16);
		
		nRoot.left = n1;
		nRoot.right = n2;
		n1.left = n3;
		n1.right = n4;
		n4.right = n5;
		n5.right = n6;
		n2.left = n7;
		n2.right = n8;
		n7.left = n9;
		n7.right = n10;
		n10.left = n11;
		n8.left = n12;
		
		System.out.println(isBalanced(nRoot));
	}

	private static boolean isBalanced(Node nRoot) {
		
		if(nRoot == null)
			return true;
		
		int hL = height(nRoot.left);
		int hR = height(nRoot.right);
		
		if(Math.abs(hL - hR) <= 1 &&
				isBalanced(nRoot.left) &&
				isBalanced(nRoot.right)) {
			System.out.println("true at" + nRoot.val);
			return true;
		}
		System.out.println("false at" + nRoot.val + " hL hR " + hL + " " + hR);
		return false;
	}

	private static int height(Node node) {
		if(node == null)
			return 0;
		
		return 1 + max(height(node.left), height(node.right));
	}

	private static int max(int height, int height2) {
		if(height > height2)
			return height;
		else
			return height2;
	}

}
class Node {
	int val;
	Node left, right;
	public Node(int val, Node left, Node right) {
		this.val = val;
		this.left = left;
		this.right = right;
	}
	public Node(int val) {
		this.val = val;
	}
}