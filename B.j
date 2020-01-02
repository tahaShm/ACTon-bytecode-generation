.class public B
.super Actor

.field a LA;


.method public <init>(I)V
.limit stack 2
.limit locals 2
aload_0
iload_1
invokespecial Actor/<init>(I)V
return
.end method

.method public setKnownActors(LA;)V
.method public send_foo(LActor;I)V
.limit stack 6
.limit lacals 3
aload_0
new B_foo
dup
aload_0
aload_1
iload_2
invokespecial B_foo/<init>(LB;LActor;I)V
invokevirtual B/send(LMessage;)V
return
.end method
.method public foo(LActor;I)V
