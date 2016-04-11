package xml.states;


import xml.InvalidTagException;

/**
 * Interface used to build the states required for XML Parsing.
 */
public interface IXMLState
{
    /**
     * Method for what to do if forward slash character '/' is found in the XML Document.
     * @param lineNumber The number of the line currently being read
     * @throws InvalidTagException Thrown if a forward slash character should not be found where it is
     */
    void forwardSlashDetected(int lineNumber) throws InvalidTagException;
    /**
     * Method for what to do if less than character '<' is found in the XML Document.
     * @param lineNumber The number of the line currently being read
     * @throws InvalidTagException Thrown if a less than character should not be found where it is
     */
    void lessThanDetected(int lineNumber) throws InvalidTagException;
    /**
     * Method for what to do if greater than character '>' is found in the XML Document.
     * @param lineNumber The number of the line currently being read
     * @throws InvalidTagException Thrown if a greater than character should not be found where it is
     */
    void greaterThanDetected(int lineNumber) throws InvalidTagException;
    /**
     * Method for what to do if any white space character  (space, tab, new line, ect.) is found in the XML Document.
     * @param lineNumber The number of the line currently being read
     * @throws InvalidTagException Thrown if any white space should not be found where it is
     */
    void whiteSpaceDetected(int lineNumber) throws InvalidTagException;
    /**
     * Method for what to do if any character that not a '>' , '<' , '/' , or white space is found in the XML Document.
     * @param lineNumber The number of the line currently being read
     * @param ch The character that was read
     * @throws InvalidTagException Thrown if any character that not a '>' , '<' , '/' , or white space should not be found where it is
     */
    void regularCharacter(int lineNumber, char ch) throws InvalidTagException;
}