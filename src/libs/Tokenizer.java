package libs;

import libs.exception.TokenizerException;
import ui.Main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static sun.plugin2.util.SystemUtil.isVerbose;

public class Tokenizer {


    private static String program;
    private static List<String> literals;
    private String[] tokens;
    private int currentToken;
    private static Tokenizer theTokenizer;

    private Tokenizer(String filename, List<String> literalsList) throws TokenizerException {
        literals = literalsList;

        try {
            program = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new TokenizerException("Didn't find file");
        }
        tokenize();
    }
    private Tokenizer(String line, List<String> literalsList, boolean isCli) {

        literals = literalsList;
        program = line;
        tokenize();
    }

    private void tokenize (){
        String tokenizedProgram = program;
        tokenizedProgram = tokenizedProgram.replace("\n"," ");
        if (Main.isVerbose())
            System.out.println(program);
        for (String s : literals){
            tokenizedProgram = tokenizedProgram.replaceAll("\\b"+s+"\\b","_"+s+"_");
            if (Main.isVerbose())
                System.out.println(tokenizedProgram);
        }
        tokenizedProgram = tokenizedProgram.replaceAll("[ ]+","");
        if (Main.isVerbose())
            System.out.println(tokenizedProgram);
        String [] temparray=tokenizedProgram.split("[_]+");
        tokens = new String[temparray.length-1];
        System.arraycopy(temparray,1,tokens,0,temparray.length-1);
        if (Main.isVerbose())
            System.out.println(Arrays.asList(tokens));
    }

    private String checkNext(){
        String token="";
        if (currentToken<tokens.length){
            token = tokens[currentToken];
        }
        else
            token="NO_MORE_TOKENS";
        return token;
    }

    public String getNext(){
        String token="";
        if (currentToken<tokens.length){
            token = tokens[currentToken];
            currentToken++;
        }
        else
            token="NULLTOKEN";
        return token;
    }


    public boolean checkToken(String regexp){
        String s = checkNext();
        if (Main.isVerbose())
            System.out.println("comparing: "+s+"  to  "+regexp);
        return (s.matches(regexp));
    }


    public String getAndCheckNext(String regexp) throws TokenizerException {
        String s = getNext();
        if (!s.matches(regexp))
            throw new TokenizerException("Token doesn't match the pattern");
        if (Main.isVerbose())
            System.out.println("matched: "+s+"  to  "+regexp);
        return s;
    }

    public boolean moreTokens(){
        return currentToken<tokens.length;
    }

    public static void makeTokenizer(String filename, List<String> literals) throws TokenizerException {
        if (theTokenizer==null){
            theTokenizer = new Tokenizer(filename,literals);
        }
    }

    public String getAndCheckUnreservedNext() throws TokenizerException {
        String next = checkNext();
        if (literals.contains(next)) {
            throw new TokenizerException("Can not use reserved word as a variable name.");
        }
        return getNext();
    }

    public static void makeCliTokenizer(String line, List<String> literals) {
        theTokenizer = new Tokenizer(line, literals, true);
    }

    public static Tokenizer getTokenizer(){
        return theTokenizer;
    }

}
