.class public A
.super Actor

.field b LB;
.field i I

.method public <init>(I)V
.limit stack 2
.limit locals 2
aload_0
iload_1
invokespecial Actor/<init>(I)V
return
.end method

.method public initial()V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_0
getfield A/i I
invokevirtual java/io/PrintStream/println(I)V
return
.end method

.method public setKnownActors(LB;)V
.limit stack 2
.limit locals 2
aload_0
aload_1
putfield A/b LB;
return
.end method

.method public send_bar(LActor;I)V
.limit stack 6
.limit locals 3
aload_0
new A_bar
dup
aload_0
aload_1
iload_2
invokespecial A_bar/<init>(LA;LActor;I)V
invokevirtual A/send(LMessage;)V
return
.end method

.method public bar(LActor;I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_0
getfield A/i I
invokevirtual java/io/PrintStream/println(I)V
return
.end method
