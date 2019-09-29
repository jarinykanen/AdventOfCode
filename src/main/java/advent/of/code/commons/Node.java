/**
 *
 */
package advent.of.code.commons;

/**
 * @author Jari
 *
 */
public class Node {

	private String name;
	private boolean workInProgress;
	private Worker assignedTo;

	public Node(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isWorkInProgress() {
		return workInProgress;
	}

	public void setWorkInProgress(boolean workInProgress) {
		this.workInProgress = workInProgress;
	}

	public Worker getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Worker assignedTo) {
		this.assignedTo = assignedTo;
	}

}
