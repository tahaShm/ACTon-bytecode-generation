.class public Producer_beginProduce
.super Message

.field private receiver LProducer;
.field private sender LActor;

.method public <init>(LProducer;LActor;)V
.limit stack 32
.limit locals 32
aload_0
invokespecial Message/<init>()V
aload_0
aload_1
putfield Producer_beginProduce/receiver LProducer;
aload_0
aload_2
putfield Producer_beginProduce/sender LActor;
return
.end method

.method public execute()V
.limit stack 32
.limit locals 32
aload_0
getfield Producer_beginProduce/receiver LProducer;
aload_0
getfield Producer_beginProduce/sender LActor;
invokevirtual Producer/beginProduce(LActor;)V
return
.end method