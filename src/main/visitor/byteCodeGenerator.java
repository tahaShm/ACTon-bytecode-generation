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
        command = "aload_0\n" + "putfield " + actorFileName + "/" + id + " " + bytecodeType(idType);
        return command;
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
//                    if (currentMsgArgs != null)
//                        currentMsgArgs.clear();
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
                    for (Statement statement : handlerDeclaration.getBody()) {
                        statement.accept(this);
                    }
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
//                    if (currentMsgArgs != null)
//                        currentMsgArgs.clear();
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

                if (handlerDeclaration.getBody() != null) {
                    for (Statement statement : handlerDeclaration.getBody()) {
                        statement.accept(this);
                    }
                }

                FileWriter myWriter2 = new FileWriter(actorFileName  + ".j" , true);
                myWriter2.write("return\n" +
                        ".end method\n");
                myWriter2.flush();
                myWriter2.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
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
            try {
                expWriter = new FileWriter(actorFileName + ".j", true);
                unaryExpression.getOperand().accept(this);
                Type uExpType = expressionType(unaryExpression.getOperand());
                expWriter.write(bytecodeType(uExpType.toString()));
                // we must handle last operand :pp
                expWriter.flush();
                expWriter.close();
            } catch (IOException e){

            }
        }
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
        if(binaryExpression.getLeft() != null)
            binaryExpression.getLeft().accept(this);
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
                myWriter.write("getstatic java/lang/System/out Ljava/io/PrintStream;\n");
                myWriter.write(getLoadCommand(((Identifier)print.getArg()).getName()) + "\n");
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

    public Type expressionType(Expression expression) {
        Type returnType = null;

        if (expression instanceof UnaryExpression) {
            Expression operand = ((UnaryExpression) expression).getOperand();
            String operator = ((UnaryExpression) expression).getUnaryOperator().toString();
            if (operator == "minus" || operator == "preinc" || operator == "postinc" || operator == "predec" || operator == "postdec") {
                if (expressionType(operand) instanceof IntType) {
                    try {
                        switch (operator) {
                            case "minus":
                                expWriter.write("ineg\n");
                                break;
                            case "preinc":
                                expWriter.write("iconst_1\n");
                                expWriter.write("iadd\n");
                                expWriter.write(getStoreCommand(((Identifier)operand).getName()) + "\n");
                                break;
                            case "postinc":
                                expWriter.write("iconst_1\n");
                                expWriter.write("iadd\n");
                                expWriter.write(getStoreCommand(((Identifier)operand).getName()) + "\n");
                                break;
                            case "predec":
                                expWriter.write("iconst_1\n");
                                expWriter.write("isub\n");
                                expWriter.write(getStoreCommand(((Identifier)operand).getName()) + "\n");
                                break;
                            case "postdec":
                                expWriter.write("iconst_1\n");
                                expWriter.write("isub\n");
                                expWriter.write(getStoreCommand(((Identifier)operand).getName()) + "\n");
                                break;
                            default:
                                break;
                        }
                    }
                    catch (IOException e) {}
                    returnType = new IntType();
                }
                else
                    returnType = new NoType();
            } //must be handled
            else if (operator == "not") {
                if (expressionType(operand) instanceof BooleanType) {
                    try {
                        expWriter.write("ifeq(" + labelIndex + ")\n");
                        int nElse = labelIndex;
                        labelIndex++;
                        expWriter.write("iconst_1\n");
                        expWriter.write("goto " + labelIndex + "\n");
                        int nAfter = labelIndex;
                        labelIndex++;
                        expWriter.write(  nElse + ": " + labelIndex + "\n");
                        expWriter.write(nAfter + ": ");
                        returnType = new BooleanType();
                    }
                    catch(IOException e){}
                }
                else
                    returnType = new NoType();
            }
        }
        else if (expression instanceof BinaryExpression) { //debugging
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
            } //must be handled
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
                            expWriter.write("ifeq(" + labelIndex + ")\n");
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
                            expWriter.write("ifeq(" + labelIndex + ")\n");
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
            else if (operator == "assign") {
                try {
                    if (lOperand instanceof Identifier) {
                        returnType = expressionType(rOperand);
                        expWriter.write(getStoreCommand(((Identifier) lOperand).getName()));
                    } else if (lOperand instanceof ActorVarAccess) {
                        returnType = expressionType(rOperand);
                        expWriter.write(getFieldStoreCommand(((ActorVarAccess) expression).getVariable().getName()) + "\n"); //doubt
                    } else if (lOperand instanceof ArrayCall) {
                        Identifier arrayInstance = (Identifier)(((ArrayCall) expression).getArrayInstance());
                        expWriter.write(getLoadCommand(arrayInstance.getName()));
                        expressionType(((ArrayCall) expression).getIndex());
                        returnType = expressionType(rOperand);
                        expWriter.write("iastore\n");
                    }
                } catch(IOException e) {}
            }
            else if (operator == "eq" || operator == "neq") {
                if (expressionType(lOperand).toString() == expressionType(rOperand).toString()) {
                    try {
                        if (operator == "eq") {
                            expWriter.write("if_icmpeq " + labelIndex + "\n");
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
                            expWriter.write("if_icmpne " + labelIndex + "\n");
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
                    }
                    catch(IOException e){}
                }
                else
                    returnType = new NoType();
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
                    expWriter.write("ldc \"" + constValue + "\"\n");
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
                Identifier arrayInstance = (Identifier)(((ArrayCall) expression).getArrayInstance());
                expWriter.write(getLoadCommand(arrayInstance.getName()));
                expressionType(((ArrayCall) expression).getIndex());
                expWriter.write("iaload\n");
                returnType = new IntType();
            }
            catch(IOException e) {}
        }

        return returnType;
    }
}