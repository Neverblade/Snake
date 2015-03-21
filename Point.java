public class Point
{
	public int x;
	public int y;
	
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	//accessors
	public int getX() { return x; }
	public int getY() { return y; }
	
	public String toString()
	{
		return "(" + this.x + ", " + this.y + ")";
	}
	
	//mutators
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
}
