package xml;

import util.FileOutput;
import util.QueueLinked;

import java.io.FileWriter;

public class XMLPrinter {
    private QueueLinked<XMLObject> things;
    private FileOutput writer;

    public XMLPrinter(FileOutput writer){
        things = new QueueLinked<XMLObject>();
        this.writer = writer;
    }

    public void printXML(){
        String s = "";
        int indentCounter = 0;
        while(!things.isEmpty()){
            XMLObject nextItem = things.dequeue();
            if(nextItem instanceof XMLTag){
                XMLTag tag = (XMLTag) nextItem;
                if(tag.isClosingTag()){
                    indentCounter--;
                    s+=getTabs(indentCounter)+tag.getXMLedContents()+"\n";
                }else{
                    s+=getTabs(indentCounter)+tag.getXMLedContents()+"\n";
                    indentCounter++;
                }
            }else{
                s+=getTabs(indentCounter)+nextItem.getXMLedContents()+"\n";
            }
        }
        writer.writeLine(s);
        writer.close();
    }

    private String getTabs(int num){
        String s = "";
        for(int i = 0; i<num; i++){
            s+="    ";
        }
        return s;
    }

    public void addItemToQueue(XMLObject object){
        things.enqueue(object);
    }

}
