package xml;

import util.FileInput;
import util.FileOutput;
import xml.states.IXMLState;
import xml.states.OutsideTagState;

import java.util.EmptyStackException;
import java.util.Stack;

public class XMLCheck {
    private FileInput input;
    private int errorLine;
    private Stack<String> tagStack;
    private IXMLState state;
    private XMLPrinter printer;

    public XMLCheck(FileInput input, FileOutput writer){
        this.input = input;
        state = null;
        tagStack = new Stack<String>();
        errorLine = 0;
        printer = new XMLPrinter(writer);
    }

    public boolean doCheck(){
        state = new OutsideTagState(this);
        while(!input.isDone()){
            errorLine++;
            String checked = input.readLine();
            if(checked==null){
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
        printer.printXML();
        return true;
    }

    public int getErrorLine(){
        return errorLine;
    }

    public void setState(IXMLState state){
        this.state = state;
    }

    public IXMLState getState(){
        return state;
    }

    public void pushToStack(String elem){
        printer.addItemToQueue(new XMLTag(elem));
        System.out.println("Pushing " + elem + " to the stack");
        tagStack.push(elem);
    }

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

    public void addDataToQueue(String s){
        XMLData newData = new XMLData(s);
        printer.addItemToQueue(newData);
    }
}
