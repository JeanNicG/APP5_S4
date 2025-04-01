package app6;

/** @author Ahmed Khoumsi */

/** Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

  // Attributs
  private int ptrLecture;
  private String input;
  int state;

  /**
   * Constructeur pour l'initialisation d'attribut(s)
   */
  public AnalLex(String input) {  // arguments possibles
    this.ptrLecture = 0;
    this.input = input;
  }


  /**
   * resteTerminal() retourne :
   * false  si tous les terminaux de l'expression arithmetique ont ete retournes
   * true s'il reste encore au moins un terminal qui n'a pas ete retourne
   */
  public boolean resteTerminal() {
    while (ptrLecture < input.length() && Character.isWhitespace(input.charAt(ptrLecture))) ptrLecture++;
    return ptrLecture < input.length();
  }

  /**
   * lectureChar() retourne :
   * le char a la postion du ptrLecture dans l'input
   * */
  public char lectureChar(){
    return input.charAt(ptrLecture++);
  }


  /**
   * prochainTerminal() retourne le prochain terminal
   * Cette methode est une implementation d'un AEF
   */
  public Terminal prochainTerminal() throws Exception {
    char currentChar;
    String returnString = "";
    while (resteTerminal()) {
      switch (state) {
        case 0:
          currentChar = lectureChar();
          if (String.valueOf(currentChar).matches("[0-9]+")) {
            returnString += currentChar;
            state = 1;
          }
          else if (String.valueOf(currentChar).matches("[A-Z]")) {
            returnString += currentChar;
            state = 2;
          }
          else if (String.valueOf(currentChar).matches("[-+*/()]")) {
            returnString += currentChar;
            state = 0;
            return new Terminal(returnString);
          }
          else if (Character.isWhitespace(currentChar)) continue;
          else ErreurLex("Erreur apres la sequence: " + input.substring(0,ptrLecture-1) + "\nCause: Caractere interdit " + currentChar + "\nCaractere permit: Lettre, Nombre, Operateur");
          break;
        case 1:
          currentChar = lectureChar();
          if (String.valueOf(currentChar).matches("[0-9]+")) {
            returnString += currentChar;
            state = 1;
          }
          else if (String.valueOf(currentChar).matches("[-+*/()]") | Character.isWhitespace(currentChar)) {
            state = 0;
            ptrLecture--;
            return new Terminal(returnString);
          }
          else ErreurLex("Erreur apres la sequence: " + input.substring(0,ptrLecture-1) + "\nCause: Caractere interdit " + currentChar + "\nCaractere permit: Nombre, Operateur");
          break;
        case 2:
          currentChar = lectureChar();
          if (String.valueOf(currentChar).matches("[A-Za-z]")) {
            returnString += currentChar;
            state = 2;
          }
          else if (String.valueOf(currentChar).matches("(_)")) {
            returnString += currentChar;
            state = 3;
          }
          else if (String.valueOf(currentChar).matches("(\\+|-|\\*|\\/|\\(|\\))") || Character.isWhitespace(currentChar)) {
            state = 0;
            ptrLecture--;
            return new Terminal(returnString);
          }
          else ErreurLex("Erreur apres la sequence: " + input.substring(0,ptrLecture-1) + "\nCause: Caractere interdit " + currentChar + "\nCaractere permit: Lettre, Operateur, '_'");
          break;
        case 3:
          currentChar = lectureChar();
          if (String.valueOf(currentChar).matches("[A-Za-z]")) {
            returnString += currentChar;
            state = 2;
          }
          else ErreurLex("Erreur apres la sequence: " + input.substring(0,ptrLecture-1) + "\nCause: Caractere interdit " + currentChar + "\nCaractere permit: Lettre");
          break;
        default:
          ErreurLex("Erreur de l'AEF");
          break;
      }
    }
    if (!returnString.isEmpty()) {
      if (state == 1) {
        state = 0;
        return new Terminal(returnString);
      } else if (state == 2) {
        state = 0;
        return new Terminal(returnString);
      }
    }
    return new Terminal();
  }

  /**
   * ErreurLex() envoie un message d'erreur lexicale
   */
  public void ErreurLex(String s) throws Exception {
    System.err.println("ErreurLex: " + s);
    throw new Exception(s);
  }
  public String getInput(){
    return this.input;
  }
  public int getPtrLecture(){
    return this.ptrLecture;
  }

  //Methode principale a lancer pour tester l'analyseur lexical
  public static void main(String[] args) {
    try {
      String toWrite = "";
      System.out.println("Debut d'analyse lexicale");
      if (args.length == 0) {
        args = new String[2];
        args[0] = "ExpArith.txt";
        args[1] = "ResultatLexical.txt";
      }
      Reader r = new Reader(args[0]);

      AnalLex lexical = new AnalLex(r.toString()); // Creation de l'analyseur lexical

      // Execution de l'analyseur lexical
      Terminal t = null;
      while (lexical.resteTerminal()) {
        t = lexical.prochainTerminal();
        toWrite += t.type + " " + t.chaine + "\n";  // toWrite contient le resultat
      }
      //    d'analyse lexicale
      System.out.println(toWrite);    // Ecriture de toWrite sur la console
      Writer w = new Writer(args[1], toWrite); // Ecriture de toWrite dans fichier args[1]
      System.out.println("Fin d'analyse lexicale");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
