package app6;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class FeuilleAST extends ElemAST {

  // Attribut(s)
  private String valeur;

/**Constructeur pour l'initialisation d'attribut(s)
 */
  public FeuilleAST(String valeur) {  // avec arguments
      this.valeur = valeur;
  }


  /** Evaluation de feuille d'AST
   */
  @Override
  public int EvalAST( ) {
      if(valeur.matches("\\d+")){
          return Integer.parseInt(valeur);
      } else{
          ErreurEvalAST("Impossible d'evaluer l'identificateur: " + valeur);
          return 0;
      }
  }


 /** Lecture de chaine de caracteres correspondant a la feuille d'AST
  */
  public String LectAST( ) {
    return this.valeur;
  }

}
