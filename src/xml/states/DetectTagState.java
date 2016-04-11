package xml.states;

import xml.InvalidTagException;
import xml.XMLCheck;

/**
 * State that handles determining whether a tag is an opening tag or closing tag, if the previous state was {@link OutsideTagState}.
 */

public class DetectTagState implements IXMLState{

    private XMLCheck checker;

    public DetectTagState(XMLCheck checker){
        this.checker = checker;
    }

    @Override
    public void forwardSlashDetected(int lineNumber) throws InvalidTagException {
        checker.setState(new CloseTagState(checker));
    }

    @Override
    public void lessThanDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("(DTS)Opening another tag inside a tag");
    }

    @Override
    public void greaterThanDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("(DTS)No tag name specified");
    }

    @Override
    public void whiteSpaceDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("(DTS)Whitespace cannot exist at the beginning of a tag");
    }

    @Override
    public void regularCharacter(int lineNumber, char ch) throws InvalidTagException {
        checker.setState(new NameTagState(checker, ch));
    }
}
