public class B_foo extends Message {
    private int i;
    private B receiver;
    private Actor sender;

    public B_foo(B receiver, Actor sender, int i){
        this.receiver = receiver;
        this.sender = sender;
        this.i = i;
    }

    @Override
    public void execute() {
        receiver.foo(sender, this.i);
    }
}
