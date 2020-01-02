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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class byteCodeGenerator implements Visitor {
    String actorFileName;
    String msgHandlerFileName;

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
                for (VarDeclaration varDeclaration : actorDeclaration.getKnownActors()) {
                    myWriter.write(".field " + varDeclaration.getIdentifier().getName() + " L" + varDeclaration.getType().toString() + ";\n");
                }
            }

            if (actorDeclaration.getActorVars() != null) {
                for (VarDeclaration varDeclaration : actorDeclaration.getActorVars()) {
                    myWriter.write(".field " + varDeclaration.getIdentifier().getName() + " " +  bytecodeType(varDeclaration.getType().toString()));
                }
            }
            myWriter.write("\n\n.method public <init>(I)V\n" +
                    ".limit stack 2\n" +
                    ".limit locals 2\n" +
                    "aload_0\n" +
                    "iload_1\n" +
                    "invokespecial Actor/<init>(I)V\n" +
                    "return\n" +
                    ".end method\n\n"
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
            myWriter.write(".method public setKnownActors(");
            if (actorDeclaration.getKnownActors() != null) {
                for (VarDeclaration varDeclaration : actorDeclaration.getKnownActors()) {
                    myWriter.write(bytecodeType(varDeclaration.getType().toString()));
                }
            }
            myWriter.write(")V\n");

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
                myWriter.write(".method public " + handlerDeclaration.getName().getName() + "(");

                if (handlerDeclaration.getArgs() != null) {
                    for (VarDeclaration varDeclaration : handlerDeclaration.getArgs()) {
                        myWriter.write(bytecodeType(varDeclaration.getType().toString()) );
                    }
                    myWriter.write(")V\n");
                }

                myWriter.flush();
                myWriter.close();
            }

            else {
                myWriter.write(".method public send_" + handlerDeclaration.getName().getName() + "(LActor;");

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
                    for (VarDeclaration varDeclaration : handlerDeclaration.getArgs()) {
                        myWriter.write(bytecodeType(varDeclaration.getType().toString()) );
                    }
                    myWriter.write(")V\n");
                }
                myWriter.write("invokevirtual " + actorFileName + "/send(LMessage;)V\n" +
                    "return\n" +
                    ".end method\n"
                );
                myWriter.flush();

//                separator


                myWriter.write(".method public " + handlerDeclaration.getName().getName() + "(LActor;");

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

        if (handlerDeclaration.getLocalVars() != null) {
            for (VarDeclaration varDeclaration : handlerDeclaration.getLocalVars()) {
                varDeclaration.accept(this);
            }
        }

        if (handlerDeclaration.getBody() != null) {
            for (Statement statement : handlerDeclaration.getBody()) {
                statement.accept(this);
            }
        }
    }

    @Override
    public void visit(VarDeclaration varDeclaration) {
        System.out.println(varDeclaration.toString());

        if (varDeclaration.getIdentifier() != null)
            varDeclaration.getIdentifier().accept(this);
    }

    @Override
    public void visit(Main mainActors) {
        System.out.println(mainActors.toString());

        if (mainActors.getMainActors() != null) {
            for (ActorInstantiation actorInstantiation : mainActors.getMainActors()) {
                actorInstantiation.accept(this);
            }
        }
    }

    @Override
    public void visit(ActorInstantiation actorInstantiation) {
        System.out.println(actorInstantiation.toString());

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
        System.out.println(unaryExpression.toString());

//        if (unaryExpression.getUnaryOperator() != null)
//            System.out.println(unaryExpression.getUnaryOperator());
        if (unaryExpression.getOperand() != null)
            unaryExpression.getOperand().accept(this);
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
        System.out.println(binaryExpression.toString());
        System.out.println("----------------");
        System.out.println(binaryExpression.getLeft().toString());
        System.out.println(binaryExpression.getRight().toString());
        System.out.println("++++++++++++++++");
        if(binaryExpression.getLeft() != null)
            binaryExpression.getLeft().accept(this);
//        if(binaryExpression.getBinaryOperator() != null)
//            System.out.println(binaryExpression.getBinaryOperator());
        if(binaryExpression.getRight() != null)
            binaryExpression.getRight().accept(this);
    }

    @Override
    public void visit(ArrayCall arrayCall) {
        System.out.println(arrayCall.toString());

        if (arrayCall.getArrayInstance() != null)
            arrayCall.getArrayInstance().accept(this);
        if (arrayCall.getIndex() != null)
            arrayCall.getIndex().accept(this);
    }

    @Override
    public void visit(ActorVarAccess actorVarAccess) {
        System.out.println(actorVarAccess.toString());

        if (actorVarAccess.getSelf() != null)
            actorVarAccess.getSelf().accept(this);

        if (actorVarAccess.getVariable() != null)
            actorVarAccess.getVariable().accept(this);
    }

    @Override
    public void visit(Identifier identifier) {
        System.out.println(identifier.toString());
    }

    @Override
    public void visit(Self self) {
        System.out.println(self.toString());
    }

    @Override
    public void visit(Sender sender) {
        System.out.println(sender.toString());
    }

    @Override
    public void visit(BooleanValue value) {
        System.out.println(value.toString());
    }

    @Override
    public void visit(IntValue value) {
        System.out.println(value.toString());
    }

    @Override
    public void visit(StringValue value) {
        System.out.println(value.toString());
    }

    @Override
    public void visit(Block block) {
        System.out.println(block.toString());

        if (block.getStatements() != null) {
            for (Statement statement : block.getStatements()) {
                statement.accept(this);
            }
        }
    }

    @Override
    public void visit(Conditional conditional) {
        System.out.println(conditional.toString());

        if (conditional.getExpression() != null)
            conditional.getExpression().accept(this);

        if (conditional.getThenBody() != null)
            conditional.getThenBody().accept(this);

        if (conditional.getElseBody() != null)
            conditional.getElseBody().accept(this);
    }

    @Override
    public void visit(For loop) {
        System.out.println(loop.toString());

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
        System.out.println(breakLoop.toString());
    }

    @Override
    public void visit(Continue continueLoop) {
        System.out.println(continueLoop.toString());
    }

    @Override
    public void visit(MsgHandlerCall msgHandlerCall) {
        System.out.println(msgHandlerCall.toString());

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
        System.out.println(print.toString());

        if (print.getArg() != null)
            print.getArg().accept(this);
    }

    @Override
    public void visit(Assign assign) {
        System.out.println(assign.toString());

        if (assign.getlValue() != null)
            assign.getlValue().accept(this);

        if (assign.getrValue() != null)
            assign.getrValue().accept(this);
    }
}