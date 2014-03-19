package de.secretcraft.scclient.util;

public class Vec2D
{
	public double x;
	public double y;
	public static final Vec2D nullVec = new Vec2D();

	public Vec2D()
	{
		x=0;
		y=0;
	}

	public Vec2D(Vec2D v)
	{
		x=v.x;
		y=v.y;
	}
	
	public Vec2D(double x1,double y1)
	{
		x=x1;
		y=y1;
	}
	
	public Vec2D clone()
	{
		return new Vec2D(x,y);
	}

	public Vec2D set(double x1,double y1)
	{
		x=x1;
		y=y1;
		return this;
	}

	public Vec2D set(Vec2D v)
	{
		x=v.x;
		y=v.y;
		return this;
	}
	
	public boolean isLin(Vec2D v)
	{
		return x/v.x==y/v.y;
	}
	
	public double dist(Vec2D v)
	{
		return new Vec2D(v.x-x,v.y-y).getLen();
	}

	public Vec2D inv()
	{
		return set(-x,-y);
	}
	
	public boolean equals(Vec2D v)
	{
		return (x==v.x)&&(y==v.y);
	}
	
	public double mul(Vec2D v)
	{
	    return (x*v.x)+(y*v.y);
	}
	
	public Vec2D mul(double d)
	{
		x*=d;
		y*=d;
		return this;
	}
	
	public Vec2D div(double d)
	{
		x/=d;
		y/=d;
		return this;
	}
	
	public Vec2D add(Vec2D v)
	{
		x+=v.x;
		y+=v.y;
		return this;
	}
	
	public Vec2D sub(Vec2D v)
	{
		x-=v.x;
		y-=v.y;
		return this;
	}
	
	public double getLen()
	{
		return Math.sqrt(x*x+y*y);
	}
	
	public Vec2D setLen(double l)
	{
		return div(getLen()).mul(l);
	}
	
	public double angle(Vec2D v)
	{
		return Math.acos((mul(v)/(getLen()*v.getLen())));
	}
	
	public Vec2D rotate(double rad)
	{
		double x1;
		x1=(x*Math.cos(rad)+(y*Math.sin(rad)));
	    y=(x*-(Math.sin(rad))+(y*Math.cos(rad)));
		x=x1;
		return this;
	}
}
