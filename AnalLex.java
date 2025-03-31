package app6;

/** @author Ahmed Khoumsi */

/** Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

// Attributs
//  ...
  private String input;
  private int ptr;
  private int etat;


  /** Constructeur pour l'initialisation d'attribut(s)
   */
  public AnalLex(String string ) {  // arguments possibles
    //
    this.ptr = 0;
    this.input = string;
    this.etat = 0;
  }


  /** resteTerminal() retourne :
   false  si tous les terminaux de l'expression arithmetique ont ete retournes
   true s'il reste encore au moins un terminal qui n'a pas ete retourne
   */
  public boolean resteTerminal( ) {
    //
    if(ptr < input.length() - 1 && Character.isWhitespace(input.charAt(ptr))) return false;
    return ptr < input.length();
  }

  public char lectureChar(){
    int temp = ptr;
    ptr++;
    return input.charAt(temp);
  }

  public boolean isOperator(char c){
    return c == '+' || c == '-' || c == '/' || c == '*';
  }

  public boolean isNumber(char c){
    return c >= '0' && c <= '9';
  }

  public boolean isMajuscule(char c){
    return c >= 'A' && c <= 'Z';
  }

  public boolean isMinuscule(char c){
    return c >= 'a' && c <= 'z';
  }

  public boolean isLetter(char c){
    return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
  }

  /** prochainTerminal() retourne le prochain terminal
   Cette methode est une implementation d'un AEF
   */
  public Terminal prochainTerminal( ) {
    //
    char c;
    Terminal terminal = new Terminal();
    //boolean next = true;

    while(next && resteTerminal()){
      switch(etat){
        case 0:
          c = lectureChar();
          if(isMajuscule(c)){
            terminal.input += c;
            etat = 2;
          }
          else if (isNumber(c)) {
            terminal.input += c;
            etat = 1;
          }
          else if (isOperator(c)) {
            terminal.input += c;
            //next = false;
          }
          else{
            ErreurLex("big mistake");
          }
          //case numbers
        case 1:
          c = lectureChar();
          if(isNumber(c)){
            terminal.input += c;
            etat = 1;
          } else if (isOperator(c) || Character.isWhitespace(c) ) {
            ptr--;
            return terminal; //checker
            //next = false;
          }
          else {
            ErreurLex("sortie etat 1");
          }
        case 2:
          c = lectureChar();
          if(isLetter(c)){
            etat = 2;
            terminal.input += c;
          }
          else if(isOperator(c) || Character.isWhitespace(c)){
            ptr--;
          } else if (c == '_') {
            terminal.input += c;
            etat = 3;
          }
          else{
            ErreurLex("sortie etat 2");
          }
        case 3:
          c = lectureChar();
          if(isLetter(c)){
            terminal.input += c;
            etat = 2;
          }
          else{
            ErreurLex("Sortie etat 3");
          }
      }
    }
  }


  /** ErreurLex() envoie un message d'erreur lexicale
   */
  public void ErreurLex(String s) {
    system.out.println(s);
  }


  //Methode principale a lancer pour tester l'analyseur lexical
  public static void main(String[] args) {
    String toWrite = "";
    System.out.println("Debut d'analyse lexicale");
    if (args.length == 0){
      args = new String [2];
      args[0] = "ExpArith.txt";
      args[1] = "ResultatLexical.txt";
    }
    Reader r = new Reader(args[0]);

    AnalLex lexical = new AnalLex(r.toString()); // Creation de l'analyseur lexical

    // Execution de l'analyseur lexical
    Terminal t = null;
    while(lexical.resteTerminal()){
      t = lexical.prochainTerminal();
      toWrite +=t.chaine + "\n" ;  // toWrite contient le resultat
    }				   //    d'analyse lexicale
    System.out.println(toWrite); 	// Ecriture de toWrite sur la console
    Writer w = new Writer(args[1],toWrite); // Ecriture de toWrite dans fichier args[1]
    System.out.println("Fin d'analyse lexicale");
  }
}
