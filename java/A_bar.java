public class A_bar extends Message {
    private int i;
    private A receiver;
    private Actor sender;

    public A_bar(A receiver, Actor sender, int i){
        this.receiver = receiver;
        this.sender = sender;
        this.i = i;
    }

    @Override
    public void execute() {
        receiver.bar(sender, this.i);
    }
}
