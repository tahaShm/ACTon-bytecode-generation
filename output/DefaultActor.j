.class public DefaultActor
.super java/lang/Thread

.method public <init>()V
.limit stack 1
.limit locals 1
aload_0
invokespecial java/lang/Thread/<init>()V
return
.end method

.method public send_beginConsume(LActor;)V
.limit stack 2
.limit locals 32
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "there is no msghandler named beginConsume in sender"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
return
.end method
