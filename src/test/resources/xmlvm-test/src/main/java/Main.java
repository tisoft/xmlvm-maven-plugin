public class Main {
	
	public static void main(String... args){
		ComparableTest c1=new ComparableTest();
		ComparableTest c2=new ComparableTest();
		System.out.println("running. "+c1.compareTo(c2));
		System.out.println(new Object(){}.getClass().getEnclosingMethod());
	}
}