package main.visitor;
import main.ast.node.*;
import main.ast.node.Program;
import main.ast.node.declaration.*;
import main.ast.node.declaration.handler.*;
import main.ast.node.declaration.VarDeclaration;
import main.ast.node.expression.*;
import main.ast.node.expression.values.BooleanValue;
import main.ast.node.expression.values.IntValue;
import main.ast.node.expression.values.StringValue;
import main.ast.node.expression.values.Value;
import main.ast.node.statement.*;
import main.ast.type.Type;
import main.ast.type.noType.NoType;
import main.ast.type.primitiveType.BooleanType;
import main.ast.type.primitiveType.IntType;
import main.ast.type.primitiveType.StringType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class byteCodeGenerator implements Visitor {
    private String actorFileName;
    private int labelIndex = 0;
    private String msgHandlerFileName;
    private ArrayList <VarDeclaration> currentMsgArgs;
    private ArrayList <VarDeclaration> currentActorVars;
    private ArrayList <VarDeclaration> currentKnownActors;
    private FileWriter expWriter = null;
    private ArrayList <MsgHandlerDeclaration> allMsgHandlers = new ArrayList<>();
    private ArrayList <String> allActorNames = new ArrayList<>();
    private ArrayList <ActorInstantiation> allActorInstantiations = new ArrayList<>();

    public String bytecodeType(String type) {
        String ans = "";
        switch(type) {
            case "string":
                ans = "[[Ljava/lang/String";
                break;
            case "int":
                ans = "I";
                break;
            case "boolean":
                ans = "Z";
                break;
            case "int[]":
                ans = "[I";
                break;
            default:
                ans = "L" + type + ";";
        }
        return ans;
    }

    public int getLocalArgIdx(String id) {
        int idx = 0;
        for (VarDeclaration varDeclaration : currentMsgArgs) {
            idx++;
            if (varDeclaration.getIdentifier().getName().equals(id)) {
                return idx;
            }
        }
        return -1;
    }

    public String getIdType(String id) {
        for (VarDeclaration varDeclaration : currentMsgArgs) {
            if (varDeclaration.getIdentifier().getName().equals(id))
                return varDeclaration.getType().toString();
        }
        for (VarDeclaration varDeclaration : currentActorVars) {
            if (varDeclaration.getIdentifier().getName().equals(id))
                return varDeclaration.getType().toString();
        }
        for (VarDeclaration varDeclaration : currentKnownActors) {
            if (varDeclaration.getIdentifier().getName().equals(id))
                return varDeclaration.getType().toString();
        }
        return "error";
    }

    public Type getIdTypeType(String id) {
        for (VarDeclaration varDeclaration : currentMsgArgs) {
            if (varDeclaration.getIdentifier().getName().equals(id))
                return varDeclaration.getType();
        }
        for (VarDeclaration varDeclaration : currentActorVars) {
            if (varDeclaration.getIdentifier().getName().equals(id))
                return varDeclaration.getType();
        }
        for (VarDeclaration varDeclaration : currentKnownActors) {
            if (varDeclaration.getIdentifier().getName().equals(id))
                return varDeclaration.getType();
        }
        return new NoType();
    }

    public String getIdTypeActorVars(String id) {
        for (VarDeclaration varDeclaration : currentActorVars) {
            if (varDeclaration.getIdentifier().getName().equals(id))
                return varDeclaration.getType().toString();
        }
        return "error";
    }

    public Type getIdTypeActorVarsType(String id) {
        for (VarDeclaration varDeclaration : currentActorVars) {
            if (varDeclaration.getIdentifier().getName().equals(id))
                return varDeclaration.getType();
        }
        return new NoType();
    }

    public String getLoadCommand(String id) {
        int index = getLocalArgIdx(id);
        String command = "";
        if (index == -1){
            String idType = getIdTypeActorVars(id);
            command = "aload_0\n" + "getfield " + actorFileName + "/" + id + " " + bytecodeType(idType);
        }
        else {
            String idType = getIdType(id);
            String preCode = "i";
            if (idType == "string" || idType == "int[]")
                preCode = "a";
            if (index > 3)
                command = preCode + "load " + index;
            else
                command = preCode + "load_" + index;
        }
        return command;
    }

    public String getFieldLoadCommand(String id) {
        String command = "";
        String idType = getIdTypeActorVars(id);
        command = "aload_0\n" + "getfield " + actorFileName + "/" + id + " " + bytecodeType(idType);
        return command;
    }

    public String getStoreCommand(String id) {
        int index = getLocalArgIdx(id);
        String command = "";
        if (index == -1){
            String idType = getIdTypeActorVars(id);
            command = "aload_0\n" + "putfield " + actorFileName + "/" + id + " " + bytecodeType(idType);
        }
        else {
            String idType = getIdType(id);
            String preCode = "i";
            if (idType == "string" || idType == "int[]")
                preCode = "a";
            if (index > 3)
                command = preCode + "store " + index;
            else
                command = preCode + "store_" + index;
        }
        return command;
    }

    public String getFieldStoreCommand(String id) {
        String command = "";
        String idType = getIdTypeActorVars(id);
        command = "putfield " + actorFileName + "/" + id + " " + bytecodeType(idType);
        return command;
    }

    public void makeDefaultActor() {
        try {
            FileWriter myWriter = new FileWriter("DefaultActor.j");
            myWriter.write(".class public DefaultActor\n" +
                    ".super java/lang/Thread\n" +
                    "\n" +
                    ".method public <init>()V\n" +
                    ".limit stack 1\n" +
                    ".limit locals 1\n" +
                    "aload_0\n" +
                    "invokespecial java/lang/Thread/<init>()V\n" +
                    "return\n" +
                    ".end method\n");
            for (MsgHandlerDeclaration msgHandlerDeclaration: allMsgHandlers) {
                myWriter.write("\n" +
                        ".method public send_" + msgHandlerDeclaration.getName().getName() +
                        "(LActor;");
                for (VarDeclaration varDeclaration: msgHandlerDeclaration.getArgs()) {
                    myWriter.write(bytecodeType(varDeclaration.getType().toString()));
                }
                myWriter.write(")V\n" +
                        ".limit stack 2\n" +
                        ".limit locals 16\n" +
                        "getstatic java/lang/System/out Ljava/io/PrintStream;\n" +
                        "ldc \"there is no msghandler named " + msgHandlerDeclaration.getName().getName() + " in sender\"\n" +
                        "invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n" +
                        "return\n" +
                        ".end method\n");
            }
            myWriter.flush();
            myWriter.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void makeMessage() {
        try {
            FileWriter myWriter = new FileWriter("Message.j");
            myWriter.write(".class public abstract Message\n" +
                    ".super java/lang/Object\n" +
                    "\n" +
                    ".method public <init>()V\n" +
                    ".limit stack 1\n" +
                    ".limit locals 1\n" +
                    "0: aload_0\n" +
                    "1: invokespecial java/lang/Object/<init>()V\n" +
                    "4: return\n" +
                    ".end method\n" +
                    "\n" +
                    ".method public abstract execute()V\n" +
                    ".end method");
            myWriter.flush();
            myWriter.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    void makeActor() {
        try {
            FileWriter myWriter = new FileWriter("Actor.j");
            myWriter.write(".class public Actor\n" +
                    ".super DefaultActor\n" +
                    "\n" +
                    ".field private queue Ljava/util/ArrayList;\n" +
                    ".signature \"Ljava/util/ArrayList<LMessage;>;\"\n" +
                    ".end field\n" +
                    ".field private lock Ljava/util/concurrent/locks/ReentrantLock;\n" +
                    ".end field\n" +
                    ".field queueSize I\n" +
                    ".end field\n" +
                    "\n" +
                    ".method public <init>(I)V\n" +
                    ".limit stack 3\n" +
                    ".limit locals 2\n" +
                    "aload_0\n" +
                    "invokespecial DefaultActor/<init>()V\n" +
                    "aload_0\n" +
                    "new java/util/ArrayList\n" +
                    "dup\n" +
                    "invokespecial java/util/ArrayList/<init>()V\n" +
                    "putfield Actor/queue Ljava/util/ArrayList;\n" +
                    "aload_0\n" +
                    "new java/util/concurrent/locks/ReentrantLock\n" +
                    "dup\n" +
                    "invokespecial java/util/concurrent/locks/ReentrantLock/<init>()V\n" +
                    "putfield Actor/lock Ljava/util/concurrent/locks/ReentrantLock;\n" +
                    "aload_0\n" +
                    "iload_1\n" +
                    "putfield Actor/queueSize I\n" +
                    "return\n" +
                    ".end method\n" +
                    "\n" +
                    ".method public run()V\n" +
                    ".limit stack 2\n" +
                    ".limit locals 1\n" +
                    "Label0:\n" +
                    "aload_0\n" +
                    "getfield Actor/lock Ljava/util/concurrent/locks/ReentrantLock;\n" +
                    "invokevirtual java/util/concurrent/locks/ReentrantLock/lock()V\n" +
                    "aload_0\n" +
                    "getfield Actor/queue Ljava/util/ArrayList;\n" +
                    "invokevirtual java/util/ArrayList/isEmpty()Z\n" +
                    "ifne Label31\n" +
                    "aload_0\n" +
                    "getfield Actor/queue Ljava/util/ArrayList;\n" +
                    "iconst_0\n" +
                    "invokevirtual java/util/ArrayList/remove(I)Ljava/lang/Object;\n" +
                    "checkcast Message\n" +
                    "invokevirtual Message/execute()V\n" +
                    "Label31:\n" +
                    "aload_0\n" +
                    "getfield Actor/lock Ljava/util/concurrent/locks/ReentrantLock;\n" +
                    "invokevirtual java/util/concurrent/locks/ReentrantLock/unlock()V\n" +
                    "goto Label0\n" +
                    ".end method\n" +
                    "\n" +
                    ".method public send(LMessage;)V\n" +
                    ".limit stack 2\n" +
                    ".limit locals 2\n" +
                    "aload_0\n" +
                    "getfield Actor/lock Ljava/util/concurrent/locks/ReentrantLock;\n" +
                    "invokevirtual java/util/concurrent/locks/ReentrantLock/lock()V\n" +
                    "aload_0\n" +
                    "getfield Actor/queue Ljava/util/ArrayList;\n" +
                    "invokevirtual java/util/ArrayList/size()I\n" +
                    "aload_0\n" +
                    "getfield Actor/queueSize I\n" +
                    "if_icmpge Label30\n" +
                    "aload_0\n" +
                    "getfield Actor/queue Ljava/util/ArrayList;\n" +
                    "aload_1\n" +
                    "invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z\n" +
                    "pop\n" +
                    "Label30:\n" +
                    "aload_0\n" +
                    "getfield Actor/lock Ljava/util/concurrent/locks/ReentrantLock;\n" +
                    "invokevirtual java/util/concurrent/locks/ReentrantLock/unlock()V\n" +
                    "return\n" +
                    ".end method");
            myWriter.flush();
            myWriter.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    void makeMessageHandlers() {
        int counter = 0, iloadCounter;
        for (MsgHandlerDeclaration msgHandlerDeclaration: allMsgHandlers) {
            try {
                iloadCounter = 3;
                String actorName = allActorNames.get(counter), handlerName = msgHandlerDeclaration.getName().getName();
                FileWriter myWriter = new FileWriter(actorName + "_" + handlerName + ".j");
                myWriter.write(".class public " + actorName + "_" + handlerName + "\n");
                myWriter.write(".super Message\n\n");
                for (VarDeclaration varDeclaration: msgHandlerDeclaration.getArgs()) {
                    myWriter.write(".field private " + varDeclaration.getIdentifier().getName() + " "
                            + bytecodeType(varDeclaration.getType().toString()) + "\n");
                }
                myWriter.write(".field private receiver L" + actorName + ";\n" +
                        ".field private sender LActor;\n\n");

                //init
                myWriter.write(".method public <init>(L" + actorName + ";LActor;");
                for (VarDeclaration varDeclaration: msgHandlerDeclaration.getArgs())
                    myWriter.write(bytecodeType(varDeclaration.getType().toString()));
                myWriter.write(")V\n");
                myWriter.write(".limit stack 2\n" +
                        ".limit locals 16\n" +
                        "aload_0\n" +
                        "invokespecial Message/<init>()V\n" +
                        "aload_0\n" +
                        "aload_1\n");
                myWriter.write("putfield " + actorName + "_" + handlerName + "/receiver L" + actorName + ";\n");
                myWriter.write("aload_0\n" +
                        "aload_2\n");
                myWriter.write("putfield " + actorName + "_" + handlerName + "/sender LActor;\n");
                for (VarDeclaration varDeclaration: msgHandlerDeclaration.getArgs()) {
                    myWriter.write("aload_0\n" + "iload");
                    if (iloadCounter > 3)
                        myWriter.write(" " + iloadCounter + "\n");
                    else
                        myWriter.write("_" + iloadCounter + "\n");
                    myWriter.write("putfield " + actorName + "_" + handlerName + "/" + varDeclaration.getIdentifier().getName() +
                            " " + bytecodeType(varDeclaration.getType().toString()) + "\n");
                    iloadCounter++;
                }
                myWriter.write("return\n" +
                        ".end method\n\n");

                //execute
                myWriter.write(".method public execute()V\n" +
                        ".limit stack 3\n" +
                        ".limit locals 16\n" +
                        "aload_0\n");
                myWriter.write("getfield " + actorName + "_" + handlerName + "/receiver L" + actorName + ";\n");
                myWriter.write("aload_0\n");
                myWriter.write("getfield " + actorName + "_" + handlerName + "/sender LActor;\n");
                for (VarDeclaration varDeclaration: msgHandlerDeclaration.getArgs()) {
                    myWriter.write("aload_0\n");
                    myWriter.write("getfield " + actorName + "_" + handlerName + "/" + varDeclaration.getIdentifier().getName() +
                            " " + bytecodeType(varDeclaration.getType().toString()) + "\n");
                }
                myWriter.write("invokevirtual " + actorName + "/" + handlerName + "(LActor;");
                for (VarDeclaration varDeclaration: msgHandlerDeclaration.getArgs())
                    myWriter.write(bytecodeType(varDeclaration.getType().toString()));
                myWriter.write(")V\n");
                myWriter.write("return\n" + ".end method");

                myWriter.flush();
                myWriter.close();
            }
            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            counter++;
        }
    }

    int findQueueSize(ArrayList<ActorDeclaration> actors, String name) {
        for (ActorDeclaration actorDeclaration: actors) {
            if (name.equals(actorDeclaration.getName().getName()))
                return actorDeclaration.getQueueSize();
        }
        return -1;
    }

    boolean hasInitial(String name, ArrayList<ActorDeclaration> actors) {
        for (ActorDeclaration actorDeclaration: actors) {
            if (actorDeclaration.getName().getName().equals(name) && actorDeclaration.getInitHandler() != null)
                return true;
        }
        return false;
    }

    void makeMain(ArrayList<ActorDeclaration> actors) {
        int aloadNum = 1;
        try {
            FileWriter myWriter = new FileWriter("Main.j");
            myWriter.write(".class public Main\n" +
                    ".super java/lang/Object\n" +
                    "\n" +
                    ".method public <init>()V\n" +
                    ".limit stack 1\n" +
                    ".limit locals 1\n" +
                    "aload_0\n" +
                    "invokespecial java/lang/Object/<init>()V\n" +
                    "return\n" +
                    ".end method\n\n");
            myWriter.write(".method public static main([Ljava/lang/String;)V\n" +
                    ".limit stack 16\n" +
                    ".limit locals 16\n");

            //instantiation
            int queueSize, instantiationNum = 1;
            for (ActorInstantiation actorInstantiation: allActorInstantiations) {
                myWriter.write("new " + actorInstantiation.getType().toString() + "\n" +
                        "dup\n");
                queueSize = findQueueSize(actors, actorInstantiation.getType().toString());
                if (queueSize > 5)
                    myWriter.write("bipush " + queueSize + "\n");
                else
                    myWriter.write("iconst_" + queueSize + "\n");
                myWriter.write("invokespecial " + actorInstantiation.getType().toString() + "/<init>(I)V\n");
                myWriter.write("astore");
                if (instantiationNum > 3)
                    myWriter.write(" " + instantiationNum + "\n");
                else
                    myWriter.write("_" + instantiationNum + "\n");
                instantiationNum++;
            }

            //set known actors
            for (ActorInstantiation actorInstantiation: allActorInstantiations) {
                myWriter.write("aload");
                if (aloadNum > 3)
                    myWriter.write(" " + aloadNum + "\n");
                else
                    myWriter.write("_" + aloadNum + "\n");
                int foundNum;
                ArrayList<String> knownActorTypes = new ArrayList<>();
                for (Identifier identifier: actorInstantiation.getKnownActors()) {
                    foundNum = 1;
                    for (ActorInstantiation find: allActorInstantiations) {
                        if (identifier.getName().equals(find.getIdentifier().getName())) {
                            knownActorTypes.add(bytecodeType(find.getType().toString()));
                            myWriter.write("aload");
                            if (foundNum > 3)
                                myWriter.write(" " + foundNum + "\n");
                            else
                                myWriter.write("_" + foundNum + "\n");
                        }
                        foundNum++;
                    }
                }
                myWriter.write("invokevirtual " + actorInstantiation.getType().toString() + "/setKnownActors(");
                for (String type: knownActorTypes)
                    myWriter.write(type);
                myWriter.write(")V\n");
                aloadNum++;
            }

            //initialization
            aloadNum = 1;
            for (ActorInstantiation actorInstantiation: allActorInstantiations) {
                ArrayList<String> argTypes = new ArrayList<>();
                if (hasInitial(actorInstantiation.getType().toString(), actors)) {
                    if (aloadNum > 3)
                        myWriter.write("aload " + aloadNum +  "\n");
                    else
                        myWriter.write("aload_" + aloadNum +  "\n");
                    for (Expression initArg: actorInstantiation.getInitArgs()) {
                        argTypes.add(expressionType(initArg).toString());
                    }
                    myWriter.write("invokevirtual " + actorInstantiation.getType().toString() + "/initial(");
                    for (String argType: argTypes)
                        myWriter.write(bytecodeType(argType));
                    myWriter.write(")V\n");
                }
                aloadNum++;
            }

            //start
            aloadNum = 1;
            for (ActorInstantiation actorInstantiation: allActorInstantiations) {
                if (aloadNum > 3)
                    myWriter.write("aload " + aloadNum + "\n");
                else
                    myWriter.write("aload_" + aloadNum + "\n");
                myWriter.write("invokevirtual " + actorInstantiation.getType().toString() + "/start()V\n");
                aloadNum++;
            }

            myWriter.write("return\n" +
                    ".end method");
            myWriter.flush();
            myWriter.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void visit(Program program) {
        makeActor();
        makeMessage();

        if (program.getActors() != null) {
            for (ActorDeclaration actorDeclaration : program.getActors()) {
                actorDeclaration.accept(this);
            }
        }

        makeDefaultActor();
        makeMessageHandlers();

        if (program.getMain() != null)
            program.getMain().accept(this);

        makeMain(program.getActors());
    }

    @Override
    public void visit(ActorDeclaration actorDeclaration) {

        try {
            File actorFile = new File(actorDeclaration.getName().getName() + ".j");
            actorFile.createNewFile();
            actorFileName = actorDeclaration.getName().getName();
        } catch (IOException e) {
            System.out.println("An error occurred1.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(actorFileName + ".j");
            myWriter.write(".class public " + actorFileName + "\n");
            myWriter.write(".super Actor\n\n");

            if (actorDeclaration.getKnownActors() != null) {
                currentKnownActors = actorDeclaration.getKnownActors();
                for (VarDeclaration varDeclaration : actorDeclaration.getKnownActors()) {
                    myWriter.write(".field " + varDeclaration.getIdentifier().getName() + " L" + varDeclaration.getType().toString() + ";\n");
                }
            }

            if (actorDeclaration.getActorVars() != null) {
                currentActorVars = actorDeclaration.getActorVars();
                for (VarDeclaration varDeclaration : actorDeclaration.getActorVars()) {
                    myWriter.write(".field " + varDeclaration.getIdentifier().getName() + " " +  bytecodeType(varDeclaration.getType().toString()) + "\n");
                }
            }
            myWriter.write("\n.method public <init>(I)V\n" +
                    ".limit stack 2\n" +
                    ".limit locals 2\n" +
                    "aload_0\n" +
                    "iload_1\n" +
                    "invokespecial Actor/<init>(I)V\n" +
                    "return\n" +
                    ".end method\n"
            );

            myWriter.flush();
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred2.");
            e.printStackTrace();
        }

        if (actorDeclaration.getInitHandler() != null) {
            actorDeclaration.getInitHandler().accept(this);
        }

        try {
            FileWriter myWriter = new FileWriter(actorFileName + ".j" , true);
            myWriter.write("\n.method public setKnownActors(");
            if (actorDeclaration.getKnownActors() != null) {
                for (VarDeclaration varDeclaration : actorDeclaration.getKnownActors()) {
                    myWriter.write(bytecodeType(varDeclaration.getType().toString()));
                }
            }
            myWriter.write(")V\n");
            myWriter.write(".limit stack 2\n");
            myWriter.write(".limit locals " + (actorDeclaration.getKnownActors().size() + 1) + "\n");
            if (actorDeclaration.getKnownActors() != null) {
                int knownActorIdx = 0;
                for (VarDeclaration varDeclaration : actorDeclaration.getKnownActors()) {
                    knownActorIdx ++;
                    myWriter.write("aload_0\n");
                    myWriter.write("aload_" + knownActorIdx +  "\n");
                    myWriter.write("putfield " + actorFileName + "/" + varDeclaration.getIdentifier().getName() + " " + bytecodeType(varDeclaration.getType().toString()) + "\n");
                }
            }
            myWriter.write("return\n" +
                ".end method\n"
            );

            myWriter.flush();
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred3.");
            e.printStackTrace();
        }

        if (actorDeclaration.getMsgHandlers() != null) {
            for (MsgHandlerDeclaration msgHandlerDeclaration : actorDeclaration.getMsgHandlers()) {
                allMsgHandlers.add(msgHandlerDeclaration);
                allActorNames.add(actorDeclaration.getName().getName());
                msgHandlerDeclaration.accept(this);
            }
        }

    }

    @Override
    public void visit(HandlerDeclaration handlerDeclaration) {
        msgHandlerFileName = handlerDeclaration.getName().getName();
        try {
            FileWriter myWriter = new FileWriter(actorFileName  + ".j" , true);
            if (handlerDeclaration instanceof InitHandlerDeclaration) {
                myWriter.write("\n.method public " + handlerDeclaration.getName().getName() + "(");

                if (handlerDeclaration.getArgs() != null) {
                    currentMsgArgs = handlerDeclaration.getArgs();
                    for (VarDeclaration varDeclaration : handlerDeclaration.getArgs()) {
                        myWriter.write(bytecodeType(varDeclaration.getType().toString()) );
                    }
                    myWriter.write(")V\n");
                }
                if (handlerDeclaration.getLocalVars() != null) {
                    for (VarDeclaration varDeclaration : handlerDeclaration.getLocalVars()) {
                        currentMsgArgs.add(varDeclaration);
                    }
                }

                myWriter.flush();
                myWriter.close();

                if (handlerDeclaration.getBody() != null) {
                    expWriter = new FileWriter(actorFileName + ".j", true);
                    for (Statement statement : handlerDeclaration.getBody()) {
                        statement.accept(this);
                    }
                    expWriter.flush();
                    expWriter.close();
                }

                FileWriter myWriter2 = new FileWriter(actorFileName  + ".j" , true);
                myWriter2.write("return\n" +
                        ".end method\n");
                myWriter2.flush();
                myWriter2.close();
            }

            else {
                myWriter.write("\n.method public send_" + handlerDeclaration.getName().getName() + "(LActor;");

                if (handlerDeclaration.getArgs() != null) {
                    for (VarDeclaration varDeclaration : handlerDeclaration.getArgs()) {
                        myWriter.write(bytecodeType(varDeclaration.getType().toString()) );
                    }
                    myWriter.write(")V\n");
                }
                myWriter.write(".limit stack 6\n");
                myWriter.write(".limit locals 3\n");
                myWriter.write("aload_0\n");
                myWriter.write("new " + actorFileName + "_" + msgHandlerFileName + "\n");
                myWriter.write("dup\n" +
                    "aload_0\n" +
                    "aload_1\n" +
                    "iload_2\n"
                );
                myWriter.write("invokespecial " + actorFileName + "_" + msgHandlerFileName + "/<init>(L" + actorFileName + ";" + "LActor;");
                if (handlerDeclaration.getArgs() != null) {
                    currentMsgArgs = handlerDeclaration.getArgs();
                    for (VarDeclaration varDeclaration : handlerDeclaration.getArgs()) {
                        myWriter.write(bytecodeType(varDeclaration.getType().toString()) );
                    }
                    myWriter.write(")V\n");
                }

                if (handlerDeclaration.getLocalVars() != null) {
                    for (VarDeclaration varDeclaration : handlerDeclaration.getLocalVars()) {
                        currentMsgArgs.add(varDeclaration);
                    }
                }
                myWriter.write("invokevirtual " + actorFileName + "/send(LMessage;)V\n" +
                    "return\n" +
                    ".end method\n"
                );
                myWriter.flush();

                myWriter.write("\n.method public " + handlerDeclaration.getName().getName() + "(LActor;");

                if (handlerDeclaration.getArgs() != null) {
                    for (VarDeclaration varDeclaration : handlerDeclaration.getArgs()) {
                        myWriter.write(bytecodeType(varDeclaration.getType().toString()) );
                    }
                    myWriter.write(")V\n");
                }

                myWriter.flush();
                myWriter.close();

                if (handlerDeclaration.getBody() != null) {
                    expWriter = new FileWriter(actorFileName + ".j", true);
                    for (Statement statement : handlerDeclaration.getBody()) {
                        statement.accept(this);
                    }
                    expWriter.flush();
                    expWriter.close();
                }

                FileWriter myWriter2 = new FileWriter(actorFileName  + ".j" , true);
                myWriter2.write("return\n" +
                        ".end method\n");
                myWriter2.flush();
                myWriter2.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred4.");
            e.printStackTrace();
        }
    }

    @Override
    public void visit(VarDeclaration varDeclaration) {

        if (varDeclaration.getIdentifier() != null)
            varDeclaration.getIdentifier().accept(this);
    }

    @Override
    public void visit(Main mainActors) {

        if (mainActors.getMainActors() != null) {
            for (ActorInstantiation actorInstantiation : mainActors.getMainActors()) {
                allActorInstantiations.add(actorInstantiation);
                actorInstantiation.accept(this);
            }
        }
    }

    @Override
    public void visit(ActorInstantiation actorInstantiation) {

        if (actorInstantiation.getIdentifier() != null)
            actorInstantiation.getIdentifier().accept(this);
        if (actorInstantiation.getKnownActors() != null) {
            for (Identifier identifier : actorInstantiation.getKnownActors()) {
                identifier.accept(this);
            }
        }
        if (actorInstantiation.getInitArgs() != null) {
            for (Expression expression : actorInstantiation.getInitArgs()) {
                expression.accept(this);
            }
        }
    }

    @Override
    public void visit(UnaryExpression unaryExpression) {
        if (unaryExpression.getOperand() != null) {
            unaryExpression.getOperand().accept(this);
            Type uExpType = expressionType(unaryExpression);
        }
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {

        if(binaryExpression.getLeft() != null && binaryExpression.getRight() != null) {
            binaryExpression.getLeft().accept(this);
            binaryExpression.getRight().accept(this);
            Type lType = expressionType(binaryExpression);
        }
    }

    @Override
    public void visit(ArrayCall arrayCall) {

        if (arrayCall.getArrayInstance() != null)
            arrayCall.getArrayInstance().accept(this);
        if (arrayCall.getIndex() != null)
            arrayCall.getIndex().accept(this);
    }

    @Override
    public void visit(ActorVarAccess actorVarAccess) {

        if (actorVarAccess.getSelf() != null)
            actorVarAccess.getSelf().accept(this);

        if (actorVarAccess.getVariable() != null)
            actorVarAccess.getVariable().accept(this);
    }

    @Override
    public void visit(Identifier identifier) {
    }

    @Override
    public void visit(Self self) {
    }

    @Override
    public void visit(Sender sender) {
    }

    @Override
    public void visit(BooleanValue value) {
    }

    @Override
    public void visit(IntValue value) {
    }

    @Override
    public void visit(StringValue value) {
    }

    @Override
    public void visit(Block block) {
        if (block.getStatements() != null) {
            for (Statement statement : block.getStatements()) {
                statement.accept(this);
            }
        }
    }

    @Override
    public void visit(Conditional conditional) {
        if (conditional.getExpression() != null) {
            expressionType(conditional.getExpression());
            try {
                FileWriter myWriter = new FileWriter(actorFileName + ".j", true);
                myWriter.write("ifeq " + labelIndex + "\n");
                labelIndex++;
                myWriter.flush();
                myWriter.close();
            }
            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
//            conditional.getExpression().accept(this);
        }

        if (conditional.getThenBody() != null) {
            conditional.getThenBody().accept(this);
            try {
                expWriter.write("goto " + labelIndex + "\n");
                labelIndex++;
            }
            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

        if (conditional.getElseBody() != null) {
            try {
                expWriter.write((labelIndex - 2) + ": ");
                if (conditional.getElseBody() instanceof Block && ((Block)conditional.getElseBody()).getStatements().size() == 0)
                    expWriter.write("\n");
            }
            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            conditional.getElseBody().accept(this);
        }

        try {
            expWriter.write((labelIndex - 1) + ": ");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void visit(For loop) {

        if (loop.getInitialize() != null)
            loop.getInitialize().accept(this);

        if (loop.getCondition() != null)
            loop.getCondition().accept(this);

        if (loop.getUpdate() != null)
            loop.getUpdate().accept(this);

        if (loop.getBody() != null)
            loop.getBody().accept(this);
    }

    @Override
    public void visit(Break breakLoop) {
    }

    @Override
    public void visit(Continue continueLoop) {
    }

    @Override
    public void visit(MsgHandlerCall msgHandlerCall) {

        if (msgHandlerCall.getInstance() != null)
            msgHandlerCall.getInstance().accept(this);

        if (msgHandlerCall.getMsgHandlerName() != null)
            msgHandlerCall.getMsgHandlerName().accept(this);

        if (msgHandlerCall.getArgs() != null) {
            for (Expression expression : msgHandlerCall.getArgs()) {
                expression.accept(this);
            }
        }
    }

    @Override
    public void visit(Print print) {

        if (print.getArg() != null){
            try {
                expWriter.write("getstatic java/lang/System/out Ljava/io/PrintStream;\n");
                Type expType = expressionType(print.getArg());
                expWriter.write("invokevirtual java/io/PrintStream/println(" + bytecodeType(expType.toString()) + ")V\n");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

    }

    @Override
    public void visit(Assign assign) {
        Expression lOperand = assign.getlValue();
        Expression rOperand = assign.getrValue();
        System.out.println("assign in" + assign.getLine());
        try {
            if (lOperand != null && rOperand != null) {
                if (lOperand instanceof Identifier) {
                    expressionType(rOperand);
                    expWriter.write(getStoreCommand(((Identifier) lOperand).getName()));
                } else if (lOperand instanceof ActorVarAccess) {
                    expWriter.write("aload_0\n");
                    expressionType(rOperand);
                    expWriter.write(getFieldStoreCommand(((ActorVarAccess) lOperand).getVariable().getName()) + "\n");
                } else if (lOperand instanceof ArrayCall) {
                    Identifier arrayInstance = (Identifier) (((ArrayCall) lOperand).getArrayInstance());
                    expWriter.write(getLoadCommand(arrayInstance.getName()) + "\n");
                    expressionType(((ArrayCall) lOperand).getIndex());
                    expressionType(rOperand);
                    expWriter.write("iastore\n");
                }
            }
        } catch(IOException e) {}
    }

    public Type expressionType(Expression expression) {
        Type returnType = null;

        if (expression instanceof UnaryExpression) {
            Expression operand = ((UnaryExpression) expression).getOperand();
            String operator = ((UnaryExpression) expression).getUnaryOperator().toString();
            if (operator == "minus") {
                if (expressionType(operand) instanceof IntType) {
                    try {
                        expWriter.write("ineg\n");
                    } catch (IOException e) {}
                    returnType = new IntType();
                }
                else
                    returnType = new NoType();
            }
            else if (operator == "not") {
                if (expressionType(operand) instanceof BooleanType) {
                    try {
                        expWriter.write("ifeq " + labelIndex + "\n");
                        int nElse = labelIndex;
                        labelIndex++;
                        expWriter.write("iconst_1\n");
                        expWriter.write("goto " + labelIndex + "\n");
                        int nAfter = labelIndex;
                        labelIndex++;
                        expWriter.write(  nElse + ": " + "iconst_0\n");
                        expWriter.write(nAfter + ": ");
                        returnType = new BooleanType();
                    }
                    catch(IOException e){}
                }
                else
                    returnType = new NoType();
            }
            else if (operator == "postinc" || operator == "preinc" || operator == "predec" || operator == "postdec"){
                try {
                    returnType = new IntType();
                    switch (operator) {
                        case "postinc":
                            if (operand instanceof Identifier) {
                                expWriter.write("dup\n");
                                expressionType(operand);
                                expWriter.write("dup_x1\n");
                                expWriter.write("iconst_1\n");
                                expWriter.write("iadd\n");
                                expWriter.write(getStoreCommand(((Identifier) operand).getName()) + "\n");
                            } else if (operand instanceof ActorVarAccess) {
                                expWriter.write("dup\n");
                                expressionType(operand);
                                expWriter.write("dup_x1\n");
                                expWriter.write("iconst_1\n");
                                expWriter.write("iadd\n");
                                expWriter.write(getStoreCommand(((ActorVarAccess) operand).getVariable().getName()) + "\n");
                            } else if (operand instanceof ArrayCall) {
                                expressionType(operand);
                                expWriter.write("dup2\n");
                                expWriter.write("iaload\n");
                                expWriter.write("dup_x2\n");
                                expWriter.write("iconst_1\n");
                                expWriter.write("iadd\n");
                                expWriter.write("iastore\n");
                            }
                            break;

                        case "preinc":
                            if (operand instanceof Identifier) {
                                expWriter.write("dup\n");
                                expressionType(operand);
                                expWriter.write("iconst_1\n");
                                expWriter.write("iadd\n");
                                expWriter.write("dup_x1\n");
                                expWriter.write(getStoreCommand(((Identifier) operand).getName()) + "\n");
                            } else if (operand instanceof ActorVarAccess) {
                                expWriter.write("dup\n");
                                expressionType(operand);
                                expWriter.write("iconst_1\n");
                                expWriter.write("iadd\n");
                                expWriter.write("dup_x1\n");
                                expWriter.write(getStoreCommand(((ActorVarAccess) operand).getVariable().getName()) + "\n");
                            } else if (operand instanceof ArrayCall) {
                                expressionType(operand);
                                expWriter.write("dup2\n");
                                expWriter.write("iaload\n");
                                expWriter.write("iconst_1\n");
                                expWriter.write("iadd\n");
                                expWriter.write("dup_x2\n");
                                expWriter.write("iastore\n");
                            }
                            break;

                        case "predec":
                            if (operand instanceof Identifier) {
                                expWriter.write("dup\n");
                                expressionType(operand);
                                expWriter.write("iconst_1\n");
                                expWriter.write("isub\n");
                                expWriter.write("dup_x1\n");
                                expWriter.write(getStoreCommand(((Identifier) operand).getName()) + "\n");
                            } else if (operand instanceof ActorVarAccess) {
                                expWriter.write("dup\n");
                                expressionType(operand);
                                expWriter.write("iconst_1\n");
                                expWriter.write("isub\n");
                                expWriter.write("dup_x1\n");
                                expWriter.write(getStoreCommand(((ActorVarAccess) operand).getVariable().getName()) + "\n");
                            } else if (operand instanceof ArrayCall) {
                                expressionType(operand);
                                expWriter.write("dup2\n");
                                expWriter.write("iaload\n");
                                expWriter.write("iconst_1\n");
                                expWriter.write("isub\n");
                                expWriter.write("dup_x2\n");
                                expWriter.write("iastore\n");
                            }
                            break;

                        case "postdec":
                            if (operand instanceof Identifier) {
                                expWriter.write("dup\n");
                                expressionType(operand);
                                expWriter.write("dup_x1\n");
                                expWriter.write("iconst_1\n");
                                expWriter.write("isub\n");
                                expWriter.write(getStoreCommand(((Identifier) operand).getName()) + "\n");
                            } else if (operand instanceof ActorVarAccess) {
                                expWriter.write("dup\n");
                                expressionType(operand);
                                expWriter.write("dup_x1\n");
                                expWriter.write("iconst_1\n");
                                expWriter.write("isub\n");
                                expWriter.write(getStoreCommand(((ActorVarAccess) operand).getVariable().getName()) + "\n");
                            } else if (operand instanceof ArrayCall) {
                                expressionType(operand);
                                expWriter.write("dup2\n");
                                expWriter.write("iaload\n");
                                expWriter.write("dup_x2\n");
                                expWriter.write("iconst_1\n");
                                expWriter.write("isub\n");
                                expWriter.write("iastore\n");
                            }
                            break;

                        default:
                            break;
                    }
                }catch(IOException e){}
            }
        }

        else if (expression instanceof BinaryExpression) {
            Expression lOperand = ((BinaryExpression) expression).getLeft();
            Expression rOperand = ((BinaryExpression) expression).getRight();
            String operator = ((BinaryExpression) expression).getBinaryOperator().toString();
            if (operator == "add" || operator == "sub" || operator == "mult" || operator == "div" || operator == "mod") {
                if (expressionType(lOperand) instanceof IntType && expressionType(rOperand) instanceof IntType) {
                    returnType = new IntType();
                    try {
                        switch (operator) {
                            case "add":
                                expWriter.write("iadd\n");
                                break;
                            case "sub":
                                expWriter.write("isub\n");
                                break;
                            case "mult":
                                expWriter.write("imul\n");
                                break;
                            case "div":
                                expWriter.write("idiv\n");
                                break;
                            case "mod":
                                expWriter.write("irem\n");
                                break;
                            default:
                                break;
                        }
                    }
                    catch (IOException e) {}
                }
                else
                    returnType = new NoType();
            }
            else if (operator == "gt" || operator == "lt") {
                if (expressionType(lOperand) instanceof IntType && expressionType(rOperand) instanceof IntType) {
                    try {
                        if (operator == "gt") {
                            expWriter.write("if_icmpgt " + labelIndex + "\n");
                            int nElse = labelIndex;
                            labelIndex++;
                            expWriter.write(": iconst_1\n");
                            expWriter.write("goto " + labelIndex + "\n");
                            int nAfter = labelIndex;
                            labelIndex++;
                            expWriter.write(nElse + ": iconst_0\n");
                            expWriter.write(nAfter + ": ");
                        }
                        else if (operator == "lt") {
                            expWriter.write("if_icmplt " + labelIndex + "\n");
                            int nElse = labelIndex;
                            labelIndex++;
                            expWriter.write(": iconst_1\n");
                            expWriter.write("goto " + labelIndex + "\n");
                            int nAfter = labelIndex;
                            labelIndex++;
                            expWriter.write(nElse + ": iconst_0\n");
                            expWriter.write(nAfter + ": ");
                        }
                        returnType = new BooleanType();
                    } catch(IOException e){}
                }
                else
                    returnType = new NoType();
            }
            else if (operator == "and" || operator == "or") {
                if (expressionType(lOperand) instanceof BooleanType) {
                    try {
                        if (operator == "and") {
                            expWriter.write("ifeq " + labelIndex + "\n");
                            int nElse = labelIndex;
                            labelIndex++;
                            if (expressionType(rOperand) instanceof BooleanType) {
                                returnType = new BooleanType();
                                expWriter.write("goto " + labelIndex + "\n");
                                int nAfter = labelIndex;
                                labelIndex++;
                                expWriter.write(nElse + ": iconst_0\n");
                                expWriter.write(nAfter + ": ");
                            }
                            else {
                                returnType = new NoType();
                            }
                        }
                        else if (operator == "or") {
                            expWriter.write("ifeq " + labelIndex + "\n");
                            expWriter.write("iconst_1\n");
                            int nElse = labelIndex;
                            labelIndex++;
                            expWriter.write("goto " + labelIndex + "\n");
                            int nAfter = labelIndex;
                            labelIndex++;
                            expWriter.write(nElse + ": ");
                            if (expressionType(rOperand) instanceof BooleanType) {
                                returnType = new BooleanType();
                                expWriter.write(nAfter + ": ");
                            }
                            else {
                                returnType = new NoType();
                            }
                        }
                    }
                    catch(IOException e){}
                }
                else
                    returnType = new NoType();
            }
            else if (operator == "eq" || operator == "neq") {
                Type lType = expressionType(lOperand);
                Type rType = expressionType(rOperand);
                String objectType = "";
                if (lType.toString() == rType.toString()) {
                    if (lType instanceof IntType || lType instanceof BooleanType)
                        objectType = "i";
                    else
                        objectType = "a";
                    try {
                        if (operator == "eq") {
                            expWriter.write("if_" + objectType + "cmpeq " + labelIndex + "\n");
                            int nElse = labelIndex;
                            labelIndex++;
                            expWriter.write(": iconst_1\n");
                            expWriter.write("goto " + labelIndex + "\n");
                            int nAfter = labelIndex;
                            labelIndex++;
                            expWriter.write(nElse + ": iconst_0\n");
                            expWriter.write(nAfter + ": ");
                        }
                        else if (operator == "neq") {
                            expWriter.write("if_" + objectType + "cmpne " + labelIndex + "\n");
                            int nElse = labelIndex;
                            labelIndex++;
                            expWriter.write(": iconst_1\n");
                            expWriter.write("goto " + labelIndex + "\n");
                            int nAfter = labelIndex;
                            labelIndex++;
                            expWriter.write(nElse + ": iconst_0\n");
                            expWriter.write(nAfter + ": ");
                        }
                        returnType = new BooleanType();
                    } catch (IOException e) {}
                }
                else
                    returnType = new NoType();
            }
            else if (operator == "assign") {
                try {
                    if (lOperand instanceof Identifier) {
                        returnType = expressionType(rOperand);
                        expWriter.write(getStoreCommand(((Identifier) lOperand).getName()));
                        expWriter.write(getLoadCommand(((Identifier) lOperand).getName()) + "\n");
                    }
                    else if (lOperand instanceof ActorVarAccess) {
                        expWriter.write("aload_0\n");
                        returnType = expressionType(rOperand);
                        expWriter.write(getFieldStoreCommand(((ActorVarAccess) lOperand).getVariable().getName()) + "\n");
                        expWriter.write(getFieldLoadCommand(((ActorVarAccess) lOperand).getVariable().getName()) + "\n");
                    }
                    else if (lOperand instanceof ArrayCall) {
                        Identifier arrayInstance = (Identifier)(((ArrayCall) lOperand).getArrayInstance());
                        expWriter.write(getLoadCommand(arrayInstance.getName()) + "\n");
                        expressionType(((ArrayCall) lOperand).getIndex());
                        returnType = expressionType(rOperand);
                        expWriter.write("iastore\n");
                        expressionType(lOperand);
                    }
                } catch(IOException e) {}
            }

        }

        else if (expression instanceof Identifier) {
            returnType = getIdTypeType(((Identifier) expression).getName());
            try {
                expWriter.write(getLoadCommand(((Identifier) expression).getName()) + "\n");
            }
            catch(IOException e) {}
        }

        else if (expression instanceof Value) {
            try {
                returnType = expression.getType();
                if (expression instanceof IntValue) {
                    int constValue = ((IntValue) expression).getConstant();
                    if (constValue < 6)
                        expWriter.write("iconst_" + constValue + "\n");
                    else
                        expWriter.write("bipush " + constValue + "\n");
                } else if (expression instanceof BooleanValue) {
                    boolean constBool = ((BooleanValue) expression).getConstant();
                    int constValue = constBool ? 1 : 0;
                    expWriter.write("iconst_" + constValue + "\n");

                } else if (expression instanceof StringValue) {
                    String constValue = ((StringValue) expression).getConstant();
                    expWriter.write("ldc " + constValue + "\n");
                }
            }
            catch(IOException e) {}
        }

        else if (expression instanceof ActorVarAccess) {
            try {
                expWriter.write("aload_0\n");
                expWriter.write(getFieldLoadCommand(((ActorVarAccess) expression).getVariable().getName()) + "\n");
                returnType = getIdTypeActorVarsType(((ActorVarAccess) expression).getVariable().getName());
            }
            catch(IOException e) {}
        }

        else if (expression instanceof ArrayCall) {
            try {
                Expression arrayInstance = ((ArrayCall) expression).getArrayInstance();
                expressionType(arrayInstance);
                expressionType(((ArrayCall) expression).getIndex());
                expWriter.write("iaload\n");
                returnType = new IntType();
            }
            catch(IOException e) {}
        }

        return returnType;
    }
}