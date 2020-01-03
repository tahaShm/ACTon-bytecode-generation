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
import main.ast.node.statement.*;
import main.symbolTable.SymbolTable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class byteCodeGenerator implements Visitor {
    String actorFileName;
    String msgHandlerFileName;
    ArrayList <VarDeclaration> currentMsgArgs;
    ArrayList <VarDeclaration> currentActorVars;
    ArrayList <VarDeclaration> currentKnownActors;

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

    @Override
    public void visit(Program program) {

        if (program.getActors() != null) {
            for (ActorDeclaration actorDeclaration : program.getActors()) {
                actorDeclaration.accept(this);
            }
        }

        if (program.getMain() != null)
            program.getMain().accept(this);
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

    public String getIdTypeActorVars(String id) {
        for (VarDeclaration varDeclaration : currentActorVars) {
            if (varDeclaration.getIdentifier().getName().equals(id))
                return varDeclaration.getType().toString();
        }
        return "error";
    }

    public String getLoadCommand(String id) {
        int index = getLocalArgIdx(id);
        String command = "";
        if (index == -1){
            String idType = getIdTypeActorVars(id);
            command = "aload_0\n" + "getfield " + actorFileName + "/" + id + " " + bytecodeType(idType);
        }
        else {
            if (index > 3)
                command = "iload " + index;
            else
                command = "iload_" + index;
        }
        return command;
    }
    public String getStoreCommand(String id) {
        int index = getLocalArgIdx(id);
        String command = "";
        if (index == -1){
            String idType = getIdTypeActorVars(id);
            command = "aload_0\n" + "?????????????????" + "putfield " + actorFileName + "/" + id + " " + idType;
        }
        else {
            if (index > 3)
                command = "istore " + index;
            else
                command = "istore_" + index;
        }
        return command;
    }

    @Override
    public void visit(ActorDeclaration actorDeclaration) {

        try {
            File actorFile = new File(actorDeclaration.getName().getName() + ".j");
            actorFile.createNewFile();
            actorFileName = actorDeclaration.getName().getName();
        } catch (IOException e) {
            System.out.println("An error occurred.");
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
            System.out.println("An error occurred.");
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
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        if (actorDeclaration.getMsgHandlers() != null) {
            for (MsgHandlerDeclaration msgHandlerDeclaration : actorDeclaration.getMsgHandlers()) {
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
                    if (currentMsgArgs != null)
                        currentMsgArgs.clear();
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
                    if (currentMsgArgs != null)
                        currentMsgArgs.clear();
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

//                separator


                myWriter.write("\n.method public " + handlerDeclaration.getName().getName() + "(LActor;");

                if (handlerDeclaration.getArgs() != null) {
                    for (VarDeclaration varDeclaration : handlerDeclaration.getArgs()) {
                        myWriter.write(bytecodeType(varDeclaration.getType().toString()) );
                    }
                    myWriter.write(")V\n");
                }

                myWriter.flush();

                myWriter.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        if (handlerDeclaration.getBody() != null) {
            for (Statement statement : handlerDeclaration.getBody()) {
                statement.accept(this);
            }
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

//        if (unaryExpression.getUnaryOperator() != null)
//            System.out.println(unaryExpression.getUnaryOperator());
        if (unaryExpression.getOperand() != null)
            unaryExpression.getOperand().accept(this);
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
        if(binaryExpression.getLeft() != null)
            binaryExpression.getLeft().accept(this);
//        if(binaryExpression.getBinaryOperator() != null)
//            System.out.println(binaryExpression.getBinaryOperator());
        if(binaryExpression.getRight() != null)
            binaryExpression.getRight().accept(this);
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

        if (conditional.getExpression() != null)
            conditional.getExpression().accept(this);

        if (conditional.getThenBody() != null)
            conditional.getThenBody().accept(this);

        if (conditional.getElseBody() != null)
            conditional.getElseBody().accept(this);
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

        if (msgHandlerCall.getInstance() instanceof Self)
            System.out.println("self here@@@@");
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
                FileWriter myWriter = new FileWriter(actorFileName  + ".j" , true);
//                System.out.println(my);
                myWriter.write("getstatic java/lang/System/out Ljava/io/PrintStream;\n");
                myWriter.write(getLoadCommand(((Identifier)print.getArg()).getName()) +"\n");
                myWriter.write("invokevirtual java/io/PrintStream/println(" + bytecodeType(getIdType(((Identifier)print.getArg()).getName())) + ")V\n");
                myWriter.flush();
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

    }

    @Override
    public void visit(Assign assign) {

        if (assign.getlValue() != null)
            assign.getlValue().accept(this);

        if (assign.getrValue() != null)
            assign.getrValue().accept(this);
    }
}