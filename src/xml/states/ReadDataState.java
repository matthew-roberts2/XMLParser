package xml.states;

import xml.InvalidTagException;
import xml.XMLCheck;

/**
 * State for reading data from after an opening tag, to before a closing tag.
 */

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
        if(!string.equals("")) checker.addDataToQueue(string);
        checker.setState(new EndReadDataState(checker, errorFlag));
    }

    @Override
    public void greaterThanDetected(int lineNumber) throws InvalidTagException {
        throw new InvalidTagException("(RDS)Attempt to close tag without opening it");
    }

    @Override
    public void whiteSpaceDetected(int lineNumber) throws InvalidTagException {
        if(!string.equals("")) string+=" ";
    }

    @Override
    public void regularCharacter(int lineNumber, char ch) throws InvalidTagException {
        //NO-OP Not worried about constructing the new file yet
        errorFlag = true;
        string+=ch;
    }
}
