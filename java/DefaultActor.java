public class DefaultActor extends Thread {
    public void send_foo(Actor sender, int i){
        System.out.println("there is no msghandler named foo in sender");
    }

    public void send_bar(Actor sender, int i){
        System.out.println("there is no msghandler named bar in sender");
    }
}