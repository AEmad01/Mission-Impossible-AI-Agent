package code.mission;

public class Ally implements Cloneable{
	
	int damage;
	int X;
	int Y;
    boolean isCarry;
    boolean isDropped;
	public Ally(int damage,int X, int Y) {
		this.damage=damage;
		 this.X =X;
		 this.Y =Y;
	     isCarry=false;
	     isDropped=false;
	}
	
	public boolean isDropped() {
		return isDropped;
	}

	public void setDropped(boolean isDropped) {
		this.isDropped = isDropped;
	}

	public int getDamage() {
		
		return damage;
	}
	public void damage() {
		this.damage=this.damage+2;

		if (this.damage==100)
			return;
		if (this.damage>=100)
		{	this.damage=100;
		return;}
		
	}

	public boolean isCarried() {
		return isCarry;
	}
	public void setCarried(boolean carried) {
		this.isCarry = carried;

	}
	
	public int getX() {
		return X;
	}
	@Override
	public String toString() {
		return "Ally [Damage=" + damage + ", X=" + X + ", Y=" + Y + ", isCarry=" + isCarry + ", isDropped=" + isDropped
				+ "]";
	}

	public void setPos(int x, int y) {
		X = x;
		Y=y;
	}
	public void setY(int y) {
		Y = y;
	}
	public int getY() {
		return Y;
	}

}
