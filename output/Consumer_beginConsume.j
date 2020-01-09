.class public Consumer_beginConsume
.super Message

.field private receiver LConsumer;
.field private sender LActor;

.method public <init>(LConsumer;LActor;)V
.limit stack 32
.limit locals 32
aload_0
invokespecial Message/<init>()V
aload_0
aload_1
putfield Consumer_beginConsume/receiver LConsumer;
aload_0
aload_2
putfield Consumer_beginConsume/sender LActor;
return
.end method

.method public execute()V
.limit stack 32
.limit locals 32
aload_0
getfield Consumer_beginConsume/receiver LConsumer;
aload_0
getfield Consumer_beginConsume/sender LActor;
invokevirtual Consumer/beginConsume(LActor;)V
return
.end method