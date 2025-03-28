package app6;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class NoeudAST extends ElemAST {

  // Attributs
  private char operator;
  private ElemAST ElemGauche;
  private ElemAST ElemDroite;

  /** Constructeur pour l'initialisation d'attributs
   */
  public NoeudAST(ElemAST ElemGauche, char operator, ElemAST ElemDroite ) { // avec arguments
    this.operator = operator;
    this.ElemGauche = ElemGauche;
    this.ElemDroite = ElemDroite;
  }

 
  /** Evaluation de noeud d'AST
   */
  @Override
  public int EvalAST( ) {
    int valG = ElemGauche.EvalAST();
    int valD = ElemDroite.EvalAST();
    switch(operator){
      case '+': return valG + valD;
      case '-': return valG - valD;
      case '*': return valG*valD;
      case '/':
        if (valD == 0){ErreurEvalAST("division par zero");}
        return valG/valD;
      default: ErreurEvalAST("Operateur inconnue"); return 0;
    }
  }


  /** Lecture de noeud d'AST
   */
  public String LectAST( ) {
    return "(" + ElemGauche.LectAST() + " " + operator + " " + ElemDroite.LectAST() + ")";
  }

}


