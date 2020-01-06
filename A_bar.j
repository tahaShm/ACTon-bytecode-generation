.class public A_bar
.super Message

.field private receiver LA;
.field private sender LActor;

.method public <init>(LA;LActor;)V
.limit stack 2
.limit locals 16
aload_0
invokespecial Message/<init>()V
aload_0
aload_1
putfield A_bar/receiver LA;
aload_0
aload_2
putfield A_bar/sender LActor;
return
.end method

.method public execute()V
.limit stack 3
.limit locals 16
aload_0
getfield A_bar/receiver LA;
aload_0
getfield A_bar/sender LActor;
invokevirtual A/bar(LActor;)V
return
.end method