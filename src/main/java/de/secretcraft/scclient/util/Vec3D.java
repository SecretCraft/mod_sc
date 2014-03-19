package de.secretcraft.scclient.util;

import de.secretcraft.scclient.BlockWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Vec3D {
	public double x;
	public double y;
	public double z;
	public static final Vec3D nullVec = new Vec3D();
	
	public Vec3D()
	{
		x=0;
		y=0;
		z=0;
	}
	
	public Vec3D(Vec2D v)
	{
		x=v.x;
		y=0;
		z=v.y;
	}
	
	public Vec3D(Vec3D v)
	{
		x=v.x;
		y=v.y;
		z=v.z;
	}
	
	public Vec3D(Vec3 v)
	{
		x=v.xCoord;
		y=v.yCoord;
		z=v.zCoord;
	}
	
	public Vec3D(double x1,double y1)
	{
		x=x1;
		z=y1;
		y=0;
	}
	
	public Vec3D(double x1,double y1,double z1)
	{
		x=x1;
		y=y1;
		z=z1;
	}
	
	public Vec3D(BlockWrapper block) {
		set(block.getSideCoords(BlockWrapper.INSIDE));
	}
	
	public Vec3D(Entity e) {
		x=e.posX;
		y=e.posY;
		z=e.posZ;
	}
	
	public Vec3D(Entity src, Entity target) {
		x = target.posX - src.posX;
		y = (target.posY-target.ySize) - (src.posY-src.ySize);
		z = target.posZ - src.posZ;
	}

	public Vec3 getVec3() {
		return Minecraft.getMinecraft().theWorld.getWorldVec3Pool().getVecFromPool(x, y, z);
	}
	
	public BlockWrapper getBlock(World w) {
		return new BlockWrapper(this, w);
	}
	
	public BlockWrapper getBlock() {
		return new BlockWrapper(this);
	}
	
	public Vec3D setToBlock() {
		return this.set(getX(), getY(), getZ());
	}

	public Vec3D set(double x1,double y1,double z1)
	{
		x=x1;
		y=y1;
		z=z1;
		return this;
	}

	public Vec3D set(Vec3D v)
	{
		x=v.x;
		y=v.y;
		z=v.z;
		return this;
	}
	
	public double dist(Vec3D v)
	{
		return new Vec3D(v.x-x,v.y-y,v.z-z).getLen();
	}

	public Vec3D clone()
	{
		return new Vec3D(x,y,z);
	}
	
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Vec3D)) return false;
		Vec3D v = (Vec3D)obj;
		return (x==v.x)&&(y==v.y)&&(z==v.z);
	}
	
	public int hashCode() {
		return Double.valueOf(x).hashCode() ^ Double.valueOf(y).hashCode() ^ Double.valueOf(z).hashCode();
	}
	
	public boolean isLin(Vec3D v)
	{
		return this.angle(v) == 0D;
	}
	
	public Vec3D inv()
	{
		return set(-x,-y,-z);
	}
	
	public double mul(Vec3D v)
	{
	    return (x*v.x)+(y*v.y)+(z*v.z);
	}
	
	public Vec3D mul(double d)
	{
		x*=d;
		y*=d;
		z*=d;
		return this;
	}
	
	public Vec3D cross(Vec3D v)
	{
		double x1 = y*v.z - z*v.y;
		double y1 = z*v.x - x*v.z;
		double z1 = x*v.y - y-v.x;
		return set(x1,y1,z1);
	}
	
	public Vec3D div(double d)
	{
		x/=d;
		y/=d;
		z/=d;
		return this;
	}
	
	public Vec3D add(Vec3D v)
	{
		x+=v.x;
		y+=v.y;
		z+=v.z;
		return this;
	}
	
	public Vec3D sub(Vec3D v)
	{
		x-=v.x;
		y-=v.y;
		z-=v.z;
		return this;
	}
	
	public double getLen()
	{
		return Math.sqrt(x*x+y*y+z*z);
	}
	
	public Vec3D setLen(double l)
	{
		return div(getLen()).mul(l);
	}
	
	public double angle(Vec3D v)
	{
		return Math.acos(mul(v)/(getLen()*v.getLen()));
	}
	
	/**
	 * The block x coordinate.
	 * 
	 * @return (int) Math.floor(x);
	 */
	public int getX() {
		return (int) Math.floor(x);
	}

	/**
	 * The block y coordinate.
	 * 
	 * @return (int) Math.floor(y);
	 */
	public int getY() {
		return (int) Math.floor(y);
	}

	/**
	 * The block z coordinate.
	 * 
	 * @return (int) Math.floor(z);
	 */
	public int getZ() {
		return (int) Math.floor(z);
	}
	
	public Vec2D get2D()
	{
		return new Vec2D(x-0.5*z,y-0.5*z);
	}
	
	public Vec3D rotate(double rad,Vec3D v)
	{
		return rotate(Math.sin(rad), Math.cos(rad),v);
	}
	
	public Vec3D rotate(double sin, double cos, Vec3D v)
	{
		double l = v.getLen();
		v.setLen(1.0);
		double x1 = x*(cos+v.x*v.x*(1-cos))
				  + y*(v.x*v.y*(1-cos)-v.z*sin)
				  + z*(v.x*v.z*(1-cos)+v.y*sin);
		double y1 =	x*(v.y*v.x*(1-cos)+v.z*sin)
				  + y*(cos+v.y*v.y*(1-cos))
				  + z*(v.y*v.z*(1-cos)-v.x*sin);
		double z1 = x*(v.z*v.x*(1-cos)-v.y*sin)
				  + y*(v.z*v.y*(1-cos)+v.x*sin)
				  + z*(cos+v.z*v.z*(1-cos));
		x=x1;
		y=y1;
		z=z1;
		v.setLen(l);
		return this;
	}
	
	public String toString() {
		return "("+x+"|"+y+"|"+z+")";
	}
	
	/**
	 * @return a new Vec3D with:<br>
	 * x = e.posX<br>
	 * y = (e.boundingBox.minY
	 *     + e.boundingBox.maxY)/2<br>
     * z = e.posZ
	 */
	public static Vec3D getMiddle(Entity e) {
		return new Vec3D(e.posX, (e.boundingBox.minY+e.boundingBox.maxY)/2, e.posZ);
	}
	
	public static Vec3D getPlayerFootPos(EntityPlayer e) {
		return new Vec3D(e.posX, e.boundingBox.minY, e.posZ);
	}
}
