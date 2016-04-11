package xml.states;

import xml.InvalidTagException;
import xml.XMLCheck;

public class ReadDataState implements IXMLState {

    private XMLCheck checker;
    private boolean errorFlag;
    private String string;

    public ReadDataState(XMLCheck checker){
        this.checker = checker;
        errorFlag = false;
        string="";
    }

    @Override
    public void forwardSlashDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("(RDS)Misplaced character \"/\"");
    }

    @Override
    public void lessThanDetected(int lineNumber) throws InvalidTagException {
        checker.addDataToQueue(string);
        checker.setState(new EndReadDataState(checker, errorFlag));
    }

    @Override
    public void greaterThanDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("(RDS)Attempt to close tag without opening it");
    }

    @Override
    public void whiteSpaceDetected(int lineNumber) throws InvalidTagException {
        //NO-OP Whitespace doesnt matter between tags
    }

    @Override
    public void regularCharacter(int lineNumber, char ch) throws InvalidTagException {
        //NO-OP Not worried about constructing the new file yet
        errorFlag = true;
        string+=ch;
    }
}
