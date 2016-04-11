package xml.states;

import xml.InvalidTagException;
import xml.XMLCheck;

/**
 * State that handles reading the names of only closing tags.
 */

public class CloseTagState implements IXMLState {

    private XMLCheck checker;
    private String name;

    public CloseTagState(XMLCheck checker){
        this.checker=checker;
        name = "";
    }

    @Override
    public void forwardSlashDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("(CTS)Duplicate / found in closing tag");
    }

    @Override
    public void lessThanDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("(CTS)Opening tag in closing tag");
    }

    @Override
    public void greaterThanDetected(int lineNumber) throws InvalidTagException {
        if(!name.equals(checker.popFromStack())){
            throw new InvalidTagException("(CTS)Mismatched opening and closing tags");
        }
        checker.setState(new OutsideTagState(checker));
    }

    @Override
    public void whiteSpaceDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("(CTS)Whitespace not allowed in tag names");
    }

    @Override
    public void regularCharacter(int lineNumber, char ch) throws InvalidTagException {
        name+=ch;
    }
}
