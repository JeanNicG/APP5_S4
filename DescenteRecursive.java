package app6;

/** @author Ahmed Khoumsi */

import java.lang.annotation.ElementType;

/** Cette classe effectue l'analyse syntaxique
 */
public class DescenteRecursive {
  // Attributs
  private AnalLex lex;
  private Terminal courant;

/** Constructeur de DescenteRecursive :
      - recoit en argument le nom du fichier contenant l'expression a analyser
      - pour l'initalisation d'attribut(s)
 */
public DescenteRecursive(String in) {
  try{
    Reader r = new Reader(in);
    String input = r.toString();
    this.lex = new AnalLex(input);
    if(lex.resteTerminal()){
      courant = lex.prochainTerminal();
    }
  }catch (Exception e) {
    System.err.println("Erreur DescenteRecursive: " + e.getMessage());
  }
}


/** AnalSynt() effectue l'analyse syntaxique et construit l'AST.
 *    Elle retourne une reference sur la racine de l'AST construit
 */
public ElemAST AnalSynt( ) throws Exception {
  ElemAST expr = parseExp();
  if(lex.resteTerminal()){
    ErreurSynt("Symboles inattendus en fin d'expression.");
  }
  return expr;
}


// Methode pour chaque symbole non-terminal de la grammaire retenue
  // Exp → Terme [ ('+' | '-') Exp ]
  private ElemAST parseExp() throws Exception{
    ElemAST terme = parseTerme();
    if (courant != null && (courant.chaine.equals("+") || courant.chaine.equals("-"))){
      String operateur = courant.chaine;
      courant = lex.prochainTerminal();
      ElemAST exp = parseExp(); //recursivite droite
      return new NoeudAST(terme, operateur, exp);
    } else {
      return terme;
    }
  }

  //Terme → Facteur [ ('*' | '/') Terme ]
  private ElemAST parseTerme() throws Exception{
    ElemAST facteur = parseFacteur();
    if (courant != null && (courant.chaine.equals("*") || courant.chaine.equals("/"))){
      String operateur = courant.chaine;
      courant = lex.prochainTerminal();
      ElemAST terme = parseTerme(); //recursivite droite
      return new NoeudAST(facteur, operateur, terme);
    }else{
      return facteur;
    }
  }

  // Facteur → <entier> | <identificateur> | '(' Exp ')'
  private ElemAST parseFacteur() throws Exception{
    if (courant == null){
      ErreurSynt("Fin inattendue, un opérande ou '(' était attendu");
    }
    if(courant.chaine.equals("(")){
      courant = lex.prochainTerminal();
      ElemAST expr = parseExp();
      if (courant == null || !courant.chaine.equals(")")){
        ErreurSynt("')' attendu");
      }
      courant = lex.prochainTerminal();
      return expr;
    } else if (courant.chaine.matches("\\d+") || courant.chaine.matches("[A-Z]([a-zA-Z]|_(?!_))*[a-zA-Z]")){
      FeuilleAST feuille = new FeuilleAST(courant.chaine);
      courant = lex.prochainTerminal();
      return feuille;
    }
    else {
      ErreurSynt("Opérande attendu, trouvé : " + courant.chaine);
    }
    return null;
  }


/** ErreurSynt() envoie un message d'erreur syntaxique
 */
public void ErreurSynt(String s)
{
  throw new RuntimeException("Erreur syntaxique: " + s);
}



  //Methode principale a lancer pour tester l'analyseur syntaxique 
  public static void main(String[] args) {
    String toWriteLect = "";
    String toWriteEval = "";
    String toWritePostFix = "";

    System.out.println("Debut d'analyse syntaxique");
    if (args.length == 0){
      args = new String [2];
      args[0] = "ExpArith.txt";
      args[1] = "ResultatSyntaxique.txt";
    }
    DescenteRecursive dr = new DescenteRecursive(args[0]);
    try {
      ElemAST RacineAST = dr.AnalSynt();
      toWriteLect += "Lecture de l'AST trouve : " + RacineAST.LectAST() + "\n";
      System.out.println(toWriteLect);
      toWritePostFix += "ExpressionPostFix : " + RacineAST.PostFix() +  "\n";
      System.out.println(toWritePostFix);
      toWriteEval += "Evaluation de l'AST trouve : " + RacineAST.EvalAST() + "\n";
      System.out.println(toWriteEval);
      Writer w = new Writer(args[1],toWriteLect+toWritePostFix+toWriteEval); // Ecriture de toWrite
                                                              // dans fichier args[1]
    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
      System.exit(51);
    }
    System.out.println("Analyse syntaxique terminee");
  }

}

