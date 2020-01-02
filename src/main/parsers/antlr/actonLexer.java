// Generated from C:/Users/Taha/Desktop/compiler_p4/src/antlr\acton.g4 by ANTLR 4.7.2
package antlr;

    import main.ast.node.*;
    import main.ast.node.declaration.*;
    import main.ast.node.declaration.handler.*;
    import main.ast.node.statement.*;
    import main.ast.node.expression.*;
    import main.ast.node.expression.operators.*;
    import main.ast.node.expression.values.*;
    import main.ast.type.primitiveType.*;
    import main.ast.type.arrayType.*;
    import main.ast.type.actorType.*;
    import main.ast.type.*;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class actonLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INTVAL=1, STRINGVAL=2, TRUE=3, FALSE=4, INT=5, BOOLEAN=6, STRING=7, ACTOR=8, 
		EXTENDS=9, ACTORVARS=10, KNOWNACTORS=11, INITIAL=12, MSGHANDLER=13, SENDER=14, 
		SELF=15, MAIN=16, FOR=17, CONTINUE=18, BREAK=19, IF=20, ELSE=21, PRINT=22, 
		LPAREN=23, RPAREN=24, LBRACE=25, RBRACE=26, LBRACKET=27, RBRACKET=28, 
		COLON=29, SEMICOLON=30, COMMA=31, DOT=32, ASSIGN=33, EQ=34, NEQ=35, GT=36, 
		LT=37, PLUSPLUS=38, MINUSMINUS=39, PLUS=40, MINUS=41, MULT=42, DIV=43, 
		PERCENT=44, NOT=45, AND=46, OR=47, QUES=48, IDENTIFIER=49, COMMENT=50, 
		WHITESPACE=51;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"INTVAL", "STRINGVAL", "TRUE", "FALSE", "INT", "BOOLEAN", "STRING", "ACTOR", 
			"EXTENDS", "ACTORVARS", "KNOWNACTORS", "INITIAL", "MSGHANDLER", "SENDER", 
			"SELF", "MAIN", "FOR", "CONTINUE", "BREAK", "IF", "ELSE", "PRINT", "LPAREN", 
			"RPAREN", "LBRACE", "RBRACE", "LBRACKET", "RBRACKET", "COLON", "SEMICOLON", 
			"COMMA", "DOT", "ASSIGN", "EQ", "NEQ", "GT", "LT", "PLUSPLUS", "MINUSMINUS", 
			"PLUS", "MINUS", "MULT", "DIV", "PERCENT", "NOT", "AND", "OR", "QUES", 
			"IDENTIFIER", "COMMENT", "WHITESPACE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'true'", "'false'", "'int'", "'boolean'", "'string'", 
			"'actor'", "'extends'", "'actorvars'", "'knownactors'", "'initial'", 
			"'msghandler'", "'sender'", "'self'", "'main'", "'for'", "'continue'", 
			"'break'", "'if'", "'else'", "'print'", "'('", "')'", "'{'", "'}'", "'['", 
			"']'", "':'", "';'", "','", "'.'", "'='", "'=='", "'!='", "'>'", "'<'", 
			"'++'", "'--'", "'+'", "'-'", "'*'", "'/'", "'%'", "'!'", "'&&'", "'||'", 
			"'?'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INTVAL", "STRINGVAL", "TRUE", "FALSE", "INT", "BOOLEAN", "STRING", 
			"ACTOR", "EXTENDS", "ACTORVARS", "KNOWNACTORS", "INITIAL", "MSGHANDLER", 
			"SENDER", "SELF", "MAIN", "FOR", "CONTINUE", "BREAK", "IF", "ELSE", "PRINT", 
			"LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACKET", "RBRACKET", "COLON", 
			"SEMICOLON", "COMMA", "DOT", "ASSIGN", "EQ", "NEQ", "GT", "LT", "PLUSPLUS", 
			"MINUSMINUS", "PLUS", "MINUS", "MULT", "DIV", "PERCENT", "NOT", "AND", 
			"OR", "QUES", "IDENTIFIER", "COMMENT", "WHITESPACE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public actonLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "acton.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\65\u0153\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\3\2\3\2\7\2l\n\2\f\2\16\2o\13\2\3\2\5\2r\n\2\3\3\3\3\7\3v\n\3\f"+
		"\3\16\3y\13\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6"+
		"\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3"+
		"\24\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3"+
		"\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3"+
		"\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3#\3$\3$\3$\3%\3%\3&\3&\3\'\3"+
		"\'\3\'\3(\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3/\3\60\3\60"+
		"\3\60\3\61\3\61\3\62\3\62\7\62\u0140\n\62\f\62\16\62\u0143\13\62\3\63"+
		"\3\63\3\63\3\63\7\63\u0149\n\63\f\63\16\63\u014c\13\63\3\63\3\63\3\64"+
		"\3\64\3\64\3\64\2\2\65\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27"+
		"\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33"+
		"\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63"+
		"e\64g\65\3\2\n\3\2\63;\3\2\62;\3\2\62\62\3\2$$\5\2C\\aac|\6\2\62;C\\a"+
		"ac|\4\2\f\f\17\17\5\2\13\f\17\17\"\"\2\u0157\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2"+
		"\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2"+
		"\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2"+
		"\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2"+
		"\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2"+
		"M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3"+
		"\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2"+
		"\2\2g\3\2\2\2\3q\3\2\2\2\5s\3\2\2\2\7|\3\2\2\2\t\u0081\3\2\2\2\13\u0087"+
		"\3\2\2\2\r\u008b\3\2\2\2\17\u0093\3\2\2\2\21\u009a\3\2\2\2\23\u00a0\3"+
		"\2\2\2\25\u00a8\3\2\2\2\27\u00b2\3\2\2\2\31\u00be\3\2\2\2\33\u00c6\3\2"+
		"\2\2\35\u00d1\3\2\2\2\37\u00d8\3\2\2\2!\u00dd\3\2\2\2#\u00e2\3\2\2\2%"+
		"\u00e6\3\2\2\2\'\u00ef\3\2\2\2)\u00f5\3\2\2\2+\u00f8\3\2\2\2-\u00fd\3"+
		"\2\2\2/\u0103\3\2\2\2\61\u0105\3\2\2\2\63\u0107\3\2\2\2\65\u0109\3\2\2"+
		"\2\67\u010b\3\2\2\29\u010d\3\2\2\2;\u010f\3\2\2\2=\u0111\3\2\2\2?\u0113"+
		"\3\2\2\2A\u0115\3\2\2\2C\u0117\3\2\2\2E\u0119\3\2\2\2G\u011c\3\2\2\2I"+
		"\u011f\3\2\2\2K\u0121\3\2\2\2M\u0123\3\2\2\2O\u0126\3\2\2\2Q\u0129\3\2"+
		"\2\2S\u012b\3\2\2\2U\u012d\3\2\2\2W\u012f\3\2\2\2Y\u0131\3\2\2\2[\u0133"+
		"\3\2\2\2]\u0135\3\2\2\2_\u0138\3\2\2\2a\u013b\3\2\2\2c\u013d\3\2\2\2e"+
		"\u0144\3\2\2\2g\u014f\3\2\2\2im\t\2\2\2jl\t\3\2\2kj\3\2\2\2lo\3\2\2\2"+
		"mk\3\2\2\2mn\3\2\2\2nr\3\2\2\2om\3\2\2\2pr\t\4\2\2qi\3\2\2\2qp\3\2\2\2"+
		"r\4\3\2\2\2sw\7$\2\2tv\n\5\2\2ut\3\2\2\2vy\3\2\2\2wu\3\2\2\2wx\3\2\2\2"+
		"xz\3\2\2\2yw\3\2\2\2z{\7$\2\2{\6\3\2\2\2|}\7v\2\2}~\7t\2\2~\177\7w\2\2"+
		"\177\u0080\7g\2\2\u0080\b\3\2\2\2\u0081\u0082\7h\2\2\u0082\u0083\7c\2"+
		"\2\u0083\u0084\7n\2\2\u0084\u0085\7u\2\2\u0085\u0086\7g\2\2\u0086\n\3"+
		"\2\2\2\u0087\u0088\7k\2\2\u0088\u0089\7p\2\2\u0089\u008a\7v\2\2\u008a"+
		"\f\3\2\2\2\u008b\u008c\7d\2\2\u008c\u008d\7q\2\2\u008d\u008e\7q\2\2\u008e"+
		"\u008f\7n\2\2\u008f\u0090\7g\2\2\u0090\u0091\7c\2\2\u0091\u0092\7p\2\2"+
		"\u0092\16\3\2\2\2\u0093\u0094\7u\2\2\u0094\u0095\7v\2\2\u0095\u0096\7"+
		"t\2\2\u0096\u0097\7k\2\2\u0097\u0098\7p\2\2\u0098\u0099\7i\2\2\u0099\20"+
		"\3\2\2\2\u009a\u009b\7c\2\2\u009b\u009c\7e\2\2\u009c\u009d\7v\2\2\u009d"+
		"\u009e\7q\2\2\u009e\u009f\7t\2\2\u009f\22\3\2\2\2\u00a0\u00a1\7g\2\2\u00a1"+
		"\u00a2\7z\2\2\u00a2\u00a3\7v\2\2\u00a3\u00a4\7g\2\2\u00a4\u00a5\7p\2\2"+
		"\u00a5\u00a6\7f\2\2\u00a6\u00a7\7u\2\2\u00a7\24\3\2\2\2\u00a8\u00a9\7"+
		"c\2\2\u00a9\u00aa\7e\2\2\u00aa\u00ab\7v\2\2\u00ab\u00ac\7q\2\2\u00ac\u00ad"+
		"\7t\2\2\u00ad\u00ae\7x\2\2\u00ae\u00af\7c\2\2\u00af\u00b0\7t\2\2\u00b0"+
		"\u00b1\7u\2\2\u00b1\26\3\2\2\2\u00b2\u00b3\7m\2\2\u00b3\u00b4\7p\2\2\u00b4"+
		"\u00b5\7q\2\2\u00b5\u00b6\7y\2\2\u00b6\u00b7\7p\2\2\u00b7\u00b8\7c\2\2"+
		"\u00b8\u00b9\7e\2\2\u00b9\u00ba\7v\2\2\u00ba\u00bb\7q\2\2\u00bb\u00bc"+
		"\7t\2\2\u00bc\u00bd\7u\2\2\u00bd\30\3\2\2\2\u00be\u00bf\7k\2\2\u00bf\u00c0"+
		"\7p\2\2\u00c0\u00c1\7k\2\2\u00c1\u00c2\7v\2\2\u00c2\u00c3\7k\2\2\u00c3"+
		"\u00c4\7c\2\2\u00c4\u00c5\7n\2\2\u00c5\32\3\2\2\2\u00c6\u00c7\7o\2\2\u00c7"+
		"\u00c8\7u\2\2\u00c8\u00c9\7i\2\2\u00c9\u00ca\7j\2\2\u00ca\u00cb\7c\2\2"+
		"\u00cb\u00cc\7p\2\2\u00cc\u00cd\7f\2\2\u00cd\u00ce\7n\2\2\u00ce\u00cf"+
		"\7g\2\2\u00cf\u00d0\7t\2\2\u00d0\34\3\2\2\2\u00d1\u00d2\7u\2\2\u00d2\u00d3"+
		"\7g\2\2\u00d3\u00d4\7p\2\2\u00d4\u00d5\7f\2\2\u00d5\u00d6\7g\2\2\u00d6"+
		"\u00d7\7t\2\2\u00d7\36\3\2\2\2\u00d8\u00d9\7u\2\2\u00d9\u00da\7g\2\2\u00da"+
		"\u00db\7n\2\2\u00db\u00dc\7h\2\2\u00dc \3\2\2\2\u00dd\u00de\7o\2\2\u00de"+
		"\u00df\7c\2\2\u00df\u00e0\7k\2\2\u00e0\u00e1\7p\2\2\u00e1\"\3\2\2\2\u00e2"+
		"\u00e3\7h\2\2\u00e3\u00e4\7q\2\2\u00e4\u00e5\7t\2\2\u00e5$\3\2\2\2\u00e6"+
		"\u00e7\7e\2\2\u00e7\u00e8\7q\2\2\u00e8\u00e9\7p\2\2\u00e9\u00ea\7v\2\2"+
		"\u00ea\u00eb\7k\2\2\u00eb\u00ec\7p\2\2\u00ec\u00ed\7w\2\2\u00ed\u00ee"+
		"\7g\2\2\u00ee&\3\2\2\2\u00ef\u00f0\7d\2\2\u00f0\u00f1\7t\2\2\u00f1\u00f2"+
		"\7g\2\2\u00f2\u00f3\7c\2\2\u00f3\u00f4\7m\2\2\u00f4(\3\2\2\2\u00f5\u00f6"+
		"\7k\2\2\u00f6\u00f7\7h\2\2\u00f7*\3\2\2\2\u00f8\u00f9\7g\2\2\u00f9\u00fa"+
		"\7n\2\2\u00fa\u00fb\7u\2\2\u00fb\u00fc\7g\2\2\u00fc,\3\2\2\2\u00fd\u00fe"+
		"\7r\2\2\u00fe\u00ff\7t\2\2\u00ff\u0100\7k\2\2\u0100\u0101\7p\2\2\u0101"+
		"\u0102\7v\2\2\u0102.\3\2\2\2\u0103\u0104\7*\2\2\u0104\60\3\2\2\2\u0105"+
		"\u0106\7+\2\2\u0106\62\3\2\2\2\u0107\u0108\7}\2\2\u0108\64\3\2\2\2\u0109"+
		"\u010a\7\177\2\2\u010a\66\3\2\2\2\u010b\u010c\7]\2\2\u010c8\3\2\2\2\u010d"+
		"\u010e\7_\2\2\u010e:\3\2\2\2\u010f\u0110\7<\2\2\u0110<\3\2\2\2\u0111\u0112"+
		"\7=\2\2\u0112>\3\2\2\2\u0113\u0114\7.\2\2\u0114@\3\2\2\2\u0115\u0116\7"+
		"\60\2\2\u0116B\3\2\2\2\u0117\u0118\7?\2\2\u0118D\3\2\2\2\u0119\u011a\7"+
		"?\2\2\u011a\u011b\7?\2\2\u011bF\3\2\2\2\u011c\u011d\7#\2\2\u011d\u011e"+
		"\7?\2\2\u011eH\3\2\2\2\u011f\u0120\7@\2\2\u0120J\3\2\2\2\u0121\u0122\7"+
		">\2\2\u0122L\3\2\2\2\u0123\u0124\7-\2\2\u0124\u0125\7-\2\2\u0125N\3\2"+
		"\2\2\u0126\u0127\7/\2\2\u0127\u0128\7/\2\2\u0128P\3\2\2\2\u0129\u012a"+
		"\7-\2\2\u012aR\3\2\2\2\u012b\u012c\7/\2\2\u012cT\3\2\2\2\u012d\u012e\7"+
		",\2\2\u012eV\3\2\2\2\u012f\u0130\7\61\2\2\u0130X\3\2\2\2\u0131\u0132\7"+
		"\'\2\2\u0132Z\3\2\2\2\u0133\u0134\7#\2\2\u0134\\\3\2\2\2\u0135\u0136\7"+
		"(\2\2\u0136\u0137\7(\2\2\u0137^\3\2\2\2\u0138\u0139\7~\2\2\u0139\u013a"+
		"\7~\2\2\u013a`\3\2\2\2\u013b\u013c\7A\2\2\u013cb\3\2\2\2\u013d\u0141\t"+
		"\6\2\2\u013e\u0140\t\7\2\2\u013f\u013e\3\2\2\2\u0140\u0143\3\2\2\2\u0141"+
		"\u013f\3\2\2\2\u0141\u0142\3\2\2\2\u0142d\3\2\2\2\u0143\u0141\3\2\2\2"+
		"\u0144\u0145\7\61\2\2\u0145\u0146\7\61\2\2\u0146\u014a\3\2\2\2\u0147\u0149"+
		"\n\b\2\2\u0148\u0147\3\2\2\2\u0149\u014c\3\2\2\2\u014a\u0148\3\2\2\2\u014a"+
		"\u014b\3\2\2\2\u014b\u014d\3\2\2\2\u014c\u014a\3\2\2\2\u014d\u014e\b\63"+
		"\2\2\u014ef\3\2\2\2\u014f\u0150\t\t\2\2\u0150\u0151\3\2\2\2\u0151\u0152"+
		"\b\64\2\2\u0152h\3\2\2\2\b\2mqw\u0141\u014a\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}