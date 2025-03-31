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
    while (ptrLecture < input.length() && Character.isWhitespace(input.charAt(ptrLecture))) {
      ptrLecture++;
    }
    return ptrLecture < input.length();
  }


  /**
   * prochainTerminal() retourne le prochain terminal
   * Cette methode est une implementation d'un AEF
   */
  public Terminal prochainTerminal() throws Exception {
    String returnString = "";
    while (resteTerminal()) {
      char currentChar = input.charAt(ptrLecture);
      switch (state) {
        case 0:
          if (String.valueOf(currentChar).matches("[0-9]+")) {
            returnString += currentChar;
            state = 1;
            ptrLecture++;
          }
          else if (String.valueOf(currentChar).matches("[A-Z]")) {
            returnString += currentChar;
            state = 2;
            ptrLecture++;
          }
          else if (String.valueOf(currentChar).matches("[-+*/()]")) {
            state = 0;
            returnString += currentChar;
            ptrLecture++;
            return new Terminal(returnString, "operator");
          }
          else if (Character.isWhitespace(currentChar)) continue;
          else ErreurLex("Etat 0: n'a pas pu lire le caractère '" + currentChar + "'");
          break;
        case 1:
          if (String.valueOf(currentChar).matches("[0-9]+")) {
            returnString += currentChar;
            state = 1;
            ptrLecture++;
          }
          else if (String.valueOf(currentChar).matches("[-+*/()]") | Character.isWhitespace(currentChar)) {
            state = 0;
            return new Terminal(returnString, "Entier");
          }
          else ErreurLex("Etat 1: n'a pas pu lire le caractère '" + currentChar + "'");
          break;
        case 2:
          if (String.valueOf(currentChar).matches("[A-Za-z]")) {
            returnString += currentChar;
            state = 2;
            ptrLecture++;
          }
          else if (String.valueOf(currentChar).matches("(_)")) {
            returnString += currentChar;
            state = 3;
            ptrLecture++;
          }
          else if (String.valueOf(currentChar).matches("[-+*/()]") | Character.isWhitespace(currentChar)) {
            state = 0;
            return new Terminal(returnString, "Identifiant");
          }
          else ErreurLex("Etat 2: n'a pas pu lire le caractère '" + currentChar + "'");
          break;
        case 3:
          if (String.valueOf(currentChar).matches("[A-Za-z]")) {
            returnString += currentChar;
            state = 2;
            ptrLecture++;
          }
          else ErreurLex("Etat 3: n'a pas pu lire le caractère '" + currentChar + "'");
          break;
        default:
          ErreurLex("Erreur de l'AEF");
          break;
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
        toWrite += t.chaine + "\n";  // toWrite contient le resultat
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
