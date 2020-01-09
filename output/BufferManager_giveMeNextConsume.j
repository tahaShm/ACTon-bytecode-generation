.class public BufferManager_giveMeNextConsume
.super Message

.field private receiver LBufferManager;
.field private sender LActor;

.method public <init>(LBufferManager;LActor;)V
.limit stack 32
.limit locals 32
aload_0
invokespecial Message/<init>()V
aload_0
aload_1
putfield BufferManager_giveMeNextConsume/receiver LBufferManager;
aload_0
aload_2
putfield BufferManager_giveMeNextConsume/sender LActor;
return
.end method

.method public execute()V
.limit stack 32
.limit locals 32
aload_0
getfield BufferManager_giveMeNextConsume/receiver LBufferManager;
aload_0
getfield BufferManager_giveMeNextConsume/sender LActor;
invokevirtual BufferManager/giveMeNextConsume(LActor;)V
return
.end method