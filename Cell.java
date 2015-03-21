public class Cell
{
	private int state; //0 is blank, 1 is head, 2 is trail, 3 is berry
	
	public Cell()
	{
		this.state = 0;
	}
	
	public int getState() { return this.state; }
	public void setState(int b) { this.state = b; }
}
