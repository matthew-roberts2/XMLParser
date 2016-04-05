package xml.states;


import xml.InvalidTagException;

public interface IXMLState
{
    public void forwardSlashDetected(int lineNumber) throws InvalidTagException;
    public void lessThanDetected(int lineNumber) throws InvalidTagException;
    public void greaterThanDetected(int lineNumber) throws InvalidTagException;
    public void whiteSpaceDetected(int lineNumber) throws InvalidTagException;
    public void regularCharacter(int lineNumber, char ch) throws InvalidTagException;
}