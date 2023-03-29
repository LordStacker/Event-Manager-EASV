package dk.easv.be;

public class QRCode {
    private String codedText;

    public QRCode(String codedText){
        this.codedText = codedText;
    }

    public String getCodedText(){return codedText;}

    public void setCodedText(String codedText){this.codedText = codedText;}
}
