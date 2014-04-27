abstract class A {
	public A(){
		print();	
	}

	protected abstract void print();
}

class B extends A{
	private int a;
	public B(int a){
		super();
		this.a = a;
		print();	
	}

	protected void print(){
		System.out.println("a = " + a);
	}
}

class C extends B{
	private int x;

	public C(int a, int b){
		super(b);
		this.x = a;
	}

	protected void print(){
		System.out.println("x = " + x);
	}
}

public class Test {
	public static void main(String args[]){
		A aInstance1 = new B(5);
		A aInstance2 = new C(10, 20);
		
	}
}
