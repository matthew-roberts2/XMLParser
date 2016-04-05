package xml;

import util.FileInput;
import xml.states.IXMLState;
import xml.states.OutsideTagState;

import java.util.Stack;

public class XMLCheck {
    private FileInput input;
    private int errorLine;
    private Stack<String> tagStack;
    private IXMLState state;

    public XMLCheck(FileInput input){
        this.input = input;
        state = new OutsideTagState();
        errorLine = 0;
    }

    public boolean doCheck(){
        while(!input.isDone()){
            errorLine++;
            String checked = input.readLine();
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
                }
            }
        }
        return true;
    }

    public int getErrorLine(){
        return errorLine;
    }
}
