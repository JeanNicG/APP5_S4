package app6;

/** Classe representant une feuille d'AST
 */
public class NoeudAST extends ElemAST {

  // Attributs
  private ElemAST ElemGauche;
  private String operator;
  private ElemAST ElemDroite;

  /** Constructeur pour l'initialisation d'attributs
   */
  public NoeudAST(ElemAST ElemGauche, String operator, ElemAST ElemDroite ) { // avec arguments
    this.ElemGauche = ElemGauche;
    this.operator = operator;
    this.ElemDroite = ElemDroite;
  }
 
  /** Evaluation de noeud d'AST
   */
  @Override
  public int EvalAST( ) {
    int valG = ElemGauche.EvalAST();
    int valD = ElemDroite.EvalAST();
    switch(operator){
      case "+": return valG + valD;
      case "-": return valG - valD;
      case "*": return valG * valD;
      case "/":
        if (valD == 0){ErreurEvalAST("division par zero");}
        return valG / valD;
      default: ErreurEvalAST("Operateur inconnue"); return 0;
    }
  }

  /** Lecture de noeud d'AST
   */
  public String LectAST( ) {
    return "(" + ElemGauche.LectAST() + " " + operator + " " + ElemDroite.LectAST() + ")";
  }
  /** conversion en notation PostFix
   */
  public String PostFix(){
    return ElemGauche.PostFix() + " " + ElemDroite.PostFix() + " " + operator;
  }
}


