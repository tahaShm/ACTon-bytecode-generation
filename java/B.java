public class B extends Actor{
    A a;

    public B(int queueSize){
        super(queueSize);
    }

    public void setKnownActors(A a){
        this.a = a;
    }

    @Override
    public void send_foo(Actor sender, int i){
        this.send(new B_foo(this, sender,i));
    }

    public void foo(Actor sender, int i){
        System.out.println(i);
        sender.send_bar(this,i+1);
    }
}
