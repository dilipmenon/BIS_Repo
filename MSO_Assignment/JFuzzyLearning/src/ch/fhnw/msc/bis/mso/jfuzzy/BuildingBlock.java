package ch.fhnw.msc.bis.mso.jfuzzy;
import java.awt.Point;


public class BuildingBlock extends Point {
	
	private int x;
	private int y;

	public BuildingBlock(int x, int y)
	{
		setLocation(x,y);
		
	}

	public double getX() {
		// TODO Auto-generated method stub
		return this.x;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return this.y;
	}

	@Override
	public void setLocation(int x, int y) {
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = java.lang.Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = java.lang.Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuildingBlock other = (BuildingBlock) obj;
		if (this.x == other.x && this.y == other.y)
			return true;
		return false;
	}

	
	


}
