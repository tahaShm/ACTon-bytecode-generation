.class public A
.super Actor

.field b LB;
.field i I

.method public <init>(I)V
.limit stack 2
.limit locals 2
aload_0
iload_1
invokespecial Actor/<init>(I)V
return
.end method

.method public initial()V
.method public setKnownActors(LB;)V
.method public send_bar(LActor;I)V
.limit stack 6
.limit lacals 3
aload_0
new A_bar
dup
aload_0
aload_1
iload_2
invokespecial A_bar/<init>(LA;LActor;I)V
invokevirtual A/send(LMessage;)V
return
.end method
.method public bar(LActor;I)V
