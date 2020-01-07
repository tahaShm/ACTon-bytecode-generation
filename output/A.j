.class public A
.super Actor

.field b LB;
.field i I
.field arr [I

.method public <init>(I)V
.limit stack 2
.limit locals 2
aload_0
iload_1
invokespecial Actor/<init>(I)V
return
.end method

.method public initial()V
aload_0
getfield A/b LB;
aload_0
aload_0
getfield A/i I
invokevirtual B/send_foo(LActor;I)V
return
.end method

.method public setKnownActors(LB;)V
.limit stack 2
.limit locals 2
aload_0
aload_1
putfield A/b LB;
return
.end method

.method public send_bar22(LActor;I)V
.limit stack 6
.limit locals 3
aload_0
new A_bar22
dup
aload_0
aload_1
iload_2
invokespecial A_bar22/<init>(LA;LActor;I)V
invokevirtual A/send(LMessage;)V
return
.end method

.method public bar22(LActor;I)V
return
.end method

.method public send_bar(LActor;)V
.limit stack 6
.limit locals 3
aload_0
new A_bar
dup
aload_0
aload_1
iload_2
invokespecial A_bar/<init>(LA;LActor;)V
invokevirtual A/send(LMessage;)V
return
.end method

.method public bar(LActor;)V
aload 4
iconst_2
dup2
iaload
iconst_1
iadd
dup_x2
iastore
istore_1
return
.end method
