package xml.states;

import xml.InvalidTagException;

public class ProcessOpenTagState implements IXMLState{

    @Override
    public void forwardSlashDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("\"/\" is an invalid character for tag names");
    }

    @Override
    public void lessThanDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("Starting a new tag within a tag");
    }

    @Override
    public void greaterThanDetected(int lineNumber) throws InvalidTagException {
        //GOTO BetweenTagState
    }

    @Override
    public void whiteSpaceDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("Whitespace in tags is not allowed");
    }

    @Override
    public void regularCharacter(int lineNumber, char ch) throws InvalidTagException {
        //NO-OP Continue building the name of the tag.
    }
}
