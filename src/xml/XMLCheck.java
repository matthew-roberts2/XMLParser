package xml;

import util.FileInput;
import util.FileOutput;
import xml.states.IXMLState;
import xml.states.OutsideTagState;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Class in charge of actually parsing the XML file.
 */

public class XMLCheck {
    private FileInput input;
    private int errorLine;
    private Stack<String> tagStack;
    private IXMLState state;
    private XMLPrinter printer;

    /**
     * Constructor for XMLCheck. Handles some setup for the checker.
     *
     * @param input The FileInput object that refers to the XML file being checked
     * @param output The FileOuput object that refers to the output file to which the XML will be written, if it is well-formed
     */

    public XMLCheck(FileInput input, FileOutput output){
        this.input = input;
        state = null;
        tagStack = new Stack<String>();
        errorLine = 0;
        printer = new XMLPrinter(output);
    }

    /**
     * Method that checks the XML file.
     *
     * @return true, if the file specified is well-formed XML, false otherwise.
     */

    public boolean doCheck(){
        state = new OutsideTagState(this);
        while(!input.isDone()){
            errorLine++;
            String checked = input.readLine();
            if(checked==null){
				if(!tagStack.isEmpty()){
					System.out.println("All tags did not get closed");
					return false;
				}
                input.close();
                printer.printXML();
                return true;
            }
            for(int i=0; i<checked.length(); i++){
                Character next = checked.charAt(i);
                try {
                    if (Character.isWhitespace(next)) {
                        state.whiteSpaceDetected(errorLine);
                    }else if(next.equals('/')){
                        state.forwardSlashDetected(errorLine);
                    }else if(next.equals('<')){
                        state.lessThanDetected(errorLine);
                    }else if(next.equals('>')){
                        state.greaterThanDetected(errorLine);
                    }else{
                        state.regularCharacter(errorLine, next);
                    }
                }catch(InvalidTagException e){
                    System.out.println("Malformed XML. Error on line " + errorLine + " Error Message: " + e.getMessage());
                    return false;
                }
            }
        }
        if(!tagStack.isEmpty()){
            System.out.println("All tags did not get closed");
            return false;
        }
        input.close();
        printer.printXML();
        return true;
    }

    /**
     * Returns the line that the parser is currently working on.
     *
     * @return an int referring to the line of the XML file currently being read.
     */
    public int getErrorLine(){
        return errorLine;
    }

    /**
     * Sets the state of the XML parser.
     *
     * @param state The state that the parser should be set to
     */
    public void setState(IXMLState state){
        this.state = state;
    }

    /**
     * Returns the current state of the parser.
     *
     * @return An IXMLState of the parser.
     */
    public IXMLState getState(){
        return state;
    }

    /**
     * Pushes an element to the tagStack.
     * @param elem The element to be pushed to the stack
     */
    public void pushToStack(String elem){
        printer.addItemToQueue(new XMLTag(elem));
        System.out.println("Pushing " + elem + " to the stack");
        tagStack.push(elem);
    }

    /**
     * Removes an item from the tagStack.
     *
     * @return The element that was removed from the stack
     * @throws InvalidTagException if there are no items left in the stack
     */
    public String popFromStack() throws InvalidTagException{
        String s = "";
        try{
            s = tagStack.pop();
        }catch(EmptyStackException e){
            throw new InvalidTagException("Extra closing tags detected");
        }
        printer.addItemToQueue(new XMLTag("/"+s));
        System.out.println("Popping " + s + " from the stack");
        return s;
    }

    /**
     * Adds data to the printer queue.
     *
     * @param s A string representing the data to be put in the queue.
     */
    public void addDataToQueue(String s){
        XMLData newData = new XMLData(s);
        printer.addItemToQueue(newData);
    }
}
