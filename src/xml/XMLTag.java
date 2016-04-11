package xml;

public class XMLTag implements XMLObject {

    private String contents;

    public XMLTag(String contents){
        this.contents = contents;
    }

    @Override
    public final boolean isTag() {
        return true;
    }

    public final boolean isClosingTag(){
        return contents.charAt(0)=='/';
    }

    @Override
    public String getContents() {
        return contents;
    }

    @Override
    public String getXMLedContents() {
        return "<"+contents+">";
    }
}
