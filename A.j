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

.method public bar(LActor;I)V
iload_1
iconst_3
if_icmplt 0
iconst_1
goto 1
0: iconst_0
1: ifeq 2
iload_1
bipush 6
if_icmpgt 3
iconst_1
goto 4
3: iconst_0
4: ifeq 5
iconst_2
istore_1
goto 6
5: 
6: goto 7
2: 
7: return
.end method
