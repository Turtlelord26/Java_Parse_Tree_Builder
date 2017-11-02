 import java.util.zip.DataFormatException; 
 
/** 
 * hw04 
 * James Talbott 
 */ 
public class Main { 
   
  private RDParseTree tree; 
   
  //The main method splits the input into tokens and runs the constructor 
  public static void main(String[] args) { 
    if (args.length == 0) { 
      //empty input safe-fail 
    } 
    else if (args.length > 1) { 
      //multiple arguments safe-fail 
    } 
    else { 
      String[] tokens = args[0].split("\n"); 
      new Main(tokens); 
    } 
  } 
   
  //The constuctor runs the parse tree building method, and prints based on its success 
  public Main(String[] tokens) { 
    try { 
      tree = rdParse(tokens); 
      System.out.print("match"); 
    } 
    catch (DataFormatException e) { 
      System.out.print("no match"); 
    } 
  } 
   
  //just in case this class is needed later for a larger project 
  public RDParseTree getTree() { 
    return tree; 
  } 
   
  //rdParse initializes a RDParseTree with the start symbol as its root, runs the recursor, then checks success 
  public RDParseTree rdParse(String[] tokens) throws DataFormatException{ 
    RDParseTree tree = new RDParseTree(new RDParseNode("Stmts", null)); 
    int inputPointer = rdpRecurse(tokens, tree, 0); 
    if (inputPointer == tokens.length) { 
      //i.e. all input was consumed and the parse tree successfully constructed 
      return tree; 
    } 
    else { 
      //i.e. not all input was consumed, parsing failed 
      throw new DataFormatException(); 
    } 
  } 
   
  public int rdpRecurse(String[] tokens, RDParseTree subtree, int inputPointer) throws DataFormatException { 
    RDParseNode root = subtree.getRoot(); 
    String rootContent = root.getContent(); 
    int oldInputPointer = inputPointer; 
    String[][] productions; 
    String[] prod = null; 
    switch (rootContent) { 
      //The switch cases are sorted by nonterminals and temrinals first, then by order of appearance 
      case "Stmts" :  
        productions = stmts(); 
        inputPointer = recurseNonterminalHelper(tokens, productions, prod, root, oldInputPointer, inputPointer); 
        //if a production worked, return the new inputPointer 
        if (inputPointer != oldInputPointer) { 
          return inputPointer; 
        } 
        //if no production worked, break to the exception throw 
        break; 
        //the other nonterminals function identically, and their code differs only in the productions variable 
      case "Stmt" :  
        productions = stmt(); 
        inputPointer = recurseNonterminalHelper(tokens, productions, prod, root, oldInputPointer, inputPointer); 
        if (inputPointer != oldInputPointer) { 
          return inputPointer; 
        } 
        break; 
      case "Expr" :  
        productions = expr(); 
        inputPointer = recurseNonterminalHelper(tokens, productions, prod, root, oldInputPointer, inputPointer); 
        if (inputPointer != oldInputPointer) { 
          return inputPointer; 
        } 
        break; 
      case "Params" :  
        productions = params(); 
        inputPointer = recurseNonterminalHelper(tokens, productions, prod, root, oldInputPointer, inputPointer); 
        if (inputPointer != oldInputPointer) { 
          return inputPointer; 
        } 
        break; 
      case "Params_" :  
        productions = params_(); 
        inputPointer = recurseNonterminalHelper(tokens, productions, prod, root, oldInputPointer, inputPointer); 
        if (inputPointer != oldInputPointer) { 
          return inputPointer; 
        } 
        break; 
      case "Atom" :  
        productions = atom(); 
        inputPointer = recurseNonterminalHelper(tokens, productions, prod, root, oldInputPointer, inputPointer); 
        if (inputPointer != oldInputPointer) { 
          return inputPointer; 
        } 
        break; 
      case "PRINT" :  
        //When the tree hits a terminal, it must be checked against the input's next token's toktype 
        //if it matches, add that token's lexeme as a leaf node, and return the incremented inputPointer 
        //if it does not match, return the inputPointer as is. 
        //this will tell the nonterminal case that called this to try its next production 
        if (isolateType(tokens[inputPointer]) == "PRINT") { 
          root.addChild(new RDParseNode(isolateLexeme(tokens[inputPointer]), root)); 
          return inputPointer + 1; 
        } 
        else { 
          return inputPointer; 
        } 
        //the other terminals use essentially the same code as this case, with different if conditions 
      case "VAR" :  
        if (isolateType(tokens[inputPointer]) == "VAR") { 
          root.addChild(new RDParseNode(isolateLexeme(tokens[inputPointer]), root)); 
          return inputPointer + 1; 
        } 
        else { 
          return inputPointer; 
        } 
      case "NAME" : 
        if (isolateType(tokens[inputPointer]) == "NAME") { 
          root.addChild(new RDParseNode(isolateLexeme(tokens[inputPointer]), root)); 
          return inputPointer + 1; 
        } 
        else { 
          return inputPointer; 
        } 
      case "EQUAL" : 
        if (isolateType(tokens[inputPointer]) == "EQUAL") { 
          root.addChild(new RDParseNode(isolateLexeme(tokens[inputPointer]), root)); 
          return inputPointer + 1; 
        } 
        else { 
          return inputPointer; 
        } 
      case "PLUS" : 
        if (isolateType(tokens[inputPointer]) == "PLUS") { 
          root.addChild(new RDParseNode(isolateLexeme(tokens[inputPointer]), root)); 
          return inputPointer + 1; 
        } 
        else { 
          return inputPointer; 
        } 
      case "DASH" : 
        if (isolateType(tokens[inputPointer]) == "DASH") { 
          root.addChild(new RDParseNode(isolateLexeme(tokens[inputPointer]), root)); 
          return inputPointer + 1; 
        } 
        else { 
          return inputPointer; 
        } 
      case "" : 
        //this case does not interface with the input 
        root.addChild(new RDParseNode("", root)); 
        return inputPointer; 
      case "COMMA" : 
        if (isolateType(tokens[inputPointer]) == "COMMA") { 
          root.addChild(new RDParseNode(isolateLexeme(tokens[inputPointer]), root)); 
          return inputPointer + 1; 
        } 
        else { 
          return inputPointer; 
        } 
      case "NUMBER" : 
        if (isolateType(tokens[inputPointer]) == "NUMBER") { 
          root.addChild(new RDParseNode(isolateLexeme(tokens[inputPointer]), root)); 
          return inputPointer + 1; 
        } 
        else { 
          return inputPointer; 
        } 
      case "LPAREN" : 
        if (isolateType(tokens[inputPointer]) == "LPAREN") { 
          root.addChild(new RDParseNode(isolateLexeme(tokens[inputPointer]), root)); 
          return inputPointer + 1; 
        } 
        else { 
          return inputPointer; 
        } 
      case "RPAREN" : 
        if (isolateType(tokens[inputPointer]) == "RPAREN") { 
          root.addChild(new RDParseNode(isolateLexeme(tokens[inputPointer]), root)); 
          return inputPointer + 1; 
        } 
        else { 
          return inputPointer; 
        } 
      default :  
        //Receiving a token with a toktype not specified in the language is definitely a fatal error 
        throw new DataFormatException(); 
    } 
    //breaking out of the switch statement implies that a nonterminal ran out of productions 
    return inputPointer; 
  } 
   
  public int recurseNonterminalHelper(String[] tokens, String[][] productions, String[] prod, RDParseNode root, 
                                      int oldInputPointer, int inputPointer) throws DataFormatException { 
    //try each production of the nonterminal with a recursive call until one works 
    for (int i = 0; i < productions.length; i++) { 
      //If the previous production failed the root's children must be cleared 
      root.clearChildren(); 
      //productions is an array of produtions, while prod is an array of strings deifning a single production 
      prod = productions[i]; 
      for (int j = 0; j < prod.length; j++) { 
        //Each string (token) in a prod gets a node with the content as the token's toktype 
        RDParseNode newChild = new RDParseNode(isolateType(prod[j]), root); 
        root.addChild(newChild); 
        //continuing to recurse through the nonterminals 
        inputPointer = rdpRecurse(tokens, new RDParseTree(newChild), inputPointer); 
      } 
      //if it worked return the current inputPointer 
      //If the inputPointer has advanced once for each toktype in the current production, then it worked 
      if (inputPointer == oldInputPointer + prod.length) { 
        return inputPointer; 
      } 
      //If the production didn't work, the loop will try the next one (resetting the inputPointer) 
      inputPointer = oldInputPointer; 
      //if the loop has no more iterations, the oldInputPointer is returned to signal failure to advance 
    } 
    return oldInputPointer; 
  } 
   
  private String isolateType(String token) { 
    String[] s = token.split(":"); 
    return s[0]; 
  } 
   
  private String isolateLexeme(String token) { 
    String[] s = token.split(":"); 
    return s[1].substring(s[1].indexOf('\'') + 1, s[1].lastIndexOf('\'')); 
  } 
   
  /** 
   * These methods implement the language 
   * Each nonterminal has a method that returns an array of its productions, which is itself an array of Strings 
   */ 
  private static String[][] stmts() { 
    String[] a = {"Stmt","Stmts"}; 
    String[] b = {"Stmt"}; 
    String[][] r = {a,b}; 
    return r; 
  } 
   
  private static String[][] stmt() { 
    String[] a = {"PRINT","Expr"}; 
    String[] b = {"VAR","NAME","EQUAL","Expr"}; 
    String[] c = {"NAME","EQUAL","Expr"}; 
    String[] d = {"Expr"}; 
    String[][] r = {a,b,c,d}; 
    return r; 
  } 
   
  private static String[][] expr() { 
    String[] a = {"Atom"}; 
    String[] b = {"Atom","PLUS","Expr"}; 
    String[] c = {"Atom","DASH","Expr"}; 
    String[][] r = {a,b,c}; 
    return r; 
  } 
   
  private static String[][] params() { 
    String[] a = {"Expr","Params_"}; 
    String[] b = {""}; 
    String[][] r = {a,b}; 
    return r; 
  } 
   
  private static String[][] params_() { 
    String[] a = {"COMMA","Expr","Params_"}; 
    String[] b = {""}; 
    String[][] r = {a,b}; 
    return r; 
  } 
   
  private static String[][] atom() { 
    String[] a = {"Number"}; 
    String[] b = {"NAME"}; 
    String[] c = {"NAME","LPAREN","Params","RPAREN"}; 
    String[][] r = {a,b,c}; 
    return r; 
  } 
   
   
   
  //Basic tree class to hold the parse tree 
  public class RDParseTree { 
     
    private RDParseNode root; 
     
    public RDParseTree(RDParseNode root) { 
      this.root = root; 
    } 
     
    public RDParseNode getRoot() { 
      return root; 
    } 
  } 
   
  //Basic node class to populate the parse tree 
  public class RDParseNode { 
   
    private String content; 
     
    private RDParseNode parent; 
     
    private RDParseNode[] children = new RDParseNode[0]; 
     
    public RDParseNode(String content, RDParseNode parent) {  
      this.content = content; 
      this.parent = parent; 
    } 
     
    public String getContent() { 
      return content; 
    } 
     
    public RDParseNode getParent() { 
      return parent; 
    } 
     
    public RDParseNode[] getChildren() { 
     return children; 
    } 
     
    public void clearChildren() { 
      children = new RDParseNode[0]; 
    } 
     
    public void addChild(RDParseNode newChild) { 
      RDParseNode[] r = new RDParseNode[children.length + 1]; 
      int i = 0; 
      while (i < r.length) { 
        r[i] = children[i]; 
        i++; 
      } 
      r[i] = newChild; 
      children = r; 
    } 
  } 
}