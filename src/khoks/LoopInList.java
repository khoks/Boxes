package khoks;

import java.util.ArrayList;

public class LoopInList {

	public static void main(String[] args) {
		ArrayList<Link> arr = new ArrayList<Link>();
		arr.add(new Link(1,null));
		arr.add(new Link(2,null));
		arr.add(new Link(3,null));
		arr.add(new Link(4,null));
		arr.add(new Link(5,null));
		arr.add(new Link(6,null));
		arr.add(new Link(7,null));
		arr.add(new Link(8,null));
		arr.add(new Link(9,null));
		arr.add(new Link(10,null));
		arr.add(new Link(11,null));
		arr.add(new Link(12,null));
		arr.get(0).next = arr.get(1);
		arr.get(1).next = arr.get(2);
		arr.get(2).next = arr.get(3);
		arr.get(3).next = arr.get(4);
		arr.get(4).next = arr.get(5);
		arr.get(5).next = arr.get(6);
		arr.get(6).next = arr.get(7);
		arr.get(7).next = arr.get(8);
		arr.get(8).next = arr.get(9);
		arr.get(9).next = arr.get(10);
		arr.get(10).next = arr.get(11);
		arr.get(11).next = arr.get(5);
		
		Link meetingNode = loop(arr);
		
		System.out.println(meetingNode==null?false:meetingNode.val);
		
		if(null != meetingNode) {
			int loopNodes = 1;
			Link nodeCounterLink = meetingNode;
			while(nodeCounterLink.next != meetingNode) {
				nodeCounterLink = nodeCounterLink.next;
				loopNodes++;
			}
			Link first = arr.get(0);
			Link second = arr.get(0);
			for(int i = 0 ; i < loopNodes ; i++) {
				second = second.next;
			}
			while(first != second) {
				first = first.next;
				second = second.next;
			}
			System.out.println("head" + first.val);
		}
		
	}

	private static Link loop(ArrayList<Link> arr) {
		Link slow = arr.get(0);
		Link fast = arr.get(1);
		for(int i = 0 ; i < arr.size() ; i++) {
			System.out.println("slow=" + slow.val + " fast=" + fast.val);
			if(slow == fast){
				return slow;
			}
			slow = slow.next;
			fast = fast.next.next;
		}
		return null;
	}
}
class Link{
	int val;
	Link next;
	
	public Link(int val, Link next) {
		this.val = val;
		this.next = next;
	}
}
