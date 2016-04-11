package xml.states;

import xml.InvalidTagException;
import xml.XMLCheck;

/**
 * State that handles determining whether a tag is an opening tag or closing tag, if the previous state was {@link ReadDataState}.
 */

public class EndReadDataState implements IXMLState{

    private XMLCheck checker;
    private boolean errorFlag;

    public EndReadDataState(XMLCheck checker, boolean prevStateReadChars){
        this.checker = checker;
        errorFlag = prevStateReadChars;
    }

    @Override
    public void forwardSlashDetected(int lineNumber) throws InvalidTagException {
        checker.setState(new CloseTagState(checker));
    }

    @Override
    public void lessThanDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("(ERDS)Attempting to open tag inside another tag");
    }

    @Override
    public void greaterThanDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("(ERDS)No tag name specified");
    }

    @Override
    public void whiteSpaceDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("(ERDS)Whitespace not allowed in tag names");
    }

    @Override
    public void regularCharacter(int lineNumber, char ch) throws InvalidTagException {
        if(errorFlag){
            throw new InvalidTagException("(ERDS)Characters found between two opening tags");
        }
        checker.setState(new NameTagState(checker, ch));
    }
}
