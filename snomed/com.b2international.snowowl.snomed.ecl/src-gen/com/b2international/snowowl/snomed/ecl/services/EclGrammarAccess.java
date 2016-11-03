/*
 * generated by Xtext
 */
package com.b2international.snowowl.snomed.ecl.services;

import com.google.inject.Singleton;
import com.google.inject.Inject;

import java.util.List;

import org.eclipse.xtext.*;
import org.eclipse.xtext.service.GrammarProvider;
import org.eclipse.xtext.service.AbstractElementFinder.*;


@Singleton
public class EclGrammarAccess extends AbstractGrammarElementFinder {
	
	
	public class ExpressionConstraintElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "ExpressionConstraint");
		private final Assignment cExpressionAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cExpressionFocusConceptParserRuleCall_0 = (RuleCall)cExpressionAssignment.eContents().get(0);
		
		//ExpressionConstraint hidden(WS, SL_COMMENT, ML_COMMENT):
		//	expression=FocusConcept;
		@Override public ParserRule getRule() { return rule; }

		//expression=FocusConcept
		public Assignment getExpressionAssignment() { return cExpressionAssignment; }

		//FocusConcept
		public RuleCall getExpressionFocusConceptParserRuleCall_0() { return cExpressionFocusConceptParserRuleCall_0; }
	}

	public class FocusConceptElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "FocusConcept");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cMemberOfParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cConceptReferenceParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cAnyParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		
		//FocusConcept:
		//	MemberOf | ConceptReference | Any;
		@Override public ParserRule getRule() { return rule; }

		//MemberOf | ConceptReference | Any
		public Alternatives getAlternatives() { return cAlternatives; }

		//MemberOf
		public RuleCall getMemberOfParserRuleCall_0() { return cMemberOfParserRuleCall_0; }

		//ConceptReference
		public RuleCall getConceptReferenceParserRuleCall_1() { return cConceptReferenceParserRuleCall_1; }

		//Any
		public RuleCall getAnyParserRuleCall_2() { return cAnyParserRuleCall_2; }
	}

	public class MemberOfElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "MemberOf");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cCARETTerminalRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Assignment cConceptAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Alternatives cConceptAlternatives_1_0 = (Alternatives)cConceptAssignment_1.eContents().get(0);
		private final RuleCall cConceptConceptReferenceParserRuleCall_1_0_0 = (RuleCall)cConceptAlternatives_1_0.eContents().get(0);
		private final RuleCall cConceptAnyParserRuleCall_1_0_1 = (RuleCall)cConceptAlternatives_1_0.eContents().get(1);
		
		//MemberOf:
		//	CARET concept=(ConceptReference | Any);
		@Override public ParserRule getRule() { return rule; }

		//CARET concept=(ConceptReference | Any)
		public Group getGroup() { return cGroup; }

		//CARET
		public RuleCall getCARETTerminalRuleCall_0() { return cCARETTerminalRuleCall_0; }

		//concept=(ConceptReference | Any)
		public Assignment getConceptAssignment_1() { return cConceptAssignment_1; }

		//ConceptReference | Any
		public Alternatives getConceptAlternatives_1_0() { return cConceptAlternatives_1_0; }

		//ConceptReference
		public RuleCall getConceptConceptReferenceParserRuleCall_1_0_0() { return cConceptConceptReferenceParserRuleCall_1_0_0; }

		//Any
		public RuleCall getConceptAnyParserRuleCall_1_0_1() { return cConceptAnyParserRuleCall_1_0_1; }
	}

	public class ConceptReferenceElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "ConceptReference");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cIdAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cIdSnomedIdentifierParserRuleCall_0_0 = (RuleCall)cIdAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final RuleCall cPIPETerminalRuleCall_1_0 = (RuleCall)cGroup_1.eContents().get(0);
		private final Assignment cTermAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cTermTermParserRuleCall_1_1_0 = (RuleCall)cTermAssignment_1_1.eContents().get(0);
		private final RuleCall cPIPETerminalRuleCall_1_2 = (RuleCall)cGroup_1.eContents().get(2);
		
		//ConceptReference:
		//	id=SnomedIdentifier (PIPE term=Term PIPE)?;
		@Override public ParserRule getRule() { return rule; }

		//id=SnomedIdentifier (PIPE term=Term PIPE)?
		public Group getGroup() { return cGroup; }

		//id=SnomedIdentifier
		public Assignment getIdAssignment_0() { return cIdAssignment_0; }

		//SnomedIdentifier
		public RuleCall getIdSnomedIdentifierParserRuleCall_0_0() { return cIdSnomedIdentifierParserRuleCall_0_0; }

		//(PIPE term=Term PIPE)?
		public Group getGroup_1() { return cGroup_1; }

		//PIPE
		public RuleCall getPIPETerminalRuleCall_1_0() { return cPIPETerminalRuleCall_1_0; }

		//term=Term
		public Assignment getTermAssignment_1_1() { return cTermAssignment_1_1; }

		//Term
		public RuleCall getTermTermParserRuleCall_1_1_0() { return cTermTermParserRuleCall_1_1_0; }

		//PIPE
		public RuleCall getPIPETerminalRuleCall_1_2() { return cPIPETerminalRuleCall_1_2; }
	}

	public class AnyElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Any");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cWILDCARDTerminalRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Action cAnyAction_1 = (Action)cGroup.eContents().get(1);
		
		//Any:
		//	WILDCARD {Any};
		@Override public ParserRule getRule() { return rule; }

		//WILDCARD {Any}
		public Group getGroup() { return cGroup; }

		//WILDCARD
		public RuleCall getWILDCARDTerminalRuleCall_0() { return cWILDCARDTerminalRuleCall_0; }

		//{Any}
		public Action getAnyAction_1() { return cAnyAction_1; }
	}

	public class SnomedIdentifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "SnomedIdentifier");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cDIGIT_NONZEROTerminalRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final RuleCall cDIGIT_NONZEROTerminalRuleCall_1_0 = (RuleCall)cAlternatives_1.eContents().get(0);
		private final RuleCall cZEROTerminalRuleCall_1_1 = (RuleCall)cAlternatives_1.eContents().get(1);
		private final Alternatives cAlternatives_2 = (Alternatives)cGroup.eContents().get(2);
		private final RuleCall cDIGIT_NONZEROTerminalRuleCall_2_0 = (RuleCall)cAlternatives_2.eContents().get(0);
		private final RuleCall cZEROTerminalRuleCall_2_1 = (RuleCall)cAlternatives_2.eContents().get(1);
		private final Alternatives cAlternatives_3 = (Alternatives)cGroup.eContents().get(3);
		private final RuleCall cDIGIT_NONZEROTerminalRuleCall_3_0 = (RuleCall)cAlternatives_3.eContents().get(0);
		private final RuleCall cZEROTerminalRuleCall_3_1 = (RuleCall)cAlternatives_3.eContents().get(1);
		private final Alternatives cAlternatives_4 = (Alternatives)cGroup.eContents().get(4);
		private final RuleCall cDIGIT_NONZEROTerminalRuleCall_4_0 = (RuleCall)cAlternatives_4.eContents().get(0);
		private final RuleCall cZEROTerminalRuleCall_4_1 = (RuleCall)cAlternatives_4.eContents().get(1);
		private final Alternatives cAlternatives_5 = (Alternatives)cGroup.eContents().get(5);
		private final RuleCall cDIGIT_NONZEROTerminalRuleCall_5_0 = (RuleCall)cAlternatives_5.eContents().get(0);
		private final RuleCall cZEROTerminalRuleCall_5_1 = (RuleCall)cAlternatives_5.eContents().get(1);
		
		//// hidden grammar rules
		// SnomedIdentifier hidden():
		//	DIGIT_NONZERO (DIGIT_NONZERO | ZERO) (DIGIT_NONZERO | ZERO) (DIGIT_NONZERO | ZERO) (DIGIT_NONZERO | ZERO)
		//	(DIGIT_NONZERO | ZERO)+;
		@Override public ParserRule getRule() { return rule; }

		//DIGIT_NONZERO (DIGIT_NONZERO | ZERO) (DIGIT_NONZERO | ZERO) (DIGIT_NONZERO | ZERO) (DIGIT_NONZERO | ZERO) (DIGIT_NONZERO
		//| ZERO)+
		public Group getGroup() { return cGroup; }

		//DIGIT_NONZERO
		public RuleCall getDIGIT_NONZEROTerminalRuleCall_0() { return cDIGIT_NONZEROTerminalRuleCall_0; }

		//DIGIT_NONZERO | ZERO
		public Alternatives getAlternatives_1() { return cAlternatives_1; }

		//DIGIT_NONZERO
		public RuleCall getDIGIT_NONZEROTerminalRuleCall_1_0() { return cDIGIT_NONZEROTerminalRuleCall_1_0; }

		//ZERO
		public RuleCall getZEROTerminalRuleCall_1_1() { return cZEROTerminalRuleCall_1_1; }

		//DIGIT_NONZERO | ZERO
		public Alternatives getAlternatives_2() { return cAlternatives_2; }

		//DIGIT_NONZERO
		public RuleCall getDIGIT_NONZEROTerminalRuleCall_2_0() { return cDIGIT_NONZEROTerminalRuleCall_2_0; }

		//ZERO
		public RuleCall getZEROTerminalRuleCall_2_1() { return cZEROTerminalRuleCall_2_1; }

		//DIGIT_NONZERO | ZERO
		public Alternatives getAlternatives_3() { return cAlternatives_3; }

		//DIGIT_NONZERO
		public RuleCall getDIGIT_NONZEROTerminalRuleCall_3_0() { return cDIGIT_NONZEROTerminalRuleCall_3_0; }

		//ZERO
		public RuleCall getZEROTerminalRuleCall_3_1() { return cZEROTerminalRuleCall_3_1; }

		//DIGIT_NONZERO | ZERO
		public Alternatives getAlternatives_4() { return cAlternatives_4; }

		//DIGIT_NONZERO
		public RuleCall getDIGIT_NONZEROTerminalRuleCall_4_0() { return cDIGIT_NONZEROTerminalRuleCall_4_0; }

		//ZERO
		public RuleCall getZEROTerminalRuleCall_4_1() { return cZEROTerminalRuleCall_4_1; }

		//(DIGIT_NONZERO | ZERO)+
		public Alternatives getAlternatives_5() { return cAlternatives_5; }

		//DIGIT_NONZERO
		public RuleCall getDIGIT_NONZEROTerminalRuleCall_5_0() { return cDIGIT_NONZEROTerminalRuleCall_5_0; }

		//ZERO
		public RuleCall getZEROTerminalRuleCall_5_1() { return cZEROTerminalRuleCall_5_1; }
	}

	public class TermElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Term");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cTermCharacterParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final RuleCall cWSTerminalRuleCall_1_0 = (RuleCall)cGroup_1.eContents().get(0);
		private final RuleCall cTermCharacterParserRuleCall_1_1 = (RuleCall)cGroup_1.eContents().get(1);
		
		//Term hidden():
		//	TermCharacter+ (WS+ TermCharacter+)*;
		@Override public ParserRule getRule() { return rule; }

		//TermCharacter+ (WS+ TermCharacter+)*
		public Group getGroup() { return cGroup; }

		//TermCharacter+
		public RuleCall getTermCharacterParserRuleCall_0() { return cTermCharacterParserRuleCall_0; }

		//(WS+ TermCharacter+)*
		public Group getGroup_1() { return cGroup_1; }

		//WS+
		public RuleCall getWSTerminalRuleCall_1_0() { return cWSTerminalRuleCall_1_0; }

		//TermCharacter+
		public RuleCall getTermCharacterParserRuleCall_1_1() { return cTermCharacterParserRuleCall_1_1; }
	}

	public class TermCharacterElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "TermCharacter");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cDESCENDANT_OFTerminalRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cDESCENDANT_OR_SELF_OFTerminalRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cANDTerminalRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cORTerminalRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cNOTTerminalRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		private final RuleCall cZEROTerminalRuleCall_5 = (RuleCall)cAlternatives.eContents().get(5);
		private final RuleCall cDIGIT_NONZEROTerminalRuleCall_6 = (RuleCall)cAlternatives.eContents().get(6);
		private final RuleCall cLETTERTerminalRuleCall_7 = (RuleCall)cAlternatives.eContents().get(7);
		private final RuleCall cCARETTerminalRuleCall_8 = (RuleCall)cAlternatives.eContents().get(8);
		private final RuleCall cEQUALTerminalRuleCall_9 = (RuleCall)cAlternatives.eContents().get(9);
		private final RuleCall cPLUSTerminalRuleCall_10 = (RuleCall)cAlternatives.eContents().get(10);
		private final RuleCall cCURLY_OPENTerminalRuleCall_11 = (RuleCall)cAlternatives.eContents().get(11);
		private final RuleCall cCURLY_CLOSETerminalRuleCall_12 = (RuleCall)cAlternatives.eContents().get(12);
		private final RuleCall cROUND_OPENTerminalRuleCall_13 = (RuleCall)cAlternatives.eContents().get(13);
		private final RuleCall cROUND_CLOSETerminalRuleCall_14 = (RuleCall)cAlternatives.eContents().get(14);
		private final RuleCall cSQUARE_OPENTerminalRuleCall_15 = (RuleCall)cAlternatives.eContents().get(15);
		private final RuleCall cSQUARE_CLOSETerminalRuleCall_16 = (RuleCall)cAlternatives.eContents().get(16);
		private final RuleCall cDOTTerminalRuleCall_17 = (RuleCall)cAlternatives.eContents().get(17);
		private final RuleCall cCOLONTerminalRuleCall_18 = (RuleCall)cAlternatives.eContents().get(18);
		private final RuleCall cCOMMATerminalRuleCall_19 = (RuleCall)cAlternatives.eContents().get(19);
		private final RuleCall cOTHER_CHARACTERTerminalRuleCall_20 = (RuleCall)cAlternatives.eContents().get(20);
		
		//TermCharacter hidden():
		//	DESCENDANT_OF | DESCENDANT_OR_SELF_OF | AND | OR | NOT | ZERO | DIGIT_NONZERO | LETTER | CARET | EQUAL | PLUS |
		//	CURLY_OPEN | CURLY_CLOSE | ROUND_OPEN | ROUND_CLOSE | SQUARE_OPEN | SQUARE_CLOSE | DOT | COLON | COMMA |
		//	OTHER_CHARACTER;
		@Override public ParserRule getRule() { return rule; }

		//DESCENDANT_OF | DESCENDANT_OR_SELF_OF | AND | OR | NOT | ZERO | DIGIT_NONZERO | LETTER | CARET | EQUAL | PLUS |
		//CURLY_OPEN | CURLY_CLOSE | ROUND_OPEN | ROUND_CLOSE | SQUARE_OPEN | SQUARE_CLOSE | DOT | COLON | COMMA |
		//OTHER_CHARACTER
		public Alternatives getAlternatives() { return cAlternatives; }

		//DESCENDANT_OF
		public RuleCall getDESCENDANT_OFTerminalRuleCall_0() { return cDESCENDANT_OFTerminalRuleCall_0; }

		//DESCENDANT_OR_SELF_OF
		public RuleCall getDESCENDANT_OR_SELF_OFTerminalRuleCall_1() { return cDESCENDANT_OR_SELF_OFTerminalRuleCall_1; }

		//AND
		public RuleCall getANDTerminalRuleCall_2() { return cANDTerminalRuleCall_2; }

		//OR
		public RuleCall getORTerminalRuleCall_3() { return cORTerminalRuleCall_3; }

		//NOT
		public RuleCall getNOTTerminalRuleCall_4() { return cNOTTerminalRuleCall_4; }

		//ZERO
		public RuleCall getZEROTerminalRuleCall_5() { return cZEROTerminalRuleCall_5; }

		//DIGIT_NONZERO
		public RuleCall getDIGIT_NONZEROTerminalRuleCall_6() { return cDIGIT_NONZEROTerminalRuleCall_6; }

		//LETTER
		public RuleCall getLETTERTerminalRuleCall_7() { return cLETTERTerminalRuleCall_7; }

		//CARET
		public RuleCall getCARETTerminalRuleCall_8() { return cCARETTerminalRuleCall_8; }

		//EQUAL
		public RuleCall getEQUALTerminalRuleCall_9() { return cEQUALTerminalRuleCall_9; }

		//PLUS
		public RuleCall getPLUSTerminalRuleCall_10() { return cPLUSTerminalRuleCall_10; }

		//CURLY_OPEN
		public RuleCall getCURLY_OPENTerminalRuleCall_11() { return cCURLY_OPENTerminalRuleCall_11; }

		//CURLY_CLOSE
		public RuleCall getCURLY_CLOSETerminalRuleCall_12() { return cCURLY_CLOSETerminalRuleCall_12; }

		//ROUND_OPEN
		public RuleCall getROUND_OPENTerminalRuleCall_13() { return cROUND_OPENTerminalRuleCall_13; }

		//ROUND_CLOSE
		public RuleCall getROUND_CLOSETerminalRuleCall_14() { return cROUND_CLOSETerminalRuleCall_14; }

		//SQUARE_OPEN
		public RuleCall getSQUARE_OPENTerminalRuleCall_15() { return cSQUARE_OPENTerminalRuleCall_15; }

		//SQUARE_CLOSE
		public RuleCall getSQUARE_CLOSETerminalRuleCall_16() { return cSQUARE_CLOSETerminalRuleCall_16; }

		//DOT
		public RuleCall getDOTTerminalRuleCall_17() { return cDOTTerminalRuleCall_17; }

		//COLON
		public RuleCall getCOLONTerminalRuleCall_18() { return cCOLONTerminalRuleCall_18; }

		//COMMA
		public RuleCall getCOMMATerminalRuleCall_19() { return cCOMMATerminalRuleCall_19; }

		//OTHER_CHARACTER
		public RuleCall getOTHER_CHARACTERTerminalRuleCall_20() { return cOTHER_CHARACTERTerminalRuleCall_20; }
	}
	
	
	private final ExpressionConstraintElements pExpressionConstraint;
	private final FocusConceptElements pFocusConcept;
	private final MemberOfElements pMemberOf;
	private final ConceptReferenceElements pConceptReference;
	private final AnyElements pAny;
	private final SnomedIdentifierElements pSnomedIdentifier;
	private final TermElements pTerm;
	private final TermCharacterElements pTermCharacter;
	private final TerminalRule tDESCENDANT_OF;
	private final TerminalRule tDESCENDANT_OR_SELF_OF;
	private final TerminalRule tAND;
	private final TerminalRule tOR;
	private final TerminalRule tZERO;
	private final TerminalRule tDIGIT_NONZERO;
	private final TerminalRule tLETTER;
	private final TerminalRule tPIPE;
	private final TerminalRule tCOLON;
	private final TerminalRule tCURLY_OPEN;
	private final TerminalRule tCURLY_CLOSE;
	private final TerminalRule tEQUAL;
	private final TerminalRule tCOMMA;
	private final TerminalRule tROUND_OPEN;
	private final TerminalRule tROUND_CLOSE;
	private final TerminalRule tSQUARE_OPEN;
	private final TerminalRule tSQUARE_CLOSE;
	private final TerminalRule tPLUS;
	private final TerminalRule tCARET;
	private final TerminalRule tNOT;
	private final TerminalRule tDOT;
	private final TerminalRule tWILDCARD;
	private final TerminalRule tWS;
	private final TerminalRule tML_COMMENT;
	private final TerminalRule tSL_COMMENT;
	private final TerminalRule tOTHER_CHARACTER;
	
	private final Grammar grammar;

	@Inject
	public EclGrammarAccess(GrammarProvider grammarProvider) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.pExpressionConstraint = new ExpressionConstraintElements();
		this.pFocusConcept = new FocusConceptElements();
		this.pMemberOf = new MemberOfElements();
		this.pConceptReference = new ConceptReferenceElements();
		this.pAny = new AnyElements();
		this.pSnomedIdentifier = new SnomedIdentifierElements();
		this.pTerm = new TermElements();
		this.pTermCharacter = new TermCharacterElements();
		this.tDESCENDANT_OF = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "DESCENDANT_OF");
		this.tDESCENDANT_OR_SELF_OF = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "DESCENDANT_OR_SELF_OF");
		this.tAND = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "AND");
		this.tOR = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "OR");
		this.tZERO = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "ZERO");
		this.tDIGIT_NONZERO = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "DIGIT_NONZERO");
		this.tLETTER = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "LETTER");
		this.tPIPE = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "PIPE");
		this.tCOLON = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "COLON");
		this.tCURLY_OPEN = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "CURLY_OPEN");
		this.tCURLY_CLOSE = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "CURLY_CLOSE");
		this.tEQUAL = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "EQUAL");
		this.tCOMMA = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "COMMA");
		this.tROUND_OPEN = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "ROUND_OPEN");
		this.tROUND_CLOSE = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "ROUND_CLOSE");
		this.tSQUARE_OPEN = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "SQUARE_OPEN");
		this.tSQUARE_CLOSE = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "SQUARE_CLOSE");
		this.tPLUS = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "PLUS");
		this.tCARET = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "CARET");
		this.tNOT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "NOT");
		this.tDOT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "DOT");
		this.tWILDCARD = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "WILDCARD");
		this.tWS = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "WS");
		this.tML_COMMENT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "ML_COMMENT");
		this.tSL_COMMENT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "SL_COMMENT");
		this.tOTHER_CHARACTER = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "OTHER_CHARACTER");
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("com.b2international.snowowl.snomed.ecl.Ecl".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	@Override
	public Grammar getGrammar() {
		return grammar;
	}
	

	
	//ExpressionConstraint hidden(WS, SL_COMMENT, ML_COMMENT):
	//	expression=FocusConcept;
	public ExpressionConstraintElements getExpressionConstraintAccess() {
		return pExpressionConstraint;
	}
	
	public ParserRule getExpressionConstraintRule() {
		return getExpressionConstraintAccess().getRule();
	}

	//FocusConcept:
	//	MemberOf | ConceptReference | Any;
	public FocusConceptElements getFocusConceptAccess() {
		return pFocusConcept;
	}
	
	public ParserRule getFocusConceptRule() {
		return getFocusConceptAccess().getRule();
	}

	//MemberOf:
	//	CARET concept=(ConceptReference | Any);
	public MemberOfElements getMemberOfAccess() {
		return pMemberOf;
	}
	
	public ParserRule getMemberOfRule() {
		return getMemberOfAccess().getRule();
	}

	//ConceptReference:
	//	id=SnomedIdentifier (PIPE term=Term PIPE)?;
	public ConceptReferenceElements getConceptReferenceAccess() {
		return pConceptReference;
	}
	
	public ParserRule getConceptReferenceRule() {
		return getConceptReferenceAccess().getRule();
	}

	//Any:
	//	WILDCARD {Any};
	public AnyElements getAnyAccess() {
		return pAny;
	}
	
	public ParserRule getAnyRule() {
		return getAnyAccess().getRule();
	}

	//// hidden grammar rules
	// SnomedIdentifier hidden():
	//	DIGIT_NONZERO (DIGIT_NONZERO | ZERO) (DIGIT_NONZERO | ZERO) (DIGIT_NONZERO | ZERO) (DIGIT_NONZERO | ZERO)
	//	(DIGIT_NONZERO | ZERO)+;
	public SnomedIdentifierElements getSnomedIdentifierAccess() {
		return pSnomedIdentifier;
	}
	
	public ParserRule getSnomedIdentifierRule() {
		return getSnomedIdentifierAccess().getRule();
	}

	//Term hidden():
	//	TermCharacter+ (WS+ TermCharacter+)*;
	public TermElements getTermAccess() {
		return pTerm;
	}
	
	public ParserRule getTermRule() {
		return getTermAccess().getRule();
	}

	//TermCharacter hidden():
	//	DESCENDANT_OF | DESCENDANT_OR_SELF_OF | AND | OR | NOT | ZERO | DIGIT_NONZERO | LETTER | CARET | EQUAL | PLUS |
	//	CURLY_OPEN | CURLY_CLOSE | ROUND_OPEN | ROUND_CLOSE | SQUARE_OPEN | SQUARE_CLOSE | DOT | COLON | COMMA |
	//	OTHER_CHARACTER;
	public TermCharacterElements getTermCharacterAccess() {
		return pTermCharacter;
	}
	
	public ParserRule getTermCharacterRule() {
		return getTermCharacterAccess().getRule();
	}

	//// ---TERMINALS---
	// // ECL terminals
	// terminal DESCENDANT_OF:
	//	"<";
	public TerminalRule getDESCENDANT_OFRule() {
		return tDESCENDANT_OF;
	} 

	//terminal DESCENDANT_OR_SELF_OF:
	//	"<<";
	public TerminalRule getDESCENDANT_OR_SELF_OFRule() {
		return tDESCENDANT_OR_SELF_OF;
	} 

	//// bool operators
	// terminal AND:
	//	"AND";
	public TerminalRule getANDRule() {
		return tAND;
	} 

	//terminal OR:
	//	"OR";
	public TerminalRule getORRule() {
		return tOR;
	} 

	//// numeric terminals
	// terminal ZERO:
	//	"0";
	public TerminalRule getZERORule() {
		return tZERO;
	} 

	//terminal DIGIT_NONZERO:
	//	"1".."9";
	public TerminalRule getDIGIT_NONZERORule() {
		return tDIGIT_NONZERO;
	} 

	//// character terminals
	// terminal LETTER:
	//	"a".."z" | "A".."Z";
	public TerminalRule getLETTERRule() {
		return tLETTER;
	} 

	//terminal PIPE:
	//	"|";
	public TerminalRule getPIPERule() {
		return tPIPE;
	} 

	//terminal COLON:
	//	":";
	public TerminalRule getCOLONRule() {
		return tCOLON;
	} 

	//terminal CURLY_OPEN:
	//	"{";
	public TerminalRule getCURLY_OPENRule() {
		return tCURLY_OPEN;
	} 

	//terminal CURLY_CLOSE:
	//	"}";
	public TerminalRule getCURLY_CLOSERule() {
		return tCURLY_CLOSE;
	} 

	//terminal EQUAL:
	//	"=";
	public TerminalRule getEQUALRule() {
		return tEQUAL;
	} 

	//terminal COMMA:
	//	",";
	public TerminalRule getCOMMARule() {
		return tCOMMA;
	} 

	//terminal ROUND_OPEN:
	//	"(";
	public TerminalRule getROUND_OPENRule() {
		return tROUND_OPEN;
	} 

	//terminal ROUND_CLOSE:
	//	")";
	public TerminalRule getROUND_CLOSERule() {
		return tROUND_CLOSE;
	} 

	//terminal SQUARE_OPEN:
	//	"[";
	public TerminalRule getSQUARE_OPENRule() {
		return tSQUARE_OPEN;
	} 

	//terminal SQUARE_CLOSE:
	//	"]";
	public TerminalRule getSQUARE_CLOSERule() {
		return tSQUARE_CLOSE;
	} 

	//terminal PLUS:
	//	"+";
	public TerminalRule getPLUSRule() {
		return tPLUS;
	} 

	//terminal CARET:
	//	"^";
	public TerminalRule getCARETRule() {
		return tCARET;
	} 

	//terminal NOT:
	//	"!";
	public TerminalRule getNOTRule() {
		return tNOT;
	} 

	//terminal DOT:
	//	".";
	public TerminalRule getDOTRule() {
		return tDOT;
	} 

	//terminal WILDCARD:
	//	"*";
	public TerminalRule getWILDCARDRule() {
		return tWILDCARD;
	} 

	//// comment terminals
	// terminal WS:
	//	" " | "\t" | "\n" | "\r";
	public TerminalRule getWSRule() {
		return tWS;
	} 

	//terminal ML_COMMENT:
	//	"/ *"->"* /";
	public TerminalRule getML_COMMENTRule() {
		return tML_COMMENT;
	} 

	//terminal SL_COMMENT:
	//	"//" !("\n" | "\r")* ("\r"? "\n")?;
	public TerminalRule getSL_COMMENTRule() {
		return tSL_COMMENT;
	} 

	//// misc
	// terminal OTHER_CHARACTER:
	//	.;
	public TerminalRule getOTHER_CHARACTERRule() {
		return tOTHER_CHARACTER;
	} 
}
