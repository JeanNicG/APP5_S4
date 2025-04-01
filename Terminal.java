  package app6;

  /** @author Ahmed Khoumsi */

  /** Cette classe identifie les terminaux reconnus et retournes par
   *  l'analyseur lexical
   */
  public class Terminal {

    public String chaine;
    public String type;
    //Exp -> Terme | Terme '+' Exp |  Terme '-' Exp
    //Terme -> Facteur | Facteur '*' Terme | Facteur '/' Terme
    //Facteur -> Entier | Identifiant | '(' Exp ')'

  private void setType(){
    if (chaine.matches("[A-Z]([A-Za-z_])*")){
      type = "Identificateur";
    }else if (chaine.matches("\\d+")){
      type = "Nombre";
    } else if (chaine.matches("[+]")){
      type = "Addition";
    }else if (chaine.matches("[-]")){
      type = "Soustraction";
    }else if (chaine.matches("[*]")){
      type = "Multiplication";
    }else if (chaine.matches("[/]")){
      type = "Division";
    }else if (chaine.matches("[()]")){
      type = "Parenthese";
    }
  }
  /** Un ou deux constructeurs (ou plus, si vous voulez)
    *   pour l'initalisation d'attributs
   */
    public Terminal( ) {   // arguments possibles
       this.chaine = "";
    }

    public Terminal( String thisTerminalSym) {   // arguments possibles
      this.chaine = thisTerminalSym;
      setType();
    }
  }
