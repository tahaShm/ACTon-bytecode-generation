.class public Main
.super java/lang/Object

.method public <init>()V
.limit stack 32
.limit locals 32
aload_0
invokespecial java/lang/Object/<init>()V
return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 32
.limit locals 32
new BufferManager
dup
iconst_4
invokespecial BufferManager/<init>(I)V
astore_1
new Producer
dup
iconst_2
invokespecial Producer/<init>(I)V
astore_2
new Consumer
dup
iconst_2
invokespecial Consumer/<init>(I)V
astore_3
aload_1
aload_2
aload_3
invokevirtual BufferManager/setKnownActors(LProducer;LConsumer;)V
aload_2
aload_1
invokevirtual Producer/setKnownActors(LBufferManager;)V
aload_3
aload_1
invokevirtual Consumer/setKnownActors(LBufferManager;)V
aload_1
invokevirtual BufferManager/initial()V
aload_2
invokevirtual Producer/initial()V
aload_3
invokevirtual Consumer/initial()V
aload_1
invokevirtual BufferManager/start()V
aload_2
invokevirtual Producer/start()V
aload_3
invokevirtual Consumer/start()V
return
.end method