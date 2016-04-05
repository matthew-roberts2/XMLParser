package xml.states;

import xml.InvalidTagException;

public class OutsideTagState implements IXMLState{
    @Override
    public void forwardSlashDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("There shouldn't be a / yet");
    }

    @Override
    public void lessThanDetected(int lineNumber) throws InvalidTagException {

    }

    @Override
    public void greaterThanDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("Tag never started");
    }

    @Override
    public void whiteSpaceDetected(int lineNumber) throws InvalidTagException {
        //NO-OP Just need to ignore whitespace
    }

    @Override
    public void regularCharacter(int lineNumber, char ch) throws InvalidTagException {
        throw new InvalidTagException("Invalid char " + ch + " outside of tags");
    }
}
