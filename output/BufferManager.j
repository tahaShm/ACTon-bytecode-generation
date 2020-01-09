.class public BufferManager
.super Actor

.field producer LProducer;
.field consumer LConsumer;
.field empty Z
.field full Z
.field producerWaiting Z
.field consumerWaiting Z
.field bufferlenght I
.field nextProduce I
.field nextConsume I

.method public <init>(I)V
.limit stack 32
.limit locals 32
aload_0
iload_1
invokespecial Actor/<init>(I)V
return
.end method

.method public initial()V
.limit stack 32
.limit locals 32
aload_0
iconst_2
putfield BufferManager/bufferlenght I
aload_0
iconst_1
putfield BufferManager/empty Z
aload_0
iconst_0
putfield BufferManager/full Z
aload_0
iconst_0
putfield BufferManager/producerWaiting Z
aload_0
iconst_0
putfield BufferManager/consumerWaiting Z
aload_0
iconst_0
putfield BufferManager/nextProduce I
aload_0
iconst_0
putfield BufferManager/nextConsume I
return
.end method

.method public setKnownActors(LProducer;LConsumer;)V
.limit stack 32
.limit locals 3
aload_0
aload_1
putfield BufferManager/producer LProducer;
aload_0
aload_2
putfield BufferManager/consumer LConsumer;
return
.end method

.method public send_giveMeNextProduce(LActor;)V
.limit stack 32
.limit locals 32
aload_0
new BufferManager_giveMeNextProduce
dup
aload_0
aload_1
invokespecial BufferManager_giveMeNextProduce/<init>(LBufferManager;LActor;)V
invokevirtual BufferManager/send(LMessage;)V
return
.end method

.method public giveMeNextProduce(LActor;)V
.limit stack 32
.limit locals 32
aload_0
getfield BufferManager/full Z
ifeq 0
iconst_1
goto 1
0: iconst_0
1: nop
ifeq 2
aload_0
getfield BufferManager/producer LProducer;
aload_0
invokevirtual Producer/send_produce(LActor;)V
goto 3
2: aload_0
iconst_1
putfield BufferManager/producerWaiting Z
3: nop
return
.end method

.method public send_giveMeNextConsume(LActor;)V
.limit stack 32
.limit locals 32
aload_0
new BufferManager_giveMeNextConsume
dup
aload_0
aload_1
invokespecial BufferManager_giveMeNextConsume/<init>(LBufferManager;LActor;)V
invokevirtual BufferManager/send(LMessage;)V
return
.end method

.method public giveMeNextConsume(LActor;)V
.limit stack 32
.limit locals 32
aload_0
getfield BufferManager/empty Z
ifeq 4
iconst_1
goto 5
4: iconst_0
5: nop
ifeq 6
aload_0
getfield BufferManager/consumer LConsumer;
aload_0
invokevirtual Consumer/send_consume(LActor;)V
goto 7
6: aload_0
iconst_1
putfield BufferManager/consumerWaiting Z
7: nop
return
.end method

.method public send_ackProduce(LActor;)V
.limit stack 32
.limit locals 32
aload_0
new BufferManager_ackProduce
dup
aload_0
aload_1
invokespecial BufferManager_ackProduce/<init>(LBufferManager;LActor;)V
invokevirtual BufferManager/send(LMessage;)V
return
.end method

.method public ackProduce(LActor;)V
.limit stack 32
.limit locals 32
aload_0
aload_0
getfield BufferManager/nextProduce I
iconst_1
iadd
aload_0
getfield BufferManager/bufferlenght I
irem
putfield BufferManager/nextProduce I
aload_0
getfield BufferManager/nextProduce I
aload_0
getfield BufferManager/nextConsume I
if_icmpeq 8
iconst_0
goto 9
8: iconst_1
9: nop
ifeq 10
aload_0
iconst_1
putfield BufferManager/full Z
goto 11
10: nop
11: nop
aload_0
iconst_0
putfield BufferManager/empty Z
aload_0
getfield BufferManager/consumerWaiting Z
ifeq 12
aload_0
getfield BufferManager/consumer LConsumer;
aload_0
invokevirtual Consumer/send_consume(LActor;)V
aload_0
iconst_0
putfield BufferManager/consumerWaiting Z
goto 13
12: nop
13: nop
return
.end method

.method public send_ackConsume(LActor;)V
.limit stack 32
.limit locals 32
aload_0
new BufferManager_ackConsume
dup
aload_0
aload_1
invokespecial BufferManager_ackConsume/<init>(LBufferManager;LActor;)V
invokevirtual BufferManager/send(LMessage;)V
return
.end method

.method public ackConsume(LActor;)V
.limit stack 32
.limit locals 32
aload_0
aload_0
getfield BufferManager/nextConsume I
iconst_1
iadd
aload_0
getfield BufferManager/bufferlenght I
irem
putfield BufferManager/nextConsume I
aload_0
getfield BufferManager/nextConsume I
aload_0
getfield BufferManager/nextProduce I
if_icmpeq 14
iconst_0
goto 15
14: iconst_1
15: nop
ifeq 16
aload_0
iconst_1
putfield BufferManager/empty Z
goto 17
16: nop
17: nop
aload_0
iconst_0
putfield BufferManager/full Z
aload_0
getfield BufferManager/producerWaiting Z
ifeq 18
aload_0
getfield BufferManager/producer LProducer;
aload_0
invokevirtual Producer/send_produce(LActor;)V
aload_0
iconst_0
putfield BufferManager/producerWaiting Z
goto 19
18: nop
19: nop
return
.end method
