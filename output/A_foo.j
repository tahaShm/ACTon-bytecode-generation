.class public A_foo
.super Message

.field private r I
.field private receiver LA;
.field private sender LActor;

.method public <init>(LA;LActor;I)V
.limit stack 2
.limit locals 16
aload_0
invokespecial Message/<init>()V
aload_0
aload_1
putfield A_foo/receiver LA;
aload_0
aload_2
putfield A_foo/sender LActor;
aload_0
iload_3
putfield A_foo/r I
return
.end method

.method public execute()V
.limit stack 3
.limit locals 16
aload_0
getfield A_foo/receiver LA;
aload_0
getfield A_foo/sender LActor;
aload_0
getfield A_foo/r I
invokevirtual A/foo(LActor;I)V
return
.end method