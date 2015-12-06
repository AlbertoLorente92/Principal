package Principal;

public class Triplet<L,M,R> {
	private L left;
	private M midle;
	private R right;
	
	public Triplet(L left,M midle,R right){
		this.left = left;
		this.midle = midle;
		this.right = right;
	}
	
	public Triplet(){
		this.left = null;
		this.midle = null;
		this.right = null;
	}

	public int getFirstEmpty(){
		if(leftIsEmpty()) 
			return 0;
		else if(midleIsEmpty())
			return 1;
		else if(rightIsEmpty())
			return 2;
		
		return -1;
	}
	
	public Boolean leftIsEmpty(){
		return this.left == null;
	}
	
	public Boolean midleIsEmpty(){
		return this.midle == null;
	}
	
	public Boolean rightIsEmpty(){
		return this.right == null;
	}
	
	public L getLeft() {
		return left;
	}

	public void setLeft(L left) {
		this.left = left;
	}

	public R getRight() {
		return right;
	}

	public void setRight(R right) {
		this.right = right;
	}
	
	public void setTriplet(Triplet<L,M,R> t){
		this.left = t.getLeft();
		this.midle = t.getMidle();
		this.right = t.getRight();
	}

	public M getMidle() {
		return midle;
	}

	public void setMidle(M midle) {
		this.midle = midle;
	}
}
