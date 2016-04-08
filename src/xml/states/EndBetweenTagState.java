package xml.states;

import xml.InvalidTagException;

public class EndBetweenTagState implements IXMLState{

    @Override
    public void forwardSlashDetected(int lineNumber) throws InvalidTagException {
        //GOTO ProcessClosingTagState
    }

    @Override
    public void lessThanDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("Attempting to open tag inside another tag");
    }

    @Override
    public void greaterThanDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("No tag name specified");
    }

    @Override
    public void whiteSpaceDetected(int lineNumber) throws InvalidTagException {

    }

    @Override
    public void regularCharacter(int lineNumber, char ch) throws InvalidTagException {

    }
}
