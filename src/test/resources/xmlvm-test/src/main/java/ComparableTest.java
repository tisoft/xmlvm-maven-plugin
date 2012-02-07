
public class ComparableTest implements Comparable<ComparableTest>, TestInterface{
	public int compareTo(ComparableTest o) {
		//for the lulz
		return (int)Math.signum(Math.random()-0.5d);
	}

	public void dummy1() {
		// TODO Auto-generated method stub
		
	}

	public String dummy2(int lala) {
		// TODO Auto-generated method stub
		return null;
	}
}
