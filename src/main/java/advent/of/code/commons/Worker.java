/**
 *
 */
package advent.of.code.commons;

/**
 * @author Jari
 *
 */
public class Worker {

	private String name;
	private boolean free = true;
	private short secondsLeft = 0;
	private short baseTimeForTask = 60;
	private Node nodeAssigned;

	public Worker(String name, Node nodeAssigned) {
		this.name = name;
		this.nodeAssigned = nodeAssigned;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public short getSecondsLeft() {
		return secondsLeft;
	}

	public void setSecondsLeft(short secondsLeft) {
		this.secondsLeft = (short) (secondsLeft + baseTimeForTask);
	}

	public short getBaseTimeForTask() {
		return baseTimeForTask;
	}

	public void setBaseTimeForTask(short baseTimeForTask) {
		this.baseTimeForTask = baseTimeForTask;
	}

	public Node getNodeAssigned() {
		return nodeAssigned;
	}

	public void setNodeAssigned(Node nodeAssigned) {
		this.nodeAssigned = nodeAssigned;
		if (nodeAssigned != null) {
			setFree(false);
		}
	}

	public void process() {
		secondsLeft--;
	}

}
