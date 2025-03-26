package app6;

/** @author Ahmed Khoumsi */

/** Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

  // Attributs
  private int ptrLecture;
  private String input;
  private int state;


  /**
   * Constructeur pour l'initialisation d'attribut(s)
   */
  public AnalLex(String input) {  // arguments possibles
    this.ptrLecture = 0;
    this.input = input;
    this.state = 0;
  }


  /**
   * resteTerminal() retourne :
   * false  si tous les terminaux de l'expression arithmetique ont ete retournes
   * true s'il reste encore au moins un terminal qui n'a pas ete retourne
   */
  public boolean resteTerminal() {
    return ptrLecture < input.length();
  }


  /**
   * prochainTerminal() retourne le prochain terminal
   * Cette methode est une implementation d'un AEF
   */
  public Terminal prochainTerminal() throws Exception {
    String terminalReturn = "";
    while (resteTerminal()) {
      char currentChar = input.charAt(ptrLecture);
      ptrLecture++;
      switch (state) {
        case 0:
          if (currentChar == '+') {
            terminalReturn += "+";
            return new Terminal(terminalReturn);
          }
          if (currentChar == '0' || currentChar == '1') {
            state = 1;
            terminalReturn += currentChar;
            break;
          }
          ErreurLex("Erreur");
          return null;
        case 1:
          if (currentChar == '0' || currentChar == '1') {
            terminalReturn += currentChar;
            break;
          } else {
            ptrLecture--;
            state = 0;
            return new Terminal(terminalReturn);
          }
      }
    }
    return new Terminal(terminalReturn);
  }


  /**
   * ErreurLex() envoie un message d'erreur lexicale
   */
  public void ErreurLex(String s) throws Exception {
    System.out.println(s);
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
