package ch.fhnw.msc.bis.mso.jfuzzy;


import java.awt.geom.Point2D;

public class Coordinate extends Point2D {
	private double x;
	private double y;

	public Coordinate(double x, double y)
	{
		setLocation(x,y);
		
	}
	@Override
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
	public void setLocation(double x, double y) {
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;
		
	}


}
