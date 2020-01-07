.class public A_bar22
.super Message

.field private aaa I
.field private receiver LA;
.field private sender LActor;

.method public <init>(LA;LActor;I)V
.limit stack 2
.limit locals 16
aload_0
invokespecial Message/<init>()V
aload_0
aload_1
putfield A_bar22/receiver LA;
aload_0
aload_2
putfield A_bar22/sender LActor;
aload_0
iload_3
putfield A_bar22/aaa I
return
.end method

.method public execute()V
.limit stack 3
.limit locals 16
aload_0
getfield A_bar22/receiver LA;
aload_0
getfield A_bar22/sender LActor;
aload_0
getfield A_bar22/aaa I
invokevirtual A/bar22(LActor;I)V
return
.end method