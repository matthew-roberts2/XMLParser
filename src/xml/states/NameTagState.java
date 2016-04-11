package xml.states;

import xml.InvalidTagException;
import xml.XMLCheck;

/**
 * State used for reading the characters in only the opening tags
 */

public class NameTagState implements IXMLState{

    private XMLCheck checker;
    private String name;

    public NameTagState(XMLCheck checker, char first){
        this.checker = checker;
        name = ""+first;
    }

    @Override
    public void forwardSlashDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("(NTS)\"/\" is an invalid character for tag names");
    }

    @Override
    public void lessThanDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("(NTS)Starting a new tag within a tag");
    }

    @Override
    public void greaterThanDetected(int lineNumber) throws InvalidTagException {
        checker.pushToStack(name);
        checker.setState(new ReadDataState(checker));
    }

    @Override
    public void whiteSpaceDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("(NTS)Whitespace in tags is not allowed");
    }

    @Override
    public void regularCharacter(int lineNumber, char ch) throws InvalidTagException {
        name+=ch;
    }
}
