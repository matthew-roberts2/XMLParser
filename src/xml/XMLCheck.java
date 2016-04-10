package xml;

import util.FileInput;
import xml.states.IXMLState;
import xml.states.OutsideTagState;

import java.util.EmptyStackException;
import java.util.Stack;

public class XMLCheck {
    private FileInput input;
    private int errorLine;
    private Stack<String> tagStack;
    private IXMLState state;

    public XMLCheck(FileInput input){
        this.input = input;
        state = null;
        tagStack = new Stack<String>();
        errorLine = 0;
    }

    public boolean doCheck(){
        state = new OutsideTagState(this);
        while(!input.isDone()){
            errorLine++;
            String checked = input.readLine();
            if(checked==null){
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
        System.out.println("Popping " + s + " from the stack");
        return s;
    }
}
