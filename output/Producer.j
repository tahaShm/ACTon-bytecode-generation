.class public Producer
.super Actor

.field buffer LBufferManager;

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
aload_0
invokevirtual Producer/send_beginProduce(LActor;)V
return
.end method

.method public setKnownActors(LBufferManager;)V
.limit stack 32
.limit locals 2
aload_0
aload_1
putfield Producer/buffer LBufferManager;
return
.end method

.method public send_produce(LActor;)V
.limit stack 32
.limit locals 32
aload_0
new Producer_produce
dup
aload_0
aload_1
invokespecial Producer_produce/<init>(LProducer;LActor;)V
invokevirtual Producer/send(LMessage;)V
return
.end method

.method public produce(LActor;)V
.limit stack 32
.limit locals 32
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "producer is producing"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
aload_0
getfield Producer/buffer LBufferManager;
aload_0
invokevirtual BufferManager/send_ackProduce(LActor;)V
aload_0
aload_0
invokevirtual Producer/send_beginProduce(LActor;)V
return
.end method

.method public send_beginProduce(LActor;)V
.limit stack 32
.limit locals 32
aload_0
new Producer_beginProduce
dup
aload_0
aload_1
invokespecial Producer_beginProduce/<init>(LProducer;LActor;)V
invokevirtual Producer/send(LMessage;)V
return
.end method

.method public beginProduce(LActor;)V
.limit stack 32
.limit locals 32
aload_0
getfield Producer/buffer LBufferManager;
aload_0
invokevirtual BufferManager/send_giveMeNextProduce(LActor;)V
return
.end method
