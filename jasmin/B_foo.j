.class public B_foo
.super Message

.field private i I
.field private receiver LB;
.field private sender LActor;

.method public <init>(LB;LActor;I)V
.limit stack 2
.limit locals 4
aload_0
invokespecial Message/<init>()V
aload_0
aload_1
putfield B_foo/receiver LB;
aload_0
aload_2
putfield B_foo/sender LActor;
aload_0
iload_3
putfield B_foo/i I
return
.end method

.method public execute()V
.limit stack 3
.limit locals 1
aload_0
getfield B_foo/receiver LB;
aload_0
getfield B_foo/sender LActor;
aload_0
getfield B_foo/i I
invokevirtual B/foo(LActor;I)V
return
.end method