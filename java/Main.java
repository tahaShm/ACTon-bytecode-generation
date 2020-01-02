public class Main {
    public static void main(String[] args){
        A a = new A(5);
        B b = new B(2);

        a.setKnownActors(b);
        b.setKnownActors(a);

        a.initial();

        a.start();
        b.start();
    }
}