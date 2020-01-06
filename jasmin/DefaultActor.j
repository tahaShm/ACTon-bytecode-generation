.class public DefaultActor
.super java/lang/Thread

.method public <init>()V
.limit stack 1
.limit locals 1
aload_0
invokespecial java/lang/Thread/<init>()V
return
.end method

.method public send_foo(LActor;I)V
.limit stack 2
.limit locals 3
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "there is no msghandler named foo in sender"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
return
.end method

.method public send_bar(LActor;I)V
.limit stack 2
.limit locals 3
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "there is no msghandler named bar in sender"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
return
.end method