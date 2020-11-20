
public class Ally {
	
	int damage;
	int X;
	int Y;
    boolean isCarry;
    boolean isDropped;
    public void move(Operator direction) {

    	switch(direction) {
    	case LEFT:
    		Y--;
    		break;
    	case UP:
    		X--;
    		break;
    	case RIGHT:
    		Y++;
    		break;
    	case DOWN:
    		X++;
    		break;
    		
    	}
    }
	public Ally(int damage,int X, int Y) {
		this.damage=damage;
		 this.X =X;
		 this.Y =Y;
	     isCarry=false;
	     isDropped=false;
	}
	public Ally(Ally ally) {
		this.damage=ally.getDamage();
		this.X=ally.getX();
		this.Y=ally.getY();
		this.isCarry=ally.isCarried();
		this.isDropped=ally.isDropped();

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
	public void damage(Integer depth) {
		this.damage = damage+(2*depth);
		if (damage>100)
			damage=100;
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

	public void setX(int x) {
		X = x;
	}
	public void setY(int y) {
		Y = y;
	}
	public int getY() {
		return Y;
	}

}
