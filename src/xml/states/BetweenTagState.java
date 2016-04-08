package xml.states;

import xml.InvalidTagException;

public class BetweenTagState implements IXMLState {
    @Override
    public void forwardSlashDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("Missplaced character \"/\"");
    }

    @Override
    public void lessThanDetected(int lineNumber) throws InvalidTagException {
        //GOTO EndBetweenTagState
    }

    @Override
    public void greaterThanDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("Attempt to close tag without opening it");
    }

    @Override
    public void whiteSpaceDetected(int lineNumber) throws InvalidTagException {
        //NO-OP Whitespace doesnt matter between tags
    }

    @Override
    public void regularCharacter(int lineNumber, char ch) throws InvalidTagException {
        //NO-OP Continue constructing the data
    }
}
