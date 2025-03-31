package app6;

/** @author Ahmed Khoumsi */

/** Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

  // Attributs
  private int ptrLecture;
  private String input;

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
    int temp = ptrLecture;
    while (temp < input.length() && Character.isWhitespace(input.charAt(temp))) {
      temp++;
    }
    return temp < input.length();
  }


  /**
   * prochainTerminal() retourne le prochain terminal
   * Cette methode est une implementation d'un AEF
   */
  public Terminal prochainTerminal() throws Exception {
    while(resteTerminal() && Character.isWhitespace(input.charAt(ptrLecture))) ptrLecture++;
    if (!resteTerminal()) return null;

    char currentChar = input.charAt(ptrLecture);

    // si un chiffre lire l'entier au complet
    if (Character.isDigit(currentChar)){
      String stringReturn = "";
      while(resteTerminal() && Character.isDigit((input.charAt(ptrLecture)))){
        stringReturn += input.charAt(ptrLecture);
        ptrLecture++;
      }
      return new Terminal(stringReturn, "Nombre");
    }

    //si lettre majuscule lire un identificateur
    if(currentChar >= 'A' && currentChar <= 'Z'){
      String stringReturn = "";
      stringReturn += currentChar;
      ptrLecture++;
      while (resteTerminal() && (Character.isLetter(input.charAt(ptrLecture)) || input.charAt(ptrLecture) == '_')){
        stringReturn += input.charAt(ptrLecture);
        ptrLecture++;
      }
      if (!stringReturn.matches("[A-Z](?:[A-Za-z]|_(?=[A-Za-z]))*")){
        ErreurLex("Identifiant invalide " + stringReturn);
      }
      return new Terminal(stringReturn, "Identifiant");
    }

    // si operateur ou parenthese
    if (currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/' ||
            currentChar == '(' || currentChar == ')') {
      ptrLecture++;
      return new Terminal(Character.toString(currentChar), "Operateur");
    }

    ErreurLex("Caractere non reconnu: " + currentChar);
    return null;

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
