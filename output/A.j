.class public A
.super Actor

.field i I
.field j Z
.field k [[Ljava/lang/String
.field r [I

.method public <init>(I)V
.limit stack 2
.limit locals 2
aload_0
iload_1
invokespecial Actor/<init>(I)V
return
.end method

.method public initial(IZ[[Ljava/lang/StringI)V
.limit stack 16
.limit locals 16
aload_0
iload_2
iconst_1
iadd
putfield A/i I
aload_0
iload_3
ifeq 0
iconst_1
goto 1
0: iconst_0
1: nop
ifeq 2
iconst_1
goto 3
2: iconst_0
3: nop
putfield A/j Z
aload_0
aload 4
putfield A/k [[Ljava/lang/String
aload_0
aload_0
iload 5
invokevirtual A/send_foo(LActor;I)V
return
.end method

.method public setKnownActors()V
.limit stack 2
.limit locals 1
return
.end method

.method public send_foo(LActor;I)V
.limit stack 6
.limit locals 3
aload_0
new A_foo
dup
aload_0
aload_1
iload_2
invokespecial A_foo/<init>(LA;LActor;I)V
invokevirtual A/send(LMessage;)V
return
.end method

.method public foo(LActor;I)V
.limit stack 16
.limit locals 16
aload_0
aload_0
getfield A/r [I
iconst_1
iconst_1
iastore
aload_0
aload_0
getfield A/r [I
iconst_3
aload 6
iconst_0
iaload
iload_2
iadd
iastore
getstatic java/lang/System/out Ljava/io/PrintStream;
iload_2
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
iload_3
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 7
invokevirtual java/io/PrintStream/println([[Ljava/lang/String)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_0
aload_0
getfield A/i I
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_0
getfield A/j Z
invokevirtual java/io/PrintStream/println(Z)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_0
aload_0
getfield A/k [[Ljava/lang/String
invokevirtual java/io/PrintStream/println([[Ljava/lang/String)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_0
aload_0
getfield A/r [I
invokevirtual java/io/PrintStream/println([I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_0
dup
getfield A/i I
dup_x1
iconst_1
iadd
istore_3
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_0
aload_0
getfield A/i I
invokevirtual java/io/PrintStream/println(I)V
iconst_1
istore 4
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "--------------------------------"
invokevirtual java/io/PrintStream/println([[Ljava/lang/String)V
iload 4
dup_x1
iconst_1
iadd
istore 4
aload 6
iconst_1
dup2
iaload
dup_x2
iconst_1
iadd
iastore
iadd
aload_0
dup
getfield A/i I
dup_x1
iconst_1
iadd
istore_3
iadd
istore_3
getstatic java/lang/System/out Ljava/io/PrintStream;
iload_3
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 4
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 6
iconst_1
iaload
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_0
aload_0
getfield A/i I
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "--------------------------------"
invokevirtual java/io/PrintStream/println([[Ljava/lang/String)V
iload 4
dup_x1
iconst_1
isub
istore 4
aload 6
iconst_1
dup2
iaload
dup_x2
iconst_1
isub
iastore
iadd
aload_0
dup
getfield A/i I
dup_x1
iconst_1
isub
istore_3
iadd
istore_3
getstatic java/lang/System/out Ljava/io/PrintStream;
iload_3
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 4
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 6
iconst_1
iaload
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_0
aload_0
getfield A/i I
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "--------------------------------"
invokevirtual java/io/PrintStream/println([[Ljava/lang/String)V
iload 4
iconst_1
iadd
dup_x1
istore 4
aload 6
iconst_1
dup2
iaload
iconst_1
iadd
dup_x2
iastore
iadd
aload_0
dup
getfield A/i I
iconst_1
iadd
dup_x1
istore_3
iadd
istore_3
getstatic java/lang/System/out Ljava/io/PrintStream;
iload_3
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 4
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 6
iconst_1
iaload
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_0
aload_0
getfield A/i I
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "--------------------------------"
invokevirtual java/io/PrintStream/println([[Ljava/lang/String)V
iload 4
iconst_1
isub
dup_x1
istore 4
aload 6
iconst_1
dup2
iaload
iconst_1
isub
dup_x2
iastore
iadd
aload_0
dup
getfield A/i I
iconst_1
isub
dup_x1
istore_3
iadd
istore_3
getstatic java/lang/System/out Ljava/io/PrintStream;
iload_3
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 4
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 6
iconst_1
iaload
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_0
aload_0
getfield A/i I
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "--------------------------------"
invokevirtual java/io/PrintStream/println([[Ljava/lang/String)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_0
dup
getfield A/i I
dup_x1
iconst_1
iadd
istore_3
aload_0
dup
getfield A/i I
iconst_1
isub
dup_x1
istore_3
iadd
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "--------------------------------"
invokevirtual java/io/PrintStream/println([[Ljava/lang/String)V
iconst_1
ifeq 4
iconst_0
goto 5
4: iconst_0
5: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_1
ifeq 6
iconst_0
ifeq 7
iconst_1
goto 8
7: iconst_0
8: nop
goto 9
6: iconst_0
9: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_1
ifeq 10
aload_0
aload_0
getfield A/j Z
goto 11
10: iconst_0
11: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_1
ifeq 12
aload_0
aload_0
getfield A/j Z
ifeq 13
iconst_1
goto 14
13: iconst_0
14: nop
goto 15
12: iconst_0
15: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_1
ifeq 16
aload_0
aload_0
getfield A/j Z
ifeq 17
iconst_1
goto 18
17: iconst_0
18: nop
ifeq 19
iconst_1
goto 20
19: iconst_0
20: nop
ifeq 21
iconst_1
goto 22
21: iconst_0
22: nop
goto 23
16: iconst_0
23: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_0
ifeq 24
aload_0
aload_0
getfield A/j Z
goto 25
24: iconst_0
25: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_0
ifeq 26
iconst_1
goto 27
26: iconst_0
27: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_0
ifeq 28
iconst_1
ifeq 29
iconst_1
goto 30
29: iconst_0
30: nop
ifeq 31
iconst_1
goto 32
31: iconst_0
32: nop
goto 33
28: iconst_0
33: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_0
ifeq 34
iconst_0
goto 35
34: iconst_0
35: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_0
ifeq 36
iconst_0
ifeq 37
iconst_1
goto 38
37: iconst_0
38: nop
ifeq 39
iconst_1
goto 40
39: iconst_0
40: nop
goto 41
36: iconst_0
41: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
aload_0
aload_0
getfield A/j Z
ifeq 42
iconst_1
goto 43
42: iconst_0
43: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
aload_0
aload_0
getfield A/j Z
ifeq 44
iconst_1
goto 45
44: iconst_0
45: nop
ifeq 46
iconst_1
goto 47
46: iconst_0
47: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
aload_0
aload_0
getfield A/j Z
ifeq 48
iconst_1
goto 49
48: iconst_0
49: nop
ifeq 50
iconst_1
goto 51
50: iconst_0
51: nop
ifeq 52
iconst_1
goto 53
52: iconst_0
53: nop
ifeq 54
iconst_1
goto 55
54: iconst_0
55: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
aload_0
aload_0
getfield A/j Z
ifeq 56
iconst_0
goto 57
56: iconst_0
57: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_1
ifeq 58
iconst_1
goto 59
58: iconst_0
59: nop
ifeq 60
iconst_1
goto 61
60: iconst_0
61: nop
ifeq 62
iconst_0
goto 63
62: iconst_0
63: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "--------------------------------"
invokevirtual java/io/PrintStream/println([[Ljava/lang/String)V
iconst_1
ifeq 64
iconst_1
goto 65
64: iconst_0
65: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_1
ifeq 66
iconst_1
goto 67
66: iconst_0
ifeq 68
iconst_1
goto 69
68: iconst_0
69: nop
67: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_1
ifeq 70
iconst_1
goto 71
70: aload_0
aload_0
getfield A/j Z
71: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_1
ifeq 72
iconst_1
goto 73
72: aload_0
aload_0
getfield A/j Z
ifeq 74
iconst_1
goto 75
74: iconst_0
75: nop
73: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_1
ifeq 76
iconst_1
goto 77
76: aload_0
aload_0
getfield A/j Z
ifeq 78
iconst_1
goto 79
78: iconst_0
79: nop
ifeq 80
iconst_1
goto 81
80: iconst_0
81: nop
ifeq 82
iconst_1
goto 83
82: iconst_0
83: nop
77: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_0
ifeq 84
iconst_1
goto 85
84: aload_0
aload_0
getfield A/j Z
85: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_0
ifeq 86
iconst_1
goto 87
86: iconst_1
87: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_0
ifeq 88
iconst_1
goto 89
88: iconst_1
ifeq 90
iconst_1
goto 91
90: iconst_0
91: nop
ifeq 92
iconst_1
goto 93
92: iconst_0
93: nop
89: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_0
ifeq 94
iconst_1
goto 95
94: iconst_0
95: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_0
ifeq 96
iconst_1
goto 97
96: iconst_0
ifeq 98
iconst_1
goto 99
98: iconst_0
99: nop
ifeq 100
iconst_1
goto 101
100: iconst_0
101: nop
97: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
aload_0
aload_0
getfield A/j Z
ifeq 102
iconst_1
goto 103
102: iconst_1
103: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
aload_0
aload_0
getfield A/j Z
ifeq 104
iconst_1
goto 105
104: iconst_0
105: nop
ifeq 106
iconst_1
goto 107
106: iconst_1
107: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
aload_0
aload_0
getfield A/j Z
ifeq 108
iconst_1
goto 109
108: iconst_0
109: nop
ifeq 110
iconst_1
goto 111
110: iconst_0
111: nop
ifeq 112
iconst_1
goto 113
112: iconst_0
113: nop
ifeq 114
iconst_1
goto 115
114: iconst_1
115: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
aload_0
aload_0
getfield A/j Z
ifeq 116
iconst_1
goto 117
116: iconst_0
117: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
iconst_1
ifeq 118
iconst_1
goto 119
118: iconst_0
119: nop
ifeq 120
iconst_1
goto 121
120: iconst_0
121: nop
ifeq 122
iconst_1
goto 123
122: iconst_0
123: nop
istore 5
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 5
invokevirtual java/io/PrintStream/println(Z)V
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "--------------------------------"
invokevirtual java/io/PrintStream/println([[Ljava/lang/String)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 6
aload_0
aload_0
getfield A/r [I
if_acmpeq 124
iconst_0
goto 125
124: iconst_1
125: nop
invokevirtual java/io/PrintStream/println(Z)V
aload_0
aload_0
aload_0
getfield A/r [I
putfield A/r [I
aload_0
getfield A/r [I
astore 6
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 6
aload_0
aload_0
getfield A/r [I
if_acmpeq 126
iconst_0
goto 127
126: iconst_1
127: nop
invokevirtual java/io/PrintStream/println(Z)V
getstatic java/lang/System/out Ljava/io/PrintStream;
iload_3
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 6
iconst_2
iaload
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_0
aload_0
getfield A/i I
invokevirtual java/io/PrintStream/println(I)V
aload 6
iconst_2
aload_0
aload_0
getfield A/i I
iastore
aload 6
iconst_2
iaload
istore_3
getstatic java/lang/System/out Ljava/io/PrintStream;
iload_3
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_0
aload_0
getfield A/i I
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 6
iconst_2
iaload
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "--------------------------------"
invokevirtual java/io/PrintStream/println([[Ljava/lang/String)V
getstatic java/lang/System/out Ljava/io/PrintStream;
iconst_1
iconst_2
iadd
iconst_4
iconst_2
idiv
iadd
iconst_3
isub
bipush 6
iconst_1
isub
bipush 10
imul
iconst_3
irem
iadd
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
iconst_2
ineg
iconst_3
irem
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "--------------------------------"
invokevirtual java/io/PrintStream/println([[Ljava/lang/String)V
iconst_0
istore_3
128: iload_3
bipush 12
if_icmpne 131
iconst_0
goto 132
131: iconst_1
132: nop
ifeq 130
iload_3
iconst_4
if_icmpne 133
iconst_0
goto 134
133: iconst_1
134: nop
ifeq 135
goto 129
goto 136
135: nop
136: nop
iconst_0
istore 4
137: iload 4
bipush 12
if_icmplt 140
iconst_0
goto 141
140: iconst_1
141: nop
ifeq 139
iload 4
bipush 7
irem
bipush 6
if_icmpeq 142
iconst_0
goto 143
142: iconst_1
143: nop
ifeq 144
getstatic java/lang/System/out Ljava/io/PrintStream;
iload_3
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 4
invokevirtual java/io/PrintStream/println(I)V
goto 139
goto 145
144: nop
145: nop
138: iload 4
iconst_1
iadd
istore 4
goto 137
139: nop
129: iload_3
iconst_2
iadd
istore_3
goto 128
130: nop
return
.end method
