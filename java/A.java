public class A extends Actor {
    B b;
    int i;

    public A(int queueSize){
        super(queueSize);
    }

    public void initial(String ss){
        int j = 0;
        int k = 2;
        int arr[];
        String s, t;
        s = "l;kasd1f";
        t = "kljsdflk";
        s = t;
        arr = new int[3];
        j = ++arr[2];
//        b.send_foo(this,2);
    }

    public void setKnownActors(B b){
        this.b = b;
    }

    @Override
    public void send_bar(Actor sender, int i){
        this.send(new A_bar(this, sender,i));
    }

    public void bar(Actor sender, int i){
        System.out.println(i);
        sender.send_foo(this,i+1);
        sender.send_bar(this,i+1);
    }
}