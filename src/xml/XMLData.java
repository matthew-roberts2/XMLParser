package xml;

public class XMLData implements XMLObject{

    private String contents;

    public XMLData(String contents){
        this.contents = contents;
    }

    @Override
    public final boolean isTag() {
        return false;
    }

    @Override
    public String getContents() {
        return contents;
    }

    @Override
    public String getXMLedContents(){
        return contents;
    }
}
