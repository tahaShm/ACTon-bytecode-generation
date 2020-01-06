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
iconst_2
iconst_2
iadd
aload_0
getfield A/i I
iconst_4
iadd
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

.method public bar(LActor;[I)V
aload_0
getfield A/i I
iconst_1
iadd
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_0
aload_0
getfield A/arr [I
iconst_1
iaload
iconst_2
iconst_3
imul
iadd
aload_0
getfield A/i I
iadd
invokevirtual java/io/PrintStream/println(I)V
return
.end method
