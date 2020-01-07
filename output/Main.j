.class public Main
.super java/lang/Object

.method public <init>()V
.limit stack 1
.limit locals 1
aload_0
invokespecial java/lang/Object/<init>()V
return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 16
.limit locals 16
new A
dup
bipush 10
invokespecial A/<init>(I)V
astore_1
aload_1
invokevirtual A/setKnownActors()V
aload_1
iconst_1
iconst_1
ldc "hi"
iconst_2
bipush 6
imul
invokevirtual A/initial(IZ[[Ljava/lang/StringI)V
aload_1
invokevirtual A/start()V
return
.end method