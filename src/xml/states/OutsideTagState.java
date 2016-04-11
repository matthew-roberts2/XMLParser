package xml.states;

import xml.InvalidTagException;
import xml.XMLCheck;

/**
 * State for handling initial parsing (before the first tag) as well as space after closing tags
 */

public class OutsideTagState implements IXMLState{

    private XMLCheck checker;

    public OutsideTagState(XMLCheck checker){
        this.checker = checker;
    }

    @Override
    public void forwardSlashDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("(OTS)There shouldn't be a / yet");
    }

    @Override
    public void lessThanDetected(int lineNumber) throws InvalidTagException {
        checker.setState(new DetectTagState(checker));
    }

    @Override
    public void greaterThanDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("(OTS)Tag never started");
    }

    @Override
    public void whiteSpaceDetected(int lineNumber) throws InvalidTagException {
        //NO-OP Just need to ignore whitespace
    }

    @Override
    public void regularCharacter(int lineNumber, char ch) throws InvalidTagException {
        throw new InvalidTagException("(OTS)Invalid char " + ch + " outside of tags");
    }
}
