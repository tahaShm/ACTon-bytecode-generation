.class public Consumer
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
invokevirtual Consumer/send_beginConsume(LActor;)V
return
.end method

.method public setKnownActors(LBufferManager;)V
.limit stack 32
.limit locals 2
aload_0
aload_1
putfield Consumer/buffer LBufferManager;
return
.end method

.method public send_consume(LActor;)V
.limit stack 32
.limit locals 32
aload_0
new Consumer_consume
dup
aload_0
aload_1
invokespecial Consumer_consume/<init>(LConsumer;LActor;)V
invokevirtual Consumer/send(LMessage;)V
return
.end method

.method public consume(LActor;)V
.limit stack 32
.limit locals 32
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "consumer is consuming"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
aload_0
getfield Consumer/buffer LBufferManager;
aload_0
invokevirtual BufferManager/send_ackConsume(LActor;)V
aload_0
aload_0
invokevirtual Consumer/send_beginConsume(LActor;)V
return
.end method

.method public send_beginConsume(LActor;)V
.limit stack 32
.limit locals 32
aload_0
new Consumer_beginConsume
dup
aload_0
aload_1
invokespecial Consumer_beginConsume/<init>(LConsumer;LActor;)V
invokevirtual Consumer/send(LMessage;)V
return
.end method

.method public beginConsume(LActor;)V
.limit stack 32
.limit locals 32
aload_0
getfield Consumer/buffer LBufferManager;
aload_0
invokevirtual BufferManager/send_giveMeNextConsume(LActor;)V
return
.end method
