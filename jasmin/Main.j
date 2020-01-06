.class public Main
.super java/lang/Object

.method public <init>()V
.limit stack 1
.limit locals 1
0: aload_0
1: invokespecial java/lang/Object/<init>()V
4: return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 3
.limit locals 3
new A
dup
iconst_5
invokespecial A/<init>(I)V
astore_1
new B
dup
iconst_2
invokespecial B/<init>(I)V
astore_2
aload_1
aload_2
invokevirtual A/setKnownActors(LB;)V
aload_2
aload_1
invokevirtual B/setKnownActors(LA;)V
aload_1
invokevirtual A/initial()V
aload_1
invokevirtual A/start()V
aload_2
invokevirtual B/start()V
return
.end method