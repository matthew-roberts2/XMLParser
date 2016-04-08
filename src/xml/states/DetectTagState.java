package xml.states;

import xml.InvalidTagException;

public class DetectTagState implements IXMLState{

    @Override
    public void forwardSlashDetected(int lineNumber) throws InvalidTagException {
        //GOTO ProcessClosingTagState
    }

    @Override
    public void lessThanDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("Opening another tag inside a tag");
    }

    @Override
    public void greaterThanDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("No tag name specified");
    }

    @Override
    public void whiteSpaceDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("Whitespace cannot exist at the beginning of a tag");
    }

    @Override
    public void regularCharacter(int lineNumber, char ch) throws InvalidTagException {
        //GOTO ProcessOpenTagState
    }
}
